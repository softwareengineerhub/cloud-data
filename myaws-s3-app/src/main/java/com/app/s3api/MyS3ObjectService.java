/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.s3api;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import java.io.File;
import java.io.InputStream;

/**
 *
 * @author kerch
 */
public interface MyS3ObjectService {
    
    public void createObject(String bucketNam, String fileName, String filedir);
    
    public void createObject(String bucketNam, String zoneId, InputStream in, ObjectMetadata om);
    
    public void deleteObject(String bucketNam, String fileName);
    
    public S3Object readObject(String bucketNam, String fileName);
    
    public byte[] readObjectContent(String bucketNam, String fileName);
}
