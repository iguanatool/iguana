package org.iguanatool.testobject;

import org.iguanatool.Config;
import org.iguanatool.library.SimpleIO;
import org.iguanatool.library.SystemCommandException;
import org.iguanatool.testobject.cparser.ASTParseUnit;
import org.iguanatool.testobject.cparser.CParser;
import org.iguanatool.testobject.cparser.ParseException;
import org.iguanatool.testobject.structure.CFG;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CaseStudy {

    // directory name constants
    private static final String CASE_STUDIES_DIR = "casestudies";
    private static final String CACHE_DIR = "cache";
    private static final String INSTRUMENTED_CODE_DIR = "instrumented";
    private static final String CONTROL_GRAPHS_DIR = "controlgraphs";

    // file name constants
    private static final String TEST_OBJECTS_FILE = "testobjects";

    // graphic constants
    private static final String CONTROL_GRAPH_IMAGE_TYPE_PROPERTY = "control_graph_image_type";
    private static final String IMAGE_TYPE_NONE = "none";
    private static final String IMAGE_TYPE_DOT = "dot";

    private String caseStudyName;
    private List<String> testObjectNames;

    private ASTParseUnit parseUnit;
    private Map<String, CFG> cfgs;
    private String instrumentedCode;

    public CaseStudy(String caseStudyName) {
        this.caseStudyName = caseStudyName;
    }

    public String getName() {
        return caseStudyName;
    }

    public File getCaseStudyPath() {
        return new File(Config.getInstance().getIguanaPath() + "/" + CASE_STUDIES_DIR + "/" + caseStudyName);
    }

    private File getCachePath() {
        return new File(getCaseStudyPath() + "/" + CACHE_DIR);
    }

    private File getInstrumentedCodePath() {
        return new File(getCaseStudyPath() + "/" + INSTRUMENTED_CODE_DIR);
    }

    private File getGraphicsPath() {
        return new File(getCaseStudyPath() + "/" + CONTROL_GRAPHS_DIR);
    }

    private String getSourceFileName() {
        return caseStudyName + ".c";
    }

    private File getSourceFile() {
        return new File(getCaseStudyPath() + "/" + getSourceFileName());
    }

    public File getInstrumentedCodeFile(String testObjectName) {
        return new File(getInstrumentedCodePath() + "/" + testObjectName + ".c");
    }

    public File getCFGFile(String testObjectName) {
        return new File(getCachePath() + "/" + testObjectName + ".cfg");
    }

    private File getTestObjectsFile() {
        return new File(getCaseStudyPath() + "/" + TEST_OBJECTS_FILE);
    }

    private File getCFGDotFile(String testObjectName) {
        return getCFGGraphicFile(testObjectName, IMAGE_TYPE_DOT);
    }

    private File getCDGDotFile(String testObjectName) {
        return getCDGGraphicFile(testObjectName, IMAGE_TYPE_DOT);
    }

    private File getCFGGraphicFile(String testObjectName, String imageType) {
        return new File(getGraphicsPath() + "/" + testObjectName + "-flow." + imageType);
    }

    private File getCDGGraphicFile(String testObjectName, String imageType) {
        return new File(getGraphicsPath() + "/" + testObjectName + "-dep." + imageType);
    }

    public void parse() throws IOException, ParseException {
        CParser parser = new CParser(getSourceFile());
        parseUnit = parser.ParseUnit();
        try {
            loadTestObjectsFile();
        } catch (FileNotFoundException e) {
            testObjectNames = parseUnit.getFunctionNames();
            Collections.sort(testObjectNames);
            SimpleIO.writeText(getTestObjectsFile(), testObjectNames, false);
        }
    }

    public void loadTestObjectsFile() throws IOException {
        testObjectNames = SimpleIO.readTextLines(getTestObjectsFile(), true);
    }

    public void createCFGs() {
        if (parseUnit == null) {
            throw new RuntimeException("Cannot create CFGs until source code is parsed");
        }
        cfgs = CFGExtractor.extractCFGs(parseUnit, getTestObjectNames());
    }

    public boolean instrument(String testObjectName) throws IOException {
        if (cfgs == null) {
            throw new RuntimeException("Cannnot instrument until CFGs created");
        }

        File testObjectsFile = getTestObjectsFile();
        File sourceFile = getSourceFile();
        File instrumentedCodeFile = getInstrumentedCodeFile(testObjectName);

        boolean testObjectsFileOlder = SimpleIO.lastModified(testObjectsFile, instrumentedCodeFile).equals(instrumentedCodeFile);
        boolean sourceFileOlder = SimpleIO.lastModified(sourceFile, instrumentedCodeFile).equals(instrumentedCodeFile);

        if (testObjectsFileOlder && sourceFileOlder) {
            instrumentedCode = SimpleIO.readText(instrumentedCodeFile);
            return false;
        } else {
            instrumentedCode = Instrumenter.instrument(parseUnit, testObjectName);
            SimpleIO.ensureDirectoryExists(getInstrumentedCodePath());
            SimpleIO.writeText(instrumentedCodeFile, instrumentedCode, true);
            return true;
        }
    }

    public String getInstrumentedCode() {
        return instrumentedCode;
    }

    public List<String> getTestObjectNames() {
        return Collections.unmodifiableList(testObjectNames);
    }

    public int getTestObjectID(String testObjectName) {
        return Collections.binarySearch(testObjectNames, testObjectName);
    }

    public boolean serializeCFG(String testObjectName) throws IOException {
        File cfgFile = getCFGFile(testObjectName);
        File instrumentedCodeFile = getInstrumentedCodeFile(testObjectName);

        boolean instrumentedCodeFileOlder = SimpleIO.lastModified(cfgFile, instrumentedCodeFile).equals(cfgFile);
        if (instrumentedCodeFileOlder) {
            return false;
        }

        CFG cfg = cfgs.get(testObjectName);
        SimpleIO.ensureDirectoryExists(getCachePath());
        SimpleIO.serialize(cfg, cfgFile);
        return true;
    }

    public boolean createControlGraphImages(String testObjectName) throws IOException, SystemCommandException {
        String imageType = Config.getInstance().getProperty(CONTROL_GRAPH_IMAGE_TYPE_PROPERTY);

        if (imageType.equals(IMAGE_TYPE_NONE)) {
            return false;
        }

        SimpleIO.ensureDirectoryExists(getGraphicsPath());
        ControlGraphImageGenerator g = new ControlGraphImageGenerator(cfgs.get(testObjectName));

        File cfgFile = getCFGFile(testObjectName);
        File cfgDotFile = getCFGDotFile(testObjectName);
        File cdgDotFile = getCDGDotFile(testObjectName);
        File cfgGraphicFile = getCFGGraphicFile(testObjectName, imageType);
        File cdgGraphicFile = getCDGGraphicFile(testObjectName, imageType);

        boolean writtenAFile = false;

        if (imageType.equals(IMAGE_TYPE_DOT) ||
                SimpleIO.lastModified(cfgFile, cfgGraphicFile).equals(cfgFile) ||
                SimpleIO.lastModified(cfgFile, cdgGraphicFile).equals(cfgFile)) {

            if (SimpleIO.lastModified(cfgFile, cfgDotFile).equals(cfgFile)) {
                g.writeDotForFlowGraph(cfgDotFile);
                writtenAFile = true;
            }

            if (SimpleIO.lastModified(cfgFile, cdgDotFile).equals(cfgFile)) {
                g.writeDotForDependenceGraph(cdgDotFile);
                writtenAFile = true;
            }
        }

        if (!imageType.equals(IMAGE_TYPE_DOT)) {

            if (SimpleIO.lastModified(cfgFile, cfgGraphicFile).equals(cfgFile)) {
                g.makeGraphic(cfgDotFile, cfgGraphicFile, imageType);
                writtenAFile = true;
            }

            if (SimpleIO.lastModified(cfgFile, cdgGraphicFile).equals(cfgFile)) {
                g.makeGraphic(cdgDotFile, cdgGraphicFile, imageType);
                writtenAFile = true;
            }

            cfgDotFile.delete();
            cdgDotFile.delete();
        }

        return writtenAFile;
    }
}
