/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.awsservice;

import com.app.s3api.MyS3ObjectService;
import com.app.s3api.MyS3OblectServiceImpl;
import com.cloud.awsservice.resource.reader.ResourceReader;
import com.cloud.metadata.MetaDataDAO;
import com.cloud.metadata.MetaDataDAOImpl;
import com.cloud.pojo.FileData;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kerch
 */
@WebServlet(name = "AWSDownloadServlet", urlPatterns = {"/AWSDownloadServlet"})
public class AWSDownloadServlet extends HttpServlet {

     private MetaDataDAO dao;
     private ResourceReader resourceReader;
     
    @Override
    public void init() throws ServletException {
        dao = new MetaDataDAOImpl();
        resourceReader = new ResourceReader();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("id");
        int id = Integer.valueOf(parameter);
        FileData fileData = dao.getFileMetaDataBuFileId(id);
        HttpSession session = req.getSession();
        String bucketName = session.getAttribute("user") + "cloud-data-app-tomcat";
        
        MyS3ObjectService myS3ObjectService = new MyS3OblectServiceImpl(resourceReader.getAccesskeyid(), resourceReader.getSecretaccesskey(), resourceReader.getRegion());
        byte[] content = myS3ObjectService.readObjectContent(bucketName, fileData.getFileName()+"."+fileData.getFileMask());
        
        
        
        resp.setContentType("application/"+fileData.getFileMask());
        resp.setHeader("Content-disposition","inline; filename='"+fileData.getFileName()+"'");
        resp.getOutputStream().write(content);
    }
    
    

    
}
