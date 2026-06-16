package com.aulkhami.pakupos;

import com.aulkhami.pakupos.app.dashboard.DashboardController;
import com.aulkhami.pakupos.app.login.LoginController;
import com.aulkhami.pakupos.app.inventory.InventoryController;
import com.aulkhami.pakupos.app.pos.POSController;
import com.aulkhami.pakupos.app.report.ReportController;
import com.aulkhami.pakupos.app.settings.SettingsController;
import com.aulkhami.pakupos.app.usermanagement.UserManagementController;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        LoginController login = new LoginController();
        scene = new Scene(login.getView(), 412, 915);
        stage.setScene(scene);
        stage.setTitle("PAKU POS");

        // Responsive constraints:
        // Min: small phone, Max: large tablet (portrait)
        stage.setMinWidth(320);
        stage.setMinHeight(568);
        stage.setMaxWidth(1024);
        stage.setMaxHeight(1366);

        stage.setResizable(true);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                App.class.getResource(fxml + ".fxml")
        );
        return fxmlLoader.load();
    }

    public static void navigate(String page) throws IOException {
        switch (page) {
            case "login":
                LoginController login = new LoginController();
                scene.setRoot(login.getView());
                break;
            case "dashboard":
                DashboardController dashboard = new DashboardController();
                scene.setRoot(dashboard.getView());
                break;
            case "inventory":
                InventoryController inventory = new InventoryController();
                scene.setRoot(inventory.getView());
                break;
            case "pos":
                POSController pos = new POSController();
                scene.setRoot(pos.getView());
                break;
            case "report":
                ReportController report = new ReportController();
                scene.setRoot(report.getView());
                break;
            case "settings":
                SettingsController settings = new SettingsController();
                scene.setRoot(settings.getView());
                break;
            case "user-management":
                UserManagementController userManagement = new UserManagementController();
                scene.setRoot(userManagement.getView());
                break;
            default:
                throw new AssertionError("Unknown page: " + page);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
