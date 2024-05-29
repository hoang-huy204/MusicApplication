/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.dao.GenreTrackDao;
import com.dao.PlayListDao;
import com.dao.TrackDao;
import com.dao.TrackPlaylistDao;
import com.model.GenreTrack;
import com.model.Playlist;
import com.model.Track;
import com.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;

import javax.imageio.ImageIO;

public class AdminPlayListFormController implements Initializable {
    Utils utils = new Utils();

    private Playlist playlist_edit;

    private Map<String, Track> value_Tracks;

    public AdminPlayListFormController() {
    }

    public AdminPlayListFormController(Playlist playlist) {
        this.playlist_edit = playlist;
    }

    private String selected_Image_Paths;

    private String file_name_image_Upload;

    private GenreTrackDao genreTrackDao = new GenreTrackDao();

    private TrackDao trackDao = new TrackDao();
    private PlayListDao playListDao = new PlayListDao();
    private TrackPlaylistDao trackPlaylistDao = new TrackPlaylistDao();

    public void setSelected_Image_Paths(String selected_Image_Paths) {
        this.selected_Image_Paths = selected_Image_Paths;
    }

    public void setFile_name_image_Upload(String file_name_image_Upload) {
        this.file_name_image_Upload = file_name_image_Upload;
    }

    @FXML
    private AnchorPane upload_4_artist_container;

    @FXML
    private AnchorPane upload_container;

    @FXML
    private Button btn_upload;

    @FXML
    private AnchorPane upload_playlist_container;

    @FXML
    private Pane upload_image_playlist_wrap;

    @FXML
    private Button btn_upload_image_album;

    @FXML
    private Button btn_replace_image_playlist;

    @FXML
    private Button btn_addTrack_upload_playlist;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        value_Tracks = new LinkedHashMap<>();
        upload();
        handle_cancelUpload();
    }

    public void upload() {
        if (playlist_edit == null) {
            drag_file();
            selected_Files();
            add_Track_More_Playlist();
            add_Playlist();
        } else {
            upload_container.setVisible(false);
            upload_playlist_container.setVisible(true);
            show_PlaylistEdit();
            add_Track_More_Playlist();
            update_playlist();
        }
    }

    public void show_PlaylistEdit() {
        show_image_edit();
        show_Detail_Playlist();
    }

    private void drag_file() {
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
                List<File> dropped_Files = db.getFiles();
                value_Tracks = process_Files(dropped_Files);
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
                value_Tracks = process_Files(selected_Files);
            });
        }
    }

    public void add_Track_More_Playlist() {
        if (btn_addTrack_upload_playlist.isVisible()) {
            btn_addTrack_upload_playlist.setOnAction(event -> {
                Map<String, Track> old_Tracks = value_Tracks;

                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3")
                );
                List<File> selected_Files = fileChooser.showOpenMultipleDialog(btn_upload.getScene().getWindow());
                Map<String, Track> new_Tracks = process_Files(selected_Files);
                old_Tracks.putAll(new_Tracks);

                value_Tracks = old_Tracks;
            });
        }
    }


    public void show_image_edit() {
        String currentPath = System.getProperty("user.dir");
        File file = new File(currentPath + "\\src\\main\\resources\\application\\static\\images\\playlist\\" + playlist_edit.getImage());
        Image image = new Image(file.toURI().toString());
        image_content_playlist.setImage(image);
        if (image_content_playlist.getImage() != null) {
            upload_image_playlist_wrap.setDisable(false);
            upload_image_playlist_wrap.setVisible(false);
            image_content_playlist.setDisable(false);
            btn_replace_image_playlist.setVisible(true);
            btn_upload_image_album.setVisible(false);
        }

        btn_replace_image_playlist.setOnAction(event -> {
            dialog_upload_image(btn_replace_image_playlist);
        });
    }

    public void show_Detail_Playlist() {
        name_playlist_input.setText(playlist_edit.getName());
        description_playlist_input.setText(playlist_edit.getDescription());
        show_Tracks();
    }

    public void show_Tracks() {
        if (trackDao.select_TrackByPlaylistId(playlist_edit.getId()) != null && !trackDao.select_TrackByPlaylistId(playlist_edit.getId()).isEmpty()) {
            for (Track track : trackDao.select_TrackByPlaylistId(playlist_edit.getId())) {
                value_Tracks.put(track.getFile_path(), new Track(track.getId(), track.getName(), track.getDuration(), track.getGenre_id(), track.getSingers(), track.getLyrics()));
            }
        }


        for (Map.Entry<String, Track> tracks : value_Tracks.entrySet()) {
            show_tracks_in_playlist(tracks.getKey(), tracks.getValue(), value_Tracks);
        }

    }


    private Map<String, Track> process_Files(List<File> files) {
        if (files != null && !files.isEmpty()) {
            Map<String, Duration> selected_Track_Paths = getValueFromFiles(files);
            if (!selected_Track_Paths.isEmpty()) {
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
                    show_tracks_in_playlist(tracks.getKey(), tracks.getValue(), value_Tracks);
                }

                return value_Tracks;
            }
        }
        return null;
    }

    public void upload_Image() {
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
                    image_content_playlist.setImage(image);
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


    public void handler_process_upload_image() {
        if (selected_Image_Paths != null) {
            try {
                InputStream inputStream = new FileInputStream(selected_Image_Paths);
                String currentPath = System.getProperty("user.dir");
                File file_path = new File(selected_Image_Paths);
                String file_name = file_path.getName();
                setFile_name_image_Upload(file_name);

                String targetImagePath_Track = currentPath + "\\src\\main\\resources\\application\\static\\images\\tracks\\" + file_name;
                Path target_track = Path.of(targetImagePath_Track);

                if (!Files.exists(target_track)) {
                    Files.copy(inputStream, target_track, StandardCopyOption.REPLACE_EXISTING);
                }

                String targetImagePath_Playlist = currentPath + "\\src\\main\\resources\\application\\static\\images\\playlist\\" + file_name;
                Path target_Playlist = Path.of(targetImagePath_Playlist);

                InputStream secondInputStream = new FileInputStream(selected_Image_Paths);
                if (!Files.exists(target_Playlist)) {
                    Files.copy(secondInputStream, target_Playlist, StandardCopyOption.REPLACE_EXISTING);
                }


            } catch (FileNotFoundException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException e) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            if (playlist_edit == null) {
                utils.showNotification("Error", "Image can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            }
        }
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

    public void show_tracks_in_playlist(String file_path, Track track, Map<String, Track> value_Tracks) {
        scroll_pane_tracks_in_playlist.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(777.0);
        anchorPane.setPrefHeight(104.0);

        TextField track_name_input = new TextField();
        track_name_input.setPromptText("Track_Name");
        track_name_input.setText(track.getName());
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
        if (playlist_edit != null) {
            singers_input.setText(track.getSingers());
        }
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
        if (playlist_edit != null) {
            lyrics_input.setText(track.getLyrics());
        }
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
        if (!list_genreTrack.isEmpty()) {
            ObservableList<GenreTrack> list_genreTrack_active = FXCollections.observableArrayList(list_genreTrack);

            if (playlist_edit != null) {
                list_genreTrack_active.stream()
                        .filter(_track -> Objects.equals(_track.getId(), track.getGenre_id()))
                        .findFirst()
                        .ifPresent(default_GenreTrack -> {
                            genre_track_input.setValue(default_GenreTrack);
                            Track currentTrack = value_Tracks.get(file_path);
                            currentTrack.setGenre_id(default_GenreTrack.getId());
                            value_Tracks.put(file_path, currentTrack);
                        });

            }

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
            if (playlist_edit != null) {
                dialog_Delete(track.getId(), anchorPane);
            }
        });

        Font font = Font.font("System Bold", 15.0);
        buttonX.setFont(font);

        anchorPane.getChildren().addAll(track_name_input, singers_input, lyrics_input, genre_track_input, line, buttonX);
        tracks_in_playlist_wrap.getChildren().add(anchorPane);
    }

    public void dialog_Delete(Integer track_Id, AnchorPane anchorPane) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Delete");

        ButtonType button_Remove_From_Playlist = new ButtonType("remove from playlist", ButtonBar.ButtonData.OK_DONE);
        ButtonType button_Delete_Track = new ButtonType("Delete Track and Remove from playlist", ButtonBar.ButtonData.APPLY);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(button_Remove_From_Playlist, button_Delete_Track, buttonTypeCancel);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == button_Remove_From_Playlist) {
                if (trackDao.delete_Track_playlist(track_Id, false)) {
                    Utils.showNotification("Success!", "delete success", Utils.NotificationType.INFORMATION, javafx.util.Duration.seconds(3));
                    tracks_in_playlist_wrap.getChildren().remove(anchorPane);
                    Stage stage = (Stage) tracks_in_playlist_wrap.getScene().getWindow();
                    stage.close();
                }
            }
            if (dialogButton == button_Delete_Track) {
                if (trackDao.delete_Track_playlist(track_Id, true)) {
                    Utils.showNotification("Success!", "delete success", Utils.NotificationType.INFORMATION, javafx.util.Duration.seconds(3));
                    tracks_in_playlist_wrap.getChildren().remove(anchorPane);
                    Stage stage = (Stage) tracks_in_playlist_wrap.getScene().getWindow();
                    stage.close();
                }
            }
            return null;
        });
        dialog.showAndWait();
    }

    public boolean validate_UploadTrack(String track_name, String singers) {
        String regex_trackName = "^[a-zA-Z0-9 ]*$";
        String regex_trackSingers = "^[a-zA-Z0-9,]*$";
        if (track_name == null || track_name.isEmpty()) {
            Utils.showNotification("Error", "Track name can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            return false;
        } else {
            Pattern pattern = Pattern.compile(regex_trackName);
            if (!pattern.matcher(track_name).matches()) {
                Utils.showNotification("Error", "Track name invalid", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
                return false;
            }
        }

        if (singers == null || singers.isEmpty()) {
            Utils.showNotification("Error", "Singers can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            return false;
        } else {
            Pattern pattern = Pattern.compile(regex_trackSingers);
            if (!pattern.matcher(singers).matches()) {
                Utils.showNotification("Error", "Signers name invalid(Only character ',' allowed)", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
                return false;
            }
        }
        return true;
    }

    public boolean validate_UploadPlaylist() {
        String regex_playlist = "^[a-zA-Z0-9 ]*$";
        if (name_playlist_input.getText().isEmpty()) {
            Utils.showNotification("Error", "Playlist name fields can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            return false;
        } else {
            Pattern pattern = Pattern.compile(regex_playlist);
            if (!pattern.matcher(name_playlist_input.getText()).matches()) {
                Utils.showNotification("Error", "Playlist name invalid", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
                return false;
            }
        }

        if (description_playlist_input.getText().isEmpty()) {
            Utils.showNotification("Error", "Description fields can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            return false;
        } else {
            Pattern pattern = Pattern.compile(regex_playlist);
            if (!pattern.matcher(description_playlist_input.getText()).matches()) {
                Utils.showNotification("Error", "Description playlist invalid", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
                return false;
            }
        }
        return true;
    }

    public void add_Playlist() {
        btn_save_upload_playlist.setOnAction(event_add_playlist -> {
            if (!value_Tracks.isEmpty()) {
                handler_process_upload_image();
                if (validate_UploadPlaylist()) {
                    Playlist playlist = new Playlist(name_playlist_input.getText(), file_name_image_Upload, value_Tracks.size(), description_playlist_input.getText());
                    if (playlist != null) {
                        Integer playlist_Id = playListDao.insert_playlist(1, playlist);
                        if (playlist_Id != null) {
                            Integer i = 1;

                            Set<Integer> total_trackId = new LinkedHashSet<>();
                            for (Map.Entry<String, Track> tracks : value_Tracks.entrySet()) {
                                if (validate_UploadTrack(tracks.getValue().getName(), tracks.getValue().getSingers())) {
                                    Track track = new Track(tracks.getValue().getName(), tracks.getValue().getDuration(), tracks.getValue().getGenre_id(), tracks.getValue().getSingers(), tracks.getValue().getLyrics(), file_name_image_Upload, tracks.getKey());
                                    if (track != null) {
                                        Integer track_id = trackDao.insert_Tracks(track, LoginController.authLogin.getId());
                                        total_trackId.add(track_id);
                                    }
                                } else {
                                    playListDao.delete_Playlist(playlist_Id);
                                }
                            }

                            if (!total_trackId.contains(null)) {
                                for (Integer track_id : total_trackId) {
                                    if (trackPlaylistDao.insert_playListTrack(track_id, playlist_Id, i++)) {
                                        Stage stage = (Stage) btn_save_upload_playlist.getScene().getWindow();
                                        stage.close();
                                    }
                                }
                            } else {
                                for (Integer track_id : total_trackId) {
                                    if (track_id != null) {
                                        trackDao.delete_Track(track_id);
                                        playListDao.delete_Playlist(playlist_Id);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }


    public void update_playlist() {
        btn_save_upload_playlist.setOnAction(event_update_playlist -> {
            if (!value_Tracks.isEmpty()) {
                handler_process_upload_image();
                Playlist playlist;
                if (validate_UploadPlaylist()) {
                    if (selected_Image_Paths != null) {
                        playlist = new Playlist(playlist_edit.getId(), name_playlist_input.getText(), file_name_image_Upload, value_Tracks.size(), description_playlist_input.getText());
                    } else {
                        playlist = new Playlist(playlist_edit.getId(), name_playlist_input.getText(), playlist_edit.getImage(), value_Tracks.size(), description_playlist_input.getText());
                    }
                    if (playlist != null) {
                        if (playListDao.update_Playlist(playlist)) {
                            Integer i = 1;
                            for (Map.Entry<String, Track> tracks : value_Tracks.entrySet()) {
                                Track track;
                                if (selected_Image_Paths != null) {
                                    track = new Track(tracks.getValue().getId(), tracks.getValue().getName(), tracks.getValue().getDuration(), tracks.getValue().getGenre_id(), tracks.getValue().getSingers(), tracks.getValue().getLyrics(), file_name_image_Upload, tracks.getKey());
                                } else {
                                    track = new Track(tracks.getValue().getId(), tracks.getValue().getName(), tracks.getValue().getDuration(), tracks.getValue().getGenre_id(), tracks.getValue().getSingers(), tracks.getValue().getLyrics(), playlist_edit.getImage(), tracks.getKey());
                                }
                                if (track != null) {
                                    Integer track_Id = trackDao.update_Track_4_Playlist(track, LoginController.authLogin.getId());
                                    if (track_Id != null) {
                                        if (trackPlaylistDao.insert_playListTrack(track_Id, playlist_edit.getId(), i++)) {
                                            Stage stage = (Stage) btn_save_upload_playlist.getScene().getWindow();
                                            stage.close();
                                            Utils.showNotification("Successfully", "Update Successfully", Utils.NotificationType.INFORMATION, javafx.util.Duration.seconds(3));
                                        }
                                    } else {
                                        Stage stage = (Stage) btn_save_upload_playlist.getScene().getWindow();
                                        stage.close();
                                        Utils.showNotification("Successfully", "Update Successfully", Utils.NotificationType.INFORMATION, javafx.util.Duration.seconds(3));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private void handle_cancelUpload() {
        btn_cancel_upload_playlist.setOnAction(event -> {
            if (playlist_edit != null) {
                Stage stage = (Stage) btn_cancel_upload_playlist.getScene().getWindow();
                stage.close();
            }
            upload_container.setVisible(true);
            upload_playlist_container.setVisible(false);

            setSelected_Image_Paths(null);
            image_content_playlist.setImage(null);
            upload_image_playlist_wrap.setVisible(true);
            btn_upload_image_album.setVisible(true);
            btn_replace_image_playlist.setVisible(false);

            value_Tracks.clear();
            tracks_in_playlist_wrap.getChildren().clear();

            name_playlist_input.clear();
            description_playlist_input.clear();
        });
    }

}
