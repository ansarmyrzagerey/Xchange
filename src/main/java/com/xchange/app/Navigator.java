package com.xchange.app;

import com.xchange.model.User;
import com.xchange.repository.Database;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Navigator
 */
public class Navigator extends Application{

    private static User user;
    private static ArrayList<String> currencies = Database.getCurrencies();
    private static boolean isSignIn;

    @Override
    public void start(Stage stage){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/SignIn.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            isSignIn = true;
        } catch(Exception e) {
            e.printStackTrace();
           }
    }

    public static void main(String[] args) {
        /*if (Database.getLatestDate().isBefore(LocalDate.now())) {
            try {
                GetCurrencyRates.saveHistoricalData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        launch(args);
    }
    
    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Navigator.user = user;
    }

    public static ArrayList<String> getCurrencies() {
        return currencies;
    }
    
public static boolean getIsSignIn() {
        return isSignIn;
    }

    public static void setIsSignIn(boolean isSign){
         isSignIn = isSign;
    }
    
}