/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.App;
import com.dao.GenreTrackDao;
import com.model.GenreTrack;
import com.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author daoqu
 */
public class AdminGenreController implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private VBox recordsWrapper;

    public Utils ut = new Utils();

    private GenreTrackDao genretrackDao = new GenreTrackDao();

    public static AdminGenreController adminGenreController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        printRecords("artist");
        adminGenreController = this;
    }

    public void printRecords(String role) {
        recordsWrapper.getChildren().clear();

        ArrayList<GenreTrack> genreList = genretrackDao.select_all_genreTrack("active");

        Integer count = 1;
        for (GenreTrack genre : genreList) {
            AnchorPane anchorPane = new AnchorPane();
//            anchorPane.setId(i.getId().toString());

            Label label1 = new Label(count.toString());
            label1.setLayoutX(14.0);
            label1.setLayoutY(13.0);
            label1.setFont(new Font(13.0));

            Label label2 = new Label(genre.getName());
            label2.setLayoutX(82.0);
            label2.setLayoutY(13.0);
            label2.setPrefHeight(19.0);
            label2.setPrefWidth(162.0);
            label2.setFont(new Font(13.0));

            Button editButton = new Button("Edit");
            editButton.setLayoutX(495.0);
            editButton.setLayoutY(9.0);
            editButton.setMnemonicParsing(false);
            editButton.getStyleClass().add("button-edit");
            editButton.setOnMousePressed(event -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/admin/genreForm.fxml"));
                    AdminGenreFormController controller = new AdminGenreFormController(genre);
                    fxmlLoader.setController(controller);
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(AdminGenreController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

            Button deleteButton = new Button("Delete");
            deleteButton.setLayoutX(539.0);
            deleteButton.setLayoutY(9.0);
            deleteButton.setMnemonicParsing(false);
            deleteButton.getStyleClass().add("button-delete");
            deleteButton.setOnMousePressed(event -> {
                recordsWrapper.getChildren().remove(anchorPane);
                Boolean isSuccess = genretrackDao.delete(genre.getId());
                if (isSuccess) {
                    ut.showNotification("Success", "Delete genre successfully", Utils.NotificationType.INFORMATION, Duration.seconds(3));
                } else {
                    ut.showNotification("Error", "Delete genre error", Utils.NotificationType.ERROR, Duration.seconds(3));
                }
            });
            anchorPane.getChildren().addAll(label1, label2, editButton, deleteButton);
            recordsWrapper.getChildren().add(anchorPane);
            count++;
        }
    }

    @FXML
    public void handleAddBtn(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/admin/genreForm.fxml"));
            AdminGenreFormController controller = new AdminGenreFormController();
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(AdminGenreController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
