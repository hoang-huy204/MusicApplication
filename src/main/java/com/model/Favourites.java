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
public class Favourites {
    private Integer id;
    private Integer user_id;
    private Integer track_id;
    private LocalDateTime create_at;

    public Favourites(Integer id, Integer user_id, Integer track_id, LocalDateTime create_at) {
        this.id = id;
        this.user_id = user_id;
        this.track_id = track_id;
        this.create_at = create_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getTrack_id() {
        return track_id;
    }

    public void setTrack_id(Integer track_id) {
        this.track_id = track_id;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    @Override
    public String toString() {
        return "Favourites{" + "id=" + id + ", user_id=" + user_id + ", track_id=" + track_id + ", create_at=" + create_at + '}';
    }
}
