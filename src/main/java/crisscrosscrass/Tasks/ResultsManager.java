package crisscrosscrass.Tasks;

import com.jfoenix.controls.JFXCheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class ResultsManager {
    public void updateResultsCheckbox(JFXCheckBox settingTestButton, JFXCheckBox[] checkboxes, Label BoxHomepageResult, VBox placeForFailedTestCases){
        if (settingTestButton.isSelected()){
            //count all checkboxes
            int allCheckBoxes = checkboxes.length;
            ArrayList allFailedTest = new ArrayList();
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
                        allFailedTest.add(checkBox.getText());
                    }
                }
            }
            //update Result Box
            BoxHomepageResult.setText(passTest + " / " + allCheckBoxes);
            //get all not succesffull checkboxes text and add to failed list
            if (allFailedTest.size() != 0 ){
                //Add Headline to failed test
                Label headLine = new Label("- "+settingTestButton.getText());
                headLine.setFont(Font.font ("System", FontWeight.BOLD,22));
                placeForFailedTestCases.getChildren().add(headLine);
                //add all failed TestCases below
                for (Object failedTestCase : allFailedTest){
                    Label failed = new Label(failedTestCase.toString());
                    failed.setStyle("-fx-font-size:14px;-fx-padding: 0px 0px 0px 25px;");
                    placeForFailedTestCases.getChildren().add(failed);
                }
            }
        }else{
            BoxHomepageResult.setText(" - ");
        }
    }
    public int getTestCasesNumber(JFXCheckBox settingTestButton, JFXCheckBox[] checkboxes){
        int allCheckBoxes = 0;
        if (settingTestButton.isSelected()) {
            //count all checkboxes
            allCheckBoxes = checkboxes.length;
        }
        return allCheckBoxes;
    }
    public int getPassedTestCasesNumber(JFXCheckBox settingTestButton, JFXCheckBox[] checkboxes){
        int passTest = 0;
        for (JFXCheckBox checkBox : checkboxes){
            if (checkBox.isSelected() ){
                if (checkBox.getCheckedColor().toString().substring(2,8).equals(ChangeCheckBox.getIsSuccessful())){
                    ++passTest;
                }
            }
        }
        return passTest;
    }
    public int getFailedTestCasesNumber(JFXCheckBox settingTestButton, JFXCheckBox[] checkboxes){
        int failTest = 0;
        for (JFXCheckBox checkBox : checkboxes){
            if (checkBox.isSelected() ){
                if (checkBox.getCheckedColor().toString().substring(2,8).equals(ChangeCheckBox.getIsNotSuccessful())){
                    ++failTest;
                }
            }
        }
        return failTest;
    }
}
