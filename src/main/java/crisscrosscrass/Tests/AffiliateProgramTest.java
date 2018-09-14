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
        final String infoMessage = "Checking Become Affilinet Partner";
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
                    //click on Partner Tab
                    report.writeToFile("");
                    report.writeToFile(infoMessage, "Complete");
                    ChangeCheckBox.adjustStyle(true,"complete",BecomeAffilinetPartner);
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",BecomeAffilinetPartner);
                    webDriver.navigate().to(inputAffiliateProgramURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Feed Providers\"");
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
}
