<%--
  Created by IntelliJ IDEA.
  User: nikak
  Date: 13.04.2024
  Time: 0:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>

    <header class="bg-primary p-5">
        <h3 class="text-center text-light"> Обновите страницу чтобы удаление отобразилось</h3>
    </header>

    <div class="w-50 mx-auto mt-3">
        <form id="f" action="del" method="post" class="">
            <input name="message" placeholder="Введите сообщение чтобы удалить"
                   class="form-control  w-50">
            <input type="submit" value="удалить"
                   class="btn btn-primary mt-3  w-25">
        </form>
    </div>

    <div class="w-50 mx-auto">
        <c:choose>
            <c:when test="${empty msgs}">
                <h3 class="">Entries not found</h3>
            </c:when>
            <c:otherwise>

                <h3>File messages</h3>

                <c:forEach var="msg" items="${msgs}">
                    <p><c:out value="${msg}" /></p>
                </c:forEach>

            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
