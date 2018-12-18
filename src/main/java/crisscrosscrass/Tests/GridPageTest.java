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

public class GridPageTest {
    Report failedTestCases = new Report();

    public void checkingSorting(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox sortingValues, TextField inputGridPageURL, Text statusInfo, TextField inputSearch, Properties Homepage){
        failedTestCases.writeToNamedFile("CHECKING GRID PAGE", "FailAndReview");
        final String infoMessage = sortingValues.getText();
        ChangeCheckBox.adjustStyle(false,"progress",sortingValues);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0){
                        report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                    }

                    Platform.runLater(() -> {
                        statusInfo.setText("Checking Sorting Values from Low to High...");
                    });

                    try{
                        List<WebElement> ItemsGridPage = webDriver.findElementsByXPath(Homepage.getProperty("page.items.price"));
                        double checkStartingPriceFirstItem = Double.parseDouble(ItemsGridPage.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)|[a-zA-Z]|[łč]","").trim().replaceAll("\\.","").replaceAll(",","."));
                        report.writeToFile("Detected : "+ ItemsGridPage.size() + " items on this Page!");
                        report.writeToFile("");

                        //DropDownButtonSorting
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.button"))));
                        webDriver.findElement(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.button"))).click();
                        //select 2nd DropDownElement
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.options"))));
                        List<WebElement> DropDownListActions = webDriver.findElementsByXPath(Homepage.getProperty("page.grid.sort.dropdown.options"));
                        DropDownListActions.get(1).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.options"))));

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.price"))));
                        List<WebElement> ItemsGridPageSortingLowToHigh = webDriver.findElementsByXPath(Homepage.getProperty("page.items.price"));

                        double checkPriceLowToHighFirstItem = Double.parseDouble(ItemsGridPageSortingLowToHigh.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)|[a-zA-Z]|[łč]","").trim().replaceAll("\\.","").replaceAll(",","."));
                        double checkPriceLowToHighLastItem = Double.parseDouble(ItemsGridPageSortingLowToHigh.get(ItemsGridPageSortingLowToHigh.size()-1).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)|[a-zA-Z]|[łč]","").trim().replaceAll("\\.","").replaceAll(",","."));
                        if (checkPriceLowToHighFirstItem < checkStartingPriceFirstItem && webDriver.getCurrentUrl().contains("sort=price_asc")){
                            report.writeToFile("Successful changed Sorting from Lowest to Highest Price!", "");
                        }
                        if (checkPriceLowToHighFirstItem < checkPriceLowToHighLastItem){
                            report.writeToFile("Checking  Grid Page Price Lowest to Highest: ", "Successful! First Item Price("+checkPriceLowToHighFirstItem+") is lower than last Item Price("+checkPriceLowToHighLastItem+") !");
                        }else {
                            report.writeToFile("Checking  Grid Page Price Lowest to Highest: ", "Not Successful! First Item Price("+checkPriceLowToHighFirstItem+") is NOT lower than last Item Price("+checkPriceLowToHighLastItem+") !");
                            failedTestCases.writeToNamedFile("Checking  Grid Page Price: Lowest to Highest: ", "Sorting Not Successful! First Item Price("+checkPriceLowToHighFirstItem+") is NOT lower than last Item Price("+checkPriceLowToHighLastItem+") !", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 9.1","FailAndReview");
                        }
                        report.writeToFile("");






                        Platform.runLater(() -> {
                            statusInfo.setText("Checking Sorting Values from High to Low...");
                        });

                        //DropDownButtonSorting
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.button"))));
                        webDriver.findElement(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.button"))).click();

                        //DropDownButtonSorting
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.options"))));
                        DropDownListActions = webDriver.findElementsByXPath(Homepage.getProperty("page.grid.sort.dropdown.options"));
                        DropDownListActions.get(2).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.options"))));

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.price"))));
                        List<WebElement> ItemsGridPageSortingHighToLow = webDriver.findElementsByXPath(Homepage.getProperty("page.items.price"));
                        double checkPriceHighToLowFirstItem = Double.parseDouble(ItemsGridPageSortingHighToLow.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)|[a-zA-Z]|[łč]","").trim().replaceAll("\\.","").replaceAll(",","."));
                        double checkPriceHighToLowLastItem = Double.parseDouble(ItemsGridPageSortingHighToLow.get(ItemsGridPageSortingHighToLow.size()-1).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)|[a-zA-Z]|[łč]","").trim().replaceAll("\\.","").replaceAll(",","."));
                        if (checkPriceHighToLowFirstItem > checkStartingPriceFirstItem && webDriver.getCurrentUrl().contains("sort=price_desc")){
                            report.writeToFile("Successful changed Sorting from Highest to Lowest Price!", "");
                        }
                        if (checkPriceHighToLowFirstItem > checkPriceHighToLowLastItem){
                            report.writeToFile("Checking  Grid Page Price Highest to Lowest: ", "Successful! First Item Price("+checkPriceHighToLowFirstItem+") is higher than last Item Price("+checkPriceHighToLowLastItem+") !");
                        }else {
                            report.writeToFile("Checking  Grid Page Price Highest to Lowest: ", "Not Successful! First Item Price("+checkPriceHighToLowFirstItem+") is NOT higher than last Item Price("+checkPriceHighToLowLastItem+") !");
                            failedTestCases.writeToNamedFile("Checking  Grid Page Price Highest to Lowest: ", "Sorting Not Successful! First Item Price("+checkPriceHighToLowFirstItem+") is NOT higher than last Item Price("+checkPriceHighToLowLastItem+") !", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 9.2","FailAndReview");
                        }
                        report.writeToFile("");






                        Platform.runLater(() -> {
                            statusInfo.setText("Checking Sorting Values Sales Price...");
                        });
                        //DropDownButtonSorting
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.button"))));
                        webDriver.findElement(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.button"))).click();

                        //DropDownButtonSorting
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.options"))));
                        DropDownListActions = webDriver.findElementsByXPath(Homepage.getProperty("page.grid.sort.dropdown.options"));
                        DropDownListActions.get(3).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.options"))));

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.price"))));
                        List<WebElement> ItemsGridPageSortingSale = webDriver.findElementsByXPath(Homepage.getProperty("page.items.salesprice"));
                        double checkPriceItemsGridPageSortingSale = Double.parseDouble(ItemsGridPageSortingSale.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)|[a-zA-Z]|[łč]","").trim().replaceAll("\\.","").replaceAll(",","."));
                        if (checkPriceItemsGridPageSortingSale != checkStartingPriceFirstItem && webDriver.getCurrentUrl().contains("sort=reduced_price_desc")){
                            report.writeToFile("Successful changed Sorting to Sales!", "");
                        }
                        if (ItemsGridPageSortingSale.size() != 0){
                            report.writeToFile("Checking  Grid Page Sales Price: ", "Successful! Item contains Sales Price");
                        }else {
                            report.writeToFile("Checking  Grid Page Sales Price: ", "Not Successful! Couldn't find a item with Sales Price");
                            failedTestCases.writeToNamedFile("Checking  Grid Page Sales Price: ", "Sorting Not Successful! Couldn't find a item with Sales Price", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 9.3","FailAndReview");
                        }
                        report.writeToFile("");






                        Platform.runLater(() -> {
                            statusInfo.setText("Checking Sorting Values New Items...");
                        });
                        //DropDownButtonSorting
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.button"))));
                        webDriver.findElement(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.button"))).click();

                        //DropDownButtonSorting
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.options"))));
                        DropDownListActions = webDriver.findElementsByXPath(Homepage.getProperty("page.grid.sort.dropdown.options"));
                        DropDownListActions.get(4).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.options"))));

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.price"))));
                        List<WebElement> ItemsGridPageSortingNewItems = webDriver.findElementsByXPath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]");
                        double checkPriceNewItemsFirstItem = Double.parseDouble(ItemsGridPageSortingNewItems.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)|[a-zA-Z]|[łč]","").trim().replaceAll("\\.","").replaceAll(",","."));
                        if (webDriver.getCurrentUrl().contains("sort=date_desc")){
                            report.writeToFile("Successful changed Sorting to New Items!", "");
                        }
                        if (checkPriceNewItemsFirstItem != checkStartingPriceFirstItem){
                            report.writeToFile("Checking  Grid Page New Items: ", "Successful! First Item contains different Price Informations than previous Item");
                        }else {
                            report.writeToFile("Checking  Grid Page New Items: ", "Not Successful! First Item contains Price Informations from previous Item");
                            failedTestCases.writeToNamedFile("Checking  Grid Page New Items: ", "Sorting Not Successful! First Item contains Price Information from previous Item", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 9.4","FailAndReview");
                        }
                        report.writeToFile("");





                        Platform.runLater(() -> {
                            statusInfo.setText("Checking Sorting Values Discount Items...");
                        });

                        //Discount Button
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.filter.salesprice"))));
                        webDriver.findElement(By.xpath(Homepage.getProperty("page.filter.salesprice"))).click();

                        if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0){
                            report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                            webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                        }

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.price"))));
                        List<WebElement> ItemsGridPageSortingDiscount = webDriver.findElementsByXPath(Homepage.getProperty("page.items.salesprice"));
                        if (ItemsGridPageSortingDiscount.size() > 0){
                            report.writeToFile("Checking  Grid Page Discount Label: ", "Successful! Found in Total + " +ItemsGridPageSortingDiscount.size()+" Discount Labels");
                        }else {
                            report.writeToFile("Checking  Grid Page Discount Label: ", "Not Successful! Couldn't find discount labels");
                            failedTestCases.writeToNamedFile("Checking Grid Page Discount Label: ", "Sorting Not Successful! Couldn't find discount labels", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 9.5","FailAndReview");
                        }
                        report.writeToFile("");




                        ChangeCheckBox.adjustStyle(true,"complete",sortingValues);
                        webDriver.navigate().to(inputSearch.getText().trim());
                        report.writeToFile(infoMessage, "Complete!");
                    }catch (Exception gridPageIssue){
                        ChangeCheckBox.adjustStyle(true,"nope",sortingValues);
                        boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "GridPageErrorSorting.png");
                        if (isSuccessful){
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                            failedTestCases.writeToNamedFile("For more information about the sorting error on Grid Page, see GridPageErrorSorting", "Screenshot successful! See GridPageErrorSorting ", "FailAndReview");
                        }else {
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                            failedTestCases.writeToNamedFile("For more information about the sorting error on Grid Page, see GridPageErrorSorting", "Screenshot not successful! ", "FailAndReview");
                        }
                        webDriver.navigate().to(inputSearch.getText().trim());
                        report.writeToFile(infoMessage, "Sorting on this Page doesn't seems to be working or very slow");
                        failedTestCases.writeToNamedFile(infoMessage, "Please check: Sorting on Grid Page doesn't seems to be working or very slow", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 9","FailAndReview");
                        gridPageIssue.printStackTrace();
                    }
                }catch (Exception windowBoxIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",sortingValues);
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't find a Button to remove WindowBoxes!");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't find a Button to remove WindowBoxes on Grid Page!", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 9","FailAndReview");
                    windowBoxIssue.printStackTrace();
                }

            }catch (Exception noMainMenuLinkFound){
                ChangeCheckBox.adjustStyle(true,"nope",sortingValues);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't navigate to Sorting Functionality on Grid Page!", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 9","FailAndReview");
                noMainMenuLinkFound.printStackTrace();
            }
        }catch (Exception noCategoryLinksLeftSideMenu){
            ChangeCheckBox.adjustStyle(true,"nope",sortingValues);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Grid page functionality: browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 9","FailAndReview");
            noCategoryLinksLeftSideMenu.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
    public void checkingSwitchFromSmallToLargeImages(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox switchFromSmallToLarge, TextField inputGridPageURL, Text statusInfo, TextField inputSearch, Properties Homepage){
        final String infoMessage = switchFromSmallToLarge.getText();
        ChangeCheckBox.adjustStyle(false,"progress",switchFromSmallToLarge);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });

        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);

                try{
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0){
                        report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                    }

                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.grid.size.button"))));
                    try {
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.size.button")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.price"))));
                        if (webDriver.getCurrentUrl().contains("itemSize=detail")){
                            report.writeToFile(infoMessage, "Successful! Found pattern in URL");
                            ChangeCheckBox.adjustStyle(true,"complete",switchFromSmallToLarge);
                        }else {
                            report.writeToFile(infoMessage, "Not Successful! Couldn't find pattern in URL");
                            failedTestCases.writeToNamedFile(infoMessage, "Please check: unable to switch image size on Grid Page", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 10","FailAndReview");
                            ChangeCheckBox.adjustStyle(true,"nope",switchFromSmallToLarge);
                        }
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.size.button")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.price"))));
                        report.writeToFile("");
                    }catch (Exception noLargeImageButton){
                        report.writeToFile(infoMessage, "Not Successful! Couldn't find Large Image Button");
                        failedTestCases.writeToNamedFile(infoMessage, "Please check: unable to find Large Image Button on Grid Page", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 10","FailAndReview");
                        ChangeCheckBox.adjustStyle(true,"nope",switchFromSmallToLarge);
                    }

                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",switchFromSmallToLarge);
                    boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "GridPageSwitchImageSize.png");
                    if (isSuccessful){
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("For more information about the image size error on Grid Page, see GridPageSwitchImageSize ", "Screenshot successful! See GridPageErrorSorting for reference.", "FailAndReview");
                    }else {
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("For more information about the image size error on Grid Page, see GridPageSwitchImageSize", "Screenshot not successful!", "FailAndReview");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Sorting on this Page doesn't seems to be working or very slow");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: Switching from small to large images seems to be working or very slow on Grid Page", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 10","FailAndReview");

                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noMainMenuLinkFound){
                ChangeCheckBox.adjustStyle(true,"nope",switchFromSmallToLarge);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check Grid Page: Couldn't navigate to Switch image Size Page", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 10","FailAndReview");
                noMainMenuLinkFound.printStackTrace();
            }

        }catch (Exception noCategoryLinksLeftSideMenu){
            ChangeCheckBox.adjustStyle(true,"nope",switchFromSmallToLarge);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Image Sizes Grid Page: Browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 10","FailAndReview");
            noCategoryLinksLeftSideMenu.printStackTrace();
        }
        report.writeToFile("=================================", "");

    }
    public void checkingPagingForwardBackward(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox pagingForwardBackward, TextField inputGridPageURL, Text statusInfo, TextField inputSearch, Properties Homepage){
        final String infoMessage = pagingForwardBackward.getText();
        ChangeCheckBox.adjustStyle(false,"progress",pagingForwardBackward);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });

        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);

                boolean isSuccessful;
                try{
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0){
                        report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                    }

                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.grid.size.button"))));
                    try {
                        webDriver.findElementByXPath(Homepage.getProperty("page.pageNumbers")).click();
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.price"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.previousPage.button"))));


                        if (webDriver.getCurrentUrl().contains("2")){
                            report.writeToFile("Checking  GridPage Paging Forward: ", "Successful! Found pattern in URL and Previous Page Button appeared!");
                        }else {
                            report.writeToFile("Checking  GridPage Paging Forward: ", "Not Successful! User is not redirected");
                            failedTestCases.writeToNamedFile("Grid Paging Forward", "Please check paging functionality on Grid Page.", "FailAndReview");
                            isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorPagingForward.png");
                            if (isSuccessful){
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                                failedTestCases.writeToNamedFile("For more information about the Paging Forward error, see GridPageErrorPagingForward ", "Screenshot successful!", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 11.1","FailAndReview");
                            }else {
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                                failedTestCases.writeToNamedFile("For more information about the Paging Forward error, see GridPageErrorPagingForward", "Screenshot not successful!", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 11.1","FailAndReview");
                            }
                        }
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'Click-Navigated-Previous_Page_Button')]")));
                        webDriver.findElementByXPath("//*[contains(@class, 'Click-Navigated-Previous_Page_Button')] ").click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@class, 'Click-Navigated-Previous_Page_Button')]")));
                        if (!webDriver.getCurrentUrl().contains("-2")){
                            report.writeToFile("Checking  GridPage Paging Backward: ", "Successful! Found pattern in URL and Previous Page Button disappeared!");
                        }else {
                            report.writeToFile("Checking  GridPage Paging Backward: ", "Not Successful! User is not redirected");
                            failedTestCases.writeToNamedFile("Grid Paging Forward", "Please check paging functionality on Grid Page.", "FailAndReview");
                            isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorPagingBackward.png");
                            if (isSuccessful){
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                                failedTestCases.writeToNamedFile("For more information about the Paging Backward error, see GridPageErrorPagingBackward ", "Screenshot successful!", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 11.2","FailAndReview");
                            }else {
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                                failedTestCases.writeToNamedFile("For more information about the Paging Backward error, see GridPageErrorPagingBackward", "Screenshot not successful!", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 11.2","FailAndReview");
                            }
                        }
                        ChangeCheckBox.adjustStyle(true,"complete",pagingForwardBackward);
                        report.writeToFile(infoMessage, "Complete");


                    }catch (Exception noLargeImageButton){
                        report.writeToFile(infoMessage, "Not Successful! Couldn't find Page 2 Button");
                        failedTestCases.writeToNamedFile(infoMessage, "Please check: Paging seems not to be working on Grid page.", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 11.2","FailAndReview");
                        ChangeCheckBox.adjustStyle(true,"nope",pagingForwardBackward);
                    }

                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",pagingForwardBackward);
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageForwardBackward.png");
                    if (isSuccessful){
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("For more information about the Paging Forward-Backward error, see GridPageForwardBackward", "Screenshot successful!", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 11.2","FailAndReview");
                    }else {
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("For more information about the Paging Forward-Backward error, see GridPageForwardBackward", "Screenshot not successful!", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 11.2","FailAndReview");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Sorting on this Page doesn't seems to be working or very slow");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: Paging seems not to be working on Grid page", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 11.2","FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noMainMenuLinkFound){
                ChangeCheckBox.adjustStyle(true,"nope",pagingForwardBackward);
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't navigate to Paging on Grid page", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 11","FailAndReview");
                noMainMenuLinkFound.printStackTrace();
            }

        }catch (Exception noCategoryLinksLeftSideMenu){
            ChangeCheckBox.adjustStyle(true,"nope",pagingForwardBackward);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Paging on Grid Page: browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 11","FailAndReview");
            noCategoryLinksLeftSideMenu.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
/** PRODUCT VIEW WAS REMOVED FROM PAGE */
/*
    public void checkingProductView300(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox productView300, TextField inputGridPageURL, Text statusInfo, TextField inputSearch, Properties Homepage){
        final String infoMessage = productView300.getText();
        ChangeCheckBox.adjustStyle(false,"progress",productView300);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });

        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);

                boolean isSuccessful;
                try{
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0){
                        report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                    }
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.grid.items.number"))));
                    try {
                        Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("page.grid.items.number"))).getLocation();
                        ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                        ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                        // Scroll up
                        for (int i = 0; i < 1; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,-500)");
                        }
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.items.number")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.price"))));
                        List<WebElement> ItemsGridPageProductView300 = webDriver.findElementsByXPath(Homepage.getProperty("page.items.price"));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.grid.items.number"))));

                        if (webDriver.getCurrentUrl().contains("ipp=300") && ItemsGridPageProductView300.size() == 300){
                            report.writeToFile(infoMessage, "Successful! Found pattern in URL and counted 300 Items!");
                            ChangeCheckBox.adjustStyle(true,"complete",productView300);
                        }else {
                            report.writeToFile(infoMessage, "Not Successful, loaded "+ItemsGridPageProductView300.size()+" items and Url not changed");
                            failedTestCases.writeToNamedFile(infoMessage, "Please check: Product view switch Not Successful, loaded "+ItemsGridPageProductView300.size()+" items and Url not changed", "FailAndReview");
                            isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageProductView300Error.png");
                            if (isSuccessful){
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                                failedTestCases.writeToNamedFile("For more information about the Product View (300 items) error on Grid Page, see GridPageProductView300Error", "Screenshot successful! See GridPageErrorPagingBackward, for reference", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 12","FailAndReview");
                            }else {
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                                failedTestCases.writeToNamedFile("For more information about the Product View (300 items) error on Grid Page, see GridPageProductView300Error ", "Screenshot successful!", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 12","FailAndReview");
                            }
                            ChangeCheckBox.adjustStyle(true,"nope",productView300);
                        }
                    }catch (Exception noLargeImageButton){
                        report.writeToFile(infoMessage, "Not Successful! Couldn't find 300 Items View Button");
                        failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't find 300 Items View Button on Grid Page", "FailAndreview");
                        failedTestCases.writeToNamedFile("=================================TC 12","FailAndReview");
                        ChangeCheckBox.adjustStyle(true,"nope",productView300);
                    }

                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",productView300);
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageProductViewError.png");
                    if (isSuccessful){
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("For more information about the Product View error on Grid Page, see GridPageProductViewError", "Screenshot successful! See GridPageErrorPagingBackward, for reference", "FailAndReview");
                    }else {
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("For more information about the Product View error on Grid Page, see GridPageProductViewError", "Screenshot successful!", "FailAndReview");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Sorting on this Page doesn't seems to be working or very slow");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: Product View on Grid Page doesn't seems to be working or very slow", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 12","FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noMainMenuLinkFound){
                ChangeCheckBox.adjustStyle(true,"nope",productView300);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't navigate to Product View page on Grid Page", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 12","FailAndReview");
                noMainMenuLinkFound.printStackTrace();
            }

        }catch (Exception noCategoryLinksLeftSideMenu){
            ChangeCheckBox.adjustStyle(true,"nope",productView300);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Product View on Grid Page: browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 12","FailAndReview");
            noCategoryLinksLeftSideMenu.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
*/
    public void checkingDeeperStyle(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox deeperStyle, TextField inputGridPageURL, Text statusInfo, TextField inputSearch, Properties Homepage){
        final String infoMessage = deeperStyle.getText();
        ChangeCheckBox.adjustStyle(false,"progress",deeperStyle);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });

        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                boolean isSuccessful;
                try{
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0){
                        report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                    }


                    try{
                        final String tagNameDeeperStyle = webDriver.findElementByXPath(Homepage.getProperty("page.grid.first.tag")).getText();
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.first.tag")).click();
                        if (webDriver.getCurrentUrl().contains(tagNameDeeperStyle.toLowerCase())){
                            report.writeToFile(infoMessage, "Successful! Clicked on first tag and redirected to a functioning page!");
                            ChangeCheckBox.adjustStyle(true,"complete",deeperStyle);
                        }else{
                            report.writeToFile(infoMessage, "Not Successful! Couldn't find Keyword "+ tagNameDeeperStyle +"in redirected URL "+webDriver.getCurrentUrl()+"!");
                            failedTestCases.writeToNamedFile(infoMessage, "Please check the following tag: Couldn't find Keyword "+ tagNameDeeperStyle +"in redirected URL "+webDriver.getCurrentUrl()+"!", "FailAndReview");
                            ChangeCheckBox.adjustStyle(true,"nope",deeperStyle);
                            failedTestCases.writeToNamedFile("=================================TC 13","FailAndReview");
                        }

                    }catch (Exception noStyleTagFound){
                        ChangeCheckBox.adjustStyle(true,"nope",deeperStyle);
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorDeeperStyle.png");
                        if (isSuccessful){
                            report.writeToFile("For more information about the error while testing deep style functionality, see GridPageErrorDeeperStyle", "Screenshot successful!");
                            failedTestCases.writeToNamedFile("For more information about the error while testing deep style functionality, see GridPageErrorDeeperStyle", "Screenshot successful! See GridPageErrorDeeperStyle, for reference", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 13","FailAndReview");
                        }else {
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                            failedTestCases.writeToNamedFile("For more information about the error while testing deep style functionality, see GridPageErrorDeeperStyle", "Screenshot not successful!", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 13","FailAndReview");
                        }
                    }


                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",deeperStyle);
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorDeeperStyle.png");
                    if (isSuccessful){
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("For more information about the error while testing deep style functionality, see GridPageErrorDeeperStyle ", "Screenshot successful! See GridPageErrorDeeperStyle, for reference", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 13","FailAndReview");
                    }else {
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("For more information about the error while testing deep style functionality, see GridPageErrorDeeperStyle", "Screenshot not successful!", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 13","FailAndReview");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Sorting on this Page doesn't seems to be working or very slow");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check Deep Styles on Selected Grid page manually", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 13","FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noMainMenuLinkFound){
                ChangeCheckBox.adjustStyle(true,"nope",deeperStyle);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check Deep Styles on Selected Grid page manually: couldn't navigate to tag", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 13","FailAndReview");
                noMainMenuLinkFound.printStackTrace();
            }
        }catch (Exception noCategoryLinksLeftSideMenu){
            ChangeCheckBox.adjustStyle(true,"nope",deeperStyle);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Deep Styles on Selected Grid page manually: browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 13","FailAndReview");
            noCategoryLinksLeftSideMenu.printStackTrace();
        }

        report.writeToFile("=================================", "");
    }

    public void checkingStyleBoxOpenClose(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox styleBoxOpenClose, TextField inputGridPageURL, Text statusInfo, TextField inputSearch, TextField inputEmailAdress, String xpathPattern1, String xpathPattern2, Properties Homepage, boolean isSuccessful, boolean isAvailable){
        final String infoMessage = styleBoxOpenClose.getText();
        ChangeCheckBox.adjustStyle(false,"progress",styleBoxOpenClose);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0){
                        report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                    }
                    try {
                        Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("page.sidebar.showMoreTags.button"))).getLocation();
                        ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                        ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                        // Scroll up
                        for (int i = 0; i < 1; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,-400)");
                        }
                        webDriver.findElementByXPath(Homepage.getProperty("page.sidebar.showMoreTags.button")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.sidebar.showMoreTags.button"))));
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageStyleBoxOpenClose1.png");
                        if (isSuccessful){
                            report.writeToFile("GridPage See More: ", "Screenshot successful!");
                            failedTestCases.writeToNamedFile("For checking \"See More\" functionality  in style box, see GridPageStyleBoxOpenClose1", "See GridPageStyleBoxOpenClose1, Style box should be expanded", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 14","FailAndReview");
                        }else {
                            report.writeToFile("GridPage See More: ", "Screenshot not successful!");
                            failedTestCases.writeToNamedFile("For checking \"See More\" functionality  in style box, see GridPageStyleBoxOpenClose1", "Screenshot not successful!", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 14","FailAndReview");
                        }
                        webDriver.findElementByXPath(Homepage.getProperty("page.sidebar.showLessTags.button")).click();
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
                            failedTestCases.writeToNamedFile("For checking \"See Less\" functionality  in style box, see GridPageStyleBoxOpenClose2", "See GridPageStyleBoxOpenClose2, Style box should not be expanded", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 14","FailAndReview");
                        }else {
                            report.writeToFile("GridPage See Less: ", "Screenshot not successful!");
                            failedTestCases.writeToNamedFile("For checking \"See Less\" functionality  in style box, see GridPageStyleBoxOpenClose2", "Screenshot not successful!", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 14","FailAndReview");
                        }
                        ChangeCheckBox.adjustStyle(true,"complete",styleBoxOpenClose);
                        report.writeToFile(infoMessage, "Successful! Style Box- Open/Close working as expected");
                    }catch (Exception noStyleBoxOpenCloseFound){
                        ChangeCheckBox.adjustStyle(true,"nope",styleBoxOpenClose);
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorStyleBoxOpenClose.png");
                        if (isSuccessful){
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                            failedTestCases.writeToNamedFile("For more information about the Style box functionality error (Open/Close), see GridPageErrorStyleBoxOpenClose", "See GridPageErrorStyleBoxOpenClose, Style box should not be expanded", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 14","FailAndReview");
                        }else {
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                            failedTestCases.writeToNamedFile("For more information about the Style box functionality error (Open/Close), see GridPageErrorStyleBoxOpenClose", "Screenshot not successful!", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 14","FailAndReview");
                        }
                        report.writeToFile(infoMessage, "Couldn't find any Show-More Button");
                        failedTestCases.writeToNamedFile(infoMessage, "Please check: couldn't find see-more button on style box", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 14","FailAndReview");
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",styleBoxOpenClose);
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorStyleBoxOpenClose.png");
                    if (isSuccessful){
                        report.writeToFile("For more information about the Style box functionality error, see GridPageErrorStyleBoxOpenClose", "Screenshot successful!");                failedTestCases.writeToNamedFile("=================================TC 14","FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 14","FailAndReview");
                    }else {
                        report.writeToFile("For more information about the Style box functionality error, see GridPageErrorStyleBoxOpenClose", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("=================================TC 14","FailAndReview");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Sorting on this Page doesn't seems to be working or very slow");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noMainMenuLinkFound){
                ChangeCheckBox.adjustStyle(true,"nope",styleBoxOpenClose);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please Check: couldn't navigate to Style Box link", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 14","FailAndReview");
                noMainMenuLinkFound.printStackTrace();
            }
        }catch (Exception noCategoryLinksLeftSideMenu){
            ChangeCheckBox.adjustStyle(true,"nope",styleBoxOpenClose);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Check style box: browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 14","FailAndReview");
            noCategoryLinksLeftSideMenu.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
    public void checkingFilterApply(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox filtersApply, TextField inputGridPageURL, Text statusInfo, TextField inputSearch, Properties Homepage, boolean isSuccessful, boolean isAvailable, JFXCheckBox checkingSalesPriceFilter, JFXCheckBox checkingGenderFilter, JFXCheckBox checkingColorFilter, JFXCheckBox checkingBrandFilter, JFXCheckBox checkingMerchandiseFilter){
        final String infoMessage = filtersApply.getText();
        ChangeCheckBox.adjustStyle(false,"progress",filtersApply);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0){
                        report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                    }
                    int MAX_FILTERS_TO_APPLY = 3;
                    if (checkingSalesPriceFilter.isSelected() &&  MAX_FILTERS_TO_APPLY >= 1 ){
                        final String reportInfoCurrentFilter = "Apply Sales Price: ";
                        final String xPathFilterApply = Homepage.getProperty("page.sidebar.salesprice");
                        final JFXCheckBox checkBoxToApplyChanges = checkingSalesPriceFilter;
                        Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #eef442"));
                        try {
                            if(webDriver.findElements(By.xpath(xPathFilterApply)).size() > 0){
                                final String urlLocationBefore = webDriver.getCurrentUrl();
                                Point hoverItem = webDriver.findElement(By.xpath(xPathFilterApply)).getLocation();
                                ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                                ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                                if (!webDriver.getCurrentUrl().contains(webDriver.findElementByXPath(xPathFilterApply).getText().toLowerCase())){
                                    webDriver.findElementByXPath(xPathFilterApply).click();
                                    if (urlLocationBefore != webDriver.getCurrentUrl()){
                                        Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #CCFF99"));
                                        report.writeToFile(reportInfoCurrentFilter, "Successful!");
                                        --MAX_FILTERS_TO_APPLY;
                                    }else {
                                        Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #FF0000"));
                                        report.writeToFile(reportInfoCurrentFilter, "unable to apply Sales filters! Clicked Button but Frontend doesn't response!");
                                        failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check: unable to apply filter on Grid page- frontend does not respond", "FailAndReview");
                                    }
                                }else {
                                    Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #CCFF99"));
                                    report.writeToFile(reportInfoCurrentFilter, "Successful! (Filter was already selected!)");
                                    --MAX_FILTERS_TO_APPLY;
                                }
                            }else {
                                report.writeToFile(reportInfoCurrentFilter, "unable to check! Couldn't find Filter");
                                failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check Sales filters: unable to apply filter on Grid page- could not find filters", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                            }
                        }catch (Exception FilterSelectedError){
                            report.writeToFile(reportInfoCurrentFilter, "Something goes wrong, couldn't apply Filter!");
                            failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check Sales filters: unable to apply filter on Grid page", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                        }
                    }
                    if (checkingGenderFilter.isSelected() &&  MAX_FILTERS_TO_APPLY >= 1 ){
                        final String reportInfoCurrentFilter = "Apply Gender Filter: ";
                        final String xPathFilterApply = Homepage.getProperty("page.sidebar.gender");
                        final JFXCheckBox checkBoxToApplyChanges = checkingGenderFilter;

                        Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #eef442"));
                        try {
                            if(webDriver.findElements(By.xpath(xPathFilterApply)).size() > 0){
                                final String urlLocationBefore = webDriver.getCurrentUrl();
                                Point hoverItem = webDriver.findElement(By.xpath(xPathFilterApply)).getLocation();
                                ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                                ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                                if (!webDriver.getCurrentUrl().contains(webDriver.findElementByXPath(xPathFilterApply).getText().toLowerCase())){
                                    webDriver.findElementByXPath(xPathFilterApply).click();
                                    if (urlLocationBefore != webDriver.getCurrentUrl()){
                                        Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #CCFF99"));
                                        report.writeToFile(reportInfoCurrentFilter, "Successful!");
                                        --MAX_FILTERS_TO_APPLY;
                                    }else {
                                        Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #FF0000"));
                                        report.writeToFile(reportInfoCurrentFilter, "unable to apply Gender filters! Clicked Button but Frontend doesn't response!");
                                        failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check: unable to apply gender filter on Grid page- frontend does not respond", "FailAndReview");
                                        failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                                    }
                                }else {
                                    Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #CCFF99"));
                                    report.writeToFile(reportInfoCurrentFilter, "Successful! (Filter was already selected!)");
                                    --MAX_FILTERS_TO_APPLY;
                                }
                            }else {
                                report.writeToFile(reportInfoCurrentFilter, "unable to check! Couldn't find Filter");
                                failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check Gender filters: unable to apply filter on Grid page- could not find filters", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                            }
                        }catch (Exception FilterSelectedError){
                            report.writeToFile(reportInfoCurrentFilter, "Something goes wrong, couldn't apply Filter!");
                            failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check Gender filters: unable to apply filter on Grid page", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                        }
                    }
                    if (checkingColorFilter.isSelected() &&  MAX_FILTERS_TO_APPLY >= 1 ){
                        final String reportInfoCurrentFilter = "Apply Color Filter: ";
                        final String xPathFilterApply = Homepage.getProperty("page.sidebar.color");
                        final JFXCheckBox checkBoxToApplyChanges = checkingColorFilter;

                        Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #eef442"));
                        try {
                            System.out.println(webDriver.findElementByXPath(xPathFilterApply).getText());
                            if(webDriver.findElements(By.xpath(xPathFilterApply)).size() > 0){
                                final String urlLocationBefore = webDriver.getCurrentUrl();
                                Point hoverItem = webDriver.findElement(By.xpath(xPathFilterApply)).getLocation();
                                ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                                ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                                webDriver.findElementByXPath(xPathFilterApply).click();
                                if (urlLocationBefore != webDriver.getCurrentUrl()){
                                    Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #CCFF99"));
                                    report.writeToFile(reportInfoCurrentFilter, "Successful!");
                                    --MAX_FILTERS_TO_APPLY;
                                }else {
                                    Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #FF0000"));
                                    report.writeToFile(reportInfoCurrentFilter, "unable to apply! Clicked Button but Frontend doesn't response!");
                                    failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check Color filters: unable to apply filter on Grid page- frontend does not respond", "FailAndReview");
                                    failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                                }
                            }else {
                                report.writeToFile(reportInfoCurrentFilter, "unable to check! Couldn't find Filter");
                                failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check Color filters: unable to apply filter on Grid page- could not find filters", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                            }
                        }catch (Exception FilterSelectedError){
                            report.writeToFile(reportInfoCurrentFilter, "Something goes wrong, couldn't apply Filter!");
                            failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check Color filters: unable to apply filter on Grid page", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                        }
                    }
                    if (checkingBrandFilter.isSelected() &&  MAX_FILTERS_TO_APPLY >= 1 ){
                        final String reportInfoCurrentFilter = "Apply Brand Filter: ";
                        final String xPathFilterApply = Homepage.getProperty("page.sidebar.brand");
                        final JFXCheckBox checkBoxToApplyChanges = checkingBrandFilter;
                        Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #eef442"));
                        try {
                            if(webDriver.findElements(By.xpath(xPathFilterApply)).size() > 0){
                                final String urlLocationBefore = webDriver.getCurrentUrl();
                                Point hoverItem = webDriver.findElement(By.xpath(xPathFilterApply)).getLocation();
                                ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                                ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                                if (!webDriver.getCurrentUrl().contains(webDriver.findElementByXPath(xPathFilterApply).getText().toLowerCase())){
                                    webDriver.findElementByXPath(xPathFilterApply).click();
                                    if (urlLocationBefore != webDriver.getCurrentUrl()){
                                        Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #CCFF99"));
                                        report.writeToFile(reportInfoCurrentFilter, "Successful!");
                                        --MAX_FILTERS_TO_APPLY;
                                    }else {
                                        Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #FF0000"));
                                        report.writeToFile(reportInfoCurrentFilter, "unable to apply! Clicked Button but Frontend doesn't response!");
                                        failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check brand filters: unable to apply filter on Grid page- frontend does not respond", "FailAndReview");
                                        failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                                    }
                                }else {
                                    Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #CCFF99"));
                                    report.writeToFile(reportInfoCurrentFilter, "Successful! (Filter was already selected!)");
                                    --MAX_FILTERS_TO_APPLY;
                                }
                            }else {
                                report.writeToFile(reportInfoCurrentFilter, "unable to check! Couldn't find Filter");
                                failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check brand filters: unable to apply filter on Grid page- could not find filters", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                            }
                        }catch (Exception FilterSelectedError){
                            report.writeToFile(reportInfoCurrentFilter, "Something goes wrong, couldn't apply Filter!");
                            failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check brand filters: unable to apply filter on Grid page", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                        }
                    }
                    if (checkingMerchandiseFilter.isSelected() &&  MAX_FILTERS_TO_APPLY >= 1 ){
                        final String reportInfoCurrentFilter = "Apply Merchandise Filter: ";
                        final String xPathFilterApply = Homepage.getProperty("page.sidebar.merchandise");
                        final JFXCheckBox checkBoxToApplyChanges = checkingMerchandiseFilter;

                        Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #eef442"));
                        try {
                            if(webDriver.findElements(By.xpath(xPathFilterApply)).size() > 0){
                                final String urlLocationBefore = webDriver.getCurrentUrl();
                                Point hoverItem = webDriver.findElement(By.xpath(xPathFilterApply)).getLocation();
                                ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                                ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                                if (!webDriver.getCurrentUrl().contains(webDriver.findElementByXPath(xPathFilterApply).getText().toLowerCase())){
                                    webDriver.findElementByXPath(xPathFilterApply).click();
                                    if (urlLocationBefore != webDriver.getCurrentUrl()){
                                        Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #CCFF99"));
                                        report.writeToFile(reportInfoCurrentFilter, "Successful!");
                                        --MAX_FILTERS_TO_APPLY;
                                    }else {
                                        Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #FF0000"));
                                        report.writeToFile(reportInfoCurrentFilter, "unable to apply! Clicked Button but Frontend doesn't response!");
                                        failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check Merchandise filters: unable to apply filter on Grid page- frontend does not respond", "FailAndReview");
                                        failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                                    }
                                }else {
                                    Platform.runLater(() -> checkBoxToApplyChanges.setStyle("-fx-background-color: #CCFF99"));
                                    report.writeToFile(reportInfoCurrentFilter, "Successful! (Filter was already selected!)");
                                    --MAX_FILTERS_TO_APPLY;
                                }
                            }else {
                                report.writeToFile(reportInfoCurrentFilter, "unable to check! Couldn't find Filter");
                                failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check Merchandise filters: unable to apply filter on Grid page- could not find filters", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                            }
                        }catch (Exception FilterSelectedError){
                            report.writeToFile(reportInfoCurrentFilter, "Something goes wrong, couldn't apply Filter!");
                            failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check Merchandise filters: unable to apply filter on Grid page", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                        }
                    }
                    if (MAX_FILTERS_TO_APPLY == 0){
                        report.writeToFile("Checking GridPage Filter Apply", "Complete!");
                    }
                    report.writeToFile("");

                    Platform.runLater(() -> {
                        statusInfo.setText("Checking Filters - Remove from Filter Box...");
                    });
                    List<WebElement> MySelectedFilters = webDriver.findElementsByXPath(Homepage.getProperty("page.sidebar.myfilters"));
                    String xPathMyFirstSelected = "";
                    String textFilterForTestCaseRemove = MySelectedFilters.get(0).getText().trim().toLowerCase();
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.sidebar.gender"))).size() > 0){
                        if(webDriver.findElementByXPath(Homepage.getProperty("page.sidebar.salesprice")).getText().trim().toLowerCase().matches(MySelectedFilters.get(0).getText().trim().toLowerCase())
                                &&webDriver.findElementByXPath(Homepage.getProperty("page.sidebar.salesprice")).getText().trim().toLowerCase().length() == MySelectedFilters.get(0).getText().trim().toLowerCase().length() ) {
                            xPathMyFirstSelected = Homepage.getProperty("page.sidebar.salesprice");
                        }
                    }
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.sidebar.gender"))).size() > 0){
                        if(webDriver.findElementByXPath(Homepage.getProperty("page.sidebar.gender")).getText().trim().toLowerCase().matches(MySelectedFilters.get(0).getText().trim().toLowerCase())
                                &&webDriver.findElementByXPath(Homepage.getProperty("page.sidebar.gender")).getText().trim().toLowerCase().length() == MySelectedFilters.get(0).getText().trim().toLowerCase().length() ) {
                            xPathMyFirstSelected = Homepage.getProperty("page.sidebar.gender");
                        }
                    }
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.sidebar.brand"))).size() > 0){
                        if(webDriver.findElementByXPath(Homepage.getProperty("page.sidebar.brand")).getText().trim().toLowerCase().matches(MySelectedFilters.get(0).getText().trim().toLowerCase())
                            &&webDriver.findElementByXPath(Homepage.getProperty("page.sidebar.brand")).getText().trim().toLowerCase().length() == MySelectedFilters.get(0).getText().trim().toLowerCase().length() ) {
                        xPathMyFirstSelected = Homepage.getProperty("page.sidebar.brand");
                        }
                    }
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.sidebar.merchandise"))).size() > 0){
                        if(webDriver.findElementByXPath(Homepage.getProperty("page.sidebar.merchandise")).getText().trim().toLowerCase().matches(MySelectedFilters.get(0).getText().trim().toLowerCase())
                                &&webDriver.findElementByXPath(Homepage.getProperty("page.sidebar.merchandise")).getText().trim().toLowerCase().length() == MySelectedFilters.get(0).getText().trim().toLowerCase().length() ) {
                            xPathMyFirstSelected = Homepage.getProperty("page.sidebar.merchandise");
                        }
                    }
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.sidebar.color"))).size() > 0){
                        if (xPathMyFirstSelected.length() == 0){
                            System.out.println("Couldn't detect first applied filter");
                            xPathMyFirstSelected = Homepage.getProperty("page.sidebar.color");
                        }
                    }
                    try {
                        final String reportInfoCurrentFilter = "First selected Filter \""+textFilterForTestCaseRemove+"\" from FilterBox(top): ";
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.sidebar.myfilters"))));
                        webDriver.findElementByXPath(Homepage.getProperty("page.sidebar.myfilters")).click();
                        if(webDriver.findElements(By.xpath(xPathMyFirstSelected)).size() > 0){
                            final String urlLocationBefore = webDriver.getCurrentUrl();
                            Point hoverItem = webDriver.findElement(By.xpath(xPathMyFirstSelected)).getLocation();
                            ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                            ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                            if (!webDriver.getCurrentUrl().contains(textFilterForTestCaseRemove)){
                                webDriver.findElementByXPath(xPathMyFirstSelected).click();
                                if (urlLocationBefore != webDriver.getCurrentUrl()){
                                    report.writeToFile(reportInfoCurrentFilter, "removed successfully!");
                                }else {
                                    report.writeToFile(reportInfoCurrentFilter, "unable to remove from filter box! Clicked Button but Frontend doesn't response!");
                                    failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check filters- unable to remove from filter box! Clicked Button but Frontend doesn't response!", "FailAndReview");
                                    failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                                }
                            }else {
                                report.writeToFile(reportInfoCurrentFilter, "unable to remove Filter!");
                                failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check filters- unable to remove from filter box!", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                            }
                        }else {
                            report.writeToFile(reportInfoCurrentFilter, "unable to check! Couldn't find Filter");
                            failedTestCases.writeToNamedFile(reportInfoCurrentFilter, "Please check filters- could not find any filter from filter box on Grid Page!", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                        }
                        report.writeToFile("Checking GridPage Filter Remove from Filter Box", "Complete!");
                        report.writeToFile("");


                        try{
                            Platform.runLater(() -> {
                                statusInfo.setText("Checking Filters - Remove from Sidebar...");
                            });

                            System.out.println("Detect all filters from Sidebar");
                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPathMyFirstSelected)));
                            MySelectedFilters = webDriver.findElementsByXPath(Homepage.getProperty("page.sidebar.myfilters"));
                            List<WebElement> GridPadeAppliedFilters = webDriver.findElementsByXPath(Homepage.getProperty("page.sidebar.allfilters"));
                            xPathMyFirstSelected = "";
                            for (WebElement MySelectedFilter : MySelectedFilters){
                                if(webDriver.findElements(By.xpath(Homepage.getProperty("page.sidebar.salesprice"))).size() > 0){
                                    if (MySelectedFilter.getText().trim().toLowerCase().matches(GridPadeAppliedFilters.get(0).getText().trim().toLowerCase())
                                            && webDriver.findElementByXPath(Homepage.getProperty("page.sidebar.salesprice")).getText().trim().toLowerCase().matches(MySelectedFilter.getText().trim().toLowerCase()) ){
                                        xPathMyFirstSelected = Homepage.getProperty("page.sidebar.salesprice");
                                    }
                                }
                                if(webDriver.findElements(By.xpath(Homepage.getProperty("page.sidebar.gender"))).size() > 0){
                                    if (MySelectedFilter.getText().trim().toLowerCase().matches(GridPadeAppliedFilters.get(0).getText().trim().toLowerCase())
                                            && webDriver.findElementByXPath(Homepage.getProperty("page.sidebar.gender")).getText().trim().toLowerCase().matches(MySelectedFilter.getText().trim().toLowerCase()) ){
                                        xPathMyFirstSelected = Homepage.getProperty("page.sidebar.gender");
                                    }
                                }
                                if(webDriver.findElements(By.xpath(Homepage.getProperty("page.sidebar.brand"))).size() > 0){
                                    if (MySelectedFilter.getText().trim().toLowerCase().matches(GridPadeAppliedFilters.get(0).getText().trim().toLowerCase())
                                            && webDriver.findElementByXPath(Homepage.getProperty("page.sidebar.brand")).getText().trim().toLowerCase().matches(MySelectedFilter.getText().trim().toLowerCase()) ){
                                        xPathMyFirstSelected = Homepage.getProperty("page.sidebar.brand");
                                    }
                                }
                                if(webDriver.findElements(By.xpath(Homepage.getProperty("page.sidebar.merchandise"))).size() > 0){
                                    if (MySelectedFilter.getText().trim().toLowerCase().matches(GridPadeAppliedFilters.get(0).getText().trim().toLowerCase())
                                            && webDriver.findElementByXPath(Homepage.getProperty("page.sidebar.merchandise")).getText().trim().toLowerCase().matches(MySelectedFilter.getText().trim().toLowerCase()) ){
                                        System.out.println("Merchandise Detected");
                                        xPathMyFirstSelected = Homepage.getProperty("page.sidebar.merchandsie");
                                    }
                                }
                            }
                            if (xPathMyFirstSelected.length() == 0){
                                System.out.println("Couldn't detect first applied filter");
                                xPathMyFirstSelected = Homepage.getProperty("page.sidebar.color");

                            }


                            final String urlLocationBefore = webDriver.getCurrentUrl();
                            final String textFilterForTestCaseRemoveFromSidebar = GridPadeAppliedFilters.get(0).getText().trim().toLowerCase();
                            final String reportInfoCurrentFilterFromSidebar = "First applied Filter \""+textFilterForTestCaseRemoveFromSidebar+"\" from Sidebar: ";

                            Point hoverItem = GridPadeAppliedFilters.get(0).getLocation();
                            ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                            ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                            if (webDriver.getCurrentUrl().contains(textFilterForTestCaseRemoveFromSidebar)){
                                GridPadeAppliedFilters.get(0).click();
                                if (urlLocationBefore != webDriver.getCurrentUrl()){
                                    webDriver.findElementByXPath(xPathMyFirstSelected).click();
                                    report.writeToFile(reportInfoCurrentFilterFromSidebar, "removed successfully!");
                                }else {
                                    report.writeToFile(reportInfoCurrentFilterFromSidebar, "unable to remove! Clicked Button but Frontend doesn't response!");
                                    failedTestCases.writeToNamedFile(reportInfoCurrentFilterFromSidebar, "Please check filters- unable to remove from side menu! Clicked Button but Frontend doesn't response!", "FailAndReview");

                                }
                            }else {
                                report.writeToFile(reportInfoCurrentFilterFromSidebar, "unable to remove Filter!");
                                failedTestCases.writeToNamedFile(reportInfoCurrentFilterFromSidebar, "Please check filters- unable to remove from side menu!", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                            }
                            report.writeToFile("Checking GridPage Filter Remove from Sidebar", "Complete!");
                            report.writeToFile("");


                            Platform.runLater(() -> {
                                statusInfo.setText("Checking Filters - Remove All from Sidebar...");
                            });
                            try{
                                final String LocationUrlBefore = webDriver.getCurrentUrl();
                                Point hoverItemResetAll = webDriver.findElement(By.xpath(Homepage.getProperty("page.sidebar.allfilters.reset"))).getLocation();
                                ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                                ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItemResetAll.getY())+");");
                                webDriver.findElement(By.xpath(Homepage.getProperty("page.sidebar.allfilters.reset"))).click();
                                if (LocationUrlBefore != webDriver.getCurrentUrl() ){
                                    report.writeToFile("Checking GridPage Remove All Filter from Sidebar: ", "Complete!");
                                }else {
                                    report.writeToFile("Checking GridPage Remove All Filter from Sidebar: ", "Not Successful! Couldn't remove all Filters via RemoveAllButton!");
                                    failedTestCases.writeToNamedFile("Checking GridPage- Remove All Filter from Sidebar: ", "Please check: Not Successful! Couldn't remove all Filters via RemoveAllButton!", "FailAndReview");
                                    failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                                }

                                ChangeCheckBox.adjustStyle(true,"complete",filtersApply);

                            }catch (Exception noResetAllFilters){
                                report.writeToFile("Checking GridPage Remove All Filter from Sidebar: ", "Not Successful! Couldn't find remove all Button!");
                                failedTestCases.writeToNamedFile("Checking GridPage- Remove All Filter from Sidebar: ", "Please check: Not Successful! Couldn't find remove all Button!", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                                ChangeCheckBox.adjustStyle(true,"nope",filtersApply);
                                noResetAllFilters.printStackTrace();
                            }


                        }catch (Exception noAddedPreviousFilter){
                            report.writeToFile("Checking GridPage Filter Remove from Filter Box", "unable to add previous filter!");
                            failedTestCases.writeToNamedFile("Checking GridPage- Remove All Filter from Sidebar: ", "Please check: Not Successful! unable to add previous filter!", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                            ChangeCheckBox.adjustStyle(true,"nope",filtersApply);
                            noAddedPreviousFilter.printStackTrace();
                        }

                    }catch (Exception notRemoveFirstAppliedFilter){
                        ChangeCheckBox.adjustStyle(true,"nope",filtersApply);
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorFiltersRemoveFromFilterBox.png");
                        if (isSuccessful){
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                            failedTestCases.writeToNamedFile("For more information about the filter removal error, see GridPageErrorFiltersRemoveFromFilterBox ", "Screenshot successful! For reference, see GridPageErrorFiltersRemoveFromFilterBox", "FailAndReview");
                        }else {
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                            failedTestCases.writeToNamedFile("For more information about the filter removal error, see GridPageErrorFiltersRemoveFromFilterBox", "Screenshot successful!", "FailAndReview");
                        }
                        webDriver.navigate().to(inputSearch.getText().trim());
                        report.writeToFile("Checking GridPage Remove from Filter Box", "Something goes wrong, couldn't remove Filter!");
                        failedTestCases.writeToNamedFile("Checking GridPage- Remove All Filter from Sidebar: ", "Please check all filter functionality!", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                    }



                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",filtersApply);
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorFiltersApply.png");
                    if (isSuccessful){
                        report.writeToFile("For more information about the filter removal error, see GridPageErrorFiltersApply", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("For more information about the filter removal error, see GridPageErrorFiltersApply ", "Screenshot successful! For reference, see GridPageErrorFiltersApply", "FailAndReview");
                    }else {
                        report.writeToFile("For more information about the filter removal error, see GridPageErrorFiltersApply ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("Grid page: Apply Filters screenshot: ", "Screenshot not successful!", "FailAndReview");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Sorting on this Page doesn't seems to be working or very slow");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check filters functionality on grid page", "FailAndReview");
                    gridPageIssue.printStackTrace();
                    failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                }
            }catch (Exception noMainMenuLinkFound){
                ChangeCheckBox.adjustStyle(true,"nope",filtersApply);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check filters functionality on grid page manually", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
                noMainMenuLinkFound.printStackTrace();
            }
        }catch (Exception noCategoryLinksLeftSideMenu){
            ChangeCheckBox.adjustStyle(true,"nope",filtersApply);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check filters functionality on grid page: browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 15","FailAndReview");
            noCategoryLinksLeftSideMenu.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
    public void checkingSearchBoxInBrandFilter(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox searchBoxInBrandFilter, TextField inputGridPageURL, TextField inputGridPageKeyword,Text statusInfo, TextField inputSearch, TextField inputEmailAdress, String xpathPattern1, String xpathPattern2, Properties Homepage, boolean isSuccessful, boolean isAvailable){
        final String infoMessage = searchBoxInBrandFilter.getText();
        ChangeCheckBox.adjustStyle(false,"progress",searchBoxInBrandFilter);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
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
                    try {
                        Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("page.sidebar.brand.input"))).getLocation();
                        ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                        ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.sidebar.brand.input"))));
                        WebElement element = webDriver.findElement(By.xpath(Homepage.getProperty("page.sidebar.brand.input")));
                        element.sendKeys(inputGridPageKeyword.getText().trim()); // Enter searchAliases without pressing ENTER
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.sidebar.brand.suggestions"))));
                        final String urlLocationBefore = webDriver.getCurrentUrl();
                        webDriver.findElement(By.xpath(Homepage.getProperty("page.sidebar.brand.suggestions"))).click();
                        if (urlLocationBefore != webDriver.getCurrentUrl() && webDriver.getCurrentUrl().contains(inputGridPageKeyword.getText().trim().toLowerCase())){
                            ChangeCheckBox.adjustStyle(true,"complete",searchBoxInBrandFilter);
                            report.writeToFile("Checking GridPage Search Box in Brand Filter: ", "Successful! Redirected to a functioning page containing \""+inputGridPageKeyword.getText().trim().toLowerCase()+"\" in the URL");
                        }else{
                            ChangeCheckBox.adjustStyle(true,"nope",searchBoxInBrandFilter);
                            report.writeToFile(infoMessage, "Not successful! ");
                            failedTestCases.writeToNamedFile(infoMessage, "Please check: Brand search in Filter box not successful", "FailAndReview");
                            isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorSearchInBrandFilter.png");
                            if (isSuccessful){
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                                failedTestCases.writeToNamedFile("For more information about the Brand Search error, see GridPageErrorSearchInBrandFilter ", "Screenshot successful! For reference, see GridPageErrorSearchInBrandFilter", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 16","FailAndReview");
                            }else {
                                report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                                failedTestCases.writeToNamedFile("For more information about the Brand Search error, see GridPageErrorSearchInBrandFilter ", "Screenshot not successful!", "FailAndReview");
                                failedTestCases.writeToNamedFile("=================================TC 16","FailAndReview");
                            }
                        }

                    }catch (Exception noStyleBoxOpenCloseFound){
                        ChangeCheckBox.adjustStyle(true,"nope",searchBoxInBrandFilter);
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorSearchInBrandFilter.png");
                        if (isSuccessful){
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                            failedTestCases.writeToNamedFile("For more information about the Brand Search error, see GridPageErrorSearchInBrandFilter ", "Screenshot successful! For reference, see GridPageErrorSearchInBrandFilter", "FailAndReview");
                        }else {
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                            failedTestCases.writeToNamedFile("For more information about the Brand Search error, see GridPageErrorSearchInBrandFilter: ", "Screenshot not successful!", "FailAndReview");
                        }
                        report.writeToFile(infoMessage, "Couldn't find any Suggestion Box to enter Keyword");
                        failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't find any brand on brand box ", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 16","FailAndReview");
                        noStyleBoxOpenCloseFound.printStackTrace();
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",searchBoxInBrandFilter);
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorSearchInBrandFilter.png");
                    if (isSuccessful){
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("For more information about the Brand Search error, see GridPageErrorSearchInBrandFilter ", "Screenshot successful! For reference, see GridPageErrorSearchInBrandFilter", "FailAndReview");
                    }else {
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("For more information about the Brand Search error, see GridPageErrorSearchInBrandFilter: ", "Screenshot not successful!", "FailAndReview");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Sorting on this Page doesn't seems to be working or very slow");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: Brand Search box on Grid Page doesn't seems to be working or very slow", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 16","FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",searchBoxInBrandFilter);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check grid Page: couldn't navigate to brand filter box", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 16","FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",searchBoxInBrandFilter);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check grid Page brand filter box: Browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 16","FailAndReview");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
    public void checkingSearchBoxInShopFilter(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox searchBoxInShopFilter, TextField inputGridPageURL, TextField inputGridPageKeyword,Text statusInfo, TextField inputSearch, TextField inputEmailAdress, String xpathPattern1, String xpathPattern2, Properties Homepage, boolean isSuccessful, boolean isAvailable){
        final String infoMessage = searchBoxInShopFilter.getText();
        ChangeCheckBox.adjustStyle(false,"progress",searchBoxInShopFilter);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
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
                    try {
                        Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("page.sidebar.shop.input"))).getLocation();
                        ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                        ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.sidebar.shop.input"))));
                        WebElement element = webDriver.findElement(By.xpath(Homepage.getProperty("page.sidebar.shop.input")));
                        element.sendKeys(inputGridPageKeyword.getText().trim()); // Enter searchAliases without pressing ENTER
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.sidebar.shop.suggestions"))));
                        final String urlLocationBefore = webDriver.getCurrentUrl();
                        webDriver.findElement(By.xpath(Homepage.getProperty("page.sidebar.shop.suggestions"))).click();
                        if (urlLocationBefore != webDriver.getCurrentUrl() && webDriver.getCurrentUrl().contains(inputGridPageKeyword.getText().trim().toLowerCase())){
                            ChangeCheckBox.adjustStyle(true,"complete",searchBoxInShopFilter);
                            report.writeToFile(infoMessage, "Successful! Redirected to a functioning page containing \""+inputGridPageKeyword.getText().trim().toLowerCase()+"\" in the URL");
                        }else{
                            ChangeCheckBox.adjustStyle(true,"nope",searchBoxInShopFilter);
                            report.writeToFile(infoMessage, "Not successful! URL is the same or Keyword \""+inputGridPageKeyword.getText().trim().toLowerCase()+"\" couldn't be founded in URL");
                            failedTestCases.writeToNamedFile(infoMessage, "Please check shop filter box: URL of shop is not the same or Keyword \""+inputGridPageKeyword.getText().trim().toLowerCase()+"\" couldn't be founded in URL", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 17","FailAndReview");
                        }

                    }catch (Exception noStyleBoxOpenCloseFound){
                        ChangeCheckBox.adjustStyle(true,"nope",searchBoxInShopFilter);
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorSearchInShopFilter.png");
                        if (isSuccessful){
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                            failedTestCases.writeToNamedFile("For more information about the Shop Search error, see GridPageErrorSearchInShopFilter", "No shop filter box found. Please see GridPageErrorSearchInShopFilter. Screenshot successful. ", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 17","FailAndReview");
                        }else {
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                            failedTestCases.writeToNamedFile("For more information about the Shop Search error, see GridPageErrorSearchInShopFilter", "No shop filter box found. Please see GridPageErrorSearchInShopFilter. Screenshot not successful. ", "FailAndReview");
                            failedTestCases.writeToNamedFile("=================================TC 17","FailAndReview");
                        }
                        report.writeToFile("Checking GridPage Search Box in Shop Filter: ", "Couldn't find any Suggestion Box to enter Keyword");
                        failedTestCases.writeToNamedFile("For more information about the Shop Search error, see GridPageErrorSearchInShopFilter", "Couldn't find shop in shop box ", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 17","FailAndReview");
                        noStyleBoxOpenCloseFound.printStackTrace();
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",searchBoxInShopFilter);
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"GridPageErrorSearchInShopFilter2.png");
                    if (isSuccessful){
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("For more information about the Shop Search error, see GridPageErrorSearchInShopFilter2", "Something is not right. Check screenshot for reference GridPageErrorSearchInShopFilter. Screenshot successful. ", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 17","FailAndReview");
                    }else {
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("For more information about the Shop Search error, see GridPageErrorSearchInShopFilter2", "Something is not right. Check screenshot for reference. Screenshot not successful. ", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 17","FailAndReview");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Sorting on this Page doesn't seems to be working or very slow");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: Shop search in grid page seems not to be working", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 17","FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",searchBoxInShopFilter);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check grid Page: couldnt' navigate to shop filter box", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 17","FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",searchBoxInShopFilter);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check grid shop brand filter box: Browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 17","FailAndReview");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
}