package com.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class MainController {
    @FXML
    private AnchorPane bp;



    @FXML
    void Home(ActionEvent event) {

        loadPage("Home");

    }



    @FXML
    void Programform(ActionEvent event) {

        loadPage("Programform.fxml");
    }



    public void loadPage(String page){


        Parent root = null;

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(page)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}