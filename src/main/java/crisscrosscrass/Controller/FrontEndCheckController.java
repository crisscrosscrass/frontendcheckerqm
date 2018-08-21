package crisscrosscrass.Controller;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.*;
import crisscrosscrass.Tasks.*;
import crisscrosscrass.Tests.GridPageTest;
import crisscrosscrass.Tests.HomepageTest;
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

import java.io.*;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FrontEndCheckController {


    // Basic Settings
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
    JFXCheckBox checkNewsletterPopUpFunctionality;
    @FXML
    JFXCheckBox checkFooterLinks;
    @FXML
    JFXCheckBox checkTextSearchAndSuggestions;
    @FXML
    JFXCheckBox checkFeedbackPopUp;
    @FXML
    JFXCheckBox checkPrivacyPopUp;

    // Tab Settings
    @FXML
    JFXCheckBox settingHomepage;
    @FXML
    JFXCheckBox settingGridPage;
    @FXML
    JFXCheckBox settingGridPageWithWindows;
    @FXML
    JFXCheckBox settingGridPageFillIns;
    @FXML
    JFXCheckBox settingBrandPage;
    @FXML
    JFXCheckBox settingLucenePage;
    @FXML
    JFXCheckBox settingLucenePageWithDeletions;
    @FXML
    JFXCheckBox settingDetailPage;
    @FXML
    JFXCheckBox settingImageGrouping;

    //Main Menu Settings
    @FXML
    ProgressBar progressIndicator;
    @FXML
    Text statusInfo;
    @FXML
    TextField inputSearch;
    @FXML
    TextField inputEmailAdress;
    @FXML
    TextField inputTextSearchAndSuggestions;
    @FXML
    TextField inputGridPageURL;
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

    //tab Settings
    @FXML
    Tab tabHomepage;
    @FXML
    Tab tabGridPage;
    @FXML
    Tab tabGridPageWithWindows;
    @FXML
    Tab tabGridPageFillIns;
    @FXML
    Tab tabBrandPage;
    @FXML
    Tab tabLucenePage;
    @FXML
    Tab tabLucenePageWithDeletions;
    @FXML
    Tab tabDetailPage;
    @FXML
    Tab tabImageGrouping;

    //Controller Settings
    @FXML GridPageWithWindowsController gridPageWithWindowsController;
    @FXML GridPageNoWindowsController gridPageNoWindowsController;



    private static boolean isSuccessful = false;
    private static boolean isAvailable = false;
    private static String xpathPattern1 = "";
    private static String xpathPattern2 = "";
    private static String xpathPatternImage1 = "";
    private static String xpathPatternImage2 = "";


    @FXML
    public void initialize() {
        System.out.println("FrontendCheckController launched!");

        //gridPageCheckController.init(this);


        settingHomepage.setOnAction(event -> {
            updateCheckerTabs();
        });
        settingGridPage.setOnAction(event -> {
            updateCheckerTabs();
        });

        settingGridPageWithWindows.setOnAction(event -> {
            updateCheckerTabs();
        });

        settingGridPageFillIns.setOnAction(event -> {
            updateCheckerTabs();
        });

        settingBrandPage.setOnAction(event -> {
            updateCheckerTabs();
        });

        settingLucenePage.setOnAction(event -> {
            updateCheckerTabs();
        });

        settingLucenePageWithDeletions.setOnAction(event -> {
            updateCheckerTabs();
        });

        settingDetailPage.setOnAction(event -> {
            updateCheckerTabs();
        });

        settingImageGrouping.setOnAction(event -> {
            updateCheckerTabs();
        });



        updateCheckerTabs();
    }

    public void updateCheckerTabs(){
        if (settingHomepage.isSelected())
            tabHomepage.setDisable(false);
        else tabHomepage.setDisable(true);
        if (settingGridPage.isSelected())
            tabGridPage.setDisable(false);
        else tabGridPage.setDisable(true);
        if (settingGridPageWithWindows.isSelected())
            tabGridPageWithWindows.setDisable(false);
        else tabGridPageWithWindows.setDisable(true);
        if (settingGridPageFillIns.isSelected())
            tabGridPageFillIns.setDisable(false);
        else tabGridPageFillIns.setDisable(true);
        if (settingBrandPage.isSelected())
            tabBrandPage.setDisable(false);
        else tabBrandPage.setDisable(true);
        if (settingLucenePage.isSelected())
            tabLucenePage.setDisable(false);
        else tabLucenePage.setDisable(true);
        if (settingLucenePageWithDeletions.isSelected())
            tabLucenePageWithDeletions.setDisable(false);
        else tabLucenePageWithDeletions.setDisable(true);
        if (settingDetailPage.isSelected())
            tabDetailPage.setDisable(false);
        else tabDetailPage.setDisable(true);
        if (settingImageGrouping.isSelected())
            tabImageGrouping.setDisable(false);
        else tabImageGrouping.setDisable(true);
    }

    @FXML
    public void startRealAction() {

        System.out.println("Start Engine...");


        Platform.runLater(() -> {


            progressIndicator.setProgress(-1);
            startwebdriver.setDisable(true);
            inputSearch.setDisable(true);
            inputEmailAdress.setDisable(true);
            inputTextSearchAndSuggestions.setDisable(true);
            Task task = new Task<Object>() {
                @Override
                protected Void call() {
                    resetAllFormOptions();
                    startMainCheck();
                    return null;
                }

                private void startMainCheck(){

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
                    if (!tabHomepage.isDisable()){
                        tabPane.getSelectionModel().select(tabHomepage);
                        HomepageTest homepageTest = new HomepageTest();
                        homepageTest.checkingCategories(webDriver,report,checkCategoryLinksLeftSideMenu,statusInfo,inputSearch,xpathPattern1,Homepage,isSuccessful,isAvailable);
                        homepageTest.checkingShopOfTheWeek(webDriver,report,checkLogoFromShopOfTheWeek,statusInfo,inputSearch,xpathPattern1,xpathPatternImage1,xpathPatternImage2,Homepage,isSuccessful,isAvailable);
                        homepageTest.checkingShopOfTheWeekCategories(webDriver,report,checkCategoryLinksFromShopOfTheWeek,statusInfo,inputSearch,xpathPattern1,xpathPatternImage1,xpathPatternImage2,Homepage,isSuccessful,isAvailable);
                        homepageTest.checkingNewsletterBanner(webDriver,report,checkNewsletterBannerFunctionality,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPatternImage1,xpathPatternImage2,Homepage,isSuccessful,isAvailable);
                        homepageTest.checkingNewsletterPopUp(webDriver,report,checkNewsletterPopUp,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPatternImage1,xpathPatternImage2,Homepage,isSuccessful,isAvailable);
                        homepageTest.checkingNewsletterPopUpFunctionality(webDriver,report,js,checkNewsletterPopUpFunctionality,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPatternImage1,xpathPatternImage2,Homepage,isSuccessful,isAvailable);
                        homepageTest.checkingFooterLinks(webDriver,report,js,checkFooterLinks,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                        homepageTest.checkingSearchAndSuggestions(webDriver,report,js,checkTextSearchAndSuggestions,inputTextSearchAndSuggestions,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                        homepageTest.checkingFeedbackPopUp(webDriver,report,js,checkFeedbackPopUp,inputTextSearchAndSuggestions,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                        homepageTest.checkingPrivacyPopUp(webDriver,report,js,checkPrivacyPopUp,inputTextSearchAndSuggestions,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                        Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    }


                    if (!tabGridPage.isDisable()){
                        tabPane.getSelectionModel().select(tabGridPage);
                        GridPageTest gridPageTest = new GridPageTest();
                        //gridPageTest.checkingSorting(webDriver,report,js,gridPageNoWindowsController.sortingValues,inputGridPageURL,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                        //gridPageTest.checkingSwitchFromSmallToLargeImages(webDriver,report,js,gridPageNoWindowsController.switchFromSmallToLarge,inputGridPageURL,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                        //gridPageTest.checkingPagingForwardBackward(webDriver,report,js,gridPageNoWindowsController.pagingForwardBackward,inputGridPageURL,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                        //gridPageTest.checkingProductView300(webDriver,report,js,gridPageNoWindowsController.productView300,inputGridPageURL,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                        //gridPageTest.checkingDeeperStyle(webDriver,report,js,gridPageNoWindowsController.deeperStyle,inputGridPageURL,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                        //gridPageTest.checkingStyleBoxOpenClose(webDriver,report,js,gridPageNoWindowsController.styleBoxOpenClose,inputGridPageURL,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                        gridPageTest.checkingFilterApply(webDriver,report,js,gridPageNoWindowsController.filtersApply,inputGridPageURL,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                    }


                    /**

                    // Click on Logo Test
                    Platform.runLater(() -> {
                        checkLogoHomepage.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Logo...");
                    });
                    xpathPattern1 = Homepage.getProperty("page.main.logo");

                    try {
                        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(0));
                        webDriver.findElementByXPath(xpathPattern1).click();

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

                        //TODO remove this again
                        int counterSubMenu = 1;
                        for (WebElement ItemSubMenu : MainSubMenu) {
                            //System.out.println("MainSubLink: "+ ItemSubMenu.getAttribute("textContent") + " " + ItemSubMenu.getAttribute("href"));
                            Platform.runLater(() -> statusInfo.setText(ItemSubMenu.getAttribute("href")));
                            report.writeToFile("MainMenuSubLink: "+ counterSubMenu + " " + ItemSubMenu.getAttribute("textContent"), ItemSubMenu.getAttribute("href"));
                            counterSubMenu++;
                        }


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

                    xpathPattern1 = Homepage.getProperty("page.filter.salesprice");
                    try{
                        //check sales price
                        isSuccessful = FilterButtonCheck.pressFilterButton(webDriver, js, xpathPattern1);
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
                    xpathPattern1 = Homepage.getProperty("page.filter.color");
                    try {
                        isSuccessful = FilterButtonCheck.pressFilterButton(webDriver, js, xpathPattern1);
                        if (isSuccessful) {
                            report.writeToFile("Checking Filter Color: ", "Successful!");
                        } else {
                            report.writeToFile("Checking Filter Color: ", "unable to check!");
                            report.writeToFile("Checking via JavaScript: ", "\b");
                            isSuccessful = FilterButtonCheckViaJavaScript.pressFilterButton(webDriver, js, xpathPattern1);

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
                    xpathPattern1 = Homepage.getProperty("page.filter.brand");
                    try {
                        isSuccessful = FilterButtonCheck.pressFilterButton(webDriver, js, xpathPattern1);
                        if (isSuccessful) {
                            report.writeToFile("Checking Filter Brand: ", "Successful!");
                        } else {
                            report.writeToFile("Checking Filter Brand: ", "unable to check!");
                            report.writeToFile("Checking via JavaScript: ", "\b");
                            isSuccessful = FilterButtonCheckViaJavaScript.pressFilterButton(webDriver, js, xpathPattern1);
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
                    xpathPattern1 = Homepage.getProperty("page.filter.material");
                    try{
                        isSuccessful = FilterButtonCheck.pressFilterButton(webDriver, js, xpathPattern1);
                        if (isSuccessful) {
                            report.writeToFile("Checking Filter Material: ", "Successful!");
                        } else {
                            report.writeToFile("Checking Filter Material: ", "unable to check!");
                            report.writeToFile("Checking via JavaScript: ", "\b");
                            isSuccessful = FilterButtonCheckViaJavaScript.pressFilterButton(webDriver, js, xpathPattern1);
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
                    xpathPattern1 = Homepage.getProperty("page.filter.shop");
                    try {
                        isSuccessful = FilterButtonCheck.pressFilterButton(webDriver, js, xpathPattern1);
                        if (isSuccessful) {
                            Platform.runLater(() -> {
                                checkFilter.setStyle("-fx-background-color: #CCFF99");
                                checkFilter.setSelected(true);
                            });
                            report.writeToFile("Checking Filter Shop: ", "Successful!");
                        } else {
                            report.writeToFile("Checking Filter Shop: ", "unable to check!");
                            report.writeToFile("Checking via JavaScript: ", "\b");
                            isSuccessful = FilterButtonCheckViaJavaScript.pressFilterButton(webDriver, js, xpathPattern1);
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

                     */
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
        inputSearch.setDisable(false);
        inputEmailAdress.setDisable(false);
        inputTextSearchAndSuggestions.setDisable(false);
    }

    @FXML
    private void copyFiles() {
        CopyFiles bringit = new CopyFiles();
        bringit.copyFileThere();

    }

    @FXML
    private double checkAllCheckBoxes() {
        double StateProgress = 0;

        JFXCheckBox[] checkboxes = CheckBoxesPlace.getChildren().toArray(new JFXCheckBox[0]);
        int AmountOfCheckBoxes = checkboxes.length;

        for (JFXCheckBox checkbox : checkboxes) {

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
            checkNewsletterPopUp.setStyle("-fx-background-color: #FFFFFF");
            checkNewsletterPopUpFunctionality.setStyle("-fx-background-color: #FFFFFF");
            checkFooterLinks.setStyle("-fx-background-color: #FFFFFF");
            checkTextSearchAndSuggestions.setStyle("-fx-background-color: #FFFFFF");
            checkFeedbackPopUp.setStyle("-fx-background-color: #FFFFFF");
            checkPrivacyPopUp.setStyle("-fx-background-color: #FFFFFF");

            checkCategoryLinksLeftSideMenu.setSelected(false);
            checkLogoFromShopOfTheWeek.setSelected(false);
            checkCategoryLinksFromShopOfTheWeek.setSelected(false);
            checkNewsletterBannerFunctionality.setSelected(false);
            checkNewsletterPopUp.setSelected(false);
            checkNewsletterPopUpFunctionality.setSelected(false);
            checkFooterLinks.setSelected(false);
            checkTextSearchAndSuggestions.setSelected(false);
            checkFeedbackPopUp.setSelected(false);
            checkPrivacyPopUp.setSelected(false);
            outputPlace.getChildren().clear();
        });
    }


}
