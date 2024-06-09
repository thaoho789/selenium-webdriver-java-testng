package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Topic_18_Window_Tab {
    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Basic_Form() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getTitle());

        String parentID = driver.getWindowHandle();
        System.out.println("Parent Tab ID = " + parentID);

        driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
        sleepInSeconds(3);

        switchToWindowByID(parentID);

        driver.findElement(By.xpath("//textarea[@id='APjFqb' and @class='gLFyf']")).sendKeys("selenium");

        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getTitle());

        String GoogleID = driver.getWindowHandle();

        switchToWindowByID(GoogleID);

        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getTitle());

    }

    //@Test
    public void TC_02_KynaEnglish() {
        driver.get("https://skills.kynaenglish.vn/");
        sleepInSeconds(3);

        driver.findElement(By.xpath("a=Facebook_icon")).click();
        sleepInSeconds(3);

        switchToWindowByTitle("Facebook Kyna");
        driver.findElement(By.xpath("input#email")).sendKeys("thaoho@gmail.com");

        switchToWindowByTitle("Kyna Homepage");
        driver.findElement(By.xpath("//div=Youtube_icon")).click();
        sleepInSeconds(3);

        switchToWindowByTitle("Kyna Youtube");
        Assert.assertEquals(driver.findElement(By.xpath("//title=Kyna channel")).getText(), "Kyna channel");
    }

    @Test
    public void TC_03_TechPanda() {
        driver.get("http://live.techpanda.org/index.php/");
        sleepInSeconds(3);

        driver.findElement(By.xpath("//a[text()='Mobile']")).click();
        sleepInSeconds(3);

        String parentID = driver.getWindowHandle();

        driver.findElement(By.xpath("//a[@title='IPhone']//parent::h2//following-sibling::div[@class='actions']//a[@class='link-compare']")).click();
        sleepInSeconds(3);
        Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "The product IPhone has been added to comparison list.");

       /* click compare button
                swwitch to new window
        The product IPhone has been added to comparison list.
*/
        driver.findElement(By.xpath("//button//span[text()='Compare']")).click();
        sleepInSeconds(3);

        switchToWindowByTitle("Products Comparison List - Magento Commerce");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='page-title title-buttons']/h1")).getText(), "COMPARE PRODUCTS");

        switchToWindowByTitle("Mobile");

        driver.findElement(By.cssSelector("input#search")).sendKeys("iphone");
        closeAllWindowsExceptParentWindow(parentID);
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

    public void switchToWindowByID(String expectedID){
        Set<String> allIDs = driver.getWindowHandles();

        for (String id : allIDs){
            if (!id.equals((expectedID))){
                driver.switchTo().window(id);
                break;
            }
        }
    }

    public void switchToWindowByTitle(String expectedTitle){
        Set<String> allIDs = driver.getWindowHandles();
        for (String id : allIDs){
            driver.switchTo().window(id);

        String actualTitle = driver.getTitle();
        if (actualTitle.equals(expectedTitle)){
            break;
        }
      }
    }

    public void closeAllWindowsExceptParentWindow(String parentID){
        Set<String> allIDs = driver.getWindowHandles();
        for (String id : allIDs){
            if (!id.equals(parentID)){
                driver.switchTo().window(id);
                driver.close();
            }
        }
        driver.switchTo().window(parentID);
    }
}
