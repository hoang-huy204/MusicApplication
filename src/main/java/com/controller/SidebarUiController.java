/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.utils.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author ngoch
 */
public class SidebarUiController implements Initializable {
    @FXML
    private Button home_btn;

    @FXML
    private Button search_btn;

    @FXML
    private Button library_btn;

    @FXML
    private Button favourites_tracks_btn;
    
    private Utils ut = new Utils();
    
    private static AnchorPane showMain_container;
    private static AnchorPane show_all_container;
    private static FlowPane show_detail_container;
    private static BorderPane borderPaneRoot;
    
    private Node centerNode;

    public static void setShowMain_container(AnchorPane showMain_container) {
        SidebarUiController.showMain_container = showMain_container;
    }

    public static void setShow_all_container(AnchorPane show_all_container) {
        SidebarUiController.show_all_container = show_all_container;
    }

    public static void setShow_detail_container(FlowPane show_detail_container) {
        SidebarUiController.show_detail_container = show_detail_container;
    }

    public static void setBorderPaneRoot(BorderPane borderPaneRoot) {
        SidebarUiController.borderPaneRoot = borderPaneRoot;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            home_btn.setOnAction(event -> {
                centerNode = (Node) borderPaneRoot.lookup("#homeSection");
                if (centerNode != null) {
                    showMain_container.setVisible(true);
                    show_all_container.setVisible(false);
                    show_detail_container.getChildren().clear();
                } else {
                    ut.loadSectionFxml(borderPaneRoot, "/application/views/UI/Home.fxml", "center");
                }                
            });
            
            search_btn.setOnAction(event -> {
                System.out.println("hello search btn");
            });
            
            library_btn.setOnAction(event -> {
                centerNode = (Node) borderPaneRoot.lookup("#librarySection");
                if (centerNode == null) {
                    ut.loadSectionFxml(borderPaneRoot, "/application/views/UI/CurrentTrackList.fxml", "center");
                }
            });
            
            favourites_tracks_btn.setOnAction(event -> {
                System.out.println("hello favourite btn");
            });
        });
    }
    
}
