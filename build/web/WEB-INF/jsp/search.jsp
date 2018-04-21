<%-- 
    Document   : search
    Created on : Apr 12, 2018, 1:58:06 AM
    Author     : Phan Hieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Search for ${keyword} (${list_products.size()} kết quả)</h1>
        <c:forEach var="product" items="${list_products}">
            <p><a href="${product.url}" target="_blank"><c:out value="${product.name}"/></a></p>
        </c:forEach>
    </body>
</html>
