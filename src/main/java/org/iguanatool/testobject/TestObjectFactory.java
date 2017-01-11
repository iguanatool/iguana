package org.iguanatool.testobject;

import java.util.ArrayList;
import java.util.List;

public class TestObjectFactory {

    public static List<TestObject> instantiate(String name) {
        int separatorPos = name.indexOf(".");

        if (separatorPos != -1) {
            String caseStudyName = name.substring(0, separatorPos);
            String testObjectName = name.substring(separatorPos + 1, name.length());
            TestObject testObject = instantiateTestObject(caseStudyName, testObjectName);

            List<TestObject> testObjects = new ArrayList<TestObject>();
            testObjects.add(testObject);
            return testObjects;
        }

        return instantiateTestObjects(name);
    }


    private static TestObject instantiateTestObject(String caseStudyName, String testObjectName) {
        CaseStudy caseStudy = new CaseStudy(caseStudyName);
        JavaWrapper wrapper = new JavaWrapper(caseStudy, testObjectName);
        String testObjectClass = wrapper.getTestObjectQualifiedClassName();

        try {
            return (TestObject) Class.forName(testObjectClass).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not instantiate test object " + testObjectClass + " - check name");
        }
    }

    private static List<TestObject> instantiateTestObjects(String caseStudyName) {
        try {
            CaseStudy caseStudy = new CaseStudy(caseStudyName);
            caseStudy.loadTestObjectsFile();
            List<String> testObjectNames = caseStudy.getTestObjectNames();
            List<TestObject> testObjects = new ArrayList<TestObject>();

            for (String testObjectName : testObjectNames) {
                testObjects.add(instantiateTestObject(caseStudyName, testObjectName));
            }

            return testObjects;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
