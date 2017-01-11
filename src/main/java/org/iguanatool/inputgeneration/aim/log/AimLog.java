package org.iguanatool.inputgeneration.aim.log;

import org.iguanatool.inputgeneration.inputvector.InputVector;
import org.iguanatool.log.Log;
import org.iguanatool.search.SearchResult;
import org.iguanatool.testobject.TestObject;

/**
 * User: phil
 * Date: 12-Sep-2006
 * Time: 10:47:51
 */
public class AimLog extends Log {

    private TestObject testObject;

    public void logStart(TestObject testObject) {
        this.testObject = testObject;
        println("Test object: " + testObject.getClass().getName());
        flush();
    }

    public void logAim(String name, SearchResult searchResult) {
        boolean success = searchResult.wasSuccess();
        int numEvaluations = searchResult.getNumEvaluations();

        print(name + "\t");
        print((success ? "OK  " : "FAIL") + "\t");
        print(numEvaluations + "\t");

        if (success) {
            double[] testData = ((InputVector) searchResult.getBestSolution()).getInputValues();
            String formattedTestData = testObject.getInputSpecification().formatInput(testData);
            print(formattedTestData);
        }

        println();
        flush();
    }
}
