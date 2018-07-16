package crisscrosscrass.TestCases;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SalesButtonCheck {

    static boolean answer = false;


    public static boolean pressSalesButtonFilter(WebDriver webDriver, JavascriptExecutor js){

        try{
            ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"saleButtonHeader2\"]").click();

            for (int i = 0 ; i < 10 ; i++){
                Thread.sleep(100);
                js.executeScript("window.scrollBy(0,100)");
            }
            answer = true;
        }catch (Exception SallesButtonFilterNotFound){
            System.out.println(SallesButtonFilterNotFound);
            answer = false;

        }


        return answer;
    }
}
