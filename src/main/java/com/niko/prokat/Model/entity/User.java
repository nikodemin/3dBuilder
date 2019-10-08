package com.niko.prokat.Model.entity;

import com.niko.prokat.Model.enums.UserRole;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Data
public class User extends AbstractEntity {
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String address;
    private String telnum;
    @Column(unique = true, nullable = false)
    private String email;
    private String firstname;
    private String lastname;
    private UserRole role;
    private String token;
}
