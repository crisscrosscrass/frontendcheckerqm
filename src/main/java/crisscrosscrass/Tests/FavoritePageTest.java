package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import crisscrosscrass.Tasks.ChangeCheckBox;
import crisscrosscrass.Tasks.Report;
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

public class FavoritePageTest {
    public void PersonalListTest(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox PersonalList, Text statusInfo, TextField inputSearch, Properties Homepage, JFXTextField inputAccountEmail, JFXPasswordField inputAccountPassword){
        final String infoMessage = "Checking Personal List";
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
}
