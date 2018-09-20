package crisscrosscrass.Tasks;

import com.jfoenix.controls.JFXCheckBox;
import javafx.scene.control.Label;

public class ResultsManager {
    public void updateResultsCheckbox(JFXCheckBox settingHomepage, JFXCheckBox[] checkboxes, Label BoxHomepageResult){
        if (settingHomepage.isSelected()){
            //count all checkboxes
            int allCheckBoxes = checkboxes.length;
            //count all successfully checked checkboxes
            int passTest = 0;
            int failTest = 0;
            for (JFXCheckBox checkBox : checkboxes){
                if (checkBox.isSelected() ){
                    if (checkBox.getCheckedColor().toString().substring(2,8).equals(ChangeCheckBox.getIsSuccessful())){
                        ++passTest;
                    }
                    if (checkBox.getCheckedColor().toString().substring(2,8).equals(ChangeCheckBox.getIsNotSuccessful())){
                        ++failTest;
                    }
                }
            }
            //update Result Box
            BoxHomepageResult.setText(passTest + " / " + allCheckBoxes);
            //get all not succesffull checkboxes text and add to failed list
        }else{
            BoxHomepageResult.setText(" - ");
        }
    }
}
