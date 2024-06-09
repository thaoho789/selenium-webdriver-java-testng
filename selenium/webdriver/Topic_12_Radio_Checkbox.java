package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Topic_12_Radio_Checkbox {
    WebDriver driver;
    WebDriverWait explicitWait;

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        //Wait ngam dinh: ko ro rang cho 1 trang thai cu the nao cua element
        //Used for finding element - findElement(s)
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Default_Checkbox_Telerik() {
        driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
        sleepInSeconds(9);

        By rearSideCheckbox = By.xpath("//label[text()='Rear side airbags']/preceding-sibling::span/input");
        By dualZoneCheckbox = By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::span/input");

        //Case 1: Airbag da chon san roi=>verify selected
        checkToElement(rearSideCheckbox);

        //Case 2: Dual zone chua chon =>Click chon=>verify selected
        checkToElement(dualZoneCheckbox);

        Assert.assertTrue(driver.findElement(rearSideCheckbox).isSelected());
        Assert.assertTrue(driver.findElement(dualZoneCheckbox).isSelected());
        sleepInSeconds(4);

        uncheckToElement(rearSideCheckbox);
        sleepInSeconds(3);
        uncheckToElement(dualZoneCheckbox);
        sleepInSeconds(3);
        Assert.assertFalse(driver.findElement(rearSideCheckbox).isSelected());
        Assert.assertFalse(driver.findElement(dualZoneCheckbox).isSelected());
    }

    @Test
    public void TC_03_Select_All_Checkbox() {
        driver.get("https://automationfc.github.io/multiple-fields/");
        sleepInSeconds(5);

        List<WebElement> allCheckboxes = driver.findElements(By.cssSelector("div.form-single-column input[type='checkbox']"));

        //Chon all checkbox trong man hinh do
        for (WebElement checkbox : allCheckboxes) {
            if (!checkbox.isSelected()) {
                checkbox.click();
                //sleepInSeconds(0.5);
            }
        }

        //Verify all checkbox selected
        for (WebElement checkbox : allCheckboxes) {
            Assert.assertTrue(checkbox.isSelected());
            //sleepInSeconds(2);
        }

        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        sleepInSeconds(3);


        allCheckboxes = driver.findElements(By.cssSelector("div.form-single-column input[type='checkbox']"));

        //Chon 1 checkbox trong all checkbox
        for (WebElement checkbox : allCheckboxes) {
            if (checkbox.getAttribute("value").equals("Heart Attack") && !checkbox.isSelected()) {
                checkbox.click();
                sleepInSeconds(3);
            }
        }

        //Verify heart attack selected
        for (WebElement checkbox : allCheckboxes) {
            if (checkbox.getAttribute("value").equals("Heart Attack")) {
                Assert.assertTrue(checkbox.isSelected());
                //sleepInSeconds(2);
            } else {
                Assert.assertFalse(checkbox.isSelected());
                //sleepInSeconds(2);
            }
        }
    }

    // @Test
    public void TC_03_ReactJS() {

    }

    //@Test
    public void TC_04_VueJS() {


    }

    //@Test
    public void TC_05_Editable_dropdown() {

    }

    @Test
    public void TC_06_Nopcommerce_Default_Dropdown() {

    }

    @Test
    public void TC_07_Verify_Userid_OrangeHRM() {
    }

    public void sleepInSeconds(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectItemInDropdown(String parentCSss, String childItemsCss, String itemTextExpected) {
        driver.findElement(By.cssSelector(parentCSss)).click();
        sleepInSeconds(3);
        //explicit vua wait vua luu thanh 1 list element =>gop chug lai nhanh hon
        /*explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(childItemsCss)));
        List<WebElement> allItems = driver.findElements(By.cssSelector(childItemsCss));*/
        List<WebElement> allItems = explicitWait.until(ExpectedConditions.
                presenceOfAllElementsLocatedBy(By.cssSelector(childItemsCss)));
        for (WebElement item : allItems) {
            String textItem = item.getText();
            if (textItem.equals(itemTextExpected)) {
                item.click();
                break;
            }
        }
    }

    public void checkToElement(By xPath) {
        if (!driver.findElement(xPath).isSelected()) {
            driver.findElement(xPath).click();
            sleepInSeconds(2);
        }
    }

    public void uncheckToElement(By xPath) {
        if (driver.findElement(xPath).isSelected()) {
            driver.findElement(xPath).click();
            sleepInSeconds(2);
        }
    }

    @AfterClass
    public void afterClass() {
        driver.quit();


    }
}
