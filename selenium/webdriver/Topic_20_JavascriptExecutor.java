package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

public class Topic_20_JavascriptExecutor {

    WebDriver driver;
    JavascriptExecutor jsExecutor;

    @BeforeClass
    public void beforeClass() {
//        if (osName.contains("Windows")) {
//            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
//        } else {
//            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
//        }

        driver = new FirefoxDriver();
        jsExecutor = (JavascriptExecutor) driver; //interface nen ko dùng new khi khoi tao doi tuong

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));}

    @Test
    public void TC_01_Exercise_1() {
        executeForBrowser("window.location = 'http://live.techpanda.org/'");
        sleepInSeconds(3);

        String techPandaDomain = (String) executeForBrowser("return document.domain");
        Assert.assertEquals(techPandaDomain, "live.techpanda.org");

        String pageUrl = (String) executeForBrowser("return document.URL");
        Assert.assertEquals(pageUrl,"http://live.techpanda.org/");

        hightlightElement("//a[text()='Mobile']");
        clickToElementByJS("//a[text()='Mobile']");

        hightlightElement("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div/button");
        clickToElementByJS("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div/button");
        Assert.assertTrue(getInnerText().contains("Samsung Galaxy was added to your shopping cart."));

        hightlightElement("//a[text()='Customer Service']");
        clickToElementByJS("//a[text()='Customer Service']");
        String customerServiceTitle = (String) executeForBrowser("return document.title");
        Assert.assertEquals(customerServiceTitle,"Customer Service");

        scrollToBottomPage();

        hightlightElement("//input[@id='newsletter']");
        sendkeyToElementByJS("//input[@id='newsletter']",getEmailAddress());

        hightlightElement("//button[@title='Subscribe']");
        clickToElementByJS("//button[@title='Subscribe']");
        Assert.assertTrue(isExpectedTextInInnerText("Thank you for your subscription."));

        navigateToUrlByJS("https://www.facebook.com/");
        Assert.assertEquals(executeForBrowser("return document.domain"),"Facebook");
    }

    @Test
    public void TC_02_HTML5_Validation() {
        navigateToUrlByJS("https://automationfc.github.io/html5/index.html");
        sleepInSeconds(3);
        clickToElementByJS("//input[@name='submit-btn']");

        Assert.assertEquals(getElementValidationMessage("//input[@id='fname']"),"Please fill out this field.");


    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    public String getEmailAddress() {
        Random rand = new Random();
        return "automation" + rand.nextInt(99999) + "@gmail.net";
    }

    public Object executeForBrowser(String javaScript) {

        return jsExecutor.executeScript(javaScript);
    }

    public String getInnerText() {
        return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
    }

    public boolean isExpectedTextInInnerText(String textExpected) {
        String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
        return textActual.equals(textExpected);
    }

    public void scrollToBottomPage() {
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void sleepInSeconds(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void navigateToUrlByJS(String url) {
        jsExecutor.executeScript("window.location = '" + url + "'");
        sleepInSeconds(3);
    }

    public void hightlightElement(String locator) {
        WebElement element = getWebElement(locator);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
        sleepInSeconds(2);
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
    }

    public void clickToElementByJS(String locator) {
        jsExecutor.executeScript("arguments[0].click();", getWebElement(locator));
        sleepInSeconds(3);
    }

    public void scrollToElementOnTop(String locator) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(locator));
    }

    public void scrollToElementOnDown(String locator) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getWebElement(locator));
    }

    public void setAttributeInDOM(String locator, String attributeName, String attributeValue) {
        jsExecutor.executeScript("arguments[0].setAttribute('" + attributeName + "', '" + attributeValue + "');", getWebElement(locator));
    }

    public void removeAttributeInDOM(String locator, String attributeRemove) {
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getWebElement(locator));
    }

    public void sendkeyToElementByJS(String locator, String value) {
        jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getWebElement(locator));
    }

    public String getAttributeInDOM(String locator, String attributeName) {
        return (String) jsExecutor.executeScript("return arguments[0].getAttribute('" + attributeName + "');", getWebElement(locator));
    }

    public String getElementValidationMessage(String locator) {
        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getWebElement(locator));
    }

    public boolean isImageLoaded(String locator) {
        boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", getWebElement(locator));
        return status;
    }

    public By getByXpath(String locator) {
        return By.xpath(locator);
    }

    public WebElement getWebElement(String locator) {
        return driver.findElement(getByXpath(locator));
    }
}
