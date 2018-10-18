/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.auth;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 *
 * @author kerch
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/account.jsp"})
public class AuthFilter implements Filter {
    private FilterConfig filterConfig;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig=filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("@Before ");
        //chain.doFilter(request, response);
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        if(session==null || session.getAttribute("user")==null){
          HttpServletResponse resp = (HttpServletResponse) response; 
          resp.sendRedirect("login.jsp");
        }else{
            chain.doFilter(request, response);
        }
        //System.out.println("@After ");
    }

    @Override
    public void destroy() {

    }
    
    
    
}
