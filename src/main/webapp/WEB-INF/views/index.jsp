<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" language="java"
        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<c:url value="/resources/css/index.css" />" rel="stylesheet">
    <script type="text/javascript" src="<c:url value="/resources/js/jquery-2.1.1.min.js" />"></script>
    <%-- <script type="text/javascript" src="<c:url value="/resources/js/index.js" />"></script> --%>
    <title>WebSocket Application</title>
</head>
<body>
    <div class="form-wrapper">
        <h1>Chat</h1>
        <form id="form" action="<c:url value='authentication'/>" method="post">
            <div class="form-item">
                <input type="text" name="un" required="required" placeholder="Username"></input>
            </div>
            <div class="form-item">
                <input type="password" name="pw" required="required" placeholder="Password"></input>
            </div>
            <div class="button-panel">
                <input type="submit" class="button" title="Sign In" value="Sign In"></input>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
        <div class="form-footer">
            <p><a href="./createUser">Create an account</a></p>
            <!-- TODO -->
            <p><a href="#">Forgot password?</a></p>
        </div>
    </div>
</body>
</html>