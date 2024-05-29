///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
// */
//package com.controller;
//
//import com.dao.TrackDao;
//import com.model.Track;
//import com.utils.Utils;
//import com.utils.Utils.NotificationType;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.ResourceBundle;
//import javafx.application.Platform;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.util.Duration;
//
///**
// * FXML Controller class
// *
// * @author ngoch
// */
//public class TestController implements Initializable {
//    private Utils ut = new Utils();
//    private Scene currentScene;
//    
//    @FXML
//    private Button showBtn;
//    
//    private ArrayList<Track> trackList;
//    
//    private TrackDao trackDao = new TrackDao();
//
//    /**
//     * Initializes the controller class.
//     * 
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // TODO
//        Platform.runLater(() -> {
//            this.currentScene = this.showBtn.getScene();
//            ut.setPosWindow(currentScene);
//        });
//        
//        this.trackList = this.trackDao.test();
//    }    
//    
//    public void handleShowNoti() {
////        Utils.showNotification("Test", "Hello 123", NotificationType.WARNING, Duration.seconds(3));
////        Utils.showConfirrm();
////        for (Track item : this.trackList) {
////            System.out.println(item.toString());
////        }
////        ut.sendAuthenticationEmail("ngochoanghuy0504@gmail.com", "http://localhost/verification/index.php?id=MQ==&type=YXV0aA==", "xac nhan");
//    }
//}
