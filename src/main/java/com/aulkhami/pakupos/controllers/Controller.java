/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.aulkhami.pakupos.controllers;

import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.views.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

/**
 *
 * @author Rakha
 */
public interface Controller {

    public static Region loadView(
            FXMLLoader fxmlLoader, Model model, Interactor interactor
    ) throws IOException {
        Region region = fxmlLoader.load();
        View view = fxmlLoader.getController();
        view.setModel(model);
        view.setInteractor(interactor);
        return region;
    }

    public Region getView() throws IOException;
}
