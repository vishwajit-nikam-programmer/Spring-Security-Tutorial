package com.security.code.serviceImpl;

import com.security.code.entities.UserEntity;
import com.security.code.repository.UserRepository;
import com.security.code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

//UserDetailsService is a Default Service Interface provided by Spring Security
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity getUserFromUsername(String username) {
        return userRepository.findByUsernameAndIsActive(username,true)
                             .orElseThrow(()->new UsernameNotFoundException("User Not Present"));
    }

    //This is how we integrate our database with spring security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = getUserFromUsername(username);

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }

}
