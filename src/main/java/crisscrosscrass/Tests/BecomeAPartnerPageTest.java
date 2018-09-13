package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tasks.ChangeCheckBox;
import crisscrosscrass.Tasks.Report;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Properties;

public class BecomeAPartnerPageTest {
    final static org.apache.log4j.Logger logger = Logger.getLogger(BecomeAPartnerPageTest.class);

    public void checkingRegisterButton(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox ShopLinkName, Text statusInfo, TextField inputBecomeAPartnerPageURL, Properties Homepage){
        final String infoMessage = "Checking Shop Link";
        ChangeCheckBox.adjustStyle(false,"progress",ShopLinkName);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });

        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    logger.info("Become a Partner is running!");
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",ShopLinkName);
                    webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Sorting\" Button");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",ShopLinkName);
                webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",ShopLinkName);
            webDriver.navigate().to(inputBecomeAPartnerPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
}
