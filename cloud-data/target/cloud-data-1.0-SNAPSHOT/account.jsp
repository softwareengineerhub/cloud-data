<%-- 
    Document   : account
    Created on : Oct 16, 2018, 7:13:54 PM
    Author     : kerch
--%>

<%@page import="com.cloud.pojo.FileData"%>
<%@page import="java.util.List"%>
<%@page import="com.cloud.metadata.MetaDataDAOImpl"%>
<%@page import="com.cloud.metadata.MetaDataDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style> 
            <%@include file="images/cloudImageStyle.css"%>
            <%@include file="images/table.css"%>
        </style>
    </head>
    <body>
        <%-----------------------------1--------------------------------------%>
        <h1>Account page!, <%=request.getSession().getAttribute("user")%></h1>
        <br/>
        <a href="LoginServlet">Logout</a>
        <br>
        <%-----------------------------1--------------------------------------%>
        <%-----------------------##############-------------------------------%>
        <%-----------------------------2--------------------------------------%>
        <%
            String action = request.getParameter("action");
            if (action != null) {
        %>
        <%="File upload result: " + request.getParameter("action")%>
        <%
            }
        %>
        Upload file:<br>
        <form method="POST" action="UploadServlet" enctype="multipart/form-data">
            <input type="file" name="data" />
            <input type="submit" value="SAVE/UPDATE"/>            
        </form>
        <%-------------------------------2------------------------------------%>
        <%-----------------------##############-------------------------------%>
        <%-------------------------------3------------------------------------%>
        <% MetaDataDAO dao = new MetaDataDAOImpl();
            String user = request.getSession().getAttribute("user") + "";
            List<FileData> files = dao.getAll(user);
        %>
    <center>
        <table>
            <tr>
                <td><p><b>FileName</b></p></td>
                <td><p><b>FileSize</b></p></td>
                <td><p><b>Type</b></p></td>
                <td><p><b>CreationDate</b></p></td>
            </tr>
            <%for (int i = 0; i < files.size(); i++) {%>
            <tr>
                <td><%=files.get(i).getFileName()%></td>
                <td><%=files.get(i).getSize()%></td>
                <td><%=files.get(i).getFileMask()%></td>
                <td><%=files.get(i).getCreatedDateAsString()%></td>
                <td>
                    <form action="DeleteServlet" method="post">
                        <input type="hidden" name="id" value="<%=files.get(i).getId()%>"/>
                        <input type="submit" value="DELETE" >
                    </form>
                </td>
                <td>
                    <form action="DownloadServlet" method="POST">
                        <input type="hidden" name="id" value="<%=files.get(i).getId()%>"/>
                        <input type="submit" value="DOWNLOAD" >
                    </form>
                </td>
            </tr>
    <%
        }
    %>
        </table>
    </center>

    <%-------------------------------3------------------------------------%>

    <br><br><br>        
    <a href="DownloadServlet">Download latest file:</a>

</body>
</html>
