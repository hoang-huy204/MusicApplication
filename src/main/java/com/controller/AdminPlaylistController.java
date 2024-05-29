/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.App;
import com.dao.AuthDao;
import com.dao.PlayListDao;
import com.model.Auth;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.model.Playlist;
import com.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class AdminPlaylistController implements Initializable {
    private Utils utils = new Utils();

    private PlayListDao playListDao = new PlayListDao();

    @FXML
    private Button addBtn;

    @FXML
    private Button deletedSwitchBtn;

    @FXML
    private VBox recordsWrapper;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        printRecords("active");

        addBtn.setOnAction(event_add -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/admin/PLayListForm.fxml"));
                AdminPlayListFormController controller = new AdminPlayListFormController();
                fxmlLoader.setController(controller);
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AdminAuthController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        deletedSwitchBtn.setOnAction(event_delete -> {
            printRecords("inactive");
        });

    }

    public void printRecords(String status) {
        recordsWrapper.getChildren().clear();
        if (playListDao.selectAllPlaylist("active") != null && !playListDao.selectAllPlaylist("active").isEmpty()) {
            ArrayList<Playlist> playlist_List = playListDao.selectAllPlaylist(status);

            if (playlist_List != null) {
                Integer count = 1;
                for (Playlist playlist : playlist_List) {
                    AnchorPane anchorPane = new AnchorPane();

                    Label label1 = new Label(count.toString());
                    label1.setLayoutX(14.0);
                    label1.setLayoutY(13.0);
                    label1.setFont(new Font(13.0));

                    Label label2 = new Label(playlist.getName());
                    label2.setLayoutX(82.0);
                    label2.setLayoutY(13.0);
                    label2.setPrefHeight(19.0);
                    label2.setPrefWidth(162.0);
                    label2.setFont(new Font(13.0));

                    Label label3 = new Label(playlist.getDescription());
                    label3.setLayoutX(289.0);
                    label3.setLayoutY(13.0);
                    label3.setPrefHeight(19.0);
                    label3.setPrefWidth(186.0);
                    label3.setFont(new Font(13.0));

                    Button Button = new Button("Edit");
                    Button.setLayoutX(495.0);
                    Button.setLayoutY(9.0);
                    Button.setMnemonicParsing(false);
                    if (status.equals("active")) {
                        Button.setText("Edit");
                        Button.setOnMousePressed(event -> {
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/admin/PLayListForm.fxml"));
                                AdminPlayListFormController controller = new AdminPlayListFormController(playlist);
                                fxmlLoader.setController(controller);
                                Scene scene = new Scene(fxmlLoader.load());
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(AdminAuthController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    } else if (status.equals("inactive")) {
                        Button.setLayoutX(480.0);
                        Button.setText("Restore");
                        Button.setOnMousePressed(event -> {
                            dialog_ChangeStatus(playlist.getId(), "active");
                        });
                    }

                    Button deleteButton = new Button("Delete");
                    deleteButton.setLayoutX(545.0);
                    deleteButton.setLayoutY(9.0);
                    deleteButton.setMnemonicParsing(false);
                    if (status.equals("active")) {
                        deleteButton.setOnMousePressed(event -> {
                            dialog_ChangeStatus(playlist.getId(), "inactive");
                        });
                    } else if (status.equals("inactive")) {
                        deleteButton.setOnMousePressed(event -> {
                            dialog_Delete(playlist.getId());
                        });
                    }

                    anchorPane.getChildren().addAll(label1, label2, label3, Button, deleteButton);
                    recordsWrapper.getChildren().add(anchorPane);
                    count++;
                }
            }
        }
    }

    public void dialog_ChangeStatus(Integer playlist_Id, String status) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Change Status");

        ButtonType button_ChangStatusPlaylist = new ButtonType("Change Status Only Playlist", ButtonBar.ButtonData.OK_DONE);
        ButtonType button_ChangStatusPlaylist_Track = new ButtonType("Change Status Playlist and Tracks", ButtonBar.ButtonData.APPLY);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(button_ChangStatusPlaylist, button_ChangStatusPlaylist_Track, buttonTypeCancel);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == button_ChangStatusPlaylist) {
                if (playListDao.changeStatus_Playlist(playlist_Id, false, status)) {
                    printRecords("active");
                }
            }
            if (dialogButton == button_ChangStatusPlaylist_Track) {
                if (playListDao.changeStatus_Playlist(playlist_Id, true, status)) {
                    printRecords("active");
                }
            }
            return null;
        });
        dialog.showAndWait();
    }

    public void dialog_Delete(Integer playlist_Id) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Delete");

        ButtonType button_DeletePlaylist = new ButtonType("Delete Only Playlist", ButtonBar.ButtonData.OK_DONE);
        ButtonType button_DeletePlaylist_Track = new ButtonType("Delete Playlist and Tracks", ButtonBar.ButtonData.APPLY);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(button_DeletePlaylist, button_DeletePlaylist_Track, buttonTypeCancel);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == button_DeletePlaylist) {
                if (playListDao.delete_PlaylistAndTrack(playlist_Id, false)) {

                }
            }
            if (dialogButton == button_DeletePlaylist_Track) {
                if (playListDao.delete_PlaylistAndTrack(playlist_Id, true)) {

                }
            }
            return null;
        });
        dialog.showAndWait();
    }
}
