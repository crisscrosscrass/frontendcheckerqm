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
import javafx.stage.Window;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;


import java.net.URL;
import java.util.Properties;

public class MainControllerFrontEndCheck implements Serializable{


    // Basic Settings
    @FXML
    Button startwebdriver;




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
    Accordion settingsAccordion;
    @FXML
    TitledPane settingTitledPane;
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
    @FXML
    JFXCheckBox settingFavoritePage;

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
    TextField inputImprintURL;
    @FXML
    TextField inputTermsOfUseURL;
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
    JFXTextField inputAccountEmail;
    @FXML
    JFXPasswordField inputAccountPassword;
    @FXML
    JFXTextField inputPartnershopsPageURL;
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

    //tab views
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
    @FXML
    Tab tabFavoritePage;


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



    private static boolean isSuccessful = false;
    private static boolean isAvailable = false;
    private static String xpathPattern1 = "";
    private static String xpathPattern2 = "";
    private static String xpathPatternImage1 = "";
    private static String xpathPatternImage2 = "";





    @FXML
    public void initialize() {
        System.out.println("FrontendCheckController launched!");

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

        //update Tabs on Frontend
        updateCheckerTabs();

        //check if Properties File is available if yes, load data into Input Fields
        File configSettings = new File("temp//UserSettings.properties");
        if (!configSettings.exists()) {
            copyUserSettingFiles();
        }

        String location = System.getProperty("user.dir");
        location = location.replace("\\","//");
        location += "//temp//";
        Properties userData = null;
        try{
            FileReader reader = new FileReader(location+"UserSettings.properties");
            userData = new Properties();
            userData.load(reader);
        } catch (FileNotFoundException noFileFound) {
            noFileFound.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    inputGridPageURL.setDisable(true);
                    inputGridPageKeyword.setDisable(true);
                    inputGridPageURLWithWindows.setDisable(true);
                    inputGridPageURLWithFillIns.setDisable(true);
                    inputBrandPageOverview.setDisable(true);
                    inputLucenePage.setDisable(true);
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
                    System.out.println("Webdriver not exist");
                    copyFiles();
                }




                // * Basic Settings while Starting WebDriver
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


                    // open Startpage and set window
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
                        //setting up PieChart

                        HomepageTest homepageTest = new HomepageTest();
                        //homepageTest.checkingCategories(webDriver,report,frontendHomepageController.checkCategoryLinksLeftSideMenu,statusInfo,inputSearch, Homepage);
                        //homepageTest.checkingShopOfTheWeek(webDriver,report,frontendHomepageController.checkLogoFromShopOfTheWeek,statusInfo,inputSearch, Homepage);
                        //homepageTest.checkingShopOfTheWeekCategories(webDriver,report,frontendHomepageController.checkCategoryLinksFromShopOfTheWeek,statusInfo,inputSearch, Homepage);
                        //homepageTest.checkingNewsletterBanner(webDriver,report,frontendHomepageController.checkNewsletterBannerFunctionality,statusInfo,inputSearch,inputEmailAdress, Homepage);
                        //homepageTest.checkingNewsletterPopUp(webDriver,report,frontendHomepageController.checkNewsletterPopUp,statusInfo,inputSearch, Homepage);
                        //homepageTest.checkingNewsletterPopUpFunctionality(webDriver,report,js,frontendHomepageController.checkNewsletterPopUpFunctionality,statusInfo,inputSearch,inputEmailAdress, Homepage);
                        //homepageTest.checkingFooterLinks(webDriver,report, frontendHomepageController.checkFooterLinks,statusInfo,inputSearch, Homepage);
                        //homepageTest.checkingSearchAndSuggestions(webDriver,report, frontendHomepageController.checkTextSearchAndSuggestions,inputTextSearchAndSuggestions,statusInfo,inputSearch, Homepage);
                        //homepageTest.checkingFeedbackPopUp(webDriver,report, frontendHomepageController.checkFeedbackPopUp, statusInfo,inputSearch, Homepage);
                        //homepageTest.checkingPrivacyPopUp(webDriver,report, frontendHomepageController.checkPrivacyPopUp, statusInfo,inputSearch, Homepage);
                        homepageTest.checkingImprint(webDriver,report, frontendHomepageController.checkImprint, statusInfo,inputImprintURL, Homepage);


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
            System.out.println("Process Finished");
        }));
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

    }

    @FXML
    private void copyFiles() {
        CopyFiles bringit = new CopyFiles();
        bringit.copyFileThere();

    }
    @FXML
    private void copyUserSettingFiles() {
        CopyFiles bringit = new CopyFiles();
        bringit.CopyUserSettingsThere();

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
        updateCheckerTabs();
    }


}
