package com.timetablexpert;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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
            Stages.show(stage, root, "TimetableXpert", 700, 500);
        }
    }
    public String encryptPassword(String password){

        return PasswordUtil.hash(password);
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

                    AppState.username = username.getText();

                    AppState.path = resultSet.getString(5);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();

                    loginBtn.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
                    Parent root = loader.load();
                    HomeController home = loader.getController();
                    Stage stage = new Stage();
                    // Resizable + uniformly scaled so it looks right on any screen;
                    // the guidance banner rides above and the progress rail beside
                    // the scaled view as real chrome (never overlapping the design).
                    Stages.show(stage, root, "TimetableXpert", 1280, 730,
                            home.guidanceBanner(), home.guidanceRail());

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