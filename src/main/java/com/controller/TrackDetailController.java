/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.dao.TrackDao;
import com.model.Track;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 *
 * @author daoqu
 */
public class TrackDetailController implements Initializable{
@FXML
private Label name;
@FXML
private Label singer;
@FXML
private Label dura;
@FXML
private Label genre;
@FXML
private ImageView image;
@FXML
private Label lyrics;
 private Track track;
  private String currentPath;
    private TrackDao trackDao = new TrackDao();
    
    public TrackDetailController(Track track) {
        this.track = track;
    }
 
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentPath = System.getProperty("user.dir");
            currentPath = currentPath.replace("\\", "/");
        name.setText(track.getName());
        singer.setText(track.getSingers());
        dura.setText(String.valueOf(track.getDurationString()));
        genre.setText(String.valueOf(track.getGenre_id()));
        lyrics.setText(track.getLyrics());
       String imageURL = "file:" + currentPath + "/src/main/resources/application/static/images/tracks/" + track.getImage();
        Image trackImage = new Image(imageURL);
        image.setImage(trackImage);
    }
}
