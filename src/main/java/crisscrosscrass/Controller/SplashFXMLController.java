package crisscrosscrass.Controller;

import crisscrosscrass.Tasks.AnimationObject;
import crisscrosscrass.Main;
import crisscrosscrass.Tasks.ConfigSettings;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class SplashFXMLController implements Initializable {

    @FXML
    StackPane rootPane;
    @FXML
    ImageView ImageSplash;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootPane.setOpacity(0);
        new SplashScreen().start();
    }

    class SplashScreen extends Thread{
        @Override
        public void run(){
            try {
                AnimationObject wowEffect = new AnimationObject();
                wowEffect.SlideShow(rootPane,0);
                Thread.sleep(2000);


                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        Stage primaryStage = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/UserInterface.fxml"));

                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        MainControllerFrontEndCheck controller = loader.getController();

                        primaryStage.setTitle("Frontend Check");
                        URL LogoLocation = Main.class.getClassLoader().getResource("Images/VisualMeta.png");
                        Image Logo = new Image(String.valueOf(LogoLocation));
                        primaryStage.getIcons().add(Logo);
                        primaryStage.initStyle(StageStyle.DECORATED);
                        //primaryStage.initStyle(StageStyle.UNIFIED);
                        final int shadowSize = 20;
                        root.setStyle(
                                "-fx-background-color: transparent;" +
                                        "-fx-effect: dropshadow(gaussian, #e83062, " + shadowSize + ", 0, 0, 0);" +
                                        "-fx-background-insets: " + shadowSize + ";"
                        );
                        //primaryStage.initStyle(StageStyle.UTILITY);

                        root.setStyle("-fx-background-color: grey;");

                        primaryStage.setScene(new Scene(root));
                        primaryStage.getScene().setFill(Color.TRANSPARENT);



                        primaryStage.setOnCloseRequest(e -> {
                            System.out.println("Closing");
                            File configSettings = new File("temp//UserSettings.properties");
                            if (configSettings.exists()) {

                                Properties properties = new Properties();
                                properties.setProperty("inputSearch",controller.inputSearch.getText());
                                properties.setProperty("inputEmailAdress",controller.inputEmailAdress.getText());
                                properties.setProperty("inputTextSearchAndSuggestions",controller.inputTextSearchAndSuggestions.getText());
                                properties.setProperty("inputGridPageURL",controller.inputGridPageURL.getText());
                                properties.setProperty("inputGridPageKeyword",controller.inputGridPageKeyword.getText());
                                properties.setProperty("inputGridPageURLWithWindows",controller.inputGridPageURLWithWindows.getText());
                                properties.setProperty("inputGridPageURLWithFillIns",controller.inputGridPageURLWithFillIns.getText());
                                properties.setProperty("inputBrandPageOverview",controller.inputBrandPageOverview.getText());
                                properties.setProperty("inputLucenePage",controller.inputLucenePage.getText());
                                properties.setProperty("inputAccountEmail",controller.inputAccountEmail.getText());
                                properties.setProperty("inputAccountPassword",controller.inputAccountPassword.getText());
                                ConfigSettings configSettingsOnClose = new ConfigSettings();
                                configSettingsOnClose.saveConfigSettings(properties);
                            }

                            closeProgram();
                        });

                        primaryStage.show();
                        Platform.runLater(() ->{
                            rootPane.getScene().getWindow().hide();
                        });


                    }
                    public void closeProgram(){
                        System.out.println("DataSaved!");
                    }
                });





            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
