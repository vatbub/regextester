package com.github.vatbub.regextester.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label result;
    @FXML
    private TextField regex;
    @FXML
    private TextArea sample;

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    void initialize() {
        assert result != null : "fx:id=\"result\" was not injected: check your FXML file 'Main.fxml'.";
        assert regex != null : "fx:id=\"regex\" was not injected: check your FXML file 'Main.fxml'.";
        assert sample != null : "fx:id=\"sample\" was not injected: check your FXML file 'Main.fxml'.";

        regex.textProperty().addListener((observable, oldValue, newValue) -> checkRegex(newValue, sample.getText()));
        sample.textProperty().addListener((observable, oldValue, newValue) -> checkRegex(regex.getText(), newValue));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setMinWidth(scene.getRoot().minWidth(0) + 70);
        primaryStage.setMinHeight(scene.getRoot().minHeight(0) + 70);

        primaryStage.setScene(scene);

        // Set Icon
        // primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));

        primaryStage.show();
    }

    private void checkRegex(String regex, String sampleText) {
        String newLabelText = "";
        String newLabelStyle = "-fx-border-radius: 3;";
        try {
            if (regex.equals("")) {
                newLabelText = "Enter a regular expression!";
                newLabelStyle = newLabelStyle + "-fx-border-color: #CCCCCC;";
            } else if (sampleText.equals("")) {
                newLabelText = "Please enter a sample text!";
                newLabelStyle = newLabelStyle + "-fx-border-color: #CCCCCC;";
            } else if (sampleText.matches(regex)) {
                newLabelText = "Match";
                newLabelStyle = newLabelStyle + "-fx-border-color: #00FF00;";
            } else {
                // Check if a substring matches the regex
                boolean substringFound = false;
                for (int startIndex = 0; startIndex < sampleText.length(); startIndex++) {
                    for (int endIndex = startIndex + 1; endIndex <= sampleText.length(); endIndex++) {
                        if (sampleText.substring(startIndex, endIndex).matches(regex)) {
                            substringFound = true;
                            newLabelText = "Matching substring: " + sampleText.substring(startIndex, endIndex);
                            newLabelStyle = newLabelStyle + "-fx-border-color: #00FF00;";
                        }
                    }
                }

                if (!substringFound) {
                    newLabelText = "No match";
                    newLabelStyle = newLabelStyle + "-fx-border-color: FF0000;";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            newLabelText = "Exception: " + e.getClass().getSimpleName();
            newLabelStyle = newLabelStyle + "-fx-border-color: #FF0000;";
        }

        result.setText(newLabelText);
        result.setStyle(newLabelStyle);
    }
}

