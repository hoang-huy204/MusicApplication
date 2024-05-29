/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller;

import com.utils.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author ngoch
 */
public class GeneralController implements Initializable {
     @FXML
    private BorderPane borderPane;
     
     private Utils ut = new Utils();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ut.loadSectionFxml(borderPane, "/application/views/UI/Sidebar.fxml", "left");
        ut.loadSectionFxml(borderPane, "/application/views/UI/MusicControl.fxml", "bottom");
        ut.loadSectionFxml(borderPane, "/application/views/UI/Home.fxml", "center");
    }    
    
}
