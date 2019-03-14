package sample.Controllers;

import DF.DataFrame;
import DF.Exceptions.IncoherentTypeException;
import DF.Values.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;

public class ReadingDataFrameNamesWindow {
    public VBox viewier;
    private Scene scene;
    private  HBox[] Hboxtable;
    private ComboBox<Class<? extends DF.Values.Value>> myComboBox;
    private DataFrame savedDF;
   private ArrayList<ComboBox<Class<? extends Value>>> cmbTable;
   private Stage current;

    @FXML
    private CheckBox myCheckbox;

    private static TextField text;

    protected void fillComboBox(ComboBox cmb){
        cmb.getItems().addAll(IntegerValue.class,DoubleValue.class,DateTimeValue.class,StringValue.class,FloatValue.class);
    }

    public ComboBox createComboBox(){
        if(this.cmbTable == null){ cmbTable = new  ArrayList<ComboBox<Class<? extends Value>>>();}
        ComboBox cmbBox = new ComboBox<Class<? extends DF.Values.Value>>();
        fillComboBox(cmbBox);
        cmbBox.getSelectionModel().selectFirst();
      cmbTable.add(cmbBox);
      return cmbBox;
    }

    public HBox createHbox(String name){
        myComboBox=createComboBox();
        HBox output = new HBox();
        output.getChildren().add(0,new TextField(name));
        output.getChildren().add(1, myComboBox);
        return output;
    }

    public static ReadingDataFrameNamesWindow getInstance(String [] colNames) {
        try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReadingDataFrameNamesWindow.class.getResource("/sample/XMLe/readingDataFrameWindow.fxml"));
        Scene temp = new Scene(loader.load());
        ReadingDataFrameNamesWindow ctrl = loader.getController();
        ctrl.scene=temp;
        ObservableList<Node> childern = ctrl.viewier.getChildren();
        HBox[] HboxTable = new HBox[colNames.length];
            for (int i = 0; i < colNames.length; i++) {
                HBox field = ctrl.createHbox(colNames[i]);
                childern.add(field);
                HboxTable[i]=field;

            }
        ctrl.Hboxtable = HboxTable;
            return ctrl;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DataFrameContaier  showWindow(Stage stage){

        Stage current = new Stage ();
//        current.setHeight(400);
//        current.setWidth(400);
        current.initOwner(stage);
        current.setAlwaysOnTop(true);
        current.setOnCloseRequest(Event::consume);
        current.initModality(Modality.APPLICATION_MODAL);

        current.setScene(scene);
        this.current=current;
        current.setTitle("Data Frame reader");
        current.showAndWait();

        //zapisanie nazw wprowadzonych przez użytkownika //todo : co gdyby nie wpisał nic w nazwie albo wpisał te same nazwy
        String[] colNames = new String[cmbTable.size()];
        for (int i = 0; i < Hboxtable.length; i++) {
            colNames[i]=((TextField)Hboxtable[i].getChildren().get(0)).getText();
        }

        // zapisanie typów wprowadzonych przez użytkownika
        Class<? extends Value> [] colTypes = new Class[cmbTable.size()];
        for (int i = 0; i < cmbTable.size(); i++) {
            ComboBox cmb = cmbTable.get(i);
           colTypes[i]= (Class<? extends Value>) cmb.getSelectionModel().getSelectedItem();
       }
        System.out.println("DF Container został zapisany");
        return new DataFrameContaier(colNames,colTypes,checkIfDfHasHeader());
    }



    public void PossibilityToWriteColnames() {
            System.out.println("sprawdzam checkboxa");
        boolean hasHeader =checkIfDfHasHeader();
        for (HBox hBox : Hboxtable) {
            hBox.getChildren().get(0).setDisable(hasHeader);
        }

    }

    public  boolean checkIfDfHasHeader() {
            return myCheckbox.isSelected();
    }

    @FXML
    public void updateStateCheckbox(ActionEvent actionEvent) {
        PossibilityToWriteColnames();
    }

    @FXML
    public void SaveInfoToDataFrame(ActionEvent actionEvent) {
        System.out.println("klikniecie zeby wylaczyc");
        this.current.close();
    }

    public class DataFrameContaier{
        String[] colNames;
        Class<? extends Value>[] colTypes;
        boolean isHeader;
        public DataFrameContaier(String[]colNames,Class<? extends Value>[] colTypes,boolean isHeader){
            this.colNames=colNames;
            this.colTypes=colTypes;
            this.isHeader=isHeader;
        }
    }


//    @FXML
//    public DataFrame SaveInfoToDataFrame(ActionEvent actionEvent) throws IOException, IncoherentTypeException {
//        DataFrame output=new DataFrame(new String[]{"z","g","r"}, new Class[] {IntegerValue.class,IntegerValue.class,IntegerValue.class});//przykladowe
//        MenuBarController menu = new MenuBarController();
//        Class<? extends Value> [] typesTable = new Class[Hboxtable.length];
//        for (int i = 0; i < cmbTable.size(); i++) {
//            ComboBox cmb = cmbTable.get(i);
//            typesTable[i]= (Class<? extends Value>) cmb.getSelectionModel().getSelectedItem().getClass();
//        }{
//
//        }
//        if(checkIfDfHasHeader()){
//            output = new DataFrame(menu.getFile().getName(),typesTable);
//            System.out.println("DF zappisany");
//        }
//        return output;
//    }
}
