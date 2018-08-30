package crisscrosscrass.Tasks;

import com.jfoenix.controls.JFXCheckBox;
import javafx.application.Platform;
import javafx.scene.paint.Paint;

public class ChangeCheckBox {

    private static String isInProgress = "#EEF442";
    private static String isSuccessful = "#0F9D58";
    private static String notSuccessful = "#FF0000";
    private static String isStandard = "#535341";


    public static void adjustStyle(boolean select, String status, JFXCheckBox editedCheckBox){

        if (status.equals("complete")){
            Platform.runLater(() -> {
                editedCheckBox.setStyle("-fx-background-color: transparent");
                editedCheckBox.setCheckedColor(Paint.valueOf(isSuccessful));
                editedCheckBox.setUnCheckedColor(Paint.valueOf(isSuccessful));
                editedCheckBox.setSelected(select);
            });
        } else if (status.equals("progress")){
            Platform.runLater(() -> {
                editedCheckBox.setStyle("-fx-background-color: "+isInProgress+"");
                editedCheckBox.setCheckedColor(Paint.valueOf(isInProgress));
                editedCheckBox.setUnCheckedColor(Paint.valueOf(isInProgress));
                editedCheckBox.setSelected(select);
            });
        }else if(status.equals("nope")){
            Platform.runLater(() -> {
                editedCheckBox.setStyle("-fx-background-color: transparent");
                editedCheckBox.setCheckedColor(Paint.valueOf(notSuccessful));
                editedCheckBox.setUnCheckedColor(Paint.valueOf(notSuccessful));
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

}
