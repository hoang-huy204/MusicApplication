/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;

import com.conection.Connect;
import com.model.Playlist;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daoqu
 */
public class TrackPlaylist {

    private Connection cn = null;

    public TrackPlaylist() {
        Connect conn = new Connect();
        this.cn = conn.getCn();
    }

    public ArrayList<Playlist> getAll(String statusIn, Integer vip) {
        ArrayList<Playlist> PlaylistList = new ArrayList<>();
        try {
            String query = "select * from playlist WHERE status = ?";
            if (vip == 0) {
                query = "select * from playlist WHERE status = ? AND vip = 0";
            }
            PreparedStatement prepareStm = this.cn.prepareStatement(query);
            prepareStm.setString(1, statusIn);
            ResultSet rs = prepareStm.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String des = rs.getString("description");
                String image = rs.getString("image");
                Integer totaltrack = rs.getInt("total_track");
                Integer auid = rs.getInt("auth_id");
                String status = rs.getString("status");
                Timestamp timestamp_create_at = rs.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Playlist pl = new Playlist(id, name, image, des, totaltrack, auid, status, create_at);
                PlaylistList.add(pl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrackPlaylist.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return PlaylistList;
    }

    public ArrayList<Playlist> timkiem(Integer index, String characters, Integer vip) {
        ArrayList<Playlist> PlaylistList = new ArrayList<>();
        try {
            String query = "select * from playlist WHERE name LIKE ? AND status = \"active\" ORDER BY name LIMIT ?, 4";
            if (vip == 0) {
                query = "select * from playlist WHERE name LIKE ? AND vip = 0 AND status = \"active\" ORDER BY name LIMIT ?, 4";
            }
            PreparedStatement prepareStm = this.cn.prepareStatement(query);
            prepareStm.setString(1, "%" + characters + "%");
            prepareStm.setInt(2, index);
            ResultSet rs = prepareStm.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String des = rs.getString("description");
                String image = rs.getString("image");
                Integer totaltrack = rs.getInt("total_track");
                Integer auid = rs.getInt("auth_id");
                String status = rs.getString("status");
                Timestamp timestamp_create_at = rs.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Playlist pl = new Playlist(id, name, image, des, totaltrack, auid, status, create_at);
                PlaylistList.add(pl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrackPlaylist.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return PlaylistList;
    }
}
