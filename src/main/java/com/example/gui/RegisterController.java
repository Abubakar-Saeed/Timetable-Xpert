package com.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterController extends DataBaseLayer implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private Button back_btn;
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;
    @FXML
    private ImageView profile_image;
    @FXML
    private Button import_btn;
    @FXML
    private Button registerBtn;




    @FXML
    public void registerUser() {


        String userName = name.getText();
        String userEmail = email.getText();
        String userPassword = password.getText();

        Alert alert;

        if (userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || profile_image.getImage() == null) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields and upload a profile picture");
            alert.showAndWait();

        } else {
            try {

                DataBaseLayer.connect();

                // Check if the email is already registered
                PreparedStatement checkStmt = DataBaseLayer.connection.prepareStatement("SELECT * FROM adminTable WHERE email = ? or username = ?");
                checkStmt.setString(1, userEmail);
                checkStmt.setString(2,userName);
                ResultSet checkResult = checkStmt.executeQuery();

                if (checkResult.next()) {

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Email/Name is already registered");
                    alert.showAndWait();

                } else {

                    // Insert the new user into the database
                    PreparedStatement statement = DataBaseLayer.connection.prepareStatement("INSERT INTO adminTable (userName, email, password,image) VALUES (?, ?, ?,?)");
                    statement.setString(1, userName);
                    statement.setString(2, userEmail);
                    statement.setString(3, encryptPassword(userPassword));
                    String url = getData.path;
                    url.replace("\\","\\\\");
                    statement.setString(4,url);

                    statement.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Registered");
                    alert.showAndWait();

                    // Redirect to login form
                    registerBtn.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setScene(scene);
                    stage.show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleBackButton() {

        back_btn.getScene().getWindow().hide();
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

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

    public void uploadProfilePicture() {


        FileChooser open = new FileChooser();
        open.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = open.showOpenDialog(null);

        if (file != null) {

            getData.path = file.getAbsolutePath();
            Image image = new Image(file.toURI().toString());

            profile_image.setImage(image);


        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image image = new Image(String.valueOf(getClass().getResource("default.jpeg")));
        profile_image.setImage(image);
        getData.path = image.getUrl();

    }
}
