package javaTester;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class Topic_02_And_Or {
    public static void main(String[] args) {

        //Member 01
        boolean member01;
        //Member 2
        boolean member02;
        //Result
        boolean result;

        //AND &&
        member01 = true;
        member02 = true;
        System.out.println("Result= "+ (member01&&member02));

        //OR ||
        member01 = false;
        member02 = true;
        System.out.println("Result= "+ (member01||member02));



    }

}
