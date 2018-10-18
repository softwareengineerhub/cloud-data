/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.auth;

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
@WebServlet(name = "SignupServlet", urlPatterns = {"/SignupServlet"})
public class SignupServlet extends HttpServlet {

    private AuthDAO authDAO;
    
    @Override
    public void init(){
        authDAO = new AuthDAOImpl();
    }    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        String email = req.getParameter("email");
        String role = "userrole";
        if(password.equals(password2)){
            authDAO.save(username, password, email, role);
            resp.sendRedirect("welcome.jsp");
        } else { 
            resp.sendRedirect("error_signup.jsp");
        }
    }
    
}
