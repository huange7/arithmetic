package com.qg.www.graphic;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @ClassName ShowGraphic
 * @Description TODO
 * @Author huange7
 * @Date 2020-04-05 10:09
 * @Version 1.0
 */
public class ShowGraphic extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Arithmetic-Operation");
        primaryStage.setScene(new Scene(root, 582, 458));
        primaryStage.show();
        primaryStage.setOnCloseRequest((windowEvent)->Controller.executorService.shutdown());
    }

    public static void show(String[] args) {
        launch(args);
    }
}
