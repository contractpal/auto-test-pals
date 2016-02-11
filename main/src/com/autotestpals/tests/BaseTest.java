package com.autotestpals.tests;

import com.autotestpals.Driver;
import junit.framework.TestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * User: david.martineau
 * Date: 7/28/15
 * Time: 2:23 PM
 */
public abstract class BaseTest extends TestCase
{


    public static void assertTrue(boolean expression)
    {
        if (!expression)
        {
            WebDriver driver = Driver.getInstance().getDriver();
            String body=driver.findElement(By.tagName("body")).getText();
            TestCase.assertTrue(body,expression);
        }
        else
        {
            TestCase.assertTrue(expression);
        }
    }


    public WebDriver auth(final String test,boolean ent)
    {
        String url = Driver.getInstance().getUrl();
        String user=Driver.getInstance().getBaseUserName();
        String pass=Driver.getInstance().getBasePassword();

        WebDriver driver = Driver.getInstance().getDriver();

        // navigate to the test location
        driver.get(url+"?type="+test);

        // click the element with the ID of our test
        WebElement element;
        try
        {
            element=driver.findElement(By.id(test));

            // navigate to the login page
            element.click();

        }
        catch(Throwable t)
        {
            throw new RuntimeException("Page Source: "+driver.getPageSource(),t);
        }

        // Here's the explicit wait.
        WebDriverWait wdw = new WebDriverWait(driver, 10);
        ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return d.findElement(By.id("cp-login")).findElement(By.name("username"))!=null;
            }
        };
        wdw.until(condition); // Won't get past here til timeout or element is found

        WebElement username = driver.findElement(By.id("cp-login")).findElement(By.name("username"));
        username.sendKeys(user);

        WebElement password = driver.findElement(By.id("cp-login")).findElement(By.name("password"));
        password.sendKeys(pass);

        driver.findElement(By.id("sendBtn")).click();

        wdw = new WebDriverWait(driver, 10);
        condition = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                if (d.getTitle().equals(test))
                {
                    return true;
                }
                if (d.findElement(By.id("cp-login")).findElement(By.name("profileId"))!=null)
                {
                    return true;
                }
                return false;
            }
        };
        wdw.until(condition);

        String title=driver.getTitle();
        if (title.equals(test))
        {
            return driver;
        }

        // then we're dealing with a user that has profiles.  we'll assume the first profile on the list is the
        // personal and the second is the enterprise
        if (driver.findElement(By.id("cp-login"))!=null)
        {
            if (driver.findElement(By.id("cp-login")).findElement(By.name("profileId"))!=null)
            {
                if (ent)
                {
                    driver.findElement(By.id("cp-login")).findElements(By.name("profileId")).get(1).click();
                }
                else
                {
                    driver.findElement(By.id("cp-login")).findElements(By.name("profileId")).get(0).click();
                }
            }
            driver.findElement(By.xpath("//input[@value='Continue']")).click();

            wdw = new WebDriverWait(driver, 10);
            condition = new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver d) {
                    return d.getTitle().equals(test);
                }
            };
            wdw.until(condition);

        }

        return driver;

    }

}
