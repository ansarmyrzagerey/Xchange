package com.xchange.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class HelpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane helpAnchorPane;

    @FXML
    void initialize() {
        assert helpAnchorPane != null : "fx:id=\"helpAnchorPane\" was not injected: check your FXML file 'Help.fxml'.";

    }

}
