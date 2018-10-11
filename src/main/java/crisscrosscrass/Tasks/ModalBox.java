package crisscrosscrass.Tasks;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ModalBox {
    public void showDialogTestCases(String nameOfTestCase, JFXCheckBox[] checkboxes, StackPane placeToDisplayInfo){
        placeToDisplayInfo.getChildren().clear();
        StringBuilder sb = new StringBuilder();
        for (JFXCheckBox checkBox : checkboxes){
            sb.append("- "+checkBox.getText()+"\n");
        }

        Text headerMessage = new Text();
        headerMessage.setFont(Font.font ("System", FontWeight.BOLD,22));
        headerMessage.setText(nameOfTestCase);
        Text mainContent = new Text();
        mainContent.setText(sb.toString());

        placeToDisplayInfo.setVisible(true);
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(headerMessage);
        content.setBody(mainContent);
        JFXButton jfxButton = new JFXButton("Close");
        jfxButton.setStyle("-fx-background-color:  #e83062; -fx-text-fill: white;");

        JFXDialog dialog = new JFXDialog(placeToDisplayInfo,content, JFXDialog.DialogTransition.LEFT);
        jfxButton.setOnAction(event -> {
            dialog.close();
            placeToDisplayInfo.setVisible(false);
        });
        content.setActions(jfxButton);
        dialog.setOnDialogClosed(event -> placeToDisplayInfo.setVisible(false));
        dialog.show();
    }
    public void showDialogInputFieldValidation(String nameOfTestCase, String Message, StackPane placeToDisplayInfo){
        placeToDisplayInfo.getChildren().clear();

        Text headerMessage = new Text();
        headerMessage.setFont(Font.font ("System", FontWeight.BOLD,22));
        headerMessage.setText(nameOfTestCase);
        Text mainContent = new Text();
        mainContent.setFont(Font.font ("System", FontWeight.NORMAL,12));
        mainContent.setText(Message);
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setPrefViewportHeight(200);
        scrollPane.setPrefViewportWidth(400);
        scrollPane.setMinWidth(600);

        placeToDisplayInfo.setVisible(true);
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(headerMessage);
        content.setBody(scrollPane);
        JFXButton jfxButton = new JFXButton("Close");
        jfxButton.setStyle("-fx-background-color:  #e83062; -fx-text-fill: white;");

        JFXDialog dialog = new JFXDialog(placeToDisplayInfo,content, JFXDialog.DialogTransition.BOTTOM);
        jfxButton.setOnAction(event -> {
            dialog.close();
            placeToDisplayInfo.setVisible(false);
        });
        content.setActions(jfxButton);
        dialog.setOnDialogClosed(event -> {
            placeToDisplayInfo.setVisible(false);
        });
        dialog.show();
    }
}
