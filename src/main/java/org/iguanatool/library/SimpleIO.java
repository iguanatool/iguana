package org.iguanatool.library;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleIO {

    public static String readText(String fileName) throws IOException {
        return readText(new File(fileName));
    }

    public static String readText(File f) throws IOException {
        return readText(new FileReader(f));
    }

    public static String readText(InputStream is) throws IOException {
        return readText(new InputStreamReader(is));
    }

    public static String readText(Reader r) throws IOException {
        BufferedReader br = new BufferedReader(r);
        String contents = "";
        String line = br.readLine();
        while (line != null) {
            contents += line + "\n";
            line = br.readLine();
        }
        br.close();
        return contents;
    }

    public static List<String> readTextLines(String fileName) throws IOException {
        return readTextLines(fileName, false);
    }

    public static List<String> readTextLines(String fileName, boolean trimLines) throws IOException {
        return readTextLines(new File(fileName), trimLines);
    }

    public static List<String> readTextLines(File f) throws IOException {
        return readTextLines(f, false);
    }

    public static List<String> readTextLines(File f, boolean trimLines) throws IOException {
        return readTextLines(new FileReader(f), trimLines);
    }

    public static List<String> readTextLines(InputStream is) throws IOException {
        return readTextLines(is, false);
    }

    public static List<String> readTextLines(InputStream is, boolean trimLines) throws IOException {
        return readTextLines(new InputStreamReader(is), trimLines);
    }

    public static List<String> readTextLines(Reader r) throws IOException {
        return readTextLines(r, false);
    }

    public static List<String> readTextLines(Reader r, boolean trimLines) throws IOException {
        List<String> lines = new ArrayList<String>();
        BufferedReader br = new BufferedReader(r);
        String line = br.readLine();
        while (line != null) {
            if (trimLines) {
                line = line.trim();
                if (!line.equals("")) {
                    lines.add(line);
                }
            } else {
                lines.add(line);
            }
            line = br.readLine();
        }
        br.close();
        return Collections.unmodifiableList(lines);
    }

    public static boolean writeText(String fileName, String toWrite, boolean overwrite) throws IOException {
        return writeText(new File(fileName), toWrite, overwrite);
    }

    public static boolean writeText(File file, String toWrite, boolean overwrite) throws IOException {
        if (!overwrite && file.exists()) {
            return false;
        }
        PrintWriter pw = new PrintWriter(new FileWriter(file));
        pw.print(toWrite);
        pw.close();
        return true;
    }

    public static boolean writeText(String fileName, List<String> lines, boolean overwrite) throws IOException {
        return writeText(new File(fileName), lines, overwrite);
    }

    public static boolean writeText(File file, List<String> lines, boolean overwrite) throws IOException {
        if (!overwrite && file.exists()) {
            return false;
        }
        PrintWriter pw = new PrintWriter(file);
        for (String line : lines) {
            pw.println(line);
        }
        pw.close();
        return true;
    }


    public static <T> T unserialize(String fileName) throws IOException, ClassNotFoundException {
        return unserialize(new File(fileName));
    }

    @SuppressWarnings("unchecked")
    public static <T> T unserialize(File f) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        T unserialized = (T) ois.readObject();
        ois.close();
        return unserialized;
    }

    public static <T> void serialize(T toSerialize, String fileName) throws IOException {
        serialize(toSerialize, new File(fileName));
    }

    public static <T> void serialize(T toSerialize, File f) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(toSerialize);
        oos.close();
    }

    public static void ensureDirectoryExists(File directory) throws IOException {
        if (!directory.exists() && !directory.mkdir()) {
            throw new IOException("Could not make directory: " + directory);
        }
    }

    public static File lastModified(File f1, File f2) {
        if (f1.lastModified() > f2.lastModified()) {
            return f1;
        } else {
            return f2;
        }
    }

    public static void recursiveDelete(File file) throws IOException {
        if (file.isDirectory()) {
            for (File entry : file.listFiles()) {
                recursiveDelete(entry);
            }
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete file: " + file);
        }
    }

    public static void copyFile(String sourceFileName, String targetFileName) throws IOException {
        copyFile(new File(sourceFileName), new File(targetFileName));
    }

    public static void copyFile(File source, File target) throws IOException {
        FileChannel inChannel = new
                FileInputStream(source).getChannel();
        FileChannel outChannel = new
                FileOutputStream(target).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            throw e;
        } finally {
            if (inChannel != null) inChannel.close();
            if (outChannel != null) outChannel.close();
        }
    }
}
