package com.aulkhami.pakupos.app.pos;

import com.aulkhami.pakupos.controllers.Controller;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

public class POSController implements Controller {

    private POSModel model;
    private POSInteractor interactor;

    public POSController() {
        model = new POSModel();
        interactor = new POSInteractor(model);
    }

    @Override
    public Region getView() throws IOException {
        return Controller.loadView(new FXMLLoader(getClass().getResource("pos.fxml")), model, interactor);
    }
}
