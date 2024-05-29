/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller;

import com.App;
import static com.controller.AdminAuthController.adminAuthController;
import com.dao.AuthDao;
import com.model.Auth;
import com.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RegisterController implements Initializable {

    @FXML
    private TextField ageField;

    @FXML
    private RadioButton artistRadio;

    @FXML
    private TextField emailField;

    @FXML
    private TextField fullnameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInBtn;

    @FXML
    private Button signUpBtn;

    @FXML
    private RadioButton userRadio;

    private ToggleGroup roleGroup = new ToggleGroup();

    private Utils ut = new Utils();

    private Scene currentScene;

    private Stage currentStage;

    private String roleSelectedValue = null;

    private AuthDao authDao = new AuthDao();

//    public RegisterController(com.dao.AuthDao authService) {
//     
//    this.authService = authService;
//    }
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            currentScene = fullnameField.getScene();
            currentStage = (Stage) currentScene.getWindow();
            ut.setPosWindow(currentScene);
        });

        userRadio.setToggleGroup(roleGroup);
        artistRadio.setToggleGroup(roleGroup);

        roleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue instanceof RadioButton) {
                RadioButton selectedRadioButton = (RadioButton) newValue;
                roleSelectedValue = selectedRadioButton.getText();
            }
        });
    }

    public void switchLoginPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/UI/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            currentStage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void handleSignUp() {
        String fullnameStr = fullnameField.getText();
        Integer ageInt = null;
        if (!ageField.getText().isEmpty()) {
            try {
                ageInt = Integer.parseInt(ageField.getText());
            } catch (NumberFormatException e) {
                ageInt = null;
            }
        }
        String emailStr = emailField.getText();
        String passwordStr = passwordField.getText();

        boolean isValid = isValidate(fullnameStr, ageInt, emailStr, passwordStr);
        if (isValid) {
            String statusRegister = "active";
            Auth auth = new Auth(null, emailStr, passwordStr, "User", null, fullnameStr, ageInt, null, statusRegister, null);
            Boolean isSuccess = authDao.add(auth);
            if (isSuccess) {
                try {
                    ut.showNotification("Success", "Congratulations, you have successfully registered", Utils.NotificationType.INFORMATION, Duration.seconds(3));
                    FXMLLoader fxmlLoader = null;
                    if (roleSelectedValue.equals("User")) {
                        fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/UI/login.fxml"));
                    } else {
                        fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/UI/RegisterArtist.fxml"));
                        RegisterArtistController controller = new RegisterArtistController(emailStr);
                        fxmlLoader.setController(controller);
                    }
                    Scene scene = new Scene(fxmlLoader.load());
                    currentStage.setScene(scene);
//                currentStage.close();
                } catch (IOException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                ut.showNotification("Error", "Update auth error", Utils.NotificationType.ERROR, Duration.seconds(3));
            }
        }
    }

    private boolean isValidate(String fullname, Integer age, String email, String password) {
        if (fullname.length() < 4) {
            ut.showNotification("Error", "Fullname cannot be less than 4 characters", Utils.NotificationType.ERROR, Duration.seconds(3));
            return false;
        }
        
        if (age == null) {
            ut.showNotification("Error", "Age fields can't be empty or invalid age", Utils.NotificationType.ERROR, Duration.seconds(3));
            return false;
        }
        if (email.isEmpty() || !email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            ut.showNotification("Error", "Empty or misformatted emails", Utils.NotificationType.ERROR, Duration.seconds(3));
            return false;
        }

        if (password.length() < 4) {
            ut.showNotification("Error", "Password cannot be less than 4 characters", Utils.NotificationType.ERROR, Duration.seconds(3));
            return false;
        }

        if (roleSelectedValue == null) {
            ut.showNotification("Error", "Please enter your role", Utils.NotificationType.ERROR, Duration.seconds(3));
            return false;
        }

        return true;
    }
}
