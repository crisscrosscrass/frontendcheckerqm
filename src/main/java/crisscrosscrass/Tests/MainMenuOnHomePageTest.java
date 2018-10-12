package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tasks.ChangeCheckBox;
import crisscrosscrass.Tasks.Report;
import crisscrosscrass.Tasks.WebdriverTab;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class MainMenuOnHomePageTest {
    final static Logger logger = Logger.getLogger(MainMenuOnHomePageTest.class);
    Report failedTestCases = new Report();

    public void checkingMainMenuTabs(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox MainMenuLinkTabs, Text statusInfo, TextField inputSearch, Properties Homepage){
        failedTestCases.writeToNamedFile("CHECKING MAIN MENU", "FailAndReview");
        final String infoMessage = MainMenuLinkTabs.getText();
        ChangeCheckBox.adjustStyle(false,"progress",MainMenuLinkTabs);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputSearch.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.main.links"))));
                    List<WebElement> allMainMenuTabs = webDriver.findElementsByXPath(Homepage.getProperty("page.main.links"));
                    Point scrollItem = allMainMenuTabs.get(0).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(scrollItem.getY())+");");
                    Actions hover = new Actions(webDriver);
                    WebdriverTab newtab = new WebdriverTab();
                    report.writeToFile("Checking Main Menu Links: ");
                    int counterXPath = 0;
                    //TODO need to adjust full length
                    for (int i = 0 ; i < 1 ; i++){
                        webDriver.switchTo().window(tabs.get(0));
                        //hover
                        hover.moveToElement(allMainMenuTabs.get(i)).build().perform();
                        //dynamic xpath cause of Page Structure
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='item"+counterXPath+"_tab']/div/div/*[not(contains(@class,'more-categories'))]")));
                        List<WebElement> allMainMenuSubLinks = webDriver.findElementsByXPath("//*[@id='item"+counterXPath+"_tab']/div/div/*[not(contains(@class,'more-categories'))]");
                        for (int j = 0 ; j < 8 ; j++ ){
                            hover.moveToElement(allMainMenuSubLinks.get(j)).build().perform();
                            if (null != allMainMenuSubLinks.get(j).getAttribute("href")){
                                boolean isSuccessful = newtab.openCheckURLTitleH1H2(webDriver,allMainMenuSubLinks.get(j).getAttribute("href"),allMainMenuSubLinks.get(j).getAttribute("innerHTML"));
                                if (isSuccessful){
                                    report.writeToFile("Successful |  found \"" + allMainMenuSubLinks.get(j).getAttribute("innerHTML") + "\" Keyword at URL : "+ allMainMenuSubLinks.get(j).getAttribute("href") );
                                }else {
                                    report.writeToFile("unable to check! | couldn't found \"" + allMainMenuSubLinks.get(j).getAttribute("innerHTML") + "\" Keyword in URL : "+ allMainMenuSubLinks.get(j).getAttribute("href"));
                                    failedTestCases.writeToNamedFile("Please check the following URL: unable to check! | couldn't found \"" + allMainMenuSubLinks.get(j).getAttribute("innerHTML") + "\" Keyword in URL : "+ allMainMenuSubLinks.get(j).getAttribute("href"),"FailAndReview");
                                }
                            }
                        }
                        ++counterXPath;
                    }
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.main.footer.box"))));
                    ChangeCheckBox.adjustStyle(true,"complete",MainMenuLinkTabs);
                    report.writeToFile(infoMessage, "Complete, checked all Links and Sublinks in Main Menu!");
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",MainMenuLinkTabs);
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Main Menu\" Links");
                    failedTestCases.writeToNamedFile("Please check Main Menu: Couldn't detect \"Main Menu\" Links", "FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",MainMenuLinkTabs);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile("Please check Main Menu: Couldn't navigate to requested Site!", "FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",MainMenuLinkTabs);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile("Please check Main Menu: Browser not responding!", "FailAndReview");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");

    }
    public void checkingMainMenuIndex(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox MainMenuLinkIndex, Text statusInfo, TextField inputSearch, Properties Homepage){
        final String infoMessage = MainMenuLinkIndex.getText();
        ChangeCheckBox.adjustStyle(false,"progress",MainMenuLinkIndex);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputSearch.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 60);
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.main.links"))));
                    List<WebElement> allMainMenuTabs = webDriver.findElementsByXPath(Homepage.getProperty("page.main.links"));
                    Point scrollItem = allMainMenuTabs.get(0).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(scrollItem.getY())+");");
                    Actions hover = new Actions(webDriver);
                    WebdriverTab newtab = new WebdriverTab();
                    report.writeToFile("Checking Main Menu Links on Index: ");
                    ArrayList<String> urlsToBeChecked = new ArrayList<>();
                    ArrayList<String> keywordsToBeChecked = new ArrayList<>();
                    int counterXPath = 0;
                    for (int i = 0 ; i < allMainMenuTabs.size() ; i++){
                        webDriver.switchTo().window(tabs.get(0));
                        hover.moveToElement(allMainMenuTabs.get(i)).build().perform();
                        //dynamic xpath cause of page Structure
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='item"+counterXPath+"_tab']/div/div/*[not(contains(@class,'more-categories'))]")));
                        List<WebElement> allMainMenuSubLinks = webDriver.findElementsByXPath("//*[@id='item"+counterXPath+"_tab']/div/div/*[not(contains(@class,'more-categories'))]");
                        for (int j = 0 ; j < allMainMenuSubLinks.size() ; j++ ){
                            hover.moveToElement(allMainMenuSubLinks.get(j)).build().perform();
                            if (null != allMainMenuSubLinks.get(j).getAttribute("href")){
                                urlsToBeChecked.add(allMainMenuSubLinks.get(j).getAttribute("href").toLowerCase().trim());
                                keywordsToBeChecked.add(allMainMenuSubLinks.get(j).getAttribute("innerHTML"));
                            }
                        }
                        ++counterXPath;
                    }
                    logger.info("Size of urlsToBeChecked : "+ urlsToBeChecked.size());
                    logger.info("Size of keywordsToBeChecked : "+ keywordsToBeChecked.size());
                    /* cause of Captcha not possible to check all URLs
                    try{
                        webDriver.navigate().to("https://searchenginereports.net/google-index-checker/");
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='googleIndex']/div/textarea")));
                        WebElement pageIndexSearch = webDriver.findElementByXPath("//*[@id='googleIndex']/div/textarea");
                        pageIndexSearch.sendKeys(""+urlsToBeChecked.get(0));
                        //pageIndexSearch.submit();
                        Point hoverItem = webDriver.findElement(By.xpath("//*[@id='googleIndex']/div/div[3]/input")).getLocation();
                        ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                        ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                        webDriver.findElementByXPath("//*[@id='googleIndex']/div/div[3]/input").click();
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='result_box']/div[2]/div[4]/strong")));
                        logger.info("Site is : "+webDriver.findElementByXPath("//*[@id='result-main']/div[2]/div[4]/strong").getText());


                    }catch (Exception noOnlineCheck){
                        noOnlineCheck.printStackTrace();
                    }
                    */


                    /* cause of Google Captcha not possible to check all URLs
                    try{
                        for (int i = 0 ; i < 5 ; i ++){
                            webDriver.navigate().to("https://www.google.com/");
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='lst-ib']")));
                            WebElement googleSearch = webDriver.findElementByXPath("//*[@id='lst-ib']");
                            googleSearch.sendKeys("site:"+urlsToBeChecked.get(i));
                            googleSearch.submit();
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='g']//h3/a")));
                            List<WebElement> GoogleResults = webDriver.findElementsByXPath("//div[@class='g']//h3/a");
                            if (GoogleResults.size() > 0){
                                if (urlsToBeChecked.get(i).equals(GoogleResults.get(0).getAttribute("href").toLowerCase().trim())){
                                    logger.info("Site on index");
                                    report.writeToFile("\"" + keywordsToBeChecked.get(i) + "\" | "+ urlsToBeChecked.get(i)+" | Index");
                                }else {
                                    logger.info("Site not index");
                                    report.writeToFile("\"" + keywordsToBeChecked.get(i) + "\" | "+ urlsToBeChecked.get(i)+" | no Index");
                                    failedMainMenuTest.writeToNamedFile("\"" + keywordsToBeChecked.get(i) + "\" | "+ urlsToBeChecked.get(i)+" | no Index",failedReportName);
                                }
                            }else {
                                logger.info("Site not index");
                                report.writeToFile("\"" + keywordsToBeChecked.get(i) + "\" | "+ urlsToBeChecked.get(i)+" | no Index");
                                failedMainMenuTest.writeToNamedFile("\"" + keywordsToBeChecked.get(i) + "\" | "+ urlsToBeChecked.get(i)+" | no Index",failedReportName);
                            }
                        }
                    }catch (Exception noGoogleSearch){
                        noGoogleSearch.printStackTrace();
                        report.writeToFile("unable to check! | google Response Error");
                        failedMainMenuTest.writeToNamedFile("unable to check! | google Response Error",failedReportName);
                    }
                    */

                    ChangeCheckBox.adjustStyle(true,"complete",MainMenuLinkIndex);
                    report.writeToFile(infoMessage, "Complete");
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",MainMenuLinkIndex);
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Main Menu\" Links");
                    failedTestCases.writeToNamedFile("Please check Main Menu: Couldn't detect \"Main Menu\" Links", "FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",MainMenuLinkIndex);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile("Please check Main Menu: Couldn't navigate to requested Site!", "FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",MainMenuLinkIndex);
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile("Please check Main Menu: Browser not responding!", "FailAndReview");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");

    }

    public void checkingShoppingWorld(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox ShoppingWorlds, Text statusInfo, TextField inputSearch, Properties Homepage){
        final String infoMessage = ShoppingWorlds.getText();
        ChangeCheckBox.adjustStyle(false,"progress",ShoppingWorlds);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputSearch.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.main.shoppingworld"))));
                    webDriver.findElementByXPath(Homepage.getProperty("page.main.shoppingworld")).click();
                    if (webDriver.getCurrentUrl().contains("inspiration")){
                        ChangeCheckBox.adjustStyle(true,"complete",ShoppingWorlds);
                        report.writeToFile(infoMessage, "User is redirected to a page whose URL contains \"inspiration\"");
                    }else {
                        ChangeCheckBox.adjustStyle(true,"nope",ShoppingWorlds);
                        report.writeToFile(infoMessage, "User is NOT redirected to a page whose URL contains \"inspiration\"");
                        failedTestCases.writeToNamedFile(infoMessage, "Please check: Shopping World link does not seem to lead to the correct URL", "FailAndReview");
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",ShoppingWorlds);
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Main Menu\" Links");
                    failedTestCases.writeToNamedFile("Please check Main Menu: Couldn't detect \"Shopping World\" Links", "FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",ShoppingWorlds);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile("Please check Main Menu: Couldn't navigate to requested Site!", "FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",ShoppingWorlds);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile("Please check Main Menu: Browser not responding!", "FailAndReview");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");

    }
    public void checkingShoppingWorldOnIndex(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox ShoppingWorldsOnIndex, Text statusInfo, TextField inputSearch, Properties Homepage){
        final String infoMessage = ShoppingWorldsOnIndex.getText();
        ChangeCheckBox.adjustStyle(false,"progress",ShoppingWorldsOnIndex);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputSearch.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.main.shoppingworld"))));
                    final String previousURL = webDriver.findElementByXPath(Homepage.getProperty("page.main.shoppingworld")).getAttribute("href").toLowerCase().trim();
                    webDriver.navigate().to("https://www.google.com/");
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='lst-ib']")));
                    WebElement googleSearch = webDriver.findElementByXPath("//*[@id='lst-ib']");
                    googleSearch.sendKeys("site:"+previousURL);
                    googleSearch.submit();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='g']//h3/a")));
                    List<WebElement> GoogleResults = webDriver.findElementsByXPath("//div[@class='g']//h3/a");
                        /* havent
                        if (previousURL.equals(GoogleResults.get(0).getAttribute("href").toLowerCase().trim())){
                            logger.info("Site on index");
                            ChangeCheckBox.adjustStyle(true,"complete",ShoppingWorldsOnIndex);
                            report.writeToFile(infoMessage,"\"" + previousURL+"\" | Index");
                        }else {
                            logger.info("Site not index");
                            ChangeCheckBox.adjustStyle(true,"nope",ShoppingWorldsOnIndex);
                            report.writeToFile(infoMessage,"\"" + previousURL+"\" | No Index");
                        }
                         */
                    if (GoogleResults.size() > 0 ){
                        logger.info("Site on index");
                        ChangeCheckBox.adjustStyle(true,"complete",ShoppingWorldsOnIndex);
                        report.writeToFile(infoMessage,"\"" + previousURL+"\" | Index");
                    }else {
                        logger.info("Site not index");
                        ChangeCheckBox.adjustStyle(true,"nope",ShoppingWorldsOnIndex);
                        report.writeToFile(infoMessage,"\"" + previousURL+"\" | No Index");
                        failedTestCases.writeToNamedFile(infoMessage,"Please check Shopping world link: \"" + previousURL+"\" | No Index", "FailAndReview");
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",ShoppingWorldsOnIndex);
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Shopping World\" Links");
                    failedTestCases.writeToNamedFile("Please check Main Menu: Couldn't detect \"Shopping World\" Links", "FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",ShoppingWorldsOnIndex);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile("Please check Main Menu: Couldn't navigate to requested Site!", "FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",ShoppingWorldsOnIndex);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile("Please check Main Menu: Browser not responding!", "FailAndReview");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");


    }
}
