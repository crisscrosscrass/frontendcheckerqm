package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tasks.ChangeCheckBox;
import crisscrosscrass.Tasks.Report;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MainMenuOnHomePageTest {
    final static Logger logger = Logger.getLogger(MainMenuOnHomePageTest.class);

    public void checkingMainMenuTabs(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox MainMenuLinkTabs, Text statusInfo, TextField inputSearch, Properties Homepage){
        final String infoMessage = MainMenuLinkTabs.getText();
        ChangeCheckBox.adjustStyle(false,"progress",MainMenuLinkTabs);
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
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.main.links"))));
                    List<WebElement> allMainMenuLinks = webDriver.findElementsByXPath(Homepage.getProperty("page.main.links"));
                    Point scrollItem = allMainMenuLinks.get(0).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(scrollItem.getY())+");");
                    Actions hover = new Actions(webDriver);
                    int counterXPath = 0;
                    for (WebElement MenuLink : allMainMenuLinks){
                        hover.moveToElement(MenuLink).build().perform();
                        logger.info(MenuLink.getText().trim());
                        //dynamic xpath
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='item"+counterXPath+"_tab']/div/div/*[not(contains(@class,'more-categories'))]")));
                        List<WebElement> allMainMenuSubLinks = webDriver.findElementsByXPath("//*[@id='item"+counterXPath+"_tab']/div/div/*[not(contains(@class,'more-categories'))]");
                        logger.info(allMainMenuSubLinks.size());
                        for (WebElement SubMenuLink : allMainMenuSubLinks){
                            hover.moveToElement(SubMenuLink).build().perform();
                            if (null != SubMenuLink.getAttribute("href")){
                                logger.info(SubMenuLink.getAttribute("innerHTML") + " | " + SubMenuLink.getAttribute("href"));
                            }
                        }
                        ++counterXPath;
                    }
                    /*
                     wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.submain.links"))));
                        List<WebElement> allMainMenuSubLinks = webDriver.findElementsByXPath(Homepage.getProperty("page.submain.links"));
                        logger.info(allMainMenuSubLinks.size());
                        for (WebElement SubMenuLink : allMainMenuSubLinks){
                            if (null != SubMenuLink.getAttribute("href")){
                                logger.info(SubMenuLink.getAttribute("innerHTML") + " | " + SubMenuLink.getAttribute("href"));
                            }
                        }
                     */

                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.main.footer.box"))));
                    ChangeCheckBox.adjustStyle(true,"complete",MainMenuLinkTabs);
                    report.writeToFile(infoMessage, "Successful, Find all Links and Sublinks in Main Menu!");
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",MainMenuLinkTabs);
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Main Menu\" Links");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",MainMenuLinkTabs);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",MainMenuLinkTabs);
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");

    }
}
