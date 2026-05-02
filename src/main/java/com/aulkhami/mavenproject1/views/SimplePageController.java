package com.aulkhami.mavenproject1.views;

import com.aulkhami.mavenproject1.App;
import com.aulkhami.mavenproject1.views.components.SimpleCardController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class SimplePageController {

    @FXML
    private BorderPane rootPane;

    @FXML
    private SimpleCardController simpleCardController;

    @FXML
    private void initialize() {
        simpleCardController.setData("Welcome", "This is a simple reusable component inside views/components.");
    }

    @FXML
    private void backToPrimary() throws IOException {
        Parent primaryRoot = FXMLLoader.load(App.class.getResource("primary.fxml"));
        rootPane.getScene().setRoot(primaryRoot);
    }
}
