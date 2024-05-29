package com.dao;

import com.conection.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrackPlaylistDao {
    private Connection cn = null;

    public TrackPlaylistDao() {
        Connect conn = new Connect();
        this.cn = conn.getCn();
    }

    public boolean insert_playListTrack(Integer track_Id, Integer playList_Id, Integer order) {
        try {
            cn.setAutoCommit(false);
            String query = "insert track_playlist (track_id,playlist_id,_order) values (?,?,?)";
            PreparedStatement preparedStatement = cn.prepareCall(query);
            preparedStatement.setInt(1, track_Id);
            preparedStatement.setInt(2, playList_Id);
            preparedStatement.setInt(3, order);
            preparedStatement.execute();
            cn.commit();
            return true;
        } catch (SQLException ex) {
            try {
                if (cn != null) {
                    cn.rollback();
                }
            } catch (SQLException e) {
                Logger.getLogger(PlayListDao.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return false;
    }
}
