/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;

import com.App;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.controlsfx.control.Notifications;

import com.controller.CurrentTrackListController;
import com.controller.HomeController;
import com.controller.MusicControlController;
import com.controller.SidebarUiController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ngoch
 */
public class Utils {

    private String currentPath;

    private HashMap<String, Double> windowSize;
    private HashMap<String, Double> windowPos;

    public String getCurrentPath() {
        return currentPath;
    }

    public Utils() {
        this.caculateWindowSize();
        currentPath = System.getProperty("user.dir");
        currentPath = currentPath.replace("\\", "/");
    }

    public HashMap<String, Double> getWindowSize() {
        return windowSize;
    }

    public HashMap<String, Double> getWindowPos() {
        return windowPos;
    }

    private void caculateWindowSize() {
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        this.windowSize = new HashMap<>();
        this.windowSize.put("width", screenWidth);
        this.windowSize.put("height", screenHeight);
    }

    private void caculateWindowPosition(Double screenWidth, Double screenHeight) {
        Double x = (this.getWindowSize().get("width") - screenWidth) / 2;
        Double y = (this.getWindowSize().get("height") - screenHeight) / 2;
        this.windowPos = new HashMap<>();
        this.windowPos.put("xPos", x);
        this.windowPos.put("yPos", y);
    }

    public void setPosWindow(Scene currentScene) {
        Stage currentStage = (Stage) currentScene.getWindow();
        Double sceneWidth = currentScene.getWidth();
        Double sceneHeight = currentScene.getHeight();

        this.caculateWindowPosition(sceneWidth, sceneHeight);
        currentStage.setX(this.windowPos.get("xPos"));
        currentStage.setY(this.windowPos.get("yPos"));
    }

    public static void showNotification(String title, String message, NotificationType type, Duration duration) {
        Notifications notification = Notifications.create()
                .title(title)
                .text(message)
                .position(Pos.TOP_RIGHT)
                .hideAfter(duration);

        switch (type) {
            case INFORMATION:
                notification.showInformation();
                break;
            case WARNING:
                notification.showWarning();
                break;
            case ERROR:
                notification.showError();
                break;
            default:
                notification.show();
                break;
        }
    }

    public enum NotificationType {
        INFORMATION,
        WARNING,
        ERROR
    }

    public static Boolean showConfirrm() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Dialog");
        confirmationAlert.setHeaderText("Are you sure you want to continue?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationAlert.getButtonTypes().setAll(yesButton, noButton);
        final Boolean[] result = {true};
        confirmationAlert.showAndWait().ifPresent(reponse -> {
            if (reponse == noButton) {
                result[0] = false;
            }
        });
        return result[0];
    }

//    Send email method
    public static void sendAuthenticationEmail(String recipientEmail, String authenticationToken, String title) {
        final String email = "musicaplications2@gmail.com";
        final String password = "ppbzxkzoyzhsxptg";
        final String host = "smtp.gmail.com";
        final int port = 587;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session;
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(title);
            message.setText("Click the following link to verify: " + authenticationToken);
            Transport.send(message);
            System.out.println("Email has been sent successfully");
        } catch (MessagingException ex) {
            ex.printStackTrace();
            System.out.println("Error send email!");
        }
    }

    public RotateTransition createRotateTransition() {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(5));
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.setAutoReverse(false);
        return rotateTransition;
    }

    public static void loadSectionFxml(BorderPane borderPane, String urlSection, String position) {
        try {
            Node fxmlNode = null;
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(urlSection));
            fxmlNode = fxmlLoader.load();

            if (urlSection.equals("/application/views/UI/MusicControl.fxml")) {
                MusicControlController musicControlController = fxmlLoader.getController();
                CurrentTrackListController.musicControlController = musicControlController;
                musicControlController.setBorderPane(borderPane);
            } else if (urlSection.equals("/application/views/UI/CurrentTrackList.fxml")) {
                MusicControlController.setCurrentTrackListController(fxmlLoader.getController());
            }

            switch (position) {
                case "top":
                    borderPane.setTop(fxmlNode);
                    break;
                case "center":
                    switchCenterNode(fxmlNode, borderPane);
                    break;
                case "left":
                    borderPane.setLeft(fxmlNode);
                    break;
                case "bottom":
                    borderPane.setBottom(fxmlNode);
                    break;
                default:
                    throw new AssertionError("Invalid position");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void loadSectionFxml2(BorderPane borderPane, String urlSection, String position, Integer paramPlaylistId) {
        try {
            Node fxmlNode = null;
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(urlSection));
            if (urlSection.equals("/application/views/UI/CurrentTrackList.fxml")) {
                CurrentTrackListController controller = null;
                if (paramPlaylistId != null) {
                    controller = new CurrentTrackListController(paramPlaylistId);
                } else {
                    controller = new CurrentTrackListController();
                }
                fxmlLoader.setController(controller);
            }
            fxmlNode = fxmlLoader.load();

            if (urlSection.equals("/application/views/UI/MusicControl.fxml")) {
                MusicControlController musicControlController = fxmlLoader.getController();
                CurrentTrackListController.musicControlController = musicControlController;
                musicControlController.setBorderPane(borderPane);
            } else if (urlSection.equals("/application/views/UI/CurrentTrackList.fxml")) {
                MusicControlController.setCurrentTrackListController(fxmlLoader.getController());
            }

            switch (position) {
                case "top":
                    borderPane.setTop(fxmlNode);
                    break;
                case "center":
                    switchCenterNode(fxmlNode, borderPane);
                    break;
                case "left":
                    borderPane.setLeft(fxmlNode);
                    break;
                case "bottom":
                    borderPane.setBottom(fxmlNode);
                    break;
                default:
                    throw new AssertionError("Invalid position");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void switchCenterNode(Node newNode, BorderPane borderPane) {
        borderPane.setCenter(newNode);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), newNode);
        fadeIn.setFromValue(0.1);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(false);
        fadeIn.play();
    }

    public static boolean validateEmail(String email) {
        // Biểu thức chính quy cho địa chỉ email
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public Duration parseDuration(String durationString) {
        try {
            if (durationString.isEmpty()) {
                showNotification("Error", "Duration string is empty", NotificationType.ERROR, Duration.seconds(3));
                return null;
            }

            // Phân tích chuỗi thời gian thành giờ, phút, giây
            String[] parts = durationString.split(":");
            if (parts.length != 3) {
                showNotification("Error", "Invalid duration format", NotificationType.ERROR, Duration.seconds(3));
                return null;
            }
            
            int hours = 0;
            int minutes = 0;
            int seconds = 0;            

            try {
                hours = Integer.parseInt(parts[0]);
                minutes = Integer.parseInt(parts[1]);
                seconds = Integer.parseInt(parts[2]);
            } catch (NumberFormatException ex) {
                showNotification("Error", "Hours and moments are not properly formatted", NotificationType.ERROR, Duration.seconds(3));
                return null;
            }

            // Tạo đối tượng Duration từ giá trị phân tích
            Duration duration = Duration.hours(hours).add(Duration.minutes(minutes)).add(Duration.seconds(seconds));

            return duration;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid duration format", e);
        }
    }

//    public static boolean isSha1(String passwordString) {
//        try {
//            MessageDigest sha1Digest = MessageDigest.getInstance("SHA-1");
//            byte[] passwordBytes = passwordString.getBytes();
//            byte[] sha1Bytes = sha1Digest.digest(passwordBytes);
//            String sha1String = bytesToHex(sha1Bytes);
//            System.out.println(sha1String);
//            System.out.println(passwordString);
//            return sha1String.equals(passwordString);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    private static String bytesToHex(byte[] bytes) {
//        StringBuilder hexStringBuilder = new StringBuilder();
//        for (byte b : bytes) {
//            String hexString = Integer.toHexString(0xFF & b);
//            if (hexString.length() == 1) {
//                hexStringBuilder.append('0');
//            }
//            hexStringBuilder.append(hexString);
//        }
//        return hexStringBuilder.toString();
//    }
}
