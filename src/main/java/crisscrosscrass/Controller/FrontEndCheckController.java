package crisscrosscrass.Controller;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.*;
import crisscrosscrass.Tasks.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javafx.scene.text.TextAlignment;
import javafx.stage.Window;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.*;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FrontEndCheckController {

    @FXML
    Button startwebdriver;
    @FXML
    JFXCheckBox checkCategoryLinksLeftSideMenu;
    @FXML
    JFXCheckBox checkLogoFromShopOfTheWeek;
    @FXML
    JFXCheckBox checkCategoryLinksFromShopOfTheWeek;
    @FXML
    JFXCheckBox checkNewsletterBannerFunctionality;
    @FXML
    JFXCheckBox checkNewsletterPopUp;
    @FXML
    CheckBox checkLogoHomepage;
    @FXML
    CheckBox checkGeneralLayout;
    @FXML
    CheckBox openMainMenu;
    @FXML
    CheckBox checkBannersLayout;
    @FXML
    CheckBox checkShopOfTheWeek;
    @FXML
    CheckBox checkPerfectMatch;
    @FXML
    CheckBox checkSalesPrice;
    @FXML
    CheckBox checkFilter;
    @FXML
    ProgressBar progressIndicator;
    @FXML
    Text statusInfo;
    @FXML
    TextField inputPerfectMatch;
    @FXML
    TextField inputSearch;
    @FXML
    TextField inputEmailAdress;
    @FXML
    HBox outputPlace;
    @FXML
    ImageView preloaderCat;
    @FXML
    VBox CheckBoxesPlace;
    @FXML
    AnchorPane mainStage;
    @FXML
    TabPane tabPane;
    @FXML
    Tab brandTab;

    private static boolean isSuccessful = false;
    private static boolean isAvailable = false;
    private static String xpathPattern = "";
    private static String xpathPatternImage1 = "";
    private static String xpathPatternImage2 = "";


    @FXML
    public void startRealAction() {

        System.out.println("Start Engine...");


        Platform.runLater(() -> {


            progressIndicator.setProgress(-1);
            startwebdriver.setDisable(true);
            inputPerfectMatch.setDisable(true);
            inputSearch.setDisable(true);
            inputEmailAdress.setDisable(true);
            Task task = new Task<Object>() {
                @Override
                protected Void call() throws InterruptedException {
                    resetAllFormOptions();
                    takeRoutine();
                    return null;
                }

                private void takeRoutine() throws InterruptedException {

                    // * Load Properties File
                    String resourceName = "configs/page.properties";
                    ClassLoader loader = Thread.currentThread().getContextClassLoader();
                    Properties Homepage = new Properties();
                    try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
                        Homepage.load(resourceStream);
                    }catch (Exception nope){
                        nope.getStackTrace();
                    }



                    // * Basic Settings before Starting WebDriver
                    // * Browser, Javascript , etc.
                    Platform.runLater(() -> {
                        statusInfo.setText("Detecting Sources...");
                        //DocFlavor.URL catty = Main.class.getResource("preloaderCat.gif");
                        URL cattyLocation = Main.class.getClassLoader().getResource("Images/preloaderCat.gif");
                        Image catty = new Image(String.valueOf(cattyLocation));
                        preloaderCat.setImage(catty);
                    });

                    File webdriverFile = new File("temp//chromedriver.exe");
                    if (!webdriverFile.exists()) {
                        System.out.println("Webdriver not exist");
                        copyFiles();
                    }

                    Platform.runLater(() -> statusInfo.setText("Starting Engine..."));


                    System.setProperty("webdriver.chrome.driver", "temp//chromedriver.exe");
                    ChromeOptions option = new ChromeOptions();
                    option.addArguments("disable-infobars");
                    option.addArguments("start-maximized");
                    ChromeDriver webDriver = new ChromeDriver(option);

                    JavascriptExecutor js = webDriver;
                    Report report = new Report();
                    report.clearWrittenReport();
                    ScreenshotViaWebDriver.clearWrittenScreenshots();


                    // open Startpage and have a basic overview
                    Platform.runLater(() -> {
                        Window window = mainStage.getScene().getWindow();
                        window.requestFocus();
                        statusInfo.setText("Open Maximize Mode...");
                    });

                    webDriver.manage().window().maximize();
                    Platform.runLater(() -> statusInfo.setText("Go to requested Website..."));

                    long start = System.currentTimeMillis();
                    webDriver.navigate().to(inputSearch.getText().trim());
                    long finish = System.currentTimeMillis();
                    long totalTime = finish - start;
                    System.out.println("Total Time for page load - "+totalTime);

                    report.writeToFile("Checking Website: ", inputSearch.getText().trim());


                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");





                    // Check on Category Links - Left Side Menu
                    Platform.runLater(() -> {
                        checkCategoryLinksLeftSideMenu.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Category Links...");
                    });
                    xpathPattern = Homepage.getProperty("page.main.category.links.left");
                    try {
                        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(0));
                            try {
                                isAvailable = webDriver.findElementByXPath(xpathPattern) != null;
                                List<WebElement> CategoryLinksLeftSideMenu = webDriver.findElementsByXPath(xpathPattern);
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
                        report.writeToFile("Checking Category Links: ", "unable to check! Browser not responding");
                    }

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");




                    // Check on Logo Shop of the Week
                    Platform.runLater(() -> {
                        checkLogoFromShopOfTheWeek.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Logo Shop of the Week...");
                    });
                    xpathPattern = Homepage.getProperty("page.main.shop.promo.link");
                    xpathPatternImage1 = Homepage.getProperty("page.main.shop.promo.image");
                    xpathPatternImage2 = Homepage.getProperty("page.grid.shop.image");
                    try{
                        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(0));
                        try {
                            isAvailable = webDriver.findElementByXPath(xpathPattern) != null;
                            String ShopOfTheWeekImage = webDriver.findElementByXPath(xpathPatternImage1).getAttribute("src");
                            webDriver.findElementByXPath(xpathPattern).click();
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
                                noLogoFoundOnGrid.printStackTrace();
                            }


                        }catch (Exception noShopLogoFound){
                            Platform.runLater(() -> {
                                checkLogoFromShopOfTheWeek.setStyle("-fx-background-color: #FF0000");
                                checkLogoFromShopOfTheWeek.setSelected(true);
                            });
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

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");





                    // Category Links from Shop of the Week
                    Platform.runLater(() -> {
                        checkCategoryLinksFromShopOfTheWeek.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Category Links from Shop of the Week ...");
                    });
                    xpathPattern = Homepage.getProperty("page.main.shop.promo.category");
                    xpathPatternImage1 = Homepage.getProperty("page.main.shop.promo.image");
                    xpathPatternImage2 = Homepage.getProperty("page.grid.shop.image");
                    try {
                        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(0));
                        try{
                            isAvailable = webDriver.findElementByXPath(xpathPattern) != null;
                            String ShopOfTheWeekImage = webDriver.findElementByXPath(xpathPatternImage1).getAttribute("src");
                            List<WebElement> shopCategoryLinks = webDriver.findElementsByXPath(xpathPattern);
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

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");





                    // Newsletter Banner Functionality
                    Platform.runLater(() -> {
                        checkNewsletterBannerFunctionality.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Newsletter Banner Functionality...");
                    });
                    xpathPattern = Homepage.getProperty("page.main.newsletter.input");
                    try {
                        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(0));
                        try{
                            /*
                            Point hoverItem = webDriver.findElement(By.xpath("//*[@id='newsletter']")).getLocation();
                            ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                            ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                            */

                            WebElement element = webDriver.findElementByXPath(xpathPattern);
                            element.sendKeys(inputEmailAdress.getText());
                            element.submit();

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

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");




                    // Newsletter PopUp
                    Platform.runLater(() -> {
                        checkNewsletterPopUp.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Newsletter PopUp...");
                    });
                    xpathPattern = Homepage.getProperty("page.main.newsletter.icon");
                    try {
                        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(0));
                        try {
                            isAvailable = webDriver.findElementByXPath(xpathPattern) != null;
                            webDriver.findElementByXPath(xpathPattern).click();
                            webDriver.findElementByXPath("//*[@id=\"inactivity_popup\"]/div[1]").click();
                            Platform.runLater(() -> {
                                checkNewsletterPopUp.setStyle("-fx-background-color: #CCFF99");
                                checkNewsletterPopUp.setSelected(true);
                            });
                            report.writeToFile("Checking Newsletter PopUp: ", "Successful!");
                        }catch (Exception noNewsletterIconFound){
                            Platform.runLater(() -> {
                                checkNewsletterPopUp.setStyle("-fx-background-color: #FF0000");
                                checkNewsletterPopUp.setSelected(true);
                            });
                            report.writeToFile("Checking Newsletter PopUp: ", "Couldn't find Newsletter Element!");
                            noNewsletterIconFound.printStackTrace();
                        }


                    }catch (Exception noNewsLetterIcon){
                        Platform.runLater(() -> {
                            checkNewsletterPopUp.setStyle("-fx-background-color: #FF0000");
                            checkNewsletterPopUp.setSelected(true);
                        });
                        report.writeToFile("Checking Newsletter Icon Functionality: ", "unable to check! Browser not responding");
                        noNewsLetterIcon.printStackTrace();
                    }

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");



                    // Click on Logo Test
                    Platform.runLater(() -> {
                        checkLogoHomepage.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Logo...");
                    });
                    xpathPattern = Homepage.getProperty("page.main.logo");

                    try {
                        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(0));
                        webDriver.findElementByXPath(xpathPattern).click();

                        Platform.runLater(() -> {
                            checkLogoHomepage.setStyle("-fx-background-color: #CCFF99");
                            checkLogoHomepage.setSelected(true);
                        });
                        report.writeToFile("Checking Logo: ", "Successful!");

                    } catch (Exception Message) {
                        Platform.runLater(() -> {
                            statusInfo.setText("no Logo...");
                            checkLogoHomepage.setStyle("-fx-background-color: #FF0000");
                            checkLogoHomepage.setSelected(true);
                        });
                        report.writeToFile("Checking Logo: ", "unable to check! Browser not responding");
                    }

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");


                    //Check general Layout Test
                    Platform.runLater(() -> {
                        checkGeneralLayout.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Layout...");
                    });
                    Thread.sleep(100);

                    try {
                        // make SCREENSHOT 1 | parameter "webdriver" + "name";
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"screenshot1.png");
                        if (isSuccessful){
                            report.writeToFile("Checking Layout: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("Checking Layout: ", "Screenshot not successful!");
                        }


                        // Scroll down
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,100)");
                        }


                        // make SCREENSHOT 2
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"screenshot2.png");
                        if (isSuccessful){
                            report.writeToFile("Checking Layout: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("Checking Layout: ", "Screenshot not successful!");
                        }

                        // Scroll down
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,100)");
                        }

                        // make SCREENSHOT 3
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"screenshot3.png");
                        if (isSuccessful){
                            report.writeToFile("Checking Layout: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("Checking Layout: ", "Screenshot not successful!");
                        }

                        // Scroll down
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,100)");
                        }

                        // make SCREENSHOT 4
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"screenshot4.png");
                        if (isSuccessful){
                            report.writeToFile("Checking Layout: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("Checking Layout: ", "Screenshot not successful!");
                        }

                        Platform.runLater(() -> {
                            checkGeneralLayout.setStyle("-fx-background-color: #CCFF99");
                            checkGeneralLayout.setSelected(true);
                        });
                    }catch (Exception noLayoutTest){
                        Platform.runLater(() -> {
                            checkGeneralLayout.setStyle("-fx-background-color: #FF0000");
                            checkGeneralLayout.setSelected(true);
                        });
                        report.writeToFile("Checking Layout: ", "unable to check! Browser not responding");
                    }


                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");




                    // open Hover Main Menu and have a check on all DeepLinks Test
                    Platform.runLater(() -> {
                        openMainMenu.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Menu...");
                    });

                    Actions hover = new Actions(webDriver);

                    //String winHandleBefore = webDriver.getWindowHandle();
                    //webDriver.switchTo().window(winHandleBefore);
                    try {
                        List<WebElement> MainMenu = webDriver.findElementsByXPath(Homepage.getProperty("page.main.links"));
                        int counterInfo = 1;
                        for (WebElement MainMenuItem : MainMenu) {
                            hover.moveToElement(MainMenuItem).perform();
                            //System.out.println("MainMenuLink: "+ MainMenuItem.getText() + " " + counterInfo + MainMenuItem.getAttribute("href"));
                            report.writeToFile("MainMenuLink: "+ counterInfo + " " + MainMenuItem.getText(), MainMenuItem.getAttribute("href"));
                            counterInfo++;
                        }
                        Thread.sleep(100);

                        //checking Sub Main Menu Links
                        Platform.runLater(() -> statusInfo.setText("Checking SubMenu..."));

                        List<WebElement> MainSubMenu = webDriver.findElementsByXPath(Homepage.getProperty("page.submain.links"));

                        /*//TODO remove this again
                        int counterSubMenu = 1;
                        for (WebElement ItemSubMenu : MainSubMenu) {
                            //System.out.println("MainSubLink: "+ ItemSubMenu.getAttribute("textContent") + " " + ItemSubMenu.getAttribute("href"));
                            Platform.runLater(() -> statusInfo.setText(ItemSubMenu.getAttribute("href")));
                            report.writeToFile("MainMenuSubLink: "+ counterSubMenu + " " + ItemSubMenu.getAttribute("textContent"), ItemSubMenu.getAttribute("href"));
                            counterSubMenu++;
                        }
                        */

                        report.writeToFile("=================================", "");

                        //int randomPicker = 0;
                        Platform.runLater(() -> {
                            statusInfo.setText("Test 10 SubMenu Links in new Tab...");
                        });
                        //test Open New Tab
                        WebdriverTab newtab = new WebdriverTab();
                        for (int i = 0 ; i < 2 ; i++){
                            //randomPicker = 0 + (int)(Math.random() * (((MainSubMenu.size()-1) - 0) + 1));
                            isSuccessful = newtab.open(webDriver,MainSubMenu.get(i).getAttribute("href"),MainSubMenu.get(i).getAttribute("textContent"));
                            if (isSuccessful){
                                report.writeToFile("TEST MainMenuSubLink "+i+": Successful | ", "found \"" + MainSubMenu.get(i).getAttribute("textContent") + "\" Keyword at URL : "+ MainSubMenu.get(i).getAttribute("href"));
                            }else {
                                report.writeToFile("TEST MainMenuSubLink "+i+": unable to check! |", "couldn't found \"" + MainSubMenu.get(i).getAttribute("textContent") + "\" Keyword in URL : "+ MainSubMenu.get(i).getAttribute("href"));
                            }
                        }

                        Platform.runLater(() -> {
                            openMainMenu.setStyle("-fx-background-color: #CCFF99");
                            openMainMenu.setSelected(true);
                        });
                        report.writeToFile("Checking Menu: ", "Complete!");
                    } catch (Exception noHover) {
                        noHover.printStackTrace();
                        Platform.runLater(() -> {
                            openMainMenu.setStyle("-fx-background-color: #FF0000");
                            openMainMenu.setSelected(true);
                        });
                        report.writeToFile("Checking Menu: ", "unable to complete! Browser not responding");
                    }
                    Thread.sleep(1000);

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");



                    // Check banner for layout
                    Platform.runLater(() -> {
                        checkBannersLayout.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Banners...");
                    });

                    try{
                        List<WebElement> infos = webDriver.findElementsByXPath(Homepage.getProperty("page.main.banner"));
                        int MainMenuCounter = 1;
                        for (WebElement info : infos) {
                            hover.moveToElement(info).perform();
                            report.writeToFile("BannerLink " + MainMenuCounter, info.getAttribute("href"));
                            MainMenuCounter++;
                        }

                        Platform.runLater(() -> {
                            checkBannersLayout.setStyle("-fx-background-color: #CCFF99");
                            checkBannersLayout.setSelected(true);
                        });
                        report.writeToFile("Checking Banner: ", "Successful!");
                    }catch (Exception noBanner){
                        noBanner.printStackTrace();
                        Platform.runLater(() -> {
                            checkBannersLayout.setStyle("-fx-background-color: #FF0000");
                            checkBannersLayout.setSelected(true);
                        });
                        report.writeToFile("Checking Banner: ", "unable to complete! Browser not responding");
                    }



                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));


                    // check shop of the week

                    Platform.runLater(() -> {
                        checkShopOfTheWeek.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Shop of the Week...");
                    });

                    try {
                        for (int i = 0; i < 10; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,100)");
                        }

                        Platform.runLater(() -> {
                            checkShopOfTheWeek.setStyle("-fx-background-color: #CCFF99");
                            checkShopOfTheWeek.setSelected(true);
                        });
                    }catch (Exception noShop){
                        noShop.printStackTrace();
                        Platform.runLater(() -> {
                            checkShopOfTheWeek.setStyle("-fx-background-color: #FF0000");
                            checkShopOfTheWeek.setSelected(true);
                        });
                    }


                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");


                    // go to Search Input and look for PerfectMatch
                    Platform.runLater(() -> {
                        checkPerfectMatch.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Perfect Match...");
                    });
                    try{
                        WebElement element = webDriver.findElement(By.id(Homepage.getProperty("page.search.bar")));
                        element.sendKeys(inputPerfectMatch.getText());
                        element.submit();

                        if (webDriver.getCurrentUrl().contains("?q=")) {
                            Platform.runLater(() -> {
                                checkPerfectMatch.setStyle("-fx-background-color: #FF0000");
                                checkPerfectMatch.setSelected(true);
                            });
                            report.writeToFile("Checking Perfect Match: ", "unable to check!");
                        } else {
                            Platform.runLater(() -> {
                                checkPerfectMatch.setStyle("-fx-background-color: #CCFF99");
                                checkPerfectMatch.setSelected(true);
                            });
                            report.writeToFile("Checking Perfect Match: ", "Successful!");
                        }
                    }catch (Exception noPerfectMatch){
                        Platform.runLater(() -> {
                            checkPerfectMatch.setStyle("-fx-background-color: #FF0000");
                            checkPerfectMatch.setSelected(true);
                        });
                        report.writeToFile("Checking Perfect Match: ", "unable to complete! Browser not responding");
                        noPerfectMatch.printStackTrace();
                    }


                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");


                    // select Price Hint Filter


                    Platform.runLater(() -> {
                        checkSalesPrice.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Price Hint...");
                    });

                    xpathPattern = Homepage.getProperty("page.filter.salesprice");
                    try{
                        //check sales price
                        isSuccessful = FilterButtonCheck.pressFilterButton(webDriver, js, xpathPattern);
                        if (isSuccessful) {
                            Platform.runLater(() -> {
                                checkSalesPrice.setStyle("-fx-background-color: #CCFF99");
                                checkSalesPrice.setSelected(true);
                            });
                            report.writeToFile("Checking Price Hint: ", "Successful!");
                        } else {
                            Platform.runLater(() -> {
                                checkSalesPrice.setStyle("-fx-background-color: #FF0000");
                                checkSalesPrice.setSelected(true);
                            });
                            report.writeToFile("Checking Price Hint: ", "unable to check!");
                        }
                        // make SCREENSHOT
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"screenshot5.png");
                        if (isSuccessful){
                            report.writeToFile("Checking Layout Salesprice: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("Checking Layout Salesprice: ", "Screenshot not successful!");
                        }
                    }catch (Exception noSalesFilter){
                        Platform.runLater(() -> {
                            checkSalesPrice.setStyle("-fx-background-color: #FF0000");
                            checkSalesPrice.setSelected(true);
                        });
                        report.writeToFile("Checking Price Hint: ", "unable to check! Browser not responding");
                        noSalesFilter.printStackTrace();
                    }


                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");



                    // select  Filters
                    Platform.runLater(() -> {
                        checkFilter.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Filters...");
                    });


                    //  check color filter
                    Platform.runLater(() -> statusInfo.setText("Checking Color..."));
                    xpathPattern = Homepage.getProperty("page.filter.color");
                    try {
                        isSuccessful = FilterButtonCheck.pressFilterButton(webDriver, js, xpathPattern);
                        if (isSuccessful) {
                            report.writeToFile("Checking Filter Color: ", "Successful!");
                        } else {
                            report.writeToFile("Checking Filter Color: ", "unable to check!");
                            report.writeToFile("Checking via JavaScript: ", "\b");
                            isSuccessful = FilterButtonCheckViaJavaScript.pressFilterButton(webDriver, js, xpathPattern);

                            if (isSuccessful){
                                report.writeToFile("Checking Filter Color via JS: ", "Successful!");
                            }else{
                                report.writeToFile("Checking Filter Color via JS: ", "unable to check!");
                            }
                        }
                        // make SCREENSHOT
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"screenshot6.png");
                        if (isSuccessful){
                            report.writeToFile("Checking Layout Color: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("Checking Layout Color: ", "Screenshot not successful!");
                        }
                    }catch (Exception noColorFilter){
                        report.writeToFile("Checking Filter Color: ", "unable to check! Browser not responding");
                        noColorFilter.printStackTrace();
                    }



                    // check brand filter
                    Platform.runLater(() -> statusInfo.setText("Checking Brand..."));
                    xpathPattern = Homepage.getProperty("page.filter.brand");
                    try {
                        isSuccessful = FilterButtonCheck.pressFilterButton(webDriver, js, xpathPattern);
                        if (isSuccessful) {
                            report.writeToFile("Checking Filter Brand: ", "Successful!");
                        } else {
                            report.writeToFile("Checking Filter Brand: ", "unable to check!");
                            report.writeToFile("Checking via JavaScript: ", "\b");
                            isSuccessful = FilterButtonCheckViaJavaScript.pressFilterButton(webDriver, js, xpathPattern);
                            if (isSuccessful){
                                report.writeToFile("Checking Filter Brand via JS: ", "Successful!");
                            }else{
                                report.writeToFile("Checking Filter Brand via JS: ", "unable to check!");
                            }
                        }

                        // make SCREENSHOT
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"screenshot7.png");
                        if (isSuccessful){
                            report.writeToFile("Checking Layout Brand: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("Checking Layout Brand: ", "Screenshot not successful!");
                        }
                    }catch (Exception noBrandFilter){
                        report.writeToFile("Checking Filter Brand: ", "unable to check! Browser not responding");
                        noBrandFilter.printStackTrace();
                    }



                    // check material filter
                    Platform.runLater(() -> statusInfo.setText("Checking Material..."));
                    xpathPattern = Homepage.getProperty("page.filter.material");
                    try{
                        isSuccessful = FilterButtonCheck.pressFilterButton(webDriver, js, xpathPattern);
                        if (isSuccessful) {
                            report.writeToFile("Checking Filter Material: ", "Successful!");
                        } else {
                            report.writeToFile("Checking Filter Material: ", "unable to check!");
                            report.writeToFile("Checking via JavaScript: ", "\b");
                            isSuccessful = FilterButtonCheckViaJavaScript.pressFilterButton(webDriver, js, xpathPattern);
                            if (isSuccessful){
                                report.writeToFile("Checking Filter Material via JS: ", "Successful!");
                            }else{
                                report.writeToFile("Checking Filter Material via JS: ", "unable to check!");
                            }
                        }
                    }catch (Exception noMaterialFilter){
                        report.writeToFile("Checking Filter Material via JS: ", "unable to check! Browser not responding");
                    }


                    // check shop filter
                    Platform.runLater(() -> statusInfo.setText("Checking Shop..."));
                    xpathPattern = Homepage.getProperty("page.filter.shop");
                    try {
                        isSuccessful = FilterButtonCheck.pressFilterButton(webDriver, js, xpathPattern);
                        if (isSuccessful) {
                            Platform.runLater(() -> {
                                checkFilter.setStyle("-fx-background-color: #CCFF99");
                                checkFilter.setSelected(true);
                            });
                            report.writeToFile("Checking Filter Shop: ", "Successful!");
                        } else {
                            report.writeToFile("Checking Filter Shop: ", "unable to check!");
                            report.writeToFile("Checking via JavaScript: ", "\b");
                            isSuccessful = FilterButtonCheckViaJavaScript.pressFilterButton(webDriver, js, xpathPattern);
                            if (isSuccessful){
                                report.writeToFile("Checking Filter Shop via JS: ", "Successful!");
                                Platform.runLater(() -> {
                                    checkFilter.setStyle("-fx-background-color: #CCFF99");
                                    checkFilter.setSelected(true);
                                });
                            }else{
                                report.writeToFile("Checking Filter Shop via JS: ", "unable to check!");
                                Platform.runLater(() -> {
                                    checkFilter.setStyle("-fx-background-color: #FF0000");
                                    checkFilter.setSelected(true);
                                });
                            }
                        }
                    }catch (Exception noShopFilter){
                        report.writeToFile("Checking Filter: ", "unable to check! Browser not responding");
                        noShopFilter.printStackTrace();
                    }



                    // close webdriver and clear tasklist
                    Platform.runLater(() -> statusInfo.setText("Closing Browser..."));
                    try {
                        webDriver.close();
                    }catch (Exception driverClosing){
                        driverClosing.printStackTrace();
                    }
                    try {
                        webDriver.quit();
                    }catch (Exception driverQuit){
                        driverQuit.printStackTrace();
                    }
                    // not used now but got to know
                    // tabPane.getSelectionModel().select(brandTab);

                    try {
                        Runtime.getRuntime().exec("TASKKILL /F /IM chromedriver.exe");
                        Runtime.getRuntime().exec("taskkill /im chromedriver.exe /f");

                    } catch (IOException io) {
                        io.printStackTrace();
                    }

                }


            };

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();

            task.setOnSucceeded(e -> Platform.runLater(() -> {
                Hyperlink link = new Hyperlink("open Report");
                link.setOnAction(event -> {
                    ReportWindow window = new ReportWindow();
                    try {
                        window.MyReportWindow();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
                preloaderCat.setImage(null);
                link.setStyle("-fx-font: 24 arial;");
                link.setTextAlignment(TextAlignment.CENTER);
                outputPlace.getChildren().addAll(link);
                progressIndicator.setProgress(100);
                changeButtonText();
                System.out.println("Process Finished");

            }));


        });

    }


    @FXML
    private void changeButtonText() {
        statusInfo.setText("Running complete");
        startwebdriver.setDisable(false);
        inputPerfectMatch.setDisable(false);
        inputSearch.setDisable(false);
        inputEmailAdress.setDisable(false);
    }

    @FXML
    private void copyFiles() {
        CopyFiles bringit = new CopyFiles();
        bringit.copyFileThere();

    }

    @FXML
    private double checkAllCheckBoxes() {
        double StateProgress = 0;

        CheckBox[] checkboxes = CheckBoxesPlace.getChildren().toArray(new CheckBox[0]);
        int AmountOfCheckBoxes = checkboxes.length;

        for (CheckBox checkbox : checkboxes) {

            if (checkbox.isSelected()) {
                StateProgress += 1;

            }
        }
        //System.out.println("State : " + StateProgress + "Amount of Boxes: "+AmountOfCheckBoxes);
        StateProgress = StateProgress / AmountOfCheckBoxes;
        return StateProgress;
    }

    @FXML
    private void resetAllFormOptions() {
        Platform.runLater(() -> {
            checkCategoryLinksLeftSideMenu.setStyle("-fx-background-color: #FFFFFF");
            checkLogoFromShopOfTheWeek.setStyle("-fx-background-color: #FFFFFF");
            checkCategoryLinksFromShopOfTheWeek.setStyle("-fx-background-color: #FFFFFF");
            checkNewsletterBannerFunctionality.setStyle("-fx-background-color: #FFFFFF");
            checkLogoHomepage.setStyle("-fx-background-color: #FFFFFF");
            checkGeneralLayout.setStyle("-fx-background-color: #FFFFFF");
            openMainMenu.setStyle("-fx-background-color: #FFFFFF");
            checkBannersLayout.setStyle("-fx-background-color: #FFFFFF");
            checkShopOfTheWeek.setStyle("-fx-background-color: #FFFFFF");
            checkPerfectMatch.setStyle("-fx-background-color: #FFFFFF");
            checkSalesPrice.setStyle("-fx-background-color: #FFFFFF");
            checkFilter.setStyle("-fx-background-color: #FFFFFF");
            checkCategoryLinksLeftSideMenu.setSelected(false);
            checkLogoFromShopOfTheWeek.setSelected(false);
            checkCategoryLinksFromShopOfTheWeek.setSelected(false);
            checkNewsletterBannerFunctionality.setSelected(false);
            checkLogoHomepage.setSelected(false);
            checkGeneralLayout.setSelected(false);
            openMainMenu.setSelected(false);
            checkBannersLayout.setSelected(false);
            checkShopOfTheWeek.setSelected(false);
            checkPerfectMatch.setSelected(false);
            checkSalesPrice.setSelected(false);
            checkFilter.setSelected(false);
            outputPlace.getChildren().clear();
        });
    }


}
