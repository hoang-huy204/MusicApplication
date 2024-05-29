/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import java.time.LocalDateTime;

/**
 * @author ngoch
 */
public class Auth {
    private Integer id;
    private String email;
    private String password;
    private String role;
    private String image;
    private String fullname;
    private Integer age;
    private String description;
    private String status;
    private LocalDateTime create_at;

    public Auth(Integer id, String email, String password, String image, String fullname, Integer age, String description, String status, LocalDateTime create_at) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.image = image;
        this.fullname = fullname;
        this.age = age;
        this.description = description;
        this.status = status;
        this.create_at = create_at;
    }

    public Auth(Integer id, String email, String password, String role, String image, String fullname, Integer age, String description, String status, LocalDateTime create_at) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.image = image;
        this.fullname = fullname;
        this.age = age;
        this.description = description;
        this.status = status;
        this.create_at = create_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    @Override
    public String toString() {
        return "Auth{" + "id=" + id + ", email=" + email + ", password=" + password + ", role=" + role + ", image=" + image + ", fullname=" + fullname + ", age=" + age + ", description=" + description + ", status=" + status + ", create_at=" + create_at + '}';
    }
}
