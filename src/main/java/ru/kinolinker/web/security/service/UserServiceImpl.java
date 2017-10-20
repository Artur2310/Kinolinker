package ru.kinolinker.web.security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kinolinker.web.security.dao.RoleDao;
import ru.kinolinker.web.security.dao.UserDao;
import ru.kinolinker.web.security.model.Role;
import ru.kinolinker.web.security.model.User;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getOne(1L));
        user.setRoles(roles);
        userDao.save(user);
        
    }
    
    @Override
    @Transactional
    public void updatePassword(String password) {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user =  userDao.findByUsername(auth.getName());

        user.setPassword(bCryptPasswordEncoder.encode(password));
        
        userDao.saveAndFlush(user);
        
    }
    
    @Override
    public User getUser(String name) {
        
        return userDao.findByUsername(name);
        
    }
    
   

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
    
    public UserServiceImpl(){
    	
    }
}
