package com.xchange.controller;

import com.xchange.app.Navigator;
import com.xchange.service.Authentication;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SignInController {

    private Stage stage;
    private Scene scene;
    
    @FXML
    private Button ForgotPasswordButton;

    @FXML
    private Button SignInButton;

    @FXML
    private Button SignUpButton;

    @FXML
    private Label invalidMessage;

    @FXML
    private ImageView logoForPassword;

    @FXML
    private ImageView logoForUsername;

    @FXML
    private ImageView mainLogoView;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameTextField;


    @FXML
    void forgotPasswordButtonListener(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ForgotPassword.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void signInButtonListener(MouseEvent event) throws IOException {
        Navigator.setIsSignIn(true);

        if (passwordField.getText().isBlank() || usernameTextField.getText().isBlank()){
            invalidMessage.setText("Invalid login, please try again!");
        }
        else if(Authentication.userSignIn(usernameTextField.getText(), passwordField.getText()) == 1){
            invalidMessage.setText("");
            Parent root = FXMLLoader.load(getClass().getResource("/SidebarMenu.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        else if (Authentication.userSignIn(usernameTextField.getText(), passwordField.getText()) == 2){
            invalidMessage.setText("Such username does not exist, please try again!");
        }
        else if (Authentication.userSignIn(usernameTextField.getText(), passwordField.getText()) == 3){
            invalidMessage.setText("Password is wrong, please try again!");
        }

    }

    @FXML
    void signUpNowButtonListener(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/SignUp.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
