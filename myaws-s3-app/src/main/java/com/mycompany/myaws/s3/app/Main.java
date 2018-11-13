/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myaws.s3.app;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteBucketRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.RestoreObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kerch
 */
public class Main {
    
    public static void main(String[] args) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAJJL5BXPVGTQVLO3A", "y97Am2q44IG4QBYW0gs3eli0+blcCOW/4V6RE1+o");
        AmazonS3 client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion("eu-central-1").build();
        
        List<Bucket> allBuckets = client.listBuckets();
        for(Bucket bucket: allBuckets){
            System.out.println(bucket);
        }
        
        //Bucket createdBucket=client.createBucket("mys3-hello-bucket");
        //System.out.println(createdBucket);
        
        //client.deleteBucket(new DeleteBucketRequest("mys3-hello-bucket"));
        
        //PutObjectRequest putObjectRequest = new PutObjectRequest("mys3-my-bucket-01", "pom.xml", new File("C:\\JavaProjects\\2018\\myaws-s3-app\\pom.xml"));
        //client.putObject(putObjectRequest);
        
        
        PutObjectRequest putObjectRequest = new PutObjectRequest("mys3-my-bucket-01", "pom.xml", new File("C:\\JavaProjects\\2018\\myaws-s3-app\\pom.xml"));
        client.putObject(putObjectRequest);
        
        
        
        S3Object s3Object = client.getObject(new GetObjectRequest("mys3-my-bucket-01", "pom.xml"));
        ObjectMetadata meta=s3Object.getObjectMetadata();
        
        //long t1=1;
        //int t2=1;        
        
        long n=meta.getContentLength();          
        byte[] content = new byte[(int)n];        
        //byte[] content = new byte[];        
        try(InputStream in = s3Object.getObjectContent()){
            in.read(content);
            try(OutputStream out = new FileOutputStream("myawspom.xml")){
                out.write(content);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        
        client.deleteObject(new DeleteObjectRequest("mys3-my-bucket-01", "pom.xml"));
        
        
    }
    
    
}
