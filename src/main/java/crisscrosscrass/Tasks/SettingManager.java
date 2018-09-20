package crisscrosscrass.Tasks;

import com.jfoenix.controls.JFXCheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class SettingManager {
    final String colorActive = "-fx-background-color: #165f6fff;";
    final String colorNotActive = "-fx-background-color: #666666ff;";

    public void updateSettingControlls(JFXCheckBox settingCheckBox, Tab tabSettingRelation, TabPane tabPane){
        if (settingCheckBox.isSelected()){
            tabSettingRelation.setDisable(false);
            tabPane.getSelectionModel().select(tabSettingRelation);
            settingCheckBox.setStyle(colorActive);
        }else{
            tabSettingRelation.setDisable(true);
            settingCheckBox.setStyle(colorNotActive);
        }
    }
}
