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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BrandPageTest {
    Report failedTestCases = new Report();

    public void checkingBrandsWithoutLogo(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox brandsWithoutLogo, TextField inputBrandPageOverview, Text statusInfo, TextField inputSearch, Properties Homepage){
        failedTestCases.writeToNamedFile("CHECKING BRANDS PAGE", "FailAndReview");
        final String infoMessage = brandsWithoutLogo.getText();
        ChangeCheckBox.adjustStyle(false,"progress",brandsWithoutLogo);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputBrandPageOverview.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    boolean isAvailable = webDriver.findElementByXPath(Homepage.getProperty("brandpage.quicklinks")) != null;
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("brandpage.quicklinks"))));
                    List<WebElement> quickMenuLetters = webDriver.findElementsByXPath(Homepage.getProperty("brandpage.quicklinks"));
                    ArrayList allCollectedLinks = new ArrayList();
                    for (WebElement FillIns : quickMenuLetters){
                        allCollectedLinks.add(FillIns.getAttribute("href").trim().toLowerCase());
                    }
                    for (int i = 0 ; i < allCollectedLinks.size() ; i++){
                        ((JavascriptExecutor)webDriver).executeScript("window.open()");
                        tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(1)); //switches to new tab
                        webDriver.get(allCollectedLinks.get(i).toString());
                        report.writeToFile("Missing Logos on page " + webDriver.getCurrentUrl() + " :");
                        failedTestCases.writeToNamedFile("Missing Logos on page " + webDriver.getCurrentUrl() + " :", "FailAndReview");


                        if(webDriver.findElements(By.xpath(Homepage.getProperty("brandpage.boxwrapper.top"))).size() > 0){
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("brandpage.boxwrapper.top"))));
                            List<WebElement> boxWrapperTop = webDriver.findElementsByXPath(Homepage.getProperty("brandpage.boxwrapper.top"));
                            for (WebElement quickMenuItem : boxWrapperTop){
                                report.writeToFile(quickMenuItem.getText());
                                failedTestCases.writeToNamedFile(quickMenuItem.getText(), "FailAndReview");
                            }
                        }
                        if(webDriver.findElements(By.xpath(Homepage.getProperty("brandpage.boxwrapper.bottom"))).size() > 0){
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("brandpage.boxwrapper.bottom"))));
                            List<WebElement> boxWrapperBottom = webDriver.findElementsByXPath(Homepage.getProperty("brandpage.boxwrapper.bottom"));
                            for (WebElement quickMenuItem : boxWrapperBottom){
                                report.writeToFile(quickMenuItem.getText());
                                failedTestCases.writeToNamedFile(quickMenuItem.getText(), "FailAndReview");
                            }
                        }
                        if(webDriver.findElements(By.xpath(Homepage.getProperty("brandpage.brandbox"))).size() > 0){
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("brandpage.brandbox"))));
                            List<WebElement> brandboxes = webDriver.findElementsByXPath(Homepage.getProperty("brandpage.brandbox"));
                            for (WebElement quickMenuItem : brandboxes){
                                report.writeToFile(quickMenuItem.getText());
                                failedTestCases.writeToNamedFile(quickMenuItem.getText(), "FailAndReview");
                            }
                        }
                        webDriver.switchTo().window(tabs.get(1)).close();
                        webDriver.switchTo().window(tabs.get(0));
                    }
                    ChangeCheckBox.adjustStyle(true,"complete",brandsWithoutLogo);
                    report.writeToFile(infoMessage, "Successful! Redirected to a functioning page!");
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",brandsWithoutLogo);
                    boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "ErrorCheckingBrandPageOverview.png");
                    if (isSuccessful){
                        report.writeToFile("BrandPage Error Screenshot: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("BrandPage Error Screenshot", "Please check functionality of Brand Page. For reference, see ErrorCheckingBrandPageOverview", "FailAndReview");
                    }else {
                        report.writeToFile("BrandPage Error Screenshot: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("BrandPage Error Screenshot", "Screenshot not successful!", "FailAndReview");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't find any QuickMenu Elements");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check Brand Page: could not find menu element", "FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",brandsWithoutLogo);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check Brand Page: could not navigate to requested site", "FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",brandsWithoutLogo);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Brand Page: browser not responding", "FailAndReview");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================TC 20","FailAndReview");

    }

}
