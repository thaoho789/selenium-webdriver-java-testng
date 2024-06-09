package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_10_Custom_Dropdown {
    WebDriver driver;
 /*   String firstname = "Thao", lastname = "Ho", email = getEmailAddress(), password = "123456";
    String day = "1", month = "April", year = "1995";*/

    //Wait tuong minh: trang thai cu the cho element
    //Visible/Invisible/Presence/Number/Clickable/...
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
    public void TC_01_() {
        driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");

        //Click on dropdown =>show all items of dropdown
        driver.findElement(By.cssSelector("span#number-button")).click();
        sleepInSeconds(3);

        //Explicit wait until all item display in HTML
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul#number-menu div")));

        //Gom all item ve list=>de duyet qua all = foreach
        List<WebElement> allItems = driver.findElements(By.cssSelector("ul#number-menu div"));

        //Check dung text => Select option

        for (WebElement item : allItems) {
            String textItem = item.getText();

            if (textItem.equals("8")) {
                item.click();
                break; //dung vong lap sau khi click vao 8
            }
        }
    }

    @Test
    public void TC_02_JQuery() {
        driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");

        selectItemInDropdown("span#speed-button", "ul#speed-menu div", "Slow");
        sleepInSeconds(3);

        driver.navigate().refresh();
        selectItemInDropdown("span#files-button", "ul#files-menu div", "Some unknown file");
        sleepInSeconds(3);

        selectItemInDropdown("span#number-button", "ul#number-menu div", "5");
        sleepInSeconds(3);

        selectItemInDropdown("span#salutation-button", "ul#salutation-menu div", "Prof.");
        sleepInSeconds(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("span#speed-button>span.ui-selectmenu-text")).getText(),"Slow");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#files-button>span.ui-selectmenu-text")).getText(),"Some unknown file");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button>span.ui-selectmenu-text")).getText(),"5");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#salutation-button>span.ui-selectmenu-text")).getText(),"Prof.");

    }

    @Test
    public void TC_03_ReactJS(){
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
        sleepInSeconds(3);

        selectItemInDropdown("i.dropdown.icon","div.item>span.text","Stevie Feliciano");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Stevie Feliciano");
        sleepInSeconds(3);

        selectItemInDropdown("i.dropdown.icon","div.item>span.text","Elliot Fu");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Elliot Fu");
        sleepInSeconds(3);
    }

    @Test
    public void TC_04_VueJS() {
        driver.get("https://mikerodham.github.io/vue-dropdowns/");
        sleepInSeconds(3);

        selectItemInDropdown("li.dropdown-toggle","ul.dropdown-menu a","First Option");
        Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "First Option");
        sleepInSeconds(3);

        selectItemInDropdown("li.dropdown-toggle","ul.dropdown-menu a","Second Option");
        Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "Second Option");
        sleepInSeconds(3);

    }
    @Test
    public void TC_05_Editable_dropdown() {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
        sleepInSeconds(3);

        selectItemInEditableDropdown("input.search","div.item span.text","Australia");
        sleepInSeconds(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Australia");
    }

    @Test
    public void TC_06_Nopcommerce_Default_Dropdown() {
        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
        sleepInSeconds(3);

        selectItemInDropdown("select[name='DateOfBirthDay']","select[name='DateOfBirthDay']>option","12");
        sleepInSeconds(3);

        Assert.assertTrue(driver.findElement(By.cssSelector("select[name='DateOfBirthDay']>option[value='12']")).isSelected());
    }

    @Test
    public void TC_07_Verify_Userid_OrangeHRM() {
        driver.get("https://opensource-demo.orangehrmlive.com");
        sleepInSeconds(6);

        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("Admin");
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        sleepInSeconds(6);

        driver.findElement(By.xpath("//a[@href='/web/index.php/pim/viewMyDetails']")).click();
        sleepInSeconds(15);

        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value"), "muser");
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

    public void selectItemInEditableDropdown(String parentCSss, String childItemsCss, String itemTextExpected) {
        driver.findElement(By.cssSelector(parentCSss)).clear();
        driver.findElement(By.cssSelector(parentCSss)).sendKeys(itemTextExpected);
        sleepInSeconds(3);
        List<WebElement> allItems = explicitWait.until(ExpectedConditions.
                presenceOfAllElementsLocatedBy(By.cssSelector(childItemsCss)));
        for (WebElement item : allItems) {
            //String textItem = item.getText();
            if (item.getText().equals(itemTextExpected)) {
                item.click();
                break;
            }
        }
    }

    @AfterClass
    public void afterClass() {
        driver.quit();


    }
}
