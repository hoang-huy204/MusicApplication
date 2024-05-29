package com.dao;

import com.conection.Connect;
import com.model.Package_Auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PackageAuthDao {
    private Connection cn;

    public PackageAuthDao() {
        Connect conn = new Connect();
        this.cn = conn.getCn();
    }

    public boolean extendPremium(Integer package_id, Integer auth_id, LocalDate expiryDate) {
        try {
            cn.setAutoCommit(false);
            String query = "update package_auth set expiration_date = ? where package_id = ? and auth_id = ?";
            PreparedStatement preparedStatement = cn.prepareCall(query);
            preparedStatement.setDate(1, Date.valueOf(expiryDate));
            preparedStatement.setInt(2, package_id);
            preparedStatement.setInt(3, auth_id);
            preparedStatement.execute();
            cn.commit();
            return true;
        } catch (
                SQLException ex) {
            Logger.getLogger(PackageAuthDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean update_Package4User(Integer package_id, Integer auth_id, LocalDate expiryDate) {
        try {
            cn.setAutoCommit(false);
            String query = "insert package_auth(package_id,auth_id,expiration_date) values (?,?,?)";
            PreparedStatement preparedStatement = cn.prepareCall(query);
            preparedStatement.setInt(1, package_id);
            preparedStatement.setInt(2, auth_id);
            preparedStatement.setDate(3, Date.valueOf(expiryDate));
            preparedStatement.execute();
            cn.commit();
            return true;
        } catch (
                SQLException ex) {
            Logger.getLogger(PackageAuthDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean checkAuthPackage(Integer authId) {
        try {
            String query = "SELECT * FROM package_auth WHERE auth_id = ? AND expiration_date >= CURDATE() ORDER BY expiration_date DESC LIMIT 1";
            PreparedStatement preparedStatement = cn.prepareCall(query);
            preparedStatement.setInt(1, authId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (
                SQLException ex) {
            Logger.getLogger(GenreTrackDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Package_Auth select_UserCurrentPackage(Integer authId, String status) {
        try {
            Package_Auth packageAuth = new Package_Auth();
            cn.setAutoCommit(false);
            String query = "select pa.expiration_date,p.package_name " +
                    "from package_auth as pa " +
                    "inner join package as p  " +
                    "on pa.package_id = p.id " +
                    "where pa.auth_id = ? and p.status = ?";
            PreparedStatement preparedStatement = cn.prepareCall(query);
            preparedStatement.setInt(1, authId);
            preparedStatement.setString(2, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                packageAuth.setPackage_Name(resultSet.getString("package_name"));
                packageAuth.setExpiryDate(resultSet.getDate("expiration_date").toLocalDate());
            }
            if (packageAuth.getPackage_Name() != null && packageAuth.getExpiryDate() != null) {
                return packageAuth;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PackageAuthDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean delete_Package(Integer authId) {
        try {
            cn.setAutoCommit(false);
            String query = "delete from package_auth where auth_id = ?";
            PreparedStatement preparedStatement = cn.prepareCall(query);
            preparedStatement.setInt(1, authId);
            preparedStatement.execute();
            cn.commit();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PackageAuthDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
