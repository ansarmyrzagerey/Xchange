package com.xchange.controller;

import com.xchange.service.Authentication;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ForgotPasswordController {

    private Stage stage;
    private Scene scene;
    
    @FXML
    private TextField favouriteColorTextField;

    @FXML
    private Label invalidMessage;

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
    private ImageView mainLogo;

    @FXML
    private TextField motherNameTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField repeatPasswordField;

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
    void changePasswordButtonListener(MouseEvent event) throws IOException {
        if(usernameTextField.getText().isBlank() || motherNameTextField.getText().isBlank() || favouriteColorTextField.getText().isBlank() || passwordField.getText().isBlank() || repeatPasswordField.getText().isBlank()){
            invalidMessage.setText("Please enter all of the required information!");
        }
        else if(Authentication.forgotPassword(usernameTextField.getText(), motherNameTextField.getText(), favouriteColorTextField.getText(), passwordField.getText(), repeatPasswordField.getText()) == 1){
            Parent root = FXMLLoader.load(getClass().getResource("/SignIn.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(Authentication.forgotPassword(usernameTextField.getText(), motherNameTextField.getText(), favouriteColorTextField.getText(), passwordField.getText(), repeatPasswordField.getText()) == 2){
            invalidMessage.setText("Such username does not exist, please try again!");
        }
        else if(Authentication.forgotPassword(usernameTextField.getText(), motherNameTextField.getText(), favouriteColorTextField.getText(), passwordField.getText(), repeatPasswordField.getText()) == 3){
            invalidMessage.setText("Answer to the security question is wrong, please try again!");
        }
        else{
            invalidMessage.setText("The repeated password is not the same as the original");
        }
    }

}
