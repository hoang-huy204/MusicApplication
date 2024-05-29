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
public class TrackPlaylist {
    private Integer id;
    private Integer track_id;
    private Integer playlist_id;
    private LocalDateTime create_at;

    public TrackPlaylist(Integer id, Integer track_id, Integer playlist_id, LocalDateTime create_at) {
        this.id = id;
        this.track_id = track_id;
        this.playlist_id = playlist_id;
        this.create_at = create_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrack_id() {
        return track_id;
    }

    public void setTrack_id(Integer track_id) {
        this.track_id = track_id;
    }

    public Integer getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(Integer playlist_id) {
        this.playlist_id = playlist_id;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    @Override
    public String toString() {
        return "TrackPlaylist{" + "id=" + id + ", track_id=" + track_id + ", playlist_id=" + playlist_id + ", create_at=" + create_at + '}';
    }
}
