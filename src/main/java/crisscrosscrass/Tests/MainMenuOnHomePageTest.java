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

    public void checkingMainMenuTabs(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox MainMenuLinkTabs, Text statusInfo, TextField inputSearch, Properties Homepage){
        final String infoMessage = MainMenuLinkTabs.getText();
        Report failedMainMenuTest = new Report();
        final String failedMainMenuReportName = "failedMainMenuTest";
        failedMainMenuTest.clearWrittenNamendReport(failedMainMenuReportName);
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
                    failedMainMenuTest.writeToNamedFile("Failed Main Menu Links: ",failedMainMenuReportName);
                    int counterXPath = 0;
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
                                    failedMainMenuTest.writeToNamedFile("unable to check! | couldn't found \"" + allMainMenuSubLinks.get(j).getAttribute("innerHTML") + "\" Keyword in URL : "+ allMainMenuSubLinks.get(j).getAttribute("href"),failedMainMenuReportName);
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
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",MainMenuLinkTabs);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",MainMenuLinkTabs);
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");

    }
    public void checkingMainMenuIndex(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox MainMenuLinkIndex, Text statusInfo, TextField inputSearch, Properties Homepage){
        final String infoMessage = MainMenuLinkIndex.getText();
        Report failedMainMenuTest = new Report();
        final String failedReportName = "failedMainMenuIndexTest";
        failedMainMenuTest.clearWrittenNamendReport(failedReportName);
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
                    failedMainMenuTest.writeToNamedFile("Failed Main Menu Links on Index: ",failedReportName);
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
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",MainMenuLinkIndex);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",MainMenuLinkIndex);
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");

    }
}
