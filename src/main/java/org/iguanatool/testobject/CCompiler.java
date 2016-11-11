package org.iguanatool.testobject;

import org.iguanatool.Config;
import org.iguanatool.library.TextFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CCompiler {
	
	private static final String C_COMPILERS_DIR  			 	 = "ccompilers"; 
	private static final String C_COMPILER_ID_PROPERTY 		   	 = "c_compiler_id";
	private static final String C_COMPILER_CLEANUP_EXTS_PROPERTY = "c_compiler_cleanup_extensions";
	
	private File getPath() {
		return new File(Config.getInstance().getIguanaPath() + "/" + C_COMPILERS_DIR);
	}		
	
	private File getTemplateFile(String compiler) {
		return new File(getPath() + "/" + compiler);
	}	
	
	public String getID() {
		String compiler = Config.getInstance().getProperty(C_COMPILER_ID_PROPERTY);
		
		if (compiler == null) {
			String os = Config.getInstance().getOS();
			int delim = os.indexOf(" ");
			if (delim == -1) delim = os.length();
			compiler = os.substring(0, delim).toLowerCase() + "-default";
		}
		
		return compiler;
	}
	
	public List<String> getCleanupExtensions() {
		List<String> extensionsList = new ArrayList<String>();
		String cleanup = Config.getInstance().getProperty(C_COMPILER_CLEANUP_EXTS_PROPERTY);
		
		if (cleanup != null) {
			String[] extensions = cleanup.split(" ");
		
			for (String extension: extensions) {
				extension = extension.trim();
				if (!extension.equals("")) {
					extensionsList.add(extension);
				}
			}
		}
		return extensionsList;
	}
	
	public List<String> getCommands(Map<String, String> locations) throws IOException {
		File templateFile = getTemplateFile(getID());
		
		TextFile t = new TextFile(templateFile);
    	String commandsString = t.searchAndReplace(locations);	
		String[] rawCommands = commandsString.split("\n");
		List<String> commands = new ArrayList<String>();
		
		for (String command: rawCommands) {
			if (!command.startsWith("#") && !command.equals("")) {
	    		commands.add(command);
	    	}			
		}
		
	    return commands;
	}
}
