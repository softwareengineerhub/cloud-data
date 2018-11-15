/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.metadata;

import com.cloud.auth.FakeDS;
import com.cloud.pojo.FileData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author kerch
 */
public class MetaDataDAOImpl implements MetaDataDAO {

    private DataSource ds;

    public MetaDataDAOImpl() {
        try {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/cloudDataDataSource");
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        //ds = new FakeDS();
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
                            "INSERT INTO files(name, type, size, device, last_updated, created_date, user_id) "
                    + " values(?,?,?,?,?,?,(SELECT id FROM cloud_data.users where username=?))");
            
            //System.out.println("fileName="+fileName);
            
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
            PreparedStatement ps = c.prepareStatement("UPDATE FILES set size=?, device=?, last_updated=?" 
                    + "WHERE user_id= (SELECT id FROM cloud_data.users where username=?) and name=? and type=?");
            //ps.setString(1, user);
          //  ps.setString(1, fileName);
          //  ps.setString(2, fileMask);
            ps.setInt(1, (int)size);
            ps.setString(2, device);
            ps.setTimestamp(3, new Timestamp(currentDate));          
            ps.setString(4, user);
            ps.setString(5, fileName);
            ps.setString(6, fileMask);
            ps.execute();
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteFile(int id) {
        try(Connection c = ds.getConnection()){
            PreparedStatement ps = c.prepareStatement("DELETE FROM files WHERE id=?");
            ps.setInt(1, id);    
            ps.execute();    
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<FileData> getAll(String userName) {
    List<FileData> files = new ArrayList<FileData>();
    try(Connection c = ds.getConnection()){
        PreparedStatement ps = c.prepareStatement("SELECT * FROM files WHERE user_id=(SELECT id FROM users WHERE username=?)");
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            FileData fd = new FileData();
            String fileName = rs.getString("name");
            int id = rs.getInt("id");
            long size = rs.getLong("size");
            String fileMask = rs.getString("type");
            Timestamp createdDate = rs.getTimestamp("created_date");
            fd.setFileName(fileName);
            fd.setFileMask(fileMask);
            fd.setSize(size);
            fd.setCreatedDate(createdDate);
            files.add(fd);
            fd.setId(id);
        }
        return files;
    }   catch (SQLException ex) {
     throw new RuntimeException(ex);
    }
    }

//    @Override
//    public void readFile(String fileName, String fileMask, String user) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

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

    @Override
    public boolean bucketExists(String user) {
        try(Connection c = ds.getConnection()){
            PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM files WHERE user_id=(SELECT id FROM cloud_data.users where username=?)");
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1) >0;
            } 
            return false;
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public FileData getFileMetaDataBuFileId(int id) {
        FileData fileData = new FileData();
        try(Connection c = ds.getConnection()){
            PreparedStatement ps = c.prepareStatement("SELECT * FROM files where id=?");
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                fileData.setFileName(rs.getString("name"));
                fileData.setFileMask(rs.getString("type"));
            }
            return fileData;
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    
}
