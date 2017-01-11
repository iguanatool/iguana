package org.iguanatool.testobject;

import org.iguanatool.Config;
import org.iguanatool.library.SimpleIO;
import org.iguanatool.library.SystemCommand;
import org.iguanatool.library.SystemCommandException;
import org.iguanatool.library.TextFile;
import org.iguanatool.testobject.cparser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JNILibrary {

	private static final String C_SRC_EXTENSION            = ".c";
	private static final String C_HEADER_EXTENSION         = ".h";
	private static final String HANDLE_INCLUDE_DIR    	   = "include"; 
    private static final String LIBRARY_DIR			       = "lib";	
	private static final String LIBRARY_SOURCE_DIR		   = LIBRARY_DIR + "/src";		
	private static final String LIBRARY_TEMPLATE_FILE      = "lib" + C_SRC_EXTENSION;
	private static final String PERFORM_CALL_DIR		   = "call";
	private static final String PERFORM_CALL_TEMPLATE_FILE = "perform_call" + C_SRC_EXTENSION;
	private static final String TEST_OBJECT_HANDLE    	   = "CTestObjectHandle";

	private CaseStudy caseStudy;
	private String testObjectName;
	
	public JNILibrary(CaseStudy caseStudy, String testObjectName) {
		this.caseStudy = caseStudy;
		this.testObjectName = testObjectName;
	}	
	
	private File getHandleIncludePath() {
		return new File(Config.getInstance().getIguanaPath() + "/" + HANDLE_INCLUDE_DIR);
	}
	
	private File getHandleSourceFile() {
		return new File(getHandleIncludePath() + "/" + TEST_OBJECT_HANDLE + C_SRC_EXTENSION);
	}
	
	public File getLibraryFile() {
		CCompiler compiler = new CCompiler();
		return new File(getLibraryPath() + "/" + testObjectName + "." + compiler.getID());	
	}
	
	private File getLibraryPath() {
		return new File(caseStudy.getCaseStudyPath() + "/" + LIBRARY_DIR);
	}
	
	private File getLibraryHeaderFile() {
		return new File(getLibrarySourcePath() + "/" + testObjectName + C_HEADER_EXTENSION);
	}
	
	private File getLibrarySourcePath() {
		return new File(caseStudy.getCaseStudyPath() + "/" + LIBRARY_SOURCE_DIR);	
	}
	
	private File getPerformCallPath() {
		return new File(caseStudy.getCaseStudyPath() + "/" + PERFORM_CALL_DIR);
	}
	
	private File getPerformCallSrcFile() {
		return new File(getPerformCallPath() + "/" + testObjectName + C_SRC_EXTENSION);
	}
	
	private File getLibrarySourceFile() {
		return new File(getLibrarySourcePath() + "/" + testObjectName + C_SRC_EXTENSION);
	}
	
	private File getPerformCallTemplateFile() {
		return new File(Config.getInstance().getTemplatesPath() + "/" + PERFORM_CALL_TEMPLATE_FILE);
	}
	
	private File getLibraryTemplateFile() {
		return new File(Config.getInstance().getTemplatesPath() + "/" + LIBRARY_TEMPLATE_FILE);
	}
    
    private String getJNIFunctionName() {
    	JavaWrapper javaWrapper = new JavaWrapper(caseStudy, testObjectName);
    	String packageName = javaWrapper.getTestObjectsPackageName().replace(".", "_");
    	String safeClassName = testObjectName.replace("_", "_1");
    	return "Java_" + packageName + "_" + safeClassName + "_call";
    } 	
			
	public boolean makeLibraryHeaderFile() throws IOException, SystemCommandException {
        File headerFile = getLibraryHeaderFile();
        if (headerFile.exists()) {
        	return false;
        }
    	
        SimpleIO.ensureDirectoryExists(getLibraryPath());
		SimpleIO.ensureDirectoryExists(getLibrarySourcePath());
    	        	
    	JavaWrapper javaWrapper = new JavaWrapper(caseStudy, testObjectName);
        String command = "javah -o \"" + headerFile + "\" " + javaWrapper.getTestObjectQualifiedClassName();
        SystemCommand.execute(command);
        return true;
    }
	
	public boolean makeLibrarySourceFile() throws IOException, ParseException {
		File targetFile = getLibrarySourceFile();
		File performCallFile = getPerformCallSrcFile();
		
		boolean performCallFileOlder = SimpleIO.lastModified(performCallFile, targetFile).equals(targetFile);
		boolean instrumentedCodeFileOlder = SimpleIO.lastModified(caseStudy.getInstrumentedCodeFile(testObjectName), targetFile).equals(targetFile); 
		
		if (performCallFileOlder && instrumentedCodeFileOlder) {
			return false;
		}		
		
		File templateFile = getLibraryTemplateFile();
		SimpleIO.ensureDirectoryExists(getLibraryPath());
		SimpleIO.ensureDirectoryExists(getLibrarySourcePath());
		
    	Map<String, String> searchAndReplace = new HashMap<>();
    	searchAndReplace.put("{CASE_STUDY_NAME}",   caseStudy.getName());
    	searchAndReplace.put("{INSTRUMENTED_CODE}", caseStudy.getInstrumentedCode());
    	searchAndReplace.put("{JNI_FUNCTION_NAME}", getJNIFunctionName());
    	searchAndReplace.put("{PERFORM_CALL_CODE}", SimpleIO.readText(getPerformCallSrcFile()));
    	searchAndReplace.put("{TEST_OBJECT_NAME}",  testObjectName);		
		
		TextFile.searchAndReplace(templateFile, targetFile, searchAndReplace);
		return true; 	    	 			
	}	

	public boolean makePerformCallFile() throws IOException {
		File targetFile = getPerformCallSrcFile();
		if (targetFile.exists()) {
			return false;
		}
		
		File templateFile = getPerformCallTemplateFile();
		SimpleIO.ensureDirectoryExists(getPerformCallPath());
		
    	Map<String, String> searchAndReplace = new HashMap<>();
    	searchAndReplace.put("{TEST_OBJECT_NAME}", testObjectName);  	
    	
		TextFile.searchAndReplace(templateFile, targetFile, searchAndReplace);
		return true; 	
    }   
	
	public boolean compile() throws IOException, SystemCommandException {
		File libraryFile =  getLibraryFile();
		File librarySourceFile = getLibrarySourceFile();
		
		boolean librarySourceFileOlder = SimpleIO.lastModified(libraryFile, librarySourceFile).equals(libraryFile); 
		if (librarySourceFileOlder) {
			return false;
		}
		
		SimpleIO.ensureDirectoryExists(getLibraryPath());
		
		Map<String, String> locations = new HashMap<>();
		
		// creating file objects and invoking getPath will convert paths so that they have OS-specific separators
        locations.put("{CASE_STUDY_PATH}",     caseStudy.getCaseStudyPath().toString());
		locations.put("{JAVA_HOME}", 		   Config.getInstance().getJavaPath().toString());
		locations.put("{HANDLE_INCLUDE_PATH}", getHandleIncludePath().getPath());
		locations.put("{HANDLE_SRC_FILE}",     getHandleSourceFile().getPath());
        locations.put("{LIB_FILE}", 		   libraryFile.getPath());
		locations.put("{LIB_PATH}", 		   getLibraryPath().getPath());
		locations.put("{LIB_SRC_FILE}",        librarySourceFile.getPath());

		CCompiler compiler = new CCompiler();
		List<String> compilationCommands = compiler.getCommands(locations);
		
		// compile
	    for (String command: compilationCommands) {		
	    	SystemCommand.execute(command, getLibraryPath());
	    }		
	    
	    // cleanup
	    List<String> cleanupExtensions = compiler.getCleanupExtensions();
        File[] files = getLibraryPath().listFiles();
        for (File f: files) {
            for (String extension: cleanupExtensions) {
            	if (f.getName().endsWith("." + extension)) {
            		f.delete();
            	}
            }
        }
	    return true;
	}	
}
