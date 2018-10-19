package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tasks.ChangeCheckBox;
import crisscrosscrass.Tasks.Report;
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

public class AffiliateProgramTest {
    final static Logger logger = Logger.getLogger(AffiliateProgramTest.class);
    Report failedTestCases = new Report();

    public void checkingBecomeAffilinetPartner(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox BecomeAffilinetPartner, Text statusInfo, TextField inputAffiliateProgramURL,TextField backupURL, Properties Homepage){
        failedTestCases.writeToNamedFile("CHECKING AFFILIATE PROGRAM PAGE", "FailAndReview");
        final String infoMessage = BecomeAffilinetPartner.getText();
        ChangeCheckBox.adjustStyle(false,"progress",BecomeAffilinetPartner);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputAffiliateProgramURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("page.main.footer.box"))).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("affiliatepage.become.buttons"))));
                    List<WebElement> allAffiliateButtons = webDriver.findElementsByXPath(Homepage.getProperty("affiliatepage.become.buttons"));
                    allAffiliateButtons.get(0).click();
                    tabs = new ArrayList<>(webDriver.getWindowHandles());
                    webDriver.switchTo().window(tabs.get(1));
                    if (webDriver.getCurrentUrl().contains("affilinet")){
                        report.writeToFile(infoMessage, "User is redirected to a functioning affilinet page : "+webDriver.getCurrentUrl().toLowerCase().trim());
                        ChangeCheckBox.adjustStyle(true,"complete",BecomeAffilinetPartner);
                    }else{
                        report.writeToFile(infoMessage, "User is redirected to a NOT affilinet page : "+webDriver.getCurrentUrl().toLowerCase().trim());
                        failedTestCases.writeToNamedFile("Please check: User is NOT redirected to an affilinet page : "+webDriver.getCurrentUrl().toLowerCase().trim(),"FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 50.1","FailAndReview");
                        ChangeCheckBox.adjustStyle(true,"nope",BecomeAffilinetPartner);
                    }
                    webDriver.switchTo().window(tabs.get(1)).close();
                    webDriver.switchTo().window(tabs.get(0));
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",BecomeAffilinetPartner);
                    webDriver.navigate().to(backupURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Become Affilinet Partner\"");
                    failedTestCases.writeToNamedFile(infoMessage,"Couldn't detect \"Become Affilinet Partner\" link","FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 50.1","FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",BecomeAffilinetPartner);
                webDriver.navigate().to(backupURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage,"Please check: Couldn't navigate to Affilinet Partner page!","FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 50.1","FailAndReview");

                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",BecomeAffilinetPartner);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage,"Please check Affilinet Partner page: Browser not responding","FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 50.1","FailAndReview");

            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");
    }
    public void checkingBecomeTradeTrackerPartner(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox BecomeTradeTrackerPartner, Text statusInfo, TextField inputAffiliateProgramURL, TextField backupURL, Properties Homepage){
        final String infoMessage = BecomeTradeTrackerPartner.getText();
        ChangeCheckBox.adjustStyle(false,"progress",BecomeTradeTrackerPartner);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputAffiliateProgramURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("page.main.footer.box"))).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("affiliatepage.become.tradetracker"))));
                    List<WebElement> allAffiliateButtons = webDriver.findElementsByXPath(Homepage.getProperty("affiliatepage.become.tradetracker"));
                    allAffiliateButtons.get(1).click();
                    tabs = new ArrayList<>(webDriver.getWindowHandles());
                    webDriver.switchTo().window(tabs.get(1));
                    if (webDriver.getCurrentUrl().contains("tradetracker")){
                        report.writeToFile(infoMessage, "User is redirected to a functioning tradetracker page : "+webDriver.getCurrentUrl().toLowerCase().trim());
                        ChangeCheckBox.adjustStyle(true,"complete",BecomeTradeTrackerPartner);
                    }else{
                        report.writeToFile(infoMessage, "User is redirected to a NOT tradetracker page : "+webDriver.getCurrentUrl().toLowerCase().trim());
                        ChangeCheckBox.adjustStyle(true,"nope",BecomeTradeTrackerPartner);
                        failedTestCases.writeToNamedFile(infoMessage,"Please check: User is redirected to a non-functioning tradetracker page : "+webDriver.getCurrentUrl().toLowerCase().trim(),"FailAndReview");
                        failedTestCases.writeToNamedFile("================================= TC 50.2","FailAndReview");
                    }
                    webDriver.switchTo().window(tabs.get(1)).close();
                    webDriver.switchTo().window(tabs.get(0));
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",BecomeTradeTrackerPartner);
                    webDriver.navigate().to(backupURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Become TradeTracker  Partner\"");
                    failedTestCases.writeToNamedFile(infoMessage,"Please check: Couldn't detect \"Become TradeTracker  Partner\"","FailAndReview");
                    failedTestCases.writeToNamedFile("================================= TC 50.2","FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",BecomeTradeTrackerPartner);
                webDriver.navigate().to(backupURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't navigate to Become Trade Tracker Partner page!", "FailAndReview");
                failedTestCases.writeToNamedFile("================================= TC 50.2","FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",BecomeTradeTrackerPartner);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage,"Please check Trade Tracker Partner Page! Browser not responding","FailAndReview");
            failedTestCases.writeToNamedFile("================================= TC 50.2","FailAndReview");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");

    }
}
