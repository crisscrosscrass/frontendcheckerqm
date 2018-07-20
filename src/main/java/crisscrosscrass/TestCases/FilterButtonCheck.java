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

            for (int i = 0 ; i < 10 ; i++){
                Thread.sleep(100);
                js.executeScript("window.scrollBy(0,100)");
            }
            answer = true;

        }catch (Exception ButtonFilterNotFound){
            System.out.println("Couldn't find clickable button");

            answer = false;

        }


        return answer;
    }

}
