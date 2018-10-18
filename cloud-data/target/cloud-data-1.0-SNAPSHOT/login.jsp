<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
         <%@include file="images/cloudImageStyle.css"%>
    </head>
    <body>
    <center>
        <table style="padding-top: 20px;">
            <tr>
                <td>
                    <h1>Login</h1>
                    <br>
                    <form method="POST" action="LoginServlet">
                        <p>Username:<br>
                            <input type="text" name="username"/> 
                        </p>
                        <p>Password:<br>
                            <input type="password" name="password"/> 
                        </p>
                        <input type="submit" value="Submit"/> 
                    </form>
                </td>
                <td>
                    
            </tr>
        </table>
    </center>
    </body>
</html>
