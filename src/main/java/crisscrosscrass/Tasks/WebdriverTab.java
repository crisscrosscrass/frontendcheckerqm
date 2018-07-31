package crisscrosscrass.Tasks;



import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class WebdriverTab {

    private boolean answer = false;

    public boolean open(WebDriver webDriver, String baseUrl, String checkKeyword ){
        answer = false;
        //String winHandleBefore = webDriver.getWindowHandle();

        ((JavascriptExecutor)webDriver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(1)); //switches to new tab
        webDriver.get(baseUrl);
        try{
            if ( webDriver.getTitle().contains(checkKeyword) | webDriver.findElement(By.xpath("//*[@id='headline']/h1")).getText().contains(checkKeyword) ) {
                answer = true;
            }else{
                answer = false;
            }
        }catch (Exception noSupport){
            System.out.println("Error here : "+noSupport);
        }
        finally {
            webDriver.switchTo().window(tabs.get(1)).close();
            webDriver.switchTo().window(tabs.get(0)); // switch back to main screen
            //webDriver.switchTo().window(winHandleBefore);
        }

        return answer;
    }

}
