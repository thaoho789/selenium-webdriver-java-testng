package webdriver;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_13_Alert {
    WebDriver driver;
    WebDriverWait explicitWait;
    By resulText = By.cssSelector("p#result");

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get("https://demo.nopcommerce.com/register");
    }

    @Test
    public void TC_01_Accept_Alert() {
        //get url
        //click button alert
        //alert display=>verify text alert
        //accept
        //verify text after click p#result//but
        driver.get("https://automationfc.github.io/basic-form/");
        sleepInSeconds(2);

        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());

        Assert.assertEquals(alert.getText(),"I am a JS Alert");
        sleepInSeconds(5);
        alert.accept();

        Assert.assertEquals(driver.findElement(resulText).getText(),"You clicked an alert successfully");
    }

    @Test
    public void TC_02_Confirm_Alert() {
        driver.get("https://automationfc.github.io/basic-form/");
        sleepInSeconds(3);

        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        sleepInSeconds(4);

        Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());

        Assert.assertEquals(alert.getText(),"I am a JS Confirm");
        sleepInSeconds(4);

        alert.dismiss();
        Assert.assertEquals(driver.findElement(resulText).getText(),"You clicked: Cancel");
    }

    @Test
    public void TC_03_Prompt_Alert() {
        driver.get("https://automationfc.github.io/basic-form/");
        sleepInSeconds(3);

        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
        Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(),"I am a JS prompt");
        sleepInSeconds(3);

        String text = "abc";
        alert.sendKeys(text);
        sleepInSeconds(3);
        alert.accept();

        Assert.assertEquals(driver.findElement(resulText).getText(), "You entered: "+text);

    }
    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    public void sleepInSeconds(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
