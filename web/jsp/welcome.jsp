<%@include file="../parts/header.jsp" %>

<html>
<head>
    <title><fmt:message key="welcome"/></title>
</head>
<body>

<h2>
    <c:out value='${sessionScope.get("email")}'/>
    <c:set var="role" scope="session" value='${sessionScope.get("role")}'/>
    <br>
    <fmt:message key='${role}'/>

    <br>
</h2>

<h4>
    <%--<c:set var = "user" scope = "page" value = '${sessionScope.get("user")}'/>--%>
    <%--<c:out value='user'/>--%>
    <br>

</h4>
<%--<c:set var="user" scope="session" value='${sessionScope.get("user")}'/>--%>
<%--<c:set var="role" scope="session" value='${user.getType()}'/>--%>
<%--<c:set var="reports" scope="session" value='${sessionScope.get("reports")}'/>--%>

<%--<c:if test="${role == 'Individual'}">--%>
<%--<table border="1" style="border: 3px black solid">--%>
<%--<thead>--%>

<%--<th><fmt:message key="id"/></th>--%>
<%--<th><fmt:message key="submit_date"/></th>--%>
<%--<th><fmt:message key="content"/></th>--%>
<%--<th><fmt:message key="inspector"/></th>--%>
<%--<th><fmt:message key="status"/></th>--%>
<%--<th><fmt:message key="update_date"/></th>--%>
<%--<th><fmt:message key="change"/></th>--%>


<%--</thead>--%>
<%--<tbody>--%>
<%--<c:forEach items="${reports}" var="current">--%>
<%--<c:set var="report_id" value="${current.getId()}"/>--%>
<%--<c:set var="report_status" value="${current.getReportStatus().getStatus()}"/>--%>
<%--<tr>--%>

<%--<td>${current.getId()}</td>--%>
<%--<td>${current.getCreationTime()}</td>--%>
<%--<td>--%>
<%--<table border="1" style="border: solid thin grey">--%>
<%--<thead>--%>
<%--<th><fmt:message key="earned_income"/></th>--%>
<%--<th><fmt:message key="unearned_income"/></th>--%>
<%--<th><fmt:message key="tax_rate"/></th>--%>
<%--<th><fmt:message key="comment"/></th>--%>
<%--<th><fmt:message key="tax"/></th>--%>
<%--</thead>--%>
<%--<tbody>--%>
<%--<td>${current.getContent().getEarnedIncome()}</td>--%>
<%--<td>${current.getContent().getUnearnedIncome()}</td>--%>
<%--<td>${current.getContent().getTaxRate()}</td>--%>
<%--<td>${current.getContent().getComment()}</td>--%>
<%--<td>${current.getContent(). getTax()}</td>--%>
<%--</tbody>--%>
<%--</table>--%>
<%--&lt;%&ndash;${current.getContent().toJson()}&ndash;%&gt;--%>
<%--</td>--%>
<%--<td>${current.getInspector().getName()}</td>--%>
<%--<td><fmt:message key='${report_status}'/></td>--%>
<%--<td>${current.getLastUpdatedTime()}</td>--%>
<%--<td><a href='/edit_report?id=${report_id}'>--%>
<%--<fmt:message key="edit_report"/></a></td>--%>
<%--</tr>--%>

<%--</c:forEach>--%>


<%--</tbody>--%>
<%--</table>--%>
<%--</c:if>--%>


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
    <a href="<c:url value = "/?command=Reports"/>"><fmt:message key="my_reports"/></a>
    <br>
    <a href="<c:url value = "/?command=AddReport"/>"><fmt:message key="new_report"/></a>
    <br>
    <a href="<c:url value = "/?command=Logout"/>"><fmt:message key="logout"/></a>
    <br>
    <c:out value=""/>
</h3>
<%--<br>--%>
<%--<a href="<c:url value = "?lang=ru"/>"><fmt:message key="russian"/></a></a>--%>
<%--<br>--%>
<%--<a href="<c:url value = "?lang=en"/>"><fmt:message key="english"/></a></a>--%>

<%--<br>--%>
<%--<a href="?lang=ru"> RU </a>--%>
<%--<br>--%>
<%--<a href="?lang=en"> EN </a>--%>

<%--</c:if>--%>
<c:if test="${role == 'Admin'}">
    <a href="<c:url value = "/jsp/welcome.jsp"/>">Admin</a>
</c:if>
</body>
</html>
