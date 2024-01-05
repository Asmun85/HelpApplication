package com.AuthentificationService.AuthentificationService.ServiceImpl;

import com.AuthentificationService.AuthentificationService.Entity.User;
import com.AuthentificationService.AuthentificationService.Repository.UserRepository;
import com.AuthentificationService.AuthentificationService.Service.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthentificationServiceImpl implements AuthentificationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String login, String password) {
        User user = userRepository.findByLogin(login);
        if (user != null && user.getPassword().equals(password)) {
            // we can use JWT or token authentification but for our scale we don't need it
            return user;
        }
        throw new SecurityException("Invalid username or password");
    }

    @Override
    public String logout(String login) {
        return "User " + login + " has been logged out successfully.";
    }
}
