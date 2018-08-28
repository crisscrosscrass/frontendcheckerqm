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

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("detailpage.similar.products.header"))));
                        Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("detailpage.similar.products.header"))).getLocation();
                        ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                        ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                        for (int i = 0; i < 1; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,-300)");
                        }
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.links"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.links"))));
                        List<WebElement> ItemsOnGridPage = webDriver.findElementsByXPath(Homepage.getProperty("page.items.links"));
                        List<WebElement> ShopNamesfromItemsOnGridPage = webDriver.findElementsByXPath(Homepage.getProperty("page.items.shopnames"));
                        //Shopname is empty
                        final String ShopnameFromFirstItem = ShopNamesfromItemsOnGridPage.get(0).getAttribute("textContent").trim();
                        ItemsOnGridPage.get(0).click();

                        tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(1));
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,100)");
                        }
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,-100)");
                        }
                        if (webDriver.getCurrentUrl().contains(ShopnameFromFirstItem)){
                            Platform.runLater(() -> {
                                SimilarProductsClickOut.setStyle("-fx-background-color: #CCFF99");
                                SimilarProductsClickOut.setSelected(true);
                            });
                            report.writeToFile("Checking Detail Page Similar Product: ", "Complete!");
                        }else{
                            Platform.runLater(() -> {
                                SimilarProductsClickOut.setStyle("-fx-background-color: #FF0000");
                                SimilarProductsClickOut.setSelected(true);
                            });
                            report.writeToFile("Checking Detail Page Similar Product: ", "Unable to complete! Shopname from GridPage is not Clickout Item");
                        }

                        webDriver.switchTo().window(tabs.get(1)).close();
                        webDriver.switchTo().window(tabs.get(0));



                    }catch (Exception noSimilarProduct){
                        Platform.runLater(() -> {
                            SimilarProductsClickOut.setStyle("-fx-background-color: #FF0000");
                            SimilarProductsClickOut.setSelected(true);
                        });
                        webDriver.navigate().to(inputGridPageURL.getText().trim());
                        report.writeToFile("Checking Detail Page Similar Product: ", "Couldn't detect Similar Product for Detail Page");
                        noSimilarProduct.printStackTrace();
                    }


                }catch (Exception gridPageIssue){
                    Platform.runLater(() -> {
                        SimilarProductsClickOut.setStyle("-fx-background-color: #FF0000");
                        SimilarProductsClickOut.setSelected(true);
                    });
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "DetailPage.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Detail Page Similar Product: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("Checking Detail Page Similar Product: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputGridPageURL.getText().trim());
                    report.writeToFile("Checking Detail Page Similar Product: ", "Couldn't detect item for Detail Page");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                Platform.runLater(() -> {
                    SimilarProductsClickOut.setStyle("-fx-background-color: #FF0000");
                    SimilarProductsClickOut.setSelected(true);
                });
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                report.writeToFile("Checking Detail Page Similar Product: ", "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            Platform.runLater(() -> {
                SimilarProductsClickOut.setStyle("-fx-background-color: #FF0000");
                SimilarProductsClickOut.setSelected(true);
            });
            webDriver.navigate().to(inputGridPageURL.getText().trim());
            report.writeToFile("Checking Detail Page Similar Product: ", "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }

    public void PagingForwardBackward(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox SimilarProductsClickOut, TextField inputLucenePage, Text statusInfo, TextField inputGridPageURL, Properties Homepage){
        Platform.runLater(() -> {
            SimilarProductsClickOut.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Detail Page Paging-Forward/Backward...");
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

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.pageNumbers"))));
                        Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("page.pageNumbers"))).getLocation();
                        ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                        ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                        for (int i = 0; i < 1; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,-300)");
                        }
                        webDriver.findElementByXPath(Homepage.getProperty("page.pageNumbers")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.price"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.previousPage.button"))));
                        if (webDriver.getCurrentUrl().contains("2")){
                            report.writeToFile("Checking Detail Page Paging-Forward:: ", "Successful! Found pattern in URL and Previous Page Button appeared!");
                        }else {
                            report.writeToFile("Checking Detail Page Paging-Forward:: ", "Not Successful! User is not redirected");
                            isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"DetailPageErrorPagingForward.png");
                            if (isSuccessful){
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                            }else {
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                            }
                        }

                        webDriver.findElementByXPath(Homepage.getProperty("page.previousPage.button")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.price"))));
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.previousPage.button"))));
                        if (!webDriver.getCurrentUrl().contains("-2")){
                            report.writeToFile("Checking Detail Page Paging-Backward:: ", "Successful! Found pattern in URL and Previous Page Button disappeared!");
                            Platform.runLater(() -> {
                                SimilarProductsClickOut.setStyle("-fx-background-color: #CCFF99");
                                SimilarProductsClickOut.setSelected(true);
                            });
                        }else {
                            report.writeToFile("Checking Detail Page Paging-Backward:: ", "Not Successful! User is not redirected");
                            isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"DetailPageErrorPagingBackward.png");
                            if (isSuccessful){
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                            }else {
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                            }
                            Platform.runLater(() -> {
                                SimilarProductsClickOut.setStyle("-fx-background-color: #FF0000");
                                SimilarProductsClickOut.setSelected(true);
                            });
                        }

                        report.writeToFile("Checking Detail Page Paging-Forward:", "Complete!");




                    }catch (Exception noTabDetailPage){
                        Platform.runLater(() -> {
                            SimilarProductsClickOut.setStyle("-fx-background-color: #FF0000");
                            SimilarProductsClickOut.setSelected(true);
                        });
                        webDriver.navigate().to(inputGridPageURL.getText().trim());
                        report.writeToFile("Checking Detail Page Paging-Forward/Backward: ", "Couldn't detect Paging Forward/Backward for Detail Page");
                        noTabDetailPage.printStackTrace();
                    }


                }catch (Exception gridPageIssue){
                    Platform.runLater(() -> {
                        SimilarProductsClickOut.setStyle("-fx-background-color: #FF0000");
                        SimilarProductsClickOut.setSelected(true);
                    });
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "DetailPage.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Detail Page Paging-Forward/Backward: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("Checking Detail Page Paging-Forward/Backward: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputGridPageURL.getText().trim());
                    report.writeToFile("Checking Detail Page Paging-Forward/Backward: ", "Couldn't detect item for Detail Page");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                Platform.runLater(() -> {
                    SimilarProductsClickOut.setStyle("-fx-background-color: #FF0000");
                    SimilarProductsClickOut.setSelected(true);
                });
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                report.writeToFile("Checking Detail Page Paging-Forward/Backward: ", "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            Platform.runLater(() -> {
                SimilarProductsClickOut.setStyle("-fx-background-color: #FF0000");
                SimilarProductsClickOut.setSelected(true);
            });
            webDriver.navigate().to(inputGridPageURL.getText().trim());
            report.writeToFile("Checking Detail Page Paging-Forward/Backward: ", "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
}
