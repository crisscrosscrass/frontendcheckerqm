package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tasks.Report;
import crisscrosscrass.Tasks.ScreenshotViaWebDriver;
import crisscrosscrass.Tasks.WebdriverTab;
import javafx.application.Platform;
import javafx.scene.control.TextField;
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


        // Check on Category Links - Left Side Menu
        Platform.runLater(() -> {
            checkCategoryLinksLeftSideMenu.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Category Links...");
        });
        String xpathPattern1 = Homepage.getProperty("page.main.category.links.left");
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

    public void checkingShopOfTheWeek(ChromeDriver webDriver, Report report, JFXCheckBox checkLogoFromShopOfTheWeek, Text statusInfo, TextField inputSearch, Properties Homepage){
        // Check on Logo Shop of the Week
        Platform.runLater(() -> {
            checkLogoFromShopOfTheWeek.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Logo Shop of the Week...");
        });
        String xpathPattern1 = Homepage.getProperty("page.main.shop.promo.link");
        String xpathPatternImage1 = Homepage.getProperty("page.main.shop.promo.image");
        String xpathPatternImage2 = Homepage.getProperty("page.grid.shop.image");
        try{
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                boolean isAvailable = webDriver.findElementByXPath(xpathPattern1) != null;
                Point hoverItem = webDriver.findElement(By.xpath(xpathPattern1)).getLocation();
                ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                String ShopOfTheWeekImage = webDriver.findElementByXPath(xpathPatternImage1).getAttribute("src");
                webDriver.findElementByXPath(xpathPattern1).click();
                boolean isSuccessful;
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
    public void checkingShopOfTheWeekCategories(ChromeDriver webDriver, Report report, JFXCheckBox checkCategoryLinksFromShopOfTheWeek, Text statusInfo, TextField inputSearch, Properties Homepage){
        // Category Links from Shop of the Week
        Platform.runLater(() -> {
            checkCategoryLinksFromShopOfTheWeek.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Category Links from Shop of the Week ...");
        });
        String xpathPattern1 = Homepage.getProperty("page.main.shop.promo.category");
        String xpathPatternImage1 = Homepage.getProperty("page.main.shop.promo.image");
        String xpathPatternImage2 = Homepage.getProperty("page.grid.shop.image");
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try{
                boolean isAvailable = webDriver.findElementByXPath(xpathPattern1) != null;
                String ShopOfTheWeekImage = webDriver.findElementByXPath(xpathPatternImage1).getAttribute("src");
                List<WebElement> shopCategoryLinks = webDriver.findElementsByXPath(xpathPattern1);
                List<WebElement> shopCategoryNames = webDriver.findElementsByXPath("//*[@id='pagecontent']/*/*[@class='hp-shop-promo']/*[contains(@class, 'hp-shop-promo-categorylink')]/div/a/div/div[1]");
                WebdriverTab newtab = new WebdriverTab();
                //use shopCategoryLinks.size() instead of 1 for Loop!
                for (int i = 0 ; i < shopCategoryLinks.size() ; i++){
                    webDriver.switchTo().window(tabs.get(0));
                    boolean isSuccessful = newtab.open(webDriver, shopCategoryLinks.get(i).getAttribute("href"), shopCategoryNames.get(i).getAttribute("textContent").trim(), ShopOfTheWeekImage, xpathPatternImage2);
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
    public void checkingNewsletterBanner(ChromeDriver webDriver, Report report, JFXCheckBox checkNewsletterBannerFunctionality, Text statusInfo, TextField inputSearch, TextField inputEmailAdress, Properties Homepage){
        // Newsletter Banner Functionality
        Platform.runLater(() -> {
            checkNewsletterBannerFunctionality.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Newsletter Banner Functionality...");
        });
        String xpathPattern1 = Homepage.getProperty("page.main.newsletter.input");
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try{
                            /*
                            Point hoverItem = webDriver.findElement(By.xpath("//*[@id='newsletter']")).getLocation();
                            ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                            ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                            */

                WebElement element = webDriver.findElementByXPath(xpathPattern1);
                element.sendKeys(inputEmailAdress.getText());
                element.submit();
                boolean isAvailable = webDriver.findElementByXPath("//*[@id=\"confirmation-wrapper\"]") != null;
                try{
                    if (webDriver.getCurrentUrl().contains("newsletter.html")) {
                        Platform.runLater(() -> {
                            checkNewsletterBannerFunctionality.setStyle("-fx-background-color: #CCFF99");
                            checkNewsletterBannerFunctionality.setSelected(true);
                        });
                        report.writeToFile("Checking Newsletter Banner Functionality: ", "Successful!");
                    } else {
                        Platform.runLater(() -> {
                            checkNewsletterBannerFunctionality.setStyle("-fx-background-color: #FF0000");
                            checkNewsletterBannerFunctionality.setSelected(true);
                        });
                        report.writeToFile("Checking Newsletter Banner Functionality: ", "Not working!");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                }catch (Exception noConfirmationWrapper){
                    Platform.runLater(() -> {
                        checkNewsletterBannerFunctionality.setStyle("-fx-background-color: #FF0000");
                        checkNewsletterBannerFunctionality.setSelected(true);
                    });
                    boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "NewsLetterBannerSignInPage.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("Checking Logo Shop Screenshot: ", "Screenshot not successful!");
                    }
                    report.writeToFile("Checking Newsletter Banner Functionality: ", "ConfirmationPage is missing!");
                    webDriver.navigate().to(inputSearch.getText().trim());
                }


            }catch (Exception noScrollingToElement){
                report.writeToFile("Checking Newsletter Banner Functionality: ", "Couldn't find Newsletter Element!");
                noScrollingToElement.printStackTrace();
            }

        }catch (Exception noNewsLetterPromo){
            Platform.runLater(() -> {
                checkNewsletterBannerFunctionality.setStyle("-fx-background-color: #FF0000");
                checkNewsletterBannerFunctionality.setSelected(true);
            });
            report.writeToFile("Checking Newsletter Banner Functionality: ", "unable to check! Browser not responding");
            noNewsLetterPromo.printStackTrace();
        }

        report.writeToFile("=================================", "");
    }
    public void checkingNewsletterPopUp(ChromeDriver webDriver, Report report, JFXCheckBox checkNewsletterPopUp, Text statusInfo, TextField inputSearch, Properties Homepage){
        // Newsletter PopUp
        Platform.runLater(() -> {
            checkNewsletterPopUp.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Newsletter PopUp...");
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
                    Platform.runLater(() -> {
                        checkNewsletterPopUp.setStyle("-fx-background-color: #CCFF99");
                        checkNewsletterPopUp.setSelected(true);
                    });
                    report.writeToFile("Checking Newsletter PopUp: ", "Successful!");
                }catch (Exception noNewsletterIconCloseFound){
                    Platform.runLater(() -> {
                        checkNewsletterPopUp.setStyle("-fx-background-color: #FF0000");
                        checkNewsletterPopUp.setSelected(true);
                    });
                    report.writeToFile("Checking Newsletter PopUp: ", "Couldn't close Newsletter Element!");
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
                Platform.runLater(() -> {
                    checkNewsletterPopUp.setStyle("-fx-background-color: #FF0000");
                    checkNewsletterPopUp.setSelected(true);
                });
                report.writeToFile("Checking Newsletter PopUp: ", "Couldn't find Newsletter Icon Element!");
                noNewsletterIconFound.printStackTrace();
            }

        }catch (Exception noNewsLetterIcon){
            Platform.runLater(() -> {
                checkNewsletterPopUp.setStyle("-fx-background-color: #FF0000");
                checkNewsletterPopUp.setSelected(true);
            });
            report.writeToFile("Checking Newsletter Pop Functionality: ", "unable to check! Browser not responding");
            noNewsLetterIcon.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
    public void checkingNewsletterPopUpFunctionality(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox checkNewsletterPopUpFunctionality, Text statusInfo, TextField inputSearch, TextField inputEmailAdress, Properties Homepage){
        // Newsletter PopUp Functionality
        Platform.runLater(() -> {
            checkNewsletterPopUpFunctionality.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Newsletter PopUp Functionality...");
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
                            Platform.runLater(() -> {
                                checkNewsletterPopUpFunctionality.setStyle("-fx-background-color: #CCFF99");
                                checkNewsletterPopUpFunctionality.setSelected(true);
                            });
                            report.writeToFile("Checking Newsletter PopUp Functionality: ", "Successful!");
                        } else {
                            Platform.runLater(() -> {
                                checkNewsletterPopUpFunctionality.setStyle("-fx-background-color: #FF0000");
                                checkNewsletterPopUpFunctionality.setSelected(true);
                            });
                            report.writeToFile("Checking Newsletter PopUp Functionality: ", "Not working!");
                        }
                        webDriver.navigate().to(inputSearch.getText().trim());
                    }catch (Exception noConfirmationWrapper){
                        Platform.runLater(() -> {
                            checkNewsletterPopUpFunctionality.setStyle("-fx-background-color: #FF0000");
                            checkNewsletterPopUpFunctionality.setSelected(true);
                        });
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
                    Platform.runLater(() -> {
                        checkNewsletterPopUpFunctionality.setStyle("-fx-background-color: #FF0000");
                        checkNewsletterPopUpFunctionality.setSelected(true);
                    });
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
                Platform.runLater(() -> {
                    checkNewsletterPopUpFunctionality.setStyle("-fx-background-color: #FF0000");
                    checkNewsletterPopUpFunctionality.setSelected(true);
                });
                report.writeToFile("Checking Newsletter PopUp Functionality: ", "Couldn't find Newsletter Icon Element!");
                noNewsletterIconFound.printStackTrace();
            }

        }catch (Exception noNewsLetterIcon){
            Platform.runLater(() -> {
                checkNewsletterPopUpFunctionality.setStyle("-fx-background-color: #FF0000");
                checkNewsletterPopUpFunctionality.setSelected(true);
            });
            report.writeToFile("Checking Newsletter Pop Functionality: ", "unable to check! Browser not responding");
            noNewsLetterIcon.printStackTrace();
        }

        report.writeToFile("=================================", "");
    }

    public void checkingFooterLinks(ChromeDriver webDriver, Report report, JFXCheckBox checkFooterLinks, Text statusInfo, TextField inputSearch, Properties Homepage){
        // Footer Links
        Platform.runLater(() -> {
            checkFooterLinks.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Footer Links & Categories...");
        });
        String xpathPattern1 = Homepage.getProperty("page.main.footer.links");
        String xpathPattern2 = Homepage.getProperty("page.main.footer.categories");
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                boolean isAvailable = webDriver.findElementByXPath(xpathPattern1) != null;
                Point hoverItem = webDriver.findElement(By.xpath(xpathPattern1)).getLocation();
                ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                WebdriverTab newtab = new WebdriverTab();
                //check all footerLinks
                List<WebElement> footerFooterLinks = webDriver.findElementsByXPath(xpathPattern1);
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
                List<WebElement> footerCategoryLinks = webDriver.findElementsByXPath(xpathPattern2);
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
                Platform.runLater(() -> {
                    checkFooterLinks.setStyle("-fx-background-color: #CCFF99");
                    checkFooterLinks.setSelected(true);
                });
                report.writeToFile("Checking Footer Links & Categories: ", "Complete!");
                webDriver.navigate().to(inputSearch.getText().trim());

            }catch (Exception noFooterFound){
                Platform.runLater(() -> {
                    checkFooterLinks.setStyle("-fx-background-color: #FF0000");
                    checkFooterLinks.setSelected(true);
                });
                report.writeToFile("Checking Footer Links & Categories: ", "Couldn't find Footer Element!");
                noFooterFound.printStackTrace();
            }

        }catch (Exception noFooter){
            Platform.runLater(() -> {
                checkFooterLinks.setStyle("-fx-background-color: #FF0000");
                checkFooterLinks.setSelected(true);
            });
            report.writeToFile("Checking Footer Links & Categories: ", "unable to check! Browser not responding");
            noFooter.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }

    public void checkingSearchAndSuggestions(ChromeDriver webDriver, Report report, JFXCheckBox checkTextSearchAndSuggestions, TextField inputTextSearchAndSuggestions, Text statusInfo, TextField inputSearch, Properties Homepage){

        // Text Search & Suggestions in InputSearch
        Platform.runLater(() -> {
            checkTextSearchAndSuggestions.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Text Search and Suggestions in InputSearch...");
        });
        String xpathPattern1 = "//*[@id=\"header-search-input\"]";
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
                            report.writeToFile("Checking Text Search and Suggestions for \""+ searchAliases[i].trim()+"\"");
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
                        Platform.runLater(() -> {
                            checkTextSearchAndSuggestions.setStyle("-fx-background-color: #CCFF99");
                            checkTextSearchAndSuggestions.setSelected(true);
                        });
                        report.writeToFile("Checking Text Search and Suggestions: ", "Complete!");
                        webDriver.navigate().to(inputSearch.getText().trim());
                    }catch (Exception noSearchElements){
                        Platform.runLater(() -> {
                            checkTextSearchAndSuggestions.setStyle("-fx-background-color: #FF0000");
                            checkTextSearchAndSuggestions.setSelected(true);
                        });
                        report.writeToFile("Checking Text Search and Suggestions: ", "unable to get Suggestions!");
                        noSearchElements.printStackTrace();
                    }
                }else{
                    Platform.runLater(() -> {
                        checkTextSearchAndSuggestions.setStyle("-fx-background-color: #FF0000");
                        checkTextSearchAndSuggestions.setSelected(true);
                    });
                    report.writeToFile("Checking Text Search and Suggestions: ", "no Informations in Inputfield provided!");
                }

            }catch (Exception noSearchBarInput){
                Platform.runLater(() -> {
                    checkTextSearchAndSuggestions.setStyle("-fx-background-color: #FF0000");
                    checkTextSearchAndSuggestions.setSelected(true);
                });
                report.writeToFile("Checking Text Search and Suggestions: ", "unable to enter Search Input");
                noSearchBarInput.printStackTrace();
            }
        }catch (Exception noInput){
            Platform.runLater(() -> {
                checkTextSearchAndSuggestions.setStyle("-fx-background-color: #FF0000");
                checkTextSearchAndSuggestions.setSelected(true);
            });
            report.writeToFile("Checking Text Search and Suggestions: ", "unable to check! Browser not responding");
            noInput.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
    public void checkingFeedbackPopUp(ChromeDriver webDriver, Report report, JFXCheckBox checkFeedbackPopUp, Text statusInfo, TextField inputSearch, Properties Homepage){
        // Feedback PopUp
        Platform.runLater(() -> {
            checkFeedbackPopUp.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Feedback PopUp...");
        });
        String xpathPattern1 = Homepage.getProperty("page.main.feedback.icon");
        String xpathPattern2 = Homepage.getProperty("page.main.feedback.close");
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            WebDriverWait wait = new WebDriverWait(webDriver, 10);
            try{
                boolean isAvailable = webDriver.findElementByXPath(xpathPattern1) != null;
                webDriver.findElementByXPath(xpathPattern1).click();
                try{
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPattern2)));
                    webDriver.findElementByXPath(xpathPattern2).click();
                    Platform.runLater(() -> {
                        checkFeedbackPopUp.setStyle("-fx-background-color: #CCFF99");
                        checkFeedbackPopUp.setSelected(true);
                    });
                    report.writeToFile("Checking Feedback PopUp: ", "Complete!");
                    webDriver.navigate().to(inputSearch.getText().trim());

                }catch (Exception noClosingFeedBackButton){
                    Platform.runLater(() -> {
                        checkFeedbackPopUp.setStyle("-fx-background-color: #FF0000");
                        checkFeedbackPopUp.setSelected(true);
                    });
                    report.writeToFile("Checking Feedback PopUp: ", "couldn't close PopUp!");
                    webDriver.navigate().to(inputSearch.getText().trim());
                }

            }catch (Exception noFeedBackButtonFound){
                Platform.runLater(() -> {
                    checkFeedbackPopUp.setStyle("-fx-background-color: #FF0000");
                    checkFeedbackPopUp.setSelected(true);
                });
                report.writeToFile("Checking Feedback PopUp: ", "unable to check! Couldn't find Feedback Button");
                webDriver.navigate().to(inputSearch.getText().trim());
            }
        }catch (Exception noFeedBack){
            Platform.runLater(() -> {
                checkFeedbackPopUp.setStyle("-fx-background-color: #FF0000");
                checkFeedbackPopUp.setSelected(true);
            });
            report.writeToFile("Checking Feedback PopUp: ", "unable to check! Browser not responding");
            noFeedBack.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }

    public void checkingPrivacyPopUp(ChromeDriver webDriver, Report report, JFXCheckBox checkPrivacyPopUp, Text statusInfo, TextField inputSearch, Properties Homepage) {
        // Privacy PopUp
        Platform.runLater(() -> {
            checkPrivacyPopUp.setStyle("-fx-background-color: #eef442");
            statusInfo.setText("Checking Privacy PopUp...");
        });
        String xpathPattern1 = Homepage.getProperty("page.main.privacy.popup");
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            webDriver.navigate().to(inputSearch.getText().trim());
            WebDriverWait wait = new WebDriverWait(webDriver, 10);
            try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPattern1)));
                boolean isAvailable = webDriver.findElementByXPath(xpathPattern1) != null;
                webDriver.findElementByXPath(xpathPattern1).click();
                ArrayList<String> tabsPrivacy = new ArrayList<>(webDriver.getWindowHandles());
                webDriver.switchTo().window(tabsPrivacy.get(1)).close();
                webDriver.switchTo().window(tabsPrivacy.get(0));
                Platform.runLater(() -> {
                    checkPrivacyPopUp.setStyle("-fx-background-color: #CCFF99");
                    checkPrivacyPopUp.setSelected(true);
                });
                report.writeToFile("Checking Privacy PopUp: ", "Complete!");

                webDriver.navigate().to(inputSearch.getText().trim());
            }catch (Exception noPrivacyBoxFound){
                Platform.runLater(() -> {
                    checkPrivacyPopUp.setStyle("-fx-background-color: #FF0000");
                    checkPrivacyPopUp.setSelected(true);
                });
                report.writeToFile("Checking Privacy PopUp: ", "unable to find Privacy PopUp");
                noPrivacyBoxFound.printStackTrace();
            }
        }catch (Exception noPrivacyBox){
            Platform.runLater(() -> {
                checkPrivacyPopUp.setStyle("-fx-background-color: #FF0000");
                checkPrivacyPopUp.setSelected(true);
            });
            report.writeToFile("Checking Privacy PopUp: ", "unable to check! Browser not responding");
            noPrivacyBox.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
}
