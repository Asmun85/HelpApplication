package com.AuthentificationService.AuthentificationService.Controller;

import com.AuthentificationService.AuthentificationService.DTO.LoginDTO;
import com.AuthentificationService.AuthentificationService.Entity.User;
import com.AuthentificationService.AuthentificationService.Service.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
@RequestMapping("/authentification")
public class AuthController {
    @Autowired
    private AuthentificationService authentificationService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginDTO loginDTO) {
        User user = authentificationService.login(loginDTO.getLogin(), loginDTO.getPassword());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String login) {
        authentificationService.logout(login);
        return ResponseEntity.ok().build();
    }
}
