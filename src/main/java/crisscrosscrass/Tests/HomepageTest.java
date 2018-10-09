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
    Report failedTestCases = new Report();

    public void checkingCategories(ChromeDriver webDriver, Report report, JFXCheckBox checkCategoryLinksLeftSideMenu, Text statusInfo, TextField inputSearch, Properties Homepage){
failedTestCases.writeToNamedFile("CHECKING HOMEPAGE", "FailAndReview");
        final String infoMessage = checkCategoryLinksLeftSideMenu.getText();

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
                report.writeToFile("Start to check all Category Links: ", "");
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
                            report.writeToFile("unable to check! ", "couldn't found \"" + CategoryLinksLeftSideMenu.get(i).getAttribute("textContent").trim() + "\" Keyword in URL : "+ CategoryLinksLeftSideMenu.get(i).getAttribute("href"));
                            failedTestCases.writeToNamedFile("Category links in left Menu! ", "Please review the following URL. Couldn't find \"" + CategoryLinksLeftSideMenu.get(i).getAttribute("textContent").trim() + "\" Keyword in URL : "+ CategoryLinksLeftSideMenu.get(i).getAttribute("href"),"FailAndReview");
                        }
                    }
                }
                ChangeCheckBox.adjustStyle(true,"complete",checkCategoryLinksLeftSideMenu);
                report.writeToFile(infoMessage, "Complete!");
            }catch (Exception noLeftSideMenu){
                ChangeCheckBox.adjustStyle(true,"nope",checkCategoryLinksLeftSideMenu);
                report.writeToFile(infoMessage, "unable to check! No Links found!");
                failedTestCases.writeToNamedFile(infoMessage, "unable to check left Menu; no links found!", "FailAndReview");
                noLeftSideMenu.printStackTrace();
            }
        }catch (Exception noCategoryLinksLeftSideMenu){
            ChangeCheckBox.adjustStyle(true,"nope",checkCategoryLinksLeftSideMenu);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "unable to check left Menu; no links found!", "FailAndReview");
            noCategoryLinksLeftSideMenu.printStackTrace();
        }
        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");
    }

    public void checkingShopOfTheWeek(ChromeDriver webDriver, Report report, JFXCheckBox checkLogoFromShopOfTheWeek, Text statusInfo, TextField inputSearch, Properties Homepage){

        final String infoMessage = checkLogoFromShopOfTheWeek.getText();

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
                        failedTestCases.writeToNamedFile(infoMessage, "Please review Logo from Shop of the week. For reference, see ShopLogoGridPage", "FailAndReview");
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
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: No Logo found on Grid Page. For reference, see ShopLogoGridPage", "FailAndReview");
                    ChangeCheckBox.adjustStyle(true,"nope",checkLogoFromShopOfTheWeek);
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"ShopLogoGridPage.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("Checking Logo Shop Screenshot: ", "Screenshot successful!", "FailAndReview");
                    }else {
                        report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("Checking Logo Shop Screenshot: ", "Screenshot not successful!", "FailAndReview");

                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    noLogoFoundOnGrid.printStackTrace();
                }


            }catch (Exception noShopLogoFound){
                ChangeCheckBox.adjustStyle(true,"nope",checkLogoFromShopOfTheWeek);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "unable to check! No Elements found!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: Shop of the Week widget not found.", "FailAndReview");
                noShopLogoFound.printStackTrace();
            }
        }catch (Exception noShopPromo){
            ChangeCheckBox.adjustStyle(true,"nope",checkLogoFromShopOfTheWeek);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Shop of the week: Browser not responding.", "FailAndReview");
            noShopPromo.printStackTrace();
        }

        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");
    }



    public void checkingShopOfTheWeekCategories(ChromeDriver webDriver, Report report, JFXCheckBox checkCategoryLinksFromShopOfTheWeek, Text statusInfo, TextField inputSearch, Properties Homepage){
        // Category Links from Shop of the Week

        final String infoMessage = checkCategoryLinksFromShopOfTheWeek.getText();
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
                    failedTestCases.writeToNamedFile("Shop of The Week Categories "+i+": Please double check links! |", "couldn't found \"" + shopCategoryNames.get(i).getAttribute("textContent").trim() + "\" Keyword in URL : "+ shopCategoryLinks.get(i).getAttribute("href") + " or Image is not available", "FailAndReview");
                    }
                }
                webDriver.navigate().to(inputSearch.getText().trim());
                ChangeCheckBox.adjustStyle(true,"complete",checkCategoryLinksFromShopOfTheWeek);
                report.writeToFile(infoMessage, "Complete!");

            }catch (Exception noShopLogoFound){
                ChangeCheckBox.adjustStyle(true,"nope",checkCategoryLinksFromShopOfTheWeek);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "unable to check! No Elements found!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: Shop of the Week widget not found", "FailAndReview");
                noShopLogoFound.printStackTrace();
            }
        }catch (Exception noShopPromo){
            ChangeCheckBox.adjustStyle(true,"nope",checkCategoryLinksFromShopOfTheWeek);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Shop of the week: Browser not responding.", "FailAndReview");
            noShopPromo.printStackTrace();
        }


        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");
    }



    public void checkingNewsletterBanner(ChromeDriver webDriver, Report report, JFXCheckBox checkNewsletterBannerFunctionality, Text statusInfo, TextField inputSearch, TextField inputEmailAdress, Properties Homepage){
        // Newsletter Banner Functionality
        final String infoMessage = checkNewsletterBannerFunctionality.getText();
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
                        failedTestCases.writeToNamedFile(infoMessage, "Please check: Newsletter not working. For reference, see NewsLetterBannerSignInPage", "FailAndReview");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                }catch (Exception noConfirmationWrapper){
                    ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterBannerFunctionality);
                    boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "NewsLetterBannerSignInPage.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Newsletter Screenshot: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("Checking Newsletter Screenshot: ", "Screenshot successful!", "FailAndReview");

                    }else {
                        report.writeToFile("Checking Newsletter Screenshot: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("Checking Newsletter Screenshot: ", "Screenshot not successful!", "FailAndReview");
                    }
                    report.writeToFile(infoMessage, "ConfirmationPage is missing!");
                    webDriver.navigate().to(inputSearch.getText().trim());
                }


            }catch (Exception noScrollingToElement){
                report.writeToFile(infoMessage, "Couldn't find Newsletter Element!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't find Newsletter Element! For reference, see NewsLetterBannerSignInPage", "FailAndReview");
                ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterBannerFunctionality);
                noScrollingToElement.printStackTrace();
            }

        }catch (Exception noNewsLetterPromo){
            ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterBannerFunctionality);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Newsletter: Browser not responding! For reference, see NewsLetterBannerSignInPage", "FailAndReview");
            noNewsLetterPromo.printStackTrace();
        }
        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");

    }
    public void checkingNewsletterPopUp(ChromeDriver webDriver, Report report, JFXCheckBox checkNewsletterPopUp, Text statusInfo, TextField inputSearch, Properties Homepage){
        // Newsletter PopUp
        final String infoMessage = checkNewsletterPopUp.getText();
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
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.main.newsletter.close"))));
                    webDriver.findElementByXPath(Homepage.getProperty("page.main.newsletter.close")).click();
                    ChangeCheckBox.adjustStyle(true,"complete",checkNewsletterPopUp);
                    report.writeToFile(infoMessage, "Successful!");
                }catch (Exception noNewsletterIconCloseFound){
                    ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterPopUp);
                    report.writeToFile(infoMessage, "Couldn't close Newsletter Element!");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: Could not Close Newsletter, For reference, see NewsLetterPopUpError", "FailAndReview");
                    boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "NewsLetterPopUpError.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Newsletter Shop Screenshot: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("Checking Newsletter Screenshot: ", "Screenshot successful!", "FailAndReview");
                    }else {
                        report.writeToFile("Checking Newsletter Shop Screenshot: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("Checking Newsletter Screenshot: ", "Screenshot not successful!", "FailAndReview");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    noNewsletterIconCloseFound.printStackTrace();
                }
            }catch (Exception noNewsletterIconFound){
                ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterPopUp);
                report.writeToFile(infoMessage, "Couldn't find Newsletter Icon Element!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: could not find newsletter. For reference, see NewsLetterPopUpError", "FailAndReview");
                noNewsletterIconFound.printStackTrace();
            }

        }catch (Exception noNewsLetterIcon){
            ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterPopUp);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "please check Newsletter: browser not responding! For reference, see NewsLetterPopUpError", "FailAndReview");
            noNewsLetterIcon.printStackTrace();
        }
        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");

    }
    public void checkingNewsletterPopUpFunctionality(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox checkNewsletterPopUpFunctionality, Text statusInfo, TextField inputSearch, TextField inputEmailAdress, Properties Homepage){
        // Newsletter PopUp Functionality
        final String infoMessage = checkNewsletterPopUpFunctionality.getText();
        ChangeCheckBox.adjustStyle(false,"progress",checkNewsletterPopUpFunctionality);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                boolean isAvailable = webDriver.findElementByXPath(Homepage.getProperty("page.main.newsletter.icon")) != null;
                webDriver.findElementByXPath(Homepage.getProperty("page.main.newsletter.icon")).click();
                boolean isSuccessful;
                try{
                    // Scroll down
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(100);
                        js.executeScript("window.scrollBy(0,100)");
                    }

                    WebElement element = webDriver.findElementByXPath(Homepage.getProperty("page.main.newsletter.input"));
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
                            failedTestCases.writeToNamedFile("Newsletter PopUp Functionality", "Please check: newsletter subscription process appears NOT to be working. For reference, see NewsLetterSignInPage", "FailAndReview");
                        }
                        webDriver.navigate().to(inputSearch.getText().trim());
                    }catch (Exception noConfirmationWrapper){
                        ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterPopUpFunctionality);
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"NewsLetterSignInPage.png");
                        if (isSuccessful){
                            report.writeToFile("Checking Newsletter Subscription Screenshot: ", "Screenshot successful!");
                            failedTestCases.writeToNamedFile("Checking Newsletter Subscription Screenshot: ", "Screenshot successful!", "FailAndReview");
                        }else {
                            report.writeToFile("Checking Newsletter Subscription Screenshot: ", "Screenshot not successful!");
                            failedTestCases.writeToNamedFile("Checking Newsletter Subscription Screenshot: ", "Screenshot not successful!", "FailAndReview");

                        }
                        report.writeToFile("Checking Newsletter PopUp Functionality: ", "ConfirmationPage is missing!");
                        webDriver.navigate().to(inputSearch.getText().trim());
                    }
                }catch (Exception noEmailCouldBeEntered){
                    ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterPopUpFunctionality);
                    report.writeToFile("Checking Newsletter PopUp Functionality: ", "Email Address couldn't be entered!");
                    failedTestCases.writeToNamedFile("Checking Newsletter PopUp Functionality: ", "Please check newsletter: Email Address couldn't be entered!, For reference, see PopUpNewsLetter", "FailAndReview");
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"PopUpNewsLetter.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot successful!");
                        failedTestCases.writeToNamedFile("Checking Newsletter Subscription Screenshot: ", "Screenshot successful!", "FailAndReview");
                    }else {
                        report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot not successful!");
                        failedTestCases.writeToNamedFile("Checking Newsletter Subscription Screenshot: ", "Screenshot not successful!", "FailAndReview");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    noEmailCouldBeEntered.printStackTrace();
                }
            }catch (Exception noNewsletterIconFound){
                ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterPopUpFunctionality);
                report.writeToFile("Checking Newsletter PopUp Functionality: ", "Couldn't find Newsletter Icon Element!");
                failedTestCases.writeToNamedFile("Checking Newsletter PopUp Functionality: ", "Please check: Couldn't not find Newsletter element!For reference, see PopUpNewsLetter", "FailAndReview");
                noNewsletterIconFound.printStackTrace();
            }

        }catch (Exception noNewsLetterIcon){
            ChangeCheckBox.adjustStyle(true,"nope",checkNewsletterPopUpFunctionality);
            report.writeToFile("Checking Newsletter Pop Functionality: ", "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile("Checking Newsletter PopUp Functionality: ", "Please check Newsletter: Browser not responding!For reference, see PopUpNewsLetter", "FailAndReview");
            noNewsLetterIcon.printStackTrace();
        }

        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");
    }

    public void checkingFooterLinks(ChromeDriver webDriver, Report report, JFXCheckBox checkFooterLinks, Text statusInfo, TextField inputSearch, Properties Homepage){
        // Footer Links
        final String infoMessage = checkFooterLinks.getText();
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
                report.writeToFile("Check all Footers First Column: ");
                for (int i = 0; i < footerFooterLinks.size() ; i++){
                    webDriver.switchTo().window(tabs.get(0));
                    if (footerFooterLinks.get(i).getAttribute("href").contains("newsletter")){
                        isSuccessful = newtab.openCheckURLTitleH1H2(webDriver,footerFooterLinks.get(i).getAttribute("href"),"newsletter");
                        if (isSuccessful){
                            report.writeToFile("TEST FooterLinks "+i+": Successful | ", "found \"" + "Newsletter" + "\" Keyword at URL : "+ footerFooterLinks.get(i).getAttribute("href") );
                        }else {
                            report.writeToFile("unable to check! |", "couldn't found \"" + "Newsletter" + "\" Keyword in URL : "+ footerFooterLinks.get(i).getAttribute("href") );
                            failedTestCases.writeToNamedFile("Checking Footer Links! |", "Please review the following URL: couldn't find \"" + "Newsletter" + "\" Keyword in URL : "+ footerFooterLinks.get(i).getAttribute("href"), "FailAndReview" );
                        }
                    }else {
                        isSuccessful = newtab.openCheckURLTitleH1H2(webDriver,footerFooterLinks.get(i).getAttribute("href"),footerFooterLinks.get(i).getText().trim());
                        if (isSuccessful){
                            report.writeToFile("TEST FooterLinks "+i+": Successful | ", "found \"" + footerFooterLinks.get(i).getText().trim() + "\" Keyword at URL : "+ footerFooterLinks.get(i).getAttribute("href") );
                        }else {
                            report.writeToFile("unable to check! ", "couldn't found \"" + footerFooterLinks.get(i).getText().trim() + "\" Keyword in URL : "+ footerFooterLinks.get(i).getAttribute("href") );
                            failedTestCases.writeToNamedFile("Checking Footer Links! ", "Please check the following URL: couldn't found \"" + footerFooterLinks.get(i).getText().trim() + "\" Keyword in URL : "+ footerFooterLinks.get(i).getAttribute("href"), "FailAndReview" );

                        }
                    }
                }
                report.writeToFile("");
                report.writeToFile("Check all Footers Second Column: ");
                List<WebElement> footerCategoryLinks = webDriver.findElementsByXPath(Homepage.getProperty("page.main.footer.categories"));
                for (int i = 0 ; i < footerCategoryLinks.size() ; i++){
                    webDriver.switchTo().window(tabs.get(0));
                    isSuccessful = newtab.openCheckURLTitleH1H2(webDriver,footerCategoryLinks.get(i).getAttribute("href"),footerCategoryLinks.get(i).getText().trim());
                    if (isSuccessful){
                        report.writeToFile("TEST FooterCategoryLinks "+i+": Successful | ", "found \"" + footerCategoryLinks.get(i).getText().trim() + "\" Keyword at URL : "+ footerCategoryLinks.get(i).getAttribute("href") );
                    }else {
                        report.writeToFile("unable to check! |", "couldn't found \"" + footerCategoryLinks.get(i).getText().trim() + "\" Keyword in URL : "+ footerCategoryLinks.get(i).getAttribute("href") );
                        failedTestCases.writeToNamedFile("Checking Footer Links! |", "Please check the following URL: couldn't found \"" + footerCategoryLinks.get(i).getText().trim() + "\" Keyword in URL : "+ footerCategoryLinks.get(i).getAttribute("href"),"FailAndReview" );

                    }
                }
                ChangeCheckBox.adjustStyle(true,"complete",checkFooterLinks);
                report.writeToFile(infoMessage, "Complete!");
                webDriver.navigate().to(inputSearch.getText().trim());

            }catch (Exception noFooterFound){
                ChangeCheckBox.adjustStyle(true,"nope",checkFooterLinks);
                report.writeToFile(infoMessage, "Couldn't find Footer Element!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: could not find footer links", "FailAndReview");
                noFooterFound.printStackTrace();
            }

        }catch (Exception noFooter){
            ChangeCheckBox.adjustStyle(true,"nope",checkFooterLinks);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check footer links: Browser not responding", "FailAndReview");
            noFooter.printStackTrace();
        }
        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");
    }

    public void checkingSearchAndSuggestions(ChromeDriver webDriver, Report report, JFXCheckBox checkTextSearchAndSuggestions, TextField inputTextSearchAndSuggestions, Text statusInfo, TextField inputSearch, Properties Homepage){

        // Text Search & Suggestions in InputSearch
        final String infoMessage = checkTextSearchAndSuggestions.getText();
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
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.search.suggestion.titles"))));
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.search.suggestion.types"))));
                            List<WebElement> searchAliasesTitles = webDriver.findElementsByXPath(Homepage.getProperty("page.search.suggestion.titles"));
                            List<WebElement> searchAliasesType = webDriver.findElementsByXPath(Homepage.getProperty("page.search.suggestion.types"));
                            report.writeToFile(infoMessage+" for \""+ searchAliases[i].trim()+"\"");
                            failedTestCases.writeToNamedFile(infoMessage+" for \""+ searchAliases[i].trim()+"\"", "FailAndReview" );
                            report.writeToFile("Suggestions :");
                            failedTestCases.writeToNamedFile("Check Suggestions :", "FailAndReview");
                            for (int j = 0 ; j < searchAliasesTitles.size() ; j++){
                                //System.out.println(searchAliasesTitles.get(j).getText() + "  | " + searchAliasesType.get(j).getText());
                                report.writeToFile(searchAliasesTitles.get(j).getText()+" | "+searchAliasesType.get(j).getText() );
                                failedTestCases.writeToNamedFile(searchAliasesTitles.get(j).getText()+" | "+searchAliasesType.get(j).getText(), "FailAndReview" );
                            }
                            report.writeToFile("");
                            element.submit();

                            if ( webDriver.getTitle().contains(searchAliases[i].trim()) | webDriver.getCurrentUrl().contains(searchAliases[i].trim()) ){
                                report.writeToFile("Test Title/URL Keyword \"" + searchAliases[i].trim() + "\" : ", "Successful!");
                            }else{
                                report.writeToFile("Test Title/URL Keyword \"" + searchAliases[i].trim() + "\" : ", "unable to find Keyword at " + webDriver.getCurrentUrl() + " !");
                                failedTestCases.writeToNamedFile("Suggestion Term/URL Keyword \"" + searchAliases[i].trim() + "\" : ", "Please check: unable to find Keyword at URL " + webDriver.getCurrentUrl() + " !", "FailAndReview");
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
                        failedTestCases.writeToNamedFile(infoMessage, "Please check: unable to get suggestions", "FailAndReview");
                        noSearchElements.printStackTrace();
                    }
                }else{
                    ChangeCheckBox.adjustStyle(true,"nope",checkTextSearchAndSuggestions);
                    report.writeToFile(infoMessage, "no Information in Inputfield provided!");
                    failedTestCases.writeToNamedFile(infoMessage, "Please note: no information was provided in input field", "FailAndReview");
                }

            }catch (Exception noSearchBarInput){
                ChangeCheckBox.adjustStyle(true,"nope",checkTextSearchAndSuggestions);
                report.writeToFile(infoMessage, "unable to enter Search Input");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: unable to enter Search Input", "FailAndReview");
                noSearchBarInput.printStackTrace();
            }
        }catch (Exception noInput){
            ChangeCheckBox.adjustStyle(true,"nope",checkTextSearchAndSuggestions);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check suggestions: Browser not responding", "FailAndReview");
            noInput.printStackTrace();
        }
        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");
    }
    public void checkingFeedbackPopUp(ChromeDriver webDriver, Report report, JFXCheckBox checkFeedbackPopUp, Text statusInfo, TextField inputSearch, Properties Homepage){
        // Feedback PopUp
        final String infoMessage = checkFeedbackPopUp.getText();
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
                    failedTestCases.writeToNamedFile(infoMessage, "Please check: Could not close pop up", "FailAndReview");
                    webDriver.navigate().to(inputSearch.getText().trim());
                }

            }catch (Exception noFeedBackButtonFound){
                ChangeCheckBox.adjustStyle(true,"nope",checkFeedbackPopUp);
                report.writeToFile(infoMessage, "unable to check! Couldn't find Feedback Button");
                failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't find Feedback Button", "FailAndReview");
                webDriver.navigate().to(inputSearch.getText().trim());
            }
        }catch (Exception noFeedBack){
            ChangeCheckBox.adjustStyle(true,"nope",checkFeedbackPopUp);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Feedback pop up: browser not responding", "FailAndReview");
            noFeedBack.printStackTrace();
        }
        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");
    }

    public void checkingPrivacyPopUp(ChromeDriver webDriver, Report report, JFXCheckBox checkPrivacyPopUp, Text statusInfo, TextField inputSearch, Properties Homepage) {
        // Privacy PopUp
        final String infoMessage = checkPrivacyPopUp.getText();
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
                failedTestCases.writeToNamedFile(infoMessage, "Please check: Couldn't find Privacy PopUp", "FailAndReview");
                noPrivacyBoxFound.printStackTrace();
            }
        }catch (Exception noPrivacyBox){
            ChangeCheckBox.adjustStyle(true,"nope",checkPrivacyPopUp);
            failedTestCases.writeToNamedFile(infoMessage, "Please check Privacy PopUp: browser not responding", "FailAndReview");
            noPrivacyBox.printStackTrace();
        }
        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");
    }

    public void checkingImprint(ChromeDriver webDriver, Report report, JFXCheckBox checkImprint, Text statusInfo, TextField inputImprintURL, Properties Homepage) {
        final String infoMessage = checkImprint.getText();
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
                    failedTestCases.writeToNamedFile(infoMessage, "Please check Imprint page: user is not redirected to working page", "FailAndReview");
                }

            }catch (Exception noPrivacyBoxFound){
                ChangeCheckBox.adjustStyle(true,"nope",checkImprint);
                report.writeToFile(infoMessage, "unable to find Costumer Service Button");
                failedTestCases.writeToNamedFile(infoMessage, "Please check Imprint page: unable to find Costumer Service Button", "FailAndReview");
                noPrivacyBoxFound.printStackTrace();
            }
        }catch (Exception noPrivacyBox){
            ChangeCheckBox.adjustStyle(true,"nope",checkImprint);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Imprint page: browser not responding", "FailAndReview");
            noPrivacyBox.printStackTrace();
        }
        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");
    }
    public void checkingPrivacyPolicy(ChromeDriver webDriver, Report report, JFXCheckBox PrivacyPolicy, Text statusInfo, TextField inputPrivacyPolicy, Properties Homepage) {
        final String infoMessage = PrivacyPolicy.getText();
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
                    final String previousURL = webDriver.getCurrentUrl().toLowerCase().trim();
                    webDriver.findElementByXPath(Homepage.getProperty("privacypage.link")).click();
                    if (webDriver.getCurrentUrl().toLowerCase().trim() != previousURL){
                        report.writeToFile(infoMessage, "Successful!");
                        ChangeCheckBox.adjustStyle(true,"complete",PrivacyPolicy);
                    }else{
                        report.writeToFile(infoMessage, "Failed !");
                        failedTestCases.writeToNamedFile(infoMessage, "Please check Terms of Use page: link to Privacy policy not working", "FailAndReview");
                        ChangeCheckBox.adjustStyle(true,"nope",PrivacyPolicy);
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",PrivacyPolicy);
                    webDriver.navigate().to(inputPrivacyPolicy.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect \"Privacy\" Link");
                    failedTestCases.writeToNamedFile(infoMessage, "Please check Terms of Use page: could not find link to Privacy policy (Please ignore this error if not applicable to specific country)", "FailAndReview");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",PrivacyPolicy);
                webDriver.navigate().to(inputPrivacyPolicy.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                failedTestCases.writeToNamedFile(infoMessage, "Please check Terms of Use page: could not navigate to Privacy policy page", "FailAndReview");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",PrivacyPolicy);
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            failedTestCases.writeToNamedFile(infoMessage, "Please check Privacy Policy link on Terms of Use page: browser not working", "FailAndReview");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");
        failedTestCases.writeToNamedFile("=================================","FailAndReview");

    }
}
