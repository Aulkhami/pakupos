/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aulkhami.pakupos.interactors;

import com.aulkhami.pakupos.app.App;
import com.aulkhami.pakupos.app.utils.AlertHelper;
import java.io.IOException;

/**
 *
 * @author Rakha
 */
public abstract class MenuBarInteractor implements Interactor {

    public void navDashboard() {
        try {
            App.navigate("dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navNewSale() {
        try {
            App.navigate("pos");
        } catch (IOException e) {
            AlertHelper.showError("System Error", "Could not load POS screen.");
            e.printStackTrace();
        }
    }

    public void navInventory() {
        try {
            App.navigate("inventory");
        } catch (IOException e) {
            AlertHelper.showError(
                    "System Error",
                    "Could not load Inventory screen."
            );
            e.printStackTrace();
        }
    }

    public void navSettings() {
        try {
            App.navigate("settings");
        } catch (IOException e) {
            AlertHelper.showError(
                    "System Error",
                    "Could not load Settings screen."
            );
            e.printStackTrace();
        }
    }
}
