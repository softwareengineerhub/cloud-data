/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.s3api;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.Region;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kerch
 */
public class MyS3BucketServiceImpl implements MyS3BucketService{
    
    //private Properties property = new Properties();
    private AmazonS3 client;
    //private String accesskeyid;
    //private String secretaccesskey;
    //private String region;
            
    
    public MyS3BucketServiceImpl(){
        try(InputStream in = getClass().getResourceAsStream("/MyPropertyFile.properties");){
            Properties property = new Properties();
            property.load(in);
            String accesskeyid = property.getProperty("accesskeyid");
            String secretaccesskey = property.getProperty("secretaccesskey");
            String region = property.getProperty("region");            
            MyClientService myClientService = new MyClientServiceImpl();
            client = myClientService.createClient(accesskeyid, secretaccesskey, region);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        /*try (FileReader fr = new FileReader("C:\\JavaProjects\\2018\\myaws-s3-app\\src\\main\\resources\\MyPropertyFile.properties")) {
            property.load(fr);
            String accesskeyid = property.getProperty("accesskeyid");
            String secretaccesskey = property.getProperty("secretaccesskey");
            String region = property.getProperty("region");
            
            MyClientService myClientService = new MyClientServiceImpl();
            client = myClientService.createClient(accesskeyid, secretaccesskey, region);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }*/
    }

    @Override
    public void createBucket(String name) {
        System.out.println("name="+name);
       client.createBucket(new CreateBucketRequest(name));
    }

    @Override
    public void deleteBucket(String name) {
       client.deleteBucket(name);
    }

    @Override
    public List<Bucket> getAllBucketsByZone(String zoneId) {
        List<Bucket> newBucketsList = new ArrayList<>();
        List<Bucket> listBuckets = client.listBuckets();
        for (Bucket bucket : listBuckets) {
            String name = bucket.getName();
            String bucketLocation = client.getBucketLocation(name);
            if(bucketLocation.equals(zoneId)){
                newBucketsList.add(bucket);
                return newBucketsList;
            }
        }
       return newBucketsList;  
    }

    @Override
    public List<Bucket> getAllBuckets() {
       List<Bucket> listBuckets = client.listBuckets();
       return listBuckets;
    }

    @Override
    public List<String> getAllZones() {
        List<String> zones = new ArrayList<>();
        zones.add("us-east-2");
        zones.add("us-east-1");
        zones.add("us-west-1");
        zones.add("us-west-2");
        zones.add("ap-south-1");
        zones.add("ap-northeast-3");
        zones.add("ap-northeast-2");
        zones.add("ap-southeast-1");
        zones.add("ap-southeast-2");
        zones.add("ap-northeast-1");
        zones.add("cn-north-1");
        zones.add("cn-northwest-1");
        zones.add("eu-central-1");
        zones.add("eu-west-1");
        zones.add("eu-west-2");
        zones.add("eu-west-3");
        zones.add("sa-east-1");
        return zones;
    }
    
}
