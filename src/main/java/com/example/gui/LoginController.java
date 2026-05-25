package com.example.gui;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController extends DataBaseLayer implements Initializable {

    @FXML
    private PasswordField password;
    @FXML
    private Button register_btn;
    @FXML
    private Button loginBtn;
    private AnchorPane main_form;
    @FXML
    private TextField username;
    @FXML
    public void switchForm(ActionEvent event){


        if (event.getSource() == register_btn){


            register_btn.getScene().getWindow().hide();
            Parent root = null;
            try {

                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Register.fxml")));
            } catch (IOException e) {

                throw new RuntimeException(e);

            }
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.centerOnScreen();

            stage.setTitle("TimetableXpert");
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.png")));
            stage.getIcons().add(image);


            stage.setScene(scene);
            stage.show();
        }
    }
    public String encryptPassword(String password){

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] messageDigest = md.digest(password.getBytes());

        BigInteger bigInt = new BigInteger(1,messageDigest);

        return bigInt.toString(16);
    }

    public void loginAdmin(){

        try{
            Alert alert;

           if (DataBaseLayer.connect() == null){

               alert = new Alert(AlertType.ERROR);
               alert.setTitle("Error Message");
               alert.setHeaderText(null);
               alert.setContentText("Database is not connected");
               alert.showAndWait();
               return;


           }

            statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM adminTable WHERE userName = ? && password = ?");
            statement.setString(1, username.getText());
            statement.setString(2, encryptPassword(password.getText()));

            resultSet = statement.executeQuery();

            if(username.getText().isEmpty() || password.getText().isEmpty()){

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            }else{

                if(resultSet.next()){

                    getData.username = username.getText();

                    getData.path = resultSet.getString(5);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();

                    loginBtn.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml")));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setScene(scene);

                    stage.setTitle("TimetableXpert");
               //    stage.setWidth(1160);
                //   stage.setHeight(687);
                    stage.centerOnScreen();
                    stage.setResizable(false);

                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.png")));
                    stage.getIcons().add(image);
                    stage.show();

                }else{

                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password or register yourself first");
                    alert.showAndWait();

                }
            }

        }catch(Exception e){e.printStackTrace();}

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}