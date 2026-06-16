package com.aulkhami.pakupos.app.usermanagement;

import com.aulkhami.pakupos.App;
import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.models.entities.User;
import com.aulkhami.pakupos.views.View;
import java.io.IOException;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class UserManagementView implements View {

    private UserManagementModel model;
    private UserManagementInteractor interactor;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField phoneField;

    @FXML
    private VBox userListVBox;

    @Override
    public void setModel(Model model) {
        this.model = (UserManagementModel) model;

        nameField.textProperty().bindBidirectional(this.model.nameProperty());
        emailField.textProperty().bindBidirectional(this.model.emailProperty());
        passwordField.textProperty().bindBidirectional(this.model.passwordProperty());
        phoneField.textProperty().bindBidirectional(this.model.phoneProperty());

        this.model.getUsers().addListener((ListChangeListener<User>) change -> {
            renderUsers();
        });

        renderUsers();
    }

    @Override
    public void setInteractor(Interactor interactor) {
        this.interactor = (UserManagementInteractor) interactor;
        this.interactor.loadUsers();
    }

    private void renderUsers() {
        userListVBox.getChildren().clear();
        for (User user : model.getUsers()) {
            userListVBox.getChildren().add(createUserItem(user));
        }
    }

    private Node createUserItem(User user) {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("mobile-recent-item");
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10, 15, 10, 15));

        VBox info = new VBox();
        HBox.setHgrow(info, Priority.ALWAYS);

        Label nameLabel = new Label(user.getName());
        nameLabel.setStyle("-fx-font-weight: bold;");

        Label roleLabel = new Label(user.getRole().getLabel() + " • " + user.getEmail());
        roleLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");

        info.getChildren().addAll(nameLabel, roleLabel);

        Label statusLabel = new Label(user.getIsActive() ? "ACTIVE" : "INACTIVE");
        statusLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + (user.getIsActive() ? "#198754" : "#dc3545") + ";");

        hbox.getChildren().addAll(info, statusLabel);
        return hbox;
    }

    @FXML
    private void handleAddKaryawan() {
        interactor.addKaryawan();
    }

    @FXML
    private void handleBack() {
        try {
            App.navigate("settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
