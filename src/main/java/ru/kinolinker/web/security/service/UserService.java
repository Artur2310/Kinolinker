package ru.kinolinker.web.security.service;

import ru.kinolinker.web.security.model.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

	User getUser(String name);

	
	void updatePassword(String password);
}

