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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AffiliateProgramTest {
    final static Logger logger = Logger.getLogger(AffiliateProgramTest.class);

    public void checkingBecomeAffilinetPartner(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox BecomeAffilinetPartner, Text statusInfo, TextField inputAffiliateProgramURL, Properties Homepage){
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
                        ChangeCheckBox.adjustStyle(true,"nope",BecomeAffilinetPartner);
                    }
                    webDriver.switchTo().window(tabs.get(1)).close();
                    webDriver.switchTo().window(tabs.get(0));
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",BecomeAffilinetPartner);
                    webDriver.navigate().to(inputAffiliateProgramURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Become Affilinet Partner\"");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",BecomeAffilinetPartner);
                webDriver.navigate().to(inputAffiliateProgramURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",BecomeAffilinetPartner);
            webDriver.navigate().to(inputAffiliateProgramURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
    public void checkingBecomeTradeTrackerPartner(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox BecomeTradeTrackerPartner, Text statusInfo, TextField inputAffiliateProgramURL, Properties Homepage){
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
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("affiliatepage.become.buttons"))));
                    List<WebElement> allAffiliateButtons = webDriver.findElementsByXPath(Homepage.getProperty("affiliatepage.become.buttons"));
                    allAffiliateButtons.get(1).click();
                    tabs = new ArrayList<>(webDriver.getWindowHandles());
                    webDriver.switchTo().window(tabs.get(1));
                    if (webDriver.getCurrentUrl().contains("tradetracker")){
                        report.writeToFile(infoMessage, "User is redirected to a functioning tradetracker page : "+webDriver.getCurrentUrl().toLowerCase().trim());
                        ChangeCheckBox.adjustStyle(true,"complete",BecomeTradeTrackerPartner);
                    }else{
                        report.writeToFile(infoMessage, "User is redirected to a NOT tradetracker page : "+webDriver.getCurrentUrl().toLowerCase().trim());
                        ChangeCheckBox.adjustStyle(true,"nope",BecomeTradeTrackerPartner);
                    }
                    webDriver.switchTo().window(tabs.get(1)).close();
                    webDriver.switchTo().window(tabs.get(0));
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",BecomeTradeTrackerPartner);
                    webDriver.navigate().to(inputAffiliateProgramURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Become TradeTracker  Partner\"");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",BecomeTradeTrackerPartner);
                webDriver.navigate().to(inputAffiliateProgramURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",BecomeTradeTrackerPartner);
            webDriver.navigate().to(inputAffiliateProgramURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
}
