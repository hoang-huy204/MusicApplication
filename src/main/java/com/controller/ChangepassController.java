/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.dao.AuthDao;
import com.model.Auth;
import com.utils.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author daoqu
 */
public class ChangepassController implements Initializable {

    @FXML
    private PasswordField mkold;
    @FXML
    private PasswordField mknew;
    @FXML
    private PasswordField confirmpass;
    @FXML
    private Button btnsavechange;
    private AuthDao authDao = new AuthDao();
    
    private Integer authId;
    
    private Utils ut = new Utils();
    
    private Scene currentScene;
    private Stage currentStage;

    public ChangepassController() {
    }

    public ChangepassController(Integer authId) {
        this.authId = authId;
    }

//private Auth user;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            currentScene = mkold.getScene();
            currentStage = (Stage) currentScene.getWindow();
        });
        
        btnsavechange.setOnAction(event -> savePassword());
    }

    private void savePassword() {
        String oldPassword = mkold.getText();
        String newPassword = mknew.getText();
        String confirmPassword = confirmpass.getText();

        if (!newPassword.equals(confirmPassword)) {
            ut.showNotification("Error", "New password and confirm password do not match", Utils.NotificationType.ERROR, Duration.seconds(3));
            return;
        }

        boolean updateSuccessful = authDao.updatePassword(oldPassword, newPassword, authId);
        if (updateSuccessful) {
            ut.showNotification("Success", "Updated password successfully", Utils.NotificationType.INFORMATION, Duration.seconds(3));
            currentStage.close();
        } else {
            ut.showNotification("Error", "The old password is incorrect", Utils.NotificationType.ERROR, Duration.seconds(3));
        }

    }
}
