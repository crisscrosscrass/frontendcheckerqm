package crisscrosscrass.Tasks;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tests.PartnerShopsPageTest;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.apache.log4j.Logger;

public class VisualResults {
    final static Logger logger = Logger.getLogger(VisualResults.class);

    public void createPieChart(VBox PlaceForPieCharts, JFXCheckBox[] checkboxes, String nameOfPieChart){
        PieChart pieChart = new PieChart();
        PlaceForPieCharts.getChildren().add(pieChart);
        ObservableList<PieChart.Data> dataObservableList = FXCollections.observableArrayList();
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
        PieChart.Data HomepagePass = new PieChart.Data("Pass", passTest);
        PieChart.Data HomepageFail = new PieChart.Data("Fail", failTest);

        try{
            int finalFailTest = failTest;
            int finalPassTest = passTest;
            Platform.runLater(() -> {
                pieChart.setData(dataObservableList);
                dataObservableList.add(HomepagePass);
                dataObservableList.add(HomepageFail);
                HomepageFail.setPieValue(finalFailTest);
                HomepagePass.setPieValue(finalPassTest);
                pieChart.setTitle(nameOfPieChart);
            });
        }catch (Exception DataPieChartNotWorking){
            DataPieChartNotWorking.printStackTrace();
        }

    }
}
