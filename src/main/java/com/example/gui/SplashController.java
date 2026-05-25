package com.example.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SplashController implements Initializable {

    @FXML
    private AnchorPane pane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Start a new thread for the splash screen
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            // After the delay, update JavaFX UI components
            Platform.runLater(() -> {


                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setScene(scene);

                stage.setTitle("TimetableXpert");
                stage.centerOnScreen();
                stage.setResizable(false);

                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.png")));
                stage.getIcons().add(image);
                stage.show();
                pane.getScene().getWindow().hide();
            });
        }).start();
    }
}
