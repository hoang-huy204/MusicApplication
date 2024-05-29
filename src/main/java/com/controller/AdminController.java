/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.App;
import com.utils.Utils;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author ngoch
 */
public class AdminController implements Initializable {
    @FXML
    private Label artistSwitch;

    @FXML
    private BorderPane borderPaneRoot;

    @FXML
    private Label dashBroardSwitch;

    @FXML
    private Label playlistSwitch;

    @FXML
    private Label trackSwitch;

    @FXML
    private Label userSwitch;
     @FXML
    private Label genreSwitch;
    
    @FXML
    private Label packageSwitch;

    private Utils ut = new Utils();
    
    private Scene currentScene;
    private Stage currentStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            currentScene = borderPaneRoot.getScene();
            currentStage = (Stage) currentScene.getWindow();
            ut.setPosWindow(currentScene);
        });
        
        ut.loadSectionFxml(borderPaneRoot, "/application/views/admin/DashBroard.fxml", "center");
    }

    @FXML
    void switchMenu(MouseEvent event) {
        Label label = (Label) event.getSource();
        if (label == dashBroardSwitch) {
            ut.loadSectionFxml(borderPaneRoot, "/application/views/admin/DashBroard.fxml", "center");
        } else if (label == playlistSwitch) {
            ut.loadSectionFxml(borderPaneRoot, "/application/views/admin/Playlist.fxml", "center");
        } else if (label == trackSwitch) {
             ut.loadSectionFxml(borderPaneRoot, "/application/views/admin/Track.fxml", "center");
        } else if (label == userSwitch) {
            ut.loadSectionFxml(borderPaneRoot, "/application/views/admin/Auth.fxml", "center");
        }else if (label == genreSwitch) {
            ut.loadSectionFxml(borderPaneRoot, "/application/views/admin/genre.fxml", "center");
        } else if (label == packageSwitch) {
            ut.loadSectionFxml(borderPaneRoot, "/application/views/admin/Package.fxml", "center");
        }
    }
    
    @FXML
    private void handleLogout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/UI/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            currentStage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
