package com.xchange.controller;

import com.xchange.app.Navigator;
import com.xchange.repository.Database;
import com.xchange.service.GetCurrencyRates;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class CurrencyConverterController{

    ObservableList<String> currencyList = FXCollections.observableArrayList();
    ObservableList<String> graphTimeList = FXCollections.observableArrayList("Yearly", "6 Months", "Monthly", "Weekly");


    @FXML
    private ResourceBundle resources;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private URL location;

    @FXML
    private LineChart<String, Double> currencyConvertLinearChart;

    
    @FXML
    private Label info1;

    @FXML
    private Label info2;

    @FXML
    private Label info3;

    @FXML
    private ComboBox currencyConverterDateDropdown;

    @FXML
    private TextField fromCurreencyTextField;

    @FXML
    private Label toCurreencyLabelField;

    @FXML
    private Label welcomeText;
    
    @FXML
    private ComboBox convertFromBox;

    @FXML
    private ComboBox convertToBox;

    @FXML
    private ImageView fromCurrencyFlag;

    @FXML
    private ImageView toCurrencyFlag;

    ArrayList<String> infoBoxes;

    double theRate;

    @FXML
    void initialize() throws Exception {

        infoBoxes = getRandomInfo();

        info1.setText("   " + infoBoxes.get(0));
        info2.setText("   " + infoBoxes.get(1));
        info3.setText("   " + infoBoxes.get(2));

        currencyConverterDateDropdown.setItems(graphTimeList);

        theRate = GetCurrencyRates.calculateLatest(Navigator.getUser().getCurDefaultFrom(), Navigator.getUser().getCurDefaultTo());
        fromCurreencyTextField.setText("1");
        toCurreencyLabelField.setText("" + theRate);

        String imagePath = Database.getCurrencyFlag(Navigator.getUser().getCurDefaultFrom());
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        fromCurrencyFlag.setImage(image);

        String imagePath2 = Database.getCurrencyFlag(Navigator.getUser().getCurDefaultTo());
        File file2 = new File(imagePath2);
        Image image2 = new Image(file2.toURI().toString());
        toCurrencyFlag.setImage(image2);
        
        for (int i = 0; i < Navigator.getCurrencies().size(); i ++){
            currencyList.add(Navigator.getCurrencies().get(i));
        }

        welcomeText.setText("Welcome to XChange " + Navigator.getUser().getFirstName() + " " + Navigator.getUser().getLastName());
        convertFromBox.setValue(Navigator.getUser().getCurDefaultFrom());
        convertFromBox.setItems(currencyList);

        convertToBox.setValue(Navigator.getUser().getCurDefaultTo());
        convertToBox.setItems(currencyList);

        currencyConverterDateDropdown.setValue("Monthly");
        currencyConverterDateDropdownOnAction();
    }

    
    @FXML
    void fromCurrencyDropdownAction(ActionEvent event) throws Exception {
        String imagePath = Database.getCurrencyFlag((String)convertFromBox.getValue());
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        fromCurrencyFlag.setImage(image);

        theRate = GetCurrencyRates.calculateLatest((String)convertFromBox.getValue(), (String)convertToBox.getValue());
        currencyConverterDateDropdown.setValue("Monthly");
        currencyConverterDateDropdownOnAction();
    }

    @FXML
    void toCurrencyDropdownAction(ActionEvent event) throws Exception {
        String imagePath2 = Database.getCurrencyFlag((String)convertToBox.getValue());
        File file2 = new File(imagePath2);
        Image image2 = new Image(file2.toURI().toString());
        toCurrencyFlag.setImage(image2);

        theRate = GetCurrencyRates.calculateLatest((String)convertFromBox.getValue(), (String)convertToBox.getValue());

        currencyConverterDateDropdown.setValue("Monthly");
        currencyConverterDateDropdownOnAction();
    }

    @FXML
    void changeButtonListener(MouseEvent event) {
        ImageView tempForImage;
        Object tempForCurrency;

        tempForImage = fromCurrencyFlag;
        fromCurrencyFlag.setImage(toCurrencyFlag.getImage());
        toCurrencyFlag.setImage(tempForImage.getImage());

        tempForCurrency = convertFromBox.getValue();
        convertFromBox.setValue(convertToBox.getValue());
        convertToBox.setValue(tempForCurrency);

        fromCurreencyTextField.setText(toCurreencyLabelField.getText());
        toCurreencyLabelField.setText(" "+Double.parseDouble(fromCurreencyTextField.getText()) * theRate);
    }

    @FXML
    void currencyConverterAmountListener(KeyEvent event) {

        String fromCurrencyInput = fromCurreencyTextField.getText();

        if ( fromCurrencyInput.equals("")) {

            toCurreencyLabelField.setText("");
        }
        else if (!isValidInput(fromCurrencyInput)) {
            int length = fromCurrencyInput.length()-1;
            if(length >= 0){
                fromCurreencyTextField.setText(fromCurrencyInput.substring(0, length));
            
            }
            fromCurreencyTextField.positionCaret(fromCurreencyTextField.getText().length());
            event.consume();
    }

    else {
            toCurreencyLabelField.setText("" + (theRate * Double.parseDouble(fromCurrencyInput)));
        }
       
    }

    @FXML
    //“Chatgpt.” ChatGPT, openai.com/chatgpt. Accessed 18 Dec. 2023. I mentioned before but I will mention here too as I used here. Only used for make the values seen near the cursor. 
    public void currencyConverterDateDropdownOnAction() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().minusDays(1);

        if (currencyConverterDateDropdown.getValue().equals("Yearly")) {
            startDate = startDate.minusDays(365);
        } else if (currencyConverterDateDropdown.getValue().equals("6 Months")) {
            startDate = startDate.minusDays(180);
        } else if (currencyConverterDateDropdown.getValue().equals("Monthly")) {
            startDate = startDate.minusDays(30);
        } else if (currencyConverterDateDropdown.getValue().equals("Weekly")) {
            startDate = startDate.minusDays(7);
        }

        ArrayList<Double> values = GetCurrencyRates.calculateHistorical(
        convertFromBox.getValue().toString(), convertToBox.getValue().toString(), startDate, endDate);
        ArrayList<LocalDate> dates = GetCurrencyRates.getHistoricalDates(values, endDate);

        XYChart.Series<String, Double> series = new XYChart.Series<>();

        currencyConvertLinearChart.getData().clear();

        for (int i = 0; i < values.size(); i++) {
            series.getData().add(new XYChart.Data<>(Integer.toString(i + 1), values.get(i)));
        }

        currencyConvertLinearChart.setCreateSymbols(false);
        currencyConvertLinearChart.getData().add(series);

        currencyConvertLinearChart.getXAxis().setTickLabelsVisible(false);

        Tooltip tooltip = new Tooltip();
        Tooltip.install(currencyConvertLinearChart, tooltip);

        currencyConvertLinearChart.setOnMouseMoved(e -> {
            double mouseX = e.getX();

            String nearestDate = "";
            Double nearestValue = null;
            double nearestDistance = Double.MAX_VALUE;

            for (XYChart.Series<String, Double> s : currencyConvertLinearChart.getData()) {
                for (XYChart.Data<String, Double> d : s.getData()) {
                    double displayX = currencyConvertLinearChart.getXAxis().getDisplayPosition(d.getXValue());

                    double distance = Math.abs(displayX - mouseX);

                    if (distance < nearestDistance) {
                        nearestDistance = distance;
                        nearestDate = dates.get(Integer.parseInt(d.getXValue()) - 1).toString();
                        nearestValue = d.getYValue();
                    }
                }
            }

            if (nearestValue != null) {
                tooltip.setText("Date: " + nearestDate + "\nValue: " + nearestValue);
                tooltip.show(currencyConvertLinearChart, e.getScreenX(), e.getScreenY() + 20);
            } else {
                tooltip.hide();
            }
        });

        currencyConvertLinearChart.setOnMouseExited(e -> {
            tooltip.hide();
        });
    }

    public static ArrayList<String> getRandomInfo() {
        ArrayList<String> allInfo = new ArrayList<String>();
        ArrayList<String> randomInfo = new ArrayList<String>();

        try {
            Scanner sc = new Scanner(new File("information.txt"));
            
            while (sc.hasNextLine()) {
                if (sc.nextLine().equals("")) {
                    break;
                }
                allInfo.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            int index = rand.nextInt(allInfo.size());
            randomInfo.add(allInfo.get(index));
            allInfo.remove(index);
        }

        return randomInfo;
    }

    private boolean isValidInput(String text) {
        try {
            Double.parseDouble(text);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
