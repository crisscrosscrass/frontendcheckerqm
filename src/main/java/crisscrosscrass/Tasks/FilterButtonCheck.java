package crisscrosscrass.Tasks;

import crisscrosscrass.Tests.PartnerShopsPageTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class FilterButtonCheck {
    final static Logger logger = Logger.getLogger(FilterButtonCheck.class);
    static boolean answer = false;

    public static boolean pressFilterButton(WebDriver webDriver, JavascriptExecutor js, String xpathString){
        try{
            webDriver.findElement(By.xpath(xpathString)).click();
            answer = true;
        }catch (Exception ButtonFilterNotFound){
            logger.error("Couldn't find a clickable button");
            answer = false;
        }
        return answer;
    }
}
