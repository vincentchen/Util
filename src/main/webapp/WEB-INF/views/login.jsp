<%--
  @author Vincent.Chan
  @since 2012.04.12 16:15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:url value="/login" var="postUrl"/>
<html>
<head>
    <title>用户登录</title>
    <link href="<c:url value="/resources/form.css" />" rel="stylesheet" type="text/css"/>
</head>
<body>
<form:form id="form" method="post" action="${postUrl}" modelAttribute="loginUser" cssClass="cleanform">
    <div class="header">
        <h2>登录表单</h2>
        <s:bind path="*">
            <c:if test="${status.error}">
                <div id="message" class="error">登录信息有误!</div>
            </c:if>
        </s:bind>
        <c:if test="${result!=null}">
            <c:choose>
                <c:when test="${result.code == ErrorCode.OK}">
                    <div id="message" class="success">登录成功！</div>
                </c:when>
                <c:when test="${result.code > 1}">
                    <div id="message" class="error">${result.message}</div>
                </c:when>
            </c:choose>
        </c:if>
    </div>
    <fieldset>
        <legend>用户信息</legend>
        <form:label path="username">
            用户名 <form:errors path="username" cssClass="error"/>
        </form:label>
        <form:input path="username"/>
        <form:label path="passwd">
            密码 <form:errors path="passwd" cssClass="error"/>
        </form:label>
        <form:password path="passwd"/>
        <form:label path="roleType">
            角色 <form:errors path="roleType" cssClass="error"/>
        </form:label>
        <form:input path="roleType"/>
    </fieldset>
    <p>
        <button type="submit">登 录</button>
    </p>
</form:form>
</body>
</html>