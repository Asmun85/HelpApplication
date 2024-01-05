package com.AuthentificationService.AuthentificationService.Service;

import com.AuthentificationService.AuthentificationService.Entity.User;

public interface AuthentificationService {
    User login(String login, String password);
    String logout(String login);
}
