package org.iguanatool.log;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Log {

    protected List<PrintWriter> outputs = new ArrayList<>();

    public void attachSysOut() {
        outputs.add(new PrintWriter(System.out));
    }

    public void attachFile(String fileName) {

        try {
            PrintWriter output = new PrintWriter(fileName);
            outputs.add(output);
        } catch (FileNotFoundException e) {
            throw new LogException("Could not open log file: " + fileName);
        }
    }

    public void print(double toPrint) {
        for (PrintWriter output : outputs) {
            output.print(toPrint);
        }
    }

    public void print(double[] toPrint) {
        boolean first = true;
        print("[");
        for (double d : toPrint) {
            if (!first) {
                print(", ");
            } else {
                first = false;
            }
            print(d);
        }
        print("]");
    }

    public void print(String toPrint) {
        for (PrintWriter output : outputs) {
            output.print(toPrint);
        }
    }

    public void println() {
        for (PrintWriter output : outputs) {
            output.println();
        }
    }

    public void println(String toPrint) {
        for (PrintWriter output : outputs) {
            output.println(toPrint);
        }
    }

    public void flush() {
        for (PrintWriter output : outputs) {
            output.flush();
        }
    }

    public void close() {
        for (PrintWriter output : outputs) {
            output.close();
        }
    }
}
