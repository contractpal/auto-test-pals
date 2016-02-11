package com.autotestpals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 * User: david.martineau
 * Date: 7/28/15
 * Time: 2:00 PM
 */
public class Driver
{
    private final ThreadLocal<WebDriver> thread = new ThreadLocal<WebDriver>();

    private static Driver ourInstance = new Driver();

    public static Driver getInstance()
    {
        return ourInstance;
    }

    private Driver()
    {
    }

    public String getUrl()
    {
        return System.getProperty("testUrl");
    }

    public String getBaseUserName()
    {
        return System.getProperty("username");
    }

    public String getBasePassword()
    {
        return System.getProperty("password");
    }

    public String getEntUserName()
    {
        return System.getProperty("entUsername");
    }

    public String getEntPassword()
    {
        return System.getProperty("entPassword");
    }


    public WebDriver getDriver()
    {
        WebDriver driver = this.thread.get();
        if (driver==null)
        {
            driver = new FirefoxDriver();
            this.thread.set(driver);
        }
        return driver;
    }



    public void removeDriver()
    {
        WebDriver driver = getDriver();
        if (driver!=null)
        {
            driver.quit();
            this.thread.remove();
        }
    }


}

