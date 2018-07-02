package crisscrosscrass;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/UserInterface.fxml"));
        primaryStage.setTitle("Frontend Check");
        URL LogoLocation = Main.class.getClassLoader().getResource("VisualMeta.png");
        Image Logo = new Image(String.valueOf(LogoLocation));
        primaryStage.getIcons().add(Logo);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
