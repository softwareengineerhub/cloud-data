<%-- 
    Document   : Signup
    Created on : Oct 16, 2018, 5:54:45 PM
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
    <center>
        <table style="padding-top: 20px;">
              <tr>
                  <td>
                    <h1>Please Signup</h1>
                    <br>
                    <form method="POST" action="SignupServlet">
                        <p>Username <br>
                            <input type="text" name="username"/>
                        </p>
                        <p>Email <br>
                            <input type="text" name="email"/>
                        </p>
                        <p>Password <br>
                            <input type="password" name="password1"/>
                        </p>
                        <p>Retype password <br>
                            <input type="password" name="password2"/>
                        </p>
                        <input type="submit"/> 
                    </form>
                  </td>
              </tr>
         </table>
      </center>
    </body>
</html>
