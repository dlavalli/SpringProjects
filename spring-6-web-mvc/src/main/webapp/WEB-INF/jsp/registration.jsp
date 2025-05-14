<%@ page contentType="text/html; ISO-8859-1" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
    <head>
        <title>Registration</title>
    </head>
    <body>
        <h1>Registration</h1>

        <!-- THIS IS NOT WORKING -  POST NOS SENT from JSP -->
        <form:form modelAttribute="registration" method="POST">
            <table>
              <tr>
                <td>Name:</td>
                <td><form:input path="name" /></td>
              </tr>
              <tr>
                <td>
                  <input type="submit" value="Add Registration"/>
                </td>
              </tr>
            </table>
        </form:form>
    </body>
</html>