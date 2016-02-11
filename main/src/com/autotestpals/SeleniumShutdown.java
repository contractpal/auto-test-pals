package com.autotestpals;

import junit.framework.TestCase;
import org.openqa.selenium.WebDriver;

/**
 * User: david.martineau
 * Date: 7/28/15
 * Time: 1:43 PM
 */
public class SeleniumShutdown extends TestCase
{

    public void testClose()
    {
        Driver.getInstance().removeDriver();
    }
}

