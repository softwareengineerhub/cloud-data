/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.service;

import com.cloud.metadata.MetaDataDAO;
import com.cloud.metadata.MetaDataDAOImpl;
import com.cloud.metadata.Servise;
import com.cloud.metadata.ServiseImpl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
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
@WebServlet(name = "UploadServlet", urlPatterns = {"/UploadServlet"})
@MultipartConfig
public class UploadServlet extends HttpServlet {

    private static final String FILE_DIR = "C:\\GIT_PROJECTS\\WORK\\cloud-data";
    private MetaDataDAO metaDataDAO = new MetaDataDAOImpl();
    private Servise servise = new ServiseImpl();

    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Part> files = req.getParts();
        //System.out.println("files.size="+files);
        HttpSession session = req.getSession();
        String user = session.getAttribute("user") + "";
        File file = new File(FILE_DIR + "\\" + user);
        if (!file.exists()) {
            file.mkdir();
        }
        for (Part part : files) {            
            String contentType = part.getContentType();
            //application/pdf
            String[] array = contentType.split("\\/");
            String fileMask = array[1];
            
            //content-disposition=form-data; name="data"; filename="Ð?Ð¼Ñ„Ð¸Ñ‚ÐµÐ°Ñ‚Ñ€_R-1_P-12_1506084457_636748706345897818.pdf"
            //Ð?Ð¼Ñ„Ð¸Ñ‚ÐµÐ°Ñ‚Ñ€_R-1_P-12_1506084457_636748706345897818.pdf
            String headerValue=part.getHeader("content-disposition");
            System.out.println(headerValue);
            String fileName = servise.fileNameParser(headerValue);
            System.out.println("AfterParsingFileName=" + fileName);
                    
            //System.out.println(part.getName());
            //System.out.println(part.getSize());
            long currentDate = System.currentTimeMillis();
            boolean fileExists = metaDataDAO.fileExists(fileName, fileMask, user);
            System.out.println("fileExists="+fileExists);
            if (!fileExists) {
                metaDataDAO.createFile(fileName, fileMask, part.getSize(), "device", currentDate, currentDate, user);
            } else {
                metaDataDAO.updateFile(fileName, fileMask, part.getSize(), "device", currentDate, user);
            }
            //user

            //fileName, type=fileMask, size, device, currentDate, createdDate, user   
            try (InputStream in = part.getInputStream();
                    OutputStream out = new FileOutputStream(FILE_DIR + "\\" + user + "\\data" + System.currentTimeMillis() + "." + fileMask);) {
                int i = -1;
                while ((i = in.read()) != -1) {
                    out.write(i);
                }
            }
        }
        resp.sendRedirect("account.jsp?action=success");
    }

}
