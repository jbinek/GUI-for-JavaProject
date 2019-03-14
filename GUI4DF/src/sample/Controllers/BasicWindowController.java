package sample.Controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.swing.*;
import javafx.event.EventHandler;
import java.io.File;

public class BasicWindowController   {
    @FXML
    protected StackPane mainPane;
    TextArea actionStatus;

    @FXML
    protected Button button;

    public BasicWindowController(){
        System.out.println("konstruktor kontrolera");
    }

    @FXML
    void initialize(){
        button.setText("Wczytaj plik");
    }


    @FXML
    public void openExproler(){
        System.out.println("klikniecie");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(null);
    }

    EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            showSingleFileChooser();
        }
    };

        private void showSingleFileChooser() {

            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                actionStatus = new TextArea();
                actionStatus.setText("File selected: " + selectedFile.getName());
                // df= new DataFrame("groubymulti.csv", new Class[]{StringValue.class, DateTimeValue.class, DoubleValue.class, DoubleValue.class});
                mainPane.getChildren().add(actionStatus);
                //  actionStatus.setText("wczytuje do DF");


            }

        }

}
