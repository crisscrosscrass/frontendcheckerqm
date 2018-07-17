package crisscrosscrass;


import javafx.animation.FadeTransition;
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
                TranslateTransition transition = new TranslateTransition();
                FadeTransition fadeTransition = new FadeTransition();

                ImageViewReport.setOpacity(100);

                transition.setDuration(Duration.millis(1));
                fadeTransition.setDuration(Duration.millis(1));
                transition.setNode(ImageViewReport);
                fadeTransition.setNode(ImageViewReport);


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


        String location = System.getProperty("user.dir");
        location = location.replace("\\","//");
        location += "//temp//";
        Image image = new Image("file:///"+location+"screenshot1.png");
        ImageViewReport.setImage(image);
        ImageViewReport.setPreserveRatio(true);
        ImageViewReport.setOpacity(0);
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

