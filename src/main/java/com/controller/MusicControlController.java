/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller;

import com.App;

import static com.controller.HomeController.homeController;
import static com.controller.LoginController.authLogin;

import com.dao.AuthDao;
import com.dao.FavouriteTrackDao;
import com.dao.TrackDao;
import com.model.Auth;
import com.model.Records;
import com.model.Track;
import com.utils.Utils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author ngoch
 */
public class MusicControlController implements Initializable {

    public static MusicControlController musicControlController;

    @FXML
    private Circle mucsicImgCd;
    @FXML
    private Label labelSong;
    @FXML
    private Label singersSong;
    @FXML
    private FontAwesomeIconView favaouriteIcon;
    @FXML
    private StackPane favouriteSongIconWrapper;
    @FXML
    private FontAwesomeIconView randomIcon;
    @FXML
    private StackPane randomIconWrapper;
    @FXML
    private FontAwesomeIconView preIcon;
    @FXML
    private StackPane preIconWrapper;
    @FXML
    private FontAwesomeIconView playIcon;
    @FXML
    private StackPane playIconWrapper;
    @FXML
    private FontAwesomeIconView nextIcon;
    @FXML
    private StackPane nextIconWrapper;
    @FXML
    private FontAwesomeIconView repeatIcon;
    @FXML
    private StackPane repeatIconWrapper;
    @FXML
    private ProgressBar songProressControl;
    @FXML
    private ProgressBar volumnSongControl;
    @FXML
    private Label timeCurrentProressSong;
    @FXML
    private Label timeEndProressSong;
    @FXML
    private FontAwesomeIconView lyricsIcon;

    @FXML
    private ChoiceBox accountChoice;

    private MediaPlayer mediaPlayer;

    private Boolean isActiveFauvoriteSongBtn;
    private Boolean isActiveRandomBtn = false;
    private Boolean isActiveRepeatBtn = false;
    private Boolean isPlayingSong = false;
    private Boolean isPlayingSongSample = true;
    private Boolean isChangingSongProgress = false;
    private Boolean isInitialize = true;

    private Track trackSample;

    private TrackDao trackDao = new TrackDao();
    private AuthDao authDao = new AuthDao();

    private RotateTransition rotateTransition;

    private Utils ut = new Utils();

    private String currentPath;

    private static CurrentTrackListController currentTrackListController;

    private BorderPane borderPane;

    private Track currentTrack;

    private FavouriteTrackDao favouriteTrackDao = new FavouriteTrackDao();

    private Scene currentScene;

    private Stage currentStage;

    public static CurrentTrackListController getCurrentTrackListController() {
        return currentTrackListController;
    }

    public static void setCurrentTrackListController(CurrentTrackListController currentTrackListController) {
        MusicControlController.currentTrackListController = currentTrackListController;
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
        Platform.runLater(() -> {
            currentScene = mucsicImgCd.getScene();
            currentStage = (Stage) currentScene.getWindow();
        });

        musicControlController = this;

        rotateTransition = ut.createRotateTransition();
        rotateTransition.setNode(mucsicImgCd);

        currentPath = System.getProperty("user.dir");
        currentPath = currentPath.replace("\\", "/");

        preIconWrapper.setOnMousePressed(event -> {
            preIcon.setFill(Color.RED);
            preIcon.setScaleY(0.8);
            preIcon.setScaleX(0.8);
        });
        preIconWrapper.setOnMouseReleased(event -> {
            preIcon.setFill(Color.WHITE);
            preIcon.setScaleY(1);
            preIcon.setScaleX(1);
            if (isPlayingSongSample) {
                ut.showNotification("Switch page", "Please select the song in the list", Utils.NotificationType.INFORMATION, Duration.seconds(3));
                ut.loadSectionFxml2(borderPane, "/application/views/UI/CurrentTrackList.fxml", "center", null);
                isPlayingSongSample = false;
            } else {
                if (isActiveRandomBtn) {
                    currentTrackListController.handleChangeSongBtn(0);
                } else {
                    currentTrackListController.handleChangeSongBtn(-1);
                }
            }

        });
        nextIconWrapper.setOnMousePressed(event -> {
            nextIcon.setFill(Color.RED);
            nextIcon.setScaleY(0.8);
            nextIcon.setScaleX(0.8);
        });
        nextIconWrapper.setOnMouseReleased(event -> {
            nextIcon.setFill(Color.WHITE);
            nextIcon.setScaleY(1);
            nextIcon.setScaleX(1);
            if (isPlayingSongSample) {
                ut.showNotification("Switch page", "Please select the song in the list", Utils.NotificationType.INFORMATION, Duration.seconds(3));
                ut.loadSectionFxml2(borderPane, "/application/views/UI/CurrentTrackList.fxml", "center", null);
                isPlayingSongSample = false;
            } else {
                if (isActiveRandomBtn) {
                    currentTrackListController.handleChangeSongBtn(0);
                } else {
                    currentTrackListController.handleChangeSongBtn(1);
                }
            }
        });

        favaouriteIcon.setOnMouseClicked(event -> {
            isActiveFauvoriteSongBtn = !isActiveFauvoriteSongBtn;
            handleUpdateFauvoriteSongBtn();
        });
        randomIconWrapper.setOnMouseClicked(event -> {
            isActiveRandomBtn = !isActiveRandomBtn;
            handleUpdateRandomSongBtn();
        });
        repeatIconWrapper.setOnMouseClicked(event -> {
            isActiveRepeatBtn = !isActiveRepeatBtn;
            handleUpdateRepeatSongBtn();
        });
        playIconWrapper.setOnMouseClicked(event -> {
            isPlayingSong = !isPlayingSong;
            handleUpdatePlaySongBtn();
        });

        songProressControl.setOnMousePressed(event -> {
            isChangingSongProgress = true;
            handleUpdateSongProress(event);
        });
        songProressControl.setOnMouseDragged(event -> {
            handleUpdateSongProress(event);
        });
        songProressControl.setOnMouseReleased(event -> {
            changeCurrentDutationAudio();
        });

        volumnSongControl.setOnMousePressed(event -> {
            handleUpdateVolumnProress(event);
        });
        volumnSongControl.setOnMouseDragged(event -> {
            handleUpdateVolumnProress(event);
        });
        volumnSongControl.setOnMouseReleased(event -> {
            changeCurrentVolunmAudio();

        });

        trackSample = trackDao.selectFirstTrack("active");
        ArrayList<Auth> authList = authDao.getAuthByTrackId(trackSample.getId());
        String artistNames = null;
        Boolean isFirst = true;
        for (Auth i : authList) {
            if (isFirst) {
                artistNames = i.getFullname();
                isFirst = false;
            } else {
                artistNames += ", " + i.getFullname();
            }
        }
        currentIndexSong(trackSample, artistNames);

        ObservableList<String> accountOption = null;
        if (authLogin.getRole().equalsIgnoreCase("user")) {
            accountOption = FXCollections.observableArrayList(
                    "Profile",
                    "Payment",
                    "Logout"
            );
        } else {
            accountOption = FXCollections.observableArrayList(
                    "Profile",
                    "Upload music",
                    "My records",
                    "Payment",
                    "Logout"
            );
        }
        accountChoice.setItems(accountOption);
        accountChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("Selected Account: " + newValue);
                if (newValue.equalsIgnoreCase("logout")) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/UI/Login.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        currentStage.setScene(scene);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (newValue.equalsIgnoreCase("Upload music")) {
                    Node centerNode = (Node) homeController.borderPaneRoot.lookup("#homeSection");
                    if (centerNode == null) {
                        homeController.borderPaneRoot.setCenter(homeController.originalHomeSection);
                    }
                    homeController.showUploadMusic();
                } else if (newValue.equalsIgnoreCase("My records")) {
                    ut.loadSectionFxml(borderPane, "/application/views/UI/ArtistRecords.fxml", "center");
                } else if (newValue.equalsIgnoreCase("Profile")) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/UI/profileuser.fxml"));
                        ProfileuserController controller = new ProfileuserController(authLogin);
                        fxmlLoader.setController(controller);
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(MusicControlController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (newValue.equalsIgnoreCase("Payment")) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/UI/Package.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(MusicControlController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    public void currentIndexSong(Track track, String artistNames) {
        currentTrack = track;

        String mediaPath = "src/main/resources/application/static/audio/" + track.getFile_path();
        File file = new File(mediaPath);
        if (file.exists()) {
            if (!isInitialize) {
                if (isPlayingSong) {
                    mediaPlayer.stop();
                    mediaPlayer.seek(Duration.ZERO);
                }
            }

            String selectedFile = file.toURI().toString();
            Media media = new Media(selectedFile);
            mediaPlayer = new MediaPlayer(media);

            volumnSongControl.setProgress(0.5);
            mediaPlayer.setVolume(0.5);

            songProressControl.setProgress(0);

            mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                Duration totalDuration = mediaPlayer.getTotalDuration();
                Duration currentTime = newValue;

                int currentMinutes = (int) currentTime.toMinutes() % 60;
                int currentSeconds = (int) currentTime.toSeconds() % 60;

                String formattedcurrentTime = String.format("%02d:%02d", currentMinutes, currentSeconds);

                timeCurrentProressSong.setText(formattedcurrentTime);

                Duration remainingTime = totalDuration.subtract(currentTime);

                int remainingMinutes = (int) remainingTime.toMinutes() % 60;
                int remainingSeconds = (int) remainingTime.toSeconds() % 60;

                String formattedRemainingTime = String.format("%02d:%02d", remainingMinutes, remainingSeconds);

                timeEndProressSong.setText(formattedRemainingTime);

                double progress = currentTime.toMillis() / totalDuration.toMillis();
                if (!isChangingSongProgress) {
                    songProressControl.setProgress(progress);
                }
            });

            mediaPlayer.setOnEndOfMedia(() -> {
                if (isPlayingSongSample) {
                    ut.showNotification("Switch page", "Please select the song in the list", Utils.NotificationType.INFORMATION, Duration.seconds(3));
                    ut.loadSectionFxml2(borderPane, "/application/views/UI/CurrentTrackList.fxml", "center", null);
                    isPlayingSongSample = false;
                } else {
                    if (isActiveRepeatBtn) {
                        mediaPlayer.seek(Duration.ZERO);
                    } else {
                        if (isActiveRandomBtn) {
                            currentTrackListController.handleChangeSongBtn(0);
                        } else {
                            currentTrackListController.handleChangeSongBtn(1);
                        }
                    }
                }
            });

            if (!isInitialize) {
//                mediaPlayer.play();
                isPlayingSong = true;
                handleUpdatePlaySongBtn();
            } else {
                isInitialize = false;
            }

            lyricsIcon.setOnMouseClicked(event -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/UI/LyricsDetails.fxml"));
                    LyricsDetailsController controller = new LyricsDetailsController(track.getLyrics());
                    fxmlLoader.setController(controller);
                    Parent fxmlParent = fxmlLoader.load();

                    Scene scene = new Scene(fxmlParent);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(MusicControlController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        isActiveFauvoriteSongBtn = favouriteTrackDao.isFavouriteTrack(authLogin.getId(), track.getId());
        if (isActiveFauvoriteSongBtn) {
            favaouriteIcon.setFill(Color.RED);
        } else {
            favaouriteIcon.setFill(Color.WHITE);
        }

        String imagePath = "file:" + currentPath + "/src/main/resources/application/static/images/tracks/" + track.getImage();

        try {
            File fileImage = new File("D:\\Aptech\\Do_an\\Sem2\\MusicApplication\\src\\main\\resources\\application\\static\\images\\tracks\\" + track.getImage());
            if (fileImage.exists()) {
                if (ImageIO.read(fileImage) != null) {
                    System.out.println(imagePath);
                    Image image = new Image(imagePath);
                    ImagePattern imagePattern = new ImagePattern(image);
                    mucsicImgCd.setFill(imagePattern);
                } else {
                    System.out.println("Tep tin khong phai anh hop le");
                }
            } else {
                System.out.println("Tep tin khong ton tai.");
            }
        } catch (IOException e) {
            System.out.println("Da xay ra loi khi doc tep tin.");
            e.printStackTrace();
        }
        labelSong.setText(track.getName());
        singersSong.setText(track.getSingers());
    }

    public void currentIndexRecord(Records record) {
        String mediaPath = "src/main/resources/application/records/" + record.getPath();
        File file = new File(mediaPath);
        System.out.println("src/main/resources/application/records/" + record.getPath());
        if (file.exists()) {
            if (!isInitialize) {
                if (isPlayingSong) {
                    mediaPlayer.stop();
                    mediaPlayer.seek(Duration.ZERO);
                }
            }

            System.out.println("Chay vao");

            String selectedFile = file.toURI().toString();
            Media media = new Media(selectedFile);
            mediaPlayer = new MediaPlayer(media);

            volumnSongControl.setProgress(0.5);
            mediaPlayer.setVolume(0.5);

            songProressControl.setProgress(0);

            mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                Duration totalDuration = mediaPlayer.getTotalDuration();
                Duration currentTime = newValue;

                int currentMinutes = (int) currentTime.toMinutes() % 60;
                int currentSeconds = (int) currentTime.toSeconds() % 60;

                String formattedcurrentTime = String.format("%02d:%02d", currentMinutes, currentSeconds);

                timeCurrentProressSong.setText(formattedcurrentTime);

                Duration remainingTime = totalDuration.subtract(currentTime);

                int remainingMinutes = (int) remainingTime.toMinutes() % 60;
                int remainingSeconds = (int) remainingTime.toSeconds() % 60;

                String formattedRemainingTime = String.format("%02d:%02d", remainingMinutes, remainingSeconds);

                timeEndProressSong.setText(formattedRemainingTime);

                double progress = currentTime.toMillis() / totalDuration.toMillis();
                if (!isChangingSongProgress) {
                    songProressControl.setProgress(progress);
                }
            });

            mediaPlayer.setOnEndOfMedia(() -> {
                if (isPlayingSongSample) {
                    ut.showNotification("Switch page", "Please select the song in the list", Utils.NotificationType.INFORMATION, Duration.seconds(3));
                    ut.loadSectionFxml2(borderPane, "/application/views/UI/CurrentTrackList.fxml", "center", null);
                    isPlayingSongSample = false;
                } else {
                    if (isActiveRepeatBtn) {
                        mediaPlayer.seek(Duration.ZERO);
                    } else {
                        if (isActiveRandomBtn) {
                            currentTrackListController.handleChangeSongBtn(0);
                        } else {
                            currentTrackListController.handleChangeSongBtn(1);
                        }
                    }
                }
            });

            mediaPlayer.play();
            System.out.println("ghi am bat len");

//            if (!isInitialize) {
//                isPlayingSong = true;
//                handleUpdatePlaySongBtn();
//            } else {
//                isInitialize = false;
//            }
            lyricsIcon.setVisible(false);
        }
        favaouriteIcon.setVisible(false);
        labelSong.setText(record.getName());
        singersSong.setText(record.getPath());
    }
    //  Handle

    private void handleUpdateFauvoriteSongBtn() {
        if (isActiveFauvoriteSongBtn) {
            favaouriteIcon.setFill(Color.RED);
            favouriteTrackDao.add(authLogin.getId(), currentTrack.getId());
            ut.showNotification("SUCCESS", "Added song to favorites list successfully", Utils.NotificationType.INFORMATION, Duration.seconds(3));
        } else {
            favaouriteIcon.setFill(Color.WHITE);
            favouriteTrackDao.del(authLogin.getId(), currentTrack.getId());
            ut.showNotification("SUCCESS", "Successfully deleted the song from the favorites list", Utils.NotificationType.INFORMATION, Duration.seconds(3));
        }
    }

    private void handleUpdateRandomSongBtn() {
        if (isActiveRandomBtn) {
            randomIcon.setFill(Color.RED);
        } else {
            randomIcon.setFill(Color.WHITE);
        }
    }

    private void handleUpdateRepeatSongBtn() {
        if (isActiveRepeatBtn) {
            repeatIcon.setFill(Color.RED);
        } else {
            repeatIcon.setFill(Color.WHITE);
        }
    }

    private void handleUpdatePlaySongBtn() {
        if (isPlayingSong) {
            playIcon.setGlyphName("PAUSE");
            rotateTransition.play();
            mediaPlayer.play();
        } else {
            playIcon.setGlyphName("PLAY");
            rotateTransition.pause();
            mediaPlayer.pause();
        }
    }

    private void handleUpdateSongProress(MouseEvent event) {
        double progress = 0;
        double currentX = event.getX();
        double totalWidth = songProressControl.getWidth();

        if (currentX < 0) {
            progress = 0;
        } else if (currentX > totalWidth) {
            progress = 1;
        } else {
            progress = currentX / totalWidth;
        }

        songProressControl.setProgress(progress);
    }

    private void handleUpdateVolumnProress(MouseEvent event) {
        double progress = 0;
        double currentX = event.getX();
        double totalWidth = volumnSongControl.getWidth();

        if (currentX < 0) {
            progress = 0;
        } else if (currentX > totalWidth) {
            progress = 1;
        } else {
            progress = currentX / totalWidth;
        }

//        System.out.println(progress);
        volumnSongControl.setProgress(progress);
    }

    private void changeCurrentDutationAudio() {
        double progress = songProressControl.getProgress();
        double totalDuration = mediaPlayer.getTotalDuration().toMillis();
        double currentTime = progress * totalDuration;
        mediaPlayer.seek(Duration.millis(currentTime));
        isChangingSongProgress = false;
    }

    private void changeCurrentVolunmAudio() {
        double progress = volumnSongControl.getProgress();
        mediaPlayer.setVolume(progress);
    }

//    public void showStageLyrics() {
//        System.out.println("here");
//    }
}
