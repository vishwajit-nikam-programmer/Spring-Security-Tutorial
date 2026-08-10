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
    public UserEntity saveUserWithEncodedPassword(@RequestParam String username, @RequestParam String password, @RequestParam String role){
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setIsActive(true);
        user.setRole(role);

        userRepository.save(user);
        return user;
    }

    @PostMapping("/authenticate")
    public String authenticateUser(@RequestBody AuthRequest authRequest){
        //Authentication is a class from Spring Security
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                 authRequest.getPassword()));

        if(authenticate.isAuthenticated()){
            //Here we traverse the authorities and extract the required role
            // and put these role into below function
            String role = authenticate
                    .getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority()
                    .replace("ROLE_","");//Every role starts with ROLE_ keyword to avoid it we replaced ROLE_ with empty String ""
            return jwtServiceImpl.generateToken(authRequest.getUsername(),role);
        }
        return null;
    }
}
