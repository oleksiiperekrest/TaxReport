<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login to our app</title>
</head>
<body>
<c:set var = "error" scope = "session" value ='${sessionScope.get("error")}' />

<c:if test="${error == 'login_failed'}">
    <h1>Login failed</h1>
</c:if>

<c:if test="${error != 'login_failed'}">
<h1>Please login</h1>
</c:if>



<form method="post" >
    Username: <input type="text" name="email"/><br/>
    Password: <input type="password" name="password"/><br/>
    <input type="submit" />
</form>

</body>
</html>
