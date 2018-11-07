/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.service;

import com.cloud.metadata.MetaDataDAO;
import com.cloud.metadata.MetaDataDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kerch
 */
@WebServlet(name = "DeleteServlet", urlPatterns = {"/DeleteServlet"})
public class DeleteServlet extends HttpServlet {
    
    private MetaDataDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new MetaDataDAOImpl();
        }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("id");
        int id = Integer.valueOf(parameter);
        dao.deleteFile(id);
        resp.sendRedirect("account.jsp");
    }

   
}
