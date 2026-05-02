package com.aulkhami.mavenproject1.views.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SimpleCardController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label bodyLabel;

    public void setData(String title, String body) {
        titleLabel.setText(title);
        bodyLabel.setText(body);
    }
}
