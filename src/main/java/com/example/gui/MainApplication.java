package com.example.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

    public class MainApplication extends Application {

        @SuppressWarnings("exports")
        @Override
    public void start(Stage stage) throws IOException {



        DataBaseLayer.connect();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("SplashScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("login.css")).toExternalForm());

            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.png")));
            stage.getIcons().add(image);

            stage.setTitle("TimetableXpert");
            stage.initStyle(StageStyle.UNDECORATED);

        stage.setResizable(false);
        stage.setScene(scene);

        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}