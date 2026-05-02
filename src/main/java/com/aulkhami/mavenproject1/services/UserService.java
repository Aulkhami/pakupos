package com.aulkhami.mavenproject1.services;

import com.aulkhami.mavenproject1.dao.UserDAO;
import com.aulkhami.mavenproject1.models.entities.User;
import com.aulkhami.mavenproject1.utils.PasswordUtil;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User registerUser(String username, String email, String plainPassword) {
        String salt = PasswordUtil.generateSalt();
        String hashed = PasswordUtil.hashPassword(plainPassword, salt);
        User user = new User(username, email, hashed, salt);
        return userDAO.save(user);
    }

    public Optional<User> loginUser(String username, String plainPassword) {
        Optional<User> opt = userDAO.findByUsername(username);
        if (opt.isPresent()) {
            User user = opt.get();
            if (PasswordUtil.verifyPassword(plainPassword, user.getPassword(), user.getSalt())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }
}
