
package com.conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public Connection cn = null;

    public Connect() {
        this.getConnection();
    }

    public Connection getCn() {
        return cn;
    }

    public void setCn(Connection cn) {
        this.cn = cn;
    }

    public void getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/music_application", "root", "");
        } catch (SQLException ex) {
            System.out.println("Error DriverManager connect");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        if (this.cn != null) {
            try {
                this.cn.close();
            } catch (SQLException ex) {
                System.out.println("Error close connect");
            }
        }
    }
}
