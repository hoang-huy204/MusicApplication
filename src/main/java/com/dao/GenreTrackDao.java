package com.dao;

import com.conection.Connect;
import com.model.GenreTrack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenreTrackDao {

    private Connection cn = null;

    public GenreTrackDao() {
        Connect conn = new Connect();
        this.cn = conn.getCn();
    }

    public ArrayList<GenreTrack> select_all_genreTrack(String status) {
        try {
            ArrayList<GenreTrack> list_genreTrack = new ArrayList<>();
            cn.setAutoCommit(false);
            String query = "select * from genre_track where status= ?";
            PreparedStatement preparedStatement = cn.prepareCall(query);
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                GenreTrack genreTrack = new GenreTrack(id, name);
                list_genreTrack.add(genreTrack);
            }
            cn.commit();
            return list_genreTrack;
        } catch (SQLException ex) {
            Logger.getLogger(GenreTrackDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getNameById(Integer id) {
        try {
            String query = "select name from genre_track where id = ? AND status= ?";
            PreparedStatement preparedStatement = cn.prepareCall(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, "active");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                return name;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenreTrackDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public Boolean add(GenreTrack genre) {
        try {
            cn.setAutoCommit(false);
            String query = "INSERT INTO genre_track(name,status) VALUE (?, ?)";
            PreparedStatement preparedStm = cn.prepareCall(query);
            preparedStm.setString(1, genre.getName());
            preparedStm.setString(2, "active");
            Integer rowAffect = preparedStm.executeUpdate();
            if (rowAffect > 0) {
                cn.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean update(GenreTrack genre) {
        try {
            cn.setAutoCommit(false);
            String query = "UPDATE genre_track SET name = ?,status = ? WHERE id = ?";
            PreparedStatement preparedStm = cn.prepareCall(query);
            preparedStm.setString(1, genre.getName());
            preparedStm.setString(2, "active");
            preparedStm.setInt(3, genre.getId());
            Integer rowAffect = preparedStm.executeUpdate();
            if (rowAffect > 0) {
                cn.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean delete(Integer id) {
        try {
            cn.setAutoCommit(false);
            String query = "DELETE FROM genre_track WHERE id = ?";
            PreparedStatement preparedStm = cn.prepareCall(query);
            preparedStm.setInt(1, id);
            Integer rowAffect = preparedStm.executeUpdate();
            if (rowAffect > 0) {
                cn.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
