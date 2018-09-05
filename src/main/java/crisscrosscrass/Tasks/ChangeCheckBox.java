package crisscrosscrass.Tasks;

import com.jfoenix.controls.JFXCheckBox;
import javafx.application.Platform;
import javafx.scene.paint.Paint;

public class ChangeCheckBox {

    private static String isInProgress = "#EEF442";
    private static String isSuccessful = "#0F9D58";
    private static String isNotSuccessful = "#FF0000";
    private static String isStandard = "#535341";


    public static void adjustStyle(boolean select, String status, JFXCheckBox editedCheckBox){

        if (("complete").equals(status)){
            Platform.runLater(() -> {
                editedCheckBox.setStyle("-fx-background-color: transparent");
                editedCheckBox.setCheckedColor(Paint.valueOf(isSuccessful));
                editedCheckBox.setUnCheckedColor(Paint.valueOf(isSuccessful));
                editedCheckBox.setSelected(select);
            });
        } else if (("progress").equals(status)){
            Platform.runLater(() -> {
                editedCheckBox.setStyle("-fx-background-color: "+isInProgress+"");
                editedCheckBox.setCheckedColor(Paint.valueOf(isInProgress));
                editedCheckBox.setUnCheckedColor(Paint.valueOf(isInProgress));
                editedCheckBox.setSelected(select);
            });
        }else if(("nope").equals(status)){
            Platform.runLater(() -> {
                editedCheckBox.setStyle("-fx-background-color: transparent");
                editedCheckBox.setCheckedColor(Paint.valueOf(isNotSuccessful));
                editedCheckBox.setUnCheckedColor(Paint.valueOf(isNotSuccessful));
                editedCheckBox.setSelected(select);
            });
        }else {
            Platform.runLater(() -> {
                editedCheckBox.setStyle("-fx-background-color: transparent");
                editedCheckBox.setCheckedColor(Paint.valueOf(isStandard));
                editedCheckBox.setUnCheckedColor(Paint.valueOf(isStandard));
                editedCheckBox.setSelected(select);
            });
        }

    }

    public static String getIsInProgress() { return isInProgress.substring(1,7).toLowerCase(); }

    public static String getIsSuccessful() {
        return isSuccessful.substring(1,7).toLowerCase();
    }

    public static String getIsNotSuccessful() {
        return isNotSuccessful.substring(1,7).toLowerCase();
    }

    public static String getIsStandard() {
        return isStandard.substring(1,7).toLowerCase();
    }



}
