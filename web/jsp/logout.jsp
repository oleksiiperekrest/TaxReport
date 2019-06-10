<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="lang"
       value="${not empty param.lang ? param.lang : lang}"
       scope="session"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="text"/>
<fmt:requestEncoding value="utf-8"/>

<html>
<head>
    <title><fmt:message key="logout_title"/></title>
</head>
<body>
<h2>
    <fmt:message key="logout_successful"/>
</h2>

<a href="<c:url value = "?command=Login"/>"><fmt:message key="login_title"/></a>
</body>
</html>
