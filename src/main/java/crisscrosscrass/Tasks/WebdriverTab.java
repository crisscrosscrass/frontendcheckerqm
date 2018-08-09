package crisscrosscrass.Tasks;



import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WebdriverTab {

    private boolean answer = false;
    private boolean screenShot = false;

    public boolean open(WebDriver webDriver, String baseUrl, String checkKeyword ){

        answer = false;
        screenShot = false;
        //String winHandleBefore = webDriver.getWindowHandle();

        ((JavascriptExecutor)webDriver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(1)); //switches to new tab
        webDriver.get(baseUrl);
        try{
            if ( webDriver.getTitle().contains(checkKeyword) | webDriver.findElement(By.xpath("//*[@id='headline']/h1")).getText().contains(checkKeyword) | webDriver.getCurrentUrl().contains(checkKeyword) ) {
                answer = true;
            }else{
                answer = false;
            }
        }catch (Exception noSupport){
            System.out.println("Error here : "+noSupport);
        }
        finally {

            /*
            screenShot = ScreenshotViaWebDriver.printScreen(webDriver,"ScreenshotTab"+checkKeyword.trim()+".png");
            if (screenShot){
                System.out.println("Screenshot tab yes");
            }else {
                System.out.println("Screenshot tab no");
            }
            */

            webDriver.switchTo().window(tabs.get(1)).close();
            webDriver.switchTo().window(tabs.get(0)); // switch back to main screen
            //webDriver.switchTo().window(winHandleBefore);
        }

        return answer;
    }

}
