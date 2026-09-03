package com.timetablexpert;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.Objects;

    public class MainApplication extends Application {

        @SuppressWarnings("exports")
        @Override
    public void start(Stage stage) throws IOException {


        try {
            EmbeddedDatabase.start();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("TimetableXpert");
            alert.setHeaderText("Could not start the built-in database");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            Platform.exit();
            return;
        }

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

    @Override
    public void stop() {
        // Shut the bundled MariaDB down cleanly so the next launch isn't blocked
        // by a locked data directory, then make sure the JVM actually exits
        // (JavaFX + background library threads can otherwise keep it alive, which
        // is what leaves an orphaned engine process behind).
        Thread t = new Thread(EmbeddedDatabase::stop, "embedded-db-shutdown");
        t.setDaemon(true);
        t.start();
        try {
            t.join(15_000); // don't let a stuck shutdown hang the exit forever
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        Runtime.getRuntime().halt(0);
    }

    public static void main(String[] args) {
        launch();
    }
}