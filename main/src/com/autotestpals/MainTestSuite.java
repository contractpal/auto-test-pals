package com.autotestpals;

import com.autotestpals.tests.Test1;
import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;


// see https://selenium.googlecode.com/svn/trunk/docs/api/java/index.html?org/openqa/selenium/WebElement.html
public class MainTestSuite implements Test
{
    private Test test;

    public MainTestSuite()
    {
        try
        {
            test = MainTestSuite.suite();
        }
        catch (Exception e)
        {
            // nop
            e.printStackTrace();
        }
        finally
        {
            test.countTestCases();
        }
    }

    public static Test suite() throws Exception
    {
        TestSuite suite = new TestSuite();

        suite.addTestSuite(Test1.class);

        // add other tests here..

        // this one must always be last, it does the cleanup and shuts down Firefox
        suite.addTestSuite(SeleniumShutdown.class);

        return suite;
    }

    public static void main(String[] args)
    {
        try
        {
            Test test = suite();
            junit.textui.TestRunner.run(test);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public int countTestCases()
    {
        return test.countTestCases();
    }

    @Override
    public void run(TestResult result)
    {
        test.run(result);
    }


}
