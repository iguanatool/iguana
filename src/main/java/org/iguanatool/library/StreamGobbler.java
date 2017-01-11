package org.iguanatool.library;

import java.io.*;

public class StreamGobbler extends Thread {
    protected InputStream is;
    protected OutputStream os;

    public StreamGobbler(InputStream is) {
        this(is, null);
    }

    public StreamGobbler(InputStream is, OutputStream redirect) {
        this.is = is;
        this.os = redirect;
    }

    public void run() {
        try {
            PrintWriter pw = null;
            if (os != null)
                pw = new PrintWriter(os);

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                if (pw != null) {
                    pw.println(line);
                }
                System.out.println(line);
            }
            if (pw != null) {
                pw.flush();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
