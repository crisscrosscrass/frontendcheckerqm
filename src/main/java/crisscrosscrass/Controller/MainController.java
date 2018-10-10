package crisscrosscrass.Controller;

import com.jfoenix.controls.*;
import crisscrosscrass.*;
import crisscrosscrass.Tasks.*;
import crisscrosscrass.Tests.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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

public class MainController implements Serializable{
    final static Logger logger = Logger.getLogger(MainController.class);

    // Basic Settings
    @FXML Button startwebdriver;
    @FXML Button stopWebdriver;
    //Filter Settings
    @FXML JFXComboBox countrySelection;
    @FXML public JFXCheckBox checkingSalesPriceFilter;
    @FXML public JFXCheckBox checkingGenderFilter;
    @FXML public JFXCheckBox checkingColorFilter;
    @FXML public JFXCheckBox checkingBrandFilter;
    @FXML public JFXCheckBox checkingMerchandiseFilter;
    // Tab Settings
    //@FXML Accordion settingsAccordion;
    //@FXML TitledPane settingTitledPane;
    @FXML JFXCheckBox settingHomepage;
    @FXML JFXCheckBox settingGridPage;
    @FXML JFXCheckBox settingGridPageWithWindows;
    @FXML JFXCheckBox settingGridPageFillIns;
    @FXML JFXCheckBox settingBrandPage;
    @FXML JFXCheckBox settingLucenePage;
    @FXML JFXCheckBox settingMainMenuOnHomePage;
    @FXML JFXCheckBox settingDetailPage;
    @FXML JFXCheckBox settingImageGrouping;
    @FXML JFXCheckBox settingFavoritePage;
    @FXML JFXCheckBox settingPartnerShopPage;
    @FXML JFXCheckBox settingBecomeAPartnerPage;
    @FXML JFXCheckBox settingAffiliateProgram;
    @FXML JFXCheckBox settingMerchandiseOverviewPage;
    //Element Boxes to hide
    @FXML HBox ElementLuceneBox;
    @FXML VBox ElementLoginBox;
    @FXML HBox ElementTextSearchSuggestionBox;
    @FXML HBox ElementShopSearchBox;
    @FXML HBox ElementMerchandiseSearchBox;
    @FXML HBox ElementGridPageSearchBox;
    @FXML HBox ElementGridPageWithoutWindowBox;
    @FXML HBox ElementGridPageWithWindowBox;
    @FXML HBox ElementGridPageWithFillInsBox;
    @FXML HBox ElementFiltersBox;
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
    @FXML StackPane placeForTooltipSetting;
    @FXML StackPane placeForTooltipInput;
    //Info Buttons
    @FXML FontAwesomeIconView infoHomepageTest;
    @FXML FontAwesomeIconView infoGridPageTest;
    @FXML FontAwesomeIconView infoGridPageWindowTest;
    @FXML FontAwesomeIconView infoGridPageFillInsTest;
    @FXML FontAwesomeIconView infoBrandTest;
    @FXML FontAwesomeIconView infoLuceneTest;
    @FXML FontAwesomeIconView infoOnMainMenuTest;
    @FXML FontAwesomeIconView infoDetailPageTest;
    @FXML FontAwesomeIconView infoImageGroupingTest;
    @FXML FontAwesomeIconView infoFavoritePageTest;
    @FXML FontAwesomeIconView infoPartnerShopPageTest;
    @FXML FontAwesomeIconView infoBecomeAPartnerPageTest;
    @FXML FontAwesomeIconView infoAffiliateTest;
    @FXML FontAwesomeIconView infoMerchandiseTest;
    @FXML FontAwesomeIconView infoInputFieldTextSearch;
    //tab views
    @FXML Tab tabHomepage;
    @FXML Tab tabGridPage;
    @FXML Tab tabGridPageWithWindows;
    @FXML Tab tabGridPageFillIns;
    @FXML Tab tabBrandPage;
    @FXML Tab tabLucenePage;
    @FXML Tab tabMainMenuOnHomePage;
    @FXML Tab tabDetailPage;
    @FXML Tab tabImageGrouping;
    @FXML Tab tabFavoritePage;
    @FXML Tab tabPartnerShopPage;
    @FXML Tab tabBecomeAPartnerPage;
    @FXML Tab tabAffiliateProgram;
    @FXML Tab tabMerchandiseOverviewPage;
    //data visualisation
    @FXML VBox PlaceForPieCharts;
    //Controller for each CheckList
    @FXML FrontendHomepageController frontendHomepageController;
    @FXML GridPageWithWindowsController gridPageWithWindowsController;
    @FXML GridPageNoWindowsController gridPageNoWindowsController;
    @FXML GridPageWithFillInsController gridPageWithFillInsController;
    @FXML BrandOverviewController brandOverviewController;
    @FXML PageLuceneWithItemsController pageLuceneWithItemsController;
    @FXML MainMenuOnHomePageController mainMenuOnHomePageController;
    @FXML DetailPageController detailPageController;
    @FXML ImageGroupingController imageGroupingController;
    @FXML FavoritePageController favoritePageController;
    @FXML PartnershopsPageController partnershopsPageController;
    @FXML BecomeAPartnerController becomeAPartnerController;
    @FXML AffiliateProgramController affiliateProgramController;
    @FXML MerchandiseOverviewPageController merchandiseOverviewPageController;
    //ResultBoxes
    @FXML Label globalCounterTestCases;
    @FXML Label resultBoxHomepage;
    @FXML Label resultBoxGridPage;
    @FXML Label resultBoxGridPageWindows;
    @FXML Label resultBoxGridPageFillIns;
    @FXML Label resultBoxBrandPage;
    @FXML Label resultBoxLucenePage;
    @FXML Label resultBoxMainMenuOnHomePage;
    @FXML Label resultBoxDetailPage;
    @FXML Label resultBoxImageGrouping;
    @FXML Label resultBoxFavoritePage;
    @FXML Label resultBoxPartnershopPage;
    @FXML Label resultBoxBecomePartner;
    @FXML Label resultBoxAffiliateProgram;
    @FXML Label resultBoxMerchandise;
    @FXML Label BoxHomepageResult;
    @FXML Label BoxGridPageResult;
    @FXML Label BoxGridPageWindowsResult;
    @FXML Label BoxGridPageFillInsResult;
    @FXML Label BoxBrandPageResult;
    @FXML Label BoxLucenePageResult;
    @FXML Label BoxMainMenuOnHomePageResult;
    @FXML Label BoxDetailPageResult;
    @FXML Label BoxImageGroupingResult;
    @FXML Label BoxFavoritePageResult;
    @FXML Label BoxPartnershopPageResult;
    @FXML Label BoxBecomePartnerResult;
    @FXML Label BoxAffiliateProgramResult;
    @FXML Label BoxMerchandiseResult;
    @FXML VBox placeForFailedTestCases;
    @FXML Label globalPercentTestPassedCounter;
    @FXML Label globalPassedTestPassedCounter;
    @FXML Label globalSelectedTestPassedCounter;

    private static boolean isSuccessful = false;
    private static boolean isAvailable = false;
    private static String xpathPattern1 = "";
    private static String xpathPattern2 = "";
    private static String xpathPatternImage1 = "";
    private static String xpathPatternImage2 = "";

    @FXML
    public void initialize() {
        logger.info( "Main Program started!" );

        for (countries country : countries.values()){
            countrySelection.getItems().add(country);
        }
        //add Countries to country select
        countrySelection.setPromptText("Which country?");
        //add Listener to Settings
        settingHomepage.setOnAction(event -> updateCheckerTabs());
        settingGridPage.setOnAction(event -> updateCheckerTabs());
        settingGridPageWithWindows.setOnAction(event -> updateCheckerTabs());
        settingGridPageFillIns.setOnAction(event -> updateCheckerTabs());
        settingBrandPage.setOnAction(event -> updateCheckerTabs());
        settingLucenePage.setOnAction(event -> updateCheckerTabs());
        settingMainMenuOnHomePage.setOnAction(event -> updateCheckerTabs());
        settingDetailPage.setOnAction(event -> updateCheckerTabs());
        settingImageGrouping.setOnAction(event -> updateCheckerTabs());
        settingFavoritePage.setOnAction(event -> updateCheckerTabs());
        settingPartnerShopPage.setOnAction(event -> updateCheckerTabs());
        settingBecomeAPartnerPage.setOnAction(event -> updateCheckerTabs());
        settingAffiliateProgram.setOnAction(event -> updateCheckerTabs());
        settingMerchandiseOverviewPage.setOnAction(event -> updateCheckerTabs());
        //Bind Element Inputs to Settings
        ElementLuceneBox.visibleProperty().bind(settingLucenePage.selectedProperty());
        ElementLoginBox.visibleProperty().bind(settingFavoritePage.selectedProperty());
        ElementTextSearchSuggestionBox.visibleProperty().bind(settingHomepage.selectedProperty());
        ElementShopSearchBox.visibleProperty().bind(settingPartnerShopPage.selectedProperty());
        ElementMerchandiseSearchBox.visibleProperty().bind(settingMerchandiseOverviewPage.selectedProperty());
        ElementGridPageSearchBox.visibleProperty().bind(settingGridPage.selectedProperty());
        ElementFiltersBox.visibleProperty().bind(settingGridPage.selectedProperty());
        ElementGridPageWithWindowBox.visibleProperty().bind(settingGridPageWithWindows.selectedProperty());
        ElementGridPageWithFillInsBox.visibleProperty().bind(settingGridPageFillIns.selectedProperty());
        inputAccountEmail.setOnAction(ValidationEvent -> updateCheckerTabs());
        infoInputFieldTextSearch.visibleProperty().bind(ElementTextSearchSuggestionBox.visibleProperty());

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
        //Binding Values to Selection
        countrySelection.setOnAction( selectedEvent -> {
            inputSearch.setText(countries.valueOf(countrySelection.getSelectionModel().getSelectedItem().toString()).getLocationMainPage());
            inputImprintURL.setText(countries.valueOf(countrySelection.getSelectionModel().getSelectedItem().toString()).getlocationImprintPage());
            inputPrivacyPolicy.setText(countries.valueOf(countrySelection.getSelectionModel().getSelectedItem().toString()).getPrivacyPage());
            inputBrandPageOverview.setText(countries.valueOf(countrySelection.getSelectionModel().getSelectedItem().toString()).getLocationBrandOverviewPage());
            inputPartnerShopPageURL.setText(countries.valueOf(countrySelection.getSelectionModel().getSelectedItem().toString()).getLocationPartnershopsPageURL());
            inputBecomeAPartnerPageURL.setText(countries.valueOf(countrySelection.getSelectionModel().getSelectedItem().toString()).getLocationBecomePartnerPageURL());
            inputAffiliateProgramURL.setText(countries.valueOf(countrySelection.getSelectionModel().getSelectedItem().toString()).getLocationAffiliateProgramPageURL());
            inputMerchandiseOverviewPageURL.setText(countries.valueOf(countrySelection.getSelectionModel().getSelectedItem().toString()).getLocationMerchandiseOverviewPageURL());
            startwebdriver.setDisable(false);
        });
        //Bind Info to QuestionMark Buttons
        ModalBox modalBox = new ModalBox();
        infoHomepageTest.setOnMouseClicked(event -> modalBox.showDialogTestCases(settingHomepage.getText(),frontendHomepageController.frontendHomePageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]), placeForTooltipSetting));
        infoGridPageTest.setOnMouseClicked(event -> modalBox.showDialogTestCases(settingGridPage.getText(),gridPageNoWindowsController.GridPageNoWindowsCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]), placeForTooltipSetting));
        infoGridPageWindowTest.setOnMouseClicked(event -> modalBox.showDialogTestCases(settingGridPageWithWindows.getText(),gridPageWithWindowsController.gridPageWithWindowsCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]), placeForTooltipSetting));
        infoGridPageFillInsTest.setOnMouseClicked(event -> modalBox.showDialogTestCases(settingGridPageFillIns.getText(),gridPageWithFillInsController.gridPageWithFillInsCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]), placeForTooltipSetting));
        infoBrandTest.setOnMouseClicked(event -> modalBox.showDialogTestCases(settingBrandPage.getText(),brandOverviewController.brandPageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]), placeForTooltipSetting));
        infoLuceneTest.setOnMouseClicked(event -> modalBox.showDialogTestCases(settingLucenePage.getText(),pageLuceneWithItemsController.lucenePageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]), placeForTooltipSetting));
        infoOnMainMenuTest.setOnMouseClicked(event -> modalBox.showDialogTestCases(settingMainMenuOnHomePage.getText(),mainMenuOnHomePageController.mainMenuCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]), placeForTooltipSetting));
        infoDetailPageTest.setOnMouseClicked(event -> modalBox.showDialogTestCases(settingDetailPage.getText(),detailPageController.detailPageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]), placeForTooltipSetting));
        infoImageGroupingTest.setOnMouseClicked(event -> modalBox.showDialogTestCases(settingImageGrouping.getText(),imageGroupingController.imageGroupingCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]), placeForTooltipSetting));
        infoFavoritePageTest.setOnMouseClicked(event -> modalBox.showDialogTestCases(settingFavoritePage.getText(),favoritePageController.favoritePageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]), placeForTooltipSetting));
        infoPartnerShopPageTest.setOnMouseClicked(event -> modalBox.showDialogTestCases(settingPartnerShopPage.getText(),partnershopsPageController.partnerShopCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]), placeForTooltipSetting));
        infoBecomeAPartnerPageTest.setOnMouseClicked(event -> modalBox.showDialogTestCases(settingBecomeAPartnerPage.getText(),becomeAPartnerController.becomePartnerCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]), placeForTooltipSetting));
        infoAffiliateTest.setOnMouseClicked(event -> modalBox.showDialogTestCases(settingAffiliateProgram.getText(),affiliateProgramController.affiliateProgramCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]), placeForTooltipSetting));
        infoMerchandiseTest.setOnMouseClicked(event -> modalBox.showDialogTestCases(settingMerchandiseOverviewPage.getText(),merchandiseOverviewPageController.merchandiseOverviewCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]), placeForTooltipSetting));
        infoInputFieldTextSearch.setOnMouseClicked(event -> modalBox.showDialogInputFieldValidation(InfoText.valueOf("TextSearch").getHeaderMessage(),InfoText.valueOf("TextSearch").getMainMessage(), placeForTooltipInput));
        // Bind validation to Input Fields
        inputGridPageURL.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                validateInputAttributesAndShowColor();
            }
        });
        //set Start Button to disable, first Country has to be selected
        startwebdriver.setDisable(true);
        stopWebdriver.setDisable(true);
        //update all Boxes before Starting
        updateResultsBoxes();
        updateCheckerTabs();
    }

    @FXML
    public void checkBeforeStart() {
        if (validateInputAttributes()){
            Platform.runLater(this::startRealAction);
        }
    }

    @FXML
    public void startRealAction() {
        disableAllInputButtons();

        Task task = new Task<Object>() {
            @Override
            protected Void call() {
                removeOpenReportFromProgram();
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
                Platform.runLater(() -> {
                    placeForFailedTestCases.getChildren().clear();
                    statusInfo.setText("Starting Engine...");
                });
                System.setProperty("webdriver.chrome.driver", "temp//chromedriver.exe");
                ChromeOptions option = new ChromeOptions();
                //option.addArguments("--window-size=1920,1080");
                //option.addArguments("--headless");
                option.addArguments("--disable-gpu");
                option.addArguments("--disable-infobars");
                option.addArguments("--disable-notifications");
                option.addArguments("--start-maximized");
                ChromeDriver webDriver = new ChromeDriver(option);
                try{
                    stopWebdriver.setDisable(false);
                    stopWebdriver.setOnAction(event -> webDriver.close());
                    JavascriptExecutor js = webDriver;
                    Report report = new Report();
                    report.clearWrittenReport();
                    ScreenshotViaWebDriver.clearWrittenScreenshots();
                    ResultsManager resultsManager = new ResultsManager();
                    SettingManager settingManager = new SettingManager();

                    // open Startpage and set window
                    Platform.runLater(() -> {
                        Window window = mainStage.getScene().getWindow();
                        window.requestFocus();
                        statusInfo.setText("Open Maximize Mode...");
                    });
                    //webDriver.manage().window().maximize();
                    //webDriver.manage().window().setPosition(new Point(-2000, 0));   minimize function doesn't allow Javascript ViewPort
                    Platform.runLater(() -> statusInfo.setText("Go to requested Website..."));
                    long start = System.currentTimeMillis();
                    webDriver.navigate().to(inputSearch.getText().trim());
                    long finish = System.currentTimeMillis();
                    long totalTime = finish - start;
                    logger.info("Total Time for page load - "+totalTime);
                    report.writeToFile("Checking Website: ", inputSearch.getText().trim());
                    report.writeToFile("=================================", "");
                    if (!tabHomepage.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabHomepage);
                            settingManager.updateResultBoxes(settingHomepage,"progress",resultBoxHomepage,BoxHomepageResult);
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
                            //VisualResults visualResults = new VisualResults();
                            //Platform.runLater(() -> visualResults.createPieChart(PlaceForPieCharts,frontendHomepageController.frontendHomePageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]),"HomepageTest"));
                        }catch (Exception noHomePageWorking){
                            noHomePageWorking.printStackTrace();
                        }
                    }
                    Platform.runLater(() ->{
                        updateGlobalTestCases();
                        resultsManager.updateResultsCheckbox(settingHomepage,frontendHomepageController.frontendHomePageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]),BoxHomepageResult,placeForFailedTestCases);
                        settingManager.updateResultBoxes(settingHomepage,"complete",resultBoxHomepage,BoxHomepageResult);
                    });
                    if (!tabGridPage.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabGridPage);
                            settingManager.updateResultBoxes(settingGridPage,"progress",resultBoxGridPage,BoxGridPageResult);
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
                            //VisualResults visualResults = new VisualResults();
                            //Platform.runLater(() -> visualResults.createPieChart(PlaceForPieCharts,gridPageNoWindowsController.GridPageNoWindowsCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]),"GridPageTest"));

                        }catch (Exception noGridPageWorking){
                            noGridPageWorking.printStackTrace();
                        }
                    }
                    Platform.runLater(() -> {
                        updateGlobalTestCases();
                        resultsManager.updateResultsCheckbox(settingGridPage,gridPageNoWindowsController.GridPageNoWindowsCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]),BoxGridPageResult,placeForFailedTestCases);
                        settingManager.updateResultBoxes(settingGridPage,"complete",resultBoxGridPage,BoxGridPageResult);
                    } );
                    if (!tabGridPageWithWindows.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabGridPageWithWindows);
                            settingManager.updateResultBoxes(settingGridPageWithWindows,"progress",resultBoxGridPageWindows,BoxGridPageWindowsResult);
                            GridPageTestWithWindows gridPageTestWithWindows = new GridPageTestWithWindows();
                            gridPageTestWithWindows.checkingPagingWithWindowsForward(webDriver,report,js,gridPageWithWindowsController.PagingWithWindowsForward,inputGridPageURLWithWindows,statusInfo,inputSearch,inputEmailAdress,xpathPattern1,xpathPattern2,Homepage,isSuccessful,isAvailable);
                        }catch (Exception noGridPagWindowsWorking){
                            noGridPagWindowsWorking.printStackTrace();
                        }
                    }
                    Platform.runLater(() -> {
                        updateGlobalTestCases();
                        resultsManager.updateResultsCheckbox(settingGridPageWithWindows,gridPageWithWindowsController.gridPageWithWindowsCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]),BoxGridPageWindowsResult,placeForFailedTestCases);
                        settingManager.updateResultBoxes(settingGridPageWithWindows,"complete",resultBoxGridPageWindows,BoxGridPageWindowsResult);
                    } );
                    if (!tabGridPageFillIns.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabGridPageFillIns);
                            settingManager.updateResultBoxes(settingGridPageFillIns,"progress",resultBoxGridPageFillIns,BoxGridPageFillInsResult);
                            GridPageTestWithFillIns gridPageTestWithFillIns = new GridPageTestWithFillIns();
                            gridPageTestWithFillIns.checkingShowAllFillInPage(webDriver,report,js,gridPageWithFillInsController.showAllFillInPage,inputGridPageURLWithFillIns,statusInfo,inputSearch, Homepage);
                        }catch (Exception noGridPageWorking){
                            noGridPageWorking.printStackTrace();
                        }
                    }
                    Platform.runLater(() -> {
                        updateGlobalTestCases();
                        resultsManager.updateResultsCheckbox(settingGridPageFillIns,gridPageWithFillInsController.gridPageWithFillInsCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]),BoxGridPageFillInsResult,placeForFailedTestCases);
                        settingManager.updateResultBoxes(settingGridPageFillIns,"complete",resultBoxGridPageFillIns,BoxGridPageFillInsResult);
                    } );
                    if (!tabBrandPage.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabBrandPage);
                            settingManager.updateResultBoxes(settingBrandPage,"progress",resultBoxBrandPage,BoxBrandPageResult);
                            BrandPageTest brandPageTest = new BrandPageTest();
                            brandPageTest.checkingBrandsWithoutLogo(webDriver,report,js,brandOverviewController.brandsWithoutLogo,inputBrandPageOverview,statusInfo,inputSearch, Homepage);
                        }catch (Exception noBrandWorking){
                            noBrandWorking.printStackTrace();
                        }
                    }
                    Platform.runLater(() -> {
                        updateGlobalTestCases();
                        resultsManager.updateResultsCheckbox(settingBrandPage,brandOverviewController.brandPageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]),BoxBrandPageResult,placeForFailedTestCases);
                        settingManager.updateResultBoxes(settingBrandPage,"complete",resultBoxBrandPage,BoxBrandPageResult);
                    } );
                    if (!tabLucenePage.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabLucenePage);
                            settingManager.updateResultBoxes(settingLucenePage,"progress",resultBoxLucenePage,BoxLucenePageResult);
                            PageLuceneWithItemsTest pageLuceneWithItemsTest = new PageLuceneWithItemsTest();
                            pageLuceneWithItemsTest.checkingSorting(webDriver,report,js,pageLuceneWithItemsController.PageLuceneWithItemsSorting,inputLucenePage,statusInfo,inputSearch, Homepage);
                            pageLuceneWithItemsTest.checkingCollapse(webDriver,report,js,pageLuceneWithItemsController.PageLuceneWithItemsCollapse,inputLucenePage,statusInfo,inputSearch, Homepage);
                            pageLuceneWithItemsTest.checkingMultiselect(webDriver,report,js,pageLuceneWithItemsController.PageLuceneWithItemsMultiSelect,inputLucenePage,statusInfo,inputSearch, Homepage);
                        }catch (Exception noLucenePageWorking){
                            noLucenePageWorking.printStackTrace();
                        }
                    }
                    Platform.runLater(() -> {
                        updateGlobalTestCases();
                        resultsManager.updateResultsCheckbox(settingLucenePage,pageLuceneWithItemsController.lucenePageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]),BoxLucenePageResult,placeForFailedTestCases);
                        settingManager.updateResultBoxes(settingLucenePage,"complete",resultBoxLucenePage,BoxLucenePageResult);
                    } );
                    if (!tabMainMenuOnHomePage.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabMainMenuOnHomePage);
                            settingManager.updateResultBoxes(settingMainMenuOnHomePage,"progress",resultBoxMainMenuOnHomePage,BoxMainMenuOnHomePageResult);
                            MainMenuOnHomePageTest mainMenuOnHomePageTest = new MainMenuOnHomePageTest();
                            mainMenuOnHomePageTest.checkingMainMenuTabs(webDriver,report,js,mainMenuOnHomePageController.MainMenuLinkTabs,statusInfo,inputSearch, Homepage);
                            //mainMenuOnHomePageTest.checkingMainMenuIndex(webDriver,report,js,mainMenuOnHomePageController.MainMenuLinkIndex,statusInfo,inputSearch, Homepage);
                            mainMenuOnHomePageTest.checkingShoppingWorld(webDriver,report,js,mainMenuOnHomePageController.ShoppingWorlds,statusInfo,inputSearch, Homepage);
                            mainMenuOnHomePageTest.checkingShoppingWorldOnIndex(webDriver,report,js,mainMenuOnHomePageController.ShoppingWorldsOnIndex,statusInfo,inputSearch, Homepage);
                        }catch (Exception noMainMenuWorking){
                            noMainMenuWorking.printStackTrace();
                        }
                    }
                    Platform.runLater(() -> {
                        updateGlobalTestCases();
                        resultsManager.updateResultsCheckbox(settingMainMenuOnHomePage,mainMenuOnHomePageController.mainMenuCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]),BoxMainMenuOnHomePageResult,placeForFailedTestCases);
                        settingManager.updateResultBoxes(settingMainMenuOnHomePage,"complete",resultBoxMainMenuOnHomePage,BoxMainMenuOnHomePageResult);
                    } );
                    if (!tabDetailPage.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabDetailPage);
                            settingManager.updateResultBoxes(settingDetailPage,"progress",resultBoxDetailPage,BoxDetailPageResult);
                            DetailPageTest detailPageTest = new DetailPageTest();
                            detailPageTest.checkingSwitchTabsinDetailPage(webDriver,report,js,detailPageController.SwitchTabsInDetailPage, statusInfo,inputGridPageURL, Homepage);
                            detailPageTest.checkingSimilarProductClickOut(webDriver,report,js,detailPageController.SimilarProductsClickOut, statusInfo,inputGridPageURL, Homepage);
                            detailPageTest.checkingPagingForwardBackward(webDriver,report,js,detailPageController.PagingForwardBackward, statusInfo,inputGridPageURL, Homepage);
                            detailPageTest.checkingJumpToNonExistingPage(webDriver,report,js,detailPageController.JumpToNonExistingPage, statusInfo,inputGridPageURL, Homepage);
                        }catch (Exception noDetailPageWorking){
                            noDetailPageWorking.printStackTrace();
                        }
                    }
                    Platform.runLater(() -> {
                        updateGlobalTestCases();
                        resultsManager.updateResultsCheckbox(settingDetailPage,detailPageController.detailPageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]),BoxDetailPageResult,placeForFailedTestCases);
                        settingManager.updateResultBoxes(settingDetailPage,"complete",resultBoxDetailPage,BoxDetailPageResult);
                    } );
                    if (!tabImageGrouping.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabImageGrouping);
                            settingManager.updateResultBoxes(settingImageGrouping,"progress",resultBoxImageGrouping,BoxImageGroupingResult);
                            ImageGroupingPageTest imageGroupingPageTest = new ImageGroupingPageTest();
                            imageGroupingPageTest.checkingImageGroupingClickOut(webDriver,report,js,imageGroupingController.ImageGroupingClickOut, statusInfo,inputGridPageURL, Homepage);
                            imageGroupingPageTest.checkingDetailPageOfOffer(webDriver,report,js,imageGroupingController.DetailPageOfOffer, statusInfo,inputGridPageURL, Homepage);
                        }catch (Exception noLucenePageWorking){
                            noLucenePageWorking.printStackTrace();
                        }
                    }
                    Platform.runLater(() -> {
                        updateGlobalTestCases();
                        resultsManager.updateResultsCheckbox(settingImageGrouping,imageGroupingController.imageGroupingCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]),BoxImageGroupingResult,placeForFailedTestCases);
                        settingManager.updateResultBoxes(settingImageGrouping,"complete",resultBoxImageGrouping,BoxImageGroupingResult);
                    } );
                    if (!tabFavoritePage.isDisable() & inputAccountEmail.getText() != null & !inputAccountEmail.getText().equals("") & inputAccountEmail.getText().toLowerCase().contains("@visual-meta.com") ){
                        try{
                            tabPane.getSelectionModel().select(tabFavoritePage);
                            settingManager.updateResultBoxes(settingFavoritePage,"progress",resultBoxFavoritePage,BoxFavoritePageResult);
                            FavoritePageTest favoritePageTest = new FavoritePageTest();
                            favoritePageTest.checkingPersonalListTest(webDriver,report,js,favoritePageController.PersonalList, statusInfo,inputSearch, Homepage, inputAccountEmail, inputAccountPassword);
                            favoritePageTest.checkingApplySortingOnList(webDriver,report,js,favoritePageController.SortingOnList,statusInfo,inputGridPageURL, Homepage, inputAccountEmail, inputAccountPassword);
                            favoritePageTest.checkingSelectionOnList(webDriver,report,js,favoritePageController.SelectionOnList,statusInfo,inputSearch, Homepage, inputAccountEmail, inputAccountPassword);
                        }catch (Exception noFavoritePageWorking){
                            noFavoritePageWorking.printStackTrace();
                        }
                    }
                    Platform.runLater(() -> {
                        updateGlobalTestCases();
                        resultsManager.updateResultsCheckbox(settingFavoritePage,favoritePageController.favoritePageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]),BoxFavoritePageResult,placeForFailedTestCases);
                        settingManager.updateResultBoxes(settingFavoritePage,"complete",resultBoxFavoritePage,BoxFavoritePageResult);
                    } );
                    if (!tabPartnerShopPage.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabPartnerShopPage);
                            settingManager.updateResultBoxes(settingPartnerShopPage,"progress",resultBoxPartnershopPage,BoxPartnershopPageResult);
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
                    Platform.runLater(() -> {
                        updateGlobalTestCases();
                        resultsManager.updateResultsCheckbox(settingPartnerShopPage,partnershopsPageController.partnerShopCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]),BoxPartnershopPageResult,placeForFailedTestCases);
                        settingManager.updateResultBoxes(settingPartnerShopPage,"complete",resultBoxPartnershopPage,BoxPartnershopPageResult);
                    } );
                    if (!tabBecomeAPartnerPage.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabBecomeAPartnerPage);
                            settingManager.updateResultBoxes(settingBecomeAPartnerPage,"progress",resultBoxBecomePartner,BoxBecomePartnerResult);
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
                    Platform.runLater(() -> {
                        updateGlobalTestCases();
                        resultsManager.updateResultsCheckbox(settingBecomeAPartnerPage,becomeAPartnerController.becomePartnerCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]),BoxBecomePartnerResult,placeForFailedTestCases);
                        settingManager.updateResultBoxes(settingBecomeAPartnerPage,"complete",resultBoxBecomePartner,BoxBecomePartnerResult);
                    } );
                    if (!tabAffiliateProgram.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabAffiliateProgram);
                            settingManager.updateResultBoxes(settingAffiliateProgram,"progress",resultBoxAffiliateProgram,BoxAffiliateProgramResult);
                            AffiliateProgramTest affiliateProgramTest = new AffiliateProgramTest();
                            affiliateProgramTest.checkingBecomeAffilinetPartner(webDriver,report,js,affiliateProgramController.BecomeAffilinetPartner,statusInfo,inputAffiliateProgramURL,inputSearch,Homepage);
                            affiliateProgramTest.checkingBecomeTradeTrackerPartner(webDriver,report,js,affiliateProgramController.BecomeTradeTrackerPartner,statusInfo,inputAffiliateProgramURL,inputSearch,Homepage);
                        }catch (Exception noBecomeAPartnerWorking){
                            noBecomeAPartnerWorking.printStackTrace();
                        }
                    }
                    Platform.runLater(() -> {
                        updateGlobalTestCases();
                        resultsManager.updateResultsCheckbox(settingAffiliateProgram,affiliateProgramController.affiliateProgramCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]),BoxAffiliateProgramResult,placeForFailedTestCases);
                        settingManager.updateResultBoxes(settingAffiliateProgram,"complete",resultBoxAffiliateProgram,BoxAffiliateProgramResult);
                    } );
                    if (!tabMerchandiseOverviewPage.isDisable()){
                        try{
                            tabPane.getSelectionModel().select(tabMerchandiseOverviewPage);
                            settingManager.updateResultBoxes(settingMerchandiseOverviewPage,"progress",resultBoxMerchandise,BoxMerchandiseResult);
                            MerchandiseOverviewPageTest merchandiseOverviewPageTest = new MerchandiseOverviewPageTest();
                            merchandiseOverviewPageTest.checkingMerchandiseLetters(webDriver,report,js,merchandiseOverviewPageController.LettertoMerchandise,statusInfo,inputMerchandiseOverviewPageURL, Homepage);
                            merchandiseOverviewPageTest.checkingMerchandiseName(webDriver,report,js,merchandiseOverviewPageController.MerchandiseName,statusInfo,inputMerchandiseOverviewPageURL, Homepage);
                            merchandiseOverviewPageTest.checkingMerchandiseSearch(webDriver,report,js,merchandiseOverviewPageController.MerchandiseSearch,statusInfo,inputMerchandiseOverviewPageURL,inputMerchandiseSearch, Homepage);
                            merchandiseOverviewPageTest.checkingMerchandiseGoToTop(webDriver,report,js,merchandiseOverviewPageController.GoToTop,statusInfo,inputMerchandiseOverviewPageURL, Homepage);
                        }catch (Exception noBecomeAPartnerWorking){
                            noBecomeAPartnerWorking.printStackTrace();
                        }
                    }
                    Platform.runLater(() -> {
                        updateGlobalTestCases();
                        resultsManager.updateResultsCheckbox(settingMerchandiseOverviewPage,merchandiseOverviewPageController.merchandiseOverviewCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]),BoxMerchandiseResult,placeForFailedTestCases);
                        settingManager.updateResultBoxes(settingMerchandiseOverviewPage,"complete",resultBoxMerchandise,BoxMerchandiseResult);
                        updateGlobalPercentTestCounter();
                    } );


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
            link.setStyle("-fx-font: 18 arial;");
            link.setTextAlignment(TextAlignment.CENTER);
            outputPlace.getChildren().addAll(link);
            progressIndicator.setProgress(100);
            enableAllInputButtons();
            logger.info("All process are finished");
        }));
    }
    //Just for Themeweek! Will be removed once the Themeweek is over and no one need it anymore
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
                enableAllInputButtons();
                logger.info("All process to check are finished");
            }));

        }

    }

    @FXML
    private void disableAllInputButtons(){
        Platform.runLater(() -> {
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
            //settingTitledPane.setExpanded(false);  no need to expand any Setting for now
        });
    }

    @FXML
    private void enableAllInputButtons() {
        Platform.runLater(() -> {
            statusInfo.setText("");
            startwebdriver.setDisable(false);
            stopWebdriver.setDisable(true);
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
        });
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
    private void removeOpenReportFromProgram() {
        Platform.runLater(() -> outputPlace.getChildren().clear());
    }

    @FXML
    public void selectAllSettingCheckBoxes(){
        settingHomepage.setSelected(true);
        settingGridPage.setSelected(true);
        settingGridPageWithWindows.setSelected(true);
        settingGridPageFillIns.setSelected(true);
        settingLucenePage.setSelected(true);
        settingMainMenuOnHomePage.setSelected(true);
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
        settingMainMenuOnHomePage.setSelected(false);
        settingDetailPage.setSelected(false);
        settingImageGrouping.setSelected(false);
        settingFavoritePage.setSelected(false);
        settingPartnerShopPage.setSelected(false);
        settingBecomeAPartnerPage.setSelected(false);
        settingAffiliateProgram.setSelected(false);
        settingMerchandiseOverviewPage.setSelected(false);
        updateCheckerTabs();
    }
    private boolean validateInputAttributes(){
        //String successStyleSettings = null;
        boolean webDriverCanStart = false;

        //no inputfield should be empty
        //for filter test at least one filer needs to be checked
        //if GridPageURL+Windiws+FillIns contains not the selected Counry then no
        //email visual meta cntains
        boolean isEverythingFilledCorrectly = true;
        StringBuilder ValidationsErrors = new StringBuilder();
        if (settingHomepage.isSelected()){
            if (inputTextSearchAndSuggestions.getText().length() < 1){
                ValidationsErrors.append("- the inputTextSearchAndSuggestions cannot be empty\n");
            }
        }
        if (settingGridPage.isSelected()){
            int amountOfSelectedFilters = 0;
            if (checkingSalesPriceFilter.isSelected()){
                ++amountOfSelectedFilters;
            }
            if (checkingGenderFilter.isSelected()){
                ++amountOfSelectedFilters;
            }
            if (checkingColorFilter.isSelected()){
                ++amountOfSelectedFilters;
            }
            if (checkingBrandFilter.isSelected()){
                ++amountOfSelectedFilters;
            }
            if (checkingMerchandiseFilter.isSelected()){
                ++amountOfSelectedFilters;
            }
            if (amountOfSelectedFilters < 1){
                ValidationsErrors.append("- at least one the Filter must be selected\n");
            }
            if (inputGridPageKeyword.getText().length() < 1){
                ValidationsErrors.append("- the inputGridPageKeyword cannot be empty\n");
            }
        }
        if (settingGridPage.isSelected() | settingImageGrouping.isSelected() | settingDetailPage.isSelected()| settingFavoritePage.isSelected()){
            if (inputGridPageURL.getText().length() < 1) {
                ValidationsErrors.append("- the inputGridPageURL cannot be empty\n");
            }
            if (!inputGridPageURL.getText().contains(countries.valueOf(countrySelection.getSelectionModel().getSelectedItem().toString()).getLocationMainPage()) & !inputGridPageURL.getText().equals("")){
                ValidationsErrors.append("- the inputGridPageURL cannot be unrelated to selected Country\n");
            }
        }
        if (settingGridPageWithWindows.isSelected()) {
            if (inputGridPageURLWithWindows.getText().length() < 1) {
                ValidationsErrors.append("- the inputGridPageURLWithWindows cannot be empty\n");
            }
            if (!inputGridPageURLWithWindows.getText().contains(countries.valueOf(countrySelection.getSelectionModel().getSelectedItem().toString()).getLocationMainPage()) & !inputGridPageURLWithWindows.getText().equals("")) {
                ValidationsErrors.append("- the inputGridPageURLWithWindows cannot be unrelated to selected Country\n");
            }
        }
        if (settingGridPageFillIns.isSelected()) {
            if (inputGridPageURLWithFillIns.getText().length() < 1) {
                ValidationsErrors.append("- the inputGridPageURLWithFillIns cannot be empty\n");
            }
        }
        if (settingLucenePage.isSelected()){
            if (inputLucenePage.getText().length() < 1){
                ValidationsErrors.append("- the inputLucenePage cannot be empty\n");
            }
        }
        if (settingPartnerShopPage.isSelected()){
            if (inputPartnerShopSearch.getText().length() < 1){
                ValidationsErrors.append("- the inputPartnerShopSearch cannot be empty\n");
            }
        }
        if (settingMerchandiseOverviewPage.isSelected()){
            if (inputMerchandiseSearch.getText().length() < 1){
                ValidationsErrors.append("- the inputMerchandiseSearch cannot be empty\n");
            }
        }
        if (settingFavoritePage.isSelected()){
            if (inputAccountEmail.getText().length() < 1){
                ValidationsErrors.append("- the inputAccountEmail cannot be empty\n");
            }
            if (!inputAccountEmail.getText().equals("") & !inputAccountEmail.getText().toLowerCase().contains("@visual-meta.com")){
                ValidationsErrors.append("- the inputAccountEmail cannot be unrelated to Company\n");
            }
        }
        logger.info(ValidationsErrors);
        if (ValidationsErrors.length() > 0 ){
            isEverythingFilledCorrectly = false;
        }
        if (!isEverythingFilledCorrectly){
            ModalBox ErrorInputFields = new ModalBox();
            ErrorInputFields.showDialogInputFieldValidation("Validation Error",ValidationsErrors.toString(),placeForTooltipInput);
            validateInputAttributesAndShowColor();
        }
        return isEverythingFilledCorrectly;
    }
    private void validateInputAttributesAndShowColor(){
        if (countrySelection.getValue() != null){
            String failureStyleSettings = "-fx-border-style: none solid solid solid; -fx-border-width: 3; -fx-border-color:  #e83062;";
            //String successStyleSettings = "-fx-border-style: none none none none; -fx-border-width: 1; -fx-border-color: green;";
            String successStyleSettings = null;
            if (settingHomepage.isSelected()){
                if (inputTextSearchAndSuggestions.getText().length() < 1){
                    inputTextSearchAndSuggestions.setStyle(failureStyleSettings);
                }else{
                    inputTextSearchAndSuggestions.setStyle(successStyleSettings);
                }
            }
            if (settingGridPage.isSelected()){
                int amountOfSelectedFilters = 0;
                if (checkingSalesPriceFilter.isSelected()){++amountOfSelectedFilters;}
                if (checkingGenderFilter.isSelected()){++amountOfSelectedFilters;}
                if (checkingColorFilter.isSelected()){++amountOfSelectedFilters;}
                if (checkingBrandFilter.isSelected()){++amountOfSelectedFilters;}
                if (checkingMerchandiseFilter.isSelected()){++amountOfSelectedFilters;}
                if (amountOfSelectedFilters < 1){
                    ElementFiltersBox.setStyle(failureStyleSettings);
                }else{
                    ElementFiltersBox.setStyle(successStyleSettings);
                }
                if (inputGridPageKeyword.getText().length() < 1){
                    inputGridPageKeyword.setStyle(failureStyleSettings);
                }else{
                    inputGridPageKeyword.setStyle(successStyleSettings);
                }
            }
            if (settingGridPage.isSelected() | settingImageGrouping.isSelected() | settingDetailPage.isSelected()| settingFavoritePage.isSelected()){
                if (inputGridPageURL.getText().length() < 1) {
                    inputGridPageURL.setStyle(failureStyleSettings);
                }else{
                    inputGridPageURL.setStyle(successStyleSettings);
                }
                if (!inputGridPageURL.getText().contains(countries.valueOf(countrySelection.getSelectionModel().getSelectedItem().toString()).getLocationMainPage()) & !inputGridPageURL.getText().equals("")){
                    inputGridPageURL.setStyle(failureStyleSettings);
                }else{
                    inputGridPageURL.setStyle(successStyleSettings);
                }
            }else{
                inputGridPageURL.setStyle(successStyleSettings);
            }
            if (settingGridPageWithWindows.isSelected()) {
                if (inputGridPageURLWithWindows.getText().length() < 1) {
                    inputGridPageURLWithWindows.setStyle(failureStyleSettings);
                }else{
                    inputGridPageURLWithWindows.setStyle(successStyleSettings);
                }
                if (!inputGridPageURLWithWindows.getText().contains(countries.valueOf(countrySelection.getSelectionModel().getSelectedItem().toString()).getLocationMainPage()) & !inputGridPageURLWithWindows.getText().equals("")) {
                    inputGridPageURLWithWindows.setStyle(failureStyleSettings);
                }else{
                    inputGridPageURLWithWindows.setStyle(successStyleSettings);
                }
            }
            if (settingGridPageFillIns.isSelected()) {
                if (inputGridPageURLWithFillIns.getText().length() < 1) {
                    inputGridPageURLWithFillIns.setStyle(failureStyleSettings);
                }else{
                    inputGridPageURLWithFillIns.setStyle(successStyleSettings);
                }
            }
            if (settingLucenePage.isSelected()){
                if (inputLucenePage.getText().length() < 1){
                    inputLucenePage.setStyle(failureStyleSettings);
                }else{
                    inputLucenePage.setStyle(successStyleSettings);
                }
            }
            if (settingPartnerShopPage.isSelected()){
                if (inputPartnerShopSearch.getText().length() < 1){
                    inputPartnerShopSearch.setStyle(failureStyleSettings);
                }else{
                    inputPartnerShopSearch.setStyle(successStyleSettings);
                }
            }
            if (settingMerchandiseOverviewPage.isSelected()){
                if (inputMerchandiseSearch.getText().length() < 1){
                    inputMerchandiseSearch.setStyle(failureStyleSettings);
                }else{
                    inputMerchandiseSearch.setStyle(successStyleSettings);
                }
            }
            if (settingFavoritePage.isSelected()){
                if (inputAccountEmail.getText().length() < 1 | !inputAccountEmail.getText().equals("") & !inputAccountEmail.getText().toLowerCase().contains("@visual-meta.com")){
                    inputAccountEmail.setStyle(failureStyleSettings);
                }else{
                    inputAccountEmail.setStyle(successStyleSettings);
                }
            }
        }
    }
    public void updateCheckerTabs(){
        SettingManager settingManager = new SettingManager();
        settingManager.updateSettingControlls(settingHomepage,tabHomepage);
        settingManager.updateSettingControlls(settingGridPage,tabGridPage);
        settingManager.updateSettingControlls(settingGridPageWithWindows,tabGridPageWithWindows);
        settingManager.updateSettingControlls(settingGridPageFillIns,tabGridPageFillIns);
        settingManager.updateSettingControlls(settingBrandPage,tabBrandPage);
        settingManager.updateSettingControlls(settingLucenePage,tabLucenePage);
        settingManager.updateSettingControlls(settingMainMenuOnHomePage, tabMainMenuOnHomePage);
        settingManager.updateSettingControlls(settingDetailPage,tabDetailPage);
        settingManager.updateSettingControlls(settingImageGrouping,tabImageGrouping);
        settingManager.updateSettingControlls(settingFavoritePage,tabFavoritePage);
        settingManager.updateSettingControlls(settingPartnerShopPage,tabPartnerShopPage);
        settingManager.updateSettingControlls(settingBecomeAPartnerPage,tabBecomeAPartnerPage);
        settingManager.updateSettingControlls(settingAffiliateProgram,tabAffiliateProgram);
        settingManager.updateSettingControlls(settingMerchandiseOverviewPage,tabMerchandiseOverviewPage);
        if (settingGridPage.isSelected() | settingImageGrouping.isSelected() | settingDetailPage.isSelected()| settingFavoritePage.isSelected()){
            ElementGridPageWithoutWindowBox.setVisible(true);
        }else{
            ElementGridPageWithoutWindowBox.setVisible(false);
        }
        updateTestCasesGlobalCounter();
        settingManager.setValidationColor(settingFavoritePage,inputAccountEmail);
    }

    public void updateTestCasesGlobalCounter(){
        globalCounterTestCases.setText(""+ getGlobalTestCaseNumber());
    }
    public void updateGlobalPercentTestCounter(){
        int currentValue = getGlobalPassedTestCases();
        int MaxValue = getGlobalTestCaseNumber();
        if (0 == currentValue){
            globalPercentTestPassedCounter.setText(""+0);
        }else{
            int percent = (currentValue * 100) / MaxValue;
            globalPercentTestPassedCounter.setText(""+percent);
        }
    }
    public int getGlobalTestCaseNumber(){
        int globalCounterTestCasesNumber = 0;
        ResultsManager resultsManager = new ResultsManager();
        globalCounterTestCasesNumber += resultsManager.getTestCasesNumber(settingHomepage,frontendHomepageController.frontendHomePageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalCounterTestCasesNumber += resultsManager.getTestCasesNumber(settingGridPage,gridPageNoWindowsController.GridPageNoWindowsCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalCounterTestCasesNumber += resultsManager.getTestCasesNumber(settingGridPageWithWindows,gridPageWithWindowsController.gridPageWithWindowsCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalCounterTestCasesNumber += resultsManager.getTestCasesNumber(settingGridPageFillIns,gridPageWithFillInsController.gridPageWithFillInsCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalCounterTestCasesNumber += resultsManager.getTestCasesNumber(settingBrandPage,brandOverviewController.brandPageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalCounterTestCasesNumber += resultsManager.getTestCasesNumber(settingLucenePage,pageLuceneWithItemsController.lucenePageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalCounterTestCasesNumber += resultsManager.getTestCasesNumber(settingMainMenuOnHomePage,mainMenuOnHomePageController.mainMenuCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalCounterTestCasesNumber += resultsManager.getTestCasesNumber(settingDetailPage,detailPageController.detailPageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalCounterTestCasesNumber += resultsManager.getTestCasesNumber(settingImageGrouping,imageGroupingController.imageGroupingCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalCounterTestCasesNumber += resultsManager.getTestCasesNumber(settingFavoritePage,favoritePageController.favoritePageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalCounterTestCasesNumber += resultsManager.getTestCasesNumber(settingPartnerShopPage,partnershopsPageController.partnerShopCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalCounterTestCasesNumber += resultsManager.getTestCasesNumber(settingBecomeAPartnerPage,becomeAPartnerController.becomePartnerCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalCounterTestCasesNumber += resultsManager.getTestCasesNumber(settingAffiliateProgram,affiliateProgramController.affiliateProgramCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalCounterTestCasesNumber += resultsManager.getTestCasesNumber(settingMerchandiseOverviewPage,merchandiseOverviewPageController.merchandiseOverviewCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        return globalCounterTestCasesNumber;
    }

    public void updateResultsBoxes(){
        SettingManager settingManager = new SettingManager();
        settingManager.updateResultBoxes(settingHomepage,"",resultBoxHomepage,BoxHomepageResult);
        settingManager.updateResultBoxes(settingGridPage,"",resultBoxGridPage,BoxGridPageResult);
        settingManager.updateResultBoxes(settingGridPageWithWindows,"",resultBoxGridPageWindows,BoxGridPageWindowsResult);
        settingManager.updateResultBoxes(settingGridPageFillIns,"",resultBoxGridPageFillIns,BoxGridPageFillInsResult);
        settingManager.updateResultBoxes(settingBrandPage,"",resultBoxBrandPage,BoxBrandPageResult);
        settingManager.updateResultBoxes(settingLucenePage,"",resultBoxLucenePage,BoxLucenePageResult);
        settingManager.updateResultBoxes(settingMainMenuOnHomePage,"", resultBoxMainMenuOnHomePage, BoxMainMenuOnHomePageResult);
        settingManager.updateResultBoxes(settingDetailPage,"",resultBoxDetailPage,BoxDetailPageResult);
        settingManager.updateResultBoxes(settingImageGrouping,"",resultBoxImageGrouping,BoxImageGroupingResult);
        settingManager.updateResultBoxes(settingFavoritePage,"",resultBoxFavoritePage,BoxFavoritePageResult);
        settingManager.updateResultBoxes(settingPartnerShopPage,"",resultBoxPartnershopPage,BoxPartnershopPageResult);
        settingManager.updateResultBoxes(settingBecomeAPartnerPage,"",resultBoxBecomePartner,BoxBecomePartnerResult);
        settingManager.updateResultBoxes(settingAffiliateProgram,"",resultBoxAffiliateProgram,BoxAffiliateProgramResult);
        settingManager.updateResultBoxes(settingMerchandiseOverviewPage,"",resultBoxMerchandise,BoxMerchandiseResult);
    }
    public void updateGlobalTestCases(){
        globalPassedTestPassedCounter.setText(""+ getGlobalPassedTestCases());
        globalSelectedTestPassedCounter.setText("/"+ getGlobalTestCaseNumber());
    }
    public int getGlobalPassedTestCases(){
        int globalFailedTestCasesNumber = 0;
        ResultsManager resultsManager = new ResultsManager();
        globalFailedTestCasesNumber += resultsManager.getPassedTestCasesNumber(settingHomepage,frontendHomepageController.frontendHomePageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalFailedTestCasesNumber += resultsManager.getPassedTestCasesNumber(settingGridPage,gridPageNoWindowsController.GridPageNoWindowsCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalFailedTestCasesNumber += resultsManager.getPassedTestCasesNumber(settingGridPageWithWindows,gridPageWithWindowsController.gridPageWithWindowsCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalFailedTestCasesNumber += resultsManager.getPassedTestCasesNumber(settingGridPageFillIns,gridPageWithFillInsController.gridPageWithFillInsCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalFailedTestCasesNumber += resultsManager.getPassedTestCasesNumber(settingBrandPage,brandOverviewController.brandPageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalFailedTestCasesNumber += resultsManager.getPassedTestCasesNumber(settingLucenePage,pageLuceneWithItemsController.lucenePageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalFailedTestCasesNumber += resultsManager.getPassedTestCasesNumber(settingMainMenuOnHomePage,mainMenuOnHomePageController.mainMenuCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalFailedTestCasesNumber += resultsManager.getPassedTestCasesNumber(settingDetailPage,detailPageController.detailPageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalFailedTestCasesNumber += resultsManager.getPassedTestCasesNumber(settingImageGrouping,imageGroupingController.imageGroupingCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalFailedTestCasesNumber += resultsManager.getPassedTestCasesNumber(settingFavoritePage,favoritePageController.favoritePageCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalFailedTestCasesNumber += resultsManager.getPassedTestCasesNumber(settingPartnerShopPage,partnershopsPageController.partnerShopCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalFailedTestCasesNumber += resultsManager.getPassedTestCasesNumber(settingBecomeAPartnerPage,becomeAPartnerController.becomePartnerCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalFailedTestCasesNumber += resultsManager.getPassedTestCasesNumber(settingAffiliateProgram,affiliateProgramController.affiliateProgramCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        globalFailedTestCasesNumber += resultsManager.getPassedTestCasesNumber(settingMerchandiseOverviewPage,merchandiseOverviewPageController.merchandiseOverviewCheckBoxCollection.getChildren().toArray(new JFXCheckBox[0]));
        return globalFailedTestCasesNumber;
    }
}