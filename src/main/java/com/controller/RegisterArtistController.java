package com.controller;

import com.App;
import com.dao.AuthDao;
import com.utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.lang.IllegalArgumentException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RegisterArtistController implements Initializable {

    private AuthDao authDao = new AuthDao();

    @FXML
    private AnchorPane send_request_container;

    @FXML
    private Hyperlink btn_how_get_password;

    @FXML
    private TextField password_content;

    @FXML
    private Button registerArtist_btn;

    @FXML
    private AnchorPane token_container;

    @FXML
    private TextField token_content;

    @FXML
    private Button btn_resend_token;

    @FXML
    private Button btn_submit_Token;

    private Boolean isVerifyArtist;

    private String emailRegisterArtist;

    public RegisterArtistController() {
//        System.out.println("Controller khong");
    }

    public RegisterArtistController(String emailArtist) {
        this.emailRegisterArtist = emailArtist;
    }

    private Scene currentScene;
    private Stage currentStage;

    private Utils ut = new Utils();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            currentScene = registerArtist_btn.getScene();
            currentStage = (Stage) currentScene.getWindow();

            registerArtist_btn.setOnAction(event -> {
                if (!password_content.getText().isEmpty()) {
                    ut.showNotification("Information", "Sending email, please wait ...", Utils.NotificationType.ERROR, Duration.ZERO);
                    sendRequestRegisterArtist(emailRegisterArtist, password_content.getText().trim());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("error");
                    alert.setContentText("password is not empty");
                    alert.showAndWait();
                }
            });

            btn_resend_token.setOnAction(event -> {
                if (!password_content.getText().isEmpty()) {
                    sendRequestRegisterArtist(emailRegisterArtist, password_content.getText().trim());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("error");
                    alert.setContentText("password is not empty");
                    alert.showAndWait();
                }
            });

            btn_submit_Token.setOnAction(event -> {
                if (!token_content.getText().isEmpty()) {
                    verify_Token(emailRegisterArtist, token_content.getText().trim());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("error");
                    alert.setContentText("token is not empty");
                    alert.showAndWait();
                }
            });

            btn_how_get_password.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://support.google.com/mail/answer/185833?hl=en"));
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    private void verify_Token(String emailSender, String token) {
        if (emailSender != null && token != null) {
            try {
                if (authDao.verify_TokenForArtist(emailSender, token)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("successfully");
                    alert.setContentText("Successful artist registration");
                    alert.showAndWait();
                    handleBackLoginPage();
                }
            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private void sendRequestRegisterArtist(String emailSender, String app_password) {
        String emailRecipient = "musicaplications2@gmail.com";
        String token = UUID.randomUUID().toString();
        try {
            if (saveToken(emailSender, token)) {
                if (sendRequest(emailSender, app_password, emailRecipient, token)) {
                    ut.showNotification("Information", "Please check your email, if not, you can contact admin for support !", Utils.NotificationType.ERROR, Duration.ZERO);
                    send_request_container.setVisible(false);
                    token_container.setVisible(true);
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean sendRequest(String emailSender, String password, String emailRecipient, String token) {
        if (emailSender != null && password != null && emailRecipient != null) {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.javamail.default.address.map", System.getProperty("user.dir") + "\\src\\main\\resources\\application\\Utils\\META-INF\\javamail.default.address.map");

            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(emailSender, password);
                }
            });

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(emailSender));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRecipient));

                message.setSubject("Confirm Request");
                String htmlContent = "<html><body>"
                        + "<h1>" + emailSender + " Register as an artist</h1>"
                        + "<a href=\"mailto:" + emailSender + "?subject=Token&body=%20" + token + "\">"
                        + "<button id ='btn_submit' style=\"padding:10px; background-color:#4CAF50; color:white; border:none; cursor:pointer;\">Confirm</button></a>"
                        + "</body></html>";
                message.setContent(htmlContent, "text/html");
                Transport.send(message);
                return true;
            } catch (AuthenticationFailedException authEx) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setContentText("incorrect password");
                alert.showAndWait();
                send_request_container.setVisible(true);
                token_container.setVisible(false);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean saveToken(String email, String token) {
        if (email != null && token != null) {
            if (authDao.saveTokenForArtist(email, token)) {
                return true;
            }
        }
        return false;
    }

    public void switchTokenForm() {
        send_request_container.setVisible(false);
        token_container.setVisible(true);
    }

    public void handleBackPw() {
        send_request_container.setVisible(true);
        token_container.setVisible(false);
    }

    public void handleBackLoginPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/UI/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            currentStage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
