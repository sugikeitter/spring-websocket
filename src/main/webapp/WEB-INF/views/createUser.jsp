<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="true" language="java"
    contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta name="_csrf" content="${_csrf.token}" />
    <meta name="_csrf_header" content="${_csrf.headerName}" />
    <title>Create user</title>
    <%-- <link href="<c:url value="/resources/css/createUser.css" />" rel="stylesheet"> --%>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery-2.1.1.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/createUser.js" />"></script>
</head>
<body>
    <h1>Crate user</h1>

    <form id="form" action="<c:url value='authentication'/>" method="post">
        <input type="text" id='un' name="un" required="required"
            placeholder="Username"
            onKeyUp="isUserNameDuplication(this.value)" /><br/>
        <input type="password" id='pw' name="pw" required="required"
            placeholder="Password" /><br/>
        <input type="submit" id="button" class="button"
            value="Crate an account" disabled /><br/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>

    <div id="errorMsg" class="errorMsg">${errorMsg}</div>
    <div class="form-footer">
        <p>
            <a href="./">Sign in</a>
        </p>
    </div>
</body>
</html>
