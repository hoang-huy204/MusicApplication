package com.dao;

import com.conection.Connect;
import com.model.Auth;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.conection.Connect;

import java.sql.*;

/**
 * @author ngoch
 */
public class AuthDao {

    private Connection cn;

    public AuthDao() {
        Connect conn = new Connect();
        this.cn = conn.getCn();
    }

    public ArrayList<Auth> getAuthByTrackId(Integer trackId) {
        ArrayList<Auth> authList = new ArrayList<>();
        try {
            String query = "SELECT a.id, a.email, a.password, a.role, a.image, a.fullname, a.age, a.description, a.status, a.created_at "
                    + "FROM auth AS a JOIN track_artist AS ta ON ta.artist_id = a.id "
                    + "JOIN track AS t on t.id = ta.track_id "
                    + "WHERE t.id = ?";
            PreparedStatement preparedStatement = this.cn.prepareStatement(query);
            preparedStatement.setInt(1, trackId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String image = rs.getString("image");
                String fullname = rs.getString("fullname");
                Integer age = rs.getInt("age");
                String description = rs.getString("description");
                String status = rs.getString("status");
                Timestamp timestamp_create_at = rs.getTimestamp("created_at");
                LocalDateTime created_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
//                Auth row = new Auth(id, email, password, role, image, fullname, age, description, status, created_at);
                Auth row = new Auth(id, email, password, image, fullname, age, description, status, created_at);
                authList.add(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return authList;
    }

    public ArrayList<Auth> selectAllAuthByRole(String role, String status) {
        ArrayList<Auth> auth_List = new ArrayList<>();
        try {
            cn.setAutoCommit(false);
            String query = "SELECT * FROM auth "
                    + "WHERE status = ? and role = ?";
            PreparedStatement preparedStatement_SelectAuth = cn.prepareCall(query);
            preparedStatement_SelectAuth.setString(1, status);
            preparedStatement_SelectAuth.setString(2, role);
            ResultSet resultSet = preparedStatement_SelectAuth.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String image = resultSet.getString("image");
                String fullName = resultSet.getString("fullname");
                Integer age = resultSet.getInt("age");
                String description = resultSet.getString("description");
                Timestamp timestamp_create_at = resultSet.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Auth auth = new Auth(id, email, password, role, image, fullName, age, description, status, create_at);
                auth_List.add(auth);
            }
            if (!auth_List.isEmpty()) {
                cn.commit();
                return auth_List;
            } else {
                cn.rollback();
            }

        } catch (SQLException e) {
            try {
                cn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, e);

            }
            Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public ArrayList<Auth> selectAll(String status) {
        ArrayList<Auth> auth_List = new ArrayList<>();
        try {
            cn.setAutoCommit(false);
            String query = "SELECT * FROM auth "
                    + "WHERE status = ?";
            PreparedStatement preparedStatement_SelectAuth = cn.prepareCall(query);
            preparedStatement_SelectAuth.setString(1, status);
            ResultSet resultSet = preparedStatement_SelectAuth.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String image = resultSet.getString("image");
                String fullName = resultSet.getString("fullname");
                Integer age = resultSet.getInt("age");
                String role = resultSet.getString("role");
                String description = resultSet.getString("description");
                Timestamp timestamp_create_at = resultSet.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Auth auth = new Auth(id, email, password, role, image, fullName, age, description, status, create_at);
                auth_List.add(auth);
            }
            if (!auth_List.isEmpty()) {
                cn.commit();
                return auth_List;
            } else {
                cn.rollback();
            }
        } catch (SQLException e) {
            try {
                cn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, e);

            }
            Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public ArrayList<Auth> selectAuthByRole(String role, String status) {
        ArrayList<Auth> auth_List = new ArrayList<>();
        try {
            cn.setAutoCommit(false);
            String query = "SELECT * FROM auth "
                    + "WHERE status = ? and role = ?"
                    + "ORDER BY RAND() LIMIT 6";
            PreparedStatement preparedStatement_SelectAuth = cn.prepareCall(query);
            preparedStatement_SelectAuth.setString(1, status);
            preparedStatement_SelectAuth.setString(2, role);
            ResultSet resultSet = preparedStatement_SelectAuth.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String image = resultSet.getString("image");
                String fullName = resultSet.getString("fullname");
                Integer age = resultSet.getInt("age");
                String description = resultSet.getString("description");
                Timestamp timestamp_create_at = resultSet.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Auth auth = new Auth(id, email, password, image, fullName, age, description, status, create_at);
                auth_List.add(auth);
            }
            if (!auth_List.isEmpty()) {
                cn.commit();
                return auth_List;
            } else {
                cn.rollback();
            }

        } catch (SQLException e) {
            try {
                cn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, e);

            }
            Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public Auth accountAuthentication(String emailIn, String passwordIn) {
        Auth auth = null;
        try {
            String query = "SELECT * FROM auth"
                    + " WHERE email = ? AND password = SHA1(?) AND status = \"active\"";
            PreparedStatement preparedStatement = this.cn.prepareStatement(query);
            preparedStatement.setString(1, emailIn);
            preparedStatement.setString(2, passwordIn);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String image = rs.getString("image");
                String fullname = rs.getString("fullname");
                Integer age = rs.getInt("age");
                String description = rs.getString("description");
                String status = rs.getString("status");
                Timestamp timestamp_create_at = rs.getTimestamp("created_at");
                LocalDateTime created_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
//                Auth row = new Auth(id, email, password, role, image, fullname, age, description, status, created_at);
                auth = new Auth(id, email, password, role, image, fullname, age, description, status, created_at);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return auth;
    }

    public boolean verify_TokenForArtist(String emailSender, String token) {
        if (emailSender != null && token != null) {
            try {
                cn.setAutoCommit(false);
                String query = "update auth set role = ? where email = ? and token = ?";
                PreparedStatement preparedStatement = cn.prepareCall(query);
                preparedStatement.setString(1, "artist");
                preparedStatement.setString(2, emailSender);
                preparedStatement.setString(3, token);
                Integer row = preparedStatement.executeUpdate();
                if (row > 0) {
                    cn.commit();
                    return true;
                } else {
                    cn.rollback();
                    throw new IllegalArgumentException("Invalid Token");
                }

            } catch (SQLException e) {
                try {
                    cn.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, e);
                }
                Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return false;
    }

    public boolean saveTokenForArtist(String email, String token) {
        if (email != null && token != null) {
            try {
                cn.setAutoCommit(false);
                String queryCheck = "Select * from auth where email = ?";
                PreparedStatement preparedStatement_checkEmail = cn.prepareCall(queryCheck);
                preparedStatement_checkEmail.setString(1, email);
                ResultSet result = preparedStatement_checkEmail.executeQuery();
                if (result.next()) {
                    String query = "update auth set token = ?  where email = ?";
                    PreparedStatement preparedStatement = cn.prepareCall(query);
                    preparedStatement.setString(1, token);
                    preparedStatement.setString(2, email);
                    Integer row = preparedStatement.executeUpdate();
                    if (row > 0) {
                        cn.commit();
                        return true;
                    } else {
                        cn.rollback();
                        return false;
                    }
                } else {
                    cn.rollback();
                    throw new IllegalArgumentException("email not found");
                }
            } catch (SQLException e) {
                try {
                    cn.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, e);
                }
                Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return false;
    }

    public Boolean add(Auth auth) {
        try {
            String query = "INSERT INTO auth(email, password, role, fullname, age, status) VALUE (?, SHA1(?), ?, ?, ?, ?)";
            PreparedStatement preparedStm = cn.prepareCall(query);
            preparedStm.setString(1, auth.getEmail());
            preparedStm.setString(2, auth.getPassword());
            preparedStm.setString(3, auth.getRole());
            preparedStm.setString(4, auth.getFullname());
            preparedStm.setInt(5, auth.getAge());
            preparedStm.setString(6, auth.getStatus());
            Integer rowAffect = preparedStm.executeUpdate();
            if (rowAffect > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean update(Auth auth) {
        try {
            String query = "UPDATE auth SET email = ?, password = SHA1(?), role = ?, fullname = ?, age = ? WHERE id = ?";
            PreparedStatement preparedStm = cn.prepareCall(query);
            preparedStm.setString(1, auth.getEmail());
            preparedStm.setString(2, auth.getPassword());
            preparedStm.setString(3, auth.getRole());
            preparedStm.setString(4, auth.getFullname());
            preparedStm.setInt(5, auth.getAge());
            preparedStm.setInt(6, auth.getId());
            Integer rowAffect = preparedStm.executeUpdate();
            if (rowAffect > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean updateNotPass(Auth auth) {
        try {
            String query = "UPDATE auth SET email = ?, role = ?, fullname = ?, age = ? WHERE id = ?";
            PreparedStatement preparedStm = cn.prepareCall(query);
            preparedStm.setString(1, auth.getEmail());
            preparedStm.setString(2, auth.getRole());
            preparedStm.setString(3, auth.getFullname());
            preparedStm.setInt(4, auth.getAge());
            preparedStm.setInt(5, auth.getId());
            Integer rowAffect = preparedStm.executeUpdate();
            if (rowAffect > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean updateuser(Auth auth) {
        try {
            String query = "UPDATE auth SET email = ?, image = ?, fullname = ?, age = ? WHERE id = ? AND role = ?";
            PreparedStatement preparedStatement = cn.prepareStatement(query);
            preparedStatement.setString(1, auth.getEmail());
            preparedStatement.setString(2, auth.getImage());
            preparedStatement.setString(3, auth.getFullname());
            preparedStatement.setInt(4, auth.getAge());
            preparedStatement.setInt(5, auth.getId());
            preparedStatement.setString(6, auth.getRole());
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean delete(Integer id) {
        try {
            String query = "DELETE FROM auth WHERE id = ?";
            PreparedStatement preparedStm = cn.prepareCall(query);
            preparedStm.setInt(1, id);
            Integer rowAffect = preparedStm.executeUpdate();
            if (rowAffect > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean changeStatus(String status, Integer id) {
        try {
            String query = "UPDATE auth SET status = ? WHERE id = ?";
            PreparedStatement prepareStm = this.cn.prepareStatement(query);
            prepareStm.setString(1, status);
            prepareStm.setInt(2, id);
            Integer rowAffect = prepareStm.executeUpdate();
            if (rowAffect > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Auth getAuthByEmail(String emailIn) {
        try {
            String query = "SELECT * FROM auth WHERE email = ? AND status = \"active\"";
            PreparedStatement preparedStatement = cn.prepareCall(query);
            preparedStatement.setString(1, emailIn);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String image = resultSet.getString("image");
                String fullName = resultSet.getString("fullname");
                Integer age = resultSet.getInt("age");
                String role = resultSet.getString("role");
                String description = resultSet.getString("description");
                Timestamp timestamp_create_at = resultSet.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Auth auth = new Auth(id, email, password, role, image, fullName, age, description, null, create_at);
                return auth;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getUserCount() {
        int userCount = 0;
        try {
            String query = "SELECT COUNT(*) FROM Auth WHERE role ='user'";
            PreparedStatement preparedStm = cn.prepareCall(query);
            ResultSet resultSet = preparedStm.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userCount;
    }

    public int getArtistCount() {
        int artistCount = 0;
        try {
            String query = "SELECT COUNT(*) FROM Auth WHERE role ='artist'";
            PreparedStatement preparedStm = cn.prepareCall(query);
            ResultSet resultSet = preparedStm.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artistCount;
    }

    public boolean updatePassword(String oldPassword, String newPassword, Integer authId) {
//         if (!role.equals("user")) {
//            System.out.println("Bạn không có quyền cập nhật mật khẩu.");
//            return false;
//        }
        try {
            String query = "UPDATE Auth SET  password = SHA1(?) WHERE  password = SHA1(?) and id = ?";
            PreparedStatement preparedStatement = cn.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, oldPassword);
            preparedStatement.setInt(3, authId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
