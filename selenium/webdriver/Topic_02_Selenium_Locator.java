package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;
import java.lang.System;


public class Topic_02_Selenium_Locator {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    By by;
    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://demo.nopcommerce.com/register");

    }
/*<input type="text" data-val="true"
data-val-required="First name is required."
id="FirstName" name="FirstName">
 */
    @Test
    public void TC_01_ID() {
        //Tim element co ID = FirstName
        driver.findElement(By.id("FirstName"));
    }

    @Test
    public void TC_02_Class() {
/*<div class="header-logo"><a href="/">
<img alt="nopCommerce demo store
" src="https://demo.nopcommerce.com/Themes/DefaultClean/Content/images/logo.png"> </a></div>
 */
        driver.findElement(By.className("header-logo"));
    }

    @Test
    public void TC_03_Name() {
/*<input type="text" data-val="true" data-val-required="Last name is required."
id="LastName" name="LastName">
 */
       driver.findElement(By.name("LastName"));
    }
    @Test
    public void TC_04_Tagname() {
        driver.findElements(By.tagName("input"));

    }
    @Test
    public void TC_05_LinkText() {
        driver.findElement(By.linkText("News"));

    }
    @Test
    public void TC_06_Partial_LinkText() {
        driver.findElement(By.partialLinkText("Shipping"));
        driver.findElement(By.partialLinkText("returns"));
        driver.findElement(By.partialLinkText("Shipping & returns"));

    }
    @Test
    public void TC_07_CSS() {
        driver.findElement(By.cssSelector("input[id='FirstName']"));
    }
    @Test
    public void TC_08_Xpath() {

        driver.findElement(By.xpath("//input[@id='FirstName']"));
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
