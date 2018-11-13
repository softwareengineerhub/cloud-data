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

/**
 *
 * @author kerch
 */
public class MyClientServiceImpl implements MyClientService {

    
    
    

    @Override
    public AmazonS3 createClient(String accesskeyid, String secretaccesskey, String region) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accesskeyid, secretaccesskey);
        AmazonS3 client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(region).build();
        return client;
    }

}
