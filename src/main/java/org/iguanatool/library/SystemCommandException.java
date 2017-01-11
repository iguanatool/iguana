package org.iguanatool.library;

@SuppressWarnings("serial")
public class SystemCommandException extends Exception {

    public SystemCommandException(SystemCommand sc) {
        super("Exit code " + sc.getExitCode() + " returned whilst executing command \"" + sc.getCommand() + "\"" +
                (sc.getDirectory() != null ? " in directory \"" + sc.getDirectory() + "\"" : "") +
                (!sc.getOutputStreamOutput().equals("") ? ". Output stream output: \"" + sc.getOutputStreamOutput() + "\"" : "") +
                (!sc.getErrorStreamOutput().equals("") ? ". Error stream output: \"" + sc.getErrorStreamOutput() + "\"" : ""));
    }
}
