package com.aulkhami.mavenproject1.dao.interfaces;

import java.util.Optional;

import com.aulkhami.mavenproject1.models.entities.User;

public interface IUserDAO extends IDAO<User, Integer> {
    Optional<User> findByUsername(String username);
}
