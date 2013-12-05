<%@page language="java" pageEncoding="GBK" %>
<%
    try {
        out.println("TOMCAT-IS-HEALTHY!");
    } catch (Exception e) {
        out.flush();
        out.println("TOMCAT-IS-DIED!");
    }
%>
