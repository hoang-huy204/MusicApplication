/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller;

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
public class LyricsDetailsController implements Initializable {
    private String lyrics;
    
    @FXML
    private Label lyricsLabel;

    public LyricsDetailsController(String lyrics) {
        this.lyrics = lyrics;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.lyricsLabel.setText(lyrics);
    }    
    
}
