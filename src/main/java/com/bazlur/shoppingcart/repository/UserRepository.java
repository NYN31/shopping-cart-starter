package com.bazlur.shoppingcart.repository;

import com.bazlur.shoppingcart.domain.User;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class UserRepository {
    private static final Set<User> USERS = new CopyOnWriteArraySet<>();

    public void save(User user){
        USERS.add(user);
    }

    public Optional<User> findByUsername(String username){
        return USERS.stream().filter(user -> Objects.equals(
                user.getUsername(), username
        )).findFirst();
    }

    public Optional<User> findByEmail(String email){
        return USERS.stream().filter(user -> Objects.equals(
                user.getEmail(), email
        )).findFirst();
    }
}
