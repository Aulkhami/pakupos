package com.aulkhami.pakupos.modules.settings.interactors;

import com.aulkhami.pakupos.interactors.MenuBarInteractor;
import com.aulkhami.pakupos.app.utils.SessionManager;
import com.aulkhami.pakupos.modules.auth.services.AuthService;
import com.aulkhami.pakupos.modules.settings.models.SettingsModel;
import com.aulkhami.pakupos.modules.user.entities.User;

public class SettingsInteractor extends MenuBarInteractor {
    private final SettingsModel model;
    private final AuthService authService;

    public SettingsInteractor(SettingsModel model) {
        this.model = model;
        this.authService = new AuthService();
    }

    public void loadSessionData() {
        User user = SessionManager.getCurrentUser();
        model.setCurrentUser(user);
    }

    public void logout() {
        authService.logout();
    }
}


