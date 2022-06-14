package myTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.util.*;

/**
 * Main class to execute {@link ListAdapterTest}
 * <p>
 *
 * <strong>Preconditions</strong>: Object methods are considered in a working status when they are used
 *
 * @version JUnit 4.13
 * @version Hamcrest: 1.3
 * @version JVM from JME CLDC 1.1
 *
 * @author Andrea Stocco
 */
public class TestRunner {
    private static int totalTests = 0;

    /**
     * Main. Invoke all tests
     * @param args not used
     */
    public static void main(String[] args) {
        Result res;

        System.out.println("*** Tests in execution... ***");
        System.out.println();
        res = JUnitCore.runClasses(ListAdapterTest.class);
        esitoTest(res);
    }

    /**
     * Print the test result for every suite case
     * <p>
     *
     * For every suite is indicated how many test of the relative suite have been executed and how many failed
     * @param res - The result of the invocation of the test class
     */
    private static void esitoTest(Result res)
    {
        totalTests += res.getRunCount();
        System.out.println();
        System.out.println("*** All the tests have been completed ***");
        System.out.println(totalTests - res.getFailureCount() + "/" + totalTests + " successful, " + res.getFailureCount() + "/" + totalTests + " failed");
        if (!res.wasSuccessful()) {
            List<Failure> fails = res.getFailures();
            for (Failure fail : fails) {
                System.out.println(fail.toString());
            }
        }
        totalTests = 0;
    }
}