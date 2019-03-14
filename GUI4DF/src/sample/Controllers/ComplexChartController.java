package sample.Controllers;


import DF.DataFrame;
import DF.Kolumna;
import DF.Values.DateTimeValue;
import DF.Values.StringValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Controllers.Charts.BarChartController;
import sample.Controllers.Charts.BubbleChartController;
import sample.Controllers.Charts.LineChartController;
import sample.Controllers.Charts.ScatterChartController;


import java.io.IOException;


public class ComplexChartController {

    @FXML
    public ComboBox<String> cmbTypeOfChart;

    @FXML
    public ComboBox cmbColNamesY;

    @FXML
    public ComboBox cmbColNamesX;

    @FXML
    public ScrollPane chartPane;

    @FXML
    public Button drawChartButton;

    @FXML
    public Label additionalCountLabel;

    @FXML
    public ComboBox<Integer> additionalCountCmb;

    @FXML
    public Label additionalOYlabel;

    @FXML
    public ComboBox<String> inaCaseOfTwoCmb;

    @FXML
    public ComboBox<String> inaCaseOfThreeCmb;

    public Scene chartScene;
    private Stage current;
    public DataFrame myChartDf;


    @FXML
    void initialize() {
        cmbTypeOfChart.getItems().addAll("LineChart", "BubbleChart", "ScatterChart", "BarChart");
        drawChartButton.setDisable(true);
        additionalCountLabel.setDisable(true);
        additionalCountCmb.setDisable(true);
        additionalOYlabel.setDisable(true);
        inaCaseOfTwoCmb.setDisable(true);
        inaCaseOfThreeCmb.setDisable(true);
    }


    public static ComplexChartController getInstance() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReadingDataFrameNamesWindow.class.getResource("/sample/XMLe/ComplexChartWindow.fxml"));
            Scene temp = new Scene(loader.load());
            ComplexChartController ctrl = loader.getController();
            ctrl.chartScene = temp;

            return ctrl;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void showWindow(Stage stage, String[] colNames, DataFrame df) {
        myChartDf = df;
        this.cmbColNamesX.getItems().setAll(colNames);
        this.cmbColNamesY.getItems().setAll(colNames);
        Stage current = new Stage();
        current.initOwner(stage);
        current.setAlwaysOnTop(false);
        current.initModality(Modality.APPLICATION_MODAL);
        chartPane.setVisible(false);


        current.setScene(chartScene);
        this.current = current;
        current.setTitle("Chart Drawer");
        current.showAndWait();

        System.out.println("Okno z wykresami wyświetlone");

    }

    public void drawChart(ActionEvent actionEvent) {
        chartPane.setVisible(true);
        String type = cmbTypeOfChart.getSelectionModel().getSelectedItem().toString();

        switch (type) {
            case "LineChart":
                LineChartController tmpLine = new LineChartController(cmbColNamesX,cmbColNamesY,additionalCountCmb,chartPane,inaCaseOfTwoCmb,inaCaseOfThreeCmb);
                tmpLine.drawLineChart(myChartDf);
                break;

            case "BubbleChart":
                BubbleChartController tmpBubble = new BubbleChartController(cmbColNamesX,cmbColNamesY,additionalCountCmb,chartPane,inaCaseOfTwoCmb,inaCaseOfThreeCmb);
                tmpBubble.drawBubbleChart(myChartDf);
                break;

            case "ScatterChart":
                ScatterChartController tmpScatter = new ScatterChartController(cmbColNamesX,cmbColNamesY,additionalCountCmb,chartPane,inaCaseOfTwoCmb,inaCaseOfThreeCmb);
                tmpScatter.drawScatterChart(myChartDf);
                break;

            case "BarChart":
                BarChartController tmpBar = new BarChartController(cmbColNamesX,cmbColNamesY,additionalCountCmb,chartPane,inaCaseOfTwoCmb,inaCaseOfThreeCmb);
                tmpBar.drawBarChart(myChartDf);
                break;
        }
    }


    public void Xclicked(ActionEvent actionEvent) {
        if (cmbColNamesY.getSelectionModel().getSelectedItem() == null || cmbTypeOfChart.getSelectionModel().getSelectedItem() == null) {
            drawChartButton.setDisable(true);
        } else {
            drawChartButton.setDisable(false);
        }


    }

    public void Yclicked(ActionEvent actionEvent) {
        if (cmbColNamesX.getSelectionModel().getSelectedItem() == null || cmbTypeOfChart.getSelectionModel().getSelectedItem() == null) {
            drawChartButton.setDisable(true);
        } else {
            drawChartButton.setDisable(false);
        }

    }

    public void typeClicked(ActionEvent actionEvent) {
        if (cmbColNamesX.getSelectionModel().getSelectedItem() == null || cmbColNamesY.getSelectionModel().getSelectedItem() == null) {
            drawChartButton.setDisable(true);
        } else {
            drawChartButton.setDisable(false);
        }

        String type = cmbTypeOfChart.getSelectionModel().getSelectedItem().toString();
        System.out.println(type);
        additionalCountCmb.getItems().setAll(1, 2, 3);
      //  if (type.equals("BarChart") || type.equals("BubbleChart") || type.equals("ScatterChart")) {
            additionalCountLabel.setDisable(false);
            additionalCountCmb.setDisable(false);
            if (additionalCountCmb.getSelectionModel().getSelectedItem() == null) {
                drawChartButton.setDisable(true);
            }


//        } else {
//            additionalCountLabel.setDisable(true);
//            additionalCountCmb.setDisable(true);
//            additionalOYlabel.setDisable(true);
//            inaCaseOfTwoCmb.setDisable(true);
//            inaCaseOfThreeCmb.setDisable(true);
//        }
    }

    public void additionalCountCmbClicked() {
        drawChartButton.setDisable(true);
            int count = additionalCountCmb.getSelectionModel().getSelectedIndex();
            System.out.println(count);


            switch (count) {
                case -1:
                    drawChartButton.setDisable(true);
                case 0:
                    additionalOYlabel.setDisable(true);
                    inaCaseOfTwoCmb.setDisable(true);
                    inaCaseOfThreeCmb.setDisable(true);
                    drawChartButton.setDisable(false);
                    break;
                case 1:
                    inaCaseOfTwoCmb.valueProperty().set(null);
                    additionalOYlabel.setDisable(false);
                    inaCaseOfTwoCmb.setDisable(false);
                    drawChartButton.setDisable(true);
                    inaCaseOfThreeCmb.setDisable(true);
                    inaCaseOfTwoCmb.getItems().setAll(myChartDf.zwroc_nazwy());
                    break;
                case 2:
                    inaCaseOfTwoCmb.valueProperty().set(null);
                    inaCaseOfThreeCmb.valueProperty().set(null);
                    drawChartButton.setDisable(true);
                    additionalOYlabel.setDisable(false);
                    inaCaseOfTwoCmb.setDisable(false);
                    inaCaseOfThreeCmb.setDisable(false);
                    inaCaseOfTwoCmb.getItems().setAll(myChartDf.zwroc_nazwy());
                    inaCaseOfThreeCmb.getItems().setAll(myChartDf.zwroc_nazwy());
                    break;
            }
        }


    public void inaCaseOfTwoCmbClicked(ActionEvent actionEvent) {
        System.out.println(additionalCountCmb.getSelectionModel().getSelectedItem());
        if (additionalCountCmb.getSelectionModel().getSelectedIndex() == 1) {
            drawChartButton.setDisable(false);
        }
        if (inaCaseOfThreeCmbClicked() && additionalCountCmb.getSelectionModel().getSelectedIndex() == 2) {
            drawChartButton.setDisable(false);
        }

    }

    public boolean inaCaseOfThreeCmbClicked() {
        if (inaCaseOfThreeCmb.getSelectionModel().getSelectedItem() != null && inaCaseOfTwoCmb.getSelectionModel().getSelectedItem()!=null) {
            drawChartButton.setDisable(false);
            return true;
        }
        return false;
    }
}





