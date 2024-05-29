/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;

import com.conection.Connect;
import com.model.Records;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 *
 * @author ngoch
 */
public class RecordsDao {

    private Connection cn = null;

    public RecordsDao() {
        Connect conn = new Connect();
        this.cn = conn.getCn();
    }

    public ArrayList<Records> selectAllRecordByAuthId(Integer auth_id) {
        ArrayList<Records> recordList = new ArrayList<>();
        try {
            String query = "SELECT * from records where auth_id = ?";
            PreparedStatement prepareStm = this.cn.prepareCall(query);
            prepareStm.setInt(1, auth_id);
            ResultSet rs = prepareStm.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String path = rs.getString("path");
                Timestamp timestamp_create_at = rs.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Records row = new Records(id, name, path, auth_id, create_at);
                recordList.add(row);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return recordList;
    }

    public Boolean add(Records record) {
        try {
            String query = "INSERT INTO records (name, path, auth_id) VALUE (?, ?, ?)";
            PreparedStatement preparedStm = cn.prepareCall(query);
            preparedStm.setString(1, record.getName());
            preparedStm.setString(2, record.getPath());
            preparedStm.setInt(3, record.getAuth_id());
            Integer rowAffect = preparedStm.executeUpdate();
            if (rowAffect > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean delete(Records record) {
        try {
            String query = "DELETE FROM records WHERE id = ?";
            PreparedStatement preparedStm = cn.prepareCall(query);
            preparedStm.setInt(1, record.getId());
            Integer rowAffect = preparedStm.executeUpdate();
            if (rowAffect > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
