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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GridPageTest {

    public void checkingSorting(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox sortingValuesOnGridPageHighToLow, TextField inputGridPageURL, Text statusInfo, TextField inputSearch, TextField inputEmailAdress, String xpathPattern1, String xpathPattern2, Properties Homepage, boolean isSuccessful, boolean isAvailable){

        Platform.runLater(() -> {
            sortingValuesOnGridPageHighToLow.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking GridPage Sorting Values...");
        });
        xpathPattern1 = "//*[contains(@class, 'window-box')]";
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    if(webDriver.findElements(By.xpath(xpathPattern1)).size() > 0){
                        report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                        webDriver.findElementByXPath("//*[contains(@class, 'paging right')]/a ").click();
                        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'gridProducts')]/div")));
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'gridProducts')]/div")));
                    }else {
                        System.out.println("No Window Element!");
                    }
                    Platform.runLater(() -> {
                        statusInfo.setText("Checking Sorting Values from Low to High...");
                    });

                    try{
                        List<WebElement> ItemsGridPage = webDriver.findElementsByXPath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]");
                        double checkStartingPriceFirstItem = Double.parseDouble(ItemsGridPage.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                        report.writeToFile("Detected : "+ ItemsGridPage.size() + " items on this Page!");
                        report.writeToFile("");

                        //DropDownButtonSorting
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]")));
                        webDriver.findElement(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]")).click();
                        //select 2nd DropDownElement
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]/div/div/a")));
                        List<WebElement> DropDownListActions = webDriver.findElementsByXPath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]/div/div/a");
                        DropDownListActions.get(1).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]/div/div/a")));

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));
                        List<WebElement> ItemsGridPageSortingLowToHigh = webDriver.findElementsByXPath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]");

                        double checkPriceLowToHighFirstItem = Double.parseDouble(ItemsGridPageSortingLowToHigh.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                        double checkPriceLowToHighLastItem = Double.parseDouble(ItemsGridPageSortingLowToHigh.get(ItemsGridPageSortingLowToHigh.size()-1).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                        if (checkPriceLowToHighFirstItem < checkStartingPriceFirstItem && webDriver.getCurrentUrl().contains("sort=price_asc")){
                            report.writeToFile("Successful changed Sorting from Lowest to Highest Price!", "");
                        }
                        if (checkPriceLowToHighFirstItem < checkPriceLowToHighLastItem){
                            report.writeToFile("Checking  Grid Page Price Lowest to Highest: ", "Successful! First Item Price("+checkPriceLowToHighFirstItem+") is lower than last Item Price("+checkPriceLowToHighLastItem+") !");
                        }else {
                            report.writeToFile("Checking  Grid Page Price Lowest to Highest: ", "Not Successful! First Item Price("+checkPriceLowToHighFirstItem+") is NOT lower than last Item Price("+checkPriceLowToHighLastItem+") !");
                        }
                        report.writeToFile("");






                        Platform.runLater(() -> {
                            statusInfo.setText("Checking Sorting Values from High to Low...");
                        });

                        //DropDownButtonSorting
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]")));
                        webDriver.findElement(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]")).click();

                        //DropDownButtonSorting
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]/div/div/a")));
                        DropDownListActions = webDriver.findElementsByXPath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]/div/div/a");
                        DropDownListActions.get(2).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]/div/div/a")));

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));
                        List<WebElement> ItemsGridPageSortingHighToLow = webDriver.findElementsByXPath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]");
                        double checkPriceHighToLowFirstItem = Double.parseDouble(ItemsGridPageSortingHighToLow.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                        double checkPriceHighToLowLastItem = Double.parseDouble(ItemsGridPageSortingHighToLow.get(ItemsGridPageSortingHighToLow.size()-1).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                        if (checkPriceHighToLowFirstItem > checkStartingPriceFirstItem && webDriver.getCurrentUrl().contains("sort=price_desc")){
                            report.writeToFile("Successful changed Sorting from Highest to Lowest Price!", "");
                        }
                        if (checkPriceHighToLowFirstItem > checkPriceHighToLowLastItem){
                            report.writeToFile("Checking  Grid Page Price Highest to Lowest: ", "Successful! First Item Price("+checkPriceHighToLowFirstItem+") is higher than last Item Price("+checkPriceHighToLowLastItem+") !");
                        }else {
                            report.writeToFile("Checking  Grid Page Price Highest to Lowest: ", "Not Successful! First Item Price("+checkPriceHighToLowFirstItem+") is NOT higher than last Item Price("+checkPriceHighToLowLastItem+") !");
                        }
                        report.writeToFile("");






                        Platform.runLater(() -> {
                            statusInfo.setText("Checking Sorting Values Sales Price...");
                        });
                        //DropDownButtonSorting
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]")));
                        webDriver.findElement(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]")).click();

                        //DropDownButtonSorting
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]/div/div/a")));
                        DropDownListActions = webDriver.findElementsByXPath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]/div/div/a");
                        DropDownListActions.get(3).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]/div/div/a")));

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));
                        List<WebElement> ItemsGridPageSortingSale = webDriver.findElementsByXPath("//*[contains(@class, 'gridProducts')]/div/div/a/div/span[contains(@class, 'new sale')]");
                        double checkPriceItemsGridPageSortingSale = Double.parseDouble(ItemsGridPageSortingSale.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                        if (checkPriceItemsGridPageSortingSale != checkStartingPriceFirstItem && webDriver.getCurrentUrl().contains("sort=reduced_price_desc")){
                            report.writeToFile("Successful changed Sorting to Sales!", "");
                        }
                        if (ItemsGridPageSortingSale.size() != 0){
                            report.writeToFile("Checking  Grid Page Sales Price: ", "Successful! Item contains Sales Price");
                        }else {
                            report.writeToFile("Checking  Grid Page Sales Price: ", "Not Successful! Couldn't find a item with Sales Price");
                        }
                        report.writeToFile("");






                        Platform.runLater(() -> {
                            statusInfo.setText("Checking Sorting Values New Items...");
                        });
                        //DropDownButtonSorting
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]")));
                        webDriver.findElement(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]")).click();

                        //DropDownButtonSorting
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]/div/div/a")));
                        DropDownListActions = webDriver.findElementsByXPath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]/div/div/a");
                        DropDownListActions.get(4).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]/div/div/a")));

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));
                        List<WebElement> ItemsGridPageSortingNewItems = webDriver.findElementsByXPath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]");
                        double checkPriceNewItemsFirstItem = Double.parseDouble(ItemsGridPageSortingNewItems.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                        if (webDriver.getCurrentUrl().contains("sort=date_desc")){
                            report.writeToFile("Successful changed Sorting to New Items!", "");
                        }
                        if (checkPriceNewItemsFirstItem != checkStartingPriceFirstItem){
                            report.writeToFile("Checking  Grid Page New Items: ", "Successful! First Item contains different Price Informations than previous Item");
                        }else {
                            report.writeToFile("Checking  Grid Page New Items: ", "Not Successful! First Item contains Price Informations from previous Item");
                        }
                        report.writeToFile("");





                        Platform.runLater(() -> {
                            statusInfo.setText("Checking Sorting Values Discount Items...");
                        });

                        //Discount Button
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.filter.salesprice"))));
                        System.out.println("TestText for Discount + "+webDriver.findElement(By.xpath(Homepage.getProperty("page.filter.salesprice"))).getText());
                        webDriver.findElement(By.xpath(Homepage.getProperty("page.filter.salesprice"))).click();

                        if(webDriver.findElements(By.xpath(xpathPattern1)).size() > 0){
                            report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                            webDriver.findElementByXPath("//*[contains(@class, 'paging right')]/a ").click();
                            //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'gridProducts')]/div")));
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'gridProducts')]/div")));
                        }else {
                            System.out.println("No Window Element!");
                        }

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));
                        List<WebElement> ItemsGridPageSortingDiscount = webDriver.findElementsByXPath("//*[contains(@class, 'gridProducts')]/div/div/a/div/span[contains(@class, 'discount')]");
                        if (ItemsGridPageSortingDiscount.size() > 0){
                            report.writeToFile("Checking  Grid Page Discount Label: ", "Successful! Found in Total + " +ItemsGridPageSortingDiscount.size()+" Discount Labels");
                        }else {
                            report.writeToFile("Checking  Grid Page Discount Label: ", "Not Successful! Couldn't find discount labels");
                        }
                        report.writeToFile("");






                        Platform.runLater(() -> {
                            sortingValuesOnGridPageHighToLow.setStyle("-fx-background-color: #CCFF99");
                            sortingValuesOnGridPageHighToLow.setSelected(true);
                        });
                        webDriver.navigate().to(inputSearch.getText().trim());
                        report.writeToFile("Checking GridPage Sorting Values: ", "Complete!");
                    }catch (Exception gridPageIssue){
                        Platform.runLater(() -> {
                            sortingValuesOnGridPageHighToLow.setStyle("-fx-background-color: #FF0000");
                            sortingValuesOnGridPageHighToLow.setSelected(true);
                        });
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorSorting.png");
                        if (isSuccessful){
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                        }
                        webDriver.navigate().to(inputSearch.getText().trim());
                        report.writeToFile("Checking GridPage Sorting Values: ", "Sorting on this Page doesn't seems to be working or very slow");
                        gridPageIssue.printStackTrace();
                    }
                }catch (Exception windowBoxIssue){
                    Platform.runLater(() -> {
                        sortingValuesOnGridPageHighToLow.setStyle("-fx-background-color: #FF0000");
                        sortingValuesOnGridPageHighToLow.setSelected(true);
                    });
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile("Checking GridPage Sorting Values: ", "Couldn't find a Button to remove WindowBoxes!");
                    windowBoxIssue.printStackTrace();
                }

            }catch (Exception noMainMenuLinkFound){
                Platform.runLater(() -> {
                    sortingValuesOnGridPageHighToLow.setStyle("-fx-background-color: #FF0000");
                    sortingValuesOnGridPageHighToLow.setSelected(true);
                });
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile("Checking GridPage Sorting Values: ", "Couldn't navigate to requested Site!");
                noMainMenuLinkFound.printStackTrace();
            }
        }catch (Exception noCategoryLinksLeftSideMenu){
            Platform.runLater(() -> {
                sortingValuesOnGridPageHighToLow.setStyle("-fx-background-color: #FF0000");
                sortingValuesOnGridPageHighToLow.setSelected(true);
            });
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile("Checking GridPage Sorting Values: ", "unable to check! Browser not responding");
            noCategoryLinksLeftSideMenu.printStackTrace();
        }

        report.writeToFile("=================================", "");
    }

    public void checkingSwitchFromSmallToLargeImages(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox switchFromSmallToLarge, TextField inputGridPageURL, Text statusInfo, TextField inputSearch, TextField inputEmailAdress, String xpathPattern1, String xpathPattern2, Properties Homepage, boolean isSuccessful, boolean isAvailable){
        Platform.runLater(() -> {
            switchFromSmallToLarge.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking GridPage Switch Small to Large Images...");
        });
        xpathPattern1 = "//*[contains(@class, 'window-box')]";
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);

                try{
                    if(webDriver.findElements(By.xpath(xpathPattern1)).size() > 0){
                        report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                        webDriver.findElementByXPath("//*[contains(@class, 'paging right')]/a ").click();
                        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'gridProducts')]/div")));
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'gridProducts')]/div")));
                    }else {
                        System.out.println("No Window Element!");
                    }
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'grid-item-size-btns')]/a")));
                    try {
                        webDriver.findElementByXPath("//*[contains(@class, 'grid-item-size-btns')]/a ").click();
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));
                        if (webDriver.getCurrentUrl().contains("itemSize=detail")){
                            report.writeToFile("Checking GridPage Switch Small to Large Images: ", "Successful! Found pattern in URL");
                            Platform.runLater(() -> {
                                switchFromSmallToLarge.setStyle("-fx-background-color: #CCFF99");
                                switchFromSmallToLarge.setSelected(true);
                            });
                        }else {
                            report.writeToFile("Checking  GridPage Switch Small to Large Images: ", "Not Successful! Couldn't find pattern in URL");
                            Platform.runLater(() -> {
                                switchFromSmallToLarge.setStyle("-fx-background-color: #FF0000");
                                switchFromSmallToLarge.setSelected(true);
                            });
                        }
                        report.writeToFile("");
                    }catch (Exception noLargeImageButton){
                        report.writeToFile("Checking  GridPage Switch Small to Large Images: ", "Not Successful! Couldn't find Large Image Button");
                        Platform.runLater(() -> {
                            switchFromSmallToLarge.setStyle("-fx-background-color: #FF0000");
                            switchFromSmallToLarge.setSelected(true);
                        });
                    }

                }catch (Exception gridPageIssue){
                    Platform.runLater(() -> {
                        switchFromSmallToLarge.setStyle("-fx-background-color: #FF0000");
                        switchFromSmallToLarge.setSelected(true);
                    });
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorSorting.png");
                    if (isSuccessful){
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile("Checking GridPage Switch Small to Large Images: ", "Sorting on this Page doesn't seems to be working or very slow");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noMainMenuLinkFound){
                Platform.runLater(() -> {
                    switchFromSmallToLarge.setStyle("-fx-background-color: #FF0000");
                    switchFromSmallToLarge.setSelected(true);
                });
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile("Checking GridPage Switch Small to Large Images: ", "Couldn't navigate to requested Site!");
                noMainMenuLinkFound.printStackTrace();
            }

        }catch (Exception noCategoryLinksLeftSideMenu){
            Platform.runLater(() -> {
                switchFromSmallToLarge.setStyle("-fx-background-color: #FF0000");
                switchFromSmallToLarge.setSelected(true);
            });
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile("Checking GridPage Switch Small to Large Images: ", "unable to check! Browser not responding");
            noCategoryLinksLeftSideMenu.printStackTrace();
        }

        report.writeToFile("=================================", "");
    }
}
