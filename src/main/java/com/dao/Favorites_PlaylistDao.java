package com.dao;

import com.conection.Connect;
import com.model.Favourites_Playlist;
import com.model.Playlist;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Favorites_PlaylistDao {
    private Connection cn = null;

    public Favorites_PlaylistDao() {
        Connect conn = new Connect();
        this.cn = conn.getCn();
    }

    public ArrayList<Playlist> selectAllFavorites_PlaylistByAuthId(Integer auth_id, String status) {
        ArrayList<Playlist> Favourites_Playlist_List = new ArrayList<>();
        try {
            cn.setAutoCommit(false);
            String query = "select pl.* " +
                    "from playlist as pl " +
                    "inner join favourites_playlist as fp " +
                    "on pl.id = fp.playlist_id " +
                    "where fp.user_id = ? and pl.status = ?";
            PreparedStatement preparedStatement_selectAllFavorites_PlaylistByAuthId = cn.prepareCall(query);
            preparedStatement_selectAllFavorites_PlaylistByAuthId.setInt(1, auth_id);
            preparedStatement_selectAllFavorites_PlaylistByAuthId.setString(2, status);
            ResultSet resultSet = preparedStatement_selectAllFavorites_PlaylistByAuthId.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String image = resultSet.getString("image");
                String description = resultSet.getString("description");
                Integer total_track = resultSet.getInt("total_track");
                Timestamp timestamp_create_at = resultSet.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Playlist playlist = new Playlist(id, name, image, description, total_track, create_at);
                Favourites_Playlist_List.add(playlist);
            }
            if (!Favourites_Playlist_List.isEmpty()) {
                cn.commit();
                return Favourites_Playlist_List;
            } else {
                cn.rollback();
            }
        } catch (SQLException e) {
            Logger.getLogger(Favorites_PlaylistDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

}
