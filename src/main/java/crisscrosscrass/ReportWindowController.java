package crisscrosscrass;


import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.*;


public class ReportWindowController {

    @FXML Text text;
    @FXML FlowPane ImageGallery;

    int extraDuration = 0;


    @FXML
    public void initialize() {
        System.out.println("Controller launched!");
        runReport();
        //SlideShow();
    }



    private void runReport() {


        //dsplay all images located in temp
        AnimationObject wowEffect = new AnimationObject();
        String location = System.getProperty("user.dir");
        location = location.replace("\\","//");
        location += "//temp//";
        ImageGallery.setHgap(10);
        ImageGallery.setHgap(10);

        File folder = new File(location);
        File [] pngFiles = folder.listFiles(file -> file.isFile() && file.getName().toLowerCase().endsWith(".png"));
        for (File screenshot : pngFiles){
            String ImageSRC = screenshot.toString().replace("\\","//");
            Image imageDisplay = new Image("file:///"+ImageSRC);
            ImageView myOwnImageView = new ImageView(imageDisplay);
            myOwnImageView.setPreserveRatio(true);
            myOwnImageView.setFitWidth(200);
            myOwnImageView.setOnMouseClicked( event -> ViewImageWindow.display(imageDisplay));
            ImageGallery.getChildren().add(myOwnImageView);
            wowEffect.SlideShow(myOwnImageView,extraDuration);
            extraDuration++;
        }






        // reading and displaying report

        String myText = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(new File("temp//report.txt")))) {

            String line;
            while ((line = reader.readLine()) != null)
                //System.out.println(line);
                myText += line+"\n";

        } catch (IOException e) {
            e.printStackTrace();
        }
        text.setText(myText);
    }

}

