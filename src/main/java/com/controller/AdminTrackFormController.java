/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller;

import static com.controller.AdminTrackController.adminTrackController;
import com.dao.TrackDao;
import com.model.Track;
import com.controller.AdminTrackFormController;
import com.dao.GenreTrackDao;
import com.model.GenreTrack;
import com.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author ngoch
 */
public class AdminTrackFormController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField singerField;

    @FXML
    private TextArea lyricsField;

    @FXML
    private ChoiceBox<String> genrechoose;
   

    @FXML
    private TextField duraField;

    @FXML
    private TextField imageField;

    @FXML
    private TextField filepathField;
    @FXML
    private ImageView previewanh;

    @FXML
    private Button saveBtn;

    private Utils utils = new Utils();

    private TrackDao trackDao = new TrackDao();

    private Scene currentScene;
    private Stage currentStage;

    private boolean isEditing = false;

    private Track trackEditing;

    private List<GenreTrack> genreList = null;

    private GenreTrackDao genreTrackDao = new GenreTrackDao();

    public AdminTrackFormController() {
    }

    public AdminTrackFormController(Track trackEditing) {
        this.trackEditing = trackEditing;
        this.isEditing = true;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            currentScene = nameField.getScene();
            currentStage = (Stage) currentScene.getWindow();

            imageField.setEditable(false);
            filepathField.setEditable(false);
        });

        // TODO
        genreList = genreTrackDao.select_all_genreTrack("active");
//        genreList.add(new GenreTrack(1, "Pop"));
//        genreList.add(new GenreTrack(2, "Young music"));
//        genreList.add(new GenreTrack(3, "Love"));
//        genreList.add(new GenreTrack(4, "Rock"));
        // Tạo một StringConverter để hiển thị ID và tên thể loại
//        StringConverter<GenreTrack> genreStringConverter = new StringConverter<GenreTrack>() {
//            @Override
//            public String toString(GenreTrack genre) {
//                return genre.getId() + " - " + genre.getName();
//            }
//
//            @Override
//            public GenreTrack fromString(String string) {
//                // Không cần xử lý chuyển đổi ngược từ chuỗi về đối tượng Genre
//                return null;
//            }
//        };
        
//        System.out.println(genreList);
//        System.out.println(genreStringConverter);

        // Đặt danh sách đối tượng và StringConverter cho ComboBox
        for (GenreTrack i : genreList) {
            genrechoose.getItems().add(i.getName());
        }
//        genrechoose.setConverter(genreStringConverter);
        if (isEditing) {
            nameField.setText(trackEditing.getName());
            singerField.setText(trackEditing.getSingers());
            lyricsField.setText(trackEditing.getLyrics());
            for (GenreTrack i : genreList) {
                if (i.getId() == trackEditing.getGenre_id()) {
                    genrechoose.setValue(i.getName());
                }
            }
            duraField.setText(trackEditing.getDurationString());
            imageField.setText(trackEditing.getImage());
            filepathField.setText(trackEditing.getFile_path());
        }

        saveBtn.setOnAction(event -> {
            if (isEditing) {
                System.out.println("update");
                update();
            } else {
                add();
            }
        });
    }

    public void add() {
        String name = nameField.getText();
        String singers = singerField.getText();
        String lyrics = lyricsField.getText();
        Integer genreId = null;
        for (GenreTrack i : genreList) {
            if (i.getName().equalsIgnoreCase(genrechoose.getValue())) {
                genreId = i.getId();
            }
        }
        System.out.println(genreId);
        String durationString = duraField.getText();
//        if (durationString.isEmpty()) {
//            utils.showNotification("Error", "Duration field can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
//            return;
//        }
        Duration duration = null;
        if (utils.parseDuration(durationString) != null) {
            duration = Duration.ofMillis((long) utils.parseDuration(durationString).toMillis());
        }
//         uploadImage(); 
        String image = imageField.getText();
//         uploadFile();
        String filePath = filepathField.getText();

        Boolean isValidate = validate(name, genreId, singers, lyrics, image, duration, image, filePath);

        if (isValidate) {
            Track newTrack = new Track(null, name, duration, genreId, singers, lyrics, null, image, filePath, null);
            Boolean isSuccess = trackDao.add(newTrack);
            if (isSuccess) {
                utils.showNotification("Success", "Add Track successfully", Utils.NotificationType.INFORMATION, javafx.util.Duration.seconds(3));
                currentStage.close();
                adminTrackController.printRecords(name);
            } else {
                utils.showNotification("Error", "Add Track error", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            }
        }
    }

    public void update() {
        String name = nameField.getText();
        String singers = singerField.getText();
        String lyrics = lyricsField.getText();
        Integer genreId = null;
        for (GenreTrack i : genreList) {
            if (i.getName().equalsIgnoreCase(genrechoose.getValue())) {
                genreId = i.getId();
            }
        }
        String durationString = duraField.getText();
        Duration duration = null;
        if (utils.parseDuration(durationString) != null) {
            duration = Duration.ofMillis((long) utils.parseDuration(durationString).toMillis());
        }
        String image = imageField.getText();
        String filePath = filepathField.getText();

        Boolean isValidate = validate(name, genreId, singers, lyrics, image, duration, image, filePath);

        if (isValidate) {

            trackEditing.setName(name);
            trackEditing.setSingers(singers);
            trackEditing.setLyrics(lyrics);
            trackEditing.setGenre_id(genreId);
            trackEditing.setDuration(duration);
//        uploadImage();
            image = imageField.getText(); // Lấy đường dẫn hình ảnh sau khi đã cập nhật
            trackEditing.setImage(image);
//        uploadFile();
            filePath = filepathField.getText();
            trackEditing.setFile_path(filePath);

            Boolean isSuccess = trackDao.update(trackEditing);
            if (isSuccess) {
                utils.showNotification("Success", "Update Track successfully", Utils.NotificationType.INFORMATION, javafx.util.Duration.seconds(3));
                currentStage.close();
                adminTrackController.printRecords(name);
            } else {
                utils.showNotification("Error", "Update Track error", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            }
        }
    }

    @FXML
    public void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                if (ImageIO.read(selectedFile) != null) {
                    String imageName = selectedFile.getName();
                    imageField.setText(imageName);
                    Image image = new Image(selectedFile.toURI().toString());
                    previewanh.setImage(image);
                    System.out.println("Image uploaded: " + imageName);

                    File destinationDirectory = new File("src/main/resources/application/static/images/tracks");
                    if (destinationDirectory.exists()) {
                        Path sourcePath = selectedFile.toPath();
                        Path destinationPath = destinationDirectory.toPath().resolve(selectedFile.getName());

                        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("da copy anh thanh cong");
                    } else {
                        System.out.println("Thu muc khong ton tai!");
                    }
                } else {
                    utils.showNotification("Error", "Invalid image", Utils.NotificationType.ERROR, javafx.util.Duration.ZERO);
                }
            } catch (IOException ex) {
                Logger.getLogger(AdminTrackFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    public void uploadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("FIle_Path Files", "*.mp3")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            String filePath = selectedFile.getName();
            filepathField.setText(filePath);
            System.out.println("Image uploaded: " + filePath);

            File destinationDirectory = new File("src/main/resources/application/static/audio");
            if (destinationDirectory.exists()) {
                try {
                    Path sourcePath = selectedFile.toPath();
                    Path destinationPath = destinationDirectory.toPath().resolve(selectedFile.getName());
                    
                    Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("da copy music thanh cong");
                } catch (IOException ex) {
                    Logger.getLogger(AdminTrackFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Thu muc khong ton tai!");
            }
        }
    }

    public Boolean validate(String name, Integer genreId, String singer, String lyrics, String genre, Duration duration, String image, String filePath) {
        Boolean isValid = true;
        if (name.isEmpty()) {
            utils.showNotification("Error", "Name field can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            isValid = false;
        }

        if (genreId == null) {
            utils.showNotification("Error", "GenreId field can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            isValid = false;
        }

        if (singer.isEmpty()) {
            utils.showNotification("Error", "Singer field can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            isValid = false;
        }

        if (lyrics.isEmpty()) {
            utils.showNotification("Error", "Lyrics field can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            isValid = false;
        }

        if (genre == null) {
            utils.showNotification("Error", "Genre field can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            isValid = false;
        }
        
        if (duration == null) {
            utils.showNotification("Error", "Incorrect duration", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            isValid = false;
        }

        if (image.isEmpty()) {
            utils.showNotification("Error", "Image field can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            isValid = false;
        }

        if (filePath.isEmpty()) {
            utils.showNotification("Error", "filePath field can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            isValid = false;
        }
        return isValid;
    }
}
