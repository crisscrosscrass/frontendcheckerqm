package crisscrosscrass.Controller;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.*;
import crisscrosscrass.Tasks.*;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    JFXCheckBox checkNewsletterPopUpFunctionality;
    @FXML
    JFXCheckBox checkFooterLinks;
    @FXML
    JFXCheckBox checkTextSearchAndSuggestions;
    @FXML
    JFXCheckBox checkFeedbackPopUp;
    @FXML
    JFXCheckBox checkPrivacyPopUp;
    /**
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
     TextField inputPerfectMatch;
     */
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
    private static String xpathPattern1 = "";
    private static String xpathPattern2 = "";
    private static String xpathPatternImage1 = "";
    private static String xpathPatternImage2 = "";


    @FXML
    public void initialize() {
        System.out.println("FrontendCheckController launched!");
        //homePageController.init(this);
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
                protected Void call() throws InterruptedException {
                    resetAllFormOptions();
                    startEngine();
                    return null;
                }

                private void startEngine() throws InterruptedException {

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
                    HomepageTest homepageTest = new HomepageTest();
                    homepageTest.checkingCategories(webDriver,report,checkCategoryLinksLeftSideMenu,statusInfo,inputSearch,xpathPattern1,Homepage,isSuccessful,isAvailable);
                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    homepageTest.checkingShopOfTheWeek(webDriver,report,checkLogoFromShopOfTheWeek,statusInfo,inputSearch,xpathPattern1,xpathPatternImage1,xpathPatternImage2,Homepage,isSuccessful,isAvailable);
                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    homepageTest.checkingShopOfTheWeekCategories(webDriver,report,checkCategoryLinksFromShopOfTheWeek,statusInfo,inputSearch,xpathPattern1,xpathPatternImage1,xpathPatternImage2,Homepage,isSuccessful,isAvailable);
                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));



                    // Newsletter Banner Functionality
                    Platform.runLater(() -> {
                        checkNewsletterBannerFunctionality.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Newsletter Banner Functionality...");
                    });
                    xpathPattern1 = Homepage.getProperty("page.main.newsletter.input");
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
                            isAvailable = webDriver.findElementByXPath("//*[@id=\"confirmation-wrapper\"]") != null;
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
                                isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"NewsLetterBannerSignInPage.png");
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

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");




                    // Newsletter PopUp
                    Platform.runLater(() -> {
                        checkNewsletterPopUp.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Newsletter PopUp...");
                    });
                    xpathPattern1 = Homepage.getProperty("page.main.newsletter.icon");
                    try {
                        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(0));
                        try {
                            isAvailable = webDriver.findElementByXPath(xpathPattern1) != null;
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
                                isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver,"NewsLetterPopUpError.png");
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

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");





                    // Newsletter PopUp Functionality
                    Platform.runLater(() -> {
                        checkNewsletterPopUpFunctionality.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Newsletter PopUp Functionality...");
                    });
                    xpathPattern1 = Homepage.getProperty("page.main.newsletter.icon");
                    try {
                        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(0));
                        try {
                            isAvailable = webDriver.findElementByXPath(xpathPattern1) != null;
                            webDriver.findElementByXPath(xpathPattern1).click();
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

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");





                    // Footer Links
                    Platform.runLater(() -> {
                        checkFooterLinks.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Footer Links & Categories...");
                    });
                    xpathPattern1 = Homepage.getProperty("page.main.footer.links");
                    xpathPattern2 = Homepage.getProperty("page.main.footer.categories");
                    try {
                        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(0));
                        try {
                            isAvailable = webDriver.findElementByXPath(xpathPattern1) != null;
                            Point hoverItem = webDriver.findElement(By.xpath(xpathPattern1)).getLocation();
                            ((JavascriptExecutor)webDriver).executeScript("return window.title;");
                            ((JavascriptExecutor)webDriver).executeScript("window.scrollBy(0,"+(hoverItem.getY())+");");
                            WebdriverTab newtab = new WebdriverTab();
                            //check all footerLinks
                            List<WebElement> footerFooterLinks = webDriver.findElementsByXPath(xpathPattern1);
                            //TODO set footerFooterLinks.size()
                            for (int i = 0 ; i < 1 ; i++){
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
                            //TODO set footerCategoryLinks.size()
                            for (int i = 0 ; i < 1 ; i++){
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

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");






                    // Text Search & Suggestions in InputSearch
                    Platform.runLater(() -> {
                        checkTextSearchAndSuggestions.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Text Search and Suggestions in InputSearch...");
                    });
                    xpathPattern1 = "//*[@id=\"header-search-input\"]";
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
                                        List<WebElement> searchAliasesTitles = webDriver.findElementsByXPath("//*[contains(@class, 'main-search-suggestions')]/li/a/div/*[contains(@class, 'srTitle')]");
                                        List<WebElement> searchAliasesType = webDriver.findElementsByXPath("//*[contains(@class, 'main-search-suggestions')]/li/a/div/*[contains(@class, 'srType')]");
                                        report.writeToFile("Checking Text Search and Suggestions for \""+ searchAliases[i].trim()+"\"");
                                        report.writeToFile("Lucene Results :");
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

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");



                    // Feedback PopUp
                    Platform.runLater(() -> {
                        checkFeedbackPopUp.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Feedback PopUp...");
                    });
                    xpathPattern1 = Homepage.getProperty("page.main.feedback.icon");
                    xpathPattern2 = Homepage.getProperty("page.main.feedback.close");
                    try {
                        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(0));
                        WebDriverWait wait = new WebDriverWait(webDriver, 10);
                        try{
                            isAvailable = webDriver.findElementByXPath(xpathPattern1) != null;
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
                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");





                    // Privacy PopUp
                    Platform.runLater(() -> {
                        checkPrivacyPopUp.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Privacy PopUp...");
                    });
                    xpathPattern1 = Homepage.getProperty("page.main.privacy.popup");
                    try {
                        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(0));
                        webDriver.navigate().to(inputSearch.getText().trim());
                        WebDriverWait wait = new WebDriverWait(webDriver, 10);
                        try{
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPattern1)));
                            isAvailable = webDriver.findElementByXPath(xpathPattern1) != null;
                            webDriver.findElementByXPath(xpathPattern1).click();
                            System.out.println(webDriver.getCurrentUrl());
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
                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");


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
            /**
            checkLogoHomepage.setStyle("-fx-background-color: #FFFFFF");
            checkGeneralLayout.setStyle("-fx-background-color: #FFFFFF");
            openMainMenu.setStyle("-fx-background-color: #FFFFFF");
            checkBannersLayout.setStyle("-fx-background-color: #FFFFFF");
            checkShopOfTheWeek.setStyle("-fx-background-color: #FFFFFF");
            checkPerfectMatch.setStyle("-fx-background-color: #FFFFFF");
            checkSalesPrice.setStyle("-fx-background-color: #FFFFFF");
            checkFilter.setStyle("-fx-background-color: #FFFFFF");
             **/

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
            /**
            checkLogoHomepage.setSelected(false);
            checkGeneralLayout.setSelected(false);
            openMainMenu.setSelected(false);
            checkBannersLayout.setSelected(false);
            checkShopOfTheWeek.setSelected(false);
            checkPerfectMatch.setSelected(false);
            checkSalesPrice.setSelected(false);
            checkFilter.setSelected(false);
             */
            outputPlace.getChildren().clear();
        });
    }


}
