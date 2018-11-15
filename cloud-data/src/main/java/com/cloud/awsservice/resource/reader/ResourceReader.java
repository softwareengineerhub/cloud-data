/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.awsservice.resource.reader;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author kerch
 */
public class ResourceReader {
    //   private 

    private final String accesskeyid;
    private final String secretaccesskey;
    private final String region;

    /*public ResourceReader(String accesskeyid, String secretaccesskey, String region) {
        this.accesskeyid = accesskeyid;
        this.secretaccesskey = secretaccesskey;
        this.region = region;
    }*/
    public ResourceReader() {
        try {
            InitialContext ctx = new InitialContext();
            accesskeyid = ctx.lookup("java:comp/env/awsaccesskeyid")+"";
            secretaccesskey = ctx.lookup("java:comp/env/awssecretaccesskey")+"";
            region = ctx.lookup("java:comp/env/awsregion")+"";
            System.out.println("region="+region);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getAccesskeyid() {
        return accesskeyid;
    }

    public String getSecretaccesskey() {
        return secretaccesskey;
    }

    public String getRegion() {
        return region;
    }

}
