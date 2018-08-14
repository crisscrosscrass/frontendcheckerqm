package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tasks.Report;
import crisscrosscrass.Tasks.ScreenshotViaWebDriver;
import crisscrosscrass.Tasks.WebdriverTab;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class HomepageTest {

    public void checkingCategories(ChromeDriver webDriver, Report report, JFXCheckBox checkCategoryLinksLeftSideMenu, Text statusInfo, TextField inputSearch, String xpathPattern1, Properties Homepage, boolean isSuccessful, boolean isAvailable){


        // Check on Category Links - Left Side Menu
        Platform.runLater(() -> {
            checkCategoryLinksLeftSideMenu.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Category Links...");
        });
        xpathPattern1 = Homepage.getProperty("page.main.category.links.left");
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                if(webDriver.findElements(By.xpath(xpathPattern1)).size() > 0){
                    //call reporting function and say the element was found
                    System.out.println("Found element!");
                    System.out.println(webDriver.findElements(By.xpath(xpathPattern1)).get(0));
                }else{
                    //call reporting function and say the element was Not found
                    //then continue so I can implement my own code if it isn't found
                    System.out.println("No Element found!");
                }
                isAvailable = webDriver.findElementByXPath(xpathPattern1) != null;
                List<WebElement> CategoryLinksLeftSideMenu = webDriver.findElementsByXPath(xpathPattern1);
                WebdriverTab newtab = new WebdriverTab();
                //TODO use CategoryLinksLeftSideMenu.size() instead of 1 for Loop!
                for (int i = 0 ; i < 1 ; i++){
                    webDriver.switchTo().window(tabs.get(0));
                    isSuccessful = newtab.open(webDriver,CategoryLinksLeftSideMenu.get(i).getAttribute("href"),CategoryLinksLeftSideMenu.get(i).getAttribute("textContent").trim());
                    if (isSuccessful){
                        report.writeToFile("TEST CategoryLinksLeftSideMenu "+i+": Successful | ", "found \"" + CategoryLinksLeftSideMenu.get(i).getAttribute("textContent").trim() + "\" Keyword at URL : "+ CategoryLinksLeftSideMenu.get(i).getAttribute("href"));
                    }else {
                        report.writeToFile("TEST CategoryLinksLeftSideMenu "+i+": unable to check! |", "couldn't found \"" + CategoryLinksLeftSideMenu.get(i).getAttribute("textContent").trim() + "\" Keyword in URL : "+ CategoryLinksLeftSideMenu.get(i).getAttribute("href"));
                    }
                }
                Platform.runLater(() -> {
                    checkCategoryLinksLeftSideMenu.setStyle("-fx-background-color: #CCFF99");
                    checkCategoryLinksLeftSideMenu.setSelected(true);
                });
                report.writeToFile("Checking Category Links: ", "Complete!");
            }catch (Exception noLeftSideMenu){
                Platform.runLater(() -> {
                    checkCategoryLinksLeftSideMenu.setStyle("-fx-background-color: #FF0000");
                    checkCategoryLinksLeftSideMenu.setSelected(true);
                });
                report.writeToFile("Checking Category Links: ", "unable to check! No Links found!");
                noLeftSideMenu.printStackTrace();
            }
        }catch (Exception noCategoryLinksLeftSideMenu){
            Platform.runLater(() -> {
                checkCategoryLinksLeftSideMenu.setStyle("-fx-background-color: #FF0000");
                checkCategoryLinksLeftSideMenu.setSelected(true);
            });
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile("Checking Category Links: ", "unable to check! Browser not responding");
        }

        report.writeToFile("=================================", "");
    }

    public void checkingShopOfTheWeek(ChromeDriver webDriver, Report report, JFXCheckBox checkLogoFromShopOfTheWeek, Text statusInfo, TextField inputSearch, String xpathPattern1,String xpathPatternImage1,String xpathPatternImage2, Properties Homepage, boolean isSuccessful, boolean isAvailable){
        // Check on Logo Shop of the Week
        Platform.runLater(() -> {
            checkLogoFromShopOfTheWeek.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Logo Shop of the Week...");
        });
        xpathPattern1 = Homepage.getProperty("page.main.shop.promo.link");
        xpathPatternImage1 = Homepage.getProperty("page.main.shop.promo.image");
        xpathPatternImage2 = Homepage.getProperty("page.grid.shop.image");
        try{
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                isAvailable = webDriver.findElementByXPath(xpathPattern1) != null;
                Point hoverItem = webDriver.findElement(By.xpath(xpathPattern1)).getLocation();
                ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                String ShopOfTheWeekImage = webDriver.findElementByXPath(xpathPatternImage1).getAttribute("src");
                webDriver.findElementByXPath(xpathPattern1).click();
                try{
                    String ShopOfTheWeekGridImage = webDriver.findElementByXPath(xpathPatternImage2).getAttribute("src");
                    if (ShopOfTheWeekImage.contains(ShopOfTheWeekGridImage)){
                        Platform.runLater(() -> {
                            checkLogoFromShopOfTheWeek.setStyle("-fx-background-color: #CCFF99");
                            checkLogoFromShopOfTheWeek.setSelected(true);
                        });
                        report.writeToFile("Checking Logo Shop of the Week: ", "Successful!");
                    }else{
                        Platform.runLater(() -> {
                            checkLogoFromShopOfTheWeek.setStyle("-fx-background-color: #FF0000");
                            checkLogoFromShopOfTheWeek.setSelected(true);
                        });
                        report.writeToFile("Checking Logo Shop of the Week: ", "Not the same image url!");
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"ShopLogoGridPage.png");
                        if (isSuccessful){
                            report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot not successful!");
                        }
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());

                }catch (Exception noLogoFoundOnGrid){
                    report.writeToFile("Checking Logo Shop of the Week: ", "unable to check! No Logo found on GridPage!");
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"ShopLogoGridPage.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    noLogoFoundOnGrid.printStackTrace();
                }


            }catch (Exception noShopLogoFound){
                Platform.runLater(() -> {
                    checkLogoFromShopOfTheWeek.setStyle("-fx-background-color: #FF0000");
                    checkLogoFromShopOfTheWeek.setSelected(true);
                });
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile("Checking Logo Shop of the Week: ", "unable to check! No Elements found!");
                noShopLogoFound.printStackTrace();
            }
        }catch (Exception noShopPromo){
            Platform.runLater(() -> {
                checkLogoFromShopOfTheWeek.setStyle("-fx-background-color: #FF0000");
                checkLogoFromShopOfTheWeek.setSelected(true);
            });
            report.writeToFile("Checking Logo Shop of the Week: ", "unable to check! Browser not responding");
            noShopPromo.printStackTrace();
        }

        report.writeToFile("=================================", "");
    }
    public void checkingShopOfTheWeekCategories(ChromeDriver webDriver, Report report, JFXCheckBox checkCategoryLinksFromShopOfTheWeek, Text statusInfo, TextField inputSearch, String xpathPattern1,String xpathPatternImage1,String xpathPatternImage2, Properties Homepage, boolean isSuccessful, boolean isAvailable){
        // Category Links from Shop of the Week
        Platform.runLater(() -> {
            checkCategoryLinksFromShopOfTheWeek.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Category Links from Shop of the Week ...");
        });
        xpathPattern1 = Homepage.getProperty("page.main.shop.promo.category");
        xpathPatternImage1 = Homepage.getProperty("page.main.shop.promo.image");
        xpathPatternImage2 = Homepage.getProperty("page.grid.shop.image");
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try{
                isAvailable = webDriver.findElementByXPath(xpathPattern1) != null;
                String ShopOfTheWeekImage = webDriver.findElementByXPath(xpathPatternImage1).getAttribute("src");
                List<WebElement> shopCategoryLinks = webDriver.findElementsByXPath(xpathPattern1);
                List<WebElement> shopCategoryNames = webDriver.findElementsByXPath("//*[@id='pagecontent']/*/*[@class='hp-shop-promo']/*[contains(@class, 'hp-shop-promo-categorylink')]/div/a/div/div[1]");
                WebdriverTab newtab = new WebdriverTab();
                //TODO use shopCategoryLinks.size() instead of 1 for Loop!
                for (int i = 0 ; i < 1 ; i++){
                    webDriver.switchTo().window(tabs.get(0));
                    isSuccessful = newtab.open(webDriver,shopCategoryLinks.get(i).getAttribute("href"),shopCategoryNames.get(i).getAttribute("textContent").trim(),ShopOfTheWeekImage,xpathPatternImage2);
                    if (isSuccessful){
                        report.writeToFile("TEST ShopCategoryLinks "+i+": Successful | ", "found \"" + shopCategoryNames.get(i).getAttribute("textContent").trim() + "\" Keyword at URL : "+ shopCategoryLinks.get(i).getAttribute("href") + " and same Image is available");
                    }else {
                        report.writeToFile("TEST ShopCategoryLinks "+i+": unable to check! |", "couldn't found \"" + shopCategoryNames.get(i).getAttribute("textContent").trim() + "\" Keyword in URL : "+ shopCategoryLinks.get(i).getAttribute("href") + " or Image is not available");
                    }
                }
                webDriver.navigate().to(inputSearch.getText().trim());
                Platform.runLater(() -> {
                    checkCategoryLinksFromShopOfTheWeek.setStyle("-fx-background-color: #CCFF99");
                    checkCategoryLinksFromShopOfTheWeek.setSelected(true);
                });
                report.writeToFile("Checking Category Links: ", "Complete!");

            }catch (Exception noShopLogoFound){
                Platform.runLater(() -> {
                    checkCategoryLinksFromShopOfTheWeek.setStyle("-fx-background-color: #FF0000");
                    checkCategoryLinksFromShopOfTheWeek.setSelected(true);
                });
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile("Checking Category Links from Shop of the Week: ", "unable to check! No Elements found!");
                noShopLogoFound.printStackTrace();
            }
        }catch (Exception noShopPromo){
            Platform.runLater(() -> {
                checkCategoryLinksFromShopOfTheWeek.setStyle("-fx-background-color: #FF0000");
                checkCategoryLinksFromShopOfTheWeek.setSelected(true);
            });
            report.writeToFile("Checking Category Links from Shop of the Week: ", "unable to check! Browser not responding");
            noShopPromo.printStackTrace();
        }


        report.writeToFile("=================================", "");
    }
}
