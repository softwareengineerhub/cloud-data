/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author kerch
 */
public class AuthDAOImpl implements AuthDAO {

    //@Resource(name="")
    private DataSource ds;

    public AuthDAOImpl() {
        /*try {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("cloudDataDataSource");
        } catch (NamingException ex) {
            Logger.getLogger(AuthDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        ds = new FakeDS();
    }

    @Override
    public void save(String username, String password, String email, String role) {
        try (Connection c = ds.getConnection()) {
            PreparedStatement ps = c.prepareStatement("INSERT INTO users(username, password, email, role) values(?,?,?,?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.setString(4, role);
            ps.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean validateUserPassword(String username, String password) {
        try (Connection c = ds.getConnection()) {
            PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM users WHERE username=? and password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1) == 1;
            }
            return false;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
