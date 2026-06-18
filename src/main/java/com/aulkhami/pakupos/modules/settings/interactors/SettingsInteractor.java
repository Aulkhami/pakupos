package com.aulkhami.pakupos.modules.settings.interactors;

import com.aulkhami.pakupos.interactors.MenuBarInteractor;
import com.aulkhami.pakupos.app.utils.SessionManager;
import com.aulkhami.pakupos.app.utils.PasswordUtil;
import com.aulkhami.pakupos.app.services.UserService;
import com.aulkhami.pakupos.modules.auth.services.AuthService;
import com.aulkhami.pakupos.modules.settings.models.SettingsModel;
import com.aulkhami.pakupos.modules.user.entities.User;

public class SettingsInteractor extends MenuBarInteractor {
    private final SettingsModel model;
    private final AuthService authService;
    private final UserService userService;

    public SettingsInteractor(SettingsModel model) {
        this.model = model;
        this.authService = new AuthService();
        this.userService = new UserService();
    }

    public void loadSessionData() {
        User user = SessionManager.getCurrentUser();
        model.setCurrentUser(user);
    }

    public void logout() {
        authService.logout();
    }

    public boolean changePassword(String currentPassword, String newPassword) {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser == null) {
            return false;
        }

        if (!PasswordUtil.verifyPassword(currentPassword, currentUser.getPassword())) {
            return false;
        }

        if (newPassword == null || newPassword.length() < 8) {
            return false;
        }

        String hashedNewPassword = PasswordUtil.hashPassword(newPassword);
        currentUser.setPassword(hashedNewPassword);
        userService.updateUser(currentUser);
        return true;
    }
}


