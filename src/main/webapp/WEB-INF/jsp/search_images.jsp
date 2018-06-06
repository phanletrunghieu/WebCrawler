<%-- 
    Document   : search
    Created on : Apr 12, 2018, 1:58:06 AM
    Author     : Phan Hieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,shrink-to-fit=no">
        <title>Search results for ${keyword} - WebCrawler</title>
        
        <link rel="stylesheet" href="${contextPath}/public/css/bootstrap-4.1.0/bootstrap.min.css" />
        <link rel="stylesheet" href="${contextPath}/public/css/fontawesome-free-5.0.10/css/fontawesome-all.min.css" />
        <link rel="stylesheet" href="${contextPath}/public/css/site.css" />
        <script src="${contextPath}/public/js/jquery-3.3.1.slim.min.js"></script>
        <script src="${contextPath}/public/js/popper-1.14.0.min.js"></script>
        <script src="${contextPath}/public/js/bootstrap-4.1.0/bootstrap.min.js"></script>
        <script src="${contextPath}/public/js/main.js"></script>
    </head>
    <body class="search">
        <div class="header-container">
            <div class="search-container">
                <div class="logo-container">
                    <a href="${contextPath}">
                        <img class="logo" src="${contextPath}/public/images/logo.png" width="70" height="40" alt="Google" id="logo"/>
                    </a>
                </div>
                <div class="search-form">
                    <form action="${contextPath}/search.html">
                        <div class="input-group">
                            <input type="text" class="form-control" name="s" value="${keyword}"/>
                            <span class="input-group-append">
                                <button class="btn btn-primary" type="submit"><i class="fas fa-search"></i></button>
                            </span>
                        </div>
                    </form>
                </div>
            </div>
            <div class="tab-container">
                <ul>
                    <li><a href="${contextPath}/search?s=${keyword}">Web</a></li>
                    <li class="selected"><a href="#">Images</a></li>
                    <li><a href="#">Video</a></li>
                </ul>
            </div>
        </div>
        <div id="resultStats">Tìm thấy  kết quả (${crawlTime/1000} giây)</div>
    </body>
</html>
