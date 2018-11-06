/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.metadata;

/**
 *
 * @author kerch
 */
public interface MetaDataDAO {
    
    public boolean fileExists(String fileName, String fileMask, String user);
    
    public void createFile(String fileName, String fileMask, long size, String device, long currentDate, long createdDate, String user);
    
    public void updateFile(String fileName, String fileMask, long size, String device, long currentDate, String user);
    
    public void deleteFile(String fileName, String fileMask, String user);
    
    //public void readFile(String fileName, String fileMask, String user);
    
    
}
