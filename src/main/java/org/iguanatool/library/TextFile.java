package org.iguanatool.library;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

public class TextFile {

    private String contents;

    public TextFile(File templateFile) throws IOException {
        contents = SimpleIO.readText(templateFile);
    }

    public static void searchAndReplace(File file, Map<String, String> searchAndReplace) throws IOException {
        searchAndReplace(file, file, searchAndReplace);
    }

    public static void searchAndReplace(File templateFile, File targetFile, Map<String, String> searchAndReplace) throws IOException {
        TextFile t = new TextFile(templateFile);
        t.searchAndReplace(searchAndReplace);
        t.save(targetFile);
    }

    public String getContents() {
        return contents;
    }

    public String searchAndReplace(Map<String, String> searchAndReplace) throws IOException {
        for (Entry<String, String> replacement : searchAndReplace.entrySet()) {
            String search = replacement.getKey();
            String replace = replacement.getValue();
            contents = contents.replace(search, replace);
        }
        return contents;
    }

    public void save(File targetFile) throws IOException {
        PrintWriter pw = new PrintWriter(targetFile);
        pw.print(contents);
        pw.close();
    }
}
