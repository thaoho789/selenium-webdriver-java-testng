package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_08_Textbox_Textarea {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

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

    }

    @Test
    public void Login_01_Empty_Email_And_Pass() {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
        sleepInSeconds(3);

        driver.findElement(By.cssSelector("button#send2")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("div#advice-required-entry-email")).getText(), "This is a required field.");
        Assert.assertEquals(driver.findElement(By.cssSelector("div#advice-required-entry-pass")).getText(), "This is a required field.");


    }

    @Test
     public void Login_02_Invalid_Email() {
        driver.get("http://live.techpanda.org/");
        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
        sleepInSeconds(3);

        driver.findElement(By.cssSelector("input#email")).sendKeys("thaoho@123");
        driver.findElement(By.cssSelector("input#pass")).sendKeys("123456");
        driver.findElement(By.cssSelector("button#send2")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("div#advice-validate-email-email")).getText(), "Please enter a valid email address. For example johndoe@domain.com.");

    }
    @Test
    public void Login_03_PasswordsLessThan6characters() {
        driver.get("http://live.techpanda.org/");
        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
        sleepInSeconds(3);

        driver.findElement(By.cssSelector("input#email")).sendKeys("thaoho@gmail.com");
        driver.findElement(By.cssSelector("input#pass")).sendKeys("123");
        driver.findElement(By.cssSelector("button#send2")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("div#advice-validate-password-pass")).getText(), "Please enter 6 or more characters without leading or trailing spaces.");

    }

    @Test
    public void TC_04_Invalid_Email_Or_Password() {
        driver.get("http://live.techpanda.org/");
        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
        sleepInSeconds(3);

        driver.findElement(By.cssSelector("input#email")).sendKeys("thaoho@gmail.com");
        driver.findElement(By.cssSelector("input#pass")).sendKeys("123678");
        driver.findElement(By.cssSelector("button#send2")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//li//span")).getText(), "Invalid login or password.");
    }
    @Test
    public void Login_05_Success() {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
        sleepInSeconds(3);

        //click create new account=>sleep
        driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
        sleepInSeconds(3);

        //email => gọi bien toi 1 hàm
        String firstName = "Thao", lastName = "Ho", emailAddress = getEmailAddress(), password = "123456789";
        String fullName = firstName + " " + lastName;

        driver.findElement(By.cssSelector("input#firstname")).sendKeys(firstName);
        driver.findElement(By.cssSelector("input#lastname")).sendKeys(lastName);
        driver.findElement(By.cssSelector("input#email_address")).sendKeys(emailAddress);
        driver.findElement(By.cssSelector("input#password")).sendKeys(password);
        driver.findElement(By.cssSelector("input#confirmation")).sendKeys(password);
        driver.findElement(By.cssSelector("button[title='Register']")).click();
        sleepInSeconds(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(),"Thank you for registering with Main Website Store.");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.welcome-msg strong")).getText(),"Hello, " + fullName + "!");

        String contactInfo = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
        Assert.assertTrue(contactInfo.contains(fullName));
        Assert.assertTrue(contactInfo.contains(emailAddress));

        //logout
        driver.findElement(By.cssSelector("a.skip-link.skip-account")).click();
        sleepInSeconds(2);
        driver.findElement(By.cssSelector("a[title='Log Out']")).click();

        //login
        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
        sleepInSeconds(3);

        driver.findElement(By.cssSelector("input#email")).sendKeys(emailAddress);
        driver.findElement(By.cssSelector("input#pass")).sendKeys(password);
        driver.findElement(By.cssSelector("button#send2")).click();
        sleepInSeconds(3);

        //verify login success
        //co contactinfo o tren rồi =>gans lai o duoi thoi

        Assert.assertEquals(driver.findElement(By.cssSelector("div.welcome-msg strong")).getText(),"Hello, " + fullName + "!");

        contactInfo = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
        Assert.assertTrue(contactInfo.contains(fullName));
        Assert.assertTrue(contactInfo.contains(emailAddress));

        //Verify Account information
        driver.findElement(By.xpath("//li/a[@href='http://live.techpanda.org/index.php/customer/account/edit/']")).click();
        sleepInSeconds(3);
        Assert.assertEquals(driver.findElement(By.cssSelector("input#firstname")).getAttribute("value"),firstName);
        Assert.assertEquals(driver.findElement(By.cssSelector("input#lastname")).getAttribute("value"),lastName);
        Assert.assertEquals(driver.findElement(By.cssSelector("input#email")).getAttribute("value"),emailAddress);

    }

    /* @Test
    public void TC_02_() {

    }*/

    public void sleepInSeconds(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getEmailAddress() {
        Random rand = new Random();
        return "automation" + rand.nextInt(99999) + "@gmail.net";
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
