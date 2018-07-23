package crisscrosscrass;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimationObject {

    public void SlideShow(ImageView ImageViewReport, int extraDuration){
        Task task = new Task<Object>() {
            @Override
            protected Void call() throws InterruptedException {
                TranslateTransition transition = new TranslateTransition();
                FadeTransition fadeTransition = new FadeTransition();

                //ImageViewReport.setOpacity(100);

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
                    transition.setDuration(Duration.seconds(1));
                    fadeTransition.setDuration(Duration.seconds(1));
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
}
