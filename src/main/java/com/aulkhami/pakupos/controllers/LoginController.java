/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aulkhami.pakupos.controllers;

import com.aulkhami.pakupos.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import com.aulkhami.pakupos.models.LoginModel;
import com.aulkhami.pakupos.views.LoginView;
import com.aulkhami.pakupos.interactors.LoginInteractor;
import java.io.IOException;

/**
 *
 * @author Rakha
 */
public class LoginController implements Controller {

    private LoginModel model = new LoginModel();
    private LoginInteractor interactor = new LoginInteractor(model);

    @Override
    public Region getView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/login.fxml"));
        Region view = fxmlLoader.load();

        LoginView controller = fxmlLoader.getController();
        controller.setModel(model);
        controller.setInteractor(interactor);

        return view;
    }
}
