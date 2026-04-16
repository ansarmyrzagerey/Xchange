package com.xchange.model;

import com.xchange.repository.Database;

import java.util.ArrayList;

/**
 * User
 */
public class User {

    private int userID;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String mothersName;
    private String favouriteColor;
    private String curDefaultFrom;
    private String curDefaultTo;
    private int darkModeOn;
    private ArrayList<Graph> graphs;

    //constructor for first creation
    public User(String userName, String password, String firstName, String lastName, String mothersName, String favouriteColor) {
        this.userID = Database.getMaxID() + 1;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mothersName = mothersName;
        this.favouriteColor = favouriteColor;
        this.curDefaultFrom = "USD";
        this.curDefaultTo = "TRY";
        darkModeOn = 0;
        graphs = new ArrayList<Graph>();
        Database.insertNewUser(this);
    }

    //constructor for getting user from database
    public User(int userId, String userName, String password, String firstName, String lastName, String mothersName, String favouriteColor, String currencyDefaultFrom, String currencyDefaultTo, int darkModeOn, ArrayList<Graph> graphs) {
        this.userID = userId;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mothersName = mothersName;
        this.favouriteColor = favouriteColor;
        this.curDefaultFrom = currencyDefaultFrom;
        this.curDefaultTo = currencyDefaultTo;
        this.darkModeOn = darkModeOn;
        this.graphs = graphs;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        Database.updateUserName(userID, userName);
    }

    public void setPassword(String password) {
        this.password = password;
        Database.updatePassword(userID, password);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        Database.updateFirstName(userID, firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        Database.updateLastName(userID, lastName);
    }

    public void setCurDefaultFrom(String curDefaultFrom) {
        this.curDefaultFrom = curDefaultFrom;
        Database.updateDefaultFrom(userID, curDefaultFrom);
    }

    public void setCurDefaultTo(String curDefaultTo) {
        this.curDefaultTo = curDefaultTo;
        Database.updateDefaultTo(userID, curDefaultTo);
    }

    public void setDarkModeOn(int darkModeOn) {
        this.darkModeOn = darkModeOn;
        Database.updateDarkModeOn(userID, darkModeOn);
    }

    public void addGraph(Graph graph) {
        graphs.add(graph);
    }

    public String getCurDefaultFrom() {
        return curDefaultFrom;
    }

    public String getCurDefaultTo() {
        return curDefaultTo;
    }

    public String getFavouriteColor() {
        return favouriteColor;
    }

    public String getFirstName() {
        return firstName;
    }

    public ArrayList<Graph> getGraphs() {
        return graphs;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMothersName() {
        return mothersName;
    }

    public String getPassword() {
        return password;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public int getDarkModeOn() {
        return darkModeOn;
    }

    //returns true if another graph with same name exist
    public boolean checkGraphName(String graphName) {
        for (Graph graph : graphs) {
            if (graph.getGraphName().equals(graphName)) {
                return true;
            }
        }
        return false;
    }

    public void deleteGraph(String graphName) {
        for (int i = 0; i < graphs.size(); i++) {
            if (graphs.get(i).getGraphName().equals(graphName)) {
                graphs.remove(i);
            }
        }
        Database.deleteGraph(graphName);
    }
    
}