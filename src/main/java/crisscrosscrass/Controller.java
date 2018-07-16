package crisscrosscrass;

import crisscrosscrass.TestCases.FilterButtonCheck;
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
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.*;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Controller {

    @FXML Button startwebdriver;
    @FXML CheckBox checkLogoHomepage;
    @FXML CheckBox checkGeneralLayout;
    @FXML CheckBox openMainMenu;
    @FXML CheckBox checkBannersLayout;
    @FXML CheckBox checkShopOfTheWeek;
    @FXML CheckBox checkPerfectMatch;
    @FXML CheckBox checkSalesPrice;
    @FXML CheckBox checkFilter;
    @FXML ProgressBar progressIndicator;
    @FXML Text statusInfo;
    @FXML TextArea inputSearch;
    @FXML HBox outputPlace;
    @FXML ImageView preloaderCat;
    @FXML VBox CheckBoxesPlace;
    @FXML AnchorPane mainStage;

    private static boolean answer = false;







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
                                if(!webdriverFile.exists()) {
                                    System.out.println("Webdriver not exist");
                                    copyFiles();
                                }

                                Platform.runLater(() -> statusInfo.setText("Starting Engine..."));



                                System.setProperty("webdriver.chrome.driver", "temp//chromedriver.exe");
                                WebDriver webDriver = new ChromeDriver();

                                //if you didn't update the Path system variable to add the full directory path to the executable as above mentioned then doing this directly through code
                                //System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                                //System.setProperty("firefox.driver", "\\C:\\ProgramComparePower Files (x86)\\Mozilla Firefox\\firefox.exe");
                                //WebDriver webDriver = new ChromeDriver();
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
                                report.writeToFile("Checking Website: ",requestedWebsite);




                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));


                    // Click on Logo Test

                                Platform.runLater(() -> {
                                    checkLogoHomepage.setStyle("-fx-background-color: #eef442");
                                    statusInfo.setText("Checking Logo...");
                                });


                                try {
                                    ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"logo\"]/img").click();

                                    Platform.runLater(() -> {
                                        checkLogoHomepage.setStyle("-fx-background-color: #CCFF99");
                                        checkLogoHomepage.setSelected(true);
                                        report.writeToFile("Checking Logo: ","Successful!");
                                    });

                                }catch (Exception Message){
                                    Platform.runLater(() -> {
                                        statusInfo.setText("no Logo...");
                                        checkLogoHomepage.setStyle("-fx-background-color: #FF0000");
                                        checkLogoHomepage.setSelected(true);
                                        report.writeToFile("Checking Logo: ","unable to check!");
                                    });
                                }

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));


                     //Check general Layout Test


                                Platform.runLater(() -> {
                                    checkGeneralLayout.setStyle("-fx-background-color: #eef442");
                                    statusInfo.setText("Checking Layout...");
                                });
                                Thread.sleep(100);


                    // make SCREENSHOT 1

                                try {
                                    String location = System.getProperty("user.dir");
                                    location = location.replace("\\","//");
                                    location += "//temp//";
                                    //System.out.println(location);
                                    File scrFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
                                    FileUtils.copyFile(scrFile, new File(location+"screenshot1.png"));
                                    report.writeToFile("Checking Layout: ","Screenshot successful!");

                                } catch (IOException Message) {
                                    Message.printStackTrace();
                                    System.out.println("Screenshot was not made");
                                    report.writeToFile("Checking Layout: ","Screenshot not successful!");
                                }

                    // Scroll down


                                for (int i = 0 ; i < 10 ; i++){
                                    Thread.sleep(100);
                                    js.executeScript("window.scrollBy(0,100)");
                                }


                    // make SCREENSHOT 2

                                try {
                                    String location = System.getProperty("user.dir");
                                    location = location.replace("\\","//");
                                    location += "//temp//";
                                    //System.out.println(location);
                                    File scrFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
                                    FileUtils.copyFile(scrFile, new File(location+"screenshot2.png"));
                                    report.writeToFile("Checking Layout: ","Screenshot2 successful!");

                                } catch (IOException Message) {
                                    Message.printStackTrace();
                                    System.out.println("Screenshot was not made");
                                    report.writeToFile("Checking Layout: ","Screenshot2 not successful!");
                                }


                    // Scroll up


                                for (int i = 0 ; i < 10 ; i++){
                                    Thread.sleep(100);
                                    js.executeScript("window.scrollBy(0,-100)");
                                }

                                Platform.runLater(() -> {
                                    checkGeneralLayout.setStyle("-fx-background-color: #CCFF99");
                                    checkGeneralLayout.setSelected(true);
                                });

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));


                    // open Hover Main Menu and have a check on all DeepLinks Test


                                Platform.runLater(() -> {
                                    openMainMenu.setStyle("-fx-background-color: #eef442");
                                    statusInfo.setText("Checking Menu...");
                                });


                                Actions hover = new Actions(webDriver);

                                //WebElement Elem_to_hover = ((ChromeDriver) webDriver).findElementByCssSelector("#item0_btn");
                                //Damen
                                try{
                                    WebElement Elem_to_hover = ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"item0_btn\"]");
                                    hover.moveToElement(Elem_to_hover).perform();
                                    webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

                                    //Herren
                                    Elem_to_hover = ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"item1_btn\"]");
                                    hover.moveToElement(Elem_to_hover).perform();
                                    Thread.sleep(100);

                                    //Kinder&Babys
                                    Elem_to_hover = ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"item2_btn\"]");
                                    hover.moveToElement(Elem_to_hover).perform();
                                    Thread.sleep(100);

                                    //Taschen
                                    Elem_to_hover = ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"item3_btn\"]");
                                    hover.moveToElement(Elem_to_hover).perform();
                                    Thread.sleep(100);

                                    //Möbel
                                    Elem_to_hover = ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"item4_btn\"]");
                                    hover.moveToElement(Elem_to_hover).perform();
                                    Thread.sleep(100);

                                    //Wohnen
                                    Elem_to_hover = ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"item5_btn\"]");
                                    hover.moveToElement(Elem_to_hover).perform();
                                    Thread.sleep(100);

                                    //Garten
                                    Elem_to_hover = ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"item6_btn\"]");
                                    hover.moveToElement(Elem_to_hover).perform();
                                    Thread.sleep(100);

                                    //Weitere Kategorien
                                    Elem_to_hover = ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"item7_btn\"]");
                                    hover.moveToElement(Elem_to_hover).perform();
                                    Thread.sleep(100);

                                    //Inspiration
                                    Elem_to_hover = ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"item8_btn\"]");
                                    hover.moveToElement(Elem_to_hover).perform();
                                    Thread.sleep(100);

                                    Platform.runLater(() -> {
                                        openMainMenu.setStyle("-fx-background-color: #CCFF99");
                                        openMainMenu.setSelected(true);
                                        report.writeToFile("Checking Menu: ","Successful!");
                                    });
                                }catch (Exception noHover){
                                    Platform.runLater(() -> {
                                        openMainMenu.setStyle("-fx-background-color: #FF0000");
                                        openMainMenu.setSelected(true);
                                        report.writeToFile("Checking Menu: ","unable to check!");
                                    });
                                }
                                Thread.sleep(1000);

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));

                    // Check banner for layout

                                Platform.runLater(() -> {
                                    checkBannersLayout.setStyle("-fx-background-color: #eef442");
                                    statusInfo.setText("Checking Banners...");
                                });

                                List<WebElement> infos = ((ChromeDriver) webDriver).findElementsByXPath("//*[@class='categories-wrapper']/a");
                                int counterInfo = 1;
                                for (WebElement info : infos){
                                    hover.moveToElement(info).perform();
                                    System.out.println();
                                    report.writeToFile("BannerLink " + counterInfo + " :\n",info.getAttribute("href")+"\r");
                                    counterInfo++;
                                }

                                Platform.runLater(() -> {
                                    checkBannersLayout.setStyle("-fx-background-color: #CCFF99");
                                    report.writeToFile("Checking Banner: ","Successful!");
                                    checkBannersLayout.setSelected(true);
                                });

                                /**
                                try {
                                    List<WebElement> banners = ((ChromeDriver) webDriver).findElementsByXPath("//*[@class='categories-wrapper']/a");

                                    for ( WebElement banner : banners ) {
                                        System.out.println(banner);
                                    }

                                    Platform.runLater(() -> {
                                        checkBannersLayout.setStyle("-fx-background-color: #CCFF99");
                                        report.writeToFile("Checking Banner: ","Successful!");
                                        checkBannersLayout.setSelected(true);
                                    });


                                }catch (Exception noBanner){
                                    Platform.runLater(() -> {
                                        checkBannersLayout.setStyle("-fx-background-color: #FF0000");
                                        report.writeToFile("Checking Banner: ","unable to check!");
                                        checkBannersLayout.setSelected(true);
                                    });
                                }
                                 */

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));


                    // check shop of the week

                                Platform.runLater(() -> {
                                    checkShopOfTheWeek.setStyle("-fx-background-color: #eef442");
                                    statusInfo.setText("Checking Shop of the Week...");
                                });

                                            /*WebElement element = webDriver.findElement(By.xpath("//*[@id=\"pagecontent\"]/div[7]/div/div[1]"));
                                            ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
                                            */
                                for (int i = 0 ; i < 10 ; i++){
                                    Thread.sleep(100);
                                    js.executeScript("window.scrollBy(0,100)");
                                }

                                Platform.runLater(() -> {
                                    checkShopOfTheWeek.setStyle("-fx-background-color: #CCFF99");
                                    checkShopOfTheWeek.setSelected(true);
                                });

                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));


                    // go to Search Input and look for Pumps


                                Platform.runLater(() -> {
                                    checkPerfectMatch.setStyle("-fx-background-color: #eef442");
                                    statusInfo.setText("Checking Perfect Match...");
                                });

                                    WebElement element = webDriver.findElement(By.id("header-search-input"));
                                    element.sendKeys("pumps");
                                    element.submit();

                                    if (webDriver.getCurrentUrl().contains("?q=")){
                                        checkPerfectMatch.setStyle("-fx-background-color: #FF0000");
                                        report.writeToFile("Checking Perfect Match: ","unable to check!");
                                        checkPerfectMatch.setSelected(true);
                                    }else {
                                        Platform.runLater(() -> {
                                            checkPerfectMatch.setStyle("-fx-background-color: #CCFF99");
                                            report.writeToFile("Checking Perfect Match: ","Successful!");
                                            checkPerfectMatch.setSelected(true);
                                        });
                                    }


                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));


                    // select Price Hint Filter


                                Platform.runLater(() -> {
                                    checkSalesPrice.setStyle("-fx-background-color: #eef442");
                                    statusInfo.setText("Checking Price Hint...");
                                });


                                //check sales price
                                answer = FilterButtonCheck.pressFilterButton(webDriver,js,"//*[@id='saleButtonHeader2']");

                                if (answer){
                                    Platform.runLater(() -> {
                                        checkSalesPrice.setStyle("-fx-background-color: #CCFF99");
                                        report.writeToFile("Checking Price Hint: ","Successful!");
                                        checkSalesPrice.setSelected(true);
                                    });
                                }else{
                                    Platform.runLater(() -> {
                                        checkSalesPrice.setStyle("-fx-background-color: #FF0000");
                                        report.writeToFile("Checking Price Hint: ","unable to check!");
                                        checkSalesPrice.setSelected(true);
                                    });
                                }



                    Platform.runLater(() -> progressIndicator.setProgress(checkAllCheckBoxes()));


                    // select  Filters


                                Platform.runLater(() -> {
                                    checkFilter.setStyle("-fx-background-color: #eef442");
                                    statusInfo.setText("Checking Filters...");
                                });

                                //  check color filter

                                Platform.runLater(() -> statusInfo.setText("Checking Color..."));

                                answer = FilterButtonCheck.pressFilterButton(webDriver,js,"//*[@id='pagecontent']/*/*[@class='sidebar']/*[@data-id='farben_block']/ul/li/a ");
                                if (answer){
                                    Platform.runLater(() -> report.writeToFile("Checking Filter Color: ","Successful!"));
                                }else {
                                    Platform.runLater(() -> report.writeToFile("Checking Filter Color: ","unable to check!"));
                                }


                                // check brand filter
                                Platform.runLater(() -> statusInfo.setText("Checking Brand..."));

                                answer = FilterButtonCheck.pressFilterButton(webDriver,js,"//*[@id='pagecontent']/*/*[@class='sidebar']/*[@data-id='brand_box']/div/a ");

                                if (answer){
                                    Platform.runLater(() -> report.writeToFile("Checking Filter Brand: ","Successful!"));
                                }else {
                                    Platform.runLater(() -> report.writeToFile("Checking Filter Brand: ","unable to check!"));
                                }


                                // check material filter
                                Platform.runLater(() -> statusInfo.setText("Checking Material..."));

                                answer = FilterButtonCheck.pressFilterButton(webDriver,js,"//*[@id='pagecontent']/*/*[@class='sidebar']/*[@data-id='material_block']/div/a ");
                                if (answer){
                                    Platform.runLater(() -> report.writeToFile("Checking Filter Material: ","Successful!"));
                                }else {
                                    Platform.runLater(() -> report.writeToFile("Checking Filter Material: ","unable to check!"));
                                }

                                // check shop filter
                                Platform.runLater(() -> statusInfo.setText("Checking Shop..."));

                                answer = FilterButtonCheck.pressFilterButton(webDriver,js,"//*[@id='pagecontent']/*/*[@class='sidebar']/*[@data-id='shop_box']/div/a");
                                if (answer){
                                    Platform.runLater(() -> {
                                        checkFilter.setStyle("-fx-background-color: #CCFF99");
                                        report.writeToFile("Checking Filter Shop: ","Successful!");
                                        checkFilter.setSelected(true);
                                    });
                                }else {
                                    Platform.runLater(() -> {
                                        checkFilter.setStyle("-fx-background-color: #FF0000");
                                        report.writeToFile("Checking Filter Shop: ","unable to check!");
                                        checkFilter.setSelected(true);
                                    });
                                }


                    // close webdriver and clear tasklist

                                webDriver.close();

                                try{
                                    Runtime.getRuntime().exec("TASKKILL /F /IM chromedriver.exe");
                                }
                                catch(IOException io){
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
    private void changeButtonText(){
        statusInfo.setText("Running complete");
        startwebdriver.setDisable(false);
    }
    @FXML
    private void copyFiles(){
        CopyFiles bringit = new CopyFiles();
        bringit.copyFileThere();

    }
    @FXML
    private double checkAllCheckBoxes(){
        double StateProgress = 0;

        CheckBox[] checkboxes = CheckBoxesPlace.getChildren().toArray(new CheckBox[0]);
        int AmountOfCheckBoxes = checkboxes.length;

        for (CheckBox checkbox : checkboxes){

            if (checkbox.isSelected()){
                StateProgress += 1;

            }
        }
        //System.out.println("State : " + StateProgress + "Amount of Boxes: "+AmountOfCheckBoxes);
        StateProgress = StateProgress / AmountOfCheckBoxes;
        return StateProgress;
    }

    @FXML
    private void resetAllFormOptions(){
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
