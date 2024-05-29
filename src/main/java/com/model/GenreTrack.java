/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import java.time.LocalDateTime;

/**
 * @author ngoch
 */
public class GenreTrack {
    private Integer id;
    private String name;
    private String status;
    private LocalDateTime create_at;

    public GenreTrack(Integer id, String name, String status, LocalDateTime create_at) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.create_at = create_at;
    }

    public GenreTrack(Integer id, String name) {
        this.id = id;
        this.name = name;
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
        return name;
    }
}
