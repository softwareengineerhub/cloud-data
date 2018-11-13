/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.s3api;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.Region;
import java.util.List;

/**
 *
 * @author kerch
 */
public interface MyS3BucketService {
    
    public void createBucket(String name);
    
    public void deleteBucket(String name);
    
    public List<Bucket> getAllBucketsByZone(String zoneId);
    
    public List<Bucket> getAllBuckets();
    
    public List<String> getAllZones();
}
