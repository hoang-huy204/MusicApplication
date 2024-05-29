package com.dao;

import com.conection.Connect;
import com.model.Playlist;
import com.utils.Utils;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayListDao {

    private Connection cn = null;

    Utils utils = new Utils();
    TrackDao trackDao = new TrackDao();

    public PlayListDao() {
        Connect conn = new Connect();
        this.cn = conn.getCn();
    }

    public ArrayList<Playlist> selectAllPlaylist(String status) {
        ArrayList<Playlist> playList_List = new ArrayList<>();
        try {
            cn.setAutoCommit(false);
            String query = "SELECT pl.*, a.fullname as fullname "
                    + "FROM playlist pl "
                    + "INNER JOIN auth a ON pl.auth_id = a.id "
                    + "WHERE pl.status = ? ";
            PreparedStatement preparedStatement_SelectPlaylist = cn.prepareCall(query);
            preparedStatement_SelectPlaylist.setString(1, status);
            ResultSet resultSet = preparedStatement_SelectPlaylist.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String image = resultSet.getString("image");
                String description = resultSet.getString("description");
                Integer total_track = resultSet.getInt("total_track");
                String fullName = resultSet.getString("fullname");
                Timestamp timestamp_create_at = resultSet.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Playlist playlist = new Playlist(id, name, image, description, total_track, fullName, create_at);
                playList_List.add(playlist);
            }
            if (!playList_List.isEmpty()) {
                cn.commit();
                return playList_List;
            } else {
                cn.rollback();
            }

        } catch (SQLException e) {
            try {
                cn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(PlayListDao.class.getName()).log(Level.SEVERE, null, e);

            }
            Logger.getLogger(PlayListDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public ArrayList<Playlist> selectPlaylist(String status, Integer isVip) {
        ArrayList<Playlist> playList_List = new ArrayList<>();
        try {
            cn.setAutoCommit(false);
            String query = "SELECT pl.*, a.fullname as fullname "
                    + "FROM playlist pl "
                    + "INNER JOIN auth a ON pl.auth_id = a.id "
                    + "WHERE pl.status = ? "
                    + "ORDER BY RAND() LIMIT 6";
            if (isVip == 0) {
                query = "SELECT pl.*, a.fullname as fullname "
                    + "FROM playlist pl "
                    + "INNER JOIN auth a ON pl.auth_id = a.id "
                    + "WHERE pl.status = ? AND pl.vip = 0 "
                    + "ORDER BY RAND() LIMIT 6";
            }
            PreparedStatement preparedStatement_SelectPlaylist = cn.prepareCall(query);
            preparedStatement_SelectPlaylist.setString(1, status);
            ResultSet resultSet = preparedStatement_SelectPlaylist.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String image = resultSet.getString("image");
                String description = resultSet.getString("description");
                Integer total_track = resultSet.getInt("total_track");
                String fullName = resultSet.getString("fullname");
                Timestamp timestamp_create_at = resultSet.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Playlist playlist = new Playlist(id, name, image, description, total_track, fullName, create_at);
                playList_List.add(playlist);
            }
            if (!playList_List.isEmpty()) {
                cn.commit();
                return playList_List;
            } else {
                cn.rollback();
            }

        } catch (SQLException e) {
            try {
                cn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(PlayListDao.class.getName()).log(Level.SEVERE, null, e);

            }
            Logger.getLogger(PlayListDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public Integer insert_playlist(Integer auth_id, Playlist playlist) {
        try {
            cn.setAutoCommit(false);
            String query_checkExist = "select * from playlist where name = ?";
            PreparedStatement preparedStatement_checkExist = cn.prepareCall(query_checkExist);
            preparedStatement_checkExist.setString(1, playlist.getName());
            preparedStatement_checkExist.execute();
            ResultSet resultSet_Playlist = preparedStatement_checkExist.getResultSet();
            if (!resultSet_Playlist.next()) {
                String query = "insert playlist (name,image,description,total_track,auth_id) values (?,?,?,?,?)";
                PreparedStatement preparedStatement_insert_playlist = cn.prepareCall(query);
                preparedStatement_insert_playlist.setString(1, playlist.getName());
                preparedStatement_insert_playlist.setString(2, playlist.getImage());
                preparedStatement_insert_playlist.setString(3, playlist.getDescription());
                preparedStatement_insert_playlist.setInt(4, playlist.getTotal_track());
                preparedStatement_insert_playlist.setInt(5, auth_id);
                preparedStatement_insert_playlist.execute();
                ResultSet resultSet = preparedStatement_insert_playlist.getGeneratedKeys();
                if (resultSet.next()) {
                    Integer playlist_id = resultSet.getInt(1);
                    cn.commit();
                    return playlist_id;
                } else {
                    cn.rollback();
                }
            } else {
                cn.rollback();
                Utils.showNotification("Error", "Playlist name already exist", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlayListDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean update_Playlist(Playlist playlist) {
        try {
            cn.setAutoCommit(false);
            String check_nameExist = "select * from playlist where name = ? and id != ?";
            PreparedStatement preparedStatement_check_nameExist = cn.prepareCall(check_nameExist);
            preparedStatement_check_nameExist.setString(1, playlist.getName());
            preparedStatement_check_nameExist.setInt(2, playlist.getId());
            ResultSet resultSet = preparedStatement_check_nameExist.executeQuery();
            if (resultSet.next()) {
                Utils.showNotification("Warning!", "playlist name exists", Utils.NotificationType.WARNING, javafx.util.Duration.seconds(3));
            } else {
                String update_playlist = "update playlist set name = ? , image = ? , description = ? ,total_track = ? where id = ?";
                PreparedStatement preparedStatement_update_playlist = cn.prepareCall(update_playlist);
                preparedStatement_update_playlist.setString(1, playlist.getName());
                preparedStatement_update_playlist.setString(2, playlist.getImage());
                preparedStatement_update_playlist.setString(3, playlist.getDescription());
                preparedStatement_update_playlist.setInt(4, playlist.getTotal_track());
                preparedStatement_update_playlist.setInt(5, playlist.getId());
                preparedStatement_update_playlist.execute();
                cn.commit();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlayListDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean changeStatus_Playlist(Integer playlist_Id, boolean changeStatus_AllTracks, String status) {
        try {
            cn.setAutoCommit(false);
            if (!changeStatus_AllTracks) {
                String query_ChangeStatus_Playlist = "update playlist set status=? where id = ?";
                PreparedStatement preparedStatement_Delete_Playlist = cn.prepareCall(query_ChangeStatus_Playlist);
                preparedStatement_Delete_Playlist.setString(1, status);
                preparedStatement_Delete_Playlist.setInt(2, playlist_Id);
                preparedStatement_Delete_Playlist.execute();
                cn.commit();
                return true;
            }

            String select_Track_Id = "select track_id from track_playlist where playlist_id = ?";
            PreparedStatement preparedStatement_Select_TrackId = cn.prepareCall(select_Track_Id);
            preparedStatement_Select_TrackId.setInt(1, playlist_Id);
            ResultSet resultSet = preparedStatement_Select_TrackId.executeQuery();
            while (resultSet.next()) {
                String ChangeStatus_Track = "update track set status = ? where id = ?";
                PreparedStatement preparedStatement_ChangeStatus_Track = cn.prepareCall(ChangeStatus_Track);
                preparedStatement_ChangeStatus_Track.setString(1, status);
                preparedStatement_ChangeStatus_Track.setInt(2, resultSet.getInt("track_id"));
                preparedStatement_ChangeStatus_Track.execute();
            }

            String query_ChangeStatus_Playlist = "update playlist set status = ? where id = ?";
            PreparedStatement preparedStatement_ChangeStatus_Playlist = cn.prepareCall(query_ChangeStatus_Playlist);
            preparedStatement_ChangeStatus_Playlist.setString(1, status);
            preparedStatement_ChangeStatus_Playlist.setInt(2, playlist_Id);
            preparedStatement_ChangeStatus_Playlist.execute();

            cn.commit();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(PlayListDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void delete_Playlist(Integer playlistId) {
        try {
            cn.setAutoCommit(false);
            String query_deletePlaylist = "delete from playlist where id = ?";
            PreparedStatement preparedStatement = cn.prepareCall(query_deletePlaylist);
            preparedStatement.setInt(1, playlistId);
            preparedStatement.execute();
            cn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(PlayListDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean delete_PlaylistAndTrack(Integer playlist_Id, boolean delete_AllTracks) {
        try {
            cn.setAutoCommit(false);
            if (!delete_AllTracks) {
                String query_Delete_Playlist_1 = "delete from track_playlist where playlist_id = ?";
                PreparedStatement preparedStatement_Delete_Playlist_1 = cn.prepareCall(query_Delete_Playlist_1);
                preparedStatement_Delete_Playlist_1.setInt(1, playlist_Id);
                preparedStatement_Delete_Playlist_1.execute();

                String query_Delete_Playlist_2 = "delete from favourites_playlist where playlist_id = ?";
                PreparedStatement preparedStatement_Delete_Playlist_2 = cn.prepareCall(query_Delete_Playlist_2);
                preparedStatement_Delete_Playlist_2.setInt(1, playlist_Id);
                preparedStatement_Delete_Playlist_2.execute();

                String query_Delete_Playlist_3 = "delete from playlist where id = ?";
                PreparedStatement preparedStatement_Delete_Playlist_3 = cn.prepareCall(query_Delete_Playlist_3);
                preparedStatement_Delete_Playlist_3.setInt(1, playlist_Id);
                preparedStatement_Delete_Playlist_3.execute();
                cn.commit();
                return true;
            }

            String select_Track_Id = "select track_id from track_playlist where playlist_id = ?";
            PreparedStatement preparedStatement_Select_TrackId = cn.prepareCall(select_Track_Id);
            preparedStatement_Select_TrackId.setInt(1, playlist_Id);
            ResultSet resultSet = preparedStatement_Select_TrackId.executeQuery();
            while (resultSet.next()) {
                String query_Delete_Track_In_Playlist = "delete from track_playlist where track_id = ?";
                PreparedStatement preparedStatement_Delete_Track_In_Playlist = cn.prepareCall(query_Delete_Track_In_Playlist);
                preparedStatement_Delete_Track_In_Playlist.setInt(1, resultSet.getInt("track_id"));
                preparedStatement_Delete_Track_In_Playlist.execute();

                String query_Delete_TrackArtist = "delete from track_artist where track_id = ?";
                PreparedStatement preparedStatement_Delete_TrackArtist = cn.prepareCall(query_Delete_TrackArtist);
                preparedStatement_Delete_TrackArtist.setInt(1, resultSet.getInt("track_id"));
                preparedStatement_Delete_TrackArtist.execute();

                String query_Delete_FavouritesTrack = "delete from favourites_track where track_id = ?";
                PreparedStatement preparedStatement_Delete_FavouritesTrack = cn.prepareCall(query_Delete_FavouritesTrack);
                preparedStatement_Delete_FavouritesTrack.setInt(1, resultSet.getInt("track_id"));
                preparedStatement_Delete_FavouritesTrack.execute();

                String query_DeleteTrack = "delete from track where id = ?";
                PreparedStatement preparedStatement_DeleteTrack = cn.prepareCall(query_DeleteTrack);
                preparedStatement_DeleteTrack.setInt(1, resultSet.getInt("track_id"));
                preparedStatement_DeleteTrack.execute();
            }

            String query_query_Delete_Favourites_Playlist = "delete from favourites_playlist where playlist_id = ?";
            PreparedStatement preparedStatement_Delete_Favourites_Playlist = cn.prepareCall(query_query_Delete_Favourites_Playlist);
            preparedStatement_Delete_Favourites_Playlist.setInt(1, playlist_Id);
            preparedStatement_Delete_Favourites_Playlist.execute();

            String query_Delete_Playlist = "delete from playlist where id = ?";
            PreparedStatement preparedStatement_Delete_Playlist = cn.prepareCall(query_Delete_Playlist);
            preparedStatement_Delete_Playlist.setInt(1, playlist_Id);
            preparedStatement_Delete_Playlist.execute();

            cn.commit();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(PlayListDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int getPlaylistCount() {
        int playlistCount = 0;
        try {
            String query = "SELECT COUNT(*) FROM playlist";
            PreparedStatement preparedStm = cn.prepareCall(query);
            ;
            ResultSet resultSet = preparedStm.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playlistCount;
    }
}
