/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.App;
import com.dao.PackageDao;
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
public class AdminPackageController implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private VBox recordsWrapper;

    public Utils ut = new Utils();

//    private GenreTrackDao genretrackDao = new GenreTrackDao();
//
    public static AdminPackageController adminPackageController;
    private PackageDao packageDao = new PackageDao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        printRecords("active");
        adminPackageController = this;
    }

    public void printRecords(String status) {
        System.out.println("printRecords");
        recordsWrapper.getChildren().clear();

        ArrayList<com.model.Package> packageList = packageDao.select_all_package(status);

        Integer count = 1;
        for (com.model.Package i : packageList) {
            AnchorPane anchorPane = new AnchorPane();

            Label label1 = new Label(count.toString());
            label1.setLayoutX(20);
            label1.setLayoutY(13);

            Label label2 = new Label(i.getPackageName());
            label2.setLayoutX(91);
            label2.setLayoutY(13);

            Label label3 = new Label("$ " + i.getPackagePrice().toString());
            label3.setLayoutX(281);
            label3.setLayoutY(13);

            Button button1 = new Button("Edit");
            button1.setLayoutX(495);
            button1.setLayoutY(10);
            button1.setStyle("-fx-background-color: green;");
            button1.setTextFill(javafx.scene.paint.Color.WHITE);
            if (status.equalsIgnoreCase("inactive")) {
                button1.setText("Restore");
            }
            button1.setOnMousePressed(event -> {
                if (status.equalsIgnoreCase("active")) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/admin/PackageForm.fxml"));
                        AdminPackageFormController controller = new AdminPackageFormController(i);
                        fxmlLoader.setController(controller);
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(AdminPackageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Boolean isSuccess = packageDao.changeStatus("active", i.getId());
                    if (isSuccess) {
                        ut.showNotification("Success", "Restore package successfully", Utils.NotificationType.INFORMATION, Duration.seconds(3));
                        printRecords("active");
                    } else {
                        ut.showNotification("Error", "Restore package error", Utils.NotificationType.ERROR, Duration.seconds(3));
                    }
                }
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setLayoutX(543);
            if (status.equalsIgnoreCase("inactive")) {
                deleteButton.setLayoutX(560);
            }
            deleteButton.setLayoutY(10);
            deleteButton.setStyle("-fx-background-color: red;");
            deleteButton.setTextFill(javafx.scene.paint.Color.WHITE);
            deleteButton.setOnMousePressed(event -> {
                if (status.equalsIgnoreCase("active")) {
                    Boolean isSuccess = packageDao.changeStatus("inactive", i.getId());
                    if (isSuccess) {
                        ut.showNotification("Success", "Change status package successfully", Utils.NotificationType.INFORMATION, Duration.seconds(3));
                        printRecords("active");
                    } else {
                        ut.showNotification("Error", "Change status package error", Utils.NotificationType.ERROR, Duration.seconds(3));
                    }
                } else {
                    Boolean isSuccess = packageDao.delete(i.getId());
                    if (isSuccess) {
                        ut.showNotification("Success", "Delete package successfully", Utils.NotificationType.INFORMATION, Duration.seconds(3));
                        printRecords("active");
                    } else {
                        ut.showNotification("Error", "Delete package error", Utils.NotificationType.ERROR, Duration.seconds(3));
                    }
                }
            });

            anchorPane.getChildren().addAll(label1, label2, label3, button1, deleteButton);
            recordsWrapper.getChildren().add(anchorPane);
            count++;
        }
    }

    @FXML
    public void handleAddBtn(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/admin/PackageForm.fxml"));
            AdminPackageFormController controller = new AdminPackageFormController();
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(AdminPackageController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        System.out.println("add");
    }

    @FXML
    public void handleDeletedBtn() {
        printRecords("inactive");
    }
}
