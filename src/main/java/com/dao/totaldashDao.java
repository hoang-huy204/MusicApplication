/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dao;

import com.conection.Connect;
import java.sql.Connection;

/**
 *
 * @author daoqu
 */
public class totaldashDao {
    private Connection cn = null;

    public totaldashDao() {
         Connect conn = new Connect();
        this.cn = conn.getCn();
    }
}
