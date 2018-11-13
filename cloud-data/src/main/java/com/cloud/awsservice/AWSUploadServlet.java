/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.awsservice;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.app.s3api.MyS3BucketService;
import com.app.s3api.MyS3BucketServiceImpl;
import com.app.s3api.MyS3ObjectService;
import com.app.s3api.MyS3OblectServiceImpl;
import com.cloud.metadata.MetaDataDAO;
import com.cloud.metadata.MetaDataDAOImpl;
import com.cloud.metadata.Servise;
import com.cloud.metadata.ServiseImpl;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author kerch
 */
@WebServlet(name = "AWSUploadServlet", urlPatterns = {"/AWSUploadServlet"})
@MultipartConfig
public class AWSUploadServlet extends HttpServlet {
    private MetaDataDAO metaDataDAO = new MetaDataDAOImpl();
    private Servise servise = new ServiseImpl();
    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String user = session.getAttribute("user") + "";
        if(!metaDataDAO.bucketExists(user)){
            makeAwsBucket(user+"cloud-data-app-tomcat");
        }
        
        
        Collection<Part> parts = req.getParts();
        for(Part part: parts){
            String contentType = part.getContentType();
            String[] array = contentType.split("\\/");
            String fileMask = array[1];
            String headerValue=part.getHeader("content-disposition");
            String fileName = servise.fileNameParser(headerValue);
            System.out.println("fileName="+fileName);
            long currentDate = System.currentTimeMillis();
            boolean fileExists = metaDataDAO.fileExists(fileName, fileMask, user);
            if (!fileExists) {                
                metaDataDAO.createFile(fileName, fileMask, part.getSize(), "device", currentDate, currentDate, user);                            
            } else {
                metaDataDAO.updateFile(fileName, fileMask, part.getSize(), "device", currentDate, user);                
            }
            createAwsObject(user+"cloud-data-app-tomcat", fileName, fileMask, part.getInputStream());
        }
        resp.sendRedirect("account.jsp?action=success");
    }
    
    
    private void makeAwsBucketIfNotExists(String user){
        MyS3BucketService myS3BucketService = new MyS3BucketServiceImpl();
        List<Bucket> buckets = myS3BucketService.getAllBuckets();
        for(Bucket bucket: buckets){
            if(bucket.getName().equals(user)){
                return;
            }
        }
        myS3BucketService.createBucket(user);        
    }
    
    private void makeAwsBucket(String user){
        MyS3BucketService myS3BucketService = new MyS3BucketServiceImpl();
        try{
            myS3BucketService.createBucket(user);        
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    private void createAwsObject(String user, String fileName,String fileMask, InputStream in){
        MyS3ObjectService myS3ObjectService = new MyS3OblectServiceImpl();
        myS3ObjectService.createObject(user, fileName+"."+fileMask, in, new ObjectMetadata());
    }
    
    
}
