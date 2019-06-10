
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>Error</title>
</head>
<body>
<h2>Sorry, something went wrong</h2>
<a href="/?command=Login">Return</a>
</body>
</html>
