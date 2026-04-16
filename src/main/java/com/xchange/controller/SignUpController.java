package com.xchange.controller;

import com.xchange.service.Authentication;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

public class SignUpController {

    private Stage stage;
    private Scene scene;

    @FXML
    private TextField favouriteColorTextField;

    @FXML
    private ImageView logoForFavouriteColor;

    @FXML
    private ImageView logoForMotherName;

    @FXML
    private ImageView logoForName;

    @FXML
    private ImageView logoForPassword;

    @FXML
    private ImageView logoForRepeatPassword;

    @FXML
    private ImageView logoForSurname;

    @FXML
    private ImageView logoForUsername;

    @FXML
    private ImageView mainLogo;

    @FXML
    private TextField motherNameTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Label invalidMessage;

    @FXML
    private ImageView backButtonImage;

    @FXML
    void forgotPasswordBackButtonListener(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/SignIn.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void signUpButtonListener(MouseEvent event) throws IOException {
        if (usernameTextField.getText().isBlank() || passwordField.getText().isBlank() || repeatPasswordField.getText().isBlank() || nameTextField.getText().isBlank() || surnameTextField.getText().isBlank() || motherNameTextField.getText().isBlank() || favouriteColorTextField.getText().isBlank()){
            invalidMessage.setText("Please enter all of the required information!");
        }
        else if(Authentication.userSignUp(usernameTextField.getText(), passwordField.getText(), repeatPasswordField.getText(), nameTextField.getText(), surnameTextField.getText(), motherNameTextField.getText(), favouriteColorTextField.getText()) == 1){
            Parent root = FXMLLoader.load(getClass().getResource("/SignIn.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(Authentication.userSignUp(usernameTextField.getText(), passwordField.getText(), repeatPasswordField.getText(), nameTextField.getText(), surnameTextField.getText(), motherNameTextField.getText(), favouriteColorTextField.getText()) == 2){
            invalidMessage.setText("This username already exist, please try another one!");
        }
        else{
            invalidMessage.setText("The repeated password is different, please try again!");
        }
    }
}
