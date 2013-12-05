<%--
  Author: Vincent.Chan
  Date: 12-4-13
  Time: 下午5:34
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<html>
<head>
    <title></title>
</head>
<body>
<% Exception ex = (Exception) request.getAttribute("exception"); %>
<h2>出错了: <%= ex.getMessage()%></h2>
<p/>
<% ex.printStackTrace(new java.io.PrintWriter(out)); %>
</body>
</html>