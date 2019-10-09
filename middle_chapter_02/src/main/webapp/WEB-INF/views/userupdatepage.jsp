<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Page</title>
</head>
<body>
<hr>
<div>
    <c:choose>
        <c:when test="${not empty userForUpdate.photoId}">
            <img src="${pageContext.servletContext.contextPath}/download?name=${userForUpdate.photoId}"
                 width="100px"
                 height="100px"/></br>
        </c:when>
        <c:otherwise>
            <h4>No image</h4>
        </c:otherwise>
    </c:choose>
</div>
</br>
<form action="<%=request.getContextPath()%>/upload" method="post" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${userForUpdate.id}"/>
    <input type="file" name="file"></br>
    <button type="submit" class="btn btn-default">upload</button>
</form>
</br>
<hr>
<form action="${pageContext.servletContext.contextPath}/usersupdate" method="post">
    <input type="hidden" name="id" value="${userForUpdate.id}"/><br/>
    ID: <c:out value="${userForUpdate.id}"></c:out><br/>
    Name: <input type="text" name="name" value="${userForUpdate.name}"/><br/>
    Login: <input type="text" name="login" value="${userForUpdate.login}"/><br/>
    Email: <input type="text" name="email" value="${userForUpdate.email}"/><br/>
    Create date: <c:out value="${userForUpdate.createDate}"></c:out><br/>
    <hr>
    <input type="submit" value="update">
    </br>
    <hr>
</form>
</body>
</html>
