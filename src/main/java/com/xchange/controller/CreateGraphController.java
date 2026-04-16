package com.xchange.controller;

import com.xchange.app.Navigator;
import com.xchange.model.Graph;
import com.xchange.repository.Database;
import com.xchange.service.GetCurrencyRates;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CreateGraphController {

    ObservableList<String> currencyList = FXCollections.observableArrayList();
    private Stage stage;
    private Scene scene;
    private Graph createdGraph;
    private static Stage popupStage = new Stage();
    private LocalDate startDate = LocalDate.of(2013,01, 01);
    private LocalDate endDate = LocalDate.now();
    
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private AnchorPane createAnchorPane;

    @FXML
    private URL location;

    @FXML
    private LineChart<String, Double> linearChart;

    @FXML
    private ComboBox convertFromBox;

    @FXML
    private ComboBox convertToBox;

    @FXML
    private ImageView fromCurrencyFlag;

    @FXML
    private ImageView toCurrencyFlag;

    @FXML
    private DatePicker currencyFromDatePicker;

    @FXML
    private DatePicker currencyToDatePicker;

    private static Graph graph;

    public static Graph graphObjectGetter(){
        return graph;
    }

    @FXML
    void initialize() {
        
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
        convertFromBox.setValue(Navigator.getUser().getCurDefaultFrom());
        convertFromBox.setItems(currencyList);

        convertToBox.setValue(Navigator.getUser().getCurDefaultTo());
        convertToBox.setItems(currencyList);
        
        currencyFromDatePicker.setDayCellFactory(disableDatePicker(startDate, endDate));
        currencyToDatePicker.setDayCellFactory(disableDatePicker(startDate, endDate));

    }
    
    private Callback <DatePicker, DateCell> disableDatePicker (LocalDate startDate, LocalDate endDate){  //“26 Date Picker.” 26 Date Picker (Release 8), Oracle, docs.oracle.com/javase/8/javafx/user-interface-tutorial/date-picker.htm. Accessed 19 Dec. 2023.
        
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                           
                            if (item.isBefore(startDate)) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                            } 
                            if (item.isAfter(endDate))  {

                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");

                            }
                    }
                };
            }
        };

        return dayCellFactory;
    }

    @FXML
    void fromCurrencyDropdownAction(ActionEvent event) {
        String imagePath = Database.getCurrencyFlag((String)convertFromBox.getValue());
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        fromCurrencyFlag.setImage(image);
    }

    @FXML
    void toCurrencyDropdownAction(ActionEvent event) {
        String imagePath = Database.getCurrencyFlag((String)convertToBox.getValue());
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        toCurrencyFlag.setImage(image);
    }


    @FXML
    void changeButtonAction(MouseEvent event) {
        ImageView tempForImage;
        Object tempForCurrency;

        tempForImage = fromCurrencyFlag;
        fromCurrencyFlag.setImage(toCurrencyFlag.getImage());
        toCurrencyFlag.setImage(tempForImage.getImage());

        tempForCurrency = convertFromBox.getValue();
        convertFromBox.setValue(convertToBox.getValue());
        convertToBox.setValue(tempForCurrency);
    }

    @FXML
    //“Chatgpt.” ChatGPT, openai.com/chatgpt. Accessed 18 Dec. 2023. Only used for make the values seen near the cursor.
    void generateNowButtonAction(MouseEvent event) {

        graph = new Graph((String)convertFromBox.getValue(), (String)convertToBox.getValue(), currencyFromDatePicker.getValue(), currencyToDatePicker.getValue()); 
        
        LocalDate startDate = currencyFromDatePicker.getValue();
        LocalDate endDate = currencyToDatePicker.getValue();

        
        ArrayList<Double> values = GetCurrencyRates.calculateHistorical(
            convertFromBox.getValue().toString(), convertToBox.getValue().toString(), startDate, endDate);
        ArrayList<LocalDate> dates = GetCurrencyRates.getHistoricalDates(values, endDate);

        XYChart.Series<String, Double> series = new XYChart.Series<>();

        linearChart.getData().clear();

        for (int i = 0; i < values.size(); i++) {
            series.getData().add(new XYChart.Data<>(Integer.toString(i + 1), values.get(i)));
        }

        linearChart.setCreateSymbols(false);
        linearChart.getData().add(series);

        linearChart.getXAxis().setTickLabelsVisible(false);

        Tooltip tooltip = new Tooltip();
        Tooltip.install(linearChart, tooltip);

        linearChart.setOnMouseMoved(e -> {
            double mouseX = e.getX();

            String nearestDate = "";
            Double nearestValue = null;
            double nearestDistance = Double.MAX_VALUE;

            for (XYChart.Series<String, Double> s : linearChart.getData()) {
                for (XYChart.Data<String, Double> d : s.getData()) {
                    double displayX = linearChart.getXAxis().getDisplayPosition(d.getXValue());

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
                tooltip.show(linearChart, e.getScreenX(), e.getScreenY() + 20);
            } else {
                tooltip.hide();
            }
        });

        linearChart.setOnMouseExited(e -> {
            tooltip.hide();
        });



    }

    @FXML
    //“Chatgpt.” ChatGPT, openai.com/chatgpt. Accessed 18 Dec. 2023. Only used for taking the graph as image and saving it to the computer.
    void downloadButtonAction(MouseEvent event) {

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(); 

    LineChart<String, Number> linearChart = new LineChart<>(xAxis, yAxis);

        LocalDate startDate = currencyFromDatePicker.getValue();
        LocalDate endDate = currencyToDatePicker.getValue();

        ArrayList<Double> values = GetCurrencyRates.calculateHistorical(
        convertFromBox.getValue().toString(), convertToBox.getValue().toString(), startDate, endDate);
        ArrayList<LocalDate> dates = GetCurrencyRates.getHistoricalDates(values, endDate);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        linearChart.getData().clear();
        yAxis.setForceZeroInRange(false);
        linearChart.setHorizontalGridLinesVisible(false);
        linearChart.setVerticalGridLinesVisible(false);
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);

        for (int i = 0; i < values.size(); i++) {
            series.getData().add(new XYChart.Data<>(Integer.toString(i + 1), values.get(i)));
        }

        linearChart.setCreateSymbols(false);
        linearChart.getData().add(series);

        linearChart.getXAxis().setTickLabelsVisible(false);

        Group chartGroup = new Group(linearChart);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save your linear graph");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {

            WritableImage image = chartGroup.snapshot(new SnapshotParameters(), null);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    
    @FXML
    void saveButtonAction(MouseEvent event) throws IOException {
        FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/SaveGraphFrame.fxml"));
        Parent root = rootLoader.load();

        Stage mainStage = new Stage();
        popupStage.setTitle("Save Graph");

        Scene popupScene = new Scene(root);
        popupStage.setScene(popupScene);
        popupStage.show();
            
    }

    public static Stage getStage () {
        return popupStage;
    }


    @FXML
    void startDateListener(ActionEvent event) {
        this.startDate = currencyFromDatePicker.getValue();

        if(startDate == null){
            this.startDate = LocalDate.of(2013,01, 01);
        }
        currencyToDatePicker.setDayCellFactory(disableDatePicker(this.startDate, this.endDate));
           
    }
    
    @FXML
    void endDateListener(ActionEvent event) {
        this.endDate = currencyToDatePicker.getValue();

        if (endDate == null){
            this.endDate = LocalDate.now();
        }

        currencyFromDatePicker.setDayCellFactory(disableDatePicker(this.startDate, this.endDate));
    }

}
