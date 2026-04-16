package com.xchange.service;

import com.xchange.app.Navigator;
import com.xchange.model.User;
import com.xchange.repository.Database;

/**
 * Authentication
 */
public class Authentication {

    //1: username and password are correct, proceed to the next page
    //2: username does not exist
    //3: password is wrong
    public static int userSignIn(String userName, String password) {
        if (Database.checkUsername(userName)) {
            if (Database.checkPassword(userName, password)) {
                User user = Database.getUser(userName);
                Navigator.setUser(user);
                return 1;
            }
            else {
                return 3;
            }
        }
        else {
            return 2;
        }
    }

    //1: create user, proceed to the next page
    //2: username already exists
    //3: repeated password is wrong
    public static int userSignUp(String userName, String password, String repeatPassword, String firstName, String lastName, String mothersName, String favouriteColor) {
        if (!Database.checkUsername(userName)) {
            if (password.equals(repeatPassword)) {
                User user = new User(userName, password, firstName, lastName, mothersName, favouriteColor);
                Navigator.setUser(user);
                return 1;
            }
            else {
                return 3;
            }
        }
        else {
            return 2;
        }
    }

    //1: correct values, change password
    //2: username does not exist
    //3: wrong security answer
    //4: repeated password is wrong
    public static int forgotPassword(String userName, String mothersName, String favouriteColor, String newPassword, String repeatPassword) {
        if (Database.checkUsername(userName)) {
            if (Database.checkMothersName(userName, mothersName) && Database.checkFavouriteColor(userName, favouriteColor)) {
                if (newPassword.equals(repeatPassword)) {
                    Database.updatePassword(userName, newPassword);
                    return 1;
                }
                else {
                    return 4;
                }
            }
            else {
                return 3;
            }
        }
        else {
            return 2;
        }
    }


}