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
                        webDriver.findElementByXPath("//*[contains(@class, 'grid-item-size-btns')]/a ").click();
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));
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


    public void checkingPagingForwardBackward(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox pagingForwardBackward, TextField inputGridPageURL, Text statusInfo, TextField inputSearch, TextField inputEmailAdress, String xpathPattern1, String xpathPattern2, Properties Homepage, boolean isSuccessful, boolean isAvailable){
        Platform.runLater(() -> {
            pagingForwardBackward.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking GridPage Paging Forward / Backward...");
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
                        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'gridProducts')]/div")));
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@class, 'paging right')]/a  ")));
                    }else {
                        System.out.println("No Window Element!");
                    }
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'grid-item-size-btns')]/a")));
                    try {
                        webDriver.findElementByXPath("//*[contains(@class, 'pageNumbers left')]/a[1] ").click();
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));

                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'Click-Navigated-Previous_Page_Button')]")));


                        if (webDriver.getCurrentUrl().contains("2")){
                            report.writeToFile("Checking  GridPage Paging Forward: ", "Successful! Found pattern in URL and Previous Page Button appeared!");
                        }else {
                            report.writeToFile("Checking  GridPage Paging Forward: ", "Not Successful! User is not redirected");
                            isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorPagingForward.png");
                            if (isSuccessful){
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                            }else {
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                            }
                        }
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'Click-Navigated-Previous_Page_Button')]")));
                        webDriver.findElementByXPath("//*[contains(@class, 'Click-Navigated-Previous_Page_Button')] ").click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@class, 'Click-Navigated-Previous_Page_Button')] ")));
                        if (webDriver.getCurrentUrl().contains("1")){
                            report.writeToFile("Checking  GridPage Paging Backward: ", "Successful! Found pattern in URL and Previous Page Button disappeared!");
                        }else {
                            report.writeToFile("Checking  GridPage Paging Backward: ", "Not Successful! User is not redirected");
                            isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorPagingBackward.png");
                            if (isSuccessful){
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                            }else {
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                            }
                        }

                        Platform.runLater(() -> {
                            pagingForwardBackward.setStyle("-fx-background-color: #CCFF99");
                            pagingForwardBackward.setSelected(true);
                        });


                    }catch (Exception noLargeImageButton){
                        report.writeToFile("Checking  GridPage Paging Forward/Backward: ", "Not Successful! Couldn't find Page 2 Button");
                        Platform.runLater(() -> {
                            pagingForwardBackward.setStyle("-fx-background-color: #FF0000");
                            pagingForwardBackward.setSelected(true);
                        });
                    }

                }catch (Exception gridPageIssue){
                    Platform.runLater(() -> {
                        pagingForwardBackward.setStyle("-fx-background-color: #FF0000");
                        pagingForwardBackward.setSelected(true);
                    });
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorSorting.png");
                    if (isSuccessful){
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile("Checking GridPage Paging Forward/Backward: ", "Sorting on this Page doesn't seems to be working or very slow");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noMainMenuLinkFound){
                Platform.runLater(() -> {
                    pagingForwardBackward.setStyle("-fx-background-color: #FF0000");
                    pagingForwardBackward.setSelected(true);
                });
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile("Checking GridPage Paging Forward/Backward: ", "Couldn't navigate to requested Site!");
                noMainMenuLinkFound.printStackTrace();
            }

        }catch (Exception noCategoryLinksLeftSideMenu){
            Platform.runLater(() -> {
                pagingForwardBackward.setStyle("-fx-background-color: #FF0000");
                pagingForwardBackward.setSelected(true);
            });
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile("Checking GridPage Paging Forward/Backward: ", "unable to check! Browser not responding");
            noCategoryLinksLeftSideMenu.printStackTrace();
        }

        report.writeToFile("=================================", "");
    }

    public void checkingProductView300(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox productView300, TextField inputGridPageURL, Text statusInfo, TextField inputSearch, TextField inputEmailAdress, String xpathPattern1, String xpathPattern2, Properties Homepage, boolean isSuccessful, boolean isAvailable){
        Platform.runLater(() -> {
            productView300.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking GridPage Product View 300...");
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
                        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'gridProducts')]/div")));
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@class, 'paging right')]/a  ")));

                    }else {
                        System.out.println("No Window Element!");
                    }
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'items-per-page-entries')]/a")));
                    try {
                        Point hoverItem = webDriver.findElement(By.xpath("//*[contains(@class, 'items-per-page-entries')]/a")).getLocation();
                        ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                        ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                        // Scroll up
                        for (int i = 0; i < 1; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,-500)");
                        }


                        webDriver.findElementByXPath("//*[contains(@class, 'items-per-page-entries')]/a ").click();

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]")));
                        List<WebElement> ItemsGridPageProductView300 = webDriver.findElementsByXPath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]");
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'items-per-page-entries')]/a")));

                        if (webDriver.getCurrentUrl().contains("ipp=300") && ItemsGridPageProductView300.size() == 300){
                            report.writeToFile("Checking  GridPage Product View 300: ", "Successful! Found pattern in URL and counted 300 Items!");
                            Platform.runLater(() -> {
                                productView300.setStyle("-fx-background-color: #CCFF99");
                                productView300.setSelected(true);
                            });
                        }else {
                            report.writeToFile("Checking  GridPage Product View 300: ", "Not Successful, loaded "+ItemsGridPageProductView300.size()+" items and Url not changed");
                            isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorPagingBackward.png");
                            if (isSuccessful){
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                            }else {
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                            }
                            Platform.runLater(() -> {
                                productView300.setStyle("-fx-background-color: #FF0000");
                                productView300.setSelected(true);
                            });
                        }



                    }catch (Exception noLargeImageButton){
                        report.writeToFile("Checking  GridPage Paging Forward/Backward: ", "Not Successful! Couldn't find 300 Items View Button");
                        Platform.runLater(() -> {
                            productView300.setStyle("-fx-background-color: #FF0000");
                            productView300.setSelected(true);
                        });
                    }

                }catch (Exception gridPageIssue){
                    Platform.runLater(() -> {
                        productView300.setStyle("-fx-background-color: #FF0000");
                        productView300.setSelected(true);
                    });
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorSorting.png");
                    if (isSuccessful){
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile("Checking GridPage GridPage Product View 300: ", "Sorting on this Page doesn't seems to be working or very slow");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noMainMenuLinkFound){
                Platform.runLater(() -> {
                    productView300.setStyle("-fx-background-color: #FF0000");
                    productView300.setSelected(true);
                });
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile("Checking GridPage GridPage Product View 300: ", "Couldn't navigate to requested Site!");
                noMainMenuLinkFound.printStackTrace();
            }

        }catch (Exception noCategoryLinksLeftSideMenu){
            Platform.runLater(() -> {
                productView300.setStyle("-fx-background-color: #FF0000");
                productView300.setSelected(true);
            });
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile("Checking GridPage GridPage Product View 300: ", "unable to check! Browser not responding");
            noCategoryLinksLeftSideMenu.printStackTrace();
        }

        report.writeToFile("=================================", "");
    }

    public void checkingDeeperStyle(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox deeperStyle, TextField inputGridPageURL, Text statusInfo, TextField inputSearch, TextField inputEmailAdress, String xpathPattern1, String xpathPattern2, Properties Homepage, boolean isSuccessful, boolean isAvailable){
        Platform.runLater(() -> {
            deeperStyle.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking GridPage Deeper Style...");
        });
        xpathPattern1 = "//*[contains(@data-id, 'style_block')]/ul/li/a[1] ";
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    if(webDriver.findElements(By.xpath("//*[contains(@class, 'window-box')]")).size() > 0){
                        report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                        webDriver.findElementByXPath("//*[contains(@class, 'paging right')]/a ").click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@class, 'paging right')]/a  ")));
                    }else {
                        System.out.println("No Window Element!");
                    }


                    try{
                        final String tagNameDeeperStyle = webDriver.findElementByXPath(xpathPattern1).getText();
                        webDriver.findElementByXPath(xpathPattern1).click();
                        if (webDriver.getCurrentUrl().contains(tagNameDeeperStyle.toLowerCase())){
                            report.writeToFile("Checking  GridPage Deeper Style: ", "Successful! Clicked on first tag and redirected to a functioning page!");
                            Platform.runLater(() -> {
                                deeperStyle.setStyle("-fx-background-color: #CCFF99");
                                deeperStyle.setSelected(true);
                            });
                        }else{
                            report.writeToFile("Checking  GridPage Deeper Style: ", "Not Successful! Couldn't find Keyword "+ tagNameDeeperStyle +"in redirected URL "+webDriver.getCurrentUrl()+"!");
                            Platform.runLater(() -> {
                                deeperStyle.setStyle("-fx-background-color: #FF0000");
                                deeperStyle.setSelected(true);
                            });
                        }

                    }catch (Exception noStyleTagFound){
                        Platform.runLater(() -> {
                            deeperStyle.setStyle("-fx-background-color: #FF0000");
                            deeperStyle.setSelected(true);
                        });
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorDeeperStyle.png");
                        if (isSuccessful){
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                        }
                    }


                }catch (Exception gridPageIssue){
                    Platform.runLater(() -> {
                        deeperStyle.setStyle("-fx-background-color: #FF0000");
                        deeperStyle.setSelected(true);
                    });
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorDeeperStyle.png");
                    if (isSuccessful){
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile("Checking GridPage Deeper Style: ", "Sorting on this Page doesn't seems to be working or very slow");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noMainMenuLinkFound){
                Platform.runLater(() -> {
                    deeperStyle.setStyle("-fx-background-color: #FF0000");
                    deeperStyle.setSelected(true);
                });
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile("Checking GridPage Deeper Style: ", "Couldn't navigate to requested Site!");
                noMainMenuLinkFound.printStackTrace();
            }
            }catch (Exception noCategoryLinksLeftSideMenu){
            Platform.runLater(() -> {
                deeperStyle.setStyle("-fx-background-color: #FF0000");
                deeperStyle.setSelected(true);
            });
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile("Checking GridPage Deeper Style: ", "unable to check! Browser not responding");
            noCategoryLinksLeftSideMenu.printStackTrace();
        }

        report.writeToFile("=================================", "");
    }

    public void checkingStyleBoxOpenClose(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox styleBoxOpenClose, TextField inputGridPageURL, Text statusInfo, TextField inputSearch, TextField inputEmailAdress, String xpathPattern1, String xpathPattern2, Properties Homepage, boolean isSuccessful, boolean isAvailable){
        Platform.runLater(() -> {
            styleBoxOpenClose.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking GridPage Style Box Open/Close");
        });
        xpathPattern1 = "//*[contains(@data-id, 'style_block')]/ul/li/a ";
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    if(webDriver.findElements(By.xpath("//*[contains(@class, 'window-box')]")).size() > 0){
                        report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                        webDriver.findElementByXPath("//*[contains(@class, 'paging right')]/a ").click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@class, 'paging right')]/a  ")));
                    }else {
                        System.out.println("No Window Element!");
                    }
                    try {
                        Point hoverItem = webDriver.findElement(By.xpath("//*[contains(@data-id, 'style_block')]/*[contains(@class, 'btn btn-grey toggle-categories hidden nct nct-Click-Selected-More_Categories_Button ncts-Bottom_More_Options')]")).getLocation();
                        ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                        ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                        // Scroll up
                        for (int i = 0; i < 1; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,-400)");
                        }
                        webDriver.findElementByXPath("//*[contains(@data-id, 'style_block')]/*[contains(@class, 'btn btn-grey toggle-categories hidden nct nct-Click-Selected-More_Categories_Button ncts-Bottom_More_Options')] ").click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@data-id, 'style_block')]/*[contains(@class, 'btn btn-grey toggle-categories hidden nct nct-Click-Selected-More_Categories_Button ncts-Bottom_More_Options')]")));
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageStyleBoxOpenClose1.png");
                        if (isSuccessful){
                            report.writeToFile("GridPage See More: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("GridPage See More: ", "Screenshot not successful!");
                        }
                        webDriver.findElementByXPath("//*[contains(@data-id, 'style_block')]/*[contains(@class, 'middle-toggle-categories nct nct-Click-Selected-More_Categories_Button')]  ").click();
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,100)");
                        }
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,-100)");
                        }
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageStyleBoxOpenClose2.png");
                        if (isSuccessful){
                            report.writeToFile("GridPage See Less: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("GridPage See Less: ", "Screenshot not successful!");
                        }

                        Platform.runLater(() -> {
                            styleBoxOpenClose.setStyle("-fx-background-color: #CCFF99");
                            styleBoxOpenClose.setSelected(true);
                        });
                        report.writeToFile("Checking GridPage Style Box Open/Close: ", "Successful! Style Box- Open/Close working as expected");

                    }catch (Exception noStyleBoxOpenCloseFound){
                        Platform.runLater(() -> {
                            styleBoxOpenClose.setStyle("-fx-background-color: #FF0000");
                            styleBoxOpenClose.setSelected(true);
                        });
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorStyleBoxOpenClose.png");
                        if (isSuccessful){
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                        }
                        report.writeToFile("Checking GridPage Style Box Open/Close: ", "Couldn't find any Show-More Button");
                    }


                }catch (Exception gridPageIssue){
                    Platform.runLater(() -> {
                        styleBoxOpenClose.setStyle("-fx-background-color: #FF0000");
                        styleBoxOpenClose.setSelected(true);
                    });
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorDeeperStyle.png");
                    if (isSuccessful){
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile("Checking GridPage Style Box Open/Close: ", "Sorting on this Page doesn't seems to be working or very slow");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noMainMenuLinkFound){
                Platform.runLater(() -> {
                    styleBoxOpenClose.setStyle("-fx-background-color: #FF0000");
                    styleBoxOpenClose.setSelected(true);
                });
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile("Checking GridPage Style Box Open/Close: ", "Couldn't navigate to requested Site!");
                noMainMenuLinkFound.printStackTrace();
            }
        }catch (Exception noCategoryLinksLeftSideMenu){
            Platform.runLater(() -> {
                styleBoxOpenClose.setStyle("-fx-background-color: #FF0000");
                styleBoxOpenClose.setSelected(true);
            });
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile("Checking GridPage Style Box Open/Close: ", "unable to check! Browser not responding");
            noCategoryLinksLeftSideMenu.printStackTrace();
        }

        report.writeToFile("=================================", "");
    }
}
