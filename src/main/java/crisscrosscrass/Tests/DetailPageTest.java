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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DetailPageTest {

    public void checkingMultiselect(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox SwitchTabsInDetailPage, TextField inputLucenePage, Text statusInfo, TextField inputSearch, Properties Homepage){
        Platform.runLater(() -> {
            SwitchTabsInDetailPage.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Detail Page Multiselect Filters...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            boolean isSuccessful = false;
            try {
                webDriver.navigate().to(inputSearch.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    boolean isAvailable = webDriver.findElementById(Homepage.getProperty("page.search.bar")) != null;
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Homepage.getProperty("page.search.bar"))));
                    WebElement element = webDriver.findElement(By.id(Homepage.getProperty("page.search.bar")));
                    element.sendKeys(inputLucenePage.getText().trim());
                    element.submit();

                    if (webDriver.getCurrentUrl().contains("?q=") ){

                        try{
                            isAvailable = webDriver.findElementByXPath(Homepage.getProperty("lucenepage.sidebar.boxes")) != null;

                            List<WebElement> FilterBoxColors = webDriver.findElementsByXPath(Homepage.getProperty("lucenepage.sidebar.boxes.colors"));
                            int selectColorCounter = 0;

                            List<WebElement> FilterBoxColorsUnchecked = webDriver.findElementsByXPath(Homepage.getProperty("lucenepage.sidebar.boxes.colors.unchecked"));
                            if (FilterBoxColors.size() != 0){

                                while (FilterBoxColorsUnchecked.size() > 0 && selectColorCounter < 5){
                                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("lucenepage.sidebar.boxes.colors.unchecked"))));
                                    report.writeToFile("Select Color Filter "+(selectColorCounter+1)+": ", "Complete!");
                                    FilterBoxColorsUnchecked.get(0).click();
                                    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                                    FilterBoxColorsUnchecked = webDriver.findElementsByXPath(Homepage.getProperty("lucenepage.sidebar.boxes.colors.unchecked"));
                                    selectColorCounter++;
                                }

                                if (selectColorCounter >= 5){
                                    Platform.runLater(() -> {
                                        SwitchTabsInDetailPage.setStyle("-fx-background-color: #CCFF99");
                                        SwitchTabsInDetailPage.setSelected(true);
                                    });
                                    report.writeToFile("Checking Lucene Page Multiselect Filters: ", "Complete!");
                                }else {
                                    Platform.runLater(() -> {
                                        SwitchTabsInDetailPage.setStyle("-fx-background-color: #FF0000");
                                        SwitchTabsInDetailPage.setSelected(true);
                                    });
                                    report.writeToFile("Checking Lucene Page Multiselect Filters: ", "Not Complete! Couldn't select more than "+selectColorCounter+" color Filters");
                                }


                            }else {
                                Platform.runLater(() -> {
                                    SwitchTabsInDetailPage.setStyle("-fx-background-color: #CCFF99");
                                    SwitchTabsInDetailPage.setSelected(true);
                                });
                                isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "LucenePageCollapseFilters.png");
                                if (isSuccessful){
                                    report.writeToFile("Checking Lucene Page Multiselect Filters Error: ", "Screenshot successful!");
                                }else {
                                    report.writeToFile("Checking Lucene Page Multiselect Filters Error: ", "Screenshot not successful!");
                                }
                                report.writeToFile("Checking Lucene Page Multiselect Filters: ", "Unable to detect colors on Lucene Page");
                            }

                        }catch (Exception noSortingPossible){
                            Platform.runLater(() -> {
                                SwitchTabsInDetailPage.setStyle("-fx-background-color: #FF0000");
                                SwitchTabsInDetailPage.setSelected(true);
                            });
                            isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "LucenePageCollapseFilters.png");
                            if (isSuccessful){
                                report.writeToFile("Checking Lucene Page Multiselect Filters Error: ", "Screenshot successful!");
                            }else {
                                report.writeToFile("Checking Lucene Page Multiselect Filters Error: ", "Screenshot not successful!");
                            }
                            report.writeToFile("Checking Lucene Page Multiselect Filters: ", "Unable to complete! Couldn't detect Filter Boxes!");
                            noSortingPossible.printStackTrace();
                        }

                    }else{
                        Platform.runLater(() -> {
                            SwitchTabsInDetailPage.setStyle("-fx-background-color: #FF0000");
                            SwitchTabsInDetailPage.setSelected(true);
                        });
                        report.writeToFile("Checking Lucene Page Multiselect Filters: ", "Unable to Complete! Couldn't find Lucene Page");
                    }


                }catch (Exception gridPageIssue){
                    Platform.runLater(() -> {
                        SwitchTabsInDetailPage.setStyle("-fx-background-color: #FF0000");
                        SwitchTabsInDetailPage.setSelected(true);
                    });
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "GridPageErrorPagingWindows.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Lucene Page Multiselect Filters Error Screenshot: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("Checking Lucene Page Multiselect Filters Error Screenshot: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile("Checking Lucene Page Multiselect Filters: ", "Could find any Searchbar to enter Keyword for Lucene Page");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                Platform.runLater(() -> {
                    SwitchTabsInDetailPage.setStyle("-fx-background-color: #FF0000");
                    SwitchTabsInDetailPage.setSelected(true);
                });
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile("Checking Lucene Page Multiselect Filters: ", "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            Platform.runLater(() -> {
                SwitchTabsInDetailPage.setStyle("-fx-background-color: #FF0000");
                SwitchTabsInDetailPage.setSelected(true);
            });
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile("Checking Lucene Page Multiselect Filters: ", "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
}
