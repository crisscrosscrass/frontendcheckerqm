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
        final double checkStartingPriceFirstItem;
        final double checkPriceLowToHighFirstItem;
        final double checkPriceLowToHighLastItem;

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
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'gridProducts')]/div")));
                    System.out.println("Waiting finished!");
                    List<WebElement> SalesElements = webDriver.findElementsByXPath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]");
                    System.out.println("Detected : "+ SalesElements.size() + " items!");
                    checkStartingPriceFirstItem = Double.parseDouble(SalesElements.get(0).getText().replaceAll("\\s+\\€*\\**.*$","").trim().replace(",","."));
                    System.out.println(checkStartingPriceFirstItem);
                    webDriver.findElement(By.xpath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]")).click();
                    List<WebElement> DropDownListActions = webDriver.findElementsByXPath("//*[contains(@class, 'sort-btn btn-dropdown-wrap')]/div/div/a");
                    DropDownListActions.get(1).click();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'gridProducts')]/div")));
                    List<WebElement> SalesElementsLowToHigh = webDriver.findElementsByXPath("//*[contains(@class, 'gridProducts')]/div/div/a/div[contains(@class, 'price')]");
                    checkPriceLowToHighFirstItem = Double.parseDouble(SalesElementsLowToHigh.get(0).getText().replaceAll("\\s+\\€*\\**.*$","").trim().replace(",","."));
                    checkPriceLowToHighLastItem = Double.parseDouble(SalesElementsLowToHigh.get(SalesElementsLowToHigh.size()-1).getText().replaceAll("\\s+\\€*\\**.*$","").trim().replace(",","."));
                    if (checkPriceLowToHighFirstItem < checkStartingPriceFirstItem){
                        report.writeToFile("Successful changed Sorting", "from Lowest to Highest Price!");
                    }
                    if (checkPriceLowToHighFirstItem < checkPriceLowToHighLastItem){
                        report.writeToFile("Checking  Grid Page Price Lowest to Highest: ", "Successful! First Item Price is lower than Last Item Price !");
                    }else {
                        report.writeToFile("Checking  Grid Page Price Lowest to Highest: ", "Not Successful! First Item Price is NOT lower than Last Item Price !");
                    }
                    Platform.runLater(() -> {
                        sortingValuesOnGridPageHighToLow.setStyle("-fx-background-color: #CCFF99");
                        sortingValuesOnGridPageHighToLow.setSelected(true);
                    });
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile("Checking GridPage Sorting Values: ", "Complete!");
                }catch (Exception windowBoxIssue){
                    Platform.runLater(() -> {
                        sortingValuesOnGridPageHighToLow.setStyle("-fx-background-color: #CCFF99");
                        sortingValuesOnGridPageHighToLow.setSelected(true);
                    });
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile("Checking GridPage Sorting Values: ", "Couldn't find a for Remove Window!");
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
