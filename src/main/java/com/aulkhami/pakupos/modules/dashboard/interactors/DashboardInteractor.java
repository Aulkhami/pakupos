package com.aulkhami.pakupos.modules.dashboard.interactors;

import com.aulkhami.pakupos.app.App;
import com.aulkhami.pakupos.app.utils.AlertHelper;
import com.aulkhami.pakupos.interactors.MenuBarInteractor;
import com.aulkhami.pakupos.modules.auth.services.AuthService;
import java.io.IOException;

public class DashboardInteractor extends MenuBarInteractor {

    private final AuthService authService;

    public DashboardInteractor() {
        this.authService = new AuthService();
    }

    public void navReports() {
        try {
            App.navigate("report");
        } catch (IOException e) {
            AlertHelper.showError(
                    "System Error",
                    "Could not load Reports screen."
            );
            e.printStackTrace();
        }
    }

    public void logout() {
        authService.logout();
    }
}
