package crisscrosscrass.Tasks;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import crisscrosscrass.Controller.MainControllerFrontEndCheck;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class SettingManager {
    final String colorActive = "-fx-background-color: #165f6fff;";
    final String colorNotActive = "-fx-background-color: #666666ff;";
    final String colorActiveResults = "-fx-background-color: #45818eff;";
    final String colorNotActiveResults = "-fx-background-color: #d9d9d9ff;";

    public void updateSettingControlls(JFXCheckBox settingCheckBox, Tab tabSettingRelation, TabPane tabPane, Label correspondingNameBox, Label correspondingResultBox){
        if (settingCheckBox.isSelected()){
            tabSettingRelation.setDisable(false);
            tabPane.getSelectionModel().select(tabSettingRelation);
            settingCheckBox.setStyle(colorActive);
            correspondingNameBox.setStyle(colorActive);
            correspondingResultBox.setStyle(colorActiveResults);
        }else{
            tabSettingRelation.setDisable(true);
            settingCheckBox.setStyle(colorNotActive);
            correspondingNameBox.setStyle(colorNotActive);
            correspondingResultBox.setStyle(colorNotActiveResults);
        }
    }

    public void setValidationColor(JFXCheckBox settingCheckBox, JFXTextField validationField){
        if (validationField.getText() == null | validationField.getText().equals("") | !validationField.getText().toLowerCase().contains("@visual-meta.com")){
            settingCheckBox.setStyle(colorNotActive);
        }
    }
}
