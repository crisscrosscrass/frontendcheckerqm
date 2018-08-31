package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tasks.ChangeCheckBox;
import crisscrosscrass.Tasks.Report;
import crisscrosscrass.Tasks.ScreenshotViaWebDriver;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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

public class FavoritePageTest {
    public void PersonalListTest(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox PersonalList, Text statusInfo, TextField inputSearch, Properties Homepage){
        final String infoMessage = "Checking Personal List";
        ChangeCheckBox.adjustStyle(false,"progress",PersonalList);
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
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.main.myaccount"))));
                    webDriver.findElementByXPath(Homepage.getProperty("page.main.myaccount")).click();
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.myaccount.button.toLogin"))));
                    webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.button.toLogin")).click();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='box-headline']/div[2]/span/span/span")));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.emailInput"))));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.passwordInput"))));

                    // christopher.eckardt@visual-meta.com
                    // gtpyf12x
                    WebElement element = webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.emailInput"));
                    element.sendKeys("christopher.eckardt@visual-meta.com");
                    element = webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.passwordInput"));
                    element.sendKeys("blabla");
                    element.submit();


                    ChangeCheckBox.adjustStyle(true,"complete",PersonalList);
                    report.writeToFile(infoMessage, "Complete");



                }catch (Exception accountPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",PersonalList);
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect item for Detail Page");
                    accountPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",PersonalList);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",PersonalList);
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
}
