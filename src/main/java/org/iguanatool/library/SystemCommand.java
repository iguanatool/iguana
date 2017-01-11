package org.iguanatool.library;

import org.iguanatool.Config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SystemCommand {

    private static final String DISPLAY_SYSTEM_COMMANDS_PROPERTY = "display_system_commands";

    private static final boolean DISPLAY_SYSTEM_COMMANDS = Config.getInstance()
            .getBooleanProperty(DISPLAY_SYSTEM_COMMANDS_PROPERTY);

    private String command;
    private String[] commandTokens;
    private File directory;
    private int exitCode;
    private String outputStreamOutput;
    private String errorStreamOutput;
    private boolean executed;

    public SystemCommand(String command) {
        this(command, new File("."));
    }

    public SystemCommand(String command, File directory) {
        this.command = command;
        this.directory = directory;
        this.exitCode = 0;
        this.outputStreamOutput = "";
        this.errorStreamOutput = "";
        this.executed = false;
        parseCommand();
    }

    public static SystemCommand execute(String command) throws IOException, SystemCommandException {
        SystemCommand sc = new SystemCommand(command);
        sc.execute();
        return sc;
    }

    public static SystemCommand execute(String command, File directory)
            throws IOException, SystemCommandException {
        SystemCommand sc = new SystemCommand(command, directory);
        sc.execute();
        return sc;
    }

    public int getExitCode() {
        return exitCode;
    }

    public String getCommand() {
        return command;
    }

    public String[] getCommandTokens() {
        return commandTokens;
    }

    public File getDirectory() {
        return directory;
    }

    public String getOutputStreamOutput() {
        return outputStreamOutput;
    }

    public String getErrorStreamOutput() {
        return errorStreamOutput;
    }

    public boolean hasBeenExecuted() {
        return executed;
    }

    private void parseCommand() {
        List<String> tokens = tokenizeCommand();
        retrieveCommandTokens(tokens);
    }

    private List<String> tokenizeCommand() {
        List<String> tokens = new ArrayList<>();

        boolean lookingForSpace = true;
        int lastSpace = 0;

        for (int i = 0; i < command.length(); i++) {
            char currentChar = command.charAt(i);

            if (currentChar == ' ') {
                if (lookingForSpace) {
                    String substring = command.substring(lastSpace, i).trim();
                    if (!substring.equals("")) {
                        tokens.add(command.substring(lastSpace, i));
                    }
                    lastSpace = i + 1;
                }
            } else if (currentChar == '"') {
                if (!(i > 0 && command.charAt(i - 1) == '\\')) {
                    lookingForSpace = !lookingForSpace;
                }
            }
        }
        tokens.add(command.substring(lastSpace, command.length()));
        return tokens;
    }

    private void retrieveCommandTokens(List<String> tokens) {
        commandTokens = new String[tokens.size()];
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);

            // replace all quotes that are not slashed out with an empty string
            // (uses negative lookbehind)
            token = token.replaceAll("(?<!\\\\)\"", "");

            // replace all slashed out quotes with a quote
            token = token.replace("\\\"", "\"");

            commandTokens[i] = token;
        }
    }

    public void execute() throws IOException, SystemCommandException {

        if (DISPLAY_SYSTEM_COMMANDS) {
            System.out.println("Executing system command... \"" + command
                    + "\" in directory \"" + directory + "\"");
        }

        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(commandTokens, null, directory);

        StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream());
        StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream());
        errorGobbler.start();
        outputGobbler.start();

        try {
            process.waitFor();
        } catch (InterruptedException e) {
        }

        int exitCode = process.exitValue();
        executed = true;

        if (exitCode != 0) {
            throw new SystemCommandException(this);
        }
    }
}