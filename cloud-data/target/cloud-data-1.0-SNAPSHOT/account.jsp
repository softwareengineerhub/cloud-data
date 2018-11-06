<%-- 
    Document   : account
    Created on : Oct 16, 2018, 7:13:54 PM
    Author     : kerch
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <%@include file="images/cloudImageStyle.css"%>
    </head>
    <body>
        <h1>Account page!, <%=request.getSession().getAttribute("user")%></h1>
        <br/>
        <a href="LoginServlet">Logout</a>
        <br>
        <%
            String action=request.getParameter("action");
            if(action!=null){
        %>
        <%="File upload result: "+request.getParameter("action")%>
        <%
            }
        %>
                    
        Upload file:<br>
        <form method="POST" action="UploadServlet" enctype="multipart/form-data">
            <input type="file" name="data" />
            <input type="submit"/>            
        </form>
        
        <br><br><br>        
        <a href="DownloadServlet">Download latest file:</a>
        
    </body>
</html>
