package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import com.sun.security.sasl.ntlm.FactoryImpl;
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
import java.util.Properties;

public class GridPageTestWithWindows {
    Report failedTestCases = new Report();

    public void checkingPagingWithWindowsForward(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox searchBoxInBrandFilter, TextField inputGridPageURLWithWindows, Text statusInfo, TextField inputSearch, TextField inputEmailAdress, String xpathPattern1, String xpathPattern2, Properties Homepage, boolean isSuccessful, boolean isAvailable){
        failedTestCases.writeToNamedFile("CHECKING GRID PAGE WITH WINDOWS PAGE", "FailAndReview");
        final String infoMessage = searchBoxInBrandFilter.getText();
        ChangeCheckBox.adjustStyle(false,"progress",searchBoxInBrandFilter);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputGridPageURLWithWindows.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0){
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                        ChangeCheckBox.adjustStyle(true,"complete",searchBoxInBrandFilter);
                        report.writeToFile(infoMessage, "Successful! Redirected to a functioning page!");
                    }else {
                        ChangeCheckBox.adjustStyle(true,"nope",searchBoxInBrandFilter);
                        report.writeToFile(infoMessage, "Not successful! URL stays the same!");
                        failedTestCases.writeToNamedFile(infoMessage, "Please Check: review paging functionality on a Grid page with Windows", "FailAndReview");
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorPagingWindows.png");
                        if (isSuccessful){
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                            failedTestCases.writeToNamedFile("See GridPageErrorPagingWindows for more information about the error","Screenshot successful!", "FailAndReview");
                        }else {
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                            failedTestCases.writeToNamedFile("See GridPageErrorPagingWindows for more information about the error","Screenshot not successful!", "FailAndReview");
                        }
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",searchBoxInBrandFilter);
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorPagingWindows.png");
                    if (isSuccessful){
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("See GridPageErrorPagingWindows for more information about the error","Screenshot successful!", "FailAndReview");
                    }else {
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("See GridPageErrorPagingWindows for more information about the error","Screenshot not successful!", "FailAndReview");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't find any Windows");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: could not find any windows on Grid Page with Windows", "FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",searchBoxInBrandFilter);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: could not navigate to Grid Page with Windows", "FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",searchBoxInBrandFilter);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Grid Page with Windows: Browser not responding", "FailAndReview");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");
    }
}
