/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller;

import static com.controller.MusicControlController.musicControlController;
import com.dao.TrackDao;
import com.model.Auth;
import com.model.Track;
import com.utils.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author ngoch
 */
public class ArtistProfileController implements Initializable {

    @FXML
    private Label artistDescLabel;

    @FXML
    private Label artistNameLabel;

    @FXML
    private Circle avatarProfileWrapper;

    @FXML
    private VBox trackList;

    private Auth artist;

    private Utils ut = new Utils();

    private TrackDao trackDao = new TrackDao();

    private ArrayList<Track> trackListData;

    public ArtistProfileController(Auth artist) {
        this.artist = artist;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        trackListData = trackDao.selectTracksOfArtist(artist.getId());

        Image image = new Image("file:" + ut.getCurrentPath() + "/src/main/resources/application/static/images/artists/" + artist.getImage());
        if (image != null) {
            ImagePattern avatarImagePattern = new ImagePattern(image);
            avatarProfileWrapper.setFill(avatarImagePattern);
        }

        artistNameLabel.setText(artist.getFullname());
        artistDescLabel.setText(artist.getDescription());

        for (Track track : trackListData) {
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefHeight(36.0);
            anchorPane.setPrefWidth(424.0);
            anchorPane.setCursor(Cursor.HAND);
            anchorPane.setOnMousePressed(event -> {
                musicControlController.currentIndexSong(track, artist.getFullname());
            });

            Label label1 = new Label(track.getName());
            label1.setLayoutX(14.0);
            label1.setLayoutY(9.0);
            label1.setPrefHeight(18.0);
            label1.setPrefWidth(285.0);

            Label label2 = new Label(track.getDurationString());
            label2.setLayoutX(326.0);
            label2.setLayoutY(9.0);
            label2.setPrefHeight(18.0);
            label2.setPrefWidth(74.0);

            anchorPane.getChildren().addAll(label1, label2);

            trackList.getChildren().add(anchorPane);
        }
    }

}
