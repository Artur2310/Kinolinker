package ru.kinolinker.web.security.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}

