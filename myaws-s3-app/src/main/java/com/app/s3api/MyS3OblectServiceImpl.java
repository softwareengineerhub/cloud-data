/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.s3api;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kerch
 */
public class MyS3OblectServiceImpl implements MyS3ObjectService {

    Properties property = new Properties();
    AmazonS3 client;
    {
        try (FileReader fr = new FileReader("C:\\JavaProjects\\2018\\myaws-s3-app\\src\\main\\resources\\MyPropertyFile.properties")) {
            property.load(fr);
            String accesskeyid = property.getProperty("accesskeyid");
            String secretaccesskey = property.getProperty("secretaccesskey");
            String region = property.getProperty("region");
            
            MyClientService myClientService = new MyClientServiceImpl();
            client = myClientService.createClient(accesskeyid, secretaccesskey, region);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
 

    @Override
    public void createObject(String bucketNam, String fileName, String filedir) {
        if (bucketNam != null && fileName != null && filedir != null) {
            filedir = property.getProperty("filedir");
            client.putObject(bucketNam, fileName, new File(filedir));
            System.out.println("File was created");
        }
    }

    @Override
    public void createObject(String bucketNam, String zoneId, InputStream in, ObjectMetadata om ) {
        if(bucketNam!=null&&zoneId!=null&&in!=null&&om!=null){
        client.putObject(bucketNam, zoneId, in, om);
        System.out.println("File with input stream was created");
        }
    }

    @Override
    public void deleteObject(String bucketNam, String fileName){
        if(bucketNam!=null&&fileName!=null){
        client.deleteObject(bucketNam, fileName);
            System.out.println("File was deleted");
        }
    }

    @Override
    public S3Object readObject(String bucketNam, String fileName) {
        if(bucketNam!=null&&fileName!=null){
            return client.getObject(bucketNam, fileName);
        }
        return null;
    }

    @Override
    public byte[] readObjectContent(String bucketNam, String fileName) {
        if(bucketNam!=null&&fileName!=null){
        S3Object object = client.getObject(bucketNam, fileName);
        ObjectMetadata metaData = object.getObjectMetadata();
        long contentLength = metaData.getContentLength();
        byte [] array = new byte [(int)contentLength];
        return array;
        }
      return null;
    }

}
