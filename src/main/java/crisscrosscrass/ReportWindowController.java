package crisscrosscrass;


import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class ReportWindowController {

    @FXML ImageView ImageViewReport;
    @FXML ImageView ImageViewReport1;
    @FXML Text text;

    TranslateTransition transition = new TranslateTransition();



    @FXML
    public void initialize() {
        System.out.println("Controller launched!");
        runReport();
        SlideShow();
    }

    private void SlideShow(){
        Task task = new Task<Object>() {
            @Override
            protected Void call() throws InterruptedException {
                transition.setDuration(Duration.seconds(3));
                transition.setToX(500);
                transition.setAutoReverse(true);
                transition.setOnFinished(event -> {
                    System.out.println("some random code will happen here...");

                });
                transition.setNode(ImageViewReport);
                transition.setCycleCount(2);
                transition.play();
                return null;
            }
        };


        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void runReport() {


        String location = System.getProperty("user.dir");
        location = location.replace("\\","//");
        location += "//temp//";
        Image image = new Image("file:///"+location+"screenshot1.png");
        ImageViewReport.setImage(image);
        ImageViewReport.setPreserveRatio(true);
        ImageViewReport.setOnMouseClicked( event -> {
            ViewImageWindow.display(image);
        });
        Image image2 = new Image("file:///"+location+"screenshot2.png");
        ImageViewReport1.setImage(image2);
        ImageViewReport1.setPreserveRatio(true);
        ImageViewReport1.setOnMouseClicked( event -> {
            ViewImageWindow.display(image2);
        });


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

