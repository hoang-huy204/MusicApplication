/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller;

import com.dao.AuthDao;
import com.dao.PlayListDao;
import com.dao.TrackDao;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author ngoch
 */
public class AdminDashBroardController implements Initializable {

    @FXML
    private Label Ttrack;

    @FXML
    private Label Tplaylist;

    @FXML
    private Label Tuser;

    @FXML
    private Label Tartist;
private TrackDao trackDao = new TrackDao();
private PlayListDao playlistDao = new PlayListDao();
private AuthDao authDao = new AuthDao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int trackCount = trackDao.getTrackCount();
        int playlistCount = playlistDao.getPlaylistCount();
        int userCount = authDao.getUserCount();
        int artistCount = authDao.getArtistCount();

        Ttrack.setText(String.valueOf(trackCount));
        Tplaylist.setText(String.valueOf(playlistCount));
        Tuser.setText(String.valueOf(userCount));
        Tartist.setText(String.valueOf(artistCount));
    }

}