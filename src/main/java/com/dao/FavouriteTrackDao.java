/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;

import com.conection.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ngoch
 */
public class FavouriteTrackDao {

    private Connection cn = null;

    public FavouriteTrackDao() {
        Connect conn = new Connect();
        this.cn = conn.getCn();
    }

    public boolean isFavouriteTrack(Integer userId, Integer trackId) {
        try {
            String query = "SELECT * FROM favourites_track WHERE user_id = ? AND track_id = ?";
            PreparedStatement preparedStatement = this.cn.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, trackId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean add(Integer userId, Integer trackId) {
        try {
            String query = "INSERT INTO favourites_track(user_id, track_id) VALUE (?, ?)";
            PreparedStatement preparedStatement = this.cn.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, trackId);
            Integer rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FavouriteTrackDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean del(Integer userId, Integer trackId) {
        try {
            String query = "DELETE FROM favourites_track WHERE user_id = ? AND track_id = ?";
            PreparedStatement preparedStatement = this.cn.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, trackId);
            Integer rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FavouriteTrackDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
