package myTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.util.*;

/**
 * Main class to execute {@link myTest}
 * <p>
 *
 * Precondition: Object methods are considered in a working status when they are used
 *
 * @version JUnit 4.13
 * @version Hamcrest: 1.3
 * @version JVM from JME CLDC 1.1
 *
 * @author Alessandro Marcassa
 */
public class TestRunner {
    private static int totalTests = 0;

    /**
     * Main. Invoke all tests
     * @param args - not used
     */
    public static void main(String[] args) {
        Result res;

        System.out.println("**Test in execution..**\n");

        System.out.println("\nTest of ListAdapter..");
        res = JUnitCore.runClasses(TestList.class);
        esitoTest(res);
        res = JUnitCore.runClasses(ListTest.class);
        esitoTest(res);
        res = JUnitCore.runClasses(TestBasaglia.class);
        esitoTest(res);

        System.out.println("\n*** All the " + totalTests + " tests have been completed +**");
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
        System.out.print("Of " + res.getRunCount() + " tests ");
        if (res.wasSuccessful())
        {
            System.out.println("all are with a positive result");
        }
        else
        {
            System.out.println("failed " + res.getFailureCount() + " tests");
            List<Failure> fails = res.getFailures();
            for (Failure fail : fails) {
                System.out.println(fail.toString());
            }
        }
    }
}