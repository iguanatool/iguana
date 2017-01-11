package org.iguanatool.testobject;

import org.iguanatool.Config;
import org.iguanatool.library.SimpleIO;
import org.iguanatool.library.SystemCommand;
import org.iguanatool.library.SystemCommandException;
import org.iguanatool.library.TextFile;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BuildCParser {

    private static final String SRC_DIR = "iguana/testobject/cparser";
    private static final String META_SRC_DIR = "cparser";

    private static String JJTREE_SRC_FILENAME = "c.jjt";
    private static String JAVACC_SRC_FILENAME = "c.jj";

    private static File srcPath;
    private static File metaSrcPath;

    public static void main(String[] args) throws Exception {
        Config config = new Config(args);

        srcPath = new File(config.getIguanaSourcePath() + "/" + SRC_DIR);
        metaSrcPath = new File(config.getIguanaPath() + "/" + META_SRC_DIR);

        SimpleIO.recursiveDelete(srcPath);
        SimpleIO.ensureDirectoryExists(srcPath);
        invokeJavacc();
        modifyCParser();
        modifyCParserTokenManager();
        modifyCParserVisitor();
        modifySimpleNode();
        modifyNode();
        modifyASTFiles();
        insertAdditionalCode();
        copyAdditionalJavaFiles();
    }

    private static void invokeJavacc() throws IOException, SystemCommandException {
        File origJJTreeFile = new File(metaSrcPath + "/" + JJTREE_SRC_FILENAME);
        File jjtreeFile = new File(srcPath + "/" + JJTREE_SRC_FILENAME);
        SimpleIO.copyFile(origJJTreeFile, jjtreeFile);

        File javaccFile = new File(srcPath + "/" + JAVACC_SRC_FILENAME);

        SystemCommand.execute("java jjtree " + JJTREE_SRC_FILENAME, srcPath);
        SystemCommand.execute("java javacc " + JAVACC_SRC_FILENAME, srcPath);

        if (!jjtreeFile.delete()) {
            System.out.println("Failed to delete " + jjtreeFile);
        }

        if (!javaccFile.delete()) {
            System.out.println("Failed to delete " + javaccFile);
        }

    }

    private static void modifyCParser() throws IOException {
        Map<String, String> sr = new LinkedHashMap<String, String>();

        suppressWarnings(sr, "static private final class LookaheadSuccess extends java.lang.Error { }", "serial");
        replace(sr, "throw new Error(\"Missing return statement in function\");", "return jjtn000;");
        delete(sr, "{if (true) return jjtn000;}");

        performModifications("CParser.java", sr);
    }

    private static void modifyCParserTokenManager() throws IOException {
        Map<String, String> sr = new LinkedHashMap<String, String>();

        delete(sr, "import java.util.ArrayDeque;");
        delete(sr, "import java.util.Collections;");
        delete(sr, "import java.util.Deque;");
        delete(sr, "import java.util.HashSet;");
        delete(sr, "import java.util.Set;");
        delete(sr, "import java.io.File;");
        delete(sr, "import java.io.FileInputStream;");
        delete(sr, "import java.io.IOException;");
        suppressWarnings(sr, "private int jjimageLen;", "unused");
        suppressWarnings(sr, "private int lengthOfMatch;", "unused");

        performModifications("CParserTokenManager.java", sr);
    }

    private static void modifyCParserVisitor() throws IOException {
        Map<String, String> sr = new LinkedHashMap<String, String>();
        delete(sr, ", Object data");
        performModifications("CParserVisitor.java", sr);
    }

    private static void modifySimpleNode() throws IOException {
        Map<String, String> sr = new LinkedHashMap<String, String>();
        delete(sr, ", Object data");
        delete(sr, "return data;");
        replace(sr, "visitor.visit(this, data);", "visitor.visit(this);");
        replace(sr, "Object childrenAccept", "void childrenAccept");
        replace(sr, "children[i].jjtAccept(visitor, data);", "children[i].jjtAccept(visitor);");
        performModifications("SimpleNode.java", sr);
    }

    private static void modifyNode() throws IOException {
        Map<String, String> sr = new LinkedHashMap<String, String>();
        replace(sr, "public void jjtAccept(CParserVisitor visitor, Object data)", "public void jjtAccept(CParserVisitor visitor)");
        performModifications("Node.java", sr);
    }

    private static void modifyASTFiles() throws IOException {
        Map<String, String> sr = new LinkedHashMap<String, String>();
        replace(sr, "public void jjtAccept(CParserVisitor visitor, Object data) {", "public void jjtAccept(CParserVisitor visitor) {");
        replace(sr, "visitor.visit(this, data);", "visitor.visit(this);");

        File[] files = srcPath.listFiles(new FilenameFilter() {
            public boolean accept(File f, String s) {
                return s.startsWith("AST");
            }
        });

        for (File file : files) {
            performModifications(file.getName(), sr);
        }
    }

    private static void insertAdditionalCode() throws IOException {
        File[] files = metaSrcPath.listFiles(new FilenameFilter() {
            public boolean accept(File f, String s) {
                return s.endsWith(".java");
            }
        });

        for (File file : files) {
            File targetFile = new File(srcPath + "/" + file.getName());
            if (targetFile.exists()) {
                List<String> codeToAdd = SimpleIO.readTextLines(file);
                String imports = "";
                String body = "";

                for (String line : codeToAdd) {
                    if (line.startsWith("import")) {
                        imports += line + "\n";
                    } else {
                        body += "\t" + line + "\n";
                    }
                }

                String codeToModify = SimpleIO.readText(targetFile);
                int endOfPackage = codeToModify.indexOf(";");
                int endOfClassDeclaration = codeToModify.indexOf("{");

                String modifiedCode = codeToModify.substring(0, endOfPackage + 1) +
                        "\n" + imports +
                        codeToModify.substring(endOfPackage + 1, endOfClassDeclaration + 1) +
                        "\n" + body +
                        codeToModify.substring(endOfClassDeclaration + 1, codeToModify.length());

                SimpleIO.writeText(targetFile, modifiedCode, true);
            }
        }
    }

    private static void copyAdditionalJavaFiles() throws IOException {
        File[] files = metaSrcPath.listFiles(new FilenameFilter() {
            public boolean accept(File f, String s) {
                return s.endsWith(".java");
            }
        });

        for (File file : files) {
            File targetFile = new File(srcPath + "/" + file.getName());
            if (!targetFile.exists()) {
                SimpleIO.copyFile(file, targetFile);
            }
        }
    }

    private static void performModifications(String fileName, Map<String, String> searchAndReplace) throws IOException {
        TextFile.searchAndReplace(new File(srcPath + "/" + fileName), searchAndReplace);
    }

    private static void delete(Map<String, String> searchAndReplace, String code) {
        searchAndReplace.put(code, "");
    }

    private static void suppressWarnings(Map<String, String> searchAndReplace, String code, String suppressionType) {
        searchAndReplace.put(code, "@SuppressWarnings(\"" + suppressionType + "\")\n" + code);
    }

    private static void replace(Map<String, String> searchAndReplace, String search, String replace) {
        searchAndReplace.put(search, replace);
    }
}
