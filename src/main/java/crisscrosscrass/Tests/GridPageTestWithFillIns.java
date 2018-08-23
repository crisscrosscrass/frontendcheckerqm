package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tasks.Report;
import crisscrosscrass.Tasks.ScreenshotViaWebDriver;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GridPageTestWithFillIns {

    public void ShowAllFillInPage(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox showAllFillInPage, TextField inputGridPageURLWithFillIns, Text statusInfo, TextField inputSearch, TextField inputEmailAdress, String xpathPattern1, String xpathPattern2, Properties Homepage, boolean isSuccessful, boolean isAvailable){
        Platform.runLater(() -> {
            showAllFillInPage.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking GridPage with Fill Ins...");
        });


        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputGridPageURLWithFillIns.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    isAvailable = webDriver.findElementByXPath(Homepage.getProperty("page.grid.fillInMore")) != null;
                    List<WebElement> FillInsMore = webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.fillInMore")));
                    List<WebElement> FillInsAll = webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.fillInAll")));
                    ArrayList LinksMore = new ArrayList();
                    ArrayList LinksAll = new ArrayList();

                    for (WebElement FillIns : FillInsMore){
                        LinksMore.add(FillIns.getAttribute("href").trim().toLowerCase());
                    }
                    for (WebElement FillAll : FillInsAll){
                        LinksAll.add(FillAll.getAttribute("href").trim().toLowerCase());
                    }

                    for (int i = 0 ; i < FillInsAll.size(); i++){
                        ((JavascriptExecutor)webDriver).executeScript("window.open()");
                        tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(1)); //switches to new tab
                        webDriver.get(LinksMore.get(i).toString());
                        final String urlLocationBefore = webDriver.getCurrentUrl();

                        ((JavascriptExecutor)webDriver).executeScript("window.open()");
                        tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(2)); //switches to new tab
                        webDriver.get(LinksAll.get(i).toString());

                        if ( urlLocationBefore.contains(webDriver.getCurrentUrl()) ){
                            report.writeToFile("Checking Fill Ins: ", "Tested URL \""+ urlLocationBefore +"\" successfully!");
                        }else {
                            report.writeToFile("Checking Fill Ins: ", "Tested URL \""+ urlLocationBefore +"\" NOT successfully!");
                        }

                        webDriver.switchTo().window(tabs.get(2)).close();
                        webDriver.switchTo().window(tabs.get(1)).close();
                        webDriver.switchTo().window(tabs.get(0));
                    }
                    Platform.runLater(() -> {
                        showAllFillInPage.setStyle("-fx-background-color: #CCFF99");
                        showAllFillInPage.setSelected(true);
                    });
                    report.writeToFile("Checking GridPage with Fill Ins: ", "Complete!");
                }catch (Exception gridPageIssue){
                    Platform.runLater(() -> {
                        showAllFillInPage.setStyle("-fx-background-color: #FF0000");
                        showAllFillInPage.setSelected(true);
                    });
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorPagingWindows.png");
                    if (isSuccessful){
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile("Checking GridPage with Fill Ins: ", "Could find any FillIns-Boxes");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                Platform.runLater(() -> {
                    showAllFillInPage.setStyle("-fx-background-color: #FF0000");
                    showAllFillInPage.setSelected(true);
                });
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile("Checking GridPage with Fill Ins: ", "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            Platform.runLater(() -> {
                showAllFillInPage.setStyle("-fx-background-color: #FF0000");
                showAllFillInPage.setSelected(true);
            });
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile("Checking GridPage with Fill Ins: ", "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }

}
