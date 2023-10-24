package com.globalpay.security.ws_security.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "`LOGIN_USER`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id_login_user;
    @Column
    private String name;
    @Column
    private String lastname;
    @Column
    private String email;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private int phone;
    @Column
    private int status;

    public int getId_login_user() {
        return id_login_user;
    }

    public void setId_login_user(int id_login_user) {
        this.id_login_user = id_login_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
    	this.email = email;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

