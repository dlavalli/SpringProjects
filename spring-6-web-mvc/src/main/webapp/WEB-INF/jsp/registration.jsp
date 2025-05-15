<%@ page contentType="text/html; ISO-8859-1" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="css/registration.css" type="text/css" />
</head>
    <body>
        <h1>Registration</h1>
        <form:form modelAttribute="registration" method="POST">
            <form:errors path="*" cssClass="errorblock" element="div" />
            <table>
                <tr>
                    <td>
                        <spring:message code="name" />
                    </td>
                    <td>
                        <form:input path="name" />
                    </td>
                    <td>
                        <form:errors path="name" cssClass="error" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="<spring:message code='save.changes' />"}>
                    </td>
                </tr>
            </table>
        </form:form>
    </body>
</html>