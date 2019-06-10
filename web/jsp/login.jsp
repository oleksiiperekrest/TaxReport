<%@include file="../parts/header.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="login_title"/></title>
</head>
<body>
<c:set var = "error" scope = "session" value ='${sessionScope.get("error")}' />

<c:choose>
    <c:when test="${error == 'login_failed'}">
    <h1><fmt:message key="login_failed"/></h1>
</c:when> <c:otherwise>
    <h1><fmt:message key="please_login"/></h1>
</c:otherwise>
</c:choose>

<form method="post" >
    <fmt:message key="email"/>: <input type="text" name="email"/><br/>
    <fmt:message key="password"/>: <input type="password" name="password"/><br/>
    <input type="submit" name = "<fmt:message key="login_button"/>" />
</form>

</body>
</html>
