package sample.Controllers.Charts;

import DF.DataFrame;
import DF.Kolumna;
import DF.Values.DateTimeValue;
import DF.Values.StringValue;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;

public class LineChartController {


    private ComboBox<String> cmbColNamesX;
    private ComboBox<String> cmbColNamesY;
    private ComboBox<Integer> additionalCountCmb;
    private ScrollPane chartPane;
    private ComboBox<String> inaCaseOfTwoCmb;
    private ComboBox<String> inaCaseOfThreeCmb;

    public LineChartController(ComboBox<String> cmbColNamesX,ComboBox<String> cmbColNamesY,ComboBox<Integer> additionalCountCmb,ScrollPane chartPane,ComboBox<String> inaCaseOfTwoCmb,
                                 ComboBox<String> inaCaseOfThreeCmb){
        this.additionalCountCmb=additionalCountCmb;
        this.chartPane=chartPane;
        this.cmbColNamesX=cmbColNamesX;
        this.cmbColNamesY=cmbColNamesY;
        this.inaCaseOfThreeCmb=inaCaseOfThreeCmb;
        this.inaCaseOfTwoCmb=inaCaseOfTwoCmb;
    }


    public void showAlert(String mess) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Line Chart Drawer");
        alert.setContentText(mess);
        // alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }
    public void drawLineChart(DataFrame df) {
        String colNameX = cmbColNamesX.getSelectionModel().getSelectedItem();
        String colNameY = cmbColNamesY.getSelectionModel().getSelectedItem();
        if (df.get(colNameX).zwrocObiekt(0) instanceof StringValue || df.get(colNameX).zwrocObiekt(0) instanceof DateTimeValue || df.get(colNameY).zwrocObiekt(0) instanceof StringValue || df.get(colNameY).zwrocObiekt(0) instanceof DateTimeValue) {
            showAlert("Wartości w wybranych kolumnach muszą być numeryczne!");
        } else {

            if (additionalCountCmb.getSelectionModel().getSelectedIndex() == 0) {
                final NumberAxis xAxis = new NumberAxis();
                final NumberAxis yAxis = new NumberAxis();
                xAxis.setLabel(colNameX);
                yAxis.setLabel(colNameY);
                //creating the chart
                final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

                lineChart.setTitle("Wykres zależności " + colNameY + " od " + colNameX);
                //defining a series
                XYChart.Series series = new XYChart.Series();
                series.setName(colNameY + "(" + colNameX + ")");
                //populating the series with data

                Kolumna kolX = df.get(colNameX);
                Kolumna kolY = df.get(colNameY);
                for (int i = 0; i < df.size(); i++) {
                    series.getData().add(new XYChart.Data(kolX.zwrocObiekt(i).getValue(), kolY.zwrocObiekt(i).getValue()));
                }

                lineChart.getData().add(series);

                chartPane.setContent(lineChart);

            }
            else if (additionalCountCmb.getSelectionModel().getSelectedIndex()== 1){
                String colNameY2 = inaCaseOfTwoCmb.getSelectionModel().getSelectedItem();
                if (df.get(colNameY2).zwrocObiekt(0) instanceof StringValue || df.get(colNameY2).zwrocObiekt(0) instanceof DateTimeValue) {
                    showAlert("Wartości w wybranych kolumnach muszą być numeryczne!");}
                else{
                    final NumberAxis xAxis = new NumberAxis();
                    final NumberAxis yAxis = new NumberAxis();
                    final NumberAxis yAxis2 = new NumberAxis();
                    xAxis.setLabel(colNameX);
                    yAxis.setLabel("value");
                    //creating the chart
                    final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);


                    //defining a series
                    XYChart.Series series = new XYChart.Series();
                    XYChart.Series series2 = new XYChart.Series();
                    series.setName(colNameY);
                    series2.setName(colNameY2);
                    //populating the series with data

                    Kolumna kolX = df.get(colNameX);
                    Kolumna kolY = df.get(colNameY);
                    Kolumna kolY2 = df.get(colNameY2);
                    for (int i = 0; i < df.size(); i++) {
                        series.getData().add(new XYChart.Data(kolX.zwrocObiekt(i).getValue(), kolY.zwrocObiekt(i).getValue()));
                        series2.getData().add(new XYChart.Data(kolX.zwrocObiekt(i).getValue(), kolY2.zwrocObiekt(i).getValue()));
                    }

                    lineChart.getData().addAll(series,series2);

                    chartPane.setContent(lineChart);

                }
            }
            else  {
                String colNameY3 = inaCaseOfThreeCmb.getSelectionModel().getSelectedItem();
                String colNameY2 = inaCaseOfTwoCmb.getSelectionModel().getSelectedItem();
                if (df.get(colNameY2).zwrocObiekt(0) instanceof StringValue || df.get(colNameY2).zwrocObiekt(0) instanceof DateTimeValue||
                        df.get(colNameY3).zwrocObiekt(0) instanceof StringValue || df.get(colNameY3).zwrocObiekt(0) instanceof DateTimeValue) {
                    showAlert("Wartości w wybranych kolumnach muszą być numeryczne!");}
                else{
                    final NumberAxis xAxis = new NumberAxis();
                    final NumberAxis yAxis = new NumberAxis();
                    final NumberAxis yAxis2 = new NumberAxis();
                    final NumberAxis yAxis3 = new NumberAxis();
                    xAxis.setLabel(colNameX);
                    yAxis.setLabel("value");
                    //creating the chart
                    final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);


                    //defining a series
                    XYChart.Series series = new XYChart.Series();
                    XYChart.Series series2 = new XYChart.Series();
                    XYChart.Series series3 = new XYChart.Series();
                    series.setName(colNameY);
                    series2.setName(colNameY2);
                    series3.setName(colNameY3);
                    //populating the series with data

                    Kolumna kolX = df.get(colNameX);
                    Kolumna kolY = df.get(colNameY);
                    Kolumna kolY2 = df.get(colNameY2);
                    Kolumna kolY3 = df.get(colNameY3);
                    for (int i = 0; i < df.size(); i++) {
                        series.getData().add(new XYChart.Data(kolX.zwrocObiekt(i).getValue(), kolY.zwrocObiekt(i).getValue()));
                        series2.getData().add(new XYChart.Data(kolX.zwrocObiekt(i).getValue(), kolY2.zwrocObiekt(i).getValue()));
                        series3.getData().add(new XYChart.Data(kolX.zwrocObiekt(i).getValue(), kolY3.zwrocObiekt(i).getValue()));
                    }

                    lineChart.getData().addAll(series,series2,series3);

                    chartPane.setContent(lineChart);

                }
            }

        }
    }
}
