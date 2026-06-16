package com.aulkhami.pakupos.app.usermanagement;

import com.aulkhami.pakupos.dao.UserDAO;
import com.aulkhami.pakupos.enums.UserRole;
import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.models.entities.User;
import com.aulkhami.pakupos.utils.AlertHelper;
import java.util.List;

public class UserManagementInteractor implements Interactor {
    private final UserManagementModel model;
    private final UserDAO userDAO = new UserDAO();

    public UserManagementInteractor(UserManagementModel model) {
        this.model = model;
    }

    public void loadUsers() {
        try {
            List<User> users = userDAO.findAll();
            model.getUsers().setAll(users);
        } catch (Exception e) {
            AlertHelper.showError("Error", "Could not load users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addKaryawan() {
        String name = model.getName();
        String email = model.getEmail();
        String password = model.getPassword();
        String phone = model.getPhone();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            AlertHelper.showError("Validation Error", "Name, Email, and Password are required.");
            return;
        }

        try {
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setPhone(phone);
            newUser.setRole(UserRole.KARYAWAN);
            newUser.setIsActive(true);

            userDAO.save(newUser);

            AlertHelper.showSuccess("Success", "Karyawan added successfully!");
            clearFields();
            loadUsers();
        } catch (Exception e) {
            AlertHelper.showError("Error", "Could not add user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearFields() {
        model.setName("");
        model.setEmail("");
        model.setPassword("");
        model.setPhone("");
    }
}
