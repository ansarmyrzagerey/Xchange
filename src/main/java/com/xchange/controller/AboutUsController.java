package com.xchange.controller;

import com.xchange.app.Navigator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class AboutUsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane aboutUsAnchorPane;

    @FXML
    private Label labelOne;

    @FXML
    private Label labelTwo;

    @FXML
    private Text textThree;

    @FXML
    private Text textFour;
    @FXML
    private Text textFive;
    
    @FXML
    private Text textSix;
    
    @FXML
    private Text textSeven;


    @FXML
    void initialize() {

        if (Navigator.getUser().getDarkModeOn() == 1) {
           labelOne.setStyle("-fx-text-fill: #FFFFFF");
            labelTwo.setStyle("-fx-text-fill: #FFFFFF");
            textThree.setStyle("-fx-fill: #FFFFFF");
            textFour.setStyle("-fx-fill: #FFFFFF");
            textFive.setStyle("-fx-fill: #FFFFFF");
            textSix.setStyle("-fx-fill: #FFFFFF");
            textSeven.setStyle("-fx-fill: #FFFFFF");

        }
        else{
             labelOne.setStyle("-fx-text-fill: #000000");
             labelTwo.setStyle("-fx-text-fill: #000000");
             textThree.setStyle("-fx-fill: #000000");
             textFour.setStyle("-fx-fill: #000000");
            textFive.setStyle("-fx-fill: #000000");
            textSix.setStyle("-fx-fill: #000000");
            textSeven.setStyle("-fx-fill: #000000");
        }
    }

}
