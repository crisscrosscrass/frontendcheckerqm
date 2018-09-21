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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Properties;

public class MainMenuOnHomePageTest {
    final static Logger logger = Logger.getLogger(MainMenuOnHomePageTest.class);

    public void checkingGoToTopButton(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox GoToTopButton, Text statusInfo, TextField inputPartnerShopPageURL, Properties Homepage){
        final String infoMessage = "Checking Go to Top Button";
        ChangeCheckBox.adjustStyle(false,"progress",GoToTopButton);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });

        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.main.footer.box"))));
                    Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("page.main.footer.box"))).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.main.totopbutton"))));
                    webDriver.findElementByXPath(Homepage.getProperty("page.main.totopbutton")).click();
                    try {
                        webDriver.findElementByXPath(Homepage.getProperty("partnerpage.shops.h3")).click();
                        ChangeCheckBox.adjustStyle(true,"complete",GoToTopButton);
                        report.writeToFile(infoMessage, "Successful, Initial banner (H3) is on user's view");
                    }catch (Exception noH3InViewPort){
                        ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
                        report.writeToFile(infoMessage, "Not successful, Initial banner (H3) is NOT on user's view");
                        noH3InViewPort.printStackTrace();
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
                    webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Go To Top\" Button");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
                webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
            webDriver.navigate().to(inputPartnerShopPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
}
