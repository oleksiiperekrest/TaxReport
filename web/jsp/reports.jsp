<%@include file="../parts/header.jsp" %>

<html>
<head>
    <title><fmt:message key="reports"/></title>
</head>
<body>
<c:set var="user" scope="session" value='${sessionScope.get("user")}'/>
<c:set var="role" scope="session" value='${user.getType()}'/>
<c:set var="reports" scope="session" value='${sessionScope.get("reports")}'/>
<c:if test="${role == 'Individual' || role == 'LegalEntity'}">
    <table border="1" style="border: 3px black solid">
        <thead>

        <th><fmt:message key="id"/></th>
        <th><fmt:message key="submit_date"/></th>
        <th><fmt:message key="content"/></th>
        <th><fmt:message key="inspector"/></th>
        <th><fmt:message key="status"/></th>
        <th><fmt:message key="update_date"/></th>
        <th><fmt:message key="change"/></th>


        </thead>
        <tbody>
        <c:forEach items="${reports}" var="current">
            <c:set var="report_id" value="${current.getId()}"/>
            <c:set var="report_status" value="${current.getReportStatus().getStatus()}"/>
            <tr>

                <td>${current.getId()}</td>
                <td>${current.getCreationTime()}</td>
                <td>
                    <table border="1" style="border: solid thin grey">
                        <thead>
                        <th><fmt:message key="earned_income"/></th>
                        <th><fmt:message key="unearned_income"/></th>
                        <th><fmt:message key="tax_rate"/></th>
                        <th><fmt:message key="comment"/></th>
                        <th><fmt:message key="tax"/></th>
                        </thead>
                        <tbody>
                        <td>${current.getContent().getEarnedIncome()}</td>
                        <td>${current.getContent().getUnearnedIncome()}</td>
                        <td>${current.getContent().getTaxRate()}</td>
                        <td>${current.getContent().getComment()}</td>
                        <td>${current.getContent(). getTax()}</td>
                        </tbody>
                    </table>
                        <%--${current.getContent().toJson()}--%>
                </td>
                <td>${current.getInspector().getName()}</td>
                <td><fmt:message key='${report_status}'/></td>
                <td>${current.getLastUpdatedTime()}</td>
                <td><a href='/?command=EditReport&id=${report_id}'>
                    <fmt:message key="edit_report"/></a></td>
            </tr>

        </c:forEach>


        </tbody>
    </table>
</c:if>

<a href="<c:url value = "?command=Login"/>"><fmt:message key="back_to_title"/></a>
</body>
</html>
