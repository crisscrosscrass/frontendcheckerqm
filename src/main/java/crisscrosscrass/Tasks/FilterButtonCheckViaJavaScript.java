package crisscrosscrass.Tasks;

import crisscrosscrass.Tests.PartnerShopsPageTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FilterButtonCheckViaJavaScript {
    final static Logger logger = Logger.getLogger(FilterButtonCheckViaJavaScript.class);
    static boolean answer = false;

    public static boolean pressFilterButton(WebDriver webDriver, JavascriptExecutor js, String xpathString){
        try{
            WebElement elementTestClick = webDriver.findElement(By.xpath(xpathString));
            js.executeScript("arguments[0].click();", elementTestClick);
            for (int i = 0 ; i < 10 ; i++){
                Thread.sleep(100);
                js.executeScript("window.scrollBy(0,100)");
            }
            answer = true;
        }catch (Exception testclickscript){
            logger.error("Javascript Click couldn't find any element");
            answer = false;
        }
        return answer;
    }

}
