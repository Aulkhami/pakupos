package com.aulkhami.pakupos.modules.dashboard.view;

import com.aulkhami.pakupos.app.App;
import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.models.Model;
import com.aulkhami.pakupos.modules.dashboard.models.DashboardModel;
import com.aulkhami.pakupos.modules.dashboard.interactors.DashboardInteractor;
import com.aulkhami.pakupos.controllers.components.transactionitem.TransactionItemController;
import com.aulkhami.pakupos.modules.pos.dtos.OrderResponseDTO;
import com.aulkhami.pakupos.views.View;
import java.io.IOException;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashboardView implements View {

    private DashboardModel model;
    private DashboardInteractor interactor;

    @FXML
    private Label salesAmount;
    @FXML
    private Label salesCount;
    @FXML
    private VBox recentTransactionsVBox;

    @Override
    public void setModel(Model model) {
        this.model = (DashboardModel) model;

        salesAmount.textProperty().bind(this.model.salesAmountProperty());
        salesCount.textProperty().bind(this.model.salesCountProperty().asString());

        this.model.getRecentTransactions().addListener((ListChangeListener<OrderResponseDTO>) change -> {
            renderRecentTransactions();
        });

        renderRecentTransactions();
    }

    @Override
    public void setInteractor(Interactor interactor) {
        this.interactor = (DashboardInteractor) interactor;
        this.interactor.loadDashboardData();
    }

    @FXML
    private javafx.scene.control.ScrollPane quickActionsScrollPane;

    private double scrollStartX;
    private double hValueStart;
    private boolean dragged;

    @FXML
    public void initialize() {
        if (quickActionsScrollPane != null) {
            quickActionsScrollPane.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {
                scrollStartX = event.getSceneX();
                hValueStart = quickActionsScrollPane.getHvalue();
                dragged = false;
            });

            quickActionsScrollPane.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_DRAGGED, event -> {
                double deltaX = event.getSceneX() - scrollStartX;
                if (Math.abs(deltaX) > 5) {
                    dragged = true;
                    double contentWidth = quickActionsScrollPane.getContent().getBoundsInLocal().getWidth();
                    double viewportWidth = quickActionsScrollPane.getViewportBounds().getWidth();
                    double hbarWidth = contentWidth - viewportWidth;
                    if (hbarWidth > 0) {
                        double deltaH = -deltaX / hbarWidth;
                        quickActionsScrollPane.setHvalue(Math.max(0, Math.min(1, hValueStart + deltaH)));
                    }
                    event.consume();
                }
            });

            quickActionsScrollPane.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_RELEASED, event -> {
                if (dragged) {
                    event.consume();
                }
            });

            quickActionsScrollPane.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
                if (dragged) {
                    event.consume();
                }
            });
        }
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

    private void renderRecentTransactions() {
        if (recentTransactionsVBox == null) {
            return;
        }
        recentTransactionsVBox.getChildren().clear();
        for (OrderResponseDTO order : model.getRecentTransactions()) {
            try {
                TransactionItemController itemController = new TransactionItemController(order);
                recentTransactionsVBox.getChildren().add(itemController.getView());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
