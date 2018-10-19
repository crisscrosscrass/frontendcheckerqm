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

public class ImageGroupingPageTest {
    Report failedTestCases = new Report();


    public void checkingImageGroupingClickOut(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox ImageGroupingClickOut, Text statusInfo, TextField inputGridPageURL, Properties Homepage){
        final String infoMessage = ImageGroupingClickOut.getText();
        failedTestCases.writeToNamedFile("CHECKING IMAGE GROUPING PAGE", "FailAndReview");
        ChangeCheckBox.adjustStyle(false,"progress",ImageGroupingClickOut);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
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
                    wait.until(ExpectedConditions.elementToBeClickable((By.xpath(Homepage.getProperty("page.items.grouping")))));
                    List<WebElement> ImageGroupingElements = webDriver.findElementsByXPath(Homepage.getProperty("page.items.grouping"));
                    Point hoverItem = ImageGroupingElements.get(0).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    ImageGroupingElements.get(0).click();

                    try{
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("imagegrouping.page.toshop.main"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("imagegrouping.page.toshop.variant"))));
                        webDriver.findElementByXPath(Homepage.getProperty("imagegrouping.page.toshop.main")).click();
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,100)");
                        }
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,-100)");
                        }
                        tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(0));
                        List<WebElement> ProductsVariant = webDriver.findElementsByXPath(Homepage.getProperty("imagegrouping.page.toshop.variant"));
                        ProductsVariant.get(0).click();
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,100)");
                        }
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,-100)");
                        }
                        tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(2));
                        final String urlComparison = webDriver.getCurrentUrl();
                        webDriver.switchTo().window(tabs.get(1));
                        if (webDriver.getCurrentUrl().contains(urlComparison)){
                            report.writeToFile(infoMessage, "Successful!");
                            ChangeCheckBox.adjustStyle(true,"complete",ImageGroupingClickOut);
                        }else {
                            report.writeToFile(infoMessage, "Not successful!");
                            failedTestCases.writeToNamedFile(infoMessage, "Please check Image Grouping Page: clickout not successful!", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 30","FailAndReview");
                            ChangeCheckBox.adjustStyle(true,"nope",ImageGroupingClickOut);
                        }
                        webDriver.switchTo().window(tabs.get(2)).close();
                        webDriver.switchTo().window(tabs.get(1)).close();
                        webDriver.switchTo().window(tabs.get(0));
                        webDriver.navigate().to(inputGridPageURL.getText().trim());

                    }catch (Exception noProduktVariants){
                        ChangeCheckBox.adjustStyle(true,"nope",ImageGroupingClickOut);
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "DetailPageImageGrouping.png");
                        if (isSuccessful){
                            report.writeToFile("Checking Image Grouping Click Out: ", "Screenshot successful!");
                            failedTestCases.writeToNamedFile("For more information about the image grouping error, see DetailPageImageGrouping", "Screenshot successful!", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 30","FailAndReview");
                        }else {
                            report.writeToFile("Checking Image Grouping Click Out: ", "Screenshot not successful!");
                            failedTestCases.writeToNamedFile("For more information about the Image Grouping error, see DetailPageImageGrouping", "Screenshot not successful!", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 30","FailAndReview");
                        }
                        webDriver.navigate().to(inputGridPageURL.getText().trim());
                        report.writeToFile(infoMessage, "Couldn't detect Product Variants in Image Grouping");
                        failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't detect Product Variants in Image Grouping", "FailAndReview");
                        noProduktVariants.printStackTrace();
                        failedTestCases.writeToNamedFile("=================================TC 30","FailAndReview");
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",ImageGroupingClickOut);
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "DetailPageImageGrouping.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Image Grouping Click Out: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("For more information about the image grouping error error, see DetailPageImageGrouping", "Screenshot successful!", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 30","FailAndReview");
                    }else {
                        report.writeToFile("Checking Image Grouping Click Out: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("For more information about the image grouping error error, see DetailPageImageGrouping", "Screenshot not successful!", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 30","FailAndReview");
                    }
                    webDriver.navigate().to(inputGridPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect Image Grouping Icon for Detail Grouping Page");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check Image Grouping Page: Couldn't detect Image Grouping Icon for Detail Page", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 30","FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",ImageGroupingClickOut);
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check Image Grouping Page: Couldn't navigate to requested Site!", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 30","FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",ImageGroupingClickOut);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Image Grouping Page: Browser not responding!", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 30","FailAndReview");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }

    public void checkingDetailPageOfOffer(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox DetailPageOfOffer, Text statusInfo, TextField inputGridPageURL, Properties Homepage){
        final String infoMessage = DetailPageOfOffer.getText();
        ChangeCheckBox.adjustStyle(false,"progress",DetailPageOfOffer);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
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
                    wait.until(ExpectedConditions.elementToBeClickable((By.xpath(Homepage.getProperty("page.items.grouping")))));
                    List<WebElement> ImageGroupingElements = webDriver.findElementsByXPath(Homepage.getProperty("page.items.grouping"));
                    Point hoverItem = ImageGroupingElements.get(0).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    ImageGroupingElements.get(0).click();

                    try{
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("imagegrouping.page.toshop.main"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("imagegrouping.page.toshop.variant"))));
                        List<WebElement> productVariantsInfoIcons = webDriver.findElementsByXPath(Homepage.getProperty("imagegrouping.page.infoicon.variant"));
                        productVariantsInfoIcons.get(0).click();
                        if (webDriver.getCurrentUrl().contains("detail")){
                            report.writeToFile(infoMessage, "Successful!");
                            ChangeCheckBox.adjustStyle(true,"complete",DetailPageOfOffer);
                        }else {
                            report.writeToFile(infoMessage, "Not successful! Unable to detect Pattern in URL");
                            failedTestCases.writeToNamedFile(infoMessage, "Please check: Detail page of an clicked on item seems not to be working on a Image grouping Page", "FailAndReview");
                            failedTestCases.writeToNamedFile("================================= TC 31","FailAndReview");
                            ChangeCheckBox.adjustStyle(true,"nope",DetailPageOfOffer);
                        }
                        webDriver.navigate().to(inputGridPageURL.getText().trim());
                    }catch (Exception noProduktVariants){
                        ChangeCheckBox.adjustStyle(true,"nope",DetailPageOfOffer);
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "DetailPageImageGroupingOffer.png");
                        if (isSuccessful){
                            report.writeToFile("Checking Image Grouping Detail Page of Offer: ", "Screenshot successful!");
                            failedTestCases.writeToNamedFile("For more information about the image grouping Offer error, see DetailPageImageGroupingOffer", "Screenshot successful!", "FailAndReview");
                            failedTestCases.writeToNamedFile("================================= TC 31","FailAndReview");
                        }else {
                            report.writeToFile("Checking Image Grouping Detail Page of Offer: ", "Screenshot not successful!");
                            failedTestCases.writeToNamedFile("For more information about the image grouping Offer error, see DetailPageImageGroupingOffer", "Screenshot not successful!", "FailAndReview");
                            failedTestCases.writeToNamedFile("================================= TC 31","FailAndReview");
                        }
                        webDriver.navigate().to(inputGridPageURL.getText().trim());
                        report.writeToFile(infoMessage, "Couldn't detect Product Variants in Image Grouping");
                        failedTestCases.writeToNamedFile(infoMessage, "Please check Image Grouping Page: Couldn't detect Product Variants in Image Grouping", "FailAndReview");        failedTestCases.writeToNamedFile("================================= TC 31","FailAndReview");
                        failedTestCases.writeToNamedFile("================================= TC 31","FailAndReview");
                        noProduktVariants.printStackTrace();
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",DetailPageOfOffer);
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "DetailPageImageGroupingOffer.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Image Grouping Detail Page of Offer: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("For more information about the image grouping Offer error, see DetailPageImageGroupingOffer", "Screenshot successful!", "FailAndReview");
                        failedTestCases.writeToNamedFile("================================= TC 31","FailAndReview");
                    }else {
                        report.writeToFile("Checking Image Grouping Detail Page of Offer: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("For more information about the image grouping Offer error, see DetailPageImageGroupingOffer", "Screenshot not successful!", "FailAndReview");
                        failedTestCases.writeToNamedFile("================================= TC 31","FailAndReview");
                    }
                    webDriver.navigate().to(inputGridPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect Image Grouping Icon for Detail Grouping Page");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check Image Grouping Page: Couldn't detect Image Grouping Icon for Detail Grouping Page", "FailAndReview");
                    failedTestCases.writeToNamedFile("================================= TC 31","FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",DetailPageOfOffer);
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't navigate to  Image Grouping Page!", "FailAndReview");
                failedTestCases.writeToNamedFile("================================= TC 31","FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",DetailPageOfOffer);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Image Grouping Page: Browser not responding!", "FailAndReview");
            failedTestCases.writeToNamedFile("================================= TC 31","FailAndReview");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
}