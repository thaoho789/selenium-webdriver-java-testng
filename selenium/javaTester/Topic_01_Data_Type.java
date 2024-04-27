package javaTester;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class Topic_01_Data_Type {
    //Kieu du lieu trong Java - 2 nhom

    //I - Kieu du lieu nguyen thuy (Primitive Type)
    // 8 loai
    //So nguyen: byte = short = int - long
    //So thuc
    //Ki tu
    //Logic

    //II - Kieu du lieu tham chieu (Reference Type)
    //Class
    FirefoxDriver firefoxDriver = new FirefoxDriver();

    Select select = new Select(firefoxDriver.findElement(By.className("")));
    Topic_01_Data_Type topic01 = new Topic_01_Data_Type();

    //Interface
    WebDriver driver;
    JavascriptExecutor jsExecutor;

    //Collection: List/Set/Queue
    List<String> studentAddress = new ArrayList<String>();

    //String
    String studentName = "Thao";


}
