package crisscrosscrass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReportWindow {

    public void MyReportWindow() throws IOException {
        /*
        window = new Stage();
        window.setTitle("Frontend Report");
        window.setWidth(500);
        window.setHeight(450);
        Text text = new Text();
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
        String location = System.getProperty("user.dir");
        location = location.replace("\\","//");
        location += "//temp//";

        Image image = new Image("file:///"+location+"screenshot1.png");
        ImageView iv = new ImageView();
        iv.setImage(image);
        iv.setPreserveRatio(true);
        iv.setFitWidth(300);
        iv.setFitWidth(300);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(text,iv);
        Scene scene = new Scene(vbox);
        window.setScene(scene);
        window.show();



        String location = System.getProperty("user.dir");
        location = location.replace("\\","//");
        location += "//temp//";
        Image image = new Image("file:///"+location+"screenshot1.png");
        ImageViewReport.setImage(image);
        ImageViewReport.setPreserveRatio(true);
        */

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ReportInterface.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initStyle(StageStyle.UNDECORATED);

        stage.setTitle("Report Window");
        stage.setScene(new Scene(root));
        stage.show();


    }

}
