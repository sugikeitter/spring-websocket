<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" language="java"
    contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
    <title>Chat</title>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery-2.1.1.min.js" />"></script>
    </head>
<body>
    <h1>Chat</h1>
    <p>
        Welcome <span id="userName">${userName}</span>
    </p>
    <p>
        接続状態<span id="status">●</span>
    </p>
    <form action="<c:url value='logout'/>" method="post">
        <input type="submit" value="ログアウト" class="btn btn-warning" /> <input
            type="hidden" name="${_csrf.parameterName}"
            value="${_csrf.token}" />
    </form>
    <form id="form" action="#">
        <textarea id="message" cols="80"></textarea>
        <br /> <input type="submit" id="send" value="送信"
            class="btn btn-primary" />
    </form>
    <p>ユーザ</p>
    <ul>
        <c:forEach var="e" items="${userNameList}">
            <li class="${e.getUserName()}"><c:out
                    value="${e.getUserName()}" /></li>
        </c:forEach>
    </ul>
    <div id="log"></div>
</body>
</html>
