package org.iguanatool;

import org.iguanatool.library.SystemCommandException;
import org.iguanatool.testobject.CaseStudy;
import org.iguanatool.testobject.JNILibrary;
import org.iguanatool.testobject.JavaWrapper;
import org.iguanatool.testobject.cparser.ParseException;

import java.io.IOException;
import java.util.List;

public class Assimilate {
		
	public void assimilate(CaseStudy caseStudy) throws IOException, ParseException, SystemCommandException {
		
		printCaseStudy(caseStudy);
		caseStudy.parse();
		caseStudy.createCFGs();
		
		List<String> testObjectNames = caseStudy.getTestObjectNames();
		
		for (String testObjectName: testObjectNames) {
			JavaWrapper javaWrapper = new JavaWrapper(caseStudy, testObjectName);
			JNILibrary  jniLibrary  = new JNILibrary(caseStudy, testObjectName);
			
			printTestObject(testObjectName);

			printAction("creating input specification stub");
			boolean createdInputSpecification = javaWrapper.createInputSpecification();
			printStatus(createdInputSpecification);
			
			printAction("creating JNI perform_call stub");
			boolean createdPerformCallFile = jniLibrary.makePerformCallFile(); 
			printStatus(createdPerformCallFile);				
			
			if (!createdInputSpecification && !createdPerformCallFile) {
				printAction("compiling input specification");
				printStatus(javaWrapper.compileInputSpecification());
				
				printAction("creating test object");
				printStatus(javaWrapper.createTestObject());
				
				printAction("compiling test object");
				printStatus(javaWrapper.compileTestObject());
				
				printAction("instrumenting");
				printStatus(caseStudy.instrument(testObjectName));
								
				printAction("creating JNI header file");
				printStatus(jniLibrary.makeLibraryHeaderFile());
				
				printAction("creating JNI source file"); 	      
				printStatus(jniLibrary.makeLibrarySourceFile());
				
				printAction("compiling shared library");
				printStatus(jniLibrary.compile());
				
				printAction("serializing control graph");
				printStatus(caseStudy.serializeCFG(testObjectName));
				
				printAction("creating control graph images");
				printStatus(caseStudy.createControlGraphImages(testObjectName));
			}
		}
	}
	
	private void printCaseStudy(CaseStudy caseStudy) {
		System.out.println("Case study: " + caseStudy.getName());
	}
	
	private void printTestObject(String name) {
		System.out.println("Test object: " + name);
	}

	private void printAction(String action) {
		System.out.print("... " + action + "... ");
	}
	
	private void printStatus(boolean success) { 
		if (success) System.out.println("SUCCESS");  
		else System.out.println("ALREADY UP TO DATE");				
	}	
	
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Usage: java "+Assimilate.class+" case_study_name");
			System.exit(1);
		}

		String caseStudyName = args[0];
		
		new Config(args);
		new Assimilate().assimilate(new CaseStudy(caseStudyName));
	}
}
