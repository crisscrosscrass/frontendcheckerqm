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
                        FrontEndCheckController controller = loader.getController();

                        primaryStage.setTitle("Frontend Check");
                        URL LogoLocation = Main.class.getClassLoader().getResource("Images/VisualMeta.png");
                        Image Logo = new Image(String.valueOf(LogoLocation));
                        primaryStage.getIcons().add(Logo);
                        primaryStage.initStyle(StageStyle.UNIFIED);
                        primaryStage.setScene(new Scene(root, Color.TRANSPARENT));


                        primaryStage.setOnCloseRequest(e -> {
                            System.out.println("Closing");
                            System.out.println(controller.getInputSearch().getText());
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
