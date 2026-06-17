package com.aulkhami.pakupos.modules.dashboard.view;

import com.aulkhami.pakupos.app.App;
import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.app.utils.AlertHelper;
import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.dashboard.models.DashboardModel;
import com.aulkhami.pakupos.modules.dashboard.interactors.DashboardInteractor;
import com.aulkhami.pakupos.views.View;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardView implements View {

    private DashboardModel model;
    private DashboardInteractor interactor;

    @FXML
    private Label salesAmount;
    @FXML
    private Label salesCount;

    @Override
    public void setModel(Model model) {
        this.model = (DashboardModel) model;

        salesAmount.textProperty().bind(this.model.salesAmountProperty());
        salesCount.textProperty().bind(this.model.salesCountProperty().asString());
    }

    @Override
    public void setInteractor(Interactor interactor) {
        this.interactor = (DashboardInteractor) interactor;
    }

    @FXML
    public void initialize() {
        // Initialize dashboard state if needed
    }

    @FXML
    private void handleNewSale() {
        interactor.navNewSale();
    }

    @FXML
    private void handleInventory() {
        interactor.navInventory();
    }

    @FXML
    private void handleReports() {
        interactor.navReports();
    }

    @FXML
    private void handleSettings() {
        interactor.navSettings();
    }

    @FXML
    private void handleLogout() {
        interactor.logout();
        try {
            App.navigate("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
