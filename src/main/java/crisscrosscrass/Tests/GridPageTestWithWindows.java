package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
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

    public void pagingWithWindowsForward(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox searchBoxInBrandFilter, TextField inputGridPageURLWithWindows,  Text statusInfo, TextField inputSearch, TextField inputEmailAdress, String xpathPattern1, String xpathPattern2, Properties Homepage, boolean isSuccessful, boolean isAvailable){
        Platform.runLater(() -> {
            searchBoxInBrandFilter.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking GridPage with Windows Paging Forward...");
        });


        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputGridPageURLWithWindows.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0){
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                        Platform.runLater(() -> {
                            searchBoxInBrandFilter.setStyle("-fx-background-color: #CCFF99");
                            searchBoxInBrandFilter.setSelected(true);
                        });
                        report.writeToFile("Checking GridPage with Windows Paging Forward: ", "Successful! Redirected to a functioning page!");

                    }else {
                        Platform.runLater(() -> {
                            searchBoxInBrandFilter.setStyle("-fx-background-color: #FF0000");
                            searchBoxInBrandFilter.setSelected(true);
                        });
                        report.writeToFile("Checking GridPage with Windows Paging Forward: ", "Not successful! URL stays the same!");
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorPagingWindows.png");
                        if (isSuccessful){
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                        }
                    }
                }catch (Exception gridPageIssue){
                    Platform.runLater(() -> {
                        searchBoxInBrandFilter.setStyle("-fx-background-color: #FF0000");
                        searchBoxInBrandFilter.setSelected(true);
                    });
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorPagingWindows.png");
                    if (isSuccessful){
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile("Checking GridPage with Windows Paging Forward: ", "Couldn't find any Windows");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                Platform.runLater(() -> {
                    searchBoxInBrandFilter.setStyle("-fx-background-color: #FF0000");
                    searchBoxInBrandFilter.setSelected(true);
                });
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile("Checking GridPage with Windows Paging Forward: ", "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            Platform.runLater(() -> {
                searchBoxInBrandFilter.setStyle("-fx-background-color: #FF0000");
                searchBoxInBrandFilter.setSelected(true);
            });
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile("Checking GridPage with Windows Paging Forward: ", "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
}
