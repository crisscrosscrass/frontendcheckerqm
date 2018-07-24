package crisscrosscrass;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class AnimationObject {

    public void SlideShow(ImageView ImageViewReport, int extraDuration){
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws InterruptedException {
                TranslateTransition transition = new TranslateTransition();
                FadeTransition fadeTransition = new FadeTransition();

                //ImageViewReport.setOpacity(100);

                transition.setDuration(Duration.millis(1));
                fadeTransition.setDuration(Duration.millis(1));
                transition.setNode(ImageViewReport);
                fadeTransition.setNode(ImageViewReport);
                Task<Void> sleeper = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            Thread.sleep(1000+(extraDuration*200));
                        } catch (InterruptedException e) {
                        }
                        return null;
                    }
                };
                sleeper.setOnSucceeded(event -> {
                    transition.setToX(+200);
                    fadeTransition.setFromValue(0);
                    fadeTransition.setToValue(0.0);
                    fadeTransition.play();
                    transition.play();
                    transition.setOnFinished(event2 -> {
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
                });
                new Thread(sleeper).start();


                return null;
            }
        };


        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void SlideShow(StackPane rootPane, int extraDuration){
            Task task = new Task<Void>() {
                @Override
                protected Void call() throws InterruptedException {
                    TranslateTransition transition = new TranslateTransition();
                    FadeTransition fadeTransition = new FadeTransition();

                    transition.setDuration(Duration.millis(1));
                    fadeTransition.setDuration(Duration.millis(1));
                    transition.setNode(rootPane);
                    fadeTransition.setNode(rootPane);
                    Task<Void> sleeper = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            try {
                                Thread.sleep(500+(extraDuration*200));
                            } catch (InterruptedException e) {
                            }
                            return null;
                        }
                    };
                    sleeper.setOnSucceeded(event -> {
                        transition.setToX(+200);
                        fadeTransition.setFromValue(0);
                        fadeTransition.setToValue(0.0);
                        fadeTransition.play();
                        transition.play();
                        transition.setOnFinished(event2 -> {
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
                    });
                    new Thread(sleeper).start();


                    return null;
                }
            };


            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        }
    }
