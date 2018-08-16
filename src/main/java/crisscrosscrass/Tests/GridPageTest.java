package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tasks.Report;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GridPageTest {

    public void checkingSortingValuesLowToHigh(ChromeDriver webDriver, Report report, JFXCheckBox checkSortingValuesOnGridPageLowToHigh, Text statusInfo, TextField inputSearch, String xpathPattern1, Properties Homepage, boolean isSuccessful, boolean isAvailable){
        // Check on Category Links - Left Side Menu
        Platform.runLater(() -> {
            checkSortingValuesOnGridPageLowToHigh.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking GridPage Sorting Values...");
        });
        xpathPattern1 = Homepage.getProperty("page.main.category.links.left");
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                List<WebElement> MainMenu = webDriver.findElementsByXPath(Homepage.getProperty("page.main.links"));
                MainMenu.get(0).click();
                System.out.println("Finished clicked first Menu Link!");
                Platform.runLater(() -> {
                    checkSortingValuesOnGridPageLowToHigh.setStyle("-fx-background-color: #CCFF99");
                    checkSortingValuesOnGridPageLowToHigh.setSelected(true);
                });
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile("Checking GridPage Sorting Values: ", "Complete!");


            }catch (Exception noMainMenuLinkFound){
                Platform.runLater(() -> {
                    checkSortingValuesOnGridPageLowToHigh.setStyle("-fx-background-color: #FF0000");
                    checkSortingValuesOnGridPageLowToHigh.setSelected(true);
                });
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile("Checking GridPage Sorting Values: ", "Couldn't find a Link from Main Menu!");
            }
        }catch (Exception noCategoryLinksLeftSideMenu){
            Platform.runLater(() -> {
                checkSortingValuesOnGridPageLowToHigh.setStyle("-fx-background-color: #FF0000");
                checkSortingValuesOnGridPageLowToHigh.setSelected(true);
            });
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile("Checking GridPage Sorting Values: ", "unable to check! Browser not responding");
        }

        report.writeToFile("=================================", "");
    }
}
