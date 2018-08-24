package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tasks.Report;
import crisscrosscrass.Tasks.ScreenshotViaWebDriver;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BrandPageTest {

    public void pagingWithWindowsForward(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox brandsWithoutLogo, TextField inputBrandPageOverview, Text statusInfo, TextField inputSearch, Properties Homepage){
        Platform.runLater(() -> {
            brandsWithoutLogo.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Brand Page Overview...");
        });


        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputBrandPageOverview.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    boolean isAvailable = webDriver.findElementByXPath(Homepage.getProperty("brandpage.links")) != null;
                    List<WebElement> quickMenuLetters = webDriver.findElementsByXPath(Homepage.getProperty("brandpage.links"));
                    for (WebElement quickMenuItem : quickMenuLetters){
                        System.out.println(quickMenuItem.getText());
                    }



                    Platform.runLater(() -> {
                        brandsWithoutLogo.setStyle("-fx-background-color: #CCFF99");
                        brandsWithoutLogo.setSelected(true);
                    });
                    report.writeToFile("Checking Brand Page Overview: ", "Successful! Redirected to a functioning page!");
                }catch (Exception gridPageIssue){
                    Platform.runLater(() -> {
                        brandsWithoutLogo.setStyle("-fx-background-color: #FF0000");
                        brandsWithoutLogo.setSelected(true);
                    });
                    boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "ErrorCheckingBrandPageOverview.png");
                    if (isSuccessful){
                        report.writeToFile("BrandPage Error Screenshot: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("BrandPage Error Screenshot: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile("Checking Brand Page Overview: ", "Couldn't find any QuickMenu Elements");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                Platform.runLater(() -> {
                    brandsWithoutLogo.setStyle("-fx-background-color: #FF0000");
                    brandsWithoutLogo.setSelected(true);
                });
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile("Checking Brand Page Overview: ", "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            Platform.runLater(() -> {
                brandsWithoutLogo.setStyle("-fx-background-color: #FF0000");
                brandsWithoutLogo.setSelected(true);
            });
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile("Checking Brand Page Overview: ", "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }

}
