/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aulkhami.pakupos.app.inventory;

import com.aulkhami.pakupos.controllers.Controller;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

/**
 *
 * @author Rakha
 */
public class InventoryController implements Controller {

    private InventoryModel model;
    private InventoryInteractor interactor;

    public InventoryController() {
        model = new InventoryModel();
        interactor = new InventoryInteractor();
    }

    @Override
    public Region getView() throws IOException {
        return Controller.loadView(new FXMLLoader(getClass().getResource("inventory.fxml")), model, interactor);
    }
}
