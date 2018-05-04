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
        <title>${keyword}</title>
        
        <link rel="stylesheet" href="${contextPath}/public/css/bootstrap-4.1.0/bootstrap.min.css" />
        <link rel="stylesheet" href="${contextPath}/public/css/fontawesome-free-5.0.10/css/fontawesome-all.min.css" />
        <link rel="stylesheet" href="${contextPath}/public/css/site.css" />
        <script src="${contextPath}/public/js/jquery-3.3.1.slim.min.js"></script>
        <script src="${contextPath}/public/js/popper-1.14.0.min.js"></script>
        <script src="${contextPath}/public/js/bootstrap-4.1.0/bootstrap.min.js"></script>
        <script src="${contextPath}/public/js/main.js"></script>
    </head>
    <body class="search">
        <div class="logo-container">
            <img class="logo" src="https://raw.githubusercontent.com/ALeeMN/images-for-codepens/master/logo.jpg" width="270" height="95" alt="Google" id="logo"/>
        </div>
        <div class="row">
            <div class="col-md-6 search-form">
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
        <div class="product-container">
            <div class="row">
                <div class="col-md-3">
                    <div class="mcard filter">
                        <div>Đánh giá</div>
                        <div class="rating">
                            <span class="rating-content">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <span style="width: 100%;">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                </span>
                            </span>
                        </div>
                        <div class="rating">
                            <span class="rating-content">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <span style="width: 80%;">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                </span>
                            </span>
                        </div>
                        <div class="rating">
                            <span class="rating-content">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <span style="width: 60%;">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                </span>
                            </span>
                        </div>
                        <div class="rating">
                            <span class="rating-content">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <span style="width: 40%;">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                </span>
                            </span>
                        </div>
                        <div class="rating">
                            <span class="rating-content">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <span style="width: 20%;">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                </span>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="col-md-9">
                    <c:forEach var="product" items="${list_products}">
                        <div class="box-product" id="${product.getId()}">
                            <a class="mcard" href="${product.getUrl()}" target="_blank">
                                <div class="product-image">
                                    <img src="${product.getImage()}" alt="${product.getName()}" />
                                </div>
                                <div class="product-detail">
                                    <div class="product-name">${product.getName()}</div>
                                    <div class="product-price">${product.getPrice()}</div>
                                    <div class="rating">
                                        <span class="rating-content">
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star"></i>
                                            <i class="fa fa-star"></i>
                                            <span style="width: ${product.getRatingScorePercent()}%;">
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                            </span>
                                        </span>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </body>
</html>
