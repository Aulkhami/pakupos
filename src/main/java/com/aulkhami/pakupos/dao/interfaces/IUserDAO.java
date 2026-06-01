package com.aulkhami.pakupos.dao.interfaces;

import java.util.Optional;

import com.aulkhami.pakupos.models.entities.User;

public interface IUserDAO extends IDAO<User, Integer> {
    Optional<User> findByUsername(String username);
}
