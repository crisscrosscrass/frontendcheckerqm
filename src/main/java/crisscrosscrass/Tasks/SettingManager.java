package crisscrosscrass.Tasks;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import crisscrosscrass.Controller.MainControllerFrontEndCheck;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;

public class SettingManager {
    final private String colorActive = "-fx-background-color: #165f6fff;";
    final private String colorProgress = "-fx-background-color: #e83062ff;";
    final private String colorNotActive = "-fx-background-color: #666666ff;";
    final private String colorActiveResults = "-fx-background-color: #45818eff;";
    final private String colorNotActiveResults = "-fx-background-color: #d9d9d9ff;";
    final private String fontColorActiveResults = "white";
    final private String fontColorNotActiveResults = "#666666";

    public void updateSettingControlls(JFXCheckBox settingCheckBox, Tab tabOnCheck){
        if (settingCheckBox.isSelected()){
            Platform.runLater(() -> {
            tabOnCheck.setDisable(false);
            settingCheckBox.setStyle(colorActive);
            });
        }else{
            Platform.runLater(() -> {
            tabOnCheck.setDisable(true);
            settingCheckBox.setStyle(colorNotActive);
            });
        }
    }

    public void setValidationColor(JFXCheckBox settingCheckBox, JFXTextField validationField){
        if (validationField.getText() == null | validationField.getText().equals("") | !validationField.getText().toLowerCase().contains("@visual-meta.com")){
            Platform.runLater(() -> {
            settingCheckBox.setStyle(colorNotActive);
            });
        }
    }
    public void updateResultBoxes(JFXCheckBox settingCheckBox, String status,Label correspondingNameBox, Label correspondingResultBox){
        if (settingCheckBox.isSelected()){
            if (("complete").equals(status)){
                Platform.runLater(() -> {
                correspondingNameBox.setStyle(colorActive);
                correspondingResultBox.setTextFill(Color.web(fontColorActiveResults));
                correspondingResultBox.setStyle(colorActiveResults);
                });
            }else if (("progress").equals(status)){
                Platform.runLater(() -> {
                correspondingNameBox.setStyle(colorProgress);
                correspondingResultBox.setTextFill(Color.web(fontColorActiveResults));
                correspondingResultBox.setStyle(colorActiveResults);
                });
            }else{
                Platform.runLater(() -> {
                    correspondingNameBox.setStyle(colorNotActive);
                    correspondingResultBox.setTextFill(Color.web(fontColorNotActiveResults));
                    correspondingResultBox.setStyle(colorNotActiveResults);
                });
            }
        }else{
            Platform.runLater(() -> {
            correspondingNameBox.setStyle(colorNotActive);
            correspondingResultBox.setTextFill(Color.web(fontColorNotActiveResults));
            correspondingResultBox.setStyle(colorNotActiveResults);
            });
        }
    }
}
