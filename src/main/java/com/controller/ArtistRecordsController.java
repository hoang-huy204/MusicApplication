package com.controller;

import static com.controller.LoginController.authLogin;
import static com.controller.MusicControlController.musicControlController;
import com.dao.RecordsDao;
import com.model.Records;
import com.utils.AudioRecorder;
import com.utils.Utils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Desktop;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class ArtistRecordsController implements Initializable {

    @FXML
    private LineChart<Number, Number> waveformChart;

    @FXML
    private AnchorPane recordingActionoBtn;

    @FXML
    private FontAwesomeIconView recordingIcon;

    @FXML
    private TextField recordingNameTF;

    @FXML
    private Circle recordingStatus;

    @FXML
    private VBox currentReccordList;

    private AudioRecorder audioRecorder;

    private Boolean isRecords = false;

    private Boolean isPlaying = false;

    private Utils ut = new Utils();

    private String outputPath;

    private ArrayList<Records> recordList;

    private RecordsDao recordsDao = new RecordsDao();

    private String currentPath;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentPath = System.getProperty("user.dir");
        currentPath = currentPath.replace("\\", "/");

        Timeline recordingStatusTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    if (recordingStatus.getFill() == Color.RED) {
                        recordingStatus.setFill(Color.WHITE);
                    } else {
                        recordingStatus.setFill(Color.RED);
                    }
                })
        );
        recordingStatusTimeline.setCycleCount(Timeline.INDEFINITE);

        printRecords();

        recordingActionoBtn.setOnMousePressed(event -> {
            String recordingName = recordingNameTF.getText();
            if (!recordingNameTF.isVisible() && !isRecords) {
                recordingNameTF.setVisible(true);
            } else if (!isRecords) {
                if (recordingName.isEmpty()) {
                    ut.showNotification("Error", "Please enter the recording name", Utils.NotificationType.ERROR, Duration.seconds(3));
                } else if (recordingName.length() > 20) {
                    ut.showNotification("Error", "Please enter the recording name in no more than 20 characters", Utils.NotificationType.ERROR, Duration.seconds(3));
                } else {
                    for (Records i : recordList) {
                        if (i.getName().equalsIgnoreCase(recordingName)) {
                            ut.showNotification("Error", "Recording name already exists", Utils.NotificationType.ERROR, Duration.seconds(3));
                            return;
                        }
                    }
                    recordingNameTF.setVisible(false);
//                    System.out.println("hey status");
                    recordingStatusTimeline.play();
                    recordingIcon.setGlyphName("SQUARE");
                    record(recordingName);
                    return;
                }
            }
            if (isRecords) {
                recordingStatusTimeline.pause();
                System.out.println("Luu file trong nay");
                recordingIcon.setGlyphName("PLAY");
                isRecords = false;
                audioRecorder.stopRecording();
                Records record = new Records(null, recordingName, authLogin.getId() + "/" + recordingName + ".mp3", authLogin.getId(), null);
                recordsDao.add(record);
                printRecords();
            }
        });
    }

    public void record(String fileName) {
        outputPath = "src/main/resources/application/records/" + authLogin.getId() + "/" + fileName + ".mp3";
        File outputFile = new File(outputPath);

        File parentDir = outputFile.getParentFile();
        if (!parentDir.exists()) {
            // Tạo thư mục cha nếu không tồn tại
            boolean created = parentDir.mkdirs();
            if (!created) {
                System.out.println("Không thể tạo thư mục cha");
                return;
            }
        }

        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
                System.out.println("Created new output file: " + outputPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (outputFile.exists()) {
            System.out.println("Vao file");
            audioRecorder = new AudioRecorder(outputFile);
            isRecords = true;
//            isPlaying = true;
            recordingIcon.setGlyphName("SQUARE");
            System.out.println("Khoi dong");
            audioRecorder = new AudioRecorder(outputFile);
            audioRecorder.startRecording();
        }
    }

    public void printRecords() {
        currentReccordList.getChildren().clear();
        recordList = recordsDao.selectAllRecordByAuthId(authLogin.getId());
        Integer count = 1;
        for (Records i : recordList) {
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefWidth(810.0);
            anchorPane.setPrefHeight(42.0);
            anchorPane.setOnMousePressed(event -> {
                System.out.println("AnchorPane");
                File file = new File("src/main/resources/application/records/" + i.getPath());
                if (file.exists()) {
                    try {
                        Desktop.getDesktop().open(file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
//                musicControlController.currentIndexRecord(i);
            });

            Label label1 = new Label(count.toString());
            label1.setLayoutX(11.0);
            label1.setLayoutY(13.0);
            label1.setFont(new Font(14.0));

            Label label2 = new Label(i.getName());
            label2.setLayoutX(55.0);
            label2.setLayoutY(13.0);
            label2.setPrefWidth(237.0);
            label2.setPrefHeight(20.0);
            label2.setFont(new Font(14.0));

            Label label3 = new Label(currentPath + "/src/main/resources/application/records/" + i.getPath());
            label3.setLayoutX(315.0);
            label3.setLayoutY(13.0);
            label3.setPrefWidth(331.0);
            label3.setPrefHeight(20.0);
            label3.setFont(new Font(14.0));
            label3.setWrapText(true);

            Button button = new Button("Delete");
            button.setLayoutX(679.0);
            button.setLayoutY(7.0);
            button.setPrefWidth(66.0);
            button.setPrefHeight(27.0);
            button.setStyle("-fx-background-color: red;");
            button.setTextFill(javafx.scene.paint.Color.WHITE);
            button.setFont(Font.font("System Bold", 13.0));
            button.setOnAction(event -> {
                System.out.println("btn");
                recordsDao.delete(i);
                File file = new File("src/main/resources/application/records/" + i.getPath());
                if (file.exists()) {
                    if (file.delete()) {
                        System.out.println("File deleted successfully.");
                    } else {
                        System.out.println("Failed to delete the file.");
                    }
                } else {
                    System.out.println("File does not exist.  " + "src/main/resources/application/records/" + i.getPath());
                }
                printRecords();
            });

            anchorPane.getChildren().addAll(label1, label2, label3, button);
            currentReccordList.getChildren().add(anchorPane);
            count++;
        }
    }
}
