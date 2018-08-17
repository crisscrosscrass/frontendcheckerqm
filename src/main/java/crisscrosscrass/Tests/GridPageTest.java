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
                try{
                    if(webDriver.findElements(By.xpath(xpathPattern1)).size() > 0){
                        report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                        webDriver.findElementByXPath("//*[contains(@class, 'paging right')]/a ").click();
                    }else {
                        System.out.println("No Window Element!");
                    }
                    Platform.runLater(() -> {
                        statusInfo.setText("Checking Sorting Values from Low to High...");
                    });
                    WebDriverWait wait = new WebDriverWait(webDriver, 10);
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'gridProducts')]/div")));
                    try{

                        //checking Sorting
                        List<WebElement> SalesElements = webDriver.findElementsByXPath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]");
                        //TODO
                        for (WebElement SalesElementItem : SalesElements){
                            System.out.println(SalesElementItem.getText().replaceAll("(^\\s?\\€?)|(\\-\\s*\\€?\\d*\\,?\\.?.*)|(\\€?\\*\\s*\\d*\\,?\\.?)$|(\\€?\\*\\s\\d*.*$)|(\\s?\\€?$)","").trim().replace("\\,","."));
                        }
                        double checkStartingPriceFirstItem = Double.parseDouble(SalesElements.get(0).getText().replaceAll("^\\s*\\€","").replaceAll("\\s+\\€*\\**.*$","").replaceAll("\\*+","").trim().replace(",","."));
                        System.out.println(checkStartingPriceFirstItem);
                        //checking LowToHigh
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'gridProducts')]/div")));
                        webDriver.findElement(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]")).click();
                        List<WebElement> DropDownListActions = webDriver.findElementsByXPath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]/div/div/a");
                        DropDownListActions.get(1).click();
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'gridProducts')]/div")));
                        List<WebElement> SalesElementsLowToHigh = webDriver.findElementsByXPath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]");
                        report.writeToFile("Detected : "+ SalesElements.size() + " items on this Page!");
                        System.out.println(SalesElementsLowToHigh.get(0).getText().replaceAll("^\\s*\\€","").replaceAll("\\s*\\€*\\**.*$","").trim().replace(",","."));
                        double checkPriceLowToHighFirstItem = Double.parseDouble(SalesElementsLowToHigh.get(0).getText().replaceAll("^\\s*\\€","").replaceAll("\\s*\\€*\\**.*$","").trim().replace(",","."));
                        double checkPriceLowToHighLastItem = Double.parseDouble(SalesElementsLowToHigh.get(SalesElementsLowToHigh.size()-1).getText().replaceAll("^\\s*\\€","").replaceAll("\\s+\\€*\\**.*$","").replaceAll("\\*+","").trim().replace(",","."));
                        if (checkPriceLowToHighFirstItem < checkStartingPriceFirstItem){
                            report.writeToFile("Successful changed Sorting from Lowest to Highest Price!", "");
                        }
                        if (checkPriceLowToHighFirstItem < checkPriceLowToHighLastItem){
                            report.writeToFile("Checking  Grid Page Price Lowest to Highest: ", "Successful! First Item Price("+checkPriceLowToHighFirstItem+") is lower than last Item Price("+checkPriceLowToHighLastItem+") !");
                        }else {
                            report.writeToFile("Checking  Grid Page Price Lowest to Highest: ", "Not Successful! First Item Price("+checkPriceLowToHighFirstItem+") is NOT lower than last Item Price("+checkPriceLowToHighLastItem+") !");
                        }




                        Platform.runLater(() -> {
                            statusInfo.setText("Checking Sorting Values from High to Low...");
                        });
                        //checking HighToLow
                        System.out.println("Starting HighTolow!");
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]")));
                        System.out.println("Find DropDownList");
                        DropDownListActions = webDriver.findElementsByXPath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]/div/div/a");
                        System.out.println("Click DropDownList");
                        webDriver.findElement(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]")).click();
                        System.out.println("Wait for Grid");
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]/div/div/a")));
                        System.out.println("Click on 3rd item");
                        DropDownListActions.get(2).click();
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'gridProducts')]/div")));
                        List<WebElement> SalesElementsHighToLow = webDriver.findElementsByXPath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]");
                        System.out.println(SalesElementsHighToLow.get(0).getText());
                        System.out.println(Double.parseDouble(SalesElementsHighToLow.get(0).getText().replaceAll("^\\s*\\€","").replaceAll("\\s+\\€*\\**.*$","").replaceAll("\\*+","").trim().replace(".","").replace(",",".")));

                        double checkPriceHighToLowFirstItem = Double.parseDouble(SalesElementsHighToLow.get(0).getText().replaceAll("^\\s*\\€","").replaceAll("\\s+\\€*\\**.*$","").replaceAll("\\*+","").trim().replace(".","").replace(",","."));
                        double checkPriceHighToLowLastItem = Double.parseDouble(SalesElementsHighToLow.get(SalesElementsHighToLow.size()-1).getText().replaceAll("^\\s*\\€","").replaceAll("\\s+\\€*\\**.*$","").replaceAll("\\*+","").trim().replace(".","").replace(",","."));
                        if (checkPriceHighToLowFirstItem > checkStartingPriceFirstItem){
                            report.writeToFile("Successful changed Sorting from Highest to Lowest Price!", "");
                        }
                        if (checkPriceHighToLowFirstItem > checkPriceHighToLowLastItem){
                            report.writeToFile("Checking  Grid Page Price Highest to Lowest: ", "Successful! First Item Price("+SalesElementsHighToLow.get(0).getText().replaceAll("\\s+\\€*\\**.*$","").replaceAll("\\*+","").trim()+") is higher than last Item Price("+SalesElementsHighToLow.get(SalesElementsHighToLow.size()-1).getText().replaceAll("\\s+\\€*\\**.*$","").trim()+") !");
                        }else {
                            report.writeToFile("Checking  Grid Page Price Highest to Lowest: ", "Not Successful! First Item Price("+SalesElementsHighToLow.get(0).getText().replaceAll("\\s+\\€*\\**.*$","").replaceAll("\\*+","").trim()+") is NOT higher than last Item Price("+SalesElementsHighToLow.get(SalesElementsHighToLow.size()-1).getText().replaceAll("\\s+\\€*\\**.*$","").trim()+") !");
                        }



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
                report.writeToFile("Checking GridPage Sorting Values: ", "Couldn't find a Link from Main Menu!");
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
}
