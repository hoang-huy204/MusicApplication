/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller;

import static com.controller.AdminAuthController.adminAuthController;
import com.dao.AuthDao;
import com.model.Auth;
import com.utils.Utils;
import static com.utils.Utils.validateEmail;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ngoch
 */
public class AdminAuthFormController implements Initializable {

    @FXML
    private TextField ageField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField fullnameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<String> roleChoose;

    @FXML
    private Button saveBtn;

    private Utils ut = new Utils();

    private AuthDao authDao = new AuthDao();

    private Scene currentScene;
    private Stage currentStage;

    private Boolean isEditting = false;

    private Auth authEditting;

    private Boolean ispwEdit = false;

    public AdminAuthFormController() {
    }

    public AdminAuthFormController(Auth authEditting) {
        this.authEditting = authEditting;
        this.isEditting = true;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            currentScene = fullnameField.getScene();
            currentStage = (Stage) currentScene.getWindow();
        });

        // TODO
        ChangeListener<String> textChangeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!ispwEdit) {
                    ispwEdit = true;
                }
            }
        };

        passwordField.textProperty().addListener(textChangeListener);

        roleChoose.setItems(FXCollections.observableArrayList("Admin", "Artist", "User"));

        if (isEditting) {
            emailField.setText(authEditting.getEmail());
            roleChoose.setValue(authEditting.getRole());
            fullnameField.setText(authEditting.getFullname());
            ageField.setText(authEditting.getAge().toString());
            passwordField.setText(authEditting.getPassword());
            ispwEdit = false;
        }

        saveBtn.setOnAction(event -> {
            if (isEditting) {
                update();
            } else {
                add();
            }
        });
    }

    public void add() {
        String emailString = emailField.getText();
        String roleString = roleChoose.getValue();
        String fullnameString = fullnameField.getText();
        Integer ageInteger = null;
        if (!ageField.getText().isEmpty()) {
            try {
                ageInteger = Integer.parseInt(ageField.getText());
            } catch (NumberFormatException e) {
                ageInteger = null;
            }
        }
        String passwordString = passwordField.getText();

        Boolean isValidate = validate(emailString, roleString, fullnameString, ageInteger, passwordString);
        if (isValidate) {
            Auth auth = new Auth(null, emailString, passwordString, roleString, null, fullnameString, ageInteger, null, "active", null);
            Boolean isSuccess = authDao.add(auth);
            if (isSuccess) {
//                ut.showNotification("Success", "Add auth successfully", Utils.NotificationType.INFORMATION, Duration.seconds(3));
                currentStage.close();
                adminAuthController.printRecords(roleString, "active");
            } else {
                ut.showNotification("Error", "Add auth error", Utils.NotificationType.ERROR, Duration.seconds(3));
            }
        }

//        System.out.println(emmaiString + " " + roleString + " " + fullname + " " + ageInteger + " " + passwordString);
    }

    public void update() {
        String emailString = emailField.getText();
        String roleString = roleChoose.getValue();
        String fullnameString = fullnameField.getText();
        Integer ageInteger = null;
        if (!ageField.getText().isEmpty()) {
            try {
                ageInteger = Integer.parseInt(ageField.getText());
            } catch (NumberFormatException e) {
                ageInteger = null;
            }
        }
        String passwordString = passwordField.getText();

        Boolean isValidate = validate(emailString, roleString, fullnameString, ageInteger, passwordString);
        if (isValidate) {
            Auth auth = new Auth(authEditting.getId(), emailString, passwordString, roleString, null, fullnameString, ageInteger, null, null, null);

            Boolean isSuccess = false;
            if (!ispwEdit) {
                isSuccess = authDao.updateNotPass(auth);
                System.out.println("Update k mk");
            } else {
                isSuccess = authDao.update(auth);
                System.out.println("Update mk");
            }
            if (isSuccess) {
                currentStage.close();
                adminAuthController.printRecords(roleString, "active");
            } else {
                ut.showNotification("Error", "Update auth error", Utils.NotificationType.ERROR, Duration.seconds(3));
            }
        }

//        System.out.println(emmaiString + " " + roleString + " " + fullname + " " + ageInteger + " " + passwordString);
    }

    public Boolean validate(String email, String role, String fullname, Integer age, String password) {
        Boolean isValid = true;
        if (email.isEmpty()) {
            ut.showNotification("Error", "Email fields can't be empty", Utils.NotificationType.ERROR, Duration.seconds(3));
            isValid = false;
        }
        boolean isValidEmail = validateEmail(email);
        if (!isValidEmail) {
            ut.showNotification("Error", "Invalid email", Utils.NotificationType.ERROR, Duration.seconds(3));
            isValid = false;
        }

        if (role == null) {
            ut.showNotification("Error", "Role fields can't be empty", Utils.NotificationType.ERROR, Duration.seconds(3));
            isValid = false;
        }

        if (fullname.isEmpty()) {
            ut.showNotification("Error", "Fullname fields can't be empty", Utils.NotificationType.ERROR, Duration.seconds(3));
            isValid = false;
        }

        if (age == null) {
            ut.showNotification("Error", "Age fields can't be empty or invalid age", Utils.NotificationType.ERROR, Duration.seconds(3));
            isValid = false;
        }

        if (password.isEmpty()) {
            ut.showNotification("Error", "Password fields can't be empty", Utils.NotificationType.ERROR, Duration.seconds(3));
            isValid = false;
        }

        if (password.length() < 4) {
            ut.showNotification("Error", "Password should be over 4 characters", Utils.NotificationType.ERROR, Duration.seconds(3));
            isValid = false;
        }

        return isValid;
    }
}
