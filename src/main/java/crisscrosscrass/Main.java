package crisscrosscrass;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

import static javafx.application.Application.launch;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent parent = FXMLLoader.load(getClass().getResource("/UserInterface.fxml"));
        Parent parent = FXMLLoader.load(getClass().getResource("/UserInterface.fxml"));
        primaryStage.setTitle("Frontend Check");
        URL LogoLocation = Main.class.getClassLoader().getResource("VisualMeta.png");
        Image Logo = new Image(String.valueOf(LogoLocation));
        primaryStage.getIcons().add(Logo);
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}
