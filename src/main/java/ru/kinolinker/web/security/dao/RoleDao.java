package ru.kinolinker.web.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.kinolinker.web.security.model.Role;

public interface RoleDao extends JpaRepository<Role, Long> {
}
