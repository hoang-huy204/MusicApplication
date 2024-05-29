package com.dao;

import com.conection.Connect;
import com.model.Package;
import com.model.Payment;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PackageDao {

    private Connection cn;

    public PackageDao() {
        Connect conn = new Connect();
        this.cn = conn.getCn();
    }

    public ArrayList<Package> select_all_package(String active) {
        ArrayList<Package> packages = new ArrayList<>();
        try {
            cn.setAutoCommit(false);
            String query = "select * from package where status =?";
            PreparedStatement preparedStatement = cn.prepareCall(query);
            preparedStatement.setString(1, active);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String packageName = resultSet.getString("package_name");
                BigDecimal packagePrice = resultSet.getBigDecimal("package_price");
                Integer validity = resultSet.getInt("validity");
                Timestamp timestamp_create_at = resultSet.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Package package_app = new Package(id, packageName, packagePrice, validity, create_at);
                packages.add(package_app);
            }
            cn.commit();
            return packages;
        } catch (SQLException ex) {
            Logger.getLogger(PackageDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Package select_PackageByAuth(Integer authId, String status) {
        try {
            cn.setAutoCommit(false);
            Package _package = null;
            String query = "select * " +
                    "from package " +
                    "inner join package_auth " +
                    "on package.id = package_auth.package_id " +
                    "where package_auth.auth_id = ? and package.status = ?";
            PreparedStatement preparedStatement = cn.prepareCall(query);
            preparedStatement.setInt(1, authId);
            preparedStatement.setString(2, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("package_name");
                BigDecimal price = resultSet.getBigDecimal("package_price");
                Integer validity = resultSet.getInt("validity");
                _package = new Package(id, name, price, validity);
            }
            cn.commit();
            return _package;
        } catch (SQLException ex) {
            Logger.getLogger(PackageDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Boolean add(Package newPackage) {
        try {
            String query = "INSERT INTO package(package_name, package_price) VALUE (?, ?)";
            PreparedStatement preparedStm = cn.prepareCall(query);
            preparedStm.setString(1, newPackage.getPackageName());
            preparedStm.setBigDecimal(2, newPackage.getPackagePrice());
            Integer rowAffect = preparedStm.executeUpdate();
            if (rowAffect > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean update(Package updatePackage) {
        try {
            cn.setAutoCommit(false);
            String query = "UPDATE package SET package_name = ?,package_price = ? WHERE id = ?";
            PreparedStatement preparedStm = cn.prepareCall(query);
            preparedStm.setString(1, updatePackage.getPackageName());
            preparedStm.setBigDecimal(2, updatePackage.getPackagePrice());
            preparedStm.setInt(3, updatePackage.getId());
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

    public Boolean changeStatus(String status, Integer id) {
        try {
            cn.setAutoCommit(false);
            String query = "UPDATE package SET status = ? WHERE id = ?";
            PreparedStatement prepareStm = this.cn.prepareStatement(query);
            prepareStm.setString(1, status);
            prepareStm.setInt(2, id);
            Integer rowAffect = prepareStm.executeUpdate();
            if (rowAffect > 0) {
                cn.commit();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean delete(Integer id) {
        try {
            cn.setAutoCommit(false);
            String query = "DELETE FROM package WHERE id = ?";
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
