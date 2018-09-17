package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tasks.ChangeCheckBox;
import crisscrosscrass.Tasks.Report;
import crisscrosscrass.Tasks.ScreenshotViaWebDriver;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class MerchandiseOverviewPageTest {
    final static Logger logger = Logger.getLogger(MerchandiseOverviewPageTest.class);
    public void checkingMerchandiseLetters(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox BecomeTradeTrackerPartner, Text statusInfo, TextField inputMerchandiseOverviewPageURL, Properties Homepage){
        final String infoMessage = "Checking Letter to Merchandise";
        ChangeCheckBox.adjustStyle(false,"progress",BecomeTradeTrackerPartner);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("merchandisepage.alphabet.buttons"))));
                    List<WebElement> AllAlphabetButtons = webDriver.findElementsByXPath(Homepage.getProperty("merchandisepage.alphabet.buttons"));
                    final int randomSelectIndex = ThreadLocalRandom.current().nextInt(0, AllAlphabetButtons.size() );
                    final String selectedLetter = AllAlphabetButtons.get(randomSelectIndex).getText();
                    AllAlphabetButtons.get(randomSelectIndex).click();
                    for (int i = 0; i < 1; i++) {
                        Thread.sleep(500);
                        js.executeScript("window.scrollBy(0,1)");
                    }
                    for (int i = 0; i < 1; i++) {
                        Thread.sleep(500);
                        js.executeScript("window.scrollBy(0,-1)");
                    }
                    List<WebElement> AllAlphabetLetters = webDriver.findElementsByXPath(Homepage.getProperty("merchandisepage.alphabet.letters"));
                    WebElement ViewPortElement = null;
                    for (WebElement AlphabetLetter : AllAlphabetLetters){
                        if (AlphabetLetter.getText().toLowerCase().trim().equals(selectedLetter.toLowerCase().trim())){
                            ViewPortElement = AlphabetLetter;
                            break;
                        }
                    }
                    boolean isInViewPort = (boolean)((JavascriptExecutor)webDriver).executeScript(
                            "var elem = arguments[0],                 " +
                                    "  box = elem.getBoundingClientRect(),    " +
                                    "  cx = box.left + box.width / 2,         " +
                                    "  cy = box.top + box.height / 2,         " +
                                    "  e = document.elementFromPoint(cx, cy); " +
                                    "for (; e; e = e.parentElement) {         " +
                                    "  if (e === elem)                        " +
                                    "    return true;                         " +
                                    "}                                        " +
                                    "return false;                            "
                            , ViewPortElement);
                    if (isInViewPort){
                        ChangeCheckBox.adjustStyle(true,"complete",BecomeTradeTrackerPartner);
                        report.writeToFile(infoMessage, "The letter \""+selectedLetter+"\" which was clicked appears on top of page in user's pageview");
                    }else {
                        ChangeCheckBox.adjustStyle(true,"nope",BecomeTradeTrackerPartner);
                        report.writeToFile(infoMessage, "The letter \""+selectedLetter+"\" which was clicked appears on in user's pageview");
                        boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "MerchandisePageLetterToMerchandise.png");
                        if (isSuccessful){
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                        }
                    }
                    

                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",BecomeTradeTrackerPartner);
                    webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Letters\" to Merchandise");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",BecomeTradeTrackerPartner);
                webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",BecomeTradeTrackerPartner);
            webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
    public void checkingMerchandiseName(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox MerchandiseName, Text statusInfo, TextField inputMerchandiseOverviewPageURL, Properties Homepage){
        final String infoMessage = "Checking Merchandise Name";
        ChangeCheckBox.adjustStyle(false,"progress",MerchandiseName);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("merchandisepage.merchandise.links"))));
                    List<WebElement> AllMerchandiseLinks = webDriver.findElementsByXPath(Homepage.getProperty("merchandisepage.merchandise.links"));
                    final int randomSelectIndex = ThreadLocalRandom.current().nextInt(0, AllMerchandiseLinks.size() );
                    final String selectedMerchandise = AllMerchandiseLinks.get(randomSelectIndex).getText();
                    Point hoverItem = AllMerchandiseLinks.get(randomSelectIndex).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    AllMerchandiseLinks.get(randomSelectIndex).click();
                    if (webDriver.getCurrentUrl().toLowerCase().trim().contains(selectedMerchandise.toLowerCase().trim()) | webDriver.getTitle().toLowerCase().trim().contains(selectedMerchandise.toLowerCase().trim() )){
                        ChangeCheckBox.adjustStyle(true,"complete",MerchandiseName);
                        report.writeToFile(infoMessage, "Successful! Could detect \""+selectedMerchandise+"\" in redirected Page"+webDriver.getCurrentUrl().toLowerCase());
                    }else {
                        ChangeCheckBox.adjustStyle(true,"nope",MerchandiseName);
                        report.writeToFile(infoMessage, "Not successful! Couldn't detect \""+selectedMerchandise+"\" in redirected Page"+webDriver.getCurrentUrl().toLowerCase());
                    }

                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",MerchandiseName);
                    webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Link-Name\" to Merchandise");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",MerchandiseName);
                webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",MerchandiseName);
            webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
    public void checkingMerchandiseSearch(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox MerchandiseName, Text statusInfo, TextField inputMerchandiseOverviewPageURL,TextField inputMerchandiseSearch, Properties Homepage){
        final String infoMessage = "Checking Merchandise Search";
        ChangeCheckBox.adjustStyle(false,"progress",MerchandiseName);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("merchandisepage.search.bar"))));
                    String[] AllSearchQueries = inputMerchandiseSearch.getText().split("\\|");



                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",MerchandiseName);
                    webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Search\" Merchandise");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",MerchandiseName);
                webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",MerchandiseName);
            webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
}
