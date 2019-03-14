package sample.Controllers;

import DF.DataFrame;
import DF.Exceptions.IncoherentTypeException;
import DF.Exceptions.NoSenseInGroupingByAllColumnsException;
import DF.Exceptions.ValueParseException;
import DF.Exceptions.ZeroLengthException;
import DF.Kolumna;
import DF.Values.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Model.ObservableDataFrame;
import java.io.*;


public class MenuBarController {

    @FXML
    public TableView<Value[]> myTableView;

    @FXML
    public ComboBox cmbBoxToStatistic;

    @FXML
    public ListView<String> myListViewToStat;

    @FXML
    public Button statButton;

    @FXML
    public TableView tableVievForStat;

    @FXML
    public Button showChartMenuButton;

    @FXML
    public Button showChartMenuAfterGroupByButton;

    private FileChooser fileChooser;
    private Scene scene;
    private Stage thisStage;
    private File thisSelectedFile;
    private DataFrame myDF;
    private ObservableDataFrame myObservDF;
    public ComplexChartController chartController;

    public MenuBarController() {
        System.out.println("Konstruktor MenuBarController");
    }

    public File getFile() {
        return this.thisSelectedFile;
    }

    @FXML
    protected Label showChosenFile;


    @FXML
    protected Button closeButton;

    @FXML
    public void initialize() {
        System.out.println(myListViewToStat.getSelectionModel().getSelectedItems());
        fillComboBox();
        myListViewToStat.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("DataFrame", "*.csv");
        fileChooser.getExtensionFilters().add(filter);
        showChartMenuButton.setDisable(true);
        showChartMenuAfterGroupByButton.setDisable(true);
        statButton.setDisable(true);
        cmbBoxToStatistic.getSelectionModel().selectFirst();
    }


    protected boolean checkIfFileHasRightFormat(String fileName) {
        // System.out.println(fileName.substring(fileName.length()-3));
        if (fileName.substring(fileName.length() - 3).equals("csv")) {
            return true;
        }
        return false;
    }

    @FXML
    public void openExplorer() {
        System.out.println("klikniecie");
        fileChooser.setTitle("Open Resource File");
        File selectedFile;
        do {
            selectedFile = fileChooser.showOpenDialog(thisStage);
            thisSelectedFile = selectedFile;
        } while (selectedFile == null);
        if (checkIfFileHasRightFormat(selectedFile.getName())) {
            showChosenFile.setText("File selected: " + selectedFile.getName());
        } else {
            System.out.println("bład formatu");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Błędny format pliku!");
            alert.setContentText("Wczytany format: " + selectedFile.getName().substring(selectedFile.getName().length() - 3) + "\n" + "Obsługiwany format: csv");
            alert.showAndWait();
        }

        try {
            String[] tmp = this.readColNames(selectedFile);
            ReadingDataFrameNamesWindow win = ReadingDataFrameNamesWindow.getInstance(tmp);
            var c = win.showWindow(thisStage);
            try {
                if (c.isHeader) {
                    myDF = new DataFrame(selectedFile.getPath(), c.colTypes, null);
                    showChartMenuButton.setDisable(false);
                    statButton.setDisable(false);
                    createActualTable(myDF);
                    fillListView();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Data Frame");
                    alert.setContentText("Data Frame utowrzony pomyślnie według wybranych kryteriów!");
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                    alert.showAndWait();
                } else {
                    myDF = new DataFrame(selectedFile.getPath(), c.colTypes, c.colNames);
                    showChartMenuButton.setDisable(false);
                    statButton.setDisable(false);
                    createActualTable(myDF);
                    fillListView();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Data Frame");
                    alert.setContentText("Data Frame utowrzony pomyślnie według wybranych kryteriów!");
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                    alert.showAndWait();

                }
            } catch (IncoherentTypeException e) {
                showException(e, e.getMessage());
            }


        } catch (IOException e) {
            // i co potem?
            showException(e, e.getMessage());
        } catch (ValueParseException e) {
            showException(e, e.getMessage());
        } catch (Exception e) {
            showException(e, "W sensie bardzo ogólnym - coś poszło nie tak :)");
        }
    }


    public void quitMainWindow() {
        System.out.println("klikniecie zeby wylaczyc");
        System.exit(0);
    }

//    public static MenuBarController createWindow(){
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(MenuBarController.class.getClass().getResource("/sample/XMLe/menu.fxml"));
//            Scene scene = null;
//            scene = new Scene(loader.load());
//            MenuBarController m = loader.getController();
//            m.scene = scene;
//            return m;
//        }
//        catch (IOException e) {
//            throw new RuntimeException();
//        }
//    }
//
//    public void showWindow(Stage stage){
//
//    }

    public void setStage(Stage stage) {
        thisStage = stage;
    }

    public String[] readColNames(File filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return br.readLine().split(",");
        }
    }

    public void showException(Exception e, String mes) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Error");
        alert.setContentText(mes);


        // Create expandable Exception.k
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);

        alert.showAndWait();
    }


    //logika do wyświetlania

    public void createActualTable(DataFrame df) {
        if (df != null) {
            System.out.println("inside table for stat");
            myTableView.getColumns().clear();
            myTableView.setItems(new ObservableDataFrame(myDF));
            int i = 0;
            for (Kolumna col : df.getKolumny()) {
                TableColumn<Value[], String> kol = new TableColumn<>(col.getNazwa());
                kol.setCellValueFactory(new PropertyValueFactory<>(col.getNazwa()));
                kol.setSortable(false);
                int j = i;

                //klasa anionimowa stuff
                kol.setCellValueFactory((new Callback<TableColumn.CellDataFeatures<Value[], String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Value[], String> p) {
                        return new SimpleStringProperty((p.getValue())[j].toString());
                    }

                    ;
                }));
                i++;
                myTableView.getColumns().add(kol);
            }
        }
    }


    // logika to wyboru statystki

    protected void fillComboBox() {
        // cmbBoxToStatistic= new ComboBox<>();
        ObservableList<String> options = FXCollections.observableArrayList("Minimum", "Maximum", "Sum", "Mean", "Var", "Std");
        //cmbBoxToStatistic.getItems().addAll("Minimum","Maximum","Sum","Mean","Var","Std");
        System.out.println("elementy dodane");
        cmbBoxToStatistic.setItems(options);
        System.out.println("elementy dodane");
        //cmbBoxToStatistic.getSelectionModel().selectFirst();
    }

    protected void fillListView() {
        ObservableList<String> colNames = FXCollections.observableArrayList();
        for (String s : myDF.zwroc_nazwy()) {
            colNames.add(s);
        }
        myListViewToStat.setItems(colNames);
        System.out.println(myListViewToStat.getSelectionModel().getSelectedItems());
    }


    //logika do wyświetlenia statystki

    public DataFrame groupBy() throws IncoherentTypeException, ZeroLengthException {
        String operation = chosenOperation();
        DataFrame.Grupator group = null;
        try {
            group = myDF.groupBy( myListViewToStat.getSelectionModel().getSelectedItems().toArray(new String[0]));
            showChartMenuAfterGroupByButton.setDisable(false);
        } catch (ZeroLengthException |NoSenseInGroupingByAllColumnsException e) {
            showException(e,e.getMessage());
        }
        try {
            switch (operation) {
                case "Minimum":
                    return group.min();

                case "Maximum":
                    return group.max();

                case "Std":
                    return group.std();

                case "Var":
                    return group.var();

                case "Sum":
                    return group.sum();

                case "Mean":
                    return group.mean();

                default:
                    return myDF;
            }
        } catch (IncoherentTypeException  e) {
            showException(e, e.getMessage());
            throw new IncoherentTypeException(e.getMessage());
        }
        catch ( ZeroLengthException e){
            showException(e, e.getMessage());
            throw new ZeroLengthException(e.getMessage());
        }

    }


        public String chosenOperation () {
            String operation = cmbBoxToStatistic.getSelectionModel().getSelectedItem().toString();
            System.out.println(operation);
            return operation;

        }

        public void showStat(DataFrame df) throws IncoherentTypeException, ZeroLengthException {
            if (df != null) {
                System.out.println("inside table for stat");
                tableVievForStat.getColumns().clear();
                tableVievForStat.setItems(new ObservableDataFrame(groupBy()));
                int i = 0;
                for (Kolumna col : df.getKolumny()) {
                    TableColumn<Value[], String> kol = new TableColumn<>(col.getNazwa());
                    kol.setCellValueFactory(new PropertyValueFactory<>(col.getNazwa()));
                    //kol.setSortable(true);
                    int j = i;

                    //klasa anionimowa stuff
                    kol.setCellValueFactory((new Callback<TableColumn.CellDataFeatures<Value[], String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<Value[], String> p) {
                            return new SimpleStringProperty((p.getValue())[j].toString());
                        }

                        ;
                    }));
                    i++;
                    tableVievForStat.getColumns().add(kol);
                }
            }
        }

    public void doStatMath() throws IncoherentTypeException, ZeroLengthException {
       if(!myListViewToStat.getSelectionModel().getSelectedItems().isEmpty()) {
            System.out.println("group by done");
            showStat(groupBy());}
        else if(myListViewToStat.getSelectionModel().getSelectedItems().isEmpty()){
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setTitle("Warning Dialog");
           alert.setHeaderText("Data Frame");

           alert.setContentText("Musisz wybrać kolumnę/y według której/ych program ma grupować!");
           alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
           alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
           alert.showAndWait();}

        else if(myListViewToStat.getSelectionModel().getSelectedItems().size()==myDF.iloscKolumn()){

       }
    }

    public void showChartMenu(ActionEvent actionEvent) {
//        if(chartController==null){
//            chartController=ComplexChartController.getInstance();
//        }
        chartController=ComplexChartController.getInstance();
        chartController.showWindow(thisStage,myDF.zwroc_nazwy(),myDF);


    }

    public void showChartMenuAfterGroupBy(ActionEvent actionEvent) throws IncoherentTypeException, ZeroLengthException {
//        if(chartController==null){
//            chartController=ComplexChartController.getInstance();
//        }
        chartController=ComplexChartController.getInstance();
        chartController.showWindow(thisStage,groupBy().zwroc_nazwy(),groupBy());

    }

}



