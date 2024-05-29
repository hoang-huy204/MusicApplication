/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.App;
import static com.controller.LoginController.authLogin;
import com.dao.AuthDao;
import com.model.Auth;
import com.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author daoqu
 */
public class ProfileuserController implements Initializable {

    @FXML
    private TextField namelabel;
    @FXML
    private TextField emaillabel;
    @FXML
    private PasswordField passwordlabel;
    @FXML
    private Button upava;
    @FXML
    private Button changepass;
    @FXML
    private Button savepro;
    @FXML
    private Circle imagecircle;
    private Auth user;
    private String currentPath;
    private boolean isEdit = false;

    private AuthDao authDao = new AuthDao();
    public static ProfileuserController prouser;
    private Utils utils = new Utils();

    public ProfileuserController(Auth user) {
        this.user = user;
        this.isEdit = true;
    }

    public ProfileuserController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        System.out.println("dmmm");
// currentPath = System.getProperty("user.dir");
//        currentPath = currentPath.replace("\\", "/");
//        if (user != null) {
//             Image image = new Image("file:" + utils.getCurrentPath() + "/src/main/resources/application/static/images/users/" + user.getImage());
//            ImagePattern avatarImagePattern = new ImagePattern(image);
//            imagecircle.setFill(avatarImagePattern);
//            namelabel.setText(user.getFullname());
//            emaillabel.setText(user.getEmail());
//            passwordlabel.setText(user.getPassword());
//        } else {
//            System.out.println("nguu");
//        }
//                prouser = this;

//             String role = user.getRole();
//              String status = user.getStatus();
//             ArrayList<Auth> authList = authDao.selectAll(status);
//           if(!authList.isEmpty()){
//               Auth user = authList.get(0);
        if (isEdit) {
            File imageFile = new File("src/main/resources/application/static/images/users/" + user.getImage());
            if (imageFile.exists()) {
                Image image = new Image("file:" + utils.getCurrentPath() + "/src/main/resources/application/static/images/users/" + user.getImage());
                ImagePattern avatarImagePattern = new ImagePattern(image);
                imagecircle.setFill(avatarImagePattern);
            } else {
                System.out.println("image file k ton tai");
            }

            namelabel.setText(user.getFullname());
            emaillabel.setText(user.getEmail());
            passwordlabel.setText(user.getPassword());
        }
        savepro.setOnAction(event -> {
            if (isEdit) {
                System.out.println("update");
                updatepro();
            } else {
                System.out.println("nothing");
            }
        });
    }

    private void updatepro() {
        String name = namelabel.getText();
        String email = emaillabel.getText();
//        String password = passwordlabel.getText();
//        System.out.println(imagecircle);
        String imagePath = null;
        if (imagecircle.getFill() instanceof ImagePattern) {
            ImagePattern imagePattern = (ImagePattern) imagecircle.getFill();
            Image image = imagePattern.getImage();
            imagePath = image != null ? image.getUrl() : null;
            if (imagePath != null) {
                int lastIndex = imagePath.lastIndexOf('/');
                if (lastIndex != -1) {
                    imagePath = imagePath.substring(lastIndex + 1);
                }
            }
        }

        user.setFullname(name);
        user.setEmail(email);
//        user.setPassword(password);
        user.setImage(imagePath);

        Boolean isSuccess = authDao.updateuser(user);
        if (isSuccess) {
            utils.showNotification("Success", "Update profileuser successfully", Utils.NotificationType.INFORMATION, javafx.util.Duration.seconds(3));
        } else {
            utils.showNotification("error", "Update profileuser error", Utils.NotificationType.INFORMATION, javafx.util.Duration.seconds(3));
        }
    }

    @FXML
    public void uploadImagepro() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                if (ImageIO.read(selectedFile) != null) {
                    Image image = new Image(selectedFile.toURI().toString());
                    ImagePattern avatarImagePattern = new ImagePattern(image);
                    imagecircle.setFill(avatarImagePattern);

                    File destinationDirectory = new File("src/main/resources/application/static/images/users");
                    File oldImageDirectory = new File("src/main/resources/application/static/images/user" + user.getImage());

                    if (oldImageDirectory.exists()) {
                        oldImageDirectory.delete();
                        System.out.println("da xoa old image");
                    }

                    if (destinationDirectory.exists()) {
                        Path sourcePath = selectedFile.toPath();
                        Path destinationPath = destinationDirectory.toPath().resolve(selectedFile.getName());

                        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("da copy anh thanh cong");
                    } else {
                        System.out.println("Thu muc khong ton tai!");
                    }
                }

            } catch (Exception e) {
                // Xử lý nếu có lỗi xảy ra khi tải ảnh
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handlechangepass(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/UI/changepass.fxml"));
            ChangepassController controller = new ChangepassController(authLogin.getId());
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ProfileuserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
