<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Create report</title>
</head>
<body>

<form method="post">
    Income1: <input type="number" name="income1"/><br/>
    Income2: <input type="number" name="income2"/><br/>
    <input type="submit" />
</form>

<c:set var="inc1" scope="session" value='${sessionScope.get("inc1")}'/>
<c:if test="${not empty inc1}">
    <table border="1">
        <thead>
        <th>Inc1</th>
        </thead>

        <tbody>
        <tr>
            <td>${inc1}</td>
        </tr>
        </tbody>
    </table>
</c:if>


</body>
</html>
