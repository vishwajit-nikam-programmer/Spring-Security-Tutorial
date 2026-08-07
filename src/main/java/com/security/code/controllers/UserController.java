package com.security.code.controllers;

import com.security.code.entities.UserEntity;
import com.security.code.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository,PasswordEncoder passwordEncoder){
                           this.userRepository = userRepository;
                           this.passwordEncoder = passwordEncoder;
                           }


    @GetMapping("signup")
    public UserEntity saveUserWithEncodedPassword(@RequestParam String username, @RequestParam String password){
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setIsActive(true);

        userRepository.save(user);
        return user;
    }

}
