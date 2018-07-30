package crisscrosscrass.TestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FilterButtonCheck {

    static boolean answer = false;



    public static boolean pressFilterButton(WebDriver webDriver, JavascriptExecutor js, String xpathString){

        try{

            webDriver.findElement(By.xpath(xpathString)).click();


            answer = true;

        }catch (Exception ButtonFilterNotFound){
            System.out.println("Couldn't find clickable button");

            answer = false;

        }


        return answer;
    }

}
