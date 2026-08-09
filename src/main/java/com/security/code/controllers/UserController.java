package com.security.code.controllers;

import com.security.code.dtos.AuthRequest;
import com.security.code.entities.UserEntity;
import com.security.code.repository.UserRepository;
import com.security.code.serviceImpl.JWTServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTServiceImpl jwtServiceImpl;

    public UserController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JWTServiceImpl jwtServiceImpl){
                           this.userRepository = userRepository;
                           this.passwordEncoder = passwordEncoder;
                           this.authenticationManager = authenticationManager;
                           this.jwtServiceImpl = jwtServiceImpl;
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

    @PostMapping("/authenticate")
    public String authenticateUser(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                 authRequest.getPassword()));

        if(authenticate.isAuthenticated()){
            return jwtServiceImpl.generateToken(authRequest.getUsername());
        }
        return null;
    }
}
