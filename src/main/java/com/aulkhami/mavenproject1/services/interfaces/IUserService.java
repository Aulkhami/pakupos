package com.aulkhami.mavenproject1.services.interfaces;

import com.aulkhami.mavenproject1.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User registerUser(String username, String email, String password);

    Optional<User> loginUser(String username, String password);

    List<User> getAllUsers();

    Optional<User> getUserById(Integer id);

    void updateUser(User user);

    void deleteUser(Integer id);
}
