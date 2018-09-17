package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tasks.ChangeCheckBox;
import crisscrosscrass.Tasks.Report;
import crisscrosscrass.Tasks.ScreenshotViaWebDriver;
import crisscrosscrass.Tasks.WebdriverTab;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class HomepageTest {

    public void checkingCategories(ChromeDriver webDriver, Report report, JFXCheckBox checkCategoryLinksLeftSideMenu, Text statusInfo, TextField inputSearch, Properties Homepage){

        final String infoMessage = "Checking Category Links";

        // Check on Category Links - Left Side Menu
        ChangeCheckBox.adjustStyle(false,"progress",checkCategoryLinksLeftSideMenu);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        String xpathPattern1 = Homepage.getProperty("page.main.category.links.left");
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {

                boolean isAvailable = webDriver.findElementByXPath(xpathPattern1) != null;
                if(isAvailable){
                    List<WebElement> CategoryLinksLeftSideMenu = webDriver.findElementsByXPath(xpathPattern1);
                    WebdriverTab newtab = new WebdriverTab();
                    //use CategoryLinksLeftSideMenu.size() instead of 1 for Loop!
                    for (int i = 0 ; i < CategoryLinksLeftSideMenu.size() ; i++){
                        webDriver.switchTo().window(tabs.get(0));
                        boolean isSuccessful = newtab.open(webDriver, CategoryLinksLeftSideMenu.get(i).getAttribute("href"), CategoryLinksLeftSideMenu.get(i).getAttribute("textContent").trim());
                        if (isSuccessful){
                            report.writeToFile("TEST CategoryLinksLeftSideMenu "+i+": Successful | ", "found \"" + CategoryLinksLeftSideMenu.get(i).getAttribute("textContent").trim() + "\" Keyword at URL : "+ CategoryLinksLeftSideMenu.get(i).getAttribute("href"));
                        }else {
                            report.writeToFile("TEST CategoryLinksLeftSideMenu "+i+": unable to check! |", "couldn't found \"" + CategoryLinksLeftSideMenu.get(i).getAttribute("textContent").trim() + "\" Keyword in URL : "+ CategoryLinksLeftSideMenu.get(i).getAttribute("href"));
                        }
                    }
                }
                ChangeCheckBox.adjustStyle(true,"complete",checkCategoryLinksLeftSideMenu);
                report.writeToFile(infoMessage, "Complete!");
            }catch (Exception noLeftSideMenu){
                ChangeCheckBox.adjustStyle(true,"nope",checkCategoryLinksLeftSideMenu);
                report.writeToFile(infoMessage, "unable to check! No Links found!");
                noLeftSideMenu.printStackTrace();
            }
        }catch (Exception noCategoryLinksLeftSideMenu){
            ChangeCheckBox.adjustStyle(true,"nope",checkCategoryLinksLeftSideMenu);
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
        }

        report.writeToFile("=================================", "");
    }

    public void checkingShopOfTheWeek(ChromeDriver webDriver, Report report, JFXCheckBox checkLogoFromShopOfTheWeek, Text statusInfo, TextField inputSearch, Properties Homepage){

        final String infoMessage = "Checking Logo Shop of the Week";

        // Check on Logo Shop of the Week
        ChangeCheckBox.adjustStyle(false,"progress",checkLogoFromShopOfTheWeek);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });

        try{
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                boolean isAvailable = webDriver.findElementByXPath(Homepage.getProperty("page.main.shop.promo.link")) != null;
                Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("page.main.shop.promo.link"))).getLocation();
                ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                String ShopOfTheWeekImage = webDriver.findElementByXPath(Homepage.getProperty("page.main.shop.promo.image")).getAttribute("src");
                webDriver.findElementByXPath(Homepage.getProperty("page.main.shop.promo.link")).click();
                boolean isSuccessful;
                try{
                    String ShopOfTheWeekGridImage = webDriver.findElementByXPath(Homepage.getProperty("page.grid.shop.image")).getAttribute("src");
                    if (ShopOfTheWeekImage.contains(ShopOfTheWeekGridImage)){
                        ChangeCheckBox.adjustStyle(true,"complete",checkLogoFromShopOfTheWeek);
                        report.writeToFile(infoMessage, "Successful!");
                    }else{
                        ChangeCheckBox.adjustStyle(true,"nope",checkLogoFromShopOfTheWeek);
                        report.writeToFile(infoMessage, "Not the same image url!");
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"ShopLogoGridPage.png");
                        if (isSuccessful){
                            report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot not successful!");
                        }
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());

                }catch (Exception noLogoFoundOnGrid){
                    report.writeToFile(infoMessage, "unable to check! No Logo found on GridPage!");
                    ChangeCheckBox.adjustStyle(true,"nope",checkLogoFromShopOfTheWeek);
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
                ChangeCheckBox.adjustStyle(true,"nope",checkLogoFromShopOfTheWeek);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "unable to check! No Elements found!");
                noShopLogoFound.printStackTrace();
            }
        }catch (Exception noShopPromo){
            ChangeCheckBox.adjustStyle(true,"nope",checkLogoFromShopOfTheWeek);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noShopPromo.printStackTrace();
        }

        report.writeToFile("=================================", "");
    }



    public void checkingShopOfTheWeekCategories(ChromeDriver webDriver, Report report, JFXCheckBox checkCategoryLinksFromShopOfTheWeek, Text statusInfo, TextField inputSearch, Properties Homepage){
        // Category Links from Shop of the Week

        final String infoMessage = "Checking Category Links from Shop of the Week";
        ChangeCheckBox.adjustStyle(false,"progress",checkCategoryLinksFromShopOfTheWeek);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });

        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try{
                boolean isAvailable = webDriver.findElementByXPath(Homepage.getProperty("page.main.shop.promo.category")) != null;
                String ShopOfTheWeekImage = webDriver.findElementByXPath(Homepage.getProperty("page.main.shop.promo.image")).getAttribute("src");
                List<WebElement> shopCategoryLinks = webDriver.findElementsByXPath(Homepage.getProperty("page.main.shop.promo.category"));
                List<WebElement> shopCategoryNames = webDriver.findElementsByXPath("//*[@id='pagecontent']/*/*[@class='hp-shop-promo']/*[contains(@class, 'hp-shop-promo-categorylink')]/div/a/div/div[1]");
                WebdriverTab newtab = new WebdriverTab();
                //use shopCategoryLinks.size() instead of 1 for Loop!
                for (int i = 0 ; i < shopCategoryLinks.size() ; i++){
                    webDriver.switchTo().window(tabs.get(0));
                    boolean isSuccessful = newtab.open(webDriver, shopCategoryLinks.get(i).getAttribute("href"), shopCategoryNames.get(i).getAttribute("textContent").trim(), ShopOfTheWeekImage, Homepage.getProperty("page.grid.shop.image"));
                    if (isSuccessful){
                        report.writeToFile("TEST ShopCategoryLinks "+i+": Successful | ", "found \"" + shopCategoryNames.get(i).getAttribute("textContent").trim() + "\" Keyword at URL : "+ shopCategoryLinks.get(i).getAttribute("href") + " and same Image is available");
                    }else {
                        report.writeToFile("TEST ShopCategoryLinks "+i+": unable to check! |", "couldn't found \"" + shopCategoryNames.get(i).getAttribute("textContent").trim() + "\" Keyword in URL : "+ shopCategoryLinks.get(i).getAttribute("href") + " or Image is not available");
                    }
                }
                webDriver.navigate().to(inputSearch.getText().trim());
                ChangeCheckBox.adjustStyle(true,"complete",checkCategoryLinksFromShopOfTheWeek);
                report.writeToFile(infoMessage, "Complete!");

            }catch (Exception noShopLogoFound){
                ChangeCheckBox.adjustStyle(true,"nope",checkCategoryLinksFromShopOfTheWeek);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "unable to check! No Elements found!");
                noShopLogoFound.printStackTrace();
            }
        }catch (Exception noShopPromo){
            ChangeCheckBox.adjustStyle(true,"nope",checkCategoryLinksFromShopOfTheWeek);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noShopPromo.printStackTrace();
        }


        report.writeToFile("=================================", "");
    }



    public void checkingNewsletterBanner(ChromeDriver webDriver, Report report, JFXCheckBox checkNewsletterBannerFunctionality, Text statusInfo, TextField inputSearch, TextField inputEmailAdress, Properties Homepage){
        // Newsletter Banner Functionality
        final String infoMessage = "Checking Newsletter Banner Functionality";
        ChangeCheckBox.adjustStyle(false,"progress",checkNewsletterBannerFunctionality);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try{

                WebElement element = webDriver.findElementByXPath(Homepage.getProperty("page.main.newsletter.input"));
                element.sendKeys(inputEmailAdress.getText());
                element.submit();
                boolean isAvailable = webDriver.findElementByXPath(Homepage.getProperty("page.main.newsletter.confirmation")) != null;
                try{
                    if (webDriver.getCurrentUrl().contains("newsletter.html")) {
                        ChangeCheckBox.adjustStyle(true,"complete",checkNewsletterBannerFunctionality);
                        report.writeToFile(infoMessage, "Successful!");
                    } else {
                        ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterBannerFunctionality);
                        report.writeToFile(infoMessage, "Not working!");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                }catch (Exception noConfirmationWrapper){
                    ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterBannerFunctionality);
                    boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "NewsLetterBannerSignInPage.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot not successful!");
                    }
                    report.writeToFile(infoMessage, "ConfirmationPage is missing!");
                    webDriver.navigate().to(inputSearch.getText().trim());
                }


            }catch (Exception noScrollingToElement){
                report.writeToFile(infoMessage, "Couldn't find Newsletter Element!");
                ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterBannerFunctionality);
                noScrollingToElement.printStackTrace();
            }

        }catch (Exception noNewsLetterPromo){
            ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterBannerFunctionality);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noNewsLetterPromo.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
    public void checkingNewsletterPopUp(ChromeDriver webDriver, Report report, JFXCheckBox checkNewsletterPopUp, Text statusInfo, TextField inputSearch, Properties Homepage){
        // Newsletter PopUp
        final String infoMessage = "Checking Newsletter PopUp";
        ChangeCheckBox.adjustStyle(false,"progress",checkNewsletterPopUp);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        String xpathPattern1 = Homepage.getProperty("page.main.newsletter.icon");
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                boolean isAvailable = webDriver.findElementByXPath(xpathPattern1) != null;
                webDriver.findElementByXPath(xpathPattern1).click();
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='inactivity_popup']/div[1]")));
                    webDriver.findElementByXPath("//*[@id='inactivity_popup']/div[1]").click();
                    ChangeCheckBox.adjustStyle(true,"complete",checkNewsletterPopUp);
                    report.writeToFile(infoMessage, "Successful!");
                }catch (Exception noNewsletterIconCloseFound){
                    ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterPopUp);
                    report.writeToFile(infoMessage, "Couldn't close Newsletter Element!");
                    boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "NewsLetterPopUpError.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    noNewsletterIconCloseFound.printStackTrace();
                }
            }catch (Exception noNewsletterIconFound){
                ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterPopUp);
                report.writeToFile(infoMessage, "Couldn't find Newsletter Icon Element!");
                noNewsletterIconFound.printStackTrace();
            }

        }catch (Exception noNewsLetterIcon){
            ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterPopUp);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noNewsLetterIcon.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
    public void checkingNewsletterPopUpFunctionality(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox checkNewsletterPopUpFunctionality, Text statusInfo, TextField inputSearch, TextField inputEmailAdress, Properties Homepage){
        // Newsletter PopUp Functionality
        final String infoMessage = "Checking Newsletter PopUp Functionality";
        ChangeCheckBox.adjustStyle(false,"progress",checkNewsletterPopUpFunctionality);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        String xpathPattern1 = Homepage.getProperty("page.main.newsletter.icon");
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                boolean isAvailable = webDriver.findElementByXPath(xpathPattern1) != null;
                webDriver.findElementByXPath(xpathPattern1).click();
                boolean isSuccessful;
                try{
                    // Scroll down
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(100);
                        js.executeScript("window.scrollBy(0,100)");
                    }

                    WebElement element = webDriver.findElementByXPath("//*[@id=\"subscribing-wrapper\"]/div[1]/form/div[1]/input");
                    element.sendKeys(inputEmailAdress.getText());
                    element.submit();
                    try{
                        isAvailable = webDriver.findElementByXPath("//*[@id=\"confirmation-wrapper\"]") != null;
                        if (webDriver.getCurrentUrl().contains("newsletter.html") ) {
                            ChangeCheckBox.adjustStyle(true,"complete",checkNewsletterPopUpFunctionality);
                            report.writeToFile("Checking Newsletter PopUp Functionality: ", "Successful!");
                        } else {
                            ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterPopUpFunctionality);
                            report.writeToFile("Checking Newsletter PopUp Functionality: ", "Not working!");
                        }
                        webDriver.navigate().to(inputSearch.getText().trim());
                    }catch (Exception noConfirmationWrapper){
                        ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterPopUpFunctionality);
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"NewsLetterSignInPage.png");
                        if (isSuccessful){
                            report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot not successful!");
                        }
                        report.writeToFile("Checking Newsletter PopUp Functionality: ", "ConfirmationPage is missing!");
                        webDriver.navigate().to(inputSearch.getText().trim());
                    }
                }catch (Exception noEmailCouldBeEntered){
                    ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterPopUpFunctionality);
                    report.writeToFile("Checking Newsletter PopUp Functionality: ", "Email Adress couldn't be entered!");
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"PopUpNewsLetter.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    noEmailCouldBeEntered.printStackTrace();
                }
            }catch (Exception noNewsletterIconFound){
                ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterPopUpFunctionality);
                report.writeToFile("Checking Newsletter PopUp Functionality: ", "Couldn't find Newsletter Icon Element!");
                noNewsletterIconFound.printStackTrace();
            }

        }catch (Exception noNewsLetterIcon){
            ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterPopUpFunctionality);
            report.writeToFile("Checking Newsletter Pop Functionality: ", "unable to check! Browser not responding");
            noNewsLetterIcon.printStackTrace();
        }

        report.writeToFile("=================================", "");
    }

    public void checkingFooterLinks(ChromeDriver webDriver, Report report, JFXCheckBox checkFooterLinks, Text statusInfo, TextField inputSearch, Properties Homepage){
        // Footer Links
        final String infoMessage = "Checking Footer Links & Categories";
        ChangeCheckBox.adjustStyle(false,"progress",checkFooterLinks);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });


        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                boolean isAvailable = webDriver.findElementByXPath(Homepage.getProperty("page.main.footer.links")) != null;
                Point hoverItem = webDriver.findElement(By.xpath(Homepage.getProperty("page.main.footer.links"))).getLocation();
                ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                WebdriverTab newtab = new WebdriverTab();
                //check all footerLinks
                List<WebElement> footerFooterLinks = webDriver.findElementsByXPath(Homepage.getProperty("page.main.footer.links"));
                //set footerFooterLinks.size()
                boolean isSuccessful;
                for (int i = 0; i < footerFooterLinks.size() ; i++){
                    webDriver.switchTo().window(tabs.get(0));
                    if (footerFooterLinks.get(i).getAttribute("href").contains("newsletter")){
                        isSuccessful = newtab.openCheckURLTitleH1H2(webDriver,footerFooterLinks.get(i).getAttribute("href"),"newsletter");
                        if (isSuccessful){
                            report.writeToFile("TEST FooterLinks "+i+": Successful | ", "found \"" + "Newsletter" + "\" Keyword at URL : "+ footerFooterLinks.get(i).getAttribute("href") );
                        }else {
                            report.writeToFile("TEST FooterLinks "+i+": unable to check! |", "couldn't found \"" + "Newsletter" + "\" Keyword in URL : "+ footerFooterLinks.get(i).getAttribute("href") );
                        }
                    }else {
                        isSuccessful = newtab.openCheckURLTitleH1H2(webDriver,footerFooterLinks.get(i).getAttribute("href"),footerFooterLinks.get(i).getText().trim());
                        if (isSuccessful){
                            report.writeToFile("TEST FooterLinks "+i+": Successful | ", "found \"" + footerFooterLinks.get(i).getText().trim() + "\" Keyword at URL : "+ footerFooterLinks.get(i).getAttribute("href") );
                        }else {
                            report.writeToFile("TEST FooterLinks "+i+": unable to check! |", "couldn't found \"" + footerFooterLinks.get(i).getText().trim() + "\" Keyword in URL : "+ footerFooterLinks.get(i).getAttribute("href") );
                        }
                    }
                }
                List<WebElement> footerCategoryLinks = webDriver.findElementsByXPath(Homepage.getProperty("page.main.footer.categories"));
                //check all footerCategoryLinks
                //set footerCategoryLinks.size()
                for (int i = 0 ; i < footerCategoryLinks.size() ; i++){
                    webDriver.switchTo().window(tabs.get(0));
                    isSuccessful = newtab.openCheckURLTitleH1H2(webDriver,footerCategoryLinks.get(i).getAttribute("href"),footerCategoryLinks.get(i).getText().trim());
                    if (isSuccessful){
                        report.writeToFile("TEST FooterCategoryLinks "+i+": Successful | ", "found \"" + footerCategoryLinks.get(i).getText().trim() + "\" Keyword at URL : "+ footerCategoryLinks.get(i).getAttribute("href") );
                    }else {
                        report.writeToFile("TEST FooterCategoryLinks "+i+": unable to check! |", "couldn't found \"" + footerCategoryLinks.get(i).getText().trim() + "\" Keyword in URL : "+ footerCategoryLinks.get(i).getAttribute("href") );
                    }
                }
                ChangeCheckBox.adjustStyle(true,"complete",checkFooterLinks);
                report.writeToFile(infoMessage, "Complete!");
                webDriver.navigate().to(inputSearch.getText().trim());

            }catch (Exception noFooterFound){
                ChangeCheckBox.adjustStyle(true,"nope",checkFooterLinks);
                report.writeToFile(infoMessage, "Couldn't find Footer Element!");
                noFooterFound.printStackTrace();
            }

        }catch (Exception noFooter){
            ChangeCheckBox.adjustStyle(true,"nope",checkFooterLinks);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noFooter.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }

    public void checkingSearchAndSuggestions(ChromeDriver webDriver, Report report, JFXCheckBox checkTextSearchAndSuggestions, TextField inputTextSearchAndSuggestions, Text statusInfo, TextField inputSearch, Properties Homepage){

        // Text Search & Suggestions in InputSearch
        final String infoMessage = "Checking Text Search and Suggestions in InputSearch";
        ChangeCheckBox.adjustStyle(false,"progress",checkTextSearchAndSuggestions);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            WebDriverWait wait = new WebDriverWait(webDriver, 10);
            try{
                if (inputTextSearchAndSuggestions.getText().length() > 1){
                    String[] searchAliases = inputTextSearchAndSuggestions.getText().split("\\|");
                    try{
                        for (int i = 0; i < searchAliases.length ; i++){
                            webDriver.switchTo().window(tabs.get(0));
                            WebElement element = webDriver.findElement(By.id(Homepage.getProperty("page.search.bar")));
                            element.sendKeys(searchAliases[i].trim()); // Enter searchAliases without pressing ENTER
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'main-search-suggestions')]/li/a/div/*[contains(@class, 'srTitle')]")));
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'main-search-suggestions')]/li/a/div/*[contains(@class, 'srType')]")));
                            List<WebElement> searchAliasesTitles = webDriver.findElementsByXPath("//*[contains(@class, 'main-search-suggestions')]/li/a/div/*[contains(@class, 'srTitle')]");
                            List<WebElement> searchAliasesType = webDriver.findElementsByXPath("//*[contains(@class, 'main-search-suggestions')]/li/a/div/*[contains(@class, 'srType')]");
                            report.writeToFile(infoMessage+" for \""+ searchAliases[i].trim()+"\"");
                            report.writeToFile("Suggestions :");
                            for (int j = 0 ; j < searchAliasesTitles.size() ; j++){
                                //System.out.println(searchAliasesTitles.get(j).getText() + "  | " + searchAliasesType.get(j).getText());
                                report.writeToFile(searchAliasesTitles.get(j).getText()+" | "+searchAliasesType.get(j).getText() );
                            }
                            report.writeToFile("");
                            element.submit();

                            if ( webDriver.getTitle().contains(searchAliases[i].trim()) | webDriver.getCurrentUrl().contains(searchAliases[i].trim()) ){
                                report.writeToFile("Test Title/URL Keyword \"" + searchAliases[i].trim() + "\" : ", "Successful!");
                            }else{
                                report.writeToFile("Test Title/URL Keyword \"" + searchAliases[i].trim() + "\" : ", "unable to find Keyword at " + webDriver.getCurrentUrl() + " !");
                            }
                            report.writeToFile("");

                            webDriver.navigate().to(inputSearch.getText().trim());
                        }

                        ChangeCheckBox.adjustStyle(true,"complete",checkTextSearchAndSuggestions);
                        report.writeToFile(infoMessage, "Complete!");
                        webDriver.navigate().to(inputSearch.getText().trim());
                    }catch (Exception noSearchElements){
                        ChangeCheckBox.adjustStyle(true,"nope",checkTextSearchAndSuggestions);
                        report.writeToFile(infoMessage, "unable to get Suggestions!");
                        noSearchElements.printStackTrace();
                    }
                }else{
                    ChangeCheckBox.adjustStyle(true,"nope",checkTextSearchAndSuggestions);
                    report.writeToFile(infoMessage, "no Informations in Inputfield provided!");
                }

            }catch (Exception noSearchBarInput){
                ChangeCheckBox.adjustStyle(true,"nope",checkTextSearchAndSuggestions);
                report.writeToFile(infoMessage, "unable to enter Search Input");
                noSearchBarInput.printStackTrace();
            }
        }catch (Exception noInput){
            ChangeCheckBox.adjustStyle(true,"nope",checkTextSearchAndSuggestions);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noInput.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
    public void checkingFeedbackPopUp(ChromeDriver webDriver, Report report, JFXCheckBox checkFeedbackPopUp, Text statusInfo, TextField inputSearch, Properties Homepage){
        // Feedback PopUp
        final String infoMessage = "Checking Feedback PopUp";
        ChangeCheckBox.adjustStyle(false,"progress",checkFeedbackPopUp);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });


        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            WebDriverWait wait = new WebDriverWait(webDriver, 10);
            try{
                boolean isAvailable = webDriver.findElementByXPath(Homepage.getProperty("page.main.feedback.icon")) != null;
                webDriver.findElementByXPath(Homepage.getProperty("page.main.feedback.icon")).click();
                try{
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.main.feedback.close"))));
                    webDriver.findElementByXPath(Homepage.getProperty("page.main.feedback.close")).click();
                    ChangeCheckBox.adjustStyle(true,"complete",checkFeedbackPopUp);
                    report.writeToFile(infoMessage, "Complete!");
                    webDriver.navigate().to(inputSearch.getText().trim());

                }catch (Exception noClosingFeedBackButton){
                    ChangeCheckBox.adjustStyle(true,"nope",checkFeedbackPopUp);
                    report.writeToFile(infoMessage, "couldn't close PopUp!");
                    webDriver.navigate().to(inputSearch.getText().trim());
                }

            }catch (Exception noFeedBackButtonFound){
                ChangeCheckBox.adjustStyle(true,"nope",checkFeedbackPopUp);
                report.writeToFile(infoMessage, "unable to check! Couldn't find Feedback Button");
                webDriver.navigate().to(inputSearch.getText().trim());
            }
        }catch (Exception noFeedBack){
            ChangeCheckBox.adjustStyle(true,"nope",checkFeedbackPopUp);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noFeedBack.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }

    public void checkingPrivacyPopUp(ChromeDriver webDriver, Report report, JFXCheckBox checkPrivacyPopUp, Text statusInfo, TextField inputSearch, Properties Homepage) {
        // Privacy PopUp
        final String infoMessage = "Checking Feedback PopUp";
        ChangeCheckBox.adjustStyle(false,"progress",checkPrivacyPopUp);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });

        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            webDriver.navigate().to(inputSearch.getText().trim());
            WebDriverWait wait = new WebDriverWait(webDriver, 10);
            try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.main.privacy.popup"))));
                boolean isAvailable = webDriver.findElementByXPath(Homepage.getProperty("page.main.privacy.popup")) != null;
                webDriver.findElementByXPath(Homepage.getProperty("page.main.privacy.popup")).click();
                ArrayList<String> tabsPrivacy = new ArrayList<>(webDriver.getWindowHandles());
                webDriver.switchTo().window(tabsPrivacy.get(1)).close();
                webDriver.switchTo().window(tabsPrivacy.get(0));
                ChangeCheckBox.adjustStyle(true,"complete",checkPrivacyPopUp);
                report.writeToFile(infoMessage, "Complete!");

                webDriver.navigate().to(inputSearch.getText().trim());
            }catch (Exception noPrivacyBoxFound){
                ChangeCheckBox.adjustStyle(true,"nope",checkPrivacyPopUp);
                report.writeToFile(infoMessage, "unable to find Privacy PopUp");
                noPrivacyBoxFound.printStackTrace();
            }
        }catch (Exception noPrivacyBox){
            ChangeCheckBox.adjustStyle(true,"nope",checkPrivacyPopUp);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noPrivacyBox.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }

    public void checkingImprint(ChromeDriver webDriver, Report report, JFXCheckBox checkImprint, Text statusInfo, TextField inputImprintURL, Properties Homepage) {
        final String infoMessage = "Checking Imprint";
        ChangeCheckBox.adjustStyle(false,"progress",checkImprint);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });

        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            webDriver.navigate().to(inputImprintURL.getText().trim());
            WebDriverWait wait = new WebDriverWait(webDriver, 10);
            try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("imprintpage.costumer.button"))));
                final String checkLinkUrl = webDriver.findElementByXPath(Homepage.getProperty("imprintpage.costumer.button")).getAttribute("href").trim().toLowerCase();
                webDriver.findElementByXPath(Homepage.getProperty("imprintpage.costumer.button")).click();
                if ( webDriver.getCurrentUrl().trim().toLowerCase().equals(checkLinkUrl) ){
                    ChangeCheckBox.adjustStyle(true,"complete",checkImprint);
                    report.writeToFile(infoMessage, "Successfull! User is redirected to working page");
                }else {
                    ChangeCheckBox.adjustStyle(true,"nope",checkImprint);
                    report.writeToFile(infoMessage, "Not successfull! User is not redirected to working page");
                }

            }catch (Exception noPrivacyBoxFound){
                ChangeCheckBox.adjustStyle(true,"nope",checkImprint);
                report.writeToFile(infoMessage, "unable to find Costumer Service Button");
                noPrivacyBoxFound.printStackTrace();
            }
        }catch (Exception noPrivacyBox){
            ChangeCheckBox.adjustStyle(true,"nope",checkImprint);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noPrivacyBox.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
    public void checkingPrivacyPolicy(ChromeDriver webDriver, Report report, JFXCheckBox PrivacyPolicy, Text statusInfo, TextField inputPrivacyPolicy, Properties Homepage) {
        final String infoMessage = "Checking Privacy Policy";
        ChangeCheckBox.adjustStyle(false,"progress",PrivacyPolicy);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });

        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputPrivacyPolicy.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("privacypage.link"))));
                    final String beforeURL = webDriver.getCurrentUrl().toLowerCase().trim();
                    webDriver.findElementByXPath(Homepage.getProperty("privacypage.link")).click();
                    if (webDriver.getCurrentUrl().toLowerCase().trim() != beforeURL){
                        report.writeToFile(infoMessage, "Successful!");
                        ChangeCheckBox.adjustStyle(true,"complete",PrivacyPolicy);
                    }else{
                        report.writeToFile(infoMessage, "Failed !");
                        ChangeCheckBox.adjustStyle(true,"nope",PrivacyPolicy);
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",PrivacyPolicy);
                    webDriver.navigate().to(inputPrivacyPolicy.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Privacy\" Link");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",PrivacyPolicy);
                webDriver.navigate().to(inputPrivacyPolicy.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",PrivacyPolicy);
            webDriver.navigate().to(inputPrivacyPolicy.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }
}
