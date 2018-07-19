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


    @FXML
    public void initialize() {
        System.out.println("Controller launched!");
        runReport();
        //SlideShow();
    }

    private void SlideShow(){
        Task task = new Task<Object>() {
            @Override
            protected Void call() throws InterruptedException {
                TranslateTransition transition = new TranslateTransition();
                FadeTransition fadeTransition = new FadeTransition();

                //ImageViewReport.setOpacity(100);

                transition.setDuration(Duration.millis(1));
                fadeTransition.setDuration(Duration.millis(1));
                //transition.setNode(ImageViewReport);
               // fadeTransition.setNode(ImageViewReport);


                transition.setToX(+200);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                fadeTransition.play();
                transition.play();
                transition.setOnFinished(event -> {
                    System.out.println("some random code will happen here...");
                    transition.setDuration(Duration.seconds(2));
                    fadeTransition.setDuration(Duration.seconds(2));
                    transition.setToX(0);
                    fadeTransition.setFromValue(0.0);
                    fadeTransition.setToValue(1.0);
                    fadeTransition.play();
                    transition.play();
                    transition.setOnFinished(finisher -> {
                        System.out.println("finally done...");
                        transition.stop();
                        fadeTransition.stop();
                    });
                });
                return null;
            }
        };


        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void runReport() {


        //dsplay all images located in temp
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

