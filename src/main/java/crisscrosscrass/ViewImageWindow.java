package crisscrosscrass;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;


public class ViewImageWindow {

    public static void display(Image image){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        AnchorPane root = new AnchorPane();
        ImageView ImagePlace = new ImageView(image);
        root.getChildren().add(ImagePlace);
        ImagePlace.setOnMouseClicked(event -> {
            window.close();
        });
        Scene scene = new Scene(root);
        URL LogoLocation = Main.class.getClassLoader().getResource("VisualMeta.png");
        Image Logo = new Image(String.valueOf(LogoLocation));
        window.getIcons().add(Logo);
        window.setScene(scene);
        window.showAndWait();
        return;
    }
}
