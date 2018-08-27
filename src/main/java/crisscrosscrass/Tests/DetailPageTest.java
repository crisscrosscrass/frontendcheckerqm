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
import java.util.List;
import java.util.Properties;

public class DetailPageTest {

    public void SwitchTabsinDetailPage(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox SwitchTabsInDetailPage, TextField inputLucenePage, Text statusInfo, TextField inputGridPageURL, Properties Homepage){
        Platform.runLater(() -> {
            SwitchTabsInDetailPage.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Detail Page Tab Switch...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            boolean isSuccessful = false;
            try {
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0){
                        report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                    }else {
                        System.out.println("No Window Element!");
                    }
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.info.icon"))));
                    List<WebElement> ItemsIconSymbol = webDriver.findElementsByXPath(Homepage.getProperty("page.items.info.icon"));
                    ItemsIconSymbol.get(0).click();
                    try{
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("detailpage.tabs"))));
                        Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("detailpage.tabs"))).getLocation();
                        ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                        ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                        for (int i = 0; i < 1; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,-400)");
                        }
                        List<WebElement> detailPageTabs = webDriver.findElementsByXPath(Homepage.getProperty("detailpage.tabs"));
                        detailPageTabs.get(1).click();

                        //wait for new Items in Second Tab
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='moreProducts-style']/div[3]/div[1]/div/div")));

                        Platform.runLater(() -> {
                            SwitchTabsInDetailPage.setStyle("-fx-background-color: #CCFF99");
                            SwitchTabsInDetailPage.setSelected(true);
                        });
                        report.writeToFile("Checking Detail Page Tab Switch: ", "Complete!");

                    }catch (Exception noTabDetailPage){
                        Platform.runLater(() -> {
                            SwitchTabsInDetailPage.setStyle("-fx-background-color: #FF0000");
                            SwitchTabsInDetailPage.setSelected(true);
                        });
                        webDriver.navigate().to(inputGridPageURL.getText().trim());
                        report.writeToFile("Checking Detail Page Tab Switch: ", "Couldn't detect Tabs for Detail Page");
                        noTabDetailPage.printStackTrace();
                    }


                }catch (Exception gridPageIssue){
                    Platform.runLater(() -> {
                        SwitchTabsInDetailPage.setStyle("-fx-background-color: #FF0000");
                        SwitchTabsInDetailPage.setSelected(true);
                    });
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "DetailPage.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Detail Page Tab Switch: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("Checking Detail Page Tab Switch: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputGridPageURL.getText().trim());
                    report.writeToFile("Checking Detail Page Tab Switch: ", "Couldn't detect item for Detail Page");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                Platform.runLater(() -> {
                    SwitchTabsInDetailPage.setStyle("-fx-background-color: #FF0000");
                    SwitchTabsInDetailPage.setSelected(true);
                });
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                report.writeToFile("Checking Detail Page Tab Switch: ", "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            Platform.runLater(() -> {
                SwitchTabsInDetailPage.setStyle("-fx-background-color: #FF0000");
                SwitchTabsInDetailPage.setSelected(true);
            });
            webDriver.navigate().to(inputGridPageURL.getText().trim());
            report.writeToFile("Checking Detail Page Tab Switch: ", "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }

    public void SimilarProductClickOut(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox SimilarProductsClickOut, TextField inputLucenePage, Text statusInfo, TextField inputGridPageURL, Properties Homepage){
        Platform.runLater(() -> {
            SimilarProductsClickOut.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Detail Page Similar Product...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            boolean isSuccessful = false;
            try {
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0){
                        report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                    }else {
                        System.out.println("No Window Element!");
                    }
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.info.icon"))));
                    List<WebElement> ItemsIconSymbol = webDriver.findElementsByXPath(Homepage.getProperty("page.items.info.icon"));
                    ItemsIconSymbol.get(0).click();
                    try{
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("detailpage.tabs"))));
                        Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("detailpage.tabs"))).getLocation();
                        ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                        ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                        for (int i = 0; i < 1; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,-400)");
                        }


                        Platform.runLater(() -> {
                            SimilarProductsClickOut.setStyle("-fx-background-color: #CCFF99");
                            SimilarProductsClickOut.setSelected(true);
                        });
                        report.writeToFile("Checking Detail Page Similar Product: ", "Complete!");

                    }catch (Exception noTabDetailPage){
                        Platform.runLater(() -> {
                            SimilarProductsClickOut.setStyle("-fx-background-color: #FF0000");
                            SimilarProductsClickOut.setSelected(true);
                        });
                        webDriver.navigate().to(inputGridPageURL.getText().trim());
                        report.writeToFile("Checking Detail Page Tab Switch: ", "Couldn't detect Tabs for Detail Page");
                        noTabDetailPage.printStackTrace();
                    }


                }catch (Exception gridPageIssue){
                    Platform.runLater(() -> {
                        SimilarProductsClickOut.setStyle("-fx-background-color: #FF0000");
                        SimilarProductsClickOut.setSelected(true);
                    });
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "DetailPage.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Detail Page Tab Switch: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("Checking Detail Page Tab Switch: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputGridPageURL.getText().trim());
                    report.writeToFile("Checking Detail Page Tab Switch: ", "Couldn't detect item for Detail Page");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                Platform.runLater(() -> {
                    SimilarProductsClickOut.setStyle("-fx-background-color: #FF0000");
                    SimilarProductsClickOut.setSelected(true);
                });
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                report.writeToFile("Checking Detail Page Tab Switch: ", "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            Platform.runLater(() -> {
                SimilarProductsClickOut.setStyle("-fx-background-color: #FF0000");
                SimilarProductsClickOut.setSelected(true);
            });
            webDriver.navigate().to(inputGridPageURL.getText().trim());
            report.writeToFile("Checking Detail Page Tab Switch: ", "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
}
