package crisscrosscrass.Controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import crisscrosscrass.*;
import crisscrosscrass.Tasks.*;
import crisscrosscrass.Tests.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;


import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

public class MainControllerFrontEndCheck implements Serializable{
    final static Logger logger = Logger.getLogger(MainControllerFrontEndCheck.class);

    // Basic Settings
    @FXML Button startwebdriver;
    //Filter Settings
    @FXML public JFXCheckBox checkingSalesPriceFilter;
    @FXML public JFXCheckBox checkingGenderFilter;
    @FXML public JFXCheckBox checkingColorFilter;
    @FXML public JFXCheckBox checkingBrandFilter;
    @FXML public JFXCheckBox checkingMerchandiseFilter;
    // Tab Settings
    @FXML Accordion settingsAccordion;
    @FXML TitledPane settingTitledPane;
    @FXML JFXCheckBox settingHomepage;
    @FXML JFXCheckBox settingGridPage;
    @FXML JFXCheckBox settingGridPageWithWindows;
    @FXML JFXCheckBox settingGridPageFillIns;
    @FXML JFXCheckBox settingBrandPage;
    @FXML JFXCheckBox settingLucenePage;
    @FXML JFXCheckBox settingLucenePageWithDeletions;
    @FXML JFXCheckBox settingDetailPage;
    @FXML JFXCheckBox settingImageGrouping;
    @FXML JFXCheckBox settingFavoritePage;
    @FXML JFXCheckBox settingPartnerShopPage;
    @FXML JFXCheckBox settingBecomeAPartnerPage;
    @FXML JFXCheckBox settingAffiliateProgram;
    @FXML JFXCheckBox settingMerchandiseOverviewPage;
    //Main Menu Settings
    @FXML ProgressBar progressIndicator;
    @FXML Text statusInfo;
    @FXML TextField inputSearch;
    @FXML TextField inputEmailAdress;
    @FXML TextField inputTextSearchAndSuggestions;
    @FXML TextField inputImprintURL;
    @FXML TextField inputPrivacyPolicy;
    @FXML TextField inputGridPageURL;
    @FXML TextField inputGridPageKeyword;
    @FXML TextField inputGridPageURLWithWindows;
    @FXML TextField inputGridPageURLWithFillIns;
    @FXML TextField inputBrandPageOverview;
    @FXML TextField inputLucenePage;
    @FXML JFXTextField inputAccountEmail;
    @FXML TextField inputPartnerShopPageURL;
    @FXML TextField inputPartnerShopSearch;
    @FXML TextField inputBecomeAPartnerPageURL;
    @FXML JFXPasswordField inputAccountPassword;
    @FXML TextField inputAffiliateProgramURL;
    @FXML TextField inputMerchandiseOverviewPageURL;
    @FXML TextField inputMerchandiseSearch;
    @FXML HBox outputPlace;
    @FXML ImageView preloaderCat;
    @FXML VBox CheckBoxesPlace;
    @FXML AnchorPane mainStage;
    @FXML TabPane tabPane;
    //tab views
    @FXML Tab tabHomepage;
    @FXML Tab tabGridPage;
    @FXML Tab tabGridPageWithWindows;
    @FXML Tab tabGridPageFillIns;
    @FXML Tab tabBrandPage;
    @FXML Tab tabLucenePage;
    @FXML Tab tabLucenePageWithDeletions;
    @FXML Tab tabDetailPage;
    @FXML Tab tabImageGrouping;
    @FXML Tab tabFavoritePage;
    @FXML Tab tabPartnerShopPage;
    @FXML Tab tabBecomeAPartnerPage;
    @FXML Tab tabAffiliateProgram;
    @FXML Tab tabMerchandiseOverviewPage;
    //data visualisation
    @FXML PieChart pieChartForHomepageTests;
    @FXML PieChart pieChartForDetailPageTests;

    ObservableList<PieChart.Data> pieChartDataHomepageTest = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> pieChartDataDetailPageTest = FXCollections.observableArrayList();

    //Controller Settings
    @FXML FrontendHomepageController frontendHomepageController;
    @FXML GridPageWithWindowsController gridPageWithWindowsController;
    @FXML GridPageNoWindowsController gridPageNoWindowsController;
    @FXML GridPageWithFillInsController gridPageWithFillInsController;
    @FXML BrandOverviewController brandOverviewController;
    @FXML PageLuceneWithItemsController pageLuceneWithItemsController;
    @FXML DetailPageController detailPageController;
    @FXML ImageGroupingController imageGroupingController;
    @FXML FavoritePageController favoritePageController;
    @FXML PartnershopsPageController partnershopsPageController;
    @FXML BecomeAPartnerController becomeAPartnerController;
    @FXML AffiliateProgramController affiliateProgramController;
    @FXML MerchandiseOverviewPageController merchandiseOverviewPageController;

    private static boolean isSuccessful = false;
    private static boolean isAvailable = false;
    private static String xpathPattern1 = "";
    private static String xpathPattern2 = "";
    private static String xpathPatternImage1 = "";
    private static String xpathPatternImage2 = "";

    @FXML
    public void initialize() {
        logger.info( "Main Program started!" );
        //add Listener to Settings
        settingHomepage.setOnAction(event -> updateCheckerTabs());
        settingGridPage.setOnAction(event -> updateCheckerTabs());
        settingGridPageWithWindows.setOnAction(event -> updateCheckerTabs());
        settingGridPageFillIns.setOnAction(event -> updateCheckerTabs());
        settingBrandPage.setOnAction(event -> updateCheckerTabs());
        settingLucenePage.setOnAction(event -> updateCheckerTabs());
        settingLucenePageWithDeletions.setOnAction(event -> updateCheckerTabs());
        settingDetailPage.setOnAction(event -> updateCheckerTabs());
        settingImageGrouping.setOnAction(event -> updateCheckerTabs());
        settingFavoritePage.setOnAction(event -> updateCheckerTabs());
        settingPartnerShopPage.setOnAction(event -> updateCheckerTabs());
        settingBecomeAPartnerPage.setOnAction(event -> updateCheckerTabs());
        settingAffiliateProgram.setOnAction(event -> updateCheckerTabs());
        settingMerchandiseOverviewPage.setOnAction(event -> updateCheckerTabs());
        //update Tabs on Frontend
        updateCheckerTabs();
        //check if Properties File is available if yes, load data into Input Fields
        File file = new File("temp//UserSettings.properties");
        if (!file.exists()) {
            copyUserSettingFiles();
        }
        //load userInputData into Properties
        ConfigSettings configSettingsReader = new ConfigSettings();
        Properties userData = configSettingsReader.readConfigSettings();
        inputSearch.setText(userData.getProperty("inputSearch"));
        inputEmailAdress.setText(userData.getProperty("inputEmailAdress"));
        inputTextSearchAndSuggestions.setText(userData.getProperty("inputTextSearchAndSuggestions"));
        inputGridPageURL.setText(userData.getProperty("inputGridPageURL"));
        inputGridPageKeyword.setText(userData.getProperty("inputGridPageKeyword"));
        inputGridPageURLWithWindows.setText(userData.getProperty("inputGridPageURLWithWindows"));
        inputGridPageURLWithFillIns.setText(userData.getProperty("inputGridPageURLWithFillIns"));
        inputBrandPageOverview.setText(userData.getProperty("inputBrandPageOverview"));
        inputLucenePage.setText(userData.getProperty("inputLucenePage"));
        inputAccountEmail.setText(userData.getProperty("inputAccountEmail"));
        inputAccountPassword.setText(userData.getProperty("inputAccountPassword"));
        inputPartnerShopPageURL.setText(userData.getProperty("inputPartnerShopPageURL"));
        inputPartnerShopSearch.setText(userData.getProperty("inputPartnerShopSearch"));
        inputBecomeAPartnerPageURL.setText(userData.getProperty("inputBecomeAPartnerPageURL"));
        inputAffiliateProgramURL.setText(userData.getProperty("inputAffiliateProgramURL"));
        inputMerchandiseOverviewPageURL.setText(userData.getProperty("inputMerchandiseOverviewPageURL"));
        inputMerchandiseSearch.setText(userData.getProperty("inputMerchandiseSearch"));

        //opening Menu in User Interface
        Platform.runLater(() -> settingTitledPane.setExpanded(true));

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
        if (settingFavoritePage.isSelected()){
            tabFavoritePage.setDisable(false);
            tabPane.getSelectionModel().select(tabFavoritePage);
        }
        else{
            tabFavoritePage.setDisable(true);
        }
        if (settingPartnerShopPage.isSelected()){
            tabPartnerShopPage.setDisable(false);
            tabPane.getSelectionModel().select(tabPartnerShopPage);
        }
        else{
            tabPartnerShopPage.setDisable(true);
        }
        if (settingBecomeAPartnerPage.isSelected()){
            tabBecomeAPartnerPage.setDisable(false);
            tabPane.getSelectionModel().select(tabBecomeAPartnerPage);
        }else{
            tabBecomeAPartnerPage.setDisable(true);
        }
        if (settingAffiliateProgram.isSelected()){
            tabAffiliateProgram.setDisable(false);
            tabPane.getSelectionModel().select(tabAffiliateProgram);
        }else{
            tabAffiliateProgram.setDisable(true);
        }
        if (settingMerchandiseOverviewPage.isSelected()){
            tabMerchandiseOverviewPage.setDisable(false);
            tabPane.getSelectionModel().select(tabMerchandiseOverviewPage);
        }else{
            tabMerchandiseOverviewPage.setDisable(true);
        }
    }

    @FXML
    public void startRealAction() {
        logger.info("Start Engine...");
        Platform.runLater(() -> {

                    progressIndicator.setProgress(-1);
                    startwebdriver.setDisable(true);
                    inputSearch.setDisable(true);
                    inputEmailAdress.setDisable(true);
                    inputTextSearchAndSuggestions.setDisable(true);
                    inputGridPageURL.setDisable(true);
                    inputGridPageKeyword.setDisable(true);
                    inputGridPageURLWithWindows.setDisable(true);
                    inputGridPageURLWithFillIns.setDisable(true);
                    inputBrandPageOverview.setDisable(true);
                    inputLucenePage.setDisable(true);
                    inputAccountEmail.setDisable(true);
                    inputPartnerShopPageURL.setDisable(true);
                    inputPartnerShopSearch.setDisable(true);
                    inputBecomeAPartnerPageURL.setDisable(true);
                    inputAccountPassword.setDisable(true);
                    inputImprintURL.setDisable(true);
                    inputAffiliateProgramURL.setDisable(true);
                    inputMerchandiseOverviewPageURL.setDisable(true);
                    inputMerchandiseSearch.setDisable(true);
                    settingTitledPane.setExpanded(false);
        });


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


                // * detect if Ressources are available
                File webdriverFile = new File("temp//chromedriver.exe");
                if (!webdriverFile.exists()) {
                    logger.info("Webdriver not exist, create webdriverFile");
                    copyFiles();
                }



                // * Basic Settings while Starting WebDriver
                Platform.runLater(() -> statusInfo.setText("Starting Engine..."));
                System.setProperty("webdriver.chrome.driver", "temp//chromedriver.exe");
                ChromeOptions option = new ChromeOptions();
                //option.addArguments("--window-size=1920,1080");
                //option.addArguments("--headless");
                //option.addArguments("--disable-gpu");
                option.addArguments("--disable-infobars");
                option.addArguments("--start-maximized");
                ChromeDriver webDriver = new ChromeDriver(option);


                try{
                    JavascriptExecutor js = webDriver;
                    Report report = new Report();
                    report.clearWrittenReport();
                    ScreenshotViaWebDriver.clearWrittenScreenshots();


                    // open Startpage and set window
                    Platform.runLater(() -> {
                        Window window = mainStage.getScene().getWindow();
                        window.requestFocus();
                        statusInfo.setText("Open Maximize Mode...");
                    });
                    //webDriver.manage().window().maximize();
                    Platform.runLater(() -> statusInfo.setText("Go to requested Website..."));


                    long start = System.currentTimeMillis();
                    webDriver.navigate().to(inputSearch.getText().trim());
                    long finish = System.currentTimeMillis();
                    long totalTime = finish - start;
                    logger.info("Total Time for page load - "+totalTime);
                    report.writeToFile("Checking Website: ", inputSearch.getText().trim());
                    report.writeToFile("=================================", "");
                    if (!tabHomepage.isDisable()){
                        tabPane.getSelectionModel().select(tabHomepage);
                        HomepageTest homepageTest = new HomepageTest();
                        homepageTest.checkingCategories(webDriver,report,frontendHomepageController.checkCategoryLinksLeftSideMenu,statusInfo,inputSearch, Homepage);
                        homepageTest.checkingShopOfTheWeek(webDriver,report,frontendHomepageController.checkLogoFromShopOfTheWeek,statusInfo,inputSearch, Homepage);
                        homepageTest.checkingShopOfTheWeekCategories(webDriver,report,frontendHomepageController.checkCategoryLinksFromShopOfTheWeek,statusInfo,inputSearch, Homepage);
                        homepageTest.checkingNewsletterBanner(webDriver,report,frontendHomepageController.checkNewsletterBannerFunctionality,statusInfo,inputSearch,inputEmailAdress, Homepage);
                        homepageTest.checkingNewsletterPopUp(webDriver,report,frontendHomepageController.checkNewsletterPopUp,statusInfo,inputSearch, Homepage);
                        homepageTest.checkingNewsletterPopUpFunctionality(webDriver,report,js,frontendHomepageController.checkNewsletterPopUpFunctionality,statusInfo,inputSearch,inputEmailAdress, Homepage);
                        homepageTest.checkingFooterLinks(webDriver,report, frontendHomepageController.checkFooterLinks,statusInfo,inputSearch, Homepage);
                        homepageTest.checkingSearchAndSuggestions(webDriver,report, frontendHomepageController.checkTextSearchAndSuggestions,inputTextSearchAndSuggestions,statusInfo,inputSearch, Homepage);
                        homepageTest.checkingFeedbackPopUp(webDriver,report, frontendHomepageController.checkFeedbackPopUp, statusInfo,inputSearch, Homepage);
                        homepageTest.checkingPrivacyPopUp(webDriver,report, frontendHomepageController.checkPrivacyPopUp, statusInfo,inputSearch, Homepage);
                        homepageTest.checkingImprint(webDriver,report, frontendHomepageController.checkImprint, statusInfo,inputImprintURL, Homepage);
                        homepageTest.checkingPrivacyPolicy(webDriver,report, frontendHomepageController.PrivacyPolicy, statusInfo,inputPrivacyPolicy, Homepage);
                        //setting up PieChart
                        //updateDataViaPieChart();
                    }
                    if (!tabGridPage.isDisable()){
                        tabPane.getSelectionModel().select(tabGridPage);
                        GridPageTest gridPageTest = new GridPageTest();
                        gridPageTest.checkingSorting(webDriver,report,js,gridPageNoWindowsController.sortingValues,inputGridPageURL,statusInfo,inputSearch, Homepage);
                        gridPageTest.checkingSwitchFromSmallToLargeImages(webDriver,report,js,gridPageNoWindowsController.switchFromSmallToLarge,inputGridPageURL,statusInfo,inputSearch, Homepage);
                        gridPageTest.checkingPagingForwardBackward(webDriver,report,js,gridPageNoWindowsController.pagingForwardBackward,inputGridPageURL,statusInfo,inputSearch, Homepage);
                        gridPageTest.checkingProductView300(webDriver,report,js,gridPageNoWindowsController.productView300,inputGridPageURL,statusInfo,inputSearch, Homepage);
                        gridPageTest.checkingDeeperStyle(webDriver,report,js,gridPageNoWindowsController.deeperStyle,inputGridPageURL,statusInfo,inputSearch, Homepage);
                        gridPageTest.checkingStyleBoxOpenClose(webDriver,report,js,gridPageNoWindowsController.styleBoxOpenClose,inputGridPageURL,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                        gridPageTest.checkingFilterApply(webDriver,report,js,gridPageNoWindowsController.filtersApply,inputGridPageURL,statusInfo,inputSearch,Homepage,isSuccessful,isAvailable,checkingSalesPriceFilter,checkingGenderFilter,checkingColorFilter,checkingBrandFilter,checkingMerchandiseFilter);
                        gridPageTest.checkingSearchBoxInBrandFilter(webDriver,report,js,gridPageNoWindowsController.searchBoxInBrandFilter,inputGridPageURL,inputGridPageKeyword,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                        gridPageTest.checkingSearchBoxInShopFilter(webDriver,report,js,gridPageNoWindowsController.searchBoxInShopFilter,inputGridPageURL,inputGridPageKeyword,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                    }
                    if (!tabGridPageWithWindows.isDisable()){
                        tabPane.getSelectionModel().select(tabGridPageWithWindows);
                        GridPageTestWithWindows gridPageTestWithWindows = new GridPageTestWithWindows();
                        gridPageTestWithWindows.checkingPagingWithWindowsForward(webDriver,report,js,gridPageWithWindowsController.PagingWithWindowsForward,inputGridPageURLWithWindows,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                    }

                    if (!tabGridPageFillIns.isDisable()){
                        tabPane.getSelectionModel().select(tabGridPageFillIns);
                        GridPageTestWithFillIns gridPageTestWithFillIns = new GridPageTestWithFillIns();
                        gridPageTestWithFillIns.checkingShowAllFillInPage(webDriver,report,js,gridPageWithFillInsController.showAllFillInPage,inputGridPageURLWithFillIns,statusInfo,inputSearch, Homepage);
                    }
                    if (!tabBrandPage.isDisable()){
                        tabPane.getSelectionModel().select(tabBrandPage);
                        BrandPageTest brandPageTest = new BrandPageTest();
                        brandPageTest.checkingBrandsWithoutLogo(webDriver,report,js,brandOverviewController.brandsWithoutLogo,inputBrandPageOverview,statusInfo,inputSearch, Homepage);
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
                            detailPageTest.checkingSwitchTabsinDetailPage(webDriver,report,js,detailPageController.SwitchTabsInDetailPage,inputLucenePage,statusInfo,inputGridPageURL, Homepage);
                            detailPageTest.checkingSimilarProductClickOut(webDriver,report,js,detailPageController.SimilarProductsClickOut,inputLucenePage,statusInfo,inputGridPageURL, Homepage);
                            detailPageTest.checkingPagingForwardBackward(webDriver,report,js,detailPageController.PagingForwardBackward,inputLucenePage,statusInfo,inputGridPageURL, Homepage);
                            detailPageTest.checkingJumpToNonExistingPage(webDriver,report,js,detailPageController.JumpToNonExistingPage,inputLucenePage,statusInfo,inputGridPageURL, Homepage);
                        }catch (Exception noDetailPageWorking){
                            noDetailPageWorking.printStackTrace();
                        }

                    }
                    if (!tabImageGrouping.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabImageGrouping);
                            ImageGroupingPageTest imageGroupingPageTest = new ImageGroupingPageTest();
                            imageGroupingPageTest.checkingImageGroupingClickOut(webDriver,report,js,imageGroupingController.ImageGroupingClickOut,inputLucenePage,statusInfo,inputGridPageURL, Homepage);
                            imageGroupingPageTest.checkingDetailPageOfOffer(webDriver,report,js,imageGroupingController.DetailPageOfOffer,inputLucenePage,statusInfo,inputGridPageURL, Homepage);
                        }catch (Exception noLucenePageWorking){
                            noLucenePageWorking.printStackTrace();
                        }

                    }
                    if (!tabFavoritePage.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabFavoritePage);
                            FavoritePageTest favoritePageTest = new FavoritePageTest();
                            favoritePageTest.checkingPersonalListTest(webDriver,report,js,favoritePageController.PersonalList, statusInfo,inputSearch, Homepage, inputAccountEmail, inputAccountPassword);
                            favoritePageTest.checkingApplySortingOnList(webDriver,report,js,favoritePageController.SortingOnList,statusInfo,inputGridPageURL, Homepage, inputAccountEmail, inputAccountPassword);
                            favoritePageTest.checkingSelectionOnList(webDriver,report,js,favoritePageController.SelectionOnList,statusInfo,inputSearch, Homepage, inputAccountEmail, inputAccountPassword);
                        }catch (Exception noFavoritePageWorking){
                            noFavoritePageWorking.printStackTrace();
                        }
                    }
                    if (!tabPartnerShopPage.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabPartnerShopPage);
                            PartnerShopsPageTest partnerShopsPageTest = new PartnerShopsPageTest();
                            partnerShopsPageTest.checkingGoToTopButton(webDriver,report,js,partnershopsPageController.GoToTopButton,statusInfo,inputPartnerShopPageURL, Homepage);
                            partnerShopsPageTest.checkingBecomePartnerPopUp(webDriver,report,js,partnershopsPageController.BecomePartnerPopUp,statusInfo,inputPartnerShopPageURL, Homepage);
                            partnerShopsPageTest.checkingSortingReviews(webDriver,report,js,partnershopsPageController.SortingReviews,statusInfo,inputPartnerShopPageURL, Homepage);
                            partnerShopsPageTest.checkingShopLinkName(webDriver,report,js,partnershopsPageController.ShopLinkName,statusInfo,inputPartnerShopPageURL, Homepage);
                            partnerShopsPageTest.checkingShopLinkLogo(webDriver,report,js,partnershopsPageController.ShopLinkLogo,statusInfo,inputPartnerShopPageURL, Homepage);
                            partnerShopsPageTest.checkingShopReview(webDriver,report,js,partnershopsPageController.ShopLinkReview,statusInfo,inputPartnerShopPageURL, Homepage);
                            partnerShopsPageTest.checkingShopSearchBox(webDriver,report,js,partnershopsPageController.ShopSearchBox,statusInfo,inputPartnerShopPageURL,inputPartnerShopSearch,Homepage);
                        }catch (Exception noPartnerShopPageWorking){
                            noPartnerShopPageWorking.printStackTrace();
                        }
                    }
                    if (!tabBecomeAPartnerPage.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabBecomeAPartnerPage);
                            BecomeAPartnerPageTest becomeAPartnerPageTest = new BecomeAPartnerPageTest();
                            becomeAPartnerPageTest.checkingRegisterButton(webDriver,report,js,becomeAPartnerController.RegisterButton,statusInfo,inputBecomeAPartnerPageURL, Homepage);
                            becomeAPartnerPageTest.checkingBecomePartnerButton(webDriver,report,js,becomeAPartnerController.BecomeAPartnerButton,statusInfo,inputBecomeAPartnerPageURL, Homepage);
                            becomeAPartnerPageTest.checkingGoToTopButton(webDriver,report,js,becomeAPartnerController.GoToTopButton,statusInfo,inputBecomeAPartnerPageURL, Homepage);
                            becomeAPartnerPageTest.checkingCountryFlags(webDriver,report,js,becomeAPartnerController.CountryFlags,statusInfo,inputBecomeAPartnerPageURL, Homepage);
                            becomeAPartnerPageTest.checkingLoginPartnerdashboard(webDriver,report,js,becomeAPartnerController.LoginPartnerDashboard,statusInfo,inputBecomeAPartnerPageURL, Homepage);
                            becomeAPartnerPageTest.checkingTabHelpSection(webDriver,report,js,becomeAPartnerController.HelpTabSection,statusInfo,inputBecomeAPartnerPageURL, Homepage);
                            becomeAPartnerPageTest.checkingDownloadOnHelp(webDriver,report,js,becomeAPartnerController.DownloadPDFHelp,statusInfo,inputBecomeAPartnerPageURL, Homepage);
                            becomeAPartnerPageTest.checkingTabPartnerSection(webDriver,report,js,becomeAPartnerController.PartnerTabSection,statusInfo,inputBecomeAPartnerPageURL, Homepage);
                            becomeAPartnerPageTest.checkingFeedProviders(webDriver,report,js,becomeAPartnerController.FeedProviders,statusInfo,inputBecomeAPartnerPageURL, Homepage);
                        }catch (Exception noBecomeAPartnerWorking){
                            noBecomeAPartnerWorking.printStackTrace();
                        }
                    }
                    if (!tabAffiliateProgram.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabAffiliateProgram);
                            AffiliateProgramTest affiliateProgramTest = new AffiliateProgramTest();
                            affiliateProgramTest.checkingBecomeAffilinetPartner(webDriver,report,js,affiliateProgramController.BecomeAffilinetPartner,statusInfo,inputAffiliateProgramURL, Homepage);
                            affiliateProgramTest.checkingBecomeTradeTrackerPartner(webDriver,report,js,affiliateProgramController.BecomeTradeTrackerPartner,statusInfo,inputAffiliateProgramURL, Homepage);
                        }catch (Exception noBecomeAPartnerWorking){
                            noBecomeAPartnerWorking.printStackTrace();
                        }
                    }
                    if (!tabMerchandiseOverviewPage.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabMerchandiseOverviewPage);
                            MerchandiseOverviewPageTest merchandiseOverviewPageTest = new MerchandiseOverviewPageTest();
                            merchandiseOverviewPageTest.checkingMerchandiseLetters(webDriver,report,js,merchandiseOverviewPageController.LettertoMerchandise,statusInfo,inputMerchandiseOverviewPageURL, Homepage);
                            merchandiseOverviewPageTest.checkingMerchandiseName(webDriver,report,js,merchandiseOverviewPageController.MerchandiseName,statusInfo,inputMerchandiseOverviewPageURL, Homepage);
                            merchandiseOverviewPageTest.checkingMerchandiseSearch(webDriver,report,js,merchandiseOverviewPageController.MerchandiseSearch,statusInfo,inputMerchandiseOverviewPageURL,inputMerchandiseSearch, Homepage);
                            merchandiseOverviewPageTest.checkingMerchandiseGoToTop(webDriver,report,js,merchandiseOverviewPageController.GoToTop,statusInfo,inputMerchandiseOverviewPageURL, Homepage);
                        }catch (Exception noBecomeAPartnerWorking){
                            noBecomeAPartnerWorking.printStackTrace();
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
            logger.info("All process are finished");
        }));
    }


    //Just for Themeweek! Will be removed once the Themeweek is over
    @FXML
    public void LoadFileWithURLs() {
        FileChooser fileChooser = new FileChooser();
        File fileWithURLS = fileChooser.showOpenDialog(null);
        if (fileWithURLS != null){

            Task task = new Task<Object>() {
                @Override
                protected Void call() {
                    startCrawlerCheck();
                    return null;
                }
                private void startCrawlerCheck(){
                    Platform.runLater(() -> {
                        progressIndicator.setProgress(-1);
                    });

                    try {
                        final Logger logger = Logger.getRootLogger();
                        // * Load Properties File
                        String resourceName = "configs/page.properties";
                        ClassLoader loader = Thread.currentThread().getContextClassLoader();
                        Properties Homepage = new Properties();
                        try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
                            Homepage.load(resourceStream);
                        }catch (Exception nope){
                            nope.getStackTrace();
                        }
                        Report report = new Report();
                        report.clearWrittenReport();

                        // * Setup up Webdriver
                        System.setProperty("webdriver.chrome.driver", "temp//chromedriver.exe");
                        ChromeOptions option = new ChromeOptions();
                        option.addArguments("disable-infobars");
                        option.addArguments("start-maximized");
                        //option.addArguments("--headless");
                        ChromeDriver webDriver = new ChromeDriver(option);
                        JavascriptExecutor js = webDriver;
                        WebDriverWait wait = new WebDriverWait(webDriver, 10);



                        Platform.runLater(() -> {
                            Window window = mainStage.getScene().getWindow();
                            window.requestFocus();
                            statusInfo.setText("Open Maximize Mode...");
                        });
                        webDriver.manage().window().maximize();
                        Platform.runLater(() -> statusInfo.setText("Go to requested Website..."));

                        String fileWithURLLocation = fileWithURLS.getAbsolutePath();
                        FileReader fileReader = new FileReader(fileWithURLLocation);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        String line;
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        int lineReaderAt = 0;
                        while ( (line = bufferedReader.readLine()) != null){
                            now = LocalDateTime.now();
                            long start = System.currentTimeMillis();
                            String StatusUpdate = line;
                            int StatusLine = lineReaderAt;
                            Platform.runLater(() -> statusInfo.setText("Line : "+StatusLine+" - "+StatusUpdate.toLowerCase().trim()));
                            logger.info("Webdriver call : "+line.toLowerCase().trim());
                            ++lineReaderAt;
                            try{
                                if (line.contains(";")){
                                    line = line.replaceAll("\\d+;+","");
                                }
                                webDriver.navigate().to(line.toLowerCase().trim());
                                long finish = System.currentTimeMillis();
                                long totalTime = finish - start;
                                //logger.info(line + " Total Time for page load - " + totalTime);
                                try{
                                    try{
                                        if (StatusUpdate.toLowerCase().trim().equals(webDriver.getCurrentUrl().toLowerCase().trim()) ){
                                            if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0){
                                                report.writeToFile(dtf.format(now)+" | "+line.toLowerCase().trim()+" | YES | Windows");
                                            }else {
                                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.info.icon"))));
                                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.info.icon"))));
                                                List<WebElement> ItemsOnGridPage = webDriver.findElementsByXPath(Homepage.getProperty("page.items.info.icon"));
                                                logger.info("Site: " + webDriver.getCurrentUrl().toLowerCase().trim()+ " Items: " + ItemsOnGridPage.size());
                                                if (ItemsOnGridPage.size() != 0){
                                                    report.writeToFile(dtf.format(now)+" | "+line.toLowerCase().trim()+" | YES | "+ " Items: " + ItemsOnGridPage.size());
                                                }else {
                                                    report.writeToFile(dtf.format(now)+" | "+line.toLowerCase().trim()+" | NO | No Items");
                                                }
                                            }
                                        }else {
                                            report.writeToFile(dtf.format(now)+" | "+line.toLowerCase().trim()+" | NO | Redirect to: "+webDriver.getCurrentUrl().toLowerCase().trim());
                                        }


                                    }catch (Exception noPrice){
                                        try{
                                            if (StatusUpdate.toLowerCase().trim().equals(webDriver.getCurrentUrl().toLowerCase().trim()) ){
                                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Homepage.getProperty("page.items.info.icon"))));
                                                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("page.items.info.icon"))));
                                                List<WebElement> ItemsOnGridPage = webDriver.findElementsByXPath(Homepage.getProperty("page.items.info.icon"));
                                                logger.info("Site: " + webDriver.getCurrentUrl().toLowerCase().trim()+ " Items: " + ItemsOnGridPage.size());
                                                if (ItemsOnGridPage.size() != 0){
                                                    report.writeToFile(dtf.format(now)+" | "+line.toLowerCase().trim()+" | YES | "+ " Items: " + ItemsOnGridPage.size());
                                                }else {
                                                    report.writeToFile(dtf.format(now)+" | "+line.toLowerCase().trim()+" | NO | No Items");
                                                }
                                            }else{
                                                report.writeToFile(dtf.format(now)+" | "+line.toLowerCase().trim()+" | NO | Redirect to: "+webDriver.getCurrentUrl().toLowerCase().trim());
                                            }

                                        }catch (Exception noAtAll){
                                            report.writeToFile(dtf.format(now)+" | "+line.toLowerCase().trim()+" | YES | Couldn't detect Items for some reason, proberly Image Grouping");
                                            noPrice.printStackTrace();
                                        }
                                    }
                                }catch(NoSuchElementException noItemsFound){
                                    report.writeToFile(dtf.format(now)+" | "+line.toLowerCase().trim()+" | NO | Something went wrong while checking");
                                    noItemsFound.printStackTrace();
                                }
                            }catch (Exception noWebDriverNavigate){
                                report.writeToFile(dtf.format(now)+" | "+line.toLowerCase().trim()+" | Invalid URL | Needs to be checked again");
                            }


                        }
                        try {
                            bufferedReader.close();
                        }catch (Exception noCloseBufferReader){
                            noCloseBufferReader.printStackTrace();
                        }
                        try {
                            fileReader.close();
                        }catch (Exception noCloseFileReader){
                            noCloseFileReader.printStackTrace();
                        }

                        webDriver.navigate().to(inputSearch.getText().trim());

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
                        try {
                            Runtime.getRuntime().exec("TASKKILL /F /IM chromedriver.exe");
                            Runtime.getRuntime().exec("taskkill /im chromedriver.exe /f");
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    } catch (Exception noLoadFileWithURL) {
                        noLoadFileWithURL.printStackTrace();
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
                link.setStyle("-fx-font: 24 arial;");
                link.setTextAlignment(TextAlignment.CENTER);
                outputPlace.getChildren().addAll(link);
                progressIndicator.setProgress(100);
                changeButtonText();
                logger.info("All process to checkes are finished");
            }));

        }

    }
    @FXML
    private void changeButtonText() {
        statusInfo.setText("Running complete");
        startwebdriver.setDisable(false);
        inputSearch.setDisable(false);
        inputEmailAdress.setDisable(false);
        inputTextSearchAndSuggestions.setDisable(false);
        inputGridPageURL.setDisable(false);
        inputGridPageKeyword.setDisable(false);
        inputGridPageURLWithWindows.setDisable(false);
        inputGridPageURLWithFillIns.setDisable(false);
        inputBrandPageOverview.setDisable(false);
        inputLucenePage.setDisable(false);
        inputAccountEmail.setDisable(false);
        inputPartnerShopPageURL.setDisable(false);
        inputPartnerShopSearch.setDisable(false);
        inputBecomeAPartnerPageURL.setDisable(false);
        inputImprintURL.setDisable(false);
        inputAccountPassword.setDisable(false);
        inputAffiliateProgramURL.setDisable(false);
        inputMerchandiseOverviewPageURL.setDisable(false);
        inputMerchandiseSearch.setDisable(false);
    }

    @FXML
    private void copyFiles() {
        CopyFiles bringit = new CopyFiles();
        bringit.copyChromeDriverFile();

    }
    @FXML
    private void copyUserSettingFiles() {
        CopyFiles bringit = new CopyFiles();
        bringit.copyUserSettings();

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
            frontendHomepageController.checkCategoryLinksLeftSideMenu.setStyle("-fx-background-color: #FFFFFF");
            frontendHomepageController.checkCategoryLinksLeftSideMenu.setSelected(false);
            outputPlace.getChildren().clear();
        });
    }

    public void updateDataViaPieChart(){
        JFXCheckBox[] checkboxes = frontendHomepageController.frontendHomePageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]);
        int passTest = 0;
        int failTest = 0;


        for (JFXCheckBox checkBox : checkboxes){
            if (checkBox.isSelected() ){
                if (checkBox.getCheckedColor().toString().substring(2,8).equals(ChangeCheckBox.getIsSuccessful())){
                    System.out.println("GreenCheck!");
                    ++passTest;
                }
                if (checkBox.getCheckedColor().toString().substring(2,8).equals(ChangeCheckBox.getIsNotSuccessful())){
                    System.out.println("Red Check!");
                    ++failTest;
                }
            }
        }

        PieChart.Data HomepagePass = new PieChart.Data("Pass", passTest);
        PieChart.Data HomepageFail = new PieChart.Data("Fail", failTest);
        try{
            Platform.runLater(() -> {
                pieChartForHomepageTests.setData(pieChartDataHomepageTest);
            });
            Platform.runLater(() -> {
                pieChartDataHomepageTest.add(HomepagePass);
                pieChartDataHomepageTest.add(HomepageFail);
            });


            int finalFailTest = failTest;
            int finalPassTest = passTest;
            Platform.runLater(() -> {
                HomepageFail.setPieValue(finalFailTest);
            });
            Platform.runLater(() -> {
                HomepagePass.setPieValue(finalPassTest);
                pieChartForHomepageTests.setTitle("HomepageTest");
            });

        }catch (Exception somethingWrong){
            somethingWrong.printStackTrace();
        }
    }

    @FXML
    public void selectAllSettingCheckBoxes(){
        settingHomepage.setSelected(true);
        settingGridPage.setSelected(true);
        settingGridPageWithWindows.setSelected(true);
        settingGridPageFillIns.setSelected(true);
        settingBrandPage.setSelected(true);
        settingLucenePage.setSelected(true);
        settingLucenePageWithDeletions.setSelected(true);
        settingDetailPage.setSelected(true);
        settingImageGrouping.setSelected(true);
        settingFavoritePage.setSelected(true);
        settingPartnerShopPage.setSelected(true);
        settingBecomeAPartnerPage.setSelected(true);
        settingAffiliateProgram.setSelected(true);
        settingMerchandiseOverviewPage.setSelected(true);
        updateCheckerTabs();
    }

    @FXML
    public void deselectAllSettingCheckBoxes(){
        settingHomepage.setSelected(false);
        settingGridPage.setSelected(false);
        settingGridPageWithWindows.setSelected(false);
        settingGridPageFillIns.setSelected(false);
        settingBrandPage.setSelected(false);
        settingLucenePage.setSelected(false);
        settingLucenePageWithDeletions.setSelected(false);
        settingDetailPage.setSelected(false);
        settingImageGrouping.setSelected(false);
        settingFavoritePage.setSelected(false);
        settingPartnerShopPage.setSelected(false);
        settingBecomeAPartnerPage.setSelected(false);
        settingAffiliateProgram.setSelected(false);
        settingMerchandiseOverviewPage.setSelected(false);
        updateCheckerTabs();
    }


}
