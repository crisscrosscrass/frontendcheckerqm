package crisscrosscrass.Tasks;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ModalBox {
    public void showDialogTestCases(String nameOfTestCase, JFXCheckBox[] checkboxes, StackPane placeToAddInfo){
        StringBuilder sb = new StringBuilder();
        for (JFXCheckBox checkBox : checkboxes){
            sb.append("- "+checkBox.getText()+"\n");
        }

        Text headerMessage = new Text();
        headerMessage.setFont(Font.font ("System", FontWeight.BOLD,22));
        headerMessage.setText(nameOfTestCase);
        Text mainContent = new Text();
        mainContent.setText(sb.toString());

        placeToAddInfo.setVisible(true);
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(headerMessage);
        content.setBody(mainContent);
        JFXButton jfxButton = new JFXButton("Close");
        jfxButton.setStyle("-fx-background-color:  #e83062; -fx-text-fill: white;");

        JFXDialog dialog = new JFXDialog(placeToAddInfo,content, JFXDialog.DialogTransition.CENTER);
        jfxButton.setOnAction(event -> {
            dialog.close();
            placeToAddInfo.setVisible(false);
        });
        content.setActions(jfxButton);
        dialog.setOnDialogClosed(event -> placeToAddInfo.setVisible(false));
        dialog.show();
    }
}
