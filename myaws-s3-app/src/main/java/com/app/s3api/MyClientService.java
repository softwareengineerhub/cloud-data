/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.s3api;

import com.amazonaws.services.s3.AmazonS3;

/**
 *
 * @author kerch
 */
public interface MyClientService {
    
        public AmazonS3 createClient(String accesskeyid, String secretaccesskey, String region);
    
}
