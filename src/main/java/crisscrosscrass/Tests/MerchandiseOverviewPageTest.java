package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tasks.ChangeCheckBox;
import crisscrosscrass.Tasks.Report;
import crisscrosscrass.Tasks.ScreenshotViaWebDriver;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.log4j.Logger;
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
import java.util.concurrent.ThreadLocalRandom;

public class MerchandiseOverviewPageTest {
    final static Logger logger = Logger.getLogger(MerchandiseOverviewPageTest.class);
    public void checkingMerchandiseLetters(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox LettertoMerchandise, Text statusInfo, TextField inputMerchandiseOverviewPageURL, Properties Homepage){
        final String infoMessage = LettertoMerchandise.getText();
        ChangeCheckBox.adjustStyle(false,"progress",LettertoMerchandise);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("merchandisepage.alphabet.buttons"))));
                    List<WebElement> AllAlphabetButtons = webDriver.findElementsByXPath(Homepage.getProperty("merchandisepage.alphabet.buttons"));
                    final int randomSelectIndex = ThreadLocalRandom.current().nextInt(0, AllAlphabetButtons.size() );
                    final String selectedLetter = AllAlphabetButtons.get(randomSelectIndex).getText();
                    AllAlphabetButtons.get(randomSelectIndex).click();
                    for (int i = 0; i < 1; i++) {
                        Thread.sleep(500);
                        js.executeScript("window.scrollBy(0,1)");
                    }
                    for (int i = 0; i < 1; i++) {
                        Thread.sleep(500);
                        js.executeScript("window.scrollBy(0,-1)");
                    }
                    List<WebElement> AllAlphabetLetters = webDriver.findElementsByXPath(Homepage.getProperty("merchandisepage.alphabet.letters"));
                    WebElement ViewPortElement = null;
                    for (WebElement AlphabetLetter : AllAlphabetLetters){
                        if (AlphabetLetter.getText().toLowerCase().trim().equals(selectedLetter.toLowerCase().trim())){
                            ViewPortElement = AlphabetLetter;
                            break;
                        }
                    }
                    boolean isInViewPort = (boolean)((JavascriptExecutor)webDriver).executeScript(
                            "var elem = arguments[0],                 " +
                                    "  box = elem.getBoundingClientRect(),    " +
                                    "  cx = box.left + box.width / 2,         " +
                                    "  cy = box.top + box.height / 2,         " +
                                    "  e = document.elementFromPoint(cx, cy); " +
                                    "for (; e; e = e.parentElement) {         " +
                                    "  if (e === elem)                        " +
                                    "    return true;                         " +
                                    "}                                        " +
                                    "return false;                            "
                            , ViewPortElement);
                    if (isInViewPort){
                        ChangeCheckBox.adjustStyle(true,"complete",LettertoMerchandise);
                        report.writeToFile(infoMessage, "The letter \""+selectedLetter+"\" which was clicked appears on top of page in user's pageview");
                    }else {
                        ChangeCheckBox.adjustStyle(true,"nope",LettertoMerchandise);
                        report.writeToFile(infoMessage, "The letter \""+selectedLetter+"\" which was clicked appears on in user's pageview");
                        boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "MerchandisePageLetterToMerchandise.png");
                        if (isSuccessful){
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                        }
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",LettertoMerchandise);
                    webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Letters\" to Merchandise");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",LettertoMerchandise);
                webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",LettertoMerchandise);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
    public void checkingMerchandiseName(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox MerchandiseName, Text statusInfo, TextField inputMerchandiseOverviewPageURL, Properties Homepage){
        final String infoMessage = MerchandiseName.getText();
        ChangeCheckBox.adjustStyle(false,"progress",MerchandiseName);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("merchandisepage.merchandise.links"))));
                    List<WebElement> AllMerchandiseLinks = webDriver.findElementsByXPath(Homepage.getProperty("merchandisepage.merchandise.links"));
                    final int randomSelectIndex = ThreadLocalRandom.current().nextInt(0, AllMerchandiseLinks.size() );
                    final String selectedMerchandise = AllMerchandiseLinks.get(randomSelectIndex).getText();
                    Point hoverItem = AllMerchandiseLinks.get(randomSelectIndex).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    AllMerchandiseLinks.get(randomSelectIndex).click();
                    if (webDriver.getCurrentUrl().toLowerCase().trim().contains(selectedMerchandise.toLowerCase().trim()) | webDriver.getTitle().toLowerCase().trim().contains(selectedMerchandise.toLowerCase().trim() )){
                        ChangeCheckBox.adjustStyle(true,"complete",MerchandiseName);
                        report.writeToFile(infoMessage, "Successful! Could detect \""+selectedMerchandise+"\" in redirected Page "+webDriver.getCurrentUrl().toLowerCase());
                    }else {
                        ChangeCheckBox.adjustStyle(true,"nope",MerchandiseName);
                        report.writeToFile(infoMessage, "Not successful! Couldn't detect \""+selectedMerchandise+"\" in redirected Page "+webDriver.getCurrentUrl().toLowerCase());
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",MerchandiseName);
                    webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Link-Name\" to Merchandise");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",MerchandiseName);
                webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",MerchandiseName);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
    public void checkingMerchandiseSearch(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox MerchandiseSearch, Text statusInfo, TextField inputMerchandiseOverviewPageURL,TextField inputMerchandiseSearch, Properties Homepage){
        final String infoMessage = MerchandiseSearch.getText();
        ChangeCheckBox.adjustStyle(false,"progress",MerchandiseSearch);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    String[] AllSearchQueries = inputMerchandiseSearch.getText().split("\\|");
                    //all search queries
                    for (int i=0 ; i<AllSearchQueries.length ; i++){
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("merchandisepage.search.bar"))));
                        Point hoverItem = webDriver.findElementByXPath(Homepage.getProperty("merchandisepage.search.bar")).getLocation();
                        ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                        ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                        WebElement element = webDriver.findElement(By.xpath(Homepage.getProperty("merchandisepage.search.bar")));
                        element.sendKeys(AllSearchQueries[i].trim());
                        try{
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("merchandisepage.search.suggestions"))));
                            List<WebElement> AllMerchandiseSuggestions = webDriver.findElementsByXPath(Homepage.getProperty("merchandisepage.search.suggestions"));
                            report.writeToFile("Merchandise Suggestions for \""+AllSearchQueries[i].trim()+"\": ");
                            for (WebElement Merchandise : AllMerchandiseSuggestions){
                                report.writeToFile(Merchandise.getText());
                            }
                            final String selectedSuggestionMerchandise = AllMerchandiseSuggestions.get(0).getText().trim();
                            AllMerchandiseSuggestions.get(0).click();
                            if (webDriver.getCurrentUrl().toLowerCase().trim().contains(selectedSuggestionMerchandise.toLowerCase().trim()) | webDriver.getTitle().toLowerCase().trim().contains(selectedSuggestionMerchandise.toLowerCase().trim()) ){
                                report.writeToFile("Click on Suggestion \""+selectedSuggestionMerchandise.trim()+"\": ", "Successful! User is redirected to a page that contains \""+selectedSuggestionMerchandise.trim()+"\" ");
                            }else{
                                report.writeToFile("Click on Suggestion \""+selectedSuggestionMerchandise.trim()+"\": ", "Not successful! User is redirected to a page that NOT contains \""+selectedSuggestionMerchandise.trim()+"\" ");
                            }
                            report.writeToFile("");
                            webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                        }catch (Exception noSuggestionsFound){
                            report.writeToFile(infoMessage, "Couldn't detect \"Suggestions\" for Merchandise Search \""+AllSearchQueries[i].trim()+"\" ");
                            webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                            noSuggestionsFound.printStackTrace();
                        }
                    }
                    ChangeCheckBox.adjustStyle(true,"complete",MerchandiseSearch);
                    report.writeToFile(infoMessage, "Complete!");
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",MerchandiseSearch);
                    webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Search\" Merchandise");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",MerchandiseSearch);
                webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",MerchandiseSearch);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
    public void checkingMerchandiseGoToTop(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox GoToTopButton, Text statusInfo, TextField inputMerchandiseOverviewPageURL, Properties Homepage){
        final String infoMessage = GoToTopButton.getText();
        ChangeCheckBox.adjustStyle(false,"progress",GoToTopButton);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.main.footer.box"))));
                    Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("page.main.footer.box"))).getLocation();
                    ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                    ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                    for (int i = 0; i < 1; i++) {
                        Thread.sleep(1000);
                        js.executeScript("window.scrollBy(0,1)");
                    }
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.main.totopbutton"))));
                    webDriver.findElementByXPath(Homepage.getProperty("page.main.totopbutton")).click();
                    for (int i = 0; i < 1; i++) {
                        Thread.sleep(300);
                        js.executeScript("window.scrollBy(0,1)");
                    }
                    for (int i = 0; i < 1; i++) {
                        Thread.sleep(300);
                        js.executeScript("window.scrollBy(0,-1)");
                    }
                    WebElement h3Element = webDriver.findElementByXPath(Homepage.getProperty("merchandisepage.info.h3"));
                    h3Element.click();
                    boolean isInViewPort = (boolean)((JavascriptExecutor)webDriver).executeScript(
                            "var elem = arguments[0],                 " +
                                    "  box = elem.getBoundingClientRect(),    " +
                                    "  cx = box.left + box.width / 2,         " +
                                    "  cy = box.top + box.height / 2,         " +
                                    "  e = document.elementFromPoint(cx, cy); " +
                                    "for (; e; e = e.parentElement) {         " +
                                    "  if (e === elem)                        " +
                                    "    return true;                         " +
                                    "}                                        " +
                                    "return false;                            "
                            , h3Element);
                    if (isInViewPort){
                        ChangeCheckBox.adjustStyle(true,"complete",GoToTopButton);
                        report.writeToFile(infoMessage, "Initial banner (H3) is on user's view");
                    }else {
                        ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
                        report.writeToFile(infoMessage, "Initial banner (H3) is NOT on user's view");
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
                    webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Go To Top-Button\" ");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
                webDriver.navigate().to(inputMerchandiseOverviewPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",GoToTopButton);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
}
