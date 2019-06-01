<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%--<fmt:setLocale value="ru"/>--%>

<fmt:setLocale value="${param.lang}"/>

<fmt:setBundle basename="text"/>
<fmt:requestEncoding value="utf-8"/>


<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h2>
    <c:out value='${sessionScope.get("email")}'/>
    <br>
    <c:out value='${sessionScope.get("role")}'/>
    <%--<c:set var = "user" scope = "page" value = '${sessionScope.get("user")}'/>--%>
    <%--<c:out value='user'/>--%>
</h2>
<h4>
<br>
    <c:out value='${sessionScope.get("user").toString()}'/>
</h4>
<c:set var="user" scope="session" value='${sessionScope.get("user")}'/>
<c:set var="role" scope="session" value='${user.getType()}'/>
<c:if test="${role == 'Individual'}">
    <table border="1">
        <thead>

        <th>Id</th>
        <th>Email</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Number</th>

        </thead>
        <tbody>

            <tr>

                <td>${user.getId()}</td>
                <td>${user.getEmail()}</td>
                <td>${user.getFirstName()}</td>
                <td>${user.getLastName()}</td>
                <td>${user.getTaxpayerNumber()}</td>
            </tr>

        </tbody>
    </table>
</c:if>

<c:set var="role" scope="session" value='${sessionScope.get("role")}'/>
<%--<fmt:setLocale value="${param.lang}" />--%>

<%--<fmt:requestEncoding value="UTF-8"/>--%>

<%--<fmt:bundle basename="text" >--%>
<%--<fmt:message key="reports"/><br/>--%>
<%--<fmt:message key="new_report"/><br/>--%>
<%--<fmt:message key="logout"/><br/>--%>
<%--</fmt:bundle>--%>
<%--</body>--%>

<%--<c:if test="${role == 'Individual' or role == 'LegalEntity'}">--%>
<h3>
<a href="<c:url value = "/reports"/>"><fmt:message key="reports"/></a>
<br>
<a href="<c:url value = "/jsp/add_report.jsp"/>"><fmt:message key="new_report"/></a></a>
<br>
<a href="<c:url value = "/jsp/logout.jsp"/>"><fmt:message key="logout"/></a></a>
<br>
    <c:out value=""/>
</h3>
<%--<br>--%>
<%--<a href="<c:url value = "?lang=ru"/>"><fmt:message key="russian"/></a></a>--%>
<%--<br>--%>
<%--<a href="<c:url value = "?lang=en"/>"><fmt:message key="english"/></a></a>--%>

<br>
<a href="?lang=ru"> RU </a>
<br>
<a href="?lang=en"> EN </a>

<a href="<c:url value="/welcome?lang=ru"/><c:out value='RU'></c:out></a>
<br>
<a href = "
<c:url value="/welcome?lang=en"/>
<c:out value='EN'></c:out></a>

<%--</c:if>--%>
<c:if test="${role == 'Admin'}">
    <a href="<c:url value = "/jsp/welcome.jsp"/>">Admin</a>
</c:if>
</body>
</html>
