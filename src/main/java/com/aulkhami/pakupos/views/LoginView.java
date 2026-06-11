/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.aulkhami.pakupos.views;

import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.models.LoginModel;
import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.interactors.LoginInteractor;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * FXML Controller class
 *
 * @author Rakha
 */
public class LoginView implements View {

    private LoginModel model;
    private LoginInteractor interactor;

    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Label successLabel;

    @Override
    public void setModel(Model model) {
        this.model = (LoginModel) model;
        email.textProperty().bindBidirectional(this.model.emailProperty());
        password.textProperty().bindBidirectional(this.model.passwordProperty());

        successLabel.visibleProperty().bind(this.model.successProperty());
    }

    @Override
    public void setInteractor(Interactor interactor) {
        this.interactor = (LoginInteractor) interactor;
    }

    public void onSubmit() {
        interactor.submitForm();
    }
}
