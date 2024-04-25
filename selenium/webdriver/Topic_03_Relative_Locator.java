package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Topic_03_Relative_Locator {
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
    public void TC_01_Relative() {
        driver.get("https://demo.nopcommerce.com/login?returnUrl=%2Fregister");

        //Login button 2 cach deu duoc
        By loginButtonBy = By.cssSelector("button.login-button");
        WebElement loginButtonElement = driver.findElement(By.cssSelector("button.login-button"));

        //Rememberme checkbox
        By rememberMeCheckboxBy = By.id("RememberMe");
        WebElement rememberMeCheckboxElement = driver.findElement(By.id("RememberMe"));

        //RelativeLocator.with(By.tagName("label")).above(loginButtonElement);

        //Forgotpassword link
        WebElement forgotPasswordLink = driver.findElement(By.cssSelector("span.forgot-password"));

        //Password field
        WebElement PasswordFieldElement = driver.findElement(By.cssSelector("input#Password"));

        //Rememberme text
        //GUI (location/position)
        WebElement rememberMeText = driver.findElement
                (RelativeLocator.with(By.tagName("label"))
                .above(loginButtonBy)
                .toRightOf(rememberMeCheckboxBy)
                .toRightOf(rememberMeCheckboxElement)
                .toLeftOf(forgotPasswordLink)
                .below(PasswordFieldElement)
                .near(forgotPasswordLink));
        System.out.println(rememberMeText.getText());

        List<WebElement> allLinks = driver.findElements(RelativeLocator.with(By.tagName("a")));
        System.out.println(allLinks.size());
    }


    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
