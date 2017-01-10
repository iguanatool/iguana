package org.iguanatool.testobject;

import org.iguanatool.Config;
import org.iguanatool.library.SimpleIO;
import org.iguanatool.library.SystemCommand;
import org.iguanatool.library.SystemCommandException;
import org.iguanatool.library.TextFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JavaWrapper {	
	
	private static final String PACKAGE_DIR 					  =	"org/iguanatool/casestudies";
	
	private static final String TEST_OBJECTS_PACKAGE			  = "testobjects";
	private static final String INPUT_SPECIFICATIONS_PACKAGE	  = "inputspecifications";	
	
	private static final String INPUT_SPECIFICATION_TEMPLATE_FILE = "InputSpecification.java";
	private static final String WRAPPER_TEMPLATE_FILE 			  = "TestObject.java";
	
	private CaseStudy caseStudy;
	private String testObjectName;
	
	public JavaWrapper(CaseStudy caseStudy, String testObjectName) {
		this.caseStudy = caseStudy;
		this.testObjectName = testObjectName;
	}
	
	private String getWrapperPackageDir() {
		return PACKAGE_DIR + "/" + caseStudy.getName();
	}
	
	private String getTestObjectsPackageDir() {
		return getWrapperPackageDir() + "/" + TEST_OBJECTS_PACKAGE; 
	}
	
	public String getTestObjectsPackageName() {
		return getTestObjectsPackageDir().replace("/", ".");
	}	
	
	private String getInputSpecificationsPackageDir() {
		return getWrapperPackageDir() + "/" + INPUT_SPECIFICATIONS_PACKAGE;
	}
	
	private String getInputSpecificationsPackageName() {
		return getInputSpecificationsPackageDir().replace("/", ".");
	}	
	
	private File getWrapperSourcePath() {
		return new File(Config.getInstance().getIguanaSourcePath() + "/" + getWrapperPackageDir());
	}

	private File getWrapperClassesPath() {
		return new File(Config.getInstance().getIguanaClassesPath() + "/" + getWrapperPackageDir());
	}	
	
    private File getTestObjectsSourcePath() {
    	return new File(Config.getInstance().getIguanaSourcePath() + "/" + getTestObjectsPackageDir());
    }   	
	
    private File getTestObjectsClassPath() {
    	return new File(Config.getInstance().getIguanaClassesPath() + "/" + getTestObjectsPackageDir());
    }
   
    private File getInputSpecificationsSourcePath() {
    	return new File(Config.getInstance().getIguanaSourcePath() + "/" + getInputSpecificationsPackageDir());
    }   	
	
    private File getInputSpecificationsClassPath() {
    	return new File(Config.getInstance().getIguanaClassesPath() + "/" + getInputSpecificationsPackageDir());
    }
    
	private File getTestObjectSourceFile() {
		return new File(getTestObjectsSourcePath() + "/" + testObjectName + ".java");
	}
	
    private File getTestObjectClassFile() {
    	return new File(getTestObjectsClassPath() + "/" + testObjectName + ".class");
    }	
	
	private File getInputSpecificationSourceFile() {
		return new File(getInputSpecificationsSourcePath() + "/" + testObjectName + ".java");
	}

    private File getInputSpecificationClassFile() {
    	return new File(getInputSpecificationsClassPath() + "/" + testObjectName + ".class");
    }
    
	private File getTestObjectTemplateFile() {
		return new File(Config.getInstance().getTemplatesPath() + "/" + WRAPPER_TEMPLATE_FILE);
	}
	
	private File getInputSpecificationTemplateFile() {
		return new File(Config.getInstance().getTemplatesPath() + "/" + INPUT_SPECIFICATION_TEMPLATE_FILE);
	}	

	private String getJavaCompilerCommand(File toCompile) {
		return "javac -d \"" + Config.getInstance().getIguanaClassesPath() + "\" \"" + toCompile + "\"";
	}
	
	public String getTestObjectQualifiedClassName() {
    	return getTestObjectsPackageName() + "." + testObjectName;
    }	
	
	private String getInputSpecificationQualifiedClassName() {
		return getInputSpecificationsPackageName() + "." + testObjectName;	
	}
	
	private String getTestObjectIDAsString() {
		return "" + caseStudy.getTestObjectID(testObjectName);
	}
	
	private void ensureWrapperDirectoriesExist() throws IOException {
		SimpleIO.ensureDirectoryExists(getWrapperSourcePath());
		SimpleIO.ensureDirectoryExists(getWrapperClassesPath());
	}		
	
	public boolean createTestObject() throws IOException {
		ensureWrapperDirectoriesExist();
		SimpleIO.ensureDirectoryExists(getTestObjectsSourcePath());
		SimpleIO.ensureDirectoryExists(getTestObjectsClassPath());
		
		File templateFile = getTestObjectTemplateFile();
		File targetFile = getTestObjectSourceFile();
		
		if (targetFile.exists()) {
			return false;
		}
		 
		Map<String, String> searchAndReplace = new HashMap<String, String>();		
		searchAndReplace.put("{PACKAGE_NAME}",     		   getTestObjectsPackageName());
		searchAndReplace.put("{CASE_STUDY_NAME}",  		   caseStudy.getName());		
		searchAndReplace.put("{TEST_OBJECT_NAME}", 		   testObjectName);
		searchAndReplace.put("{TEST_OBJECT_ID}", 		   getTestObjectIDAsString());
		searchAndReplace.put("{INPUT_SPECIFICATION_NAME}", getInputSpecificationQualifiedClassName());

		TextFile.searchAndReplace(templateFile, targetFile, searchAndReplace);
		return true;
	}	

	public boolean createInputSpecification() throws IOException {
		File targetFile = getInputSpecificationSourceFile();
		if (targetFile.exists()) {
			return false;
		}

		File templateFile = getInputSpecificationTemplateFile();
		ensureWrapperDirectoriesExist();		
		SimpleIO.ensureDirectoryExists(getInputSpecificationsSourcePath());
		SimpleIO.ensureDirectoryExists(getInputSpecificationsClassPath());		
		
		Map<String, String> searchAndReplace = new HashMap<String, String>();		
		
		searchAndReplace.put("{PACKAGE_NAME}",     		   getInputSpecificationsPackageName());
		searchAndReplace.put("{INPUT_SPECIFICATION_NAME}", testObjectName);

		TextFile.searchAndReplace(templateFile, targetFile, searchAndReplace);
		return true;
	}

	public boolean compileTestObject() throws IOException, SystemCommandException {
		File testObjectSourceFile = getTestObjectSourceFile();
		File testObjectClassFile = getTestObjectClassFile();

		if (SimpleIO.lastModified(testObjectSourceFile, testObjectClassFile).equals(testObjectClassFile)) {
			return false;
		}

        String command = getJavaCompilerCommand(testObjectSourceFile);
        SystemCommand.execute(command);
        return true;
	}

	public boolean compileInputSpecification() throws IOException, SystemCommandException {
		File inputSpecificationSourceFile = getInputSpecificationSourceFile();
		File inputSpecificationClassFile = getInputSpecificationClassFile();

		if (SimpleIO.lastModified(inputSpecificationSourceFile, inputSpecificationClassFile).equals(inputSpecificationClassFile)) {
			return false;
		}

        String command = getJavaCompilerCommand(inputSpecificationSourceFile);
        SystemCommand.execute(command);
        return true;
	}
}
