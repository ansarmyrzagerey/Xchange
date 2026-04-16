package com.xchange.controller;

import com.xchange.app.Navigator;
import com.xchange.repository.Database;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;

public class SettingsController {

    ObservableList<String> currencyList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button changePasswordButton;

    @FXML
    private Button applyChangesButton;

    @FXML
    private AnchorPane changePasswordPane;

    @FXML
    private Label changePasswordText;

    @FXML
    private ImageView coinImage;

    @FXML
    private Label defaultText;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private Label firstNameText;

    @FXML
    private Label fromText;

    @FXML
    private Label wrongPassword;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private Label lastNameText;

    @FXML
    private Label applyChangesLabel;

    @FXML
    private RadioButton lightRadioButton;
    
    @FXML
    private RadioButton darkRadioButton;

    @FXML
    private ImageView moneyImage;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private Label newPasswordText;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private Label oldPasswordText;

    @FXML
    private Label profileText;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    private Label repeatPasswordText;

    @FXML
    private AnchorPane settingsAnchorPane;

    @FXML
    private AnchorPane settingsBacground;

    @FXML
    private Label toText;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Label usernameText;

    @FXML
    private Label visibilityText;

    @FXML
    private ComboBox fromDefaultComboBox;

    @FXML
    private ComboBox toDefaultComboBox;

    CurrencyConverterController currencyConverterController = new CurrencyConverterController();

    public RadioButton getLightRadioButton(){
        return lightRadioButton;
    }
    public RadioButton getDarkRadioButton(){
        return darkRadioButton;
    }

    @FXML
    void initialize() {
        Navigator.setIsSignIn(false);

        for (int i = 0; i < Navigator.getCurrencies().size(); i ++){
            currencyList.add(Navigator.getCurrencies().get(i));
        }

        fromDefaultComboBox.setValue(Navigator.getUser().getCurDefaultFrom());
        fromDefaultComboBox.setItems(currencyList);

        toDefaultComboBox.setValue(Navigator.getUser().getCurDefaultTo());
        toDefaultComboBox.setItems(currencyList);

        final ToggleGroup group = new ToggleGroup();
        darkRadioButton.setToggleGroup(group);
        lightRadioButton.setToggleGroup(group);

        firstNameTextField.setText(Navigator.getUser().getFirstName());
        lastNameTextField.setText(Navigator.getUser().getLastName());
        usernameTextField.setText(Navigator.getUser().getUserName());
        if (Navigator.getUser().getDarkModeOn() == 1) {
            Database.updateDarkModeOn(Navigator.getUser().getUserID(), 1);
            darkRadioButton.setSelected(true);
            darkMode();
        }
        else{
            Database.updateDarkModeOn(Navigator.getUser().getUserID(), 0);
            lightRadioButton.setSelected(true);
            lightMode();
        }
    }

    public void darkMode(){
        changePasswordText.setStyle("-fx-text-fill: white");
        defaultText.setStyle("-fx-text-fill: white");
        firstNameText.setStyle("-fx-text-fill: white");
        fromText.setStyle("-fx-text-fill: white");
        wrongPassword.setStyle("-fx-text-fill: white");
        lastNameText.setStyle("-fx-text-fill: white");
        applyChangesLabel.setStyle("-fx-text-fill: white");
        newPasswordText.setStyle("-fx-text-fill: white");
        oldPasswordText.setStyle("-fx-text-fill: white");
        profileText.setStyle("-fx-text-fill: white");
        repeatPasswordText.setStyle("-fx-text-fill: white");
        settingsBacground.setStyle("-fx-background-color: #00072D");
        toText.setStyle("-fx-text-fill: white");
        usernameText.setStyle("-fx-text-fill: white");
        visibilityText.setStyle("-fx-text-fill: white");
        lightRadioButton.setStyle("-fx-text-fill: white");
        darkRadioButton.setStyle("-fx-text-fill: white");
    }

    public void lightMode(){
        changePasswordText.setStyle("-fx-text-fill: black");
        defaultText.setStyle("-fx-text-fill: black");
        firstNameText.setStyle("-fx-text-fill: black");
        fromText.setStyle("-fx-text-fill: black");
        wrongPassword.setStyle("-fx-text-fill: black");
        lastNameText.setStyle("-fx-text-fill: black");
        applyChangesLabel.setStyle("-fx-text-fill: black");
        newPasswordText.setStyle("-fx-text-fill: black");
        oldPasswordText.setStyle("-fx-text-fill: black");
        profileText.setStyle("-fx-text-fill: black");
        repeatPasswordText.setStyle("-fx-text-fill: black");
        settingsBacground.setStyle("-fx-background-color: #AFD3E2");
        toText.setStyle("-fx-text-fill: black");
        usernameText.setStyle("-fx-text-fill: black");
        visibilityText.setStyle("-fx-text-fill: black");
        lightRadioButton.setStyle("-fx-text-fill: black");
        darkRadioButton.setStyle("-fx-text-fill: black");
    }
    

    @FXML
    void defaultFromDropdownAction(ActionEvent event) {
        Navigator.getUser().setCurDefaultFrom((String)fromDefaultComboBox.getValue());
    }

    @FXML
    void defaultToDropdownAction(ActionEvent event) {
        Navigator.getUser().setCurDefaultTo((String)toDefaultComboBox.getValue());
    }


    @FXML
    void visibilityAction(MouseEvent event) throws IOException {
        CreateGraphController createGraphController = new CreateGraphController();

        if (darkRadioButton.isSelected()) {
            Navigator.getUser().setDarkModeOn(1);
        }
        else{
            Navigator.getUser().setDarkModeOn(0);
        }

        if (Navigator.getUser().getDarkModeOn() == 1 || darkRadioButton.isSelected()){
            Database.updateDarkModeOn(Navigator.getUser().getUserID(), 1);
            darkMode();
        }
        else if((Navigator.getUser().getDarkModeOn() == 0 || lightRadioButton.isSelected())){
            Database.updateDarkModeOn(Navigator.getUser().getUserID(), 0);
            lightMode();
        }

        Parent root = FXMLLoader.load(getClass().getResource("/SidebarMenu.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void changePasswordButtonAction(ActionEvent event) {
        if (newPasswordField.getText().toString().equals(repeatPasswordField.getText().toString()) && Database.checkPassword(Navigator.getUser().getUserName(), oldPasswordField.getText())) {
            Navigator.getUser().setPassword(newPasswordField.getText());
            wrongPassword.setTextFill(Color.color(0, 1, 0));
            wrongPassword.setText("Succesfully changed");
        }
        else{
            wrongPassword.setTextFill(Color.color(1, 0, 0));
            wrongPassword.setText("Retry to change the password");
        }
        
    }

    @FXML
    void applyChangesButtonAction(MouseEvent event) throws IOException {

        if (!usernameTextField.getText().equals(Navigator.getUser().getUserName()) && Database.checkUsername(usernameTextField.getText())) {
            applyChangesLabel.setTextFill(Color.color(1, 0, 0));
            applyChangesLabel.setText("Username already exist");
        }
        else {
            Navigator.getUser().setFirstName(firstNameTextField.getText());
            Navigator.getUser().setLastName(lastNameTextField.getText());
            Navigator.getUser().setUserName(usernameTextField.getText());

            Parent root = FXMLLoader.load(getClass().getResource("/SidebarMenu.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();

            wrongPassword.setTextFill(Color.color(0, 1, 0));
            applyChangesLabel.setText("Succesfully Changed");
        }
        
    }




}
