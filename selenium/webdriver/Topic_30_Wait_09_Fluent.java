package webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_30_Wait_09_Fluent {

    WebDriver driver;
    WebDriverWait explicitWait;

    @BeforeClass
    public void beforeClass() {
//        if (osName.contains("Windows")) {
//            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
//        } else {
//            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
//        }

        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get("https://demo.nopcommerce.com/register");
    }

    @Test
    public void TC_01_() {
        String currentURL = driver.getCurrentUrl();
        System.out.println(currentURL);

        String pageTitle = driver.getTitle();
        System.out.println(pageTitle);

    }

    @Test
    public void TC_02_() {
    }
    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
