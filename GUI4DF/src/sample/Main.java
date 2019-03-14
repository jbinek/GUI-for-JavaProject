package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Controllers.MenuBarController;


import java.io.IOException;

public class Main extends Application {
//    private Text actionStatus;
//    private Stage savedStage;
//    private DataFrame df = null;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/sample/XMLe/menu.fxml"));
        VBox basicWindow = loader.load();

       // BasicWindowController controller = loader.getController();

        Scene scene = new Scene(basicWindow);
        primaryStage.setScene(scene);

        //StackPane stackPane = new StackPane();
        //Button button = new Button("Przycisk");
        //stackPane.getChildren().add(button);

       // Scene scene = new Scene(stackPane, 400, 600);


       // primaryStage.setScene(scene);
        primaryStage.setHeight(700);
        primaryStage.setWidth(900);
        // primaryStage.setResizable(false); // blokuje zmianę rozmiaru
        // primaryStage.setX(0); //określa wspólrzedne połozenia okienka
        // primaryStage.setY(0);//określa wspólrzedne połozenia okienka

       // primaryStage.setOpacity(0.95);
        primaryStage.setTitle("DataFrameReader");
      //  stackPane.getChildren().add(actionStatus);
        MenuBarController men=loader.getController();
        men.setStage(primaryStage);

        primaryStage.show();



//        Button open = new Button("Otworz");
//        open.setOnAction(new SingleFcButtonListener());
//        HBox open1 = new HBox(10);
//        open1.getChildren().addAll(open);
//        Button save = new Button("Wczytaj");
//        HBox save1 = new HBox(10);
//        save1.getChildren().addAll(save);
//
//        actionStatus = new Text();
//
//
//
//        VBox vbox = new VBox(30);
//        vbox.getChildren().addAll( open1,save1,  actionStatus);
//        Scene scene = new Scene(vbox, 500, 300);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//        savedStage = primaryStage;
//    }
//
//    private class SingleFcButtonListener implements EventHandler<ActionEvent> {
//
//        @Override
//        public void handle(ActionEvent e) {
//
//            try {
//                showSingleFileChooser();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            } catch (IncoherentTypeException e1) {
//                e1.printStackTrace();
//            }
//        }
//    }
//    private void showSingleFileChooser() throws IOException, IncoherentTypeException {
//
//        FileChooser fileChooser = new FileChooser();
//        File selectedFile = fileChooser.showOpenDialog(null);
//
//        if (selectedFile != null) {
//
//            actionStatus.setText("File selected: " + selectedFile.getName());
//            df= new DataFrame("groubymulti.csv", new Class[]{StringValue.class, DateTimeValue.class, DoubleValue.class, DoubleValue.class});
//
//            actionStatus.setText("wczytuje do DF");
//
//        }
//        //System.out.println(df.toString());
//
//    }

}
}
