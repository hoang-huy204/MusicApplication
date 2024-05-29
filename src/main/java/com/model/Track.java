/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import java.time.Duration;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Track {

    private Integer id;
    private String name;
    private Duration duration;
    private String durationString;
    private Integer genre_id;
    private String singers;
    private String lyrics;
    private Integer music_listener;
    private String image;
    private String file_path;
    private String status;
    private LocalDateTime create_at;

    public Track(Integer id, String name, Duration duration, Integer genre_id, String singers, String lyrics, Integer music_listener, String image, String file_path, LocalDateTime create_at) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.genre_id = genre_id;
        this.singers = singers;
        this.lyrics = lyrics;
        this.music_listener = music_listener;
        this.image = image;
        this.file_path = file_path;
        this.create_at = create_at;

        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        this.durationString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public Track(String name, Duration fileDuration, Integer genreTrackId, String singers, String lyrics, String image, String file_path) {
        this.name = name;
        this.duration = fileDuration;
        this.genre_id = genreTrackId;
        this.singers = singers;
        this.lyrics = lyrics;
        this.image = image;
        this.file_path = file_path;
    }

    public Track(String name, Duration fileDuration, Integer genreTrackId, String singers, String lyrics) {
        this.name = name;
        this.duration = fileDuration;
        this.genre_id = genreTrackId;
        this.singers = singers;
        this.lyrics = lyrics;
    }

    public Track(String name, Duration duration) {
        this.name = name;
        this.duration = duration;
    }

    public Track(Integer id, String name, Duration duration, Integer genreId, String singers, String lyrics) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.genre_id = genreId;
        this.singers = singers;
        this.lyrics = lyrics;
    }

    public Track(Integer id, String name, Duration duration, Integer genreId, String singers, String lyrics, String fileNameImageUpload, String file_path) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.genre_id = genreId;
        this.singers = singers;
        this.lyrics = lyrics;
        this.image = fileNameImageUpload;
        this.file_path = file_path;
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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getDurationString() {
        return durationString;
    }

    public void setDurationString(String durationString) {
        this.durationString = durationString;
    }

    public Integer getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(Integer genre_id) {
        this.genre_id = genre_id;
    }

    public String getSingers() {
        return singers;
    }

    public void setSingers(String singers) {
        this.singers = singers;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public Integer getMusic_listener() {
        return music_listener;
    }

    public void setMusic_listener(Integer music_listener) {
        this.music_listener = music_listener;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
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
        return "Track{" + "id=" + id + ", name=" + name + ", duration=" + duration + ", genre_id=" + genre_id + ", singers=" + singers + ", lyrics=" + lyrics + ", music_listener=" + music_listener + ", image=" + image + ", file_path=" + file_path + ", status=" + status + ", create_at=" + create_at + '}';
    }
}
