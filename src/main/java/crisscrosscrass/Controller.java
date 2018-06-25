package crisscrosscrass;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Controller {

    @FXML Button startwebdriver;
    @FXML CheckBox checkLogoHomepage;
    @FXML CheckBox checkGeneralLayout;
    @FXML CheckBox openMainMenu;
    @FXML CheckBox checkBannersLayout;
    @FXML CheckBox checkCategoryLinks;
    @FXML CheckBox checkShopOfTheWeek;
    @FXML ProgressBar progressIndicator;
    @FXML Text statusInfo;
    @FXML TextArea inputSearch;
    @FXML Pane outputPlace;





    @FXML
    public void startRealAction() {

        System.out.println("Start Engine...");




        Platform.runLater(() -> {
            progressIndicator.setProgress(-1);
            startwebdriver.setDisable(true);
            Task task = new Task<Object>() {
                @Override
                protected Void call() throws InterruptedException {
                    takeRoutine();
                    return null;
                }

                private void takeRoutine() throws InterruptedException {
                    /**
                     * Basic Settings before Starting WebDriver
                     * Browser, Javascript , etc.
                     */
                                Platform.runLater(() -> {
                                    statusInfo.setText("Detecting Sources...");
                                });


                                String requestedWebsite = inputSearch.getText();

                                File webdriverFile = new File("temp//chromedriver.exe");
                                if(!webdriverFile.exists()) {
                                    System.out.println("Webdriver not exist");
                                    copyFiles();
                                }

                                Platform.runLater(() -> {
                                    statusInfo.setText("Starting Engine...");
                                });



                                System.setProperty("webdriver.chrome.driver", "temp//chromedriver.exe");
                                WebDriver webDriver = new ChromeDriver();

                                //if you didn't update the Path system variable to add the full directory path to the executable as above mentioned then doing this directly through code
                                //System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                                //System.setProperty("firefox.driver", "\\C:\\ProgramComparePower Files (x86)\\Mozilla Firefox\\firefox.exe");
                                //WebDriver webDriver = new ChromeDriver();
                                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                                Report report = new Report();
                                report.clearWrittenReport();




                    /**
                     *
                     * open Startpage and have a basic overview
                     */
                                Platform.runLater(() -> {
                                    statusInfo.setText("Open Maximize Mode...");
                                });
                                webDriver.manage().window().maximize();
                                Platform.runLater(() -> {
                                    statusInfo.setText("Go to requested Website...");
                                });
                                webDriver.navigate().to(requestedWebsite);
                                report.writeToFile("Checking Website: ",requestedWebsite);

                    /**
                     * Click on Logo Test
                     */
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


                      /**
                      * Check general Layout Test
                      */
                                Platform.runLater(() -> {
                                    checkGeneralLayout.setStyle("-fx-background-color: #eef442");
                                    statusInfo.setText("Checking Layout...");
                                });
                                Thread.sleep(100);


                    /**
                     * make SCREENSHOT
                     */

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

                    /**
                     * Scroll down
                     */
                                for (int i = 0 ; i < 10 ; i++){
                                    Thread.sleep(100);
                                    js.executeScript("window.scrollBy(0,100)");
                                }
                     /**
                     * Scroll up
                     */
                                for (int i = 0 ; i < 10 ; i++){
                                    Thread.sleep(100);
                                    js.executeScript("window.scrollBy(0,-100)");
                                }

                                Platform.runLater(() -> {
                                    checkGeneralLayout.setStyle("-fx-background-color: #CCFF99");
                                    checkGeneralLayout.setSelected(true);
                                });


                    /**
                     * open Hover Main Menu and have a check on all DeepLinks Test
                     */
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
                    /**
                     * Check banner for layout
                     */

                                Platform.runLater(() -> {
                                    checkBannersLayout.setStyle("-fx-background-color: #eef442");
                                    statusInfo.setText("Checking Banners...");
                                });



                                try {


                                    List<WebElement> banners = ((ChromeDriver) webDriver).findElementsByXPath("//*[@id=\"main-middle-wrap\"]/div[2]/*");
                                    /*
                                    new WebDriverWait(webDriver,0,1).until(
                                                ExpectedConditions.presenceOfElementLocated(By.id("myDynamicElement")));
                                     */

                                    for ( WebElement e : banners ) {
                                        Thread.sleep(10);
                                        e.click();
                                            /*
                                            try{
                                                new WebDriverWait(webDriver, 0,2).until(
                                                        ExpectedConditions.presenceOfElementLocated(By.id("justMyPersonalWaitingProcess")));
                                            }catch (Exception somethingRandom){
                                                System.out.println(somethingRandom);
                                            }
                                            */
                                    }



                                    Platform.runLater(() -> {
                                        checkBannersLayout.setStyle("-fx-background-color: #CCFF99");
                                        checkBannersLayout.setSelected(true);
                                    });


                                }catch (Exception noBanner){
                                    Platform.runLater(() -> {
                                        checkBannersLayout.setStyle("-fx-background-color: #FF0000");
                                        checkBannersLayout.setSelected(true);
                                    });
                                }

                    /**
                     * check shop of the week
                     */

                    Platform.runLater(() -> {
                        checkShopOfTheWeek.setStyle("-fx-background-color: #eef442");
                        statusInfo.setText("Checking Shop of the Week...");
                    });

                    /*WebElement element = webDriver.findElement(By.xpath("//*[@id=\"pagecontent\"]/div[7]/div/div[1]"));
                    ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
                    */
                    Thread.sleep(500);

                    js.executeScript("window.scrollTo(0,1000)");

                    Platform.runLater(() -> {
                        checkShopOfTheWeek.setStyle("-fx-background-color: #CCFF99");
                        checkShopOfTheWeek.setSelected(true);
                    });


                    /**
                     * close webdriver and clear tasklist
                     */

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

            task.setOnSucceeded(e -> {
                Platform.runLater(() -> {
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
                    outputPlace.getChildren().addAll(link);
                    progressIndicator.setProgress(100);
                    changeButtonText();
                    System.out.println("Process Finished");

                });
            });


        });

    }


    @FXML
    public void takeroutine() throws InterruptedException {
        /*
         * Basic Settings before Starting WebDriver
         * Browser, Javascript , etc.
         */

            //if you didn't update the Path system variable to add the full directory path to the executable as above mentioned then doing this directly through code


            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
            //System.setProperty("firefox.driver", "\\C:\\ProgramComparePower Files (x86)\\Mozilla Firefox\\firefox.exe");
            WebDriver webDriver = new ChromeDriver();
            JavascriptExecutor js = (JavascriptExecutor) webDriver;




            /**
             *
             * open Startpage and have a basic overview
             */

                webDriver.manage().window().maximize();
                webDriver.navigate().to("https://www.ladenzeile.de/");



            /**
             * open Hover Main Menu and have a check on all DeepLinks
             */
            Actions hover = new Actions(webDriver);

            //WebElement Elem_to_hover = ((ChromeDriver) webDriver).findElementByCssSelector("#item0_btn");
            //Damen
            WebElement Elem_to_hover = ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"item0_btn\"]");
                hover.moveToElement(Elem_to_hover).perform();
                Thread.sleep(100);

            //Herren
            Elem_to_hover = ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"item1_btn\"]");
                hover.moveToElement(Elem_to_hover).perform();
                Thread.sleep(100);

            String oldTab = webDriver.getWindowHandle();
            String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN);
                ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"item1_tab\"]/div[1]/div/a[1]").sendKeys(selectLinkOpeninNewTab);

            //Get the current window handle
            String windowHandle = webDriver.getWindowHandle();

            //Get the list of window handles
            ArrayList tabs = new ArrayList(webDriver.getWindowHandles());
                System.out.println(tabs.size());
            //Use the list of window handles to switch between windows
                Thread.sleep(100);
                webDriver.switchTo().window((String) tabs.get(1));
                for (int i = 0 ; i < 10 ; i++){
                Thread.sleep(100);
                js.executeScript("window.scrollBy(0,100)");
            }
                Thread.sleep(1000);
                webDriver.close();
                webDriver.switchTo().window((String) tabs.get(0));
                Thread.sleep(1000);


            //Kinder & Babys
            Elem_to_hover = ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"item2_btn\"]");
                hover.moveToElement(Elem_to_hover).perform();
                Thread.sleep(1000);

            //Taschen
            Elem_to_hover = ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"item3_btn\"]");
                hover.moveToElement(Elem_to_hover).perform();
                Thread.sleep(1000);

            //Möbel
            Elem_to_hover = ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"item4_btn\"]");
                hover.moveToElement(Elem_to_hover).perform();
                Thread.sleep(3000);

            //Banner Number CLick 1+2
                ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"main-middle-wrap\"]/div[2]/a[1]").click();
                Thread.sleep(100);
                ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"main-middle-wrap\"]/div[2]/a[2]").click();
                Thread.sleep(100);

            //Quicklinks hover check
                for (int i = 1 ; i <= 17; i++){
                Elem_to_hover = ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"side-menu\"]/div["+i+"]");
                hover.moveToElement(Elem_to_hover).perform();
                Thread.sleep(100);
            }


            //*[@id="side-menu"]/div[2]
            //*[@id="side-menu"]/div[17]


            /**
             * Scroll down
             */
                for (int i = 0 ; i < 10 ; i++){
                Thread.sleep(100);
                js.executeScript("window.scrollBy(0,100)");
            }





            //Assert.assertTrue("title should be start different",webDriver.getTitle().startsWith("Selenium Simplified"));
            //webDriver.findElement(By.id("submit1")).click();
                webDriver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);



            WebElement element = webDriver.findElement(By.id("header-search-input"));
                element.sendKeys("Pumps");
                element.submit();
            //webDriver.findElement(By.id("header-search-input")).sendKeys(Keys.ENTER);
                Thread.sleep(1000);

                for (int i = 0 ; i < 30 ; i++){
                Thread.sleep(100);
                js.executeScript("window.scrollBy(0,100)");
            }


        webDriver.close();
    }


    @FXML
    public void changeButtonText(){
        statusInfo.setText("Running complete");
        startwebdriver.setDisable(false);
    }
    @FXML
    public void copyFiles(){
        CopyFiles bringit = new CopyFiles();
        bringit.copyFileThere();

    }



}
