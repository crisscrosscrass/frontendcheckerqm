package crisscrosscrass.Controller;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.*;
import crisscrosscrass.Tasks.*;
import crisscrosscrass.Tests.*;
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

    //Filter Settings
    @FXML
    public JFXCheckBox checkingSalesPriceFilter;

    @FXML
    public JFXCheckBox checkingGenderFilter;

    @FXML
    public JFXCheckBox checkingColorFilter;

    @FXML
    public JFXCheckBox checkingBrandFilter;

    @FXML
    public JFXCheckBox checkingMerchandiseFilter;

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
    TextField inputGridPageKeyword;
    @FXML
    TextField inputGridPageURLWithWindows;
    @FXML
    TextField inputGridPageURLWithFillIns;
    @FXML
    TextField inputBrandPageOverview;
    @FXML
    TextField inputLucenePage;
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
    @FXML GridPageWithFillInsController gridPageWithFillInsController;
    @FXML BrandOverviewController brandOverviewController;
    @FXML PageLuceneWithItemsController pageLuceneWithItemsController;
    @FXML DetailPageController detailPageController;
    @FXML ImageGroupingController imageGroupingController;



    private static boolean isSuccessful = false;
    private static boolean isAvailable = false;
    private static String xpathPattern1 = "";
    private static String xpathPattern2 = "";
    private static String xpathPatternImage1 = "";
    private static String xpathPatternImage2 = "";


    @FXML
    public void initialize() {
        System.out.println("FrontendCheckController launched!");
        settingHomepage.setOnAction(event -> updateCheckerTabs());
        settingGridPage.setOnAction(event -> updateCheckerTabs());
        settingGridPageWithWindows.setOnAction(event -> updateCheckerTabs());
        settingGridPageFillIns.setOnAction(event -> updateCheckerTabs());
        settingBrandPage.setOnAction(event -> updateCheckerTabs());
        settingLucenePage.setOnAction(event -> updateCheckerTabs());
        settingLucenePageWithDeletions.setOnAction(event -> updateCheckerTabs());
        settingDetailPage.setOnAction(event -> updateCheckerTabs());
        settingImageGrouping.setOnAction(event -> updateCheckerTabs());

        updateCheckerTabs();
    }

    public void updateCheckerTabs(){
        if (settingHomepage.isSelected()){
            tabHomepage.setDisable(false);
            tabPane.getSelectionModel().select(tabHomepage);
        }else{
            tabHomepage.setDisable(true);
        }
        if (settingGridPage.isSelected()){
            tabGridPage.setDisable(false);
            tabPane.getSelectionModel().select(tabGridPage);
        }else{
            tabGridPage.setDisable(true);
        }
        if (settingGridPageWithWindows.isSelected()){
            tabGridPageWithWindows.setDisable(false);
            tabPane.getSelectionModel().select(tabGridPageWithWindows);
        }else{
            tabGridPageWithWindows.setDisable(true);
        }
        if (settingGridPageFillIns.isSelected()){
            tabGridPageFillIns.setDisable(false);
            tabPane.getSelectionModel().select(tabGridPageFillIns);
        }else{
            tabGridPageFillIns.setDisable(true);
        }
        if (settingBrandPage.isSelected()){
            tabBrandPage.setDisable(false);
            tabPane.getSelectionModel().select(tabBrandPage);
        }else{
            tabBrandPage.setDisable(true);
        }
        if (settingLucenePage.isSelected()){
            tabLucenePage.setDisable(false);
            tabPane.getSelectionModel().select(tabLucenePage);
        }else{
            tabLucenePage.setDisable(true);
        }
        if (settingLucenePageWithDeletions.isSelected()){
            tabLucenePageWithDeletions.setDisable(false);
            tabPane.getSelectionModel().select(tabLucenePageWithDeletions);
        }else{
            tabLucenePageWithDeletions.setDisable(true);
        }
        if (settingDetailPage.isSelected()) {
            tabDetailPage.setDisable(false);
            tabPane.getSelectionModel().select(tabDetailPage);
        }
        else {
            tabDetailPage.setDisable(true);
        }
        if (settingImageGrouping.isSelected()){
            tabImageGrouping.setDisable(false);
            tabPane.getSelectionModel().select(tabImageGrouping);
        }
        else{
            tabImageGrouping.setDisable(true);
        }
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


                    try{
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
                        report.writeToFile("=================================", "");
                        if (!tabHomepage.isDisable()){
                            tabPane.getSelectionModel().select(tabHomepage);
                            HomepageTest homepageTest = new HomepageTest();
                            homepageTest.checkingCategories(webDriver,report,checkCategoryLinksLeftSideMenu,statusInfo,inputSearch, Homepage);
                            homepageTest.checkingShopOfTheWeek(webDriver,report,checkLogoFromShopOfTheWeek,statusInfo,inputSearch, Homepage);
                            homepageTest.checkingShopOfTheWeekCategories(webDriver,report,checkCategoryLinksFromShopOfTheWeek,statusInfo,inputSearch, Homepage);
                            homepageTest.checkingNewsletterBanner(webDriver,report,checkNewsletterBannerFunctionality,statusInfo,inputSearch,inputEmailAdress, Homepage);
                            homepageTest.checkingNewsletterPopUp(webDriver,report,checkNewsletterPopUp,statusInfo,inputSearch, Homepage);
                            homepageTest.checkingNewsletterPopUpFunctionality(webDriver,report,js,checkNewsletterPopUpFunctionality,statusInfo,inputSearch,inputEmailAdress, Homepage);
                            homepageTest.checkingFooterLinks(webDriver,report, checkFooterLinks,statusInfo,inputSearch, Homepage);
                            homepageTest.checkingSearchAndSuggestions(webDriver,report, checkTextSearchAndSuggestions,inputTextSearchAndSuggestions,statusInfo,inputSearch, Homepage);
                            homepageTest.checkingFeedbackPopUp(webDriver,report, checkFeedbackPopUp, statusInfo,inputSearch, Homepage);
                            homepageTest.checkingPrivacyPopUp(webDriver,report, checkPrivacyPopUp, statusInfo,inputSearch, Homepage);
                        }
                        if (!tabGridPage.isDisable()){
                            tabPane.getSelectionModel().select(tabGridPage);
                            GridPageTest gridPageTest = new GridPageTest();
                            gridPageTest.checkingSorting(webDriver,report,js,gridPageNoWindowsController.sortingValues,inputGridPageURL,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                            //TODO investigate why SmallToLargeImages test failed sometimes...? *Added loader Box, keep an eye of whats going on
                            gridPageTest.checkingSwitchFromSmallToLargeImages(webDriver,report,js,gridPageNoWindowsController.switchFromSmallToLarge,inputGridPageURL,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                            gridPageTest.checkingPagingForwardBackward(webDriver,report,js,gridPageNoWindowsController.pagingForwardBackward,inputGridPageURL,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                            gridPageTest.checkingProductView300(webDriver,report,js,gridPageNoWindowsController.productView300,inputGridPageURL,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                            gridPageTest.checkingDeeperStyle(webDriver,report,js,gridPageNoWindowsController.deeperStyle,inputGridPageURL,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                            gridPageTest.checkingStyleBoxOpenClose(webDriver,report,js,gridPageNoWindowsController.styleBoxOpenClose,inputGridPageURL,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                            gridPageTest.checkingFilterApply(webDriver,report,js,gridPageNoWindowsController.filtersApply,inputGridPageURL,statusInfo,inputSearch,Homepage,isSuccessful,isAvailable,checkingSalesPriceFilter,checkingGenderFilter,checkingColorFilter,checkingBrandFilter,checkingMerchandiseFilter);
                            gridPageTest.checkingSearchBoxInBrandFilter(webDriver,report,js,gridPageNoWindowsController.searchBoxInBrandFilter,inputGridPageURL,inputGridPageKeyword,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                            gridPageTest.checkingSearchBoxInShopFilter(webDriver,report,js,gridPageNoWindowsController.searchBoxInShopFilter,inputGridPageURL,inputGridPageKeyword,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                        }
                        if (!tabGridPageWithWindows.isDisable()){
                            tabPane.getSelectionModel().select(tabGridPageWithWindows);
                            GridPageTestWithWindows gridPageTestWithWindows = new GridPageTestWithWindows();
                            gridPageTestWithWindows.pagingWithWindowsForward(webDriver,report,js,gridPageWithWindowsController.PagingWithWindowsForward,inputGridPageURLWithWindows,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                        }

                        if (!tabGridPageFillIns.isDisable()){
                            tabPane.getSelectionModel().select(tabGridPageFillIns);
                            GridPageTestWithFillIns gridPageTestWithFillIns = new GridPageTestWithFillIns();
                            gridPageTestWithFillIns.ShowAllFillInPage(webDriver,report,js,gridPageWithFillInsController.showAllFillInPage,inputGridPageURLWithFillIns,statusInfo,inputSearch, Homepage);
                        }
                        if (!tabBrandPage.isDisable()){
                            tabPane.getSelectionModel().select(tabBrandPage);
                            BrandPageTest brandPageTest = new BrandPageTest();
                            brandPageTest.pagingWithWindowsForward(webDriver,report,js,brandOverviewController.brandsWithoutLogo,inputBrandPageOverview,statusInfo,inputSearch, Homepage);
                        }
                        if (!tabLucenePage.isDisable()){
                            try{
                                tabPane.getSelectionModel().select(tabLucenePage);
                                PageLuceneWithItemsTest pageLuceneWithItemsTest = new PageLuceneWithItemsTest();
                                pageLuceneWithItemsTest.checkingSorting(webDriver,report,js,pageLuceneWithItemsController.PageLuceneWithItemsSorting,inputLucenePage,statusInfo,inputSearch, Homepage);
                                pageLuceneWithItemsTest.checkingCollapse(webDriver,report,js,pageLuceneWithItemsController.PageLuceneWithItemsCollapse,inputLucenePage,statusInfo,inputSearch, Homepage);
                                pageLuceneWithItemsTest.checkingMultiselect(webDriver,report,js,pageLuceneWithItemsController.PageLuceneWithItemsMultiSelect,inputLucenePage,statusInfo,inputSearch, Homepage);
                            }catch (Exception noLucenePageWorking){
                                noLucenePageWorking.printStackTrace();
                            }

                        }
                        if (!tabDetailPage.isDisable()){
                            try{
                                tabPane.getSelectionModel().select(tabDetailPage);
                                DetailPageTest detailPageTest = new DetailPageTest();
                                detailPageTest.SwitchTabsinDetailPage(webDriver,report,js,detailPageController.SwitchTabsInDetailPage,inputLucenePage,statusInfo,inputGridPageURL, Homepage);
                                detailPageTest.SimilarProductClickOut(webDriver,report,js,detailPageController.SimilarProductsClickOut,inputLucenePage,statusInfo,inputGridPageURL, Homepage);
                                detailPageTest.PagingForwardBackward(webDriver,report,js,detailPageController.PagingForwardBackward,inputLucenePage,statusInfo,inputGridPageURL, Homepage);
                                detailPageTest.JumpToNonExistingPage(webDriver,report,js,detailPageController.JumpToNonExistingPage,inputLucenePage,statusInfo,inputGridPageURL, Homepage);
                            }catch (Exception noLucenePageWorking){
                                noLucenePageWorking.printStackTrace();
                            }

                        }
                        if (!tabImageGrouping.isDisable()){
                            try{
                                tabPane.getSelectionModel().select(tabImageGrouping);
                                ImageGroupingPageTest imageGroupingPageTest = new ImageGroupingPageTest();
                                imageGroupingPageTest.ImageGroupingClickOut(webDriver,report,js,imageGroupingController.ImageGroupingClickOut,inputLucenePage,statusInfo,inputGridPageURL, Homepage);
                                imageGroupingPageTest.DetailPageOfOffer(webDriver,report,js,imageGroupingController.DetailPageOfOffer,inputLucenePage,statusInfo,inputGridPageURL, Homepage);
                            }catch (Exception noLucenePageWorking){
                                noLucenePageWorking.printStackTrace();
                            }

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
                    }catch (NoSuchWindowException webDriverNoWindows){
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
                        webDriverNoWindows.printStackTrace();
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
