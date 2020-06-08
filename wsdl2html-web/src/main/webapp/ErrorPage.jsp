<%--
  Created by Andrés Farías@Globant.
  Date: 4/13/20
--%>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isErrorPage="true" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Error Page</title>
</head>
<body>
<h1>An error arose</h1>

The error was caught and is shown as it follows:
<span style="color: red; ">
        <%= exception.getMessage() %>
    </span><br>
</body>
</html>
