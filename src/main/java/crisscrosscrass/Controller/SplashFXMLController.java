package crisscrosscrass.Controller;

import crisscrosscrass.Tasks.AnimationObject;
import crisscrosscrass.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
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
                        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("/FXML/UserInterface.fxml"));
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/FXML/UserInterface.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        primaryStage.setTitle("Frontend Check");
                        URL LogoLocation = Main.class.getClassLoader().getResource("Images/VisualMeta.png");
                        Image Logo = new Image(String.valueOf(LogoLocation));
                        primaryStage.getIcons().add(Logo);
                        primaryStage.setScene(new Scene(root));
                        primaryStage.show();
                        Platform.runLater(() ->{
                            rootPane.getScene().getWindow().hide();
                        });
                    }
                });




            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
