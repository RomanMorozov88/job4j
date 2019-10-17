<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Private Table</title>
</head>
<body>
<c:choose>
    <c:when test="${fn:length(users) gt 0}">
        <table border=1>
            <tr>
                <td>
                    <c:choose>
                        <c:when test="${not empty mainUser.photoId}">
                            <img src="${pageContext.servletContext.contextPath}/download?name=${mainUser.photoId}"
                                 width="100px"
                                 height="100px"/></br>
                            <a href="${pageContext.servletContext.contextPath}/download?name=${mainUser.photoId}">Download</a>
                        </c:when>
                        <c:otherwise>
                            <h4>No image</h4>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><c:out value="${mainUser.rolename}"></c:out></td>
                <td><c:out value="${mainUser.id}"></c:out></td>
                <td><c:out value="${mainUser.name}"></c:out></td>
                <td><c:out value="${mainUser.login}"></c:out></td>
                <td><c:out value="${mainUser.email}"></c:out></td>
                <td><c:out value="${mainUser.createDate}"></c:out></td>
                <td>
                    <form action="${pageContext.servletContext.contextPath}/usersupdate" method="get">
                        <input type="hidden" name="id" value="${mainUser.id}"/>
                        <input type="submit" value="update">
                    </form>
                </td>
                <td>
                    <form action="${pageContext.servletContext.contextPath}/" method="post">
                        <input type="hidden" name="id" value="${mainUser.id}"/>
                        <input type="submit" value="delete">
                    </form>
                </td>
            </tr>
        </table>
        <p>All users:</p>
        <table border=1>
            <tr>
                <th>photo</th>
                <th>role</th>
                <th>id</th>
                <th>name</th>
                <th>login</th>
                <th>email</th>
                <th>create date</th>
                <th colspan=2>actions</th>
            </tr>
            <c:forEach items="${users}" var="user">
            <tr>
                <td>
                    <c:choose>
                        <c:when test="${not empty user.photoId}">
                            <img src="${pageContext.servletContext.contextPath}/download?name=${user.photoId}"
                                 width="100px"
                                 height="100px"/></br>
                            <a href="${pageContext.servletContext.contextPath}/download?name=${user.photoId}">Download</a>
                        </c:when>
                        <c:otherwise>
                            <h4>No image</h4>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><c:out value="${user.rolename}"></c:out></td>
                <td><c:out value="${user.id}"></c:out></td>
                <td><c:out value="${user.name}"></c:out></td>
                <td><c:out value="${user.login}"></c:out></td>
                <td><c:out value="${user.email}"></c:out></td>
                <td><c:out value="${user.createDate}"></c:out></td>
                <td>
                    <form action="${pageContext.servletContext.contextPath}/usersupdate" method="get">
                        <input type="hidden" name="id" value="${user.id}"/>
                        <input type="submit" value="update">
                    </form>
                </td>
                <td>
                    <form action="${pageContext.servletContext.contextPath}/" method="post">
                        <input type="hidden" name="id" value="${user.id}"/>
                        <input type="submit" value="delete">
                    </form>
                </td>
                </c:forEach>
            </tr>
        </table>
    </c:when>
    <c:otherwise>
        <h1>List is empty.</h1>
    </c:otherwise>
</c:choose>
<form action="${pageContext.servletContext.contextPath}/userscreate" method="get">
    <input type="submit" value="add new user.">
</form>
<form action="${pageContext.servletContext.contextPath}/exit" method="post">
    <button type="submit" class="btn btn-default" style="background-color: coral">Exit</button>
</form>
</body>
</html>