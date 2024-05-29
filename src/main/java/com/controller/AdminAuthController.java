/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.App;
import com.dao.AuthDao;
import com.model.Auth;
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
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author ngoch
 */
public class AdminAuthController implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private Button adminSwitchBtn;

    @FXML
    private Button artistSwitchBtn;

    @FXML
    private VBox recordsWrapper;

    @FXML
    private Button userSwitchBtn;

    @FXML
    private Button deletedSwitchBtn;

    private AuthDao authDao = new AuthDao();

    public static AdminAuthController adminAuthController;

    public Utils ut = new Utils();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        adminAuthController = this;

        printRecords("artist", "active");

        artistSwitchBtn.setOnMousePressed(event -> {
            printRecords("artist", "active");
        });

        userSwitchBtn.setOnMousePressed(event -> {
            printRecords("user", "active");
        });

        adminSwitchBtn.setOnMousePressed(event -> {
            printRecords("admin", "active");
        });

        deletedSwitchBtn.setOnMousePressed(event -> {
            printRecords("", "inactive");
        });
    }

    public void printRecords(String role, String status) {
        recordsWrapper.getChildren().clear();

        ArrayList<Auth> auth_List = new ArrayList<>();

        if (status.equals("inactive")) {
            auth_List = authDao.selectAll(status);
        } else {
            auth_List = authDao.selectAllAuthByRole(role, status);
        }

        Integer count = 1;
        if (auth_List != null) {
            for (Auth i : auth_List) {

                AnchorPane anchorPane = new AnchorPane();
//            anchorPane.setId(i.getId().toString());

                Label label1 = new Label(count.toString());
                label1.setLayoutX(14.0);
                label1.setLayoutY(13.0);
                label1.setFont(new Font(13.0));

                Label label2 = new Label(i.getEmail());
                label2.setLayoutX(82.0);
                label2.setLayoutY(13.0);
                label2.setPrefHeight(19.0);
                label2.setPrefWidth(162.0);
                label2.setFont(new Font(13.0));

                Label label3 = new Label(i.getFullname());
                label3.setLayoutX(289.0);
                label3.setLayoutY(13.0);
                label3.setPrefHeight(19.0);
                label3.setPrefWidth(186.0);
                label3.setFont(new Font(13.0));

                Button btn1 = new Button();
                btn1.setLayoutX(495.0);
                btn1.setLayoutY(9.0);
                btn1.setMnemonicParsing(false);
                if (status.equals("inactive")) {
                    btn1.setText("Restore");
                } else {
                    btn1.setText("Edit");
                }
                btn1.setOnMousePressed(event -> {
                    if (status.equals("inactive")) {
                        Boolean isSuccess = authDao.changeStatus("active", i.getId());
                        if (isSuccess) {
                            ut.showNotification("Success", "Restore auth successfully", Utils.NotificationType.INFORMATION, Duration.seconds(3));
                            printRecords(i.getRole(), "active");
                        } else {
                            ut.showNotification("Error", "Restore auth error", Utils.NotificationType.ERROR, Duration.seconds(3));
                        }
                    } else {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/admin/AuthForm.fxml"));
                            AdminAuthFormController controller = new AdminAuthFormController(i);
                            fxmlLoader.setController(controller);
                            Scene scene = new Scene(fxmlLoader.load());
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(AdminAuthController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                });

                Button deleteButton = new Button("Delete");
                if (status.equals("inactive")) {
                    deleteButton.setLayoutX(548.0);
                } else {
                    deleteButton.setLayoutX(542.0);
                }
                deleteButton.setLayoutX(545.0);
                deleteButton.setLayoutY(9.0);
                deleteButton.setMnemonicParsing(false);
                deleteButton.setOnMousePressed(event -> {
                    if (status.equals("inactive")) {
                        Boolean isSuccess = authDao.delete(i.getId());
                        if (isSuccess) {
                            ut.showNotification("Success", "Delete auth successfully", Utils.NotificationType.INFORMATION, Duration.seconds(3));
                            printRecords(i.getRole(), "active");
                        } else {
                            ut.showNotification("Error", "Delete auth error", Utils.NotificationType.ERROR, Duration.seconds(3));
                        }
                    } else {
                        Boolean isSuccess = authDao.changeStatus("inactive", i.getId());
                        if (isSuccess) {
                            ut.showNotification("Success", "Change status auth successfully", Utils.NotificationType.INFORMATION, Duration.seconds(3));
                            printRecords(i.getRole(), "active");
                        } else {
                            ut.showNotification("Error", "Change status auth error", Utils.NotificationType.ERROR, Duration.seconds(3));
                        }
                    }
                });

                anchorPane.getChildren().addAll(label1, label2, label3, btn1, deleteButton);
                recordsWrapper.getChildren().add(anchorPane);
                count++;
            }
        }
    }

    @FXML
    public void handleAddBtn(ActionEvent event) {
//        System.out.println("hello");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/admin/AuthForm.fxml"));
            AdminAuthFormController controller = new AdminAuthFormController();
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AdminAuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
