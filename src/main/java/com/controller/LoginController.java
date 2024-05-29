/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller;

import com.App;
import com.dao.AuthDao;
import com.dao.PackageAuthDao;
import com.model.Auth;
import com.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class LoginController implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField passwordField;

    @FXML
    private Button switchRegisterPage;

    @FXML
    private Label switchArtistRegisterPage;

    private Utils ut = new Utils();

    private AuthDao authDao = new AuthDao();
    private PackageAuthDao packageAuthDao = new PackageAuthDao();

    public static Auth authLogin = null;
    
    public static Boolean isAuthVip;

    private Scene currentScene;
    private Stage currentStage;

    public Auth getAuthLogin() {
        return authLogin;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
            currentScene = emailField.getScene();
            currentStage = (Stage) currentScene.getWindow();
            ut.setPosWindow(currentScene);
        });

        switchRegisterPage.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/UI/Register.fxml"));
//                RegisterController controller = new RegisterController();
//                fxmlLoader.setController(controller);
                Scene scene = new Scene(fxmlLoader.load());
                currentStage.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        emailField.setOnAction(event -> {
            passwordField.requestFocus();
        });
        
        passwordField.setOnAction(event -> {
            handleLogin();
        });
    }

    @FXML
    void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        boolean isValid = isValidAuth(email, password);
        if (isValid) {
            authLogin = authDao.accountAuthentication(email, password);
            if (authLogin != null) {
                String src = "";
                if (authLogin.getRole().equals("admin")) {
                    src = "/application/views/admin/home.fxml";
                } else {
                    src = "/application/views/UI/home.fxml";
                    isAuthVip = packageAuthDao.checkAuthPackage(authLogin.getId());
//                    System.out.println("Trang thai vip - " + isAuthVip);
                }
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(src));
                    Scene scene = new Scene(fxmlLoader.load());
                    currentStage.setScene(scene);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                ut.showNotification("Error", "EmaIl or password is incorrect", Utils.NotificationType.ERROR, Duration.seconds(3));
            }
        }
    }

    private boolean isValidAuth(String email, String password) {
        if (email.isEmpty() || !email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            ut.showNotification("Error", "Empty or misformatted emails", Utils.NotificationType.ERROR, Duration.seconds(3));
            return false;
        }

        if (password.length() < 4) {
            ut.showNotification("Error", "Password cannot be less than 4 characters", Utils.NotificationType.ERROR, Duration.seconds(3));
            return false;
        }

        return true;
    }

    public void handleArtistRegister() {
        String emailTxt = emailField.getText();
        if (emailTxt.isEmpty() || !emailTxt.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            ut.showNotification("Error", "Empty or misformatted emails", Utils.NotificationType.ERROR, Duration.seconds(3));
            return;
        }
        Auth auth = authDao.getAuthByEmail(emailTxt);
        if (auth == null) {
            ut.showNotification("Error", "Your email is not registered", Utils.NotificationType.ERROR, Duration.seconds(3));
            return;
        } else if (auth.getRole().equalsIgnoreCase("Artist")) {
            ut.showNotification("Information", "You are already an artist", Utils.NotificationType.INFORMATION, Duration.seconds(3));
            return;
        } else if (auth.getRole().equalsIgnoreCase("User")) {
            try {
                ut.showNotification("Information", "Please enter your email's application password", Utils.NotificationType.INFORMATION, Duration.seconds(3));
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/UI/RegisterArtist.fxml"));
                RegisterArtistController controller = new RegisterArtistController(auth.getEmail());
                fxmlLoader.setController(controller);
                Scene scene = new Scene(fxmlLoader.load());
                currentStage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
;
}
