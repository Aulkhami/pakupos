package com.aulkhami.pakupos.modules.user.interactors;

import com.aulkhami.pakupos.app.enums.UserRole;
import com.aulkhami.pakupos.interactors.Interactor;
import com.aulkhami.pakupos.app.utils.AlertHelper;
import com.aulkhami.pakupos.modules.user.dtos.UserRequestDTO;
import com.aulkhami.pakupos.modules.user.dtos.UserResponseDTO;
import com.aulkhami.pakupos.modules.user.models.UserManagementModel;
import com.aulkhami.pakupos.modules.user.services.UserService;

import java.util.List;

public class UserManagementInteractor implements Interactor {
    private final UserManagementModel model;
    private final UserService userService;

    public UserManagementInteractor(UserManagementModel model) {
        this.model = model;
        this.userService = new UserService();
    }

    public void loadUsers() {
        try {
            List<UserResponseDTO> users = userService.getAllUsers();
            model.getUsers().setAll(users);
        } catch (Exception e) {
            AlertHelper.showError("Error", "Could not load users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addKaryawan() {
        String currentPassword = model.getName();
        String newPassword = model.getPassword();
        String confirmPassword = model.getEmail();

        if (currentPassword == null || currentPassword.trim().isEmpty() ||
            newPassword == null || newPassword.isEmpty() ||
            confirmPassword == null || confirmPassword.isEmpty()) {
            AlertHelper.showError("Kesalahan Validasi", "Semua kolom harus diisi!");
            return;
        }

        com.aulkhami.pakupos.modules.user.entities.User currentUser = com.aulkhami.pakupos.app.utils.SessionManager.getCurrentUser();
        if (currentUser == null) {
            AlertHelper.showError("Error", "Sesi tidak ditemukan. Silakan login kembali.");
            return;
        }

        if (!com.aulkhami.pakupos.app.utils.PasswordUtil.verifyPassword(currentPassword, currentUser.getPassword())) {
            AlertHelper.showError("Kesalahan Validasi", "Kata sandi saat ini salah!");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            AlertHelper.showError("Kesalahan Validasi", "Konfirmasi kata sandi baru tidak cocok!");
            return;
        }

        if (!com.aulkhami.pakupos.app.utils.ValidationUtil.isValidPassword(newPassword)) {
            AlertHelper.showError("Kesalahan Validasi", "Kata sandi baru minimal harus 8 karakter!");
            return;
        }

        try {
            String hashedNewPassword = com.aulkhami.pakupos.app.utils.PasswordUtil.hashPassword(newPassword);
            currentUser.setPassword(hashedNewPassword);

            com.aulkhami.pakupos.app.services.UserService appUserService = new com.aulkhami.pakupos.app.services.UserService();
            appUserService.updateUser(currentUser);

            AlertHelper.showSuccess("Sukses", "Kata sandi berhasil diperbarui!");
            clearFields();
        } catch (Exception e) {
            AlertHelper.showError("Error", "Gagal memperbarui kata sandi: " + e.getMessage());
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

