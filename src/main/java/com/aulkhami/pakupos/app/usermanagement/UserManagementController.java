package com.aulkhami.pakupos.app.usermanagement;

import com.aulkhami.pakupos.controllers.Controller;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

public class UserManagementController implements Controller {

    private UserManagementModel model;
    private UserManagementInteractor interactor;

    public UserManagementController() {
        model = new UserManagementModel();
        interactor = new UserManagementInteractor(model);
    }

    @Override
    public Region getView() throws IOException {
        return Controller.loadView(new FXMLLoader(getClass().getResource("user-management.fxml")), model, interactor);
    }
}
