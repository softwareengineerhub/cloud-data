/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myaws.s3.app;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.s3.model.S3Object;
import com.app.s3api.MyS3BucketService;
import com.app.s3api.MyS3BucketServiceImpl;
import com.app.s3api.MyS3ObjectService;
import com.app.s3api.MyS3OblectServiceImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author kerch
 */
public class MyMain {

    public static void main(String[] args) throws FileNotFoundException, IOException {
//        Properties
        Properties property = new Properties();
        FileReader fr = new FileReader("C:\\JavaProjects\\2018\\myaws-s3-app\\src\\main\\resources\\MyPropertyFile.properties");
        property.load(fr);
        String filedir = property.getProperty("filedir");
        String filename = property.getProperty("filename");
        String bname = property.getProperty("bname");
//        Properties

       // MyS3ObjectService my = new MyS3OblectServiceImpl();
        //my.createObject(bname, filename, filedir);
//        *****************************************
//        File file = new File("C:\\JavaProjects\\2018\\myaws-s3-app\\pom.xml");
//        FileInputStream in = new FileInputStream(file);
//        ObjectMetadata om = new ObjectMetadata();
//        my.createObject(bname, bname, in, om);
//        *****************************************
//        my.deleteObject(bname, filename);
//        S3Object readObject = my.readObject(bname, filename);
//        System.out.println(readObject);
//        *****************************************
//        byte[] readObjectContent = my.readObjectContent(bname, filename);
//        if (readObjectContent != null) {
//            System.out.println(readObjectContent.toString());
//        }


                          //Buckets
           MyS3BucketService msbucket = new MyS3BucketServiceImpl();
           String newBucketName = "backetnumber1999"; 
//         msbucket.createBucket(newBucketName);//if region is not specified method doesnt work
          
           msbucket.deleteBucket(newBucketName);
    }

}
