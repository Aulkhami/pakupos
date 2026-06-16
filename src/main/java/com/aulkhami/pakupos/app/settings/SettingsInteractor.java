package com.aulkhami.pakupos.app.settings;

import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.models.entities.User;
import com.aulkhami.pakupos.utils.SessionManager;

public class SettingsInteractor implements Interactor {
    private final SettingsModel model;

    public SettingsInteractor(SettingsModel model) {
        this.model = model;
    }

    public void loadSessionData() {
        User user = SessionManager.getCurrentUser();
        model.setCurrentUser(user);
    }

    public void logout() {
        SessionManager.logout();
    }
}
