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

public class PageLuceneWithItemsTest {
    Report failedTestCases = new Report();

    public void checkingSorting(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox PageLuceneWithItemsSorting, TextField inputLucenePage, Text statusInfo, TextField inputSearch, Properties Homepage){
        failedTestCases.writeToNamedFile("CHECKING LUCENE PAGE WITH ITEMS", "FailAndReview");
        final String infoMessage = PageLuceneWithItemsSorting.getText();
        ChangeCheckBox.adjustStyle(false,"progress",PageLuceneWithItemsSorting);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
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
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                            try{
                                Platform.runLater(() -> {
                                    statusInfo.setText("Checking Sorting Values from Low to High...");
                                });
                                boolean isSuccessful;

                                List<WebElement> ItemsGridPage = webDriver.findElementsByXPath(Homepage.getProperty("page.items.price"));
                                double checkStartingPriceFirstItem = Double.parseDouble(ItemsGridPage.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                                report.writeToFile("Detected : "+ ItemsGridPage.size() + " items on this Page!");
                                report.writeToFile("");

                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.button"))));
                                webDriver.findElement(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.button"))).click();

                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.options"))));
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.options"))));
                                List<WebElement> DropDownListActions = webDriver.findElementsByXPath(Homepage.getProperty("lucenepage.sort.dropdown.options"));
                                DropDownListActions.get(1).click();
                                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.sort.dropdown.options"))));

                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.price"))));
                                List<WebElement> ItemsGridPageSortingLowToHigh = webDriver.findElementsByXPath(Homepage.getProperty("page.items.price"));

                                double checkPriceLowToHighFirstItem = Double.parseDouble(ItemsGridPageSortingLowToHigh.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                                double checkPriceLowToHighLastItem = Double.parseDouble(ItemsGridPageSortingLowToHigh.get(ItemsGridPageSortingLowToHigh.size()-1).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                                if (checkPriceLowToHighFirstItem < checkStartingPriceFirstItem && webDriver.getCurrentUrl().contains("sort=price_asc")){
                                    report.writeToFile("Successful changed Sorting from Lowest to Highest Price!", "");
                                }
                                if (checkPriceLowToHighFirstItem < checkPriceLowToHighLastItem){
                                    report.writeToFile("Checking Lucene Page with Items Price Lowest to Highest: ", "Successful! First Item Price("+checkPriceLowToHighFirstItem+") is lower than last Item Price("+checkPriceLowToHighLastItem+") !");
                                }else {
                                    report.writeToFile("Checking Lucene Page with Items Price Lowest to Highest: ", "Not Successful! First Item Price("+checkPriceLowToHighFirstItem+") is NOT lower than last Item Price("+checkPriceLowToHighLastItem+") !");
                                    failedTestCases.writeToNamedFile("Checking Lucene Page with Items: Sorting Price Lowest to Highest: ", "Not Successful! First Item Price("+checkPriceLowToHighFirstItem+") is NOT lower than last Item Price("+checkPriceLowToHighLastItem+") !", "FailAndReview");
                                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "LucenePageWithItemLowToHigh.png");
                                    if (isSuccessful){
                                        report.writeToFile("Checking Lucene Page with Items Price Lowest to Highest Screenshot: ", "Screenshot successful!");
                                        failedTestCases.writeToNamedFile("Lucene with items: for more information about the sorting error, see LucenePageWithItemLowToHigh","Screenshot successful!", "FailAndReview");
                                    }else {
                                        report.writeToFile("Checking Lucene Page with Items Price Lowest to Highest Screenshot: ", "Screenshot not successful!");
                                        failedTestCases.writeToNamedFile("Lucene with items: for more information about the sorting error, see LucenePageWithItemLowToHigh","Screenshot not successful!", "FailAndReview");
                                    }
                                }
                                report.writeToFile("");
                                Platform.runLater(() -> {
                                    statusInfo.setText("Checking Sorting Values from High to Low...");
                                });
                                //DropDownButtonSorting
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.button"))));
                                webDriver.findElement(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.button"))).click();
                                //DropDownButtonSorting
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.options"))));
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.options"))));
                                DropDownListActions = webDriver.findElementsByXPath(Homepage.getProperty("lucenepage.sort.dropdown.options"));
                                DropDownListActions.get(2).click();
                                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.options"))));

                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.price"))));
                                List<WebElement> ItemsGridPageSortingHighToLow = webDriver.findElementsByXPath(Homepage.getProperty("page.items.price"));
                                double checkPriceHighToLowFirstItem = Double.parseDouble(ItemsGridPageSortingHighToLow.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                                double checkPriceHighToLowLastItem = Double.parseDouble(ItemsGridPageSortingHighToLow.get(ItemsGridPageSortingHighToLow.size()-1).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                                if (checkPriceHighToLowFirstItem > checkStartingPriceFirstItem && webDriver.getCurrentUrl().contains("sort=price_desc")){
                                    report.writeToFile("Successful changed Sorting from Highest to Lowest Price!", "");
                                }
                                if (checkPriceHighToLowFirstItem > checkPriceHighToLowLastItem){
                                    report.writeToFile("Checking Lucene Page with Items Price Highest to Lowest: ", "Successful! First Item Price("+checkPriceHighToLowFirstItem+") is higher than last Item Price("+checkPriceHighToLowLastItem+") !");
                                }else {
                                    report.writeToFile("Checking Lucene Page with Items Price Highest to Lowest: ", "Not Successful! First Item Price("+checkPriceHighToLowFirstItem+") is NOT higher than last Item Price("+checkPriceHighToLowLastItem+") !");
                                    failedTestCases.writeToNamedFile("Checking Lucene Page with Items: Sorting Price Highest to Lowest: ", "Not Successful! First Item Price("+checkPriceHighToLowFirstItem+") is NOT higher than last Item Price("+checkPriceHighToLowLastItem+") !", "FailAndReview");
                                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "LucenePageWithItemHighToLow.png");
                                    if (isSuccessful){
                                        report.writeToFile("Checking Lucene Page with Items Price Highest to Lowest Screenshot: ", "Screenshot successful!");
                                        failedTestCases.writeToNamedFile("Lucene with items: for more information about the sorting error, see LucenePageWithItemHighToLow","Screenshot successful!", "FailAndReview");
                                    }else {
                                        report.writeToFile("Checking Lucene Page with Items Price Highest to Lowest Screenshot: ", "Screenshot not successful!");
                                        failedTestCases.writeToNamedFile("Lucene with items: for more information about the sorting error, see LucenePageWithItemHighToLow","Screenshot not successful!", "FailAndReview");
                                    }
                                }
                                report.writeToFile("");
                                Platform.runLater(() -> {
                                    statusInfo.setText("Checking Sorting Values Sales Price...");
                                });
                                //DropDownButtonSorting
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.button"))));
                                webDriver.findElement(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.button"))).click();

                                //DropDownButtonSorting
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.options"))));
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.options"))));
                                DropDownListActions = webDriver.findElementsByXPath(Homepage.getProperty("lucenepage.sort.dropdown.options"));
                                DropDownListActions.get(3).click();
                                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("lucenepage.sort.dropdown.options"))));

                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.price"))));
                                List<WebElement> ItemsGridPageSortingSale = webDriver.findElementsByXPath(Homepage.getProperty("page.items.salesprice"));

                                if (ItemsGridPageSortingSale.size() != 0){
                                    double checkPriceItemsGridPageSortingSale = Double.parseDouble(ItemsGridPageSortingSale.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                                    if (checkPriceItemsGridPageSortingSale != checkStartingPriceFirstItem && webDriver.getCurrentUrl().contains("sort=reduced_price_desc")){
                                        report.writeToFile("Successful changed Sorting to Sales!", "");
                                    }
                                    report.writeToFile("Checking Lucene Page with Items Sales Price: ", "Successful! Item contains Sales Price");
                                }else {
                                    report.writeToFile("Checking Lucene Page with Items Sales Price: ", "Not Successful! Couldn't find a item with Sales Price");
                                    failedTestCases.writeToNamedFile("Checking Lucene Page with Items- Sorting Sales Price: ", "Not Successful! Couldn't find a item with Sales Price", "FailAndReview");
                                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "LucenePageWithItemSalesPrice.png");
                                    if (isSuccessful){
                                        report.writeToFile("Checking Lucene Page with Items Price Sales Price Screenshot: ", "Screenshot successful!");
                                        failedTestCases.writeToNamedFile("Lucene with items: for more information about the sorting error, see LucenePageWithItemSalesPrice","Screenshot successful!", "FailAndReview");
                                    }else {
                                        report.writeToFile("Checking Lucene Page with Items Price Sales Price Screenshot: ", "Screenshot not successful!");
                                        failedTestCases.writeToNamedFile("Lucene with items: for more information about the sorting error, see LucenePageWithItemSalesPrice","Screenshot not successful!", "FailAndReview");
                                    }
                                }
                                report.writeToFile("");

                                ChangeCheckBox.adjustStyle(true,"complete",PageLuceneWithItemsSorting);
                                report.writeToFile(infoMessage, "Complete!");
                            }catch (Exception noSortingPossible){
                                ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsSorting);
                                report.writeToFile(infoMessage, "Unable to sort any Items on Lucene Page!");
                                failedTestCases.writeToNamedFile(infoMessage, "Please check: unable to sort items on Lucene Page", "FailAndReview");
                                noSortingPossible.printStackTrace();
                            }

                        }catch (Exception noItemsOnLucenePage){
                            ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsSorting);
                            report.writeToFile(infoMessage, "Unable to find any Items on Lucene Page!");
                            failedTestCases.writeToNamedFile(infoMessage, "Please check: Unable to find any Items on Lucene Page!", "FailAndReview");
                            noItemsOnLucenePage.printStackTrace();
                        }

                    }else{
                        ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsSorting);
                        report.writeToFile(infoMessage, "Unable to Complete! Couldn't find Lucene Page");
                        failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't find Lucene Page!", "FailAndReview");
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsSorting);
                    boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "GridPageErrorPagingWindows.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Lucene Page with Items Error Screenshot: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("Lucene with items: for more information about the Paging error, see GridPageErrorPagingWindows","Screenshot successful!", "FailAndReview");
                    }else {
                        report.writeToFile("Checking Lucene Page with Items Error Screenshot: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("Lucene with items: for more information about the Paging error, see GridPageErrorPagingWindows","Screenshot not successful!", "FailAndReview");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Could find any Searchbar");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: could not find Searchbar on Lucene page with items.", "FailAndReview" );
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsSorting);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't navigate to requested Site!", "FailAndReview" );
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsSorting);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Lucene page with items: Browser not responding!", "FailAndReview" );
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");

    }

    public void checkingCollapse(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox PageLuceneWithItemsCollapse, TextField inputLucenePage, Text statusInfo, TextField inputSearch, Properties Homepage){
        final String infoMessage = PageLuceneWithItemsCollapse.getText();
        ChangeCheckBox.adjustStyle(false,"progress",PageLuceneWithItemsCollapse);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
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
                            List<WebElement> FilterBoxes = webDriver.findElementsByXPath(Homepage.getProperty("lucenepage.sidebar.boxes"));
                            if (FilterBoxes.size() != 0){
                                for (int i = 0 ; i < FilterBoxes.size() ; i ++){
                                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("lucenepage.sidebar.boxes"))));
                                    FilterBoxes.get(i).click();
                                    FilterBoxes = webDriver.findElementsByXPath(Homepage.getProperty("lucenepage.sidebar.boxes"));
                                }
                                isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "LucenePageCollapseFilters.png");
                                if (isSuccessful){
                                    report.writeToFile("Checking Lucene Page Collapse Filters Screenshot: ", "Screenshot successful!");
                                    failedTestCases.writeToNamedFile("Please, see LucenePageCollapseFilters for checking Filter box functionality: filter box should be collapsed.","Screenshot successful!", "FailAndReview");
                                }else {
                                    report.writeToFile("Checking Lucene Page Collapse Filters Screenshot: ", "Screenshot not successful!");
                                    failedTestCases.writeToNamedFile("Please, see LucenePageCollapseFilters for checking Filter box functionality: filter box should be collapsed.","Screenshot not successful!", "FailAndReview");
                                }
                                ChangeCheckBox.adjustStyle(true,"complete",PageLuceneWithItemsCollapse);
                                report.writeToFile(infoMessage, "Complete!");
                            }else {
                                ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsCollapse);
                                isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "LucenePageCollapseFilters.png");
                                if (isSuccessful){
                                    report.writeToFile("Checking Lucene Page Collapse Filters Error: ", "Screenshot successful!");
                                    failedTestCases.writeToNamedFile("Please, see LucenePageCollapseFilters for checking Filter box functionality: filter box should be collapsed.","Screenshot successful!", "FailAndReview");
                                }else {
                                    report.writeToFile("Checking Lucene Page Collapse Filters Error: ", "Screenshot not successful!");
                                    failedTestCases.writeToNamedFile("Please, see LucenePageCollapseFilters for checking Filter box functionality: filter box should be collapsed.","Screenshot not successful!", "FailAndReview");
                                }
                                report.writeToFile(infoMessage, "Unable to complete! Couldn't detect Filter Boxes");
                                failedTestCases.writeToNamedFile(infoMessage, "Please check filter box functionality on Lucene page: unable to find filter box", "FailAndReview");
                            }
                        }catch (Exception noSortingPossible){
                            ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsCollapse);
                            isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "LucenePageCollapseFilters.png");
                            if (isSuccessful){
                                report.writeToFile("Checking Lucene Page Collapse Filters Error: ", "Screenshot successful!");
                                failedTestCases.writeToNamedFile("Please, see LucenePageCollapseFilters for checking Filter box functionality: filter box should be collapsed.","Screenshot successful!", "FailAndReview");
                            }else {
                                report.writeToFile("Checking Lucene Page Collapse Filters Error: ", "Screenshot not successful!");
                                failedTestCases.writeToNamedFile("Please, see LucenePageCollapseFilters for checking Filter box functionality: filter box should be collapsed.","Screenshot not successful!", "FailAndReview");
                            }
                            report.writeToFile(infoMessage, "Unable to collapse any Filters on Lucene Page!");
                            failedTestCases.writeToNamedFile(infoMessage, "Please check filter box functionality on Lucene page: unable to collapse filters", "FailAndReview");
                            noSortingPossible.printStackTrace();
                        }
                    }else{
                        ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsCollapse);
                        report.writeToFile(infoMessage, "Unable to Complete! Couldn't find Lucene Page");
                        failedTestCases.writeToNamedFile(infoMessage, "Please check filter box functionality on Lucene page: Couldn't find Lucene Page", "FailAndReview");
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsCollapse);
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "GridPageErrorPagingWindows.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Lucene Page Collapse Filters Error Screenshot: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("Please, see GridPageErrorPagingWindows for checking Filter box functionality: filter box should be collapsed.","Screenshot successful!", "FailAndReview");
                    }else {
                        report.writeToFile("Checking Lucene Page Collapse Filters Error Screenshot: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("Please, see GridPageErrorPagingWindows for checking Filter box functionality: filter box should be collapsed.","Screenshot not successful!", "FailAndReview");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Could find any Searchbar to enter Keyword for Lucene Page");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check Lucene page: Could find any Searchbar to enter Keyword for Lucene Page", "FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsCollapse);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check Lucene page: Couldn't navigate to side bar", "FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsCollapse);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check sidebar on Lucene page: Browser not responding", "FailAndReview");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");
    }

    public void checkingMultiselect(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox PageLuceneWithItemsMultiSelect, TextField inputLucenePage, Text statusInfo, TextField inputSearch, Properties Homepage){
        final String infoMessage = PageLuceneWithItemsMultiSelect.getText();
        ChangeCheckBox.adjustStyle(false,"progress",PageLuceneWithItemsMultiSelect);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
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
                                    //scroll to
                                    Point hoverItem = FilterBoxColorsUnchecked.get(0).getLocation();
                                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                                    FilterBoxColorsUnchecked.get(0).click();
                                    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.price"))));
                                    FilterBoxColorsUnchecked = webDriver.findElementsByXPath(Homepage.getProperty("lucenepage.sidebar.boxes.colors.unchecked"));
                                    selectColorCounter++;
                                }
                                if (selectColorCounter >= 5){
                                    ChangeCheckBox.adjustStyle(true,"complete",PageLuceneWithItemsMultiSelect);
                                    report.writeToFile(infoMessage, "Complete!");
                                }else {
                                    ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsMultiSelect);
                                    report.writeToFile(infoMessage, "Not Complete! Couldn't select more than "+selectColorCounter+" color Filters");
                                    failedTestCases.writeToNamedFile(infoMessage, "Please check multiselection functionality on Lucene! Couldn't select more than "+selectColorCounter+" color Filters", "FailAndReview");
                                }
                            }else {
                                ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsMultiSelect);
                                isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "LucenePageCollapseFilters.png");
                                if (isSuccessful){
                                    report.writeToFile("Checking Lucene Page Multiselect Filters Error: ", "Screenshot successful!");
                                    failedTestCases.writeToNamedFile("Please, see LucenePageCollapseFilters for more information on Multiselect error","Screenshot successful!", "FailAndReview");
                                }else {
                                    report.writeToFile("Checking Lucene Page Multiselect Filters Error: ", "Screenshot not successful!");
                                    failedTestCases.writeToNamedFile("Please, see LucenePageCollapseFilters for more information on Multiselect error","Screenshot not successful!", "FailAndReview");
                                }
                                report.writeToFile(infoMessage, "Unable to detect colors on Lucene Page");
                                failedTestCases.writeToNamedFile(infoMessage, "Please check multiselect functionality on Lucene Page: Unable to detect colors on Lucene Page", "FailAndReview");
                            }
                        }catch (Exception noSortingPossible){
                            ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsMultiSelect);
                            isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "LucenePageCollapseFilters.png");
                            if (isSuccessful){
                                report.writeToFile("Checking Lucene Page Multiselect Filters Error: ", "Screenshot successful!");
                                failedTestCases.writeToNamedFile("Please, see LucenePageCollapseFilters for more information on Multiselect error","Screenshot successful!", "FailAndReview");
                            }else {
                                report.writeToFile("Checking Lucene Page Multiselect Filters Error: ", "Screenshot not successful!");
                                failedTestCases.writeToNamedFile("Please, see LucenePageCollapseFilters for more information on Multiselect error","Screenshot not successful!", "FailAndReview");
                            }
                            report.writeToFile(infoMessage, "Unable to complete! Couldn't detect Filter Boxes!");
                            failedTestCases.writeToNamedFile(infoMessage, "Please check multiselect functionality on Lucene Page: Couldn't detect Filter Boxes", "FailAndReview");
                            noSortingPossible.printStackTrace();
                        }
                    }else{
                        ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsMultiSelect);
                        report.writeToFile("Checking Lucene Page Multiselect Filters: ", "Unable to Complete! Couldn't find Lucene Page");
                        failedTestCases.writeToNamedFile( "Checking Lucene Page Multiselect Filters: ", "Please check multiselect functionality on Lucene Page: Couldn't find Lucene Page", "FailAndReview");
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsMultiSelect);
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "GridPageErrorPagingWindows.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Lucene Page Multiselect Filters Error Screenshot: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("Please, see GridPageErrorPagingWindows for more information on Multiselect error","Screenshot successful!", "FailAndReview");
                    }else {
                        report.writeToFile("Checking Lucene Page Multiselect Filters Error Screenshot: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("Please, see GridPageErrorPagingWindows for more information on Multiselect error","Screenshot noy successful!", "FailAndReview");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Could find any Searchbar to enter Keyword for Lucene Page");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check multiselect functionality on Lucene Page: Could not find any Searchbar to enter Keyword for Lucene Page", "FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsMultiSelect);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check multiselect functionality on Lucene Page: Couldn't navigate to Filter Boxes", "FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",PageLuceneWithItemsMultiSelect);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check multiselect functionality on Lucene Page: Browser not responding", "FailAndReview");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");
    }
}
