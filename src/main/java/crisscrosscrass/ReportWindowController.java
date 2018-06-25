package crisscrosscrass;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class ReportWindowController {

    @FXML
    ImageView ImageViewReport;
    @FXML Text text;

    @FXML
    public void initialize() {
        System.out.println("Controller launched!");


        String location = System.getProperty("user.dir");
        location = location.replace("\\","//");
        location += "//temp//";
        Image image = new Image("file:///"+location+"screenshot1.png");
        ImageViewReport.setImage(image);
        ImageViewReport.setPreserveRatio(true);


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

