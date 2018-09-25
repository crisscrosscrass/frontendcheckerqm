package crisscrosscrass.Tasks;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import java.util.ArrayList;

public class WebdriverTab {
    final static Logger logger = Logger.getLogger(WebdriverTab.class);

    private boolean answer = false;
    private boolean screenShot = false;

    public boolean open(WebDriver webDriver, String baseUrl, String checkKeyword ){
        answer = false;
        screenShot = false;

        ((JavascriptExecutor)webDriver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(1)); //switches to new tab
        webDriver.get(baseUrl);
        try{
            if ( webDriver.getTitle().toLowerCase().trim().contains(checkKeyword.toLowerCase().trim()) | webDriver.findElement(By.xpath("//*[@id='headline']/h1")).getText().toLowerCase().trim().contains(checkKeyword.toLowerCase().trim()) | webDriver.getCurrentUrl().contains(checkKeyword.toLowerCase().trim()) ) {
                answer = true;
            }else{
                answer = false;
            }
        }catch (Exception noSupport){
            logger.info("Error here : "+noSupport);
        }
        finally {
            webDriver.switchTo().window(tabs.get(1)).close();
            webDriver.switchTo().window(tabs.get(0)); // switch back to main screen
        }
        return answer;
    }
    public boolean openCheckURLTitleH1H2(WebDriver webDriver, String baseUrl, String checkKeyword ){
        answer = false;

        ((JavascriptExecutor)webDriver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(1)); //switches to new tab
        webDriver.get(baseUrl);

        boolean checkH1Element = webDriver.findElements( By.id("//*[@id='headline']/h1") ).size() != 0;
        boolean checkH2Element = webDriver.findElements( By.id("//*[@id='headline']/h2") ).size() != 0;
        try{
            if ( webDriver.getTitle().contains(checkKeyword.toLowerCase().trim()) | webDriver.getCurrentUrl().contains(checkKeyword.toLowerCase().trim()) ) {
                answer = true;
            }else{
                answer = false;
            }
        }catch (Exception noSupport){
            logger.info("Error here : "+noSupport);
        }
        finally {
            webDriver.switchTo().window(tabs.get(1)).close();
            webDriver.switchTo().window(tabs.get(0));
        }
        return answer;
    }
    public boolean open(WebDriver webDriver, String baseUrl, String checkKeyword, String checkPreviousImageUrl, String imageXPathGrid ){
        answer = false;
        screenShot = false;

        ((JavascriptExecutor)webDriver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(1)); //switches to new tab
        webDriver.get(baseUrl);
        try{
            String ShopOfTheWeekGridImage = webDriver.findElement(By.xpath(imageXPathGrid)).getAttribute("src");

            if ( webDriver.getTitle().toLowerCase().trim().contains(checkKeyword.toLowerCase().trim()) | webDriver.findElement(By.xpath("//*[@id='headline']/h1")).getText().toLowerCase().trim().contains(checkKeyword.toLowerCase().trim()) ) {
                logger.info("Keyword found");
                if (checkPreviousImageUrl.contains(ShopOfTheWeekGridImage)){
                    logger.info("Image url also found!");
                    answer = true;
                }else {
                    answer = false;
                }
            }else{
                answer = false;
            }
        }catch (Exception noSupport){
            logger.info("Error here : "+noSupport);
        }
        finally {
            webDriver.switchTo().window(tabs.get(1)).close();
            webDriver.switchTo().window(tabs.get(0)); // switch back to main screen
        }
        return answer;
    }
}
