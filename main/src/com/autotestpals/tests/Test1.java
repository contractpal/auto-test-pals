package com.autotestpals.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * User: david.martineau
 * Date: 2/11/16
 * Time: 3:12 PM
 */
public class Test1 extends BaseTest
{
    public void testRender()
    {
        WebDriver driver = auth("Render",false);

        // at this point we are sitting on the test page, so lets click the button to run the test.

        driver.findElement(By.id("index")).click();

        // success is at least one "true" and no "false".
        assertTrue(driver.findElements(By.className("true")).size()>=1);
        assertTrue(driver.findElements(By.className("false")).size()==0);
    }

}
