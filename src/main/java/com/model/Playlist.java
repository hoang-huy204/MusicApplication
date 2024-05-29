/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import java.time.LocalDateTime;

/**
 * @author ngoch
 */
public class Playlist {
    private Integer id;
    private String name;
    private String image;
    private String description;
    private Integer total_track;
    private Integer auth_id;
    private String status;
    private String userName;
    private LocalDateTime create_at;

    public Playlist(Integer id, String name, String image, String description, Integer total_track, Integer auth_id, String status, LocalDateTime create_at) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.total_track = total_track;
        this.auth_id = auth_id;
        this.status = status;
        this.create_at = create_at;
    }

    public Playlist(Integer id, String name, String image, String description, Integer total_track, String userName, LocalDateTime create_at) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.total_track = total_track;
        this.userName = userName;
        this.create_at = create_at;
    }

    public Playlist(Integer id, String name, String image, String description, Integer totalTrack, LocalDateTime createAt) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.total_track = totalTrack;
        this.create_at = createAt;
    }

    public Playlist(String name, String image, Integer total_track, String description) {
        this.name = name;
        this.image = image;
        this.total_track = total_track;
        this.description = description;
    }

    public Playlist(Integer id, String name, String image, Integer total_track, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.total_track = total_track;
        this.description = description;
    }

    public Playlist() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTotal_track() {
        return total_track;
    }

    public void setTotal_track(Integer total_track) {
        this.total_track = total_track;
    }

    public Integer getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(Integer auth_id) {
        this.auth_id = auth_id;
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
        return "Playlist{" + "id=" + id + ", name=" + name + ", image=" + image + ", description=" + description + ", total_track=" + total_track + ", auth_id=" + auth_id + ", status=" + status + ", create_at=" + create_at + '}';
    }
}
