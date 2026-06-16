package com.aulkhami.pakupos.app.settings;

import com.aulkhami.pakupos.controllers.Controller;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

public class SettingsController implements Controller {

    private SettingsModel model;
    private SettingsInteractor interactor;

    public SettingsController() {
        model = new SettingsModel();
        interactor = new SettingsInteractor(model);
    }

    @Override
    public Region getView() throws IOException {
        return Controller.loadView(new FXMLLoader(getClass().getResource("settings.fxml")), model, interactor);
    }
}
