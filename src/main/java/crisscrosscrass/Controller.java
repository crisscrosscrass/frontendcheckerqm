package crisscrosscrass;

import crisscrosscrass.TestCases.FilterButtonCheck;
import crisscrosscrass.TestCases.FilterButtonCheckViaJavaScript;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import org.openqa.selenium.interactions.Actions;

import java.io.*;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Controller {

    @FXML
    Button startwebdriver;
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
    HBox outputPlace;
    @FXML
    ImageView preloaderCat;
    @FXML
    VBox CheckBoxesPlace;
    @FXML
    AnchorPane mainStage;

    private static boolean answer = false;
    private static String xpathPattern = "";


    @FXML
    public void startRealAction() {

        System.out.println("Start Engine...");


        Platform.runLater(() -> {


            progressIndicator.setProgress(-1);
            startwebdriver.setDisable(true);
            Task task = new Task<Object>() {
                @Override
                protected Void call() throws InterruptedException {
                    resetAllFormOptions();
                    takeRoutine();
                    return null;
                }

                private void takeRoutine() throws InterruptedException {
                    String resourceName = "filter.properties";
                    ClassLoader loader = Thread.currentThread().getContextClassLoader();
                    Properties Homepage = new Properties();
                    try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
                        Homepage.load(resourceStream);
                    }catch (Exception nope){
                        nope.getStackTrace();
                    }
                    //System.out.println(props.getProperty("page.main.links"));


                    // * Basic Settings before Starting WebDriver
                    // * Browser, Javascript , etc.
                    Platform.runLater(() -> {
                        statusInfo.setText("Detecting Sources...");
                        //DocFlavor.URL catty = Main.class.getResource("preloaderCat.gif");
                        URL cattyLocation = Main.class.getClassLoader().getResource("preloaderCat.gif");
                        Image catty = new Image(String.valueOf(cattyLocation));
                        preloaderCat.setImage(catty);
                    });


                    String requestedWebsite = inputSearch.getText();

                    File webdriverFile = new File("temp//chromedriver.exe");
                    if (!webdriverFile.exists()) {
                        System.out.println("Webdriver not exist");
                        copyFiles();
                    }

                    Platform.runLater(() -> statusInfo.setText("Starting Engine..."));




                    System.setProperty("webdriver.chrome.driver", "temp//chromedriver.exe");
                    WebDriver webDriver = new ChromeDriver();

                    JavascriptExecutor js = (JavascriptExecutor) webDriver;
                    Report report = new Report();
                    report.clearWrittenReport();


                    // open Startpage and have a basic overview
                    Platform.runLater(() -> {
                        Window window = mainStage.getScene().getWindow();
                        window.requestFocus();
                        statusInfo.setText("Open Maximize Mode...");
                    });

                    webDriver.manage().window().maximize();
                    Platform.runLater(() -> statusInfo.setText("Go to requested Website..."));
                    webDriver.navigate().to(requestedWebsite);
                    report.writeToFile("Checking Website: ", requestedWebsite);


                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");

                    // Click on Logo Test
                    Platform.runLater(() -> {
                        checkLogoHomepage.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Logo...");
                    });


                    try {
                        ((ChromeDriver) webDriver).findElementByXPath(Homepage.getProperty("page.main.logo")).click();

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
                        report.writeToFile("Checking Logo: ", "unable to check!");
                    }

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");


                    //Check general Layout Test
                    Platform.runLater(() -> {
                        checkGeneralLayout.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Layout...");
                    });
                    Thread.sleep(100);





                    // make SCREENSHOT 1 | parameter "webdriver" + "name";
                    answer = ScreenshotViaWebDriver.printScreen(webDriver,"screenshot1.png");
                    if (answer){
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
                    answer = ScreenshotViaWebDriver.printScreen(webDriver,"screenshot2.png");
                    if (answer){
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
                    answer = ScreenshotViaWebDriver.printScreen(webDriver,"screenshot3.png");
                    if (answer){
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
                    answer = ScreenshotViaWebDriver.printScreen(webDriver,"screenshot4.png");
                    if (answer){
                        report.writeToFile("Checking Layout: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("Checking Layout: ", "Screenshot not successful!");
                    }

                    Platform.runLater(() -> {
                        checkGeneralLayout.setStyle("-fx-background-color: #CCFF99");
                        checkGeneralLayout.setSelected(true);
                    });

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");




                    // open Hover Main Menu and have a check on all DeepLinks Test
                    Platform.runLater(() -> {
                        openMainMenu.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Menu...");
                    });

                    Actions hover = new Actions(webDriver);



                    try {
                        List<WebElement> MainMenu = ((ChromeDriver) webDriver).findElementsByXPath(Homepage.getProperty("page.main.links"));
                        int counterInfo = 1;
                        for (WebElement MainMenuItem : MainMenu) {
                            hover.moveToElement(MainMenuItem).perform();
                            //System.out.println("MainMenuLink: "+ MainMenuItem.getText() + " " + counterInfo + MainMenuItem.getAttribute("href"));
                            report.writeToFile("MainMenuLink: "+ counterInfo + " " + MainMenuItem.getText(), MainMenuItem.getAttribute("href"));
                            counterInfo++;
                        }
                        Thread.sleep(100);

                        //checking Sub Main Menu Links
                        Platform.runLater(() -> {
                            statusInfo.setText("Checking SubMenu...");
                        });

                        List<WebElement> MainSubMenu = ((ChromeDriver) webDriver).findElementsByXPath(Homepage.getProperty("page.submain.links"));
                        int counterSubMenu = 1;
                        for (WebElement ItemSubMenu : MainSubMenu) {
                            //System.out.println("MainSubLink: "+ ItemSubMenu.getAttribute("textContent") + " " + ItemSubMenu.getAttribute("href"));
                            report.writeToFile("MainMenuSubLink: "+ counterSubMenu + " " + ItemSubMenu.getAttribute("textContent"), ItemSubMenu.getAttribute("href"));
                            Platform.runLater(() -> {
                                statusInfo.setText(ItemSubMenu.getAttribute("href"));
                            });
                            counterSubMenu++;
                        }

                        report.writeToFile("=================================", "");

                        int randomPicker = 0;
                        Platform.runLater(() -> {
                            statusInfo.setText("Test 10 SubMenu Links in new Tab...");
                        });
                        //test Open New Tab
                        WebdriverTab newtab = new WebdriverTab();
                        for (int i = 0 ; i < 2 ; i++){
                            randomPicker = 0 + (int)(Math.random() * ((MainSubMenu.size() - 0) + 1));
                            answer = newtab.open(webDriver,MainSubMenu.get(randomPicker).getAttribute("href"),MainSubMenu.get(randomPicker).getAttribute("textContent"));
                            if (answer){
                                report.writeToFile("TEST MainMenuSubLink "+randomPicker+": Successful | ", "found \"" + MainSubMenu.get(randomPicker).getAttribute("textContent") + "\" Keyword at URL : "+ MainSubMenu.get(randomPicker).getAttribute("href"));
                            }else {
                                report.writeToFile("TEST MainMenuSubLink "+randomPicker+": unable to check! |", "couldn't found \"" + MainSubMenu.get(randomPicker).getAttribute("textContent") + "\" Keyword in URL : "+ MainSubMenu.get(randomPicker).getAttribute("href"));
                            }
                        }



                        Platform.runLater(() -> {
                            openMainMenu.setStyle("-fx-background-color: #CCFF99");
                            openMainMenu.setSelected(true);
                        });
                        report.writeToFile("Checking Menu: ", "Complete!");
                    } catch (Exception noHover) {
                        Platform.runLater(() -> {
                            openMainMenu.setStyle("-fx-background-color: #FF0000");
                            openMainMenu.setSelected(true);
                        });
                        report.writeToFile("Checking Menu: ", "unable to complete!");
                    }
                    Thread.sleep(1000);

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");



                    // Check banner for layout
                    Platform.runLater(() -> {
                        checkBannersLayout.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Banners...");
                    });

                    List<WebElement> infos = ((ChromeDriver) webDriver).findElementsByXPath(Homepage.getProperty("page.main.banner"));
                    int MainMenuCounter = 1;
                    for (WebElement info : infos) {
                        hover.moveToElement(info).perform();
                        System.out.println();
                        report.writeToFile("BannerLink " + MainMenuCounter, info.getAttribute("href"));
                        MainMenuCounter++;
                    }

                    Platform.runLater(() -> {
                        checkBannersLayout.setStyle("-fx-background-color: #CCFF99");
                        checkBannersLayout.setSelected(true);
                    });
                    report.writeToFile("Checking Banner: ", "Successful!");


                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));


                    // check shop of the week

                    Platform.runLater(() -> {
                        checkShopOfTheWeek.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Shop of the Week...");
                    });

                                            /*WebElement element = webDriver.findElement(By.xpath("//*[@id=\"pagecontent\"]/div[7]/div/div[1]"));
                                            ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
                                            */
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(100);
                        js.executeScript("window.scrollBy(0,100)");
                    }

                    Platform.runLater(() -> {
                        checkShopOfTheWeek.setStyle("-fx-background-color: #CCFF99");
                        checkShopOfTheWeek.setSelected(true);
                    });

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");


                    // go to Search Input and look for Pumps


                    Platform.runLater(() -> {
                        checkPerfectMatch.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Perfect Match...");
                    });
                    System.out.println(inputPerfectMatch.getText());
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


                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));
                    report.writeToFile("=================================", "");


                    // select Price Hint Filter


                    Platform.runLater(() -> {
                        checkSalesPrice.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Price Hint...");
                    });


                    //check sales price
                    answer = FilterButtonCheck.pressFilterButton(webDriver, js, Homepage.getProperty("page.filter.salesprice"));

                    if (answer) {
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
                    answer = ScreenshotViaWebDriver.printScreen(webDriver,"screenshot5.png");
                    if (answer){
                        report.writeToFile("Checking Layout Salesprice: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("Checking Layout Salesprice: ", "Screenshot not successful!");
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
                    answer = FilterButtonCheck.pressFilterButton(webDriver, js, xpathPattern);
                    if (answer) {
                        report.writeToFile("Checking Filter Color: ", "Successful!");
                    } else {
                        report.writeToFile("Checking Filter Color: ", "unable to check!");
                        report.writeToFile("Checking via JavaScript: ", "\b");
                        answer = FilterButtonCheckViaJavaScript.pressFilterButton(webDriver, js, xpathPattern);

                        if (answer){
                            report.writeToFile("Checking Filter Color via JS: ", "Successful!");
                        }else{
                            report.writeToFile("Checking Filter Color via JS: ", "unable to check!");
                        }
                    }
                    // make SCREENSHOT
                    answer = ScreenshotViaWebDriver.printScreen(webDriver,"screenshot6.png");
                    if (answer){
                        report.writeToFile("Checking Layout Color: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("Checking Layout Color: ", "Screenshot not successful!");
                    }



                    // check brand filter
                    Platform.runLater(() -> statusInfo.setText("Checking Brand..."));
                    xpathPattern = Homepage.getProperty("page.filter.brand");
                    answer = FilterButtonCheck.pressFilterButton(webDriver, js, xpathPattern);
                    if (answer) {
                        report.writeToFile("Checking Filter Brand: ", "Successful!");
                    } else {
                        report.writeToFile("Checking Filter Brand: ", "unable to check!");
                        report.writeToFile("Checking via JavaScript: ", "\b");
                        answer = FilterButtonCheckViaJavaScript.pressFilterButton(webDriver, js, xpathPattern);
                        if (answer){
                            report.writeToFile("Checking Filter Brand via JS: ", "Successful!");
                        }else{
                            report.writeToFile("Checking Filter Brand via JS: ", "unable to check!");
                        }
                    }

                    // make SCREENSHOT
                    answer = ScreenshotViaWebDriver.printScreen(webDriver,"screenshot7.png");
                    if (answer){
                        report.writeToFile("Checking Layout Brand: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("Checking Layout Brand: ", "Screenshot not successful!");
                    }


                    // check material filter
                    Platform.runLater(() -> statusInfo.setText("Checking Material..."));
                    xpathPattern = Homepage.getProperty("page.filter.material");
                    answer = FilterButtonCheck.pressFilterButton(webDriver, js, xpathPattern);
                    if (answer) {
                        report.writeToFile("Checking Filter Material: ", "Successful!");
                    } else {
                        report.writeToFile("Checking Filter Material: ", "unable to check!");
                    }

                    // check shop filter
                    Platform.runLater(() -> statusInfo.setText("Checking Shop..."));
                    xpathPattern = Homepage.getProperty("page.filter.shop");
                    answer = FilterButtonCheck.pressFilterButton(webDriver, js, xpathPattern);
                    if (answer) {
                        Platform.runLater(() -> {
                            checkFilter.setStyle("-fx-background-color: #CCFF99");
                            checkFilter.setSelected(true);
                        });
                        report.writeToFile("Checking Filter Shop: ", "Successful!");
                    } else {
                        Platform.runLater(() -> {
                            checkFilter.setStyle("-fx-background-color: #FF0000");
                            checkFilter.setSelected(true);
                        });
                        report.writeToFile("Checking Filter Shop: ", "unable to check!");
                    }


                    // close webdriver and clear tasklist
                    webDriver.close();

                    try {
                        Runtime.getRuntime().exec("TASKKILL /F /IM chromedriver.exe");
                    } catch (IOException io) {
                        System.out.println(io.getMessage());
                    }

                }


            };

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();

            task.setOnSucceeded(e -> Platform.runLater(() -> {
                Hyperlink link = new Hyperlink("Report created");
                link.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ReportWindow window = new ReportWindow();
                        try {
                            window.MyReportWindow();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
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
            checkLogoHomepage.setStyle("-fx-background-color: #FFFFFF");
            checkGeneralLayout.setStyle("-fx-background-color: #FFFFFF");
            openMainMenu.setStyle("-fx-background-color: #FFFFFF");
            checkBannersLayout.setStyle("-fx-background-color: #FFFFFF");
            checkShopOfTheWeek.setStyle("-fx-background-color: #FFFFFF");
            checkPerfectMatch.setStyle("-fx-background-color: #FFFFFF");
            checkSalesPrice.setStyle("-fx-background-color: #FFFFFF");
            checkFilter.setStyle("-fx-background-color: #FFFFFF");
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
