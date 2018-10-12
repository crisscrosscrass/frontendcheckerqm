package crisscrosscrass.Controller;


import crisscrosscrass.Tasks.AnimationObject;
import crisscrosscrass.Tasks.ViewImageWindow;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

import java.io.*;


public class ReportWindowController {

    @FXML TextArea text;
    @FXML FlowPane ImageGallery;

    int extraDuration = 1;

    @FXML
    public void initialize(String pathname) {
        runReport();
        displayReport();
    }

    private void displayReport() {
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

    public void runReport() {
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
            myOwnImageView.setOpacity(0);
            myOwnImageView.setPreserveRatio(true);
            myOwnImageView.setFitWidth(200);
            myOwnImageView.setOnMouseClicked( event -> ViewImageWindow.display(imageDisplay, screenshot.getName()));
            ImageGallery.getChildren().add(myOwnImageView);
            wowEffect.SlideShow(myOwnImageView,extraDuration);
            extraDuration++;
        }

    }
    public void displayCustomReport(String pathName){
        StringBuilder myText = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(pathName)))) {
            String line;
            while ((line = reader.readLine()) != null)
                myText.append(line).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        text.setText(myText.toString());

        runReport();
    }
}

