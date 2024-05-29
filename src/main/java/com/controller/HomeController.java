package com.controller;

import com.App;

import static com.controller.CurrentTrackListController.currentTrackListController;
import static com.controller.LoginController.authLogin;
import static com.controller.LoginController.isAuthVip;
import static com.controller.MusicControlController.musicControlController;

import com.dao.*;
import com.model.Auth;
import com.model.GenreTrack;
import com.model.Playlist;
import com.model.Track;
import com.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;

import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HomeController implements Initializable {
    public static HomeController homeController;
    private TrackDao trackDao = new TrackDao();
    private PlayListDao playListDao = new PlayListDao();
    private AuthDao authDao = new AuthDao();
    private Favorites_PlaylistDao favoritesPlaylistDao = new Favorites_PlaylistDao();
    private GenreTrackDao genreTrackDao = new GenreTrackDao();
    private String selected_Image_Paths;
    private String file_name_image_Upload;
    private Integer genreTrack_Id;
    private TrackPlaylistDao trackPlaylistDao = new TrackPlaylistDao();

    private Utils utils = new Utils();

    public void setGenreTrack_Id(Integer genreTrack_Id) {
        this.genreTrack_Id = genreTrack_Id;
    }

    public void setFile_name_image_Upload(String file_name_image_Upload) {
        this.file_name_image_Upload = file_name_image_Upload;
    }

    public void setSelected_Image_Paths(String selected_Image_Paths) {
        this.selected_Image_Paths = selected_Image_Paths;
    }

    @FXML
    private Button home_btn;

    @FXML
    private Button current_playlist_btn;

    @FXML
    private Button search_btn;

    @FXML
    private Button favourite_btn;

    @FXML
    private Pane image_user_wrap;
    @FXML
    private AnchorPane showMain_container;

    @FXML
    private HBox track_container;

    @FXML
    private Button show_all_tracks_btn;

    @FXML
    private HBox playlist_container;

    @FXML
    private Button show_all_playlist_btn;

    @FXML
    private HBox artists_container;

    @FXML
    private Button show_all_artists_btn;

    //    @FXML
//    private ScrollPane second_bar;
    @FXML
    private AnchorPane show_all_container;

    @FXML
    private ScrollPane scroll_container_detail;

    @FXML
    private FlowPane show_detail_container;

    @FXML
    private FlowPane favourites_playlist_container;

    @FXML
    private AnchorPane upload_4_artist_container;

    @FXML
    private AnchorPane upload_container;

    @FXML
    private Button btn_upload;

    //track
    @FXML
    private AnchorPane upload_track_container;

    @FXML
    private ImageView image_content;

    @FXML
    private Button btn_upload_image_track;

    @FXML
    private Pane upload_image_content_wrap;

    @FXML
    private Button btn_replace_image_track;

    @FXML
    private TextField track_name_input;

    @FXML
    private ComboBox<GenreTrack> category_track_input;

    @FXML
    private TextField track_singers_input;

    @FXML
    private TextArea track_lyrics_input;

    @FXML
    private Button btn_save_upload_track;

    @FXML
    private Button btn_cancel_upload_track;

    //album
    @FXML
    private AnchorPane upload_playlist_container;

    @FXML
    private Pane upload_image_playlist_wrap;

    @FXML
    private Button btn_upload_image_album;

    @FXML
    private Button btn_replace_image_playlist;

    @FXML
    private ImageView image_content_playlist;

    @FXML
    private Button btn_save_upload_playlist;

    @FXML
    private Button btn_cancel_upload_playlist;

    @FXML
    private TextField name_playlist_input;

    @FXML
    private TextArea description_playlist_input;

    @FXML
    private ScrollPane scroll_pane_tracks_in_playlist;

    @FXML
    private FlowPane tracks_in_playlist_wrap;

    @FXML
    private Button btn_addTrack_upload_playlist;


    @FXML
    public BorderPane borderPaneRoot;

    public static BorderPane originalBorderPaneRoot;

    @FXML
    private AnchorPane homeSection;

    public Node originalHomeSection;

    Utils ut = new Utils();

    private Scene currentScene;

    private MouseEvent mouseEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false, false, false, false, true, false, false, true, false, false, null);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeController = this;

        Platform.runLater(() -> {
            currentScene = borderPaneRoot.getScene();
            ut.setPosWindow(currentScene);
        });

        if (authLogin.getRole().equalsIgnoreCase("user")) {
            upload_4_artist_container.setVisible(false);
            showMain_container.setVisible(true);
        }

        originalBorderPaneRoot = borderPaneRoot;
        originalHomeSection = borderPaneRoot.getCenter();

        ut.loadSectionFxml(borderPaneRoot, "/application/views/UI/MusicControl.fxml", "bottom");

        show_image_user();

        home_btn.setOnAction(event -> {
            Node centerNode = (Node) borderPaneRoot.lookup("#homeSection");
            if (centerNode == null) {
                borderPaneRoot.setCenter(originalHomeSection);
            } else {
                if (show_all_container.isVisible()) {
                    showMain_container.setVisible(true);
                    show_all_container.setVisible(false);
                    show_detail_container.getChildren().clear();
                }

                if (upload_4_artist_container.isVisible()) {
                    upload_4_artist_container.setVisible(false);
                    showMain_container.setVisible(true);
                }
            }
        });

        handle_cancelUpload();

        current_playlist_btn.setOnAction(event -> {
            Node centerNode = (Node) borderPaneRoot.lookup("#currentPlaylistSection");
            if (centerNode == null) {
                ut.loadSectionFxml2(borderPaneRoot, "/application/views/UI/CurrentTrackList.fxml", "center", null);
            } else {
                currentTrackListController.printTracks(0);
            }
        });

        search_btn.setOnAction(event -> {
            Node centerNode = (Node) borderPaneRoot.lookup("#searchSection");
            if (centerNode == null) {
                ut.loadSectionFxml(borderPaneRoot, "/application/views/UI/Search.fxml", "center");
            }
        });

        favourite_btn.setOnAction(event -> {
            Node centerNode = (Node) borderPaneRoot.lookup("#currentPlaylistSection");
            if (centerNode == null) {
                ut.showNotification("Warning!", "Please select to playlist first", Utils.NotificationType.WARNING, javafx.util.Duration.seconds(3));
            } else {
                currentTrackListController.printTracks(authLogin.getId());
            }
        });

        showFavouritesPlaylist();

        if (show_all_tracks_btn != null && show_all_artists_btn != null) {
            showTrack();
            show_all_tracks_btn.setOnAction(event -> {
                showMain_container.setVisible(false);
                show_all_container.setVisible(true);
                showDetail_Tracks();
            });

            showPlaylist();
            show_all_playlist_btn.setOnAction(event -> {
                showMain_container.setVisible(false);
                show_all_container.setVisible(true);
                showDetail_PLayList();
            });

            showArtist();
            show_all_artists_btn.setOnAction(event -> {
                showMain_container.setVisible(false);
                show_all_container.setVisible(true);
                showDetail_Artists();
            });
        }
        scroll_container_detail.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll_container_detail.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        if (upload_4_artist_container.isVisible()) {
            default_ValueGenreTrack();
            Upload4_Artist();
        }
    }

    public void show_image_user() {
        image_user_wrap.getChildren().add(handleImage("users", "user_1.jpg", 360, 52.0, 54.0, 15.0, 3.0));
    }

    private void showFavouritesPlaylist() {
        if (favoritesPlaylistDao.selectAllFavorites_PlaylistByAuthId(5, "active") != null && !favoritesPlaylistDao.selectAllFavorites_PlaylistByAuthId(5, "active").isEmpty()) {
            for (Playlist playlist : favoritesPlaylistDao.selectAllFavorites_PlaylistByAuthId(5, "active")) {
                createContainerFavouritesPlaylist(playlist.getImage(), playlist.getId());
            }
        }
    }

    private void showDetail_Tracks() {
        ArrayList<Track> trackList = new ArrayList<>();
        if (isAuthVip) {
            trackList = trackDao.selectAllTrack("active");
        } else {
            trackList = trackDao.selectAllTrackNoVip();
        }
        if (trackList != null && !trackList.isEmpty()) {
            for (Track track : trackList) {
                createContainerDetails(show_detail_container, handleImage("tracks", track.getImage(), 20, null, null, null, null), createLabel_Name(track.getName()), createLabel_ArtistsName(track.getSingers()), track.getId(), false, false);
            }
        }
    }

    private void showDetail_PLayList() {
        ArrayList<Playlist> playlists_List = new ArrayList<>();
        if (isAuthVip) {
            playlists_List = playListDao.selectPlaylist("active", 1);
        } else {
            playlists_List = playListDao.selectPlaylist("active", 0);
        }
        if (playlists_List != null && !playlists_List.isEmpty()) {
            for (Playlist playlist : playlists_List) {
                createContainerDetails(show_detail_container, handleImage("playlist", playlist.getImage(), 20, null, null, null, null), createLabel_Name(playlist.getName()), createLabel_ArtistsName(playlist.getUserName()), playlist.getId(), false, true);
            }
        }
    }

    private void showDetail_Artists() {
        if (authDao.selectAllAuthByRole("artist", "active") != null && !authDao.selectAllAuthByRole("artist", "active").isEmpty()) {
            for (Auth auth : authDao.selectAllAuthByRole("artist", "active")) {
                createContainerDetails2(show_detail_container, handleImage("artists", auth.getImage(), 360, null, null, null, null), createLabel_Name(auth.getFullname()), createLabel_ArtistsName("artist"), auth.getId(), true, auth);
            }
        }
    }

    private void showTrack() {
        ArrayList<Track> trackList = new ArrayList<>();
        if (isAuthVip) {
            trackList = trackDao.selectTrack("active", 1);
        } else {
            trackList = trackDao.selectTrack("active", 0);
        }
        if (trackList != null) {
            if (trackList != null && !trackList.isEmpty()) {
                for (Track track : trackList) {
                    createContainer(track_container, handleImage("tracks", track.getImage(), 20, null, null, null, null), createLabel_Name(track.getName()), createLabel_ArtistsName(track.getSingers()), track.getId(), false);
                }
            }
        }
    }

    private void showPlaylist() {
        ArrayList<Playlist> playlists_List = new ArrayList<>();
        if (isAuthVip) {
            playlists_List = playListDao.selectPlaylist("active", 1);
        } else {
            playlists_List = playListDao.selectPlaylist("active", 0);
        }
        if (playlists_List != null) {
            if (playlists_List != null && !playlists_List.isEmpty()) {
                for (Playlist playlist : playlists_List) {
                    createContainer(playlist_container, handleImage("playlist", playlist.getImage(), 20, null, null, null, null), createLabel_Name(playlist.getName()), createLabel_ArtistsName(playlist.getUserName()), playlist.getId(), false);
                }
            }
        }
    }

    private void showArtist() {
        ArrayList<Auth> auth_List = new ArrayList<>();
        if (authDao.selectAuthByRole("artist", "active") != null) {
            auth_List = authDao.selectAuthByRole("artist", "active");
            if (auth_List != null && !auth_List.isEmpty()) {
                for (Auth auth : auth_List) {
                    createContainer2(artists_container, handleImage("artists", auth.getImage(), 360, null, null, null, null), createLabel_Name(auth.getFullname()), createLabel_ArtistsName("artist"), auth.getId(), true, auth);
                }
            }
        }
    }

    private void setBorderRadius(ImageView imageView, double radius) {
        Rectangle clip = new Rectangle();

        double width = imageView.getBoundsInLocal().getWidth();
        double height = imageView.getBoundsInLocal().getHeight();

        clip.setWidth(width);
        clip.setHeight(height);

        clip.setArcWidth(radius);
        clip.setArcHeight(radius);

        imageView.setClip(clip);
    }

    private ImageView handleImage(String path, String _image, Integer duration, Double H, Double W, Double X, Double Y) {
        try {
            if (path != null && _image != null) {
                ImageView imageView = new ImageView();
                if (H != null && W != null && X != null && Y != null) {
                    imageView.setFitHeight(H);
                    imageView.setFitWidth(W);
                    imageView.setLayoutX(X);
                    imageView.setLayoutY(Y);
                } else {
                    imageView.setFitHeight(94.0);
                    imageView.setFitWidth(101.0);
                    imageView.setLayoutX(14.0);
                    imageView.setLayoutY(14.0);
                }

                imageView.setPreserveRatio(true);
                String currentPath = System.getProperty("user.dir");
                String targetPath = currentPath + "\\src\\main\\resources\\application\\static\\images\\" + path + "\\" + _image;
                if (_image.contains(" ")) {
                    String encodedPath = URLEncoder.encode(targetPath, StandardCharsets.UTF_8.toString());
                    File file = new File("file:///" + encodedPath.replace("+", "%20"));
                    Image image = new Image(file.toURI().toString());
                    imageView.setImage(image);
                } else {
                    File file = new File(targetPath);
                    Image image = new Image(file.toURI().toString());
                    imageView.setImage(image);
                }
                setBorderRadius(imageView, duration);
                return imageView;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Label createLabel_Name(String name) {
        if (name != null) {
            Label label_Name = new Label(name);
            label_Name.setLayoutX(13.0);
            label_Name.setLayoutY(112.0);
            label_Name.setPrefHeight(21.0);
            label_Name.setPrefWidth(97.0);
            label_Name.setTextFill(javafx.scene.paint.Color.WHITE);
            label_Name.setFont(new Font("Century Gothic Bold", 12.0));
            return label_Name;
        } else {
            return null;
        }
    }

    private Label createLabel_ArtistsName(String artistName) {
        if (artistName != null) {
            Label label_TrackArtists = new Label(artistName);
            label_TrackArtists.setLayoutX(14.0);
            label_TrackArtists.setLayoutY(133.0);
            label_TrackArtists.setPrefHeight(17.0);
            label_TrackArtists.setPrefWidth(101.0);
            label_TrackArtists.setTextFill(javafx.scene.paint.Color.web("#9a9a9a"));
            label_TrackArtists.setFont(new Font("Calibri Bold", 12.0));
            return label_TrackArtists;
        } else {
            return null;
        }
    }

    private void createContainer(HBox container, ImageView imageView, Label label_Name, Label label_Artist, Integer id_Item, boolean isArtist) {
        if (container != null && label_Name != null && label_Artist != null) {
            AnchorPane anchorPane_1 = new AnchorPane();
            anchorPane_1.setPrefHeight(155.0);
            anchorPane_1.setPrefWidth(118.0);

            AnchorPane anchorPane_2 = new AnchorPane();
            anchorPane_2.setPrefHeight(155.0);
            anchorPane_2.setPrefWidth(121.0);
            anchorPane_2.setLayoutX(1.0);

            anchorPane_2.setStyle("-fx-background-color: #272626;");
            anchorPane_2.getStyleClass().add("border_wrap");

            Button button_start = new Button("start");
            button_start.getStyleClass().add("button");
            button_start.setVisible(false);
            button_start.setCursor(Cursor.HAND);
            button_start.setPrefHeight(40.0);
            button_start.setPrefWidth(40.0);
            button_start.setLayoutX(60.0);
            button_start.setLayoutY(65.0);

            button_start.setOnAction(event -> {
                System.out.println("start" + id_Item);
                if (container == track_container) {
                    Track track = trackDao.selectTrackById(id_Item);
                    musicControlController.currentIndexSong(track, label_Artist.getText());
                } else {
                    ut.loadSectionFxml2(borderPaneRoot, "/application/views/UI/CurrentTrackList.fxml", "center", id_Item);
                }
            });

            button_start.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            button_start.setGraphic(handleImage("Utils", "play-button.png", 360, 30.0, 30.0, 30.0, 40.0));

            if (isArtist == false) {
                anchorPane_2.getChildren().addAll(imageView, label_Name, label_Artist, button_start);
            } else {
                anchorPane_2.getChildren().addAll(imageView, label_Name, label_Artist);
            }

            anchorPane_1.getChildren().addAll(anchorPane_2);

            Button button = new Button("");
            button.setPrefHeight(100);
            button.setPrefWidth(100);

            button.getStyleClass().add("button");
            button.setCursor(Cursor.HAND);

            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            button.setGraphic(anchorPane_1);

            button.setOnMouseEntered(event -> {
                button_start.setVisible(true);
            });

            button.setOnMouseExited(event -> {
                button_start.setVisible(false);
            });

            button.setId(String.valueOf(id_Item));
            button.setOnAction(event -> System.out.println(id_Item));

            container.getChildren().add(button);
        }
    }

    private void createContainer2(HBox container, ImageView imageView, Label label_Name, Label label_Artist, Integer id_Item, boolean isArtist, Auth artist) {
        if (container != null && label_Name != null && label_Artist != null) {
            AnchorPane anchorPane_1 = new AnchorPane();
            anchorPane_1.setPrefHeight(155.0);
            anchorPane_1.setPrefWidth(118.0);

            AnchorPane anchorPane_2 = new AnchorPane();
            anchorPane_2.setPrefHeight(155.0);
            anchorPane_2.setPrefWidth(121.0);
            anchorPane_2.setLayoutX(1.0);

            anchorPane_2.setStyle("-fx-background-color: #272626;");
            anchorPane_2.getStyleClass().add("border_wrap");

            Button button_start = new Button("start");
            button_start.getStyleClass().add("button");
            button_start.setVisible(false);
            button_start.setCursor(Cursor.HAND);
            button_start.setPrefHeight(40.0);
            button_start.setPrefWidth(40.0);
            button_start.setLayoutX(60.0);
            button_start.setLayoutY(65.0);

            if (imageView != null) {
                anchorPane_2.getChildren().addAll(imageView);
            }

            anchorPane_2.getChildren().addAll(label_Name, label_Artist);

            anchorPane_1.getChildren().addAll(anchorPane_2);

            Button button = new Button("");
            button.setPrefHeight(100);
            button.setPrefWidth(100);

            button.getStyleClass().add("button");
            button.setCursor(Cursor.HAND);

            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            button.setGraphic(anchorPane_1);

            button.setOnMouseEntered(event -> {
                button_start.setVisible(true);
            });

            button.setOnMouseExited(event -> {
                button_start.setVisible(false);
            });

            button.setId(String.valueOf(id_Item));
            button.setOnAction(event -> {
                if (isArtist) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/UI/ArtistProfile.fxml"));
                        ArtistProfileController controller = new ArtistProfileController(artist);
                        fxmlLoader.setController(controller);
                        Parent fxml = fxmlLoader.load();

                        Scene scene = new Scene(fxml);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            container.getChildren().add(button);
        }
    }

    private void createContainerDetails(FlowPane container, ImageView imageView, Label label_Name, Label label_Artist, Integer item_id, boolean isArtist, boolean isPlaylist) {
        AnchorPane anchorPane_1 = new AnchorPane();
        anchorPane_1.setPrefHeight(155.0);
        anchorPane_1.setPrefWidth(118.0);

        AnchorPane anchorPane_2 = new AnchorPane();
        anchorPane_2.setPrefHeight(155.0);
        anchorPane_2.setPrefWidth(121.0);
        anchorPane_2.setLayoutX(1.0);

        anchorPane_2.setStyle("-fx-background-color: #272626;");
        anchorPane_2.getStyleClass().add("border_wrap");

        Button button_start = new Button("start");
        button_start.getStyleClass().add("button");
        button_start.setVisible(false);
        button_start.setCursor(Cursor.HAND);
        button_start.setPrefHeight(40.0);
        button_start.setPrefWidth(40.0);
        button_start.setLayoutX(60.0);
        button_start.setLayoutY(65.0);

        button_start.setOnAction(event -> {
            if (isPlaylist) {
                ut.loadSectionFxml2(borderPaneRoot, "/application/views/UI/CurrentTrackList.fxml", "center", item_id);
            } else {
                Track track = trackDao.selectTrackById(item_id);
                musicControlController.currentIndexSong(track, label_Artist.getText());
            }
        });

        button_start.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        button_start.setGraphic(handleImage("Utils", "play-button.png", 360, 40.0, 40.0, 100.0, 95.0));

        if (isArtist == false) {
            anchorPane_2.getChildren().addAll(imageView, label_Name, label_Artist, button_start);
        } else {
            anchorPane_2.getChildren().addAll(imageView, label_Name, label_Artist);
        }

        anchorPane_1.getChildren().addAll(anchorPane_2);

        Button button = new Button("");
        button.setPrefHeight(100.0);
        button.setPrefWidth(100.0);

        button.getStyleClass().add("button");
        button.setCursor(Cursor.HAND);

        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        button.setGraphic(anchorPane_1);

        button.setOnMouseEntered(event -> {
            button_start.setVisible(true);
        });

        button.setOnMouseExited(event -> {
            button_start.setVisible(false);
        });

        button.setId(String.valueOf(item_id));
        button.setOnAction(event -> {
            System.out.println(item_id);
        });

        container.getChildren().add(button);
    }

    private void createContainerDetails2(FlowPane container, ImageView imageView, Label label_Name, Label label_Artist, Integer item_id, boolean isArtist, Auth artist) {
        AnchorPane anchorPane_1 = new AnchorPane();
        anchorPane_1.setPrefHeight(155.0);
        anchorPane_1.setPrefWidth(118.0);

        AnchorPane anchorPane_2 = new AnchorPane();
        anchorPane_2.setPrefHeight(155.0);
        anchorPane_2.setPrefWidth(121.0);
        anchorPane_2.setLayoutX(1.0);

        anchorPane_2.setStyle("-fx-background-color: #272626;");
        anchorPane_2.getStyleClass().add("border_wrap");
        anchorPane_2.getChildren().addAll(imageView, label_Name, label_Artist);

        anchorPane_1.getChildren().addAll(anchorPane_2);

        Button button = new Button("");
        button.setPrefHeight(100.0);
        button.setPrefWidth(100.0);

        button.getStyleClass().add("button");
        button.setCursor(Cursor.HAND);

        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        button.setGraphic(anchorPane_1);

        button.setId(String.valueOf(item_id));
        button.setOnAction(event -> {
            System.out.println(item_id);
            if (isArtist) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/UI/ArtistProfile.fxml"));
                    ArtistProfileController controller = new ArtistProfileController(artist);
                    fxmlLoader.setController(controller);
                    Parent fxml = fxmlLoader.load();

                    Scene scene = new Scene(fxml);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        container.getChildren().add(button);
    }

    private void createContainerFavouritesPlaylist(String image, Integer item_Id) {
        Pane pane = new Pane();
        pane.setPrefHeight(54.0);
        pane.setPrefWidth(61.0);
        pane.setLayoutX(10.0);

        ImageView imageView = handleImage("playlist", image, 360, 46.0, 40.0, 10.0, 10.0);

        Button button = new Button("");
        button.setPrefHeight(47.0);
        button.setPrefWidth(61.0);
        button.setLayoutY(3.0);

        button.getStyleClass().add("button");
        button.setCursor(Cursor.HAND);

        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        button.setGraphic(imageView);
        button.setId(String.valueOf(item_Id));
        button.setOnAction(event -> System.out.println(item_Id));

        pane.getChildren().addAll(button);

        favourites_playlist_container.getChildren().add(pane);
    }

    public void Upload4_Artist() {
        drag_file();
        selected_Files();
    }

    //select file
    public void drag_file() {
        Set<String> allowExtension = Set.of(".mp3");
        upload_container.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            } else {
                event.consume();
            }
        });

        upload_container.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasFiles() && extensionFilters(db.getFiles(), allowExtension)) {
                success = true;
                if (!db.getFiles().isEmpty()) {
                    upload(db.getFiles());
                }

            } else {
                utils.showNotification("Warning!", "Invalid Extension", Utils.NotificationType.WARNING, javafx.util.Duration.seconds(3));
            }

            event.setDropCompleted(success);
            event.consume();
        });
    }

    private void selected_Files() {
        if (btn_upload.isVisible()) {
            btn_upload.setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3")
                );
                List<File> selected_Files = fileChooser.showOpenMultipleDialog(btn_upload.getScene().getWindow());
                if (selected_Files != null && !selected_Files.isEmpty()) {
                    upload(selected_Files);
                }
            });
        }
    }

    private boolean extensionFilters(List<File> files, Set<String> allowExtension) {
        if (!files.isEmpty() && !allowExtension.isEmpty()) {
            for (File file : files) {
                String fileName = file.getName().trim().toLowerCase();
                boolean isValid = allowExtension.stream().anyMatch(fileName::endsWith);
                if (isValid) {
                    return true;
                }
            }
        }
        return false;
    }


    public void show_tracks_in_playlist(String file_path, String track_name, Map<String, Track> value_Tracks) {
        scroll_pane_tracks_in_playlist.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(777.0);
        anchorPane.setPrefHeight(104.0);

        TextField track_name_input = new TextField();
        track_name_input.setPromptText("Track_Name");
        track_name_input.setText(track_name);
        track_name_input.setLayoutX(7.0);
        track_name_input.setLayoutY(11.0);
        track_name_input.setPrefHeight(37.0);
        track_name_input.setPrefWidth(313.0);

        track_name_input.textProperty().addListener((observable, oldValue, newValue) -> {
            Track currentTrack = value_Tracks.get(file_path);
            currentTrack.setName(newValue);
            value_Tracks.put(file_path, currentTrack);
        });

        TextField singers_input = new TextField();
        singers_input.setPromptText("Singers");
        singers_input.setLayoutX(7.0);
        singers_input.setLayoutY(61.0);
        singers_input.setPrefHeight(36.0);
        singers_input.setPrefWidth(315.0);

        singers_input.textProperty().addListener((observable, oldValue, newValue) -> {
            Track currentTrack = value_Tracks.get(file_path);
            currentTrack.setSingers(newValue);
            value_Tracks.put(file_path, currentTrack);

        });

        Line line = new Line();
        line.setLayoutX(171.0);
        line.setStartX(-100.0);
        line.setStartY(0);
        line.setEndX(524.0);
        line.setEndY(0);

        TextArea lyrics_input = new TextArea();
        lyrics_input.setPromptText("Lyrics");
        lyrics_input.setLayoutX(324.0);
        lyrics_input.setLayoutY(11.0);
        lyrics_input.setPrefHeight(86.0);
        lyrics_input.setPrefWidth(256.0);

        lyrics_input.textProperty().addListener((observable, oldValue, newValue) -> {
            Track currentTrack = value_Tracks.get(file_path);
            currentTrack.setLyrics(newValue);
            value_Tracks.put(file_path, currentTrack);
        });

        ComboBox<GenreTrack> genre_track_input = new ComboBox();
        genre_track_input.setLayoutX(613.0);
        genre_track_input.setLayoutY(11.0);
        genre_track_input.setPrefWidth(150.0);

        GenreTrack none_Option = new GenreTrack(null, "None");
        ArrayList<GenreTrack> list_genreTrack = genreTrackDao.select_all_genreTrack("active");
        list_genreTrack.add(0, none_Option);
        if (list_genreTrack != null && !list_genreTrack.isEmpty()) {
            ObservableList<GenreTrack> list_genreTrack_active = FXCollections.observableArrayList(list_genreTrack);
            if (!list_genreTrack_active.isEmpty()) {
                genre_track_input.getItems().setAll(list_genreTrack_active);
                genre_track_input.setValue(none_Option);
                genre_track_input.setOnAction(event -> {
                    GenreTrack selected_genreTrack = genre_track_input.getValue();
                    if (selected_genreTrack != null) {
                        Track currentTrack = value_Tracks.get(file_path);
                        currentTrack.setGenre_id(selected_genreTrack.getId());
                        value_Tracks.put(file_path, currentTrack);
                    }
                });
            }
        }

        Button buttonX = new Button("Delete");
        buttonX.setLayoutX(613.0);
        buttonX.setLayoutY(54.0);
        buttonX.setMnemonicParsing(false);
        buttonX.setPrefHeight(35.0);
        buttonX.setPrefWidth(151.0);
        buttonX.setStyle("-fx-cursor: hand;-fx-background-color: red");
        buttonX.setTextFill(javafx.scene.paint.Color.WHITE);

        buttonX.setOnAction(event -> {
            value_Tracks.remove(file_path);
            tracks_in_playlist_wrap.getChildren().remove(anchorPane);
        });

        Font font = Font.font("System Bold", 15.0);
        buttonX.setFont(font);

        anchorPane.getChildren().addAll(track_name_input, singers_input, lyrics_input, genre_track_input, line, buttonX);
        tracks_in_playlist_wrap.getChildren().add(anchorPane);
    }

    private Map<String, Duration> getValueFromFiles(List<File> selected_Files) {
        try {
            Map<String, Duration> selected_Track_Paths = new LinkedHashMap<>();
            for (File file : selected_Files) {

                FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
                Bitstream bitstream = new Bitstream(fileInputStream);

                Header header = bitstream.readFrame();
                if (header != null) {
                    int totalFrames = bitstream.readFrame().max_number_of_frames((int) fileInputStream.getChannel().size());
                    int durationInSeconds = (int) Math.ceil((totalFrames - 1) * header.ms_per_frame() / 1000.0);

                    int minutes = durationInSeconds / 60;
                    int seconds = durationInSeconds % 60;

                    fileInputStream.close();

                    Duration duration = Duration.ofMinutes(minutes).plusSeconds(seconds);
                    selected_Track_Paths.put(file.getAbsolutePath(), duration);

                } else {
                    Utils.showNotification("Error", "File invalid", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
                }

            }
            return selected_Track_Paths;
        } catch (IOException | BitstreamException e) {
            e.printStackTrace();
        }
        return null;
    }

    //image
    public boolean dialog_upload_image(Button button_upload_image) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG files (*.jpg)", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"),
                new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.gif")
        );
        File selected_File = fileChooser.showOpenDialog(button_upload_image.getScene().getWindow());
        try {
            if (selected_File != null) {
                BufferedImage image_read = ImageIO.read(selected_File);
                if (image_read != null) {
                    setSelected_Image_Paths(selected_File.getAbsolutePath());
                    String imagePath = selected_File.toURI().toString();
                    Image image = new Image(imagePath);
                    if (button_upload_image.equals(btn_upload_image_track) || button_upload_image.equals(btn_replace_image_track)) {
                        image_content.setImage(image);
                    } else if (button_upload_image.equals(btn_upload_image_album) || button_upload_image.equals(btn_replace_image_playlist)) {
                        image_content_playlist.setImage(image);
                    }
                    return true;
                } else {
                    Utils.showNotification("Error", "File invalid", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void upload_Image() {
        if (upload_track_container.isVisible()) {
            btn_upload_image_track.setOnAction(event -> {
                if (dialog_upload_image(btn_upload_image_track) == true) {
                    upload_image_content_wrap.setVisible(false);
                    upload_image_content_wrap.setDisable(false);
                    image_content.setDisable(false);
                    btn_replace_image_track.setVisible(true);
                    btn_upload_image_track.setVisible(false);
                }
            });

            btn_replace_image_track.setOnAction(event -> {
                dialog_upload_image(btn_replace_image_track);
            });
        }

        if (upload_playlist_container.isVisible()) {
            btn_upload_image_album.setOnAction(event -> {
                if (dialog_upload_image(btn_upload_image_album) == true) {
                    upload_image_playlist_wrap.setDisable(false);
                    upload_image_playlist_wrap.setVisible(false);
                    image_content_playlist.setDisable(false);
                    btn_replace_image_playlist.setVisible(true);
                    btn_upload_image_album.setVisible(false);
                }
            });

            btn_replace_image_playlist.setOnAction(event -> {
                dialog_upload_image(btn_replace_image_playlist);
            });
        }

    }

    public void handler_process_upload_image(Boolean isTrack) {
        if (selected_Image_Paths != null) {
            try (InputStream inputStream = new FileInputStream(selected_Image_Paths)) {
                String currentPath = System.getProperty("user.dir");
                File file_path = new File(selected_Image_Paths);
                String file_name = file_path.getName();
                setFile_name_image_Upload(file_name);
                String targetImagePath_Track = currentPath + "\\src\\main\\resources\\application\\static\\images\\tracks\\" + file_name;
                Path target_track = Path.of(targetImagePath_Track);

                if (!Files.exists(target_track)) {
                    Files.copy(inputStream, target_track, StandardCopyOption.REPLACE_EXISTING);
                }

                if (!isTrack) {
                    String targetImagePath_Playlist = currentPath + "\\src\\main\\resources\\application\\static\\images\\playlist\\" + file_name;
                    Path target_Playlist = Path.of(targetImagePath_Playlist);

                    try (InputStream secondInputStream = new FileInputStream(selected_Image_Paths)) {
                        if (!Files.exists(target_Playlist)) {
                            Files.copy(secondInputStream, target_Playlist, StandardCopyOption.REPLACE_EXISTING);
                        }
                    }
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException e) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            Utils.showNotification("Error", "Image can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
        }
    }

    //track
    public void add_More_Track_Playlist(Map<String, Track> value_Tracks) {
        btn_addTrack_upload_playlist.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3")
            );
            List<File> selected_Files = fileChooser.showOpenMultipleDialog(btn_addTrack_upload_playlist.getScene().getWindow());
            if (selected_Files != null && !selected_Files.isEmpty()) {
                Map<String, Duration> selected_Track_Paths = getValueFromFiles(selected_Files);
                if (selected_Track_Paths != null && !selected_Track_Paths.isEmpty()) {
                    tracks_in_playlist_wrap.getChildren().clear();
                    Map<String, Duration> value_Files = handler_process_upload_Tracks(selected_Track_Paths);

                    for (Map.Entry<String, Duration> entry : value_Files.entrySet()) {
                        String track_name = entry.getKey().split("\\.")[0];
                        value_Tracks.put(entry.getKey(), new Track(track_name, entry.getValue()));
                    }

                    for (Map.Entry<String, Track> tracks : value_Tracks.entrySet()) {
                        show_tracks_in_playlist(tracks.getKey(), tracks.getValue().getName(), value_Tracks);
                    }

                    if (!value_Tracks.isEmpty()) {
                        add_Playlist(value_Tracks);
                    }
                }
            }
        });
    }

    public Map<String, Duration> handler_process_upload_Tracks(Map<String, Duration> selected_Track_Paths) {
        if (!selected_Track_Paths.isEmpty()) {
            Map<String, Duration> value_Files = new LinkedHashMap<>();
            for (Map.Entry<String, Duration> entry : selected_Track_Paths.entrySet()) {
                try (InputStream inputStream = new FileInputStream(entry.getKey())) {
                    String currentPath = System.getProperty("user.dir");
                    File file_path = new File(entry.getKey());
                    String file_name = file_path.getName();
                    value_Files.put(file_name, entry.getValue());
                    String targetImagePath = currentPath + "\\src\\main\\resources\\application\\static\\audio\\" + file_name;
                    Path target = Path.of(targetImagePath);
                    if (!Files.exists(target)) {
                        Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException e) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            return value_Files;
        }
        return null;
    }

    //validate
    public boolean validate_UploadTrack(String track_name, String singers) {
        String regex_trackName = "^[a-zA-Z0-9 ]*$";
        String regex_trackSingers = "^[a-zA-Z0-9, ]*$";
        if (track_name == null || track_name.isEmpty()) {
            ut.showNotification("Error", "Track name can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            return false;
        } else {
            Pattern pattern = Pattern.compile(regex_trackName);
            if (!pattern.matcher(track_name).matches()) {
                ut.showNotification("Error", "Track name invalid", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
                return false;
            }
        }

        if (singers == null || singers.isEmpty()) {
            ut.showNotification("Error", "Singers can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            return false;
        } else {
            Pattern pattern = Pattern.compile(regex_trackSingers);
            if (!pattern.matcher(singers).matches()) {
                ut.showNotification("Error", "Signers name invalid(Only character ',' allowed)", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
                return false;
            }
        }
        return true;
    }

    public boolean validate_UploadPlaylist() {
        if (name_playlist_input.getText().isEmpty()) {
            ut.showNotification("Error", "Playlist name fields can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            return false;
        }
        return true;
    }

    //upload
    public void upload(List<File> selected_Files) {
        if (upload_track_container.isVisible()) {
            btn_cancel_upload_playlist.setOnAction(event -> {
                selected_Files.clear();
            });
        }

        if (upload_playlist_container.isVisible()) {
            btn_cancel_upload_track.setOnAction(event -> {
                selected_Files.clear();
                tracks_in_playlist_wrap.getChildren().clear();
            });
        }

        if (selected_Files != null && !selected_Files.isEmpty()) {
            Map<String, Duration> selected_Track_Paths = getValueFromFiles(selected_Files);
            if (selected_Track_Paths != null && selected_Track_Paths.size() == 1) {
                upload_container.setVisible(false);
                upload_track_container.setVisible(true);
                upload_Image();

                btn_save_upload_track.setOnAction(event_add_track -> {
                    Map<String, Duration> value_Files = handler_process_upload_Tracks(selected_Track_Paths);
                    handler_process_upload_image(null);
                    for (Map.Entry<String, Duration> entry : value_Files.entrySet()) {
                        if (validate_UploadTrack(track_name_input.getText(), track_singers_input.getText())) {
                            Track track = new Track(track_name_input.getText(), entry.getValue(), genreTrack_Id, track_singers_input.getText(), track_lyrics_input.getText(), file_name_image_Upload, entry.getKey());
                            if (track != null) {
                                Integer track_id = trackDao.insert_Tracks(track, authLogin.getId());
                                if (track_id != null) {
                                    upload_4_artist_container.setVisible(false);
                                    showMain_container.setVisible(true);
                                    showMain_container.setVisible(true);
                                }
                            }
                        }
                    }
                });
            } else {
                if (selected_Track_Paths != null && selected_Track_Paths.size() > 1) {
                    upload_container.setVisible(false);
                    upload_playlist_container.setVisible(true);
                    upload_Image();

                    Map<String, Duration> value_Files = handler_process_upload_Tracks(selected_Track_Paths);
                    Map<String, Track> value_Tracks = new LinkedHashMap<>();
                    for (Map.Entry<String, Duration> entry : value_Files.entrySet()) {
                        String track_name = entry.getKey().split("\\.")[0];
                        value_Tracks.put(entry.getKey(), new Track(track_name, entry.getValue()));
                    }

                    for (Map.Entry<String, Track> tracks : value_Tracks.entrySet()) {
                        show_tracks_in_playlist(tracks.getKey(), tracks.getValue().getName(), value_Tracks);
                    }

                    if (!value_Tracks.isEmpty()) {
                        add_Playlist(value_Tracks);
                    }

                    add_More_Track_Playlist(value_Tracks);
                }
            }
        }
    }

    public void add_Playlist(Map<String, Track> value_Tracks) {
        btn_save_upload_playlist.setOnAction(event_add_playlist -> {
            handler_process_upload_image(false);
            if (validate_UploadPlaylist()) {
                Playlist playlist = new Playlist(name_playlist_input.getText(), file_name_image_Upload, value_Tracks.size(), description_playlist_input.getText());
                if (playlist != null) {
                    Integer playlist_Id = playListDao.insert_playlist(authLogin.getId(), playlist);
                    if (playlist_Id != null) {
                        Integer i = 1;

                        Set<Integer> total_trackId = new LinkedHashSet<>();
                        for (Map.Entry<String, Track> tracks : value_Tracks.entrySet()) {
                            if (validate_UploadTrack(tracks.getValue().getName(), tracks.getValue().getSingers())) {
                                Track track = new Track(tracks.getValue().getName(), tracks.getValue().getDuration(), tracks.getValue().getGenre_id(), tracks.getValue().getSingers(), tracks.getValue().getLyrics(), file_name_image_Upload, tracks.getKey());
                                if (track != null) {
                                    Integer track_id = trackDao.insert_Tracks(track, authLogin.getId());
                                    total_trackId.add(track_id);
                                }
                            } else {
                                playListDao.delete_Playlist(playlist_Id);
                            }
                        }

                        if (!total_trackId.contains(null)) {
                            for (Integer track_id : total_trackId) {
                                if (trackPlaylistDao.insert_playListTrack(track_id, playlist_Id, i++)) {
                                    upload_4_artist_container.setVisible(false);
                                    showMain_container.setVisible(true);
                                }
                            }
                        } else {
                            for (Integer track_id : total_trackId) {
                                if (track_id != null) {
                                    trackDao.delete_Track(track_id);
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public void default_ValueGenreTrack() {
        GenreTrack none_Option = new GenreTrack(null, "None");
        ArrayList<GenreTrack> list_genreTrack = genreTrackDao.select_all_genreTrack("active");
        list_genreTrack.add(0, none_Option);
        if (list_genreTrack != null && !list_genreTrack.isEmpty()) {
            ObservableList<GenreTrack> list_genreTrack_active = FXCollections.observableArrayList(list_genreTrack);
            if (!list_genreTrack_active.isEmpty()) {
                category_track_input.getItems().setAll(list_genreTrack_active);
                category_track_input.setValue(none_Option);
                category_track_input.setOnAction(event -> {
                    GenreTrack selected_genreTrack = category_track_input.getValue();
                    if (selected_genreTrack != null) {
                        setGenreTrack_Id(selected_genreTrack.getId());
                    }
                });
            }
        }
    }

    public void showUploadMusic() {
        upload_4_artist_container.setVisible(true);
        showMain_container.setVisible(false);
    }

    public void handle_cancelUpload() {
        btn_cancel_upload_track.setOnAction(event -> {
            upload_container.setVisible(true);
            upload_track_container.setVisible(false);
            setSelected_Image_Paths(null);
            image_content.setImage(null);
            upload_image_content_wrap.setVisible(true);
            btn_upload_image_track.setVisible(true);
            btn_replace_image_track.setVisible(false);
            track_name_input.clear();
            track_lyrics_input.clear();
            track_singers_input.clear();
        });

        btn_cancel_upload_playlist.setOnAction(event -> {
            upload_container.setVisible(true);
            upload_playlist_container.setVisible(false);
            setSelected_Image_Paths(null);
            image_content_playlist.setImage(null);
            upload_image_playlist_wrap.setVisible(true);
            btn_upload_image_album.setVisible(true);
            btn_replace_image_playlist.setVisible(false);
            tracks_in_playlist_wrap.getChildren().clear();
            name_playlist_input.clear();
            description_playlist_input.clear();
        });
    }
}
