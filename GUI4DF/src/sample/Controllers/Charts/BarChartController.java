package sample.Controllers.Charts;

import DF.DataFrame;
import DF.Kolumna;
import DF.Values.DateTimeValue;
import DF.Values.StringValue;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Modality;

import javax.swing.*;


public class BarChartController {


    private ComboBox<String> cmbColNamesX;
    private ComboBox<String> cmbColNamesY;
    private ComboBox<Integer> additionalCountCmb;
    private ScrollPane chartPane;
    private ComboBox<String> inaCaseOfTwoCmb;
    private ComboBox<String> inaCaseOfThreeCmb;

    public BarChartController(ComboBox<String> cmbColNamesX,ComboBox<String> cmbColNamesY,ComboBox<Integer> additionalCountCmb,ScrollPane chartPane,ComboBox<String> inaCaseOfTwoCmb,
                    ComboBox<String> inaCaseOfThreeCmb){
        this.additionalCountCmb=additionalCountCmb;
        this.chartPane=chartPane;
        this.cmbColNamesX=cmbColNamesX;
        this.cmbColNamesY=cmbColNamesY;
        this.inaCaseOfThreeCmb=inaCaseOfThreeCmb;
        this.inaCaseOfTwoCmb=inaCaseOfTwoCmb;
    }

    public void showAlert(String mes) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Bar Chart Drawer");
        alert.setContentText(mes);
       // alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public void drawBarChart(DataFrame df) {

        String colNameX = cmbColNamesX.getSelectionModel().getSelectedItem().toString();
        String colNameY = cmbColNamesY.getSelectionModel().getSelectedItem().toString();
        if (df.get(colNameY).zwrocObiekt(0) instanceof StringValue || df.get(colNameY).zwrocObiekt(0) instanceof DateTimeValue) {
            showAlert("Kolumny do OY muszą być numeryczne!");
        } else {

            if (additionalCountCmb.getSelectionModel().getSelectedIndex()== 0) {
                final CategoryAxis xAxis = new CategoryAxis();
                final NumberAxis yAxis = new NumberAxis();
                xAxis.setLabel(colNameX);
                yAxis.setLabel(colNameY);
                //creating the chart
                final javafx.scene.chart.BarChart<String, Number> lineChart = new javafx.scene.chart.BarChart<>(xAxis, yAxis);

                lineChart.setTitle("Wykres zależności " + colNameY + " od " + colNameX);
                XYChart.Series series = new XYChart.Series();
                series.setName(colNameY + "(" + colNameX + ")");


                Kolumna kolX = df.get(colNameX);
                Kolumna kolY = df.get(colNameY);
                for (int i = 0; i < df.size(); i++) {
                    series.getData().add(new XYChart.Data(kolX.zwrocObiekt(i).getValue().toString(), kolY.zwrocObiekt(i).getValue()));
                }

                lineChart.getData().add(series);


                chartPane.setContent(lineChart);
            } else if (additionalCountCmb.getSelectionModel().getSelectedIndex() == 1) {
                String colnameY2 = inaCaseOfTwoCmb.getSelectionModel().getSelectedItem();
                if (df.get(colnameY2).zwrocObiekt(0) instanceof StringValue || df.get(colnameY2).zwrocObiekt(0) instanceof DateTimeValue) {
                    showAlert("Kolumnny do OY muszą być numeryczne!");
                }else {
                    final CategoryAxis xAxis = new CategoryAxis();
                    final NumberAxis yAxis = new NumberAxis();
                    final NumberAxis yAxis2 = new NumberAxis();
                    xAxis.setLabel(colNameX);
                    yAxis.setLabel("value");

                    //creating the chart
                    final javafx.scene.chart.BarChart<String, Number> lineChart = new javafx.scene.chart.BarChart<>(xAxis, yAxis);


                    //defining a series
                    XYChart.Series series1 = new XYChart.Series();
                    XYChart.Series series2 = new XYChart.Series();
                    series1.setName(colNameY);
                    series2.setName(colnameY2);


                    Kolumna kolX = df.get(colNameX);
                    Kolumna kolY = df.get(colNameY);
                    Kolumna kolY2 = df.get(colnameY2);
                    for (int i = 0; i < df.size(); i++) {
                        series1.getData().add(new XYChart.Data(kolX.zwrocObiekt(i).getValue().toString(), kolY.zwrocObiekt(i).getValue()));
                        series2.getData().add(new XYChart.Data(kolX.zwrocObiekt(i).getValue().toString(), kolY2.zwrocObiekt(i).getValue()));
                    }

                    lineChart.getData().addAll(series1, series2);


                    chartPane.setContent(lineChart);
                }
            }
            else{
                String colnameY2 = inaCaseOfTwoCmb.getSelectionModel().getSelectedItem();
                String colnameY3 = inaCaseOfThreeCmb.getSelectionModel().getSelectedItem();
                if (df.get(colnameY3).zwrocObiekt(0) instanceof StringValue || df.get(colnameY3).zwrocObiekt(0) instanceof DateTimeValue) {
                    showAlert("Kolumnny do OY muszą być numeryczne!");
                }else {
                    final CategoryAxis xAxis = new CategoryAxis();
                    final NumberAxis yAxis = new NumberAxis();
                    final NumberAxis yAxis2 = new NumberAxis();
                    final NumberAxis yAxis3 = new NumberAxis();
                    xAxis.setLabel(colNameX);
                    yAxis.setLabel("value");

                    //creating the chart
                    final javafx.scene.chart.BarChart<String, Number> lineChart = new javafx.scene.chart.BarChart<>(xAxis, yAxis);


                    //defining a series
                    XYChart.Series series1 = new XYChart.Series();
                    XYChart.Series series2 = new XYChart.Series();
                    XYChart.Series series3 = new XYChart.Series();
                    series1.setName(colNameY);
                    series2.setName(colnameY2);
                    series3.setName(colnameY3);


                    Kolumna kolX = df.get(colNameX);
                    Kolumna kolY = df.get(colNameY);
                    Kolumna kolY2 = df.get(colnameY2);
                    Kolumna kolY3 = df.get(colnameY3);
                    for (int i = 0; i < df.size(); i++) {
                        series1.getData().add(new XYChart.Data(kolX.zwrocObiekt(i).getValue().toString(), kolY.zwrocObiekt(i).getValue()));
                        series2.getData().add(new XYChart.Data(kolX.zwrocObiekt(i).getValue().toString(), kolY2.zwrocObiekt(i).getValue()));
                        series3.getData().add(new XYChart.Data(kolX.zwrocObiekt(i).getValue().toString(), kolY3.zwrocObiekt(i).getValue()));
                    }

                    lineChart.getData().addAll(series1, series2,series3);
                    chartPane.setContent(lineChart); }
            }

        }
    }
}
