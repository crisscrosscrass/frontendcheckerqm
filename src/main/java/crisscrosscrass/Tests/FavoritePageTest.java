package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tasks.ChangeCheckBox;
import crisscrosscrass.Tasks.Report;
import javafx.application.Platform;
import javafx.scene.control.PasswordField;
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

public class FavoritePageTest {
    Report failedTestCases = new Report();



    public void checkingAddingToList(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox AddingToList, Text statusInfo, TextField inputGridPageURL, Properties Homepage, TextField inputAccountEmail, PasswordField inputAccountPassword){
        failedTestCases.writeToNamedFile("CHECKING FAVORITE PAGE", "FailAndReview");




        final String infoMessage = AddingToList.getText();
        ChangeCheckBox.adjustStyle(false,"progress",AddingToList);
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

                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0) {
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                    }
                        //User needs to Login first!
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.main.myaccount"))));
                        webDriver.findElementByXPath(Homepage.getProperty("page.main.myaccount")).click();
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.myaccount.button.toLogin"))));
                        webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.button.toLogin")).click();
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.button.toRegister"))));
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.emailInput"))));
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.passwordInput"))));

                        WebElement element = webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.emailInput"));
                        element.sendKeys(inputAccountEmail.getText());
                        element = webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.passwordInput"));
                        element.sendKeys(inputAccountPassword.getText());
                        webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.login.button")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));


                        //go Back to First Page
                        webDriver.navigate().to(inputGridPageURL.getText().trim());

                        //check again if Windows exist and remove them
                        if (webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0) {
                            webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                        }

                        //add items to Favorites
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.favorite.final"))));


                        Point hoverOpener = webDriver.findElement(By.xpath(Homepage.getProperty("page.items.favorite.final"))).getLocation();
                        ((JavascriptExecutor) webDriver).executeScript("return window.title;");
                        ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0," + (hoverOpener.getY()) + ");");
                        List<WebElement> FavoriteListElements = webDriver.findElementsByXPath(Homepage.getProperty("page.items.favorite.final"));


                        for (int i = 0; i <= 5; i++) {

                            FavoriteListElements.get(i).click();

                        }





                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.main.myfavorites"))));
                        Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("page.main.myfavorites"))).getLocation();
                        ((JavascriptExecutor) webDriver).executeScript("return window.title;");
                        ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0," + (hoverItem.getY()) + ");");
                        // Scroll up to element
                        for (int i = 0; i < 1; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,-400)");
                        }
                        webDriver.findElementByXPath(Homepage.getProperty("page.main.myfavorites")).click();

                        int FavoriteItemsNumbers = webDriver.findElements(By.xpath(Homepage.getProperty("page.items.favorite.final"))).size();
                    if(FavoriteItemsNumbers != 0) {
                        report.writeToFile("Checking Favorite Page: ", "Items added successfully!");
                        report.writeToFile(""+FavoriteItemsNumbers+" items added to favorite list!");
                        ChangeCheckBox.adjustStyle(true,"complete",AddingToList);
                    }else{
                        failedTestCases.writeToNamedFile("Please check Favorite Page: ", "Items were not successfully added", "FailAndReview");
                        failedTestCases.writeToNamedFile("=================================TC 58","FailAndReview");
                        ChangeCheckBox.adjustStyle(true,"nope",AddingToList);
                    }





                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",AddingToList);
                    webDriver.findElementByXPath(Homepage.getProperty("page.main.myfavorites")).click();
                    report.writeToFile(infoMessage, "Couldn't detect Favorite Lists");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: couldn't find items in favorite page", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 58&59 ","FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",AddingToList);
                webDriver.findElementByXPath(Homepage.getProperty("page.main.myfavorites")).click();
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: couldn't navigate to Favorite Page", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 58&59","FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",AddingToList);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Favorite List: browser not responding", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 58&59","FailAndReview");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }

public void checkingApplySortingOnList(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox applySortingOnList, Text statusInfo, TextField inputGridPageURL, Properties Homepage, TextField inputAccountEmail, PasswordField inputAccountPassword){




    final String infoMessage = applySortingOnList.getText();
    ChangeCheckBox.adjustStyle(false,"progress",applySortingOnList);
    Platform.runLater(() -> {
        statusInfo.setText(""+infoMessage+"...");
    });


    try {
        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(0));
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, 10);
            try{


                /* ---> prices low to high*/
                List<WebElement> ItemsGridPage = webDriver.findElementsByXPath(Homepage.getProperty("page.items.price"));
                double checkStartingPriceFirstItem = Double.parseDouble(ItemsGridPage.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.myaccount.sorting.dropdown.button"))));
                webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.sorting.dropdown.button")).click();
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.myaccount.sorting.dropdown.options"))));
                List<WebElement> DropDownOptions = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.sorting.dropdown.options"));
                DropDownOptions.get(1).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.myaccount.sorting.dropdown.button"))));

                List<WebElement> ItemsGridPageSortingLowToHigh = webDriver.findElementsByXPath(Homepage.getProperty("page.items.price"));

                double checkPriceLowToHighFirstItem = Double.parseDouble(ItemsGridPageSortingLowToHigh.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                double checkPriceLowToHighLastItem = Double.parseDouble(ItemsGridPageSortingLowToHigh.get(ItemsGridPageSortingLowToHigh.size()-1).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                if (webDriver.getCurrentUrl().contains("sort=price_asc")){
                    report.writeToFile("Successful changed Sorting from Lowest to Highest Price!", "");
                }
                if (checkPriceLowToHighFirstItem < checkPriceLowToHighLastItem){
                    report.writeToFile("Checking  Personal List Page Price Lowest to Highest: ", "Successful! First Item Price("+checkPriceLowToHighFirstItem+") is lower than last Item Price("+checkPriceLowToHighLastItem+") !");
                }else {
                    report.writeToFile("Checking  Personal List Price Lowest to Highest: ", "Not Successful! First Item Price("+checkPriceLowToHighFirstItem+") is NOT lower than last Item Price("+checkPriceLowToHighLastItem+") !");
                    failedTestCases.writeToNamedFile("Please check Personal List- Price Sorting from Lowest to Highest: ", "Not Successful! First Item Price("+checkPriceLowToHighFirstItem+") is NOT lower than last Item Price("+checkPriceLowToHighLastItem+") !", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 59.2","FailAndReview");
                }
                report.writeToFile("");

                /* ---> prices High to Low*/
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.myaccount.sorting.dropdown.button"))));
                webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.sorting.dropdown.button")).click();
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.myaccount.sorting.dropdown.options"))));
                DropDownOptions = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.sorting.dropdown.options"));
                DropDownOptions.get(2).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.loader"))));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.myaccount.sorting.dropdown.button"))));

                List<WebElement> ItemsGridPageSortingHighToLow = webDriver.findElementsByXPath(Homepage.getProperty("page.items.price"));
                double checkPriceHighToLowFirstItem = Double.parseDouble(ItemsGridPageSortingHighToLow.get(0).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                double checkPriceHighToLowLastItem = Double.parseDouble(ItemsGridPageSortingHighToLow.get(ItemsGridPageSortingHighToLow.size()-1).getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replaceAll("\\.","").replaceAll(",","."));
                if (webDriver.getCurrentUrl().contains("sort=price_desc")){
                    report.writeToFile("Successful changed Sorting from Highest to Lowest Price!", "");
                }
                if (checkPriceHighToLowFirstItem > checkPriceHighToLowLastItem){
                    report.writeToFile("Checking  Personal List Page Price Highest to Lowest: ", "Successful! First Item Price("+checkPriceHighToLowFirstItem+") is higher than last Item Price("+checkPriceHighToLowLastItem+") !");
                    ChangeCheckBox.adjustStyle(true,"complete",applySortingOnList);
                }else {
                    report.writeToFile("Checking  Personal List Page Price Highest to Lowest: ", "Not Successful! First Item Price("+checkPriceHighToLowFirstItem+") is NOT higher than last Item Price("+checkPriceHighToLowLastItem+") !");
                    failedTestCases.writeToNamedFile("Please check Personal List Page- Price Sorting from Highest to Lowest: ", "Not Successful! First Item Price("+checkPriceHighToLowFirstItem+") is NOT higher than last Item Price("+checkPriceHighToLowLastItem+") !", "FailAndReview");
                    failedTestCases.writeToNamedFile("=================================TC 59.3","FailAndReview");
                    ChangeCheckBox.adjustStyle(true,"nope",applySortingOnList);
                }
                report.writeToFile("");


            }catch (Exception gridPageIssue){
                ChangeCheckBox.adjustStyle(true,"nope",applySortingOnList);
                webDriver.findElementByXPath(Homepage.getProperty("page.main.myfavorites")).click();
                report.writeToFile(infoMessage, "Couldn't detect Favorite Lists");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: couldn't detect price of items in favorite page", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 58&59 sorting","FailAndReview");
                gridPageIssue.printStackTrace();
            }
        }catch (Exception noRequestedSiteFound){
            ChangeCheckBox.adjustStyle(true,"nope",applySortingOnList);
            webDriver.findElementByXPath(Homepage.getProperty("page.main.myfavorites")).click();
            report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
            failedTestCases.writeToNamedFile(infoMessage, "Please check: couldn't navigate to Favorite Page", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 58&59","FailAndReview");
            noRequestedSiteFound.printStackTrace();
        }
    }catch (Exception noBrowserWorking){
        ChangeCheckBox.adjustStyle(true,"nope",applySortingOnList);
        report.writeToFile(infoMessage, "unable to check! Browser not responding");
        failedTestCases.writeToNamedFile(infoMessage, "Please check Favorite List: browser not responding", "FailAndReview");
        failedTestCases.writeToNamedFile("=================================TC 58&59","FailAndReview");
        noBrowserWorking.printStackTrace();
    }

    report.writeToFile("=================================", "");

}

/**Deleting*/


public void checkingDeletingFromList(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox DeletingFromList, Text statusInfo, TextField inputGridPageURL, Properties Homepage, TextField inputAccountEmail, PasswordField inputAccountPassword){




    final String infoMessage = DeletingFromList.getText();
    ChangeCheckBox.adjustStyle(false,"progress",DeletingFromList);
    Platform.runLater(() -> {
        statusInfo.setText(""+infoMessage+"...");
    });


    try {
        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(0));
        try {
            //webDriver.navigate().to(inputGridPageURL.getText().trim());
            WebDriverWait wait = new WebDriverWait(webDriver, 10);
            try{

                int FavoriteItemsNumbers = webDriver.findElements(By.xpath(Homepage.getProperty("page.items.favorite.final"))).size();

                List<WebElement> FavoriteListElementsDelete = webDriver.findElementsByXPath(Homepage.getProperty("page.items.favorite.final"));


                 for (int i = 0; i <= 5; i++) {

                 FavoriteListElementsDelete.get(i).click();}

                 int FavoriteItemsNumberDelete = webDriver.findElements(By.xpath(Homepage.getProperty("page.items.favorite.final"))).size();
                 if(FavoriteItemsNumberDelete < FavoriteItemsNumbers) {
                 ChangeCheckBox.adjustStyle(true,"complete",DeletingFromList);
                     report.writeToFile("Checking Favorite Page: ", "Items deleted successfully!");
                     report.writeToFile("After deletions, The Favorite List has "+FavoriteItemsNumberDelete+" items");
                 }else{
                 ChangeCheckBox.adjustStyle(true,"nope",DeletingFromList);
                 failedTestCases.writeToNamedFile("Please check Favorite Page: ", "Items were not successfully deleted", "FailAndReview");
                 failedTestCases.writeToNamedFile("=================================TC 58","FailAndReview");
                 }



            }catch (Exception gridPageIssue){
                ChangeCheckBox.adjustStyle(true,"nope",DeletingFromList);
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't detect Favorite Lists");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: couldn't find items in favorite page", "FailAndReview");
                failedTestCases.writeToNamedFile("=================================TC 58&59 sorting","FailAndReview");
                gridPageIssue.printStackTrace();
            }
        }catch (Exception noRequestedSiteFound){
            ChangeCheckBox.adjustStyle(true,"nope",DeletingFromList);
            webDriver.navigate().to(inputGridPageURL.getText().trim());
            report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
            failedTestCases.writeToNamedFile(infoMessage, "Please check: couldn't navigate to Favorite Page", "FailAndReview");
            failedTestCases.writeToNamedFile("=================================TC 58&59","FailAndReview");
            noRequestedSiteFound.printStackTrace();
        }
    }catch (Exception noBrowserWorking){
        ChangeCheckBox.adjustStyle(true,"nope",DeletingFromList);
        report.writeToFile(infoMessage, "unable to check! Browser not responding");
        failedTestCases.writeToNamedFile(infoMessage, "Please check Favorite List: browser not responding", "FailAndReview");
        failedTestCases.writeToNamedFile("=================================TC 58&59","FailAndReview");
        noBrowserWorking.printStackTrace();
    }

    report.writeToFile("=================================", "");

}






}