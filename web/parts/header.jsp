<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="lang"
       value="${not empty param.lang ? param.lang : lang}"
       scope="session"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="text"/>
<fmt:requestEncoding value="utf-8"/>

<c:set var="req" value="${pageContext.request}"/>
<c:set var="baseURL" value="${fn:replace(req.requestURL, req.requestURI, '')}"/>
<c:set var="requestPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<c:set var="params" value="${requestScope['javax.servlet.forward.query_string']}"/>
<%--<c:set var="pageUrl" value="${ baseURL + requestPath + not empty params ? params : ''}"/>--%>
<c:choose>
    <c:when test='${empty params}'>

        <a href="?lang=ru"> RU </a>
        <br>
        <a href="?lang=en"> EN </a>
    </c:when>
    <c:otherwise>

        <%--<a href=<c:out value='${baseURL + requestPath + params}&lang="ru"'>RU</a>--%>
        <a href= <c:out value = "${baseURL}${requestPath}?lang=ru&${params}"/>>RU</a>

        <%--<a href='${baseURL + requestPath + params}' &lang="en">EN</a>--%>
        <a href= <c:out value = "${baseURL}${requestPath}?lang=en&${params}"/>>EN</a>
    </c:otherwise>

</c:choose>

<c:url value="${pageUrl}">a</c:url>

<%--<a href="?lang=ru"> RU </a>--%>
<%--<br>--%>
<%--<a href="?lang=en"> EN </a>--%>
