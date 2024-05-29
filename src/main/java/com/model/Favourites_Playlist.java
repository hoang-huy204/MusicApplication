package com.model;

import java.time.LocalDateTime;

public class Favourites_Playlist {
    private Integer id;
    private Integer user_id;
    private Integer playlist_id;

    public Favourites_Playlist(Integer id, Integer user_id, Integer playlist_id, LocalDateTime create_at) {
        this.id = id;
        this.user_id = user_id;
        this.playlist_id = playlist_id;
        this.create_at = create_at;
    }

    private LocalDateTime create_at;

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
        return "Favourites_Playlist{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", playlist_id=" + playlist_id +
                ", create_at=" + create_at +
                '}';
    }
}
