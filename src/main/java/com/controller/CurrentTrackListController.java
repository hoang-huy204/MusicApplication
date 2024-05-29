/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller;

import static com.controller.LoginController.isAuthVip;
import com.dao.AuthDao;
import com.dao.TrackDao;
import com.model.Auth;
import com.model.Track;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import com.controller.MusicControlController;
import java.util.Random;
import javafx.application.Platform;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ngoch
 */
public class CurrentTrackListController implements Initializable {

    private TrackDao trackDao = new TrackDao();
    private AuthDao authDao = new AuthDao();

    private ArrayList<Track> trackListData = new ArrayList<>();

    @FXML
    private ImageView currentSongmage;
    @FXML
    private VBox currentTrackList;
    @FXML
    private ScrollPane trackListScrollPane;

    @FXML
    private FontAwesomeIconView favouriteBtnIcon;

    @FXML
    private FontAwesomeIconView playBtnIcon;

    @FXML
    private StackPane playBtnWrapper;

    @FXML
    private Label currentSongName;

    @FXML
    private Label currentArtistName;

    private Boolean isActivePlayBtn = false;
    private Boolean isActiveFavouriteBtn = false;

    public static MusicControlController musicControlController;

    private String currentPath;

    private Integer currentSongIndex = 0;
    private Integer songListQty = 0;

    private Boolean isfirstTrack = true;

    private MouseEvent mouseEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false, false, false, false, true, false, false, true, false, false, null);

    public static CurrentTrackListController currentTrackListController;

    private Integer playListId;

    public CurrentTrackListController() {
    }

    public CurrentTrackListController(Integer playListId) {
        this.playListId = playListId;
//        System.out.println("Hello playlist");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        currentTrackListController = this;

        Platform.runLater(() -> {
            currentPath = System.getProperty("user.dir");
            currentPath = currentPath.replace("\\", "/");

//            this.trackListData = trackDao.selectAllTrack("active");
//            this.trackListData = trackDao.selectAllTrackFavourite(5, "active");
            printTracks(0);
        });

        trackListScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        trackListScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void printTracks(Integer favouriteTracksUserId) {
        this.currentTrackList.getChildren().clear();
        Integer vipStatus = 0;
        if (isAuthVip) {
            vipStatus = 1;
        }
        if (favouriteTracksUserId == 0) {
            if (playListId != null) {
                this.trackListData = trackDao.selectAllTrackPlaylistId(playListId, "active");
            } else {
                if (vipStatus == 1) {
                    this.trackListData = trackDao.selectAllTrack("active");
                } else {
                    this.trackListData = trackDao.selectAllTrackNoVip();
                }
            }
        } else {
            this.trackListData = trackDao.selectAllTrackFavourite(favouriteTracksUserId, "active", vipStatus);
        }
        songListQty = this.trackListData.size();

        for (Track item : this.trackListData) {
            ArrayList<Auth> authList = authDao.getAuthByTrackId(item.getId());
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

            final String finalArtistNames = artistNames;

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefSize(810, 59);
            anchorPane.setPadding(new Insets(0, 0, 10, 0));
            anchorPane.setCursor(Cursor.HAND);

            FontAwesomeIconView iconView = new FontAwesomeIconView();
            iconView.setGlyphName("PLAY");
            iconView.setLayoutX(12.0);
            iconView.setLayoutY(33.0);
            iconView.setSize("14");

            Label label1 = new Label(item.getName());
            label1.setLayoutX(59.0);
            label1.setLayoutY(7.0);
            label1.setPrefSize(261.0, 28.0);
            label1.setFont(new Font(19.0));

            Label label2 = new Label(item.getSingers());
            label2.setLayoutX(360.0);
            label2.setLayoutY(14.0);
            label2.setPrefSize(304.0, 28.0);
            label2.setFont(new Font(19.0));

            Label label3 = new Label(item.getDurationString());
            label3.setLayoutX(702.0);
            label3.setLayoutY(18.0);
            label3.setPrefSize(73.0, 22.0);
            label3.setTextFill(javafx.scene.paint.Color.valueOf("#616060"));
            label3.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
            label3.setFont(new Font(14.0));

            Label label4 = new Label(artistNames);
            label4.setLayoutX(60.0);
            label4.setLayoutY(27.0);
            label4.setPrefSize(261.0, 28.0);
            label4.setTextFill(javafx.scene.paint.Color.valueOf("#737272"));
            label4.setFont(new Font(15.0));

            anchorPane.setOnMouseClicked(event -> {
                iconView.setGlyphName("PAUSE");
                iconView.setFill(Color.GREEN);
                label1.setTextFill(Color.GREEN);
                currentArtistName.setText(finalArtistNames);
                currentSongName.setText(item.getName());
                for (Node node : this.currentTrackList.getChildren()) {
                    if (node instanceof AnchorPane) {
                        AnchorPane otherAnchorPane = (AnchorPane) node;
                        FontAwesomeIconView otherIconView = (FontAwesomeIconView) otherAnchorPane.getChildren().get(0);
                        Label otherLabel1 = (Label) otherAnchorPane.getChildren().get(1);
                        if (!otherLabel1.getText().equals(label1.getText())) {
                            otherIconView.setGlyphName("PLAY");
                            otherIconView.setFill(Color.BLACK);
                            otherLabel1.setTextFill(Color.BLACK);
                        }
                    }
                }

                musicControlController.currentIndexSong(item, finalArtistNames);

                Image image = new Image("file:" + currentPath + "/src/main/resources/application/static/images/tracks/" + item.getImage());
                currentSongmage.setImage(image);
            });

            if (isfirstTrack) {
                iconView.setGlyphName("PLAY");
                iconView.setFill(Color.GREEN);
                label1.setTextFill(Color.GREEN);
                musicControlController.currentIndexSong(item, artistNames);

                Image image = new Image("file:" + currentPath + "/src/main/resources/application/static/images/tracks/" + item.getImage());
                currentSongmage.setImage(image);

                currentArtistName.setText(artistNames);
                currentSongName.setText(item.getName());

                isfirstTrack = false;
            }

            anchorPane.getChildren().addAll(iconView, label1, label2, label3, label4);
            this.currentTrackList.getChildren().add(anchorPane);
        }
    }

    public void handleChangeSongBtn(Integer step) {
        if (step == 0) {
            Random random = new Random();
            int randomNumber = random.nextInt(this.songListQty);

            while (randomNumber == this.currentSongIndex) {
                randomNumber = random.nextInt(this.songListQty);
            }

            this.currentSongIndex = randomNumber;
        } else {
            this.currentSongIndex += step;
        }
        if (this.currentSongIndex < 0) {
            this.currentSongIndex = this.songListQty - 1;
        } else if (this.currentSongIndex >= this.songListQty) {
            this.currentSongIndex = 0;
        }

        for (int i = 0; i < this.currentTrackList.getChildren().size(); i++) {
            Node node = this.currentTrackList.getChildren().get(i);
            if (node instanceof AnchorPane) {
                AnchorPane anchorPane = (AnchorPane) node;

                if (i == this.currentSongIndex) {
                    anchorPane.fireEvent(mouseEvent);
                }
            }
        }
    }
}
