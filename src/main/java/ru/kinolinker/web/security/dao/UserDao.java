package ru.kinolinker.web.security.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import ru.kinolinker.web.security.model.User;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

