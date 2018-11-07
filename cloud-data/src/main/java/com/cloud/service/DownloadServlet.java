/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kerch
 */
@WebServlet(name = "DownloadServlet", urlPatterns = {"/DownloadServlet"})
public class DownloadServlet extends HttpServlet {
    private static final String FILE_DIR = "C:\\GIT_PROJECTS\\WORK\\cloud-data";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user=req.getSession().getAttribute("user")+"";
        File file = new File(FILE_DIR+"\\"+user);
        File[] files=file.listFiles();
        if(files!=null && files.length!=0){
            //for(){
            
            File fileData = files[0];
            System.out.println("########"+fileData.getName()+"########");
            //int start=fileData.getName().indexOf(".");
            //fileData.getName().substring(start+1);
            String[] array=fileData.getName().split("//.");
            //System.out.println(Arrays.toString(array));
            String fileMask=array[array.length-1];
            resp.setContentType("application/"+fileMask);
            resp.setHeader("Content-disposition","inline; filename='"+fileData.getName()+"'");
            
            try(InputStream in = new FileInputStream(fileData);
                    OutputStream out = resp.getOutputStream()){
                int i=0;
                while((i=in.read())!=-1){
                    out.write(i);
                }
            }
            
            //}
        }
        
    }

}
