package com.security.code.entities;

import com.security.code.constants.Role;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",nullable = false)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "isactive")
    private Boolean isActive;

    @Column(name = "role")
    //By using below annotation data will saved in db
    //in string format
    @Enumerated(EnumType.STRING)
    private Role role;
}
