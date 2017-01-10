package {PACKAGE_NAME}; 

import org.iguanatool.testobject.CaseStudy;
import org.iguanatool.testobject.InputSpecification;
import org.iguanatool.testobject.JNILibrary;
import org.iguanatool.testobject.TestObject;

public class {TEST_OBJECT_NAME} extends TestObject { 

	private static final CaseStudy CASE_STUDY    = new CaseStudy("{CASE_STUDY_NAME}");
	private static final String TEST_OBJECT_NAME = "{TEST_OBJECT_NAME}";
	private static final int TEST_OBJECT_ID      = {TEST_OBJECT_ID};
	
	static {
		System.load(new JNILibrary(CASE_STUDY, TEST_OBJECT_NAME).getLibraryFile().getPath());
	}

	protected void loadCFG() {
		loadCFG(CASE_STUDY.getCFGFile(TEST_OBJECT_NAME));
	}
	
	protected InputSpecification instantiateInputSpecification() {
		return new {INPUT_SPECIFICATION_NAME}();
	}
	
	public int getTestObjectID() {
		return TEST_OBJECT_ID;
	}
	
    protected native void call(double[] args);	
}
