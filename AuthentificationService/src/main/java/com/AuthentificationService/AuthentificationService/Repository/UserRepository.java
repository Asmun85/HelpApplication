package com.AuthentificationService.AuthentificationService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.AuthentificationService.AuthentificationService.Entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByLogin(String login);
}
