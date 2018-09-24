package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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

public class FavoritePageTest {
    public void checkingPersonalListTest(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox PersonalList, Text statusInfo, TextField inputSearch, Properties Homepage, JFXTextField inputAccountEmail, JFXPasswordField inputAccountPassword){
        final String infoMessage = PersonalList.getText();
        ChangeCheckBox.adjustStyle(false,"progress",PersonalList);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));

            try {
                webDriver.navigate().to(inputSearch.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);

                /************************
                 * Add List Section
                 */
                try{
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


                    String testPatternList1 = "List 1 Test";
                    String testPatternList2 = "List 2 Test";

                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.createNewList"))));
                    webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.createNewList")).click();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalDialogBox"))));
                    webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.modalListNameInput")).sendKeys(testPatternList1);
                    webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.modalSaveButton")).click();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalConfirmToast"))));
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalConfirmToast"))));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.myPersonalList"))));
                    List<WebElement> MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.myPersonalList"));
                    boolean isSuccessful = false;
                    int foundItemAtIndex = 0;

                    for (int i = 0 ; i < MyPersonalList.size() ; i++ ){
                        if (MyPersonalList.get(i).getText().equals(testPatternList1) ){
                            isSuccessful = true;
                            if (i == 0){
                                i = 1;
                            }
                            foundItemAtIndex = (i-1);
                        }
                    }

                    if (isSuccessful){
                        report.writeToFile("Create List: ", "Created successfully a list called \""+testPatternList1+"\"");
                    }else {
                        report.writeToFile("Create List: ", "Couldn't create a list called \""+testPatternList1+"\"");
                    }





                    /************************
                     * Delete List Section
                     */
                    try{
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.editListIcon"))));
                        if (!isSuccessful){
                            report.writeToFile(infoMessage, "Couldn't delete List \""+testPatternList1+"\" created from Previous Test ");
                        }else {
                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.myaccount.editListIcon"))));
                            MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.editListIcon"));
                            MyPersonalList.get(foundItemAtIndex).click();
                            MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.deleteList"));
                            MyPersonalList.get(foundItemAtIndex).click();
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalDialogBox"))));
                            webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.modalSaveButton")).click();
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalConfirmToast"))));
                            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalConfirmToast"))));

                            //check in new personal List if created List Item exist
                            MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.myPersonalList"));

                            if (MyPersonalList.size() == 0){

                            }else {
                                for (int i = 0 ; i < MyPersonalList.size() ; i++ ){
                                    if (MyPersonalList.get(i).getText().equals(testPatternList1) ){
                                        isSuccessful = false;
                                    }
                                }
                            }

                        }

                        if (isSuccessful){
                            report.writeToFile("Delete List : ", "Could delete \""+testPatternList1+"\" successfully from List");
                        }else {
                            report.writeToFile("Delete List :", "Couldn't delete \""+testPatternList1+"\" from List, because Pattern still in List");
                        }





                        /************************
                         * Rename List Section
                         */
                        try{
                            if (!isSuccessful){
                                report.writeToFile(infoMessage, "Couldn't rename List \""+testPatternList1+"\" created from Previous Tests ");
                            }else {

                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.createNewList"))));
                                webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.createNewList")).click();
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalDialogBox"))));
                                webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.modalListNameInput")).sendKeys(testPatternList1);
                                webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.modalSaveButton")).click();
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalConfirmToast"))));
                                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalConfirmToast"))));
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.myPersonalList"))));
                                MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.myPersonalList"));

                                foundItemAtIndex = 0;

                                for (int i = 0 ; i < MyPersonalList.size() ; i++ ){
                                    if (MyPersonalList.get(i).getText().equals(testPatternList1) ){
                                        if (i == 0){
                                            i = 1;
                                        }
                                        foundItemAtIndex = (i-1);
                                    }
                                }

                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.myaccount.editListIcon"))));
                                MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.editListIcon"));
                                MyPersonalList.get(foundItemAtIndex).click();
                                MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.renameList"));
                                MyPersonalList.get(foundItemAtIndex).click();
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalDialogBox"))));
                                webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.modalListNameInput")).sendKeys(testPatternList2);
                                webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.modalSaveButton")).click();
                                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalSaveButton"))));

                                isSuccessful = false;
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.myPersonalList"))));
                                MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.myPersonalList"));

                                for (int i = 0 ; i < MyPersonalList.size() ; i++ ){
                                    if (MyPersonalList.get(i).getText().equals(testPatternList2) ){
                                        isSuccessful = true;
                                    }
                                }

                                if (isSuccessful){
                                    report.writeToFile(infoMessage, "Could rename \""+testPatternList1+"\" successfully from List to  \""+testPatternList2+"\" ");
                                }else {
                                    report.writeToFile(infoMessage, "Couldn't rename \""+testPatternList1+"\" from List, because new Name not exist in List");
                                }





                                /************************
                                 * Go To List Section
                                 */
                                try{
                                    if (!isSuccessful){
                                        report.writeToFile(infoMessage, "Couldn't GoTo List \""+testPatternList1+"\" created from Previous Tests ");
                                    }else {
                                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.createNewList"))));
                                        webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.createNewList")).click();
                                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalDialogBox"))));
                                        webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.modalListNameInput")).sendKeys(testPatternList1);
                                        webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.modalSaveButton")).click();
                                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalConfirmToast"))));
                                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalConfirmToast"))));
                                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.myPersonalList"))));
                                        MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.myPersonalList"));


                                        for (WebElement MyPersonalItem : MyPersonalList){
                                            if (MyPersonalItem.getText().equals(testPatternList1) ){
                                                MyPersonalItem.click();
                                                break;
                                            }
                                        }


                                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.header"))));
                                        isSuccessful = webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.header")).getText().contains(testPatternList1);


                                        if (isSuccessful){
                                            report.writeToFile(infoMessage, "Could create and select \""+testPatternList1+"\" successfully from List ");
                                            ChangeCheckBox.adjustStyle(true,"complete",PersonalList);
                                        }else {
                                            report.writeToFile(infoMessage, "Couldn't rename \""+testPatternList1+"\" from List, because new Name not exist in List");
                                            ChangeCheckBox.adjustStyle(true,"nope",PersonalList);
                                        }
                                    }
                                }catch (Exception noGoToList){
                                    ChangeCheckBox.adjustStyle(true,"nope",PersonalList);
                                    webDriver.navigate().to(inputSearch.getText().trim());
                                    report.writeToFile(infoMessage, "Couldn't detect List for GoTo selected List");
                                    noGoToList.printStackTrace();
                                }

                            }
                        }catch (Exception noRename){
                            ChangeCheckBox.adjustStyle(true,"nope",PersonalList);
                            webDriver.navigate().to(inputSearch.getText().trim());
                            report.writeToFile(infoMessage, "Couldn't detect edit Icon for Personal List");
                            noRename.printStackTrace();
                        }


                    }catch (Exception noEditList){
                        ChangeCheckBox.adjustStyle(true,"nope",PersonalList);
                        webDriver.navigate().to(inputSearch.getText().trim());
                        report.writeToFile(infoMessage, "Couldn't detect edit Icon for Personal List");
                        noEditList.printStackTrace();
                    }

                }catch (Exception accountPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",PersonalList);
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect menu items for Personal List");
                    accountPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",PersonalList);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",PersonalList);
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }

    public void checkingApplySortingOnList(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox applySortingOnList, Text statusInfo, TextField inputGridPageURL, Properties Homepage, JFXTextField inputAccountEmail, JFXPasswordField inputAccountPassword){
        final String infoMessage = applySortingOnList.getText();
        ChangeCheckBox.adjustStyle(false,"progress",applySortingOnList);
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

                    String testPatternList1 = "List 1 Test";

                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0){
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                    }
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.items.favorite.dropdown.opener"))).size() == 0){
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
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.createNewList"))));

                        //go Back to First Page
                        webDriver.navigate().to(inputGridPageURL.getText().trim());

                        //check again if Windows exist and remove them
                        if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0){
                            webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                        }
                    }
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.favorite.dropdown.opener"))));
                    Point hoverOpener = webDriver.findElement(By.xpath(Homepage.getProperty("page.items.favorite.dropdown.opener"))).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverOpener.getY())+");");
                    List<WebElement> FavoriteDropDownButtonElements = webDriver.findElementsByXPath(Homepage.getProperty("page.items.favorite.dropdown.opener"));



                    for (int i = 0 ; i <= 5 ; i++){
                        FavoriteDropDownButtonElements.get(i).click();
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.favorite.dropdown.list"))));
                        List<WebElement> FavoriteDropDownListElements = webDriver.findElementsByXPath(Homepage.getProperty("page.items.favorite.dropdown.list"));
                        for (WebElement DropDownItem : FavoriteDropDownListElements){
                            if (DropDownItem.getText().equals(testPatternList1)){
                                DropDownItem.click();
                                break;
                            }
                        }

                    }
                    report.writeToFile("Successfully added items to Personal List \""+testPatternList1+"\" !");
                    report.writeToFile("");




                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.main.myfavorites"))));
                    Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("page.main.myfavorites"))).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    // Scroll up to element
                    for (int i = 0; i < 1; i++) {
                        Thread.sleep(100);
                        js.executeScript("window.scrollBy(0,-400)");
                    }
                    webDriver.findElementByXPath(Homepage.getProperty("page.main.myfavorites")).click();




                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.myPersonalList"))));
                    List<WebElement> MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.myPersonalList"));

                    for (WebElement MyPersonalItem : MyPersonalList){
                        if (MyPersonalItem.getText().equals(testPatternList1) ){
                            MyPersonalItem.click();
                            break;
                        }
                    }
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.header"))));

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
                    }
                    report.writeToFile("");

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
                    }else {
                        report.writeToFile("Checking  Personal List Page Price Highest to Lowest: ", "Not Successful! First Item Price("+checkPriceHighToLowFirstItem+") is NOT higher than last Item Price("+checkPriceHighToLowLastItem+") !");
                    }
                    report.writeToFile("");


                    ChangeCheckBox.adjustStyle(true,"complete",applySortingOnList);
                    webDriver.navigate().to(inputGridPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Complete");


                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",applySortingOnList);
                    webDriver.navigate().to(inputGridPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect Favorite Lists");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",applySortingOnList);
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",applySortingOnList);
            webDriver.navigate().to(inputGridPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }

    public void checkingSelectionOnList(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox selectionOnList, Text statusInfo, TextField inputSearch, Properties Homepage, JFXTextField inputAccountEmail, JFXPasswordField inputAccountPassword){
        final String infoMessage = selectionOnList.getText();
        ChangeCheckBox.adjustStyle(false,"progress",selectionOnList);
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
                    String testPatternList1 = "List 1 Test";
                    String testPatternList2 = "List 2 Test";

                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.main.myfavorites"))));
                    Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("page.main.myfavorites"))).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    // Scroll up to element
                    for (int i = 0; i < 1; i++) {
                        Thread.sleep(100);
                        js.executeScript("window.scrollBy(0,-400)");
                    }
                    webDriver.findElementByXPath(Homepage.getProperty("page.main.myfavorites")).click();
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.myaccount.myPersonalList"))).size() == 0){
                        //user needs to login first
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
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.createNewList"))));
                    }

                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.myPersonalList"))));
                    List<WebElement> MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.myPersonalList"));

                    for (WebElement MyPersonalItem : MyPersonalList){
                        if (MyPersonalItem.getText().equals(testPatternList1) ){
                            MyPersonalItem.click();
                            break;
                        }
                    }

                    try{
                        int oldAmountOfGreenCheckMarks = 0;

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.header"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.myaccount.selectAllProduct.button"))));
                        if(webDriver.findElements(By.xpath(Homepage.getProperty("page.myaccount.greenCheckMarkBoxes"))).size() == 0){
                            webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.selectAllProduct.button")).click();
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.greenCheckMarkBoxes"))));
                            if(webDriver.findElements(By.xpath(Homepage.getProperty("page.myaccount.greenCheckMarkBoxes"))).size() > 0){
                                report.writeToFile(infoMessage+" GreenCheck Marks: ","Successfully! Green check marks appears on "+webDriver.findElements(By.xpath(Homepage.getProperty("page.myaccount.greenCheckMarkBoxes"))).size()+" items!");
                                oldAmountOfGreenCheckMarks = webDriver.findElements(By.xpath(Homepage.getProperty("page.myaccount.greenCheckMarkBoxes"))).size();
                            }else {
                                report.writeToFile(infoMessage+" GreenCheck Marks: ","Not Successfully! Green check marks appears on "+webDriver.findElements(By.xpath(Homepage.getProperty("page.myaccount.greenCheckMarkBoxes"))).size()+" items!");
                            }
                        }else {
                            report.writeToFile(infoMessage+" GreenCheck Marks: ","Not Successfully! Green check marks appears before selecting on "+webDriver.findElements(By.xpath(Homepage.getProperty("page.myaccount.greenCheckMarkBoxes"))).size()+" items!");
                        }

                        try{
                            boolean isStandardListFull = false;
                            boolean isTestListEmpty = false;

                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.myaccount.moveAllProduct.button"))));
                            webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.moveAllProduct.button")).click();
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.moveToStandardList"))));
                            webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.moveToStandardList")).click();
                            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.moveToStandardList"))));
                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.myaccount.selectAllProduct.button"))));
                            webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.selectAllProduct.button")).click();
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.greenCheckMarkBoxes"))));
                            if (oldAmountOfGreenCheckMarks <= webDriver.findElements(By.xpath(Homepage.getProperty("page.myaccount.greenCheckMarkBoxes"))).size()){
                                isStandardListFull = true;
                            }

                            MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.myPersonalList"));
                            for (WebElement MyPersonalItem : MyPersonalList){
                                if (MyPersonalItem.getText().equals(testPatternList1) ){
                                    MyPersonalItem.click();
                                    break;
                                }
                            }
                            if(webDriver.findElements(By.xpath(Homepage.getProperty("page.myaccount.selectAllProduct.button"))).size() == 0){
                                isTestListEmpty = true;
                            }

                            if (isStandardListFull == isTestListEmpty){
                                report.writeToFile(infoMessage+" Selection Move: ","Successfully check Selection Move! Test List is Empty and Standard List is filled ");
                            }else if (!isStandardListFull){
                                report.writeToFile(infoMessage+" Selection Move: ","Not successful check, Standard List is not filled ");
                            }else {
                                report.writeToFile(infoMessage+" Selection Move: ","Not successful check, Test List is not empty ");
                            }

                            ChangeCheckBox.adjustStyle(true,"complete",selectionOnList);
                            report.writeToFile(infoMessage, "Complete");


                            try{

                                //Delete First created List!
                                MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.myPersonalList"));
                                int foundItemAtIndex = 0;

                                for (int i = 0 ; i < MyPersonalList.size() ; i++ ){
                                    if (MyPersonalList.get(i).getText().equals(testPatternList1) ){
                                        if (i == 0){
                                            i = 1;
                                        }
                                        foundItemAtIndex = (i-1);
                                    }
                                }

                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.myaccount.editListIcon"))));
                                MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.editListIcon"));
                                MyPersonalList.get(foundItemAtIndex).click();
                                MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.deleteList"));
                                MyPersonalList.get(foundItemAtIndex).click();
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalDialogBox"))));
                                webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.modalSaveButton")).click();
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalConfirmToast"))));
                                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalConfirmToast"))));

                                //Delete Second created List!

                                MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.myPersonalList"));
                                foundItemAtIndex = 0;

                                for (int i = 0 ; i < MyPersonalList.size() ; i++ ){
                                    if (MyPersonalList.get(i).getText().equals(testPatternList2) ){
                                        if (i == 0){
                                            i = 1;
                                        }
                                        foundItemAtIndex = (i-1);
                                    }
                                }

                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.myaccount.editListIcon"))));
                                MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.editListIcon"));
                                MyPersonalList.get(foundItemAtIndex).click();
                                MyPersonalList = webDriver.findElementsByXPath(Homepage.getProperty("page.myaccount.deleteList"));
                                MyPersonalList.get(foundItemAtIndex).click();
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalDialogBox"))));
                                webDriver.findElementByXPath(Homepage.getProperty("page.myaccount.modalSaveButton")).click();
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalConfirmToast"))));
                                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.myaccount.modalConfirmToast"))));

                                report.writeToFile(infoMessage, "(Deleted all created lists too! )");

                            }catch (Exception noCleanUp){
                                webDriver.navigate().to(inputSearch.getText().trim());
                                report.writeToFile(infoMessage, "Couldn't delete all created Lists");
                                noCleanUp.printStackTrace();
                            }

                        }catch (Exception noSelectionMove){
                            ChangeCheckBox.adjustStyle(true,"nope",selectionOnList);
                            webDriver.navigate().to(inputSearch.getText().trim());
                            report.writeToFile(infoMessage, "Couldn't move items from Favorite Lists to another List");
                            noSelectionMove.printStackTrace();
                        }
                    }catch (Exception noGreenCheckMark){
                        ChangeCheckBox.adjustStyle(true,"nope",selectionOnList);
                        webDriver.navigate().to(inputSearch.getText().trim());
                        report.writeToFile(infoMessage, "Couldn't detect Favorite Lists");
                        noGreenCheckMark.printStackTrace();
                    }

                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",selectionOnList);
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect Favorite Lists");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",selectionOnList);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",selectionOnList);
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");


    }
}
