/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import static com.controller.HomeController.originalBorderPaneRoot;
import static com.controller.LoginController.isAuthVip;
import static com.controller.MusicControlController.musicControlController;
import com.dao.TrackDao;
import com.dao.TrackPlaylist;
import com.model.Playlist;
import com.model.Track;
import com.utils.Utils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author daoqu
 */
public class SearchController implements Initializable {

    @FXML
    private TextField search;
    @FXML
    private AnchorPane panephu;
    @FXML
    private Label tieude;

    @FXML
    private Label tencasi;

    @FXML
    private Label time;
    @FXML
    private ImageView anh;
    @FXML
    private PreparedStatement pst;
    @FXML
    private VBox Trackvbox;
    @FXML
    private HBox playlistHbox;
    @FXML
    private FontAwesomeIconView previousPageButton;
    @FXML
    private FontAwesomeIconView nextPageButton;
    @FXML
    private Label currentPageLabel;
    @FXML
    private Label totalPagesLabel;

    private ArrayList<Track> trackList;

    private TrackDao trackDao = new TrackDao();
    private ArrayList<Playlist> PlaylistList;
    private ArrayList<Playlist> playlistAll;
    
    private Utils ut = new Utils();

    private TrackPlaylist trackplaylist = new TrackPlaylist();

    private String currentPath;
    private static final int PLAYLISTS_PER_PAGE = 4;
    private int currentPage = 1;
    
    private String searchCharacters = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            currentPath = System.getProperty("user.dir");
            currentPath = currentPath.replace("\\", "/");

            if (isAuthVip) {
                this.playlistAll = this.trackplaylist.getAll("active", 1);
            } else {
                this.playlistAll = this.trackplaylist.getAll("active", 0);
            }
//            System.out.println("1");
            playlistDisplay(0, "");

            if (isAuthVip) {
                this.trackList = this.trackDao.selectAllTrack("active");
            } else {
                this.trackList = this.trackDao.selectAllTrackNoVip();
            }
            
            for (Track tk : this.trackList) {
//                VBox vbox = new VBox();
//                vbox.setLayoutX(-1.0);
//                vbox.setLayoutY(392.0);
//                vbox.setPrefHeight(260.0);
//                vbox.setPrefWidth(953.0);

                AnchorPane anchorPane1 = new AnchorPane();
                anchorPane1.setPrefHeight(65.0);
                anchorPane1.setPrefWidth(953.0);

                Image image = new Image("file:" + currentPath + "/src/main/resources/application/static/images/tracks/" + tk.getImage());
//                System.out.println("file:" + currentPath + "/src/main/resources/application/static/images/tracks" + tk.getImage());
                ImageView imageView1 = new ImageView(image);
                imageView1.setFitHeight(65.0);
                imageView1.setFitWidth(103.0);
                imageView1.setLayoutY(1.0);
//                imageView1.setPickOnBounds(true);
                imageView1.setPreserveRatio(true);

                Label label1 = new Label();
                label1.setLayoutX(152.0);
                label1.setLayoutY(34.0);
                label1.setText(tk.getSingers());
                label1.setTextFill(Color.WHITE);
                Font font1 = Font.font("Rockwell Nova Cond Bold", 19.0);
                label1.setFont(font1);

                Label label2 = new Label();
                label2.setId("time");
                label2.setLayoutX(790.0);
                label2.setLayoutY(16.0);
                long durationMinutes = tk.getDuration().toMinutes();
                long hours = durationMinutes;
                long minutes = durationMinutes % 60;
                String durationText = String.format("%d:%02d", hours, minutes);
                label2.setText(durationText);
                label2.setTextFill(Color.WHITE);
                Font font2 = Font.font("System Bold", 22.0);
                label2.setFont(font2);

                image = new Image("file:" + currentPath + "/src/main/resources/application/image/casi.jpg");
                ImageView imageView2 = new ImageView(image);
                imageView2.setFitHeight(22.0);
                imageView2.setFitWidth(31.0);
                imageView2.setLayoutX(116.0);
                imageView2.setLayoutY(35.0);
                imageView2.setPickOnBounds(true);
                imageView2.setPreserveRatio(true);

                Label label3 = new Label();
                label3.setLayoutX(130.0);
                label3.setLayoutY(3.0);
                label3.setText(tk.getName());
                label3.setTextFill(Color.WHITE);
                Font font3 = Font.font("Rockwell Nova Cond Bold", 22.0);
                label3.setFont(font3);
                label3.setOnMousePressed(event -> {
                    musicControlController.currentIndexSong(tk, tk.getSingers());
                });

                anchorPane1.getChildren().addAll(imageView1, label1, label2, imageView2, label3);

//                AnchorPane anchorPane2 = new AnchorPane();
//                anchorPane2.setPrefHeight(65.0);
//                anchorPane2.setPrefWidth(953.0);
//
//                AnchorPane anchorPane3 = new AnchorPane();
//                anchorPane3.setPrefHeight(65.0);
//                anchorPane3.setPrefWidth(953.0);
//
//                AnchorPane anchorPane4 = new AnchorPane();
//                anchorPane4.setPrefHeight(65.0);
//                anchorPane4.setPrefWidth(953.0);

//                vbox.getChildren().addAll(anchorPane1, anchorPane2, anchorPane3, anchorPane4);
                Trackvbox.getChildren().addAll(anchorPane1);
            }
        });
    }

    public void playlistDisplay(Integer index, String characters) {
        if (isAuthVip) {
            this.PlaylistList = this.trackplaylist.timkiem(index, characters, 1);
        } else {
            this.PlaylistList = this.trackplaylist.timkiem(index, characters, 0);
        }
        
//            System.out.println(this.PlaylistList);
        playlistHbox.getChildren().clear();
        for (Playlist i : this.PlaylistList) {
//                System.out.println(i.toString());
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefWidth(237.0);
            anchorPane.setPrefHeight(266.0);
            anchorPane.setStyle("-fx-background-color: #353434;");
            anchorPane.setOnMousePressed(event -> {
                ut.loadSectionFxml2(originalBorderPaneRoot, "/application/views/UI/CurrentTrackList.fxml", "center", i.getId());
            });
//            anchorPane.setm(new Insets(0, 10, 0, 10));
//            AnchorPane.setMargin
            Label tieude = new Label(i.getName());
            tieude.setLayoutX(14.0);
            tieude.setLayoutY(137.0);
            tieude.setTextFill(javafx.scene.paint.Color.WHITE);
            tieude.setFont(Font.font("System Bold", 17.0));

            Label tencasi = new Label("BinZ");
            tencasi.setLayoutX(48.0);
            tencasi.setLayoutY(170.0);
            tencasi.setTextFill(javafx.scene.paint.Color.WHITE);
            tencasi.setFont(Font.font("Rockwell Nova Cond Bold", 16.0));

            Image image = new Image("file:" + currentPath + "/src/main/resources/application/image/casi.jpg");
            ImageView casiImage = new ImageView(image);
            casiImage.setFitWidth(31.0);
            casiImage.setFitHeight(22.0);
            casiImage.setLayoutX(14.0);
            casiImage.setLayoutY(169.0);
            casiImage.setPreserveRatio(true);
            casiImage.setPickOnBounds(true);

            image = new Image("file:" + currentPath + "/src/main/resources/application/static/images/playlist/" + i.getImage());
//            System.out.println("file:" + currentPath + "/src/main/resources/application/image/" + i.getImage());

            ImageView anhImage = new ImageView(image);
            anhImage.setFitWidth(245.0);
            anhImage.setFitHeight(140.0);
            anhImage.setPreserveRatio(true);
            anhImage.setPickOnBounds(true);

            anchorPane.getChildren().addAll(tieude, tencasi, casiImage, anhImage);

            playlistHbox.getChildren().add(anchorPane);
        }
    }

    @FXML
    public void handlePreviousPage(MouseEvent event) {
        if (currentPage > 1) {
            currentPage--;
            System.out.println(currentPage);
            System.out.println((currentPage - 1) * PLAYLISTS_PER_PAGE);
            playlistDisplay((currentPage - 1) * PLAYLISTS_PER_PAGE, searchCharacters);
            updatePaginationLabels();
        }
    }

    @FXML
    public void handleNextPage(MouseEvent event) {
        int totalPages = getTotalPages(playlistAll.size(), PLAYLISTS_PER_PAGE);
//        System.out.println(totalPages);
        if (currentPage < totalPages) {
            currentPage++;
            System.out.println(currentPage);
            System.out.println((currentPage - 1) * PLAYLISTS_PER_PAGE);
            playlistDisplay((currentPage - 1) * PLAYLISTS_PER_PAGE, searchCharacters);
            updatePaginationLabels();
        }
    }

    private void displayFilteredPlaylists(ArrayList<Playlist> filteredPlaylists, int currentPage, int playlistsPerPage) {
        playlistHbox.getChildren().clear();

        // Tính toán vị trí bắt đầu và kết thúc của Playlist trên trang hiện tại
//        int startIndex = (currentPage - 1) * playlistsPerPage;
//        int endIndex = Mat cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccch.min(startIndex + playlistsPerPage, filteredPlaylists.size());
//
//        for (int i = startIndex; i < endIndex; i++) {
//            Playlist playlist = filteredPlaylists.get(i);
//            AnchorPane anchorPane = createPlaylistAnchorPane(playlist);
//            playlistHbox.getChildren().add(anchorPane);
//        }
        updatePaginationLabels();
    }

    private void updatePaginationLabels() {
        int totalPages = getTotalPages(playlistAll.size(), PLAYLISTS_PER_PAGE);
        currentPageLabel.setText(Integer.toString(currentPage));
        totalPagesLabel.setText(Integer.toString(totalPages));

        previousPageButton.setDisable(currentPage == 1);
        nextPageButton.setDisable(currentPage == totalPages);
    }

    // Tính tổng số trang dựa trên số lượng Playlist và số lượng Playlist trên mỗi trang
    private int getTotalPages(int totalPlaylists, int playlistsPerPage) {
        return (int) Math.ceil((double) totalPlaylists / playlistsPerPage);
    }

    @FXML
    private void handleSearchKey(KeyEvent event) {
        String query = search.getText().toLowerCase();
        
        searchCharacters = query;
        
        playlistDisplay(0, searchCharacters);

//        // Tạo danh sách để lưu các Playlist phù hợp với kết quả tìm kiếm
//        ArrayList<Playlist> filteredPlaylists = new ArrayList<>();
//
//        // Tìm kiếm trong danh sách Playlist
//        for (Playlist playlist : PlaylistList) {
//            if (playlist.getName().toLowerCase().contains(query)) {
//                filteredPlaylists.add(playlist);
//            }
//        }
//
//        // Hiển thị danh sách Playlist phù hợp trên giao diện người dùng
//        displayFilteredPlaylists(filteredPlaylists);

        // Tạo danh sách để lưu các Track phù hợp với kết quả tìm kiếm
        ArrayList<Track> filteredTracks = new ArrayList<>();

        // Tìm kiếm trong danh sách Track
        for (Track track : trackList) {
            if (track.getName().toLowerCase().contains(query) || track.getSingers().toLowerCase().contains(query)) {
                filteredTracks.add(track);
            }
        }

        // Hiển thị danh sách Track phù hợp trên giao diện người dùng
        displayFilteredTracks(filteredTracks);
    }

//    private void displayFilteredPlaylists(ArrayList<Playlist> filteredPlaylists) {
//        playlistHbox.getChildren().clear();
//
//        for (Playlist playlist : filteredPlaylists) {
//            AnchorPane anchorPane = createPlaylistAnchorPane(playlist);
//            playlistHbox.getChildren().add(anchorPane);
//        }
//    }
//
//    private AnchorPane createPlaylistAnchorPane(Playlist playlist) {
//        AnchorPane anchorPane = new AnchorPane();
//        anchorPane.setPrefWidth(237.0);
//        anchorPane.setPrefHeight(266.0);
//        anchorPane.setStyle("-fx-background-color: #353434;");
//
//        Label tieude = new Label(playlist.getName());
//        tieude.setLayoutX(14.0);
//        tieude.setLayoutY(137.0);
//        tieude.setTextFill(Color.WHITE);
//        tieude.setFont(Font.font("System Bold", 17.0));
//
//        Label tencasi = new Label(String.valueOf(playlist.getAuth_id()));
//        tencasi.setLayoutX(48.0);
//        tencasi.setLayoutY(170.0);
//        tencasi.setTextFill(Color.WHITE);
//        tencasi.setFont(Font.font("Rockwell Nova Cond Bold", 16.0));
//
//        Image image = new Image("file:" + currentPath + "/src/main/resources/application/image/casi.jpg");
//        ImageView casiImage = new ImageView(image);
//        casiImage.setFitWidth(31.0);
//        casiImage.setFitHeight(22.0);
//        casiImage.setLayoutX(14.0);
//        casiImage.setLayoutY(169.0);
//        casiImage.setPreserveRatio(true);
//        casiImage.setPickOnBounds(true);
//
//        image = new Image("file:" + currentPath + "/src/main/resources/application/image/" + playlist.getImage());
//        ImageView anhImage = new ImageView(image);
//        anhImage.setFitWidth(234.0);
//        anhImage.setFitHeight(160.0);
//        anhImage.setPreserveRatio(true);
//        anhImage.setPickOnBounds(true);
//
//        anchorPane.getChildren().addAll(tieude, tencasi, casiImage, anhImage);
//        return anchorPane;
//    }

    private void displayFilteredTracks(ArrayList<Track> filteredTracks) {
        Trackvbox.getChildren().clear();

        for (Track track : filteredTracks) {
            VBox vbox = createTrackVBox(track);
            Trackvbox.getChildren().add(vbox);
        }
    }

    private VBox createTrackVBox(Track track) {
        VBox vbox = new VBox();
        vbox.setPrefHeight(65.0);
        vbox.setPrefWidth(953.0);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(65.0);
        anchorPane.setPrefWidth(953.0);

        String imagePath = "file:" + currentPath + "/src/main/resources/application/static/images/tracks/" + track.getImage();
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(65.0);
        imageView.setFitWidth(103.0);
        imageView.setPreserveRatio(true);

        String imagePath2 = "file:" + currentPath + "/src/main/resources/application/image/casi.jpg";
        Image image2 = new Image(imagePath2);
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitHeight(22.0);
        imageView2.setFitWidth(31.0);
        imageView2.setLayoutX(116.0);
        imageView2.setLayoutY(35.0);
        imageView2.setPickOnBounds(true);
        imageView2.setPreserveRatio(true);

        AnchorPane.setLeftAnchor(imageView, 10.0);
        AnchorPane.setTopAnchor(imageView, 15.0);
        AnchorPane.setLeftAnchor(imageView2, 130.0);
        AnchorPane.setTopAnchor(imageView2, 58.0);

        Label nameLabel = new Label(track.getName());
        nameLabel.setStyle("-fx-text-fill: white;");
        nameLabel.setLayoutX(130.0);
        nameLabel.setLayoutY(3.0);
        nameLabel.setTextFill(Color.WHITE);
        Font font3 = Font.font("Rockwell Nova Cond Bold", 22.0);
        nameLabel.setFont(font3);
        nameLabel.setOnMousePressed(event -> {
            musicControlController.currentIndexSong(track, track.getSingers());
        });

        Label artistLabel = new Label(track.getSingers());
        artistLabel.setStyle("-fx-text-fill: white;");
        artistLabel.setLayoutX(60.0);
        artistLabel.setLayoutY(34.0);
        artistLabel.setTextFill(Color.WHITE);
        Font font1 = Font.font("Rockwell Nova Cond Bold", 19.0);
        artistLabel.setFont(font1);

        Label durationLabel = new Label();
        long durationSecond = track.getDuration().toSeconds();
        long minutes = durationSecond / 60;
        long second = durationSecond % 60;
        String durationText = String.format("%d:%02d", minutes, second);
        durationLabel.setStyle("-fx-text-fill: white;");
        durationLabel.setLayoutX(790.0);
        durationLabel.setLayoutY(16.0);
        durationLabel.setText(durationText);
        durationLabel.setTextFill(Color.WHITE);
        Font font2 = Font.font("System Bold", 22.0);
        durationLabel.setFont(font2);

        AnchorPane.setLeftAnchor(durationLabel, 790.0);
        AnchorPane.setTopAnchor(durationLabel, 55.0);
        AnchorPane.setLeftAnchor(nameLabel, 145.0);
        AnchorPane.setTopAnchor(nameLabel, 25.0);
        AnchorPane.setLeftAnchor(artistLabel, 163.0);
        AnchorPane.setTopAnchor(artistLabel, 55.0);

        anchorPane.getChildren().addAll(imageView, imageView2, nameLabel, artistLabel, durationLabel);
        vbox.getChildren().add(anchorPane);

        return vbox;
    }
}
