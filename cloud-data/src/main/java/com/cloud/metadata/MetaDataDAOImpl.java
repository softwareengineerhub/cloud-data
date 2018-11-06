/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.metadata;

import com.cloud.auth.FakeDS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import javax.sql.DataSource;

/**
 *
 * @author kerch
 */
public class MetaDataDAOImpl implements MetaDataDAO {

    private DataSource ds;

    public MetaDataDAOImpl() {
        /*try {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("cloudDataDataSource");
        } catch (NamingException ex) {
            Logger.getLogger(AuthDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        ds = new FakeDS();
    }
    
    @Override
    public void createFile(String fileName, String fileMask, long size, String device, long currentDate, long createdDate, String user) {
        try(Connection c = ds.getConnection()){
            
            //
            
            /*PreparedStatement ps = 
                    c.prepareStatement(
                            "INSERT INTO FILES(name, type, size, device, last_updated, created_date, user_id) "
                    + " values(?,?,?,?,?,?,?)");*/
            
            PreparedStatement ps = 
                    c.prepareStatement(
                            "INSERT INTO FILES(name, type, size, device, last_updated, created_date, user_id) "
                    + " values(?,?,?,?,?,?,(SELECT id FROM cloud_data.users where username=?))");
            
            
            ps.setString(1, fileName);
            ps.setString(2, fileMask);
            ps.setInt(3, (int)size);
            ps.setString(4, device);
            ps.setTimestamp(5, new Timestamp(currentDate));
            ps.setTimestamp(6, new Timestamp(createdDate));            
            ps.setString(7, user);
            ps.execute();
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void updateFile(String fileName, String fileMask, long size, String device, long currentDate, String user) {
        try(Connection c = ds.getConnection()){
            PreparedStatement ps = c.prepareStatement("UPDATE FILES set name=?, type=?, size=?, device=?, last_updated=?," 
                    + " user_id= (SELECT id FROM cloud_data.users where username=?) ");
            //ps.setString(1, user);
            ps.setString(1, fileName);
            ps.setString(2, fileMask);
            ps.setInt(3, (int)size);
            ps.setString(4, device);
            ps.setTimestamp(5, new Timestamp(currentDate));          
            ps.setString(6, user);
            ps.execute();
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteFile(String fileName, String fileMask, String user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void readFile(String fileName, String fileMask, String user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean fileExists(String fileName, String fileMask, String user) {
        try(Connection c = ds.getConnection()){
            System.out.println("fileName="+fileName);
            System.out.println("fileMask="+fileMask);
            System.out.println("user="+user);
            PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM files WHERE name=? and  type=?  and user_id=(SELECT id FROM cloud_data.users where username=?)");
            
            ps.setString(1, fileName);
            ps.setString(2, fileMask);  
            ps.setString(3, user);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1) >0;
            } 
            return false;
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    
}
