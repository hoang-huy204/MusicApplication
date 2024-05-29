/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import java.time.LocalDateTime;

/**
 *
 * @author ngoch
 */
public class Records {
    private Integer id;
    private String name;
    private String path;
    private Integer auth_id;
    private LocalDateTime create_at;

    public Records(Integer id, String name, String path, Integer auth_id, LocalDateTime create_at) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.auth_id = auth_id;
        this.create_at = create_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(Integer auth_id) {
        this.auth_id = auth_id;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    @Override
    public String toString() {
        return "Records{" + "id=" + id + ", name=" + name + ", path=" + path + ", auth_id=" + auth_id + ", create_at=" + create_at + '}';
    }
}
