<%--
 @author  Vincent.Chan
 @since   2012.04.20 16:24
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:url value="/homework/new" var="postUrl"/>
<html>
<head>
    <title>作业发布</title>
    <link href="<c:url value="/resources/form.css" />" rel="stylesheet" type="text/css"/>
</head>
<body>
<form:form id="form" method="post" action="${postUrl}" modelAttribute="homework"
           cssClass="cleanform" enctype="multipart/form-data">
    <div class="header">
        <h2>作业发布</h2>
        <s:bind path="*">
            <c:if test="${status.error}">
                <div id="message" class="error">请检查表单错误!</div>
            </c:if>
        </s:bind>
        <c:if test="${result!=null}">
            <c:choose>
                <c:when test="${result.code == ErrorCode.OK}">
                    <div id="message" class="success">发布成功！</div>
                </c:when>
                <c:when test="${result.code > 1}">
                    <div id="message" class="error">${result.message}</div>
                </c:when>
            </c:choose>
        </c:if>
    </div>
    <fieldset>
        <legend>作业发布</legend>
        <form:label path="subject">
            科目 <form:errors path="subject" cssClass="error"/>
        </form:label>
        <form:input path="subject"/>
        <form:label path="content">
            内容 <form:errors path="content" cssClass="error"/>
        </form:label>
        <form:input path="content"/>
        <form:label path="classIds">
            班级 <form:errors path="classIds" cssClass="error"/>
        </form:label>
        <form:input path="classIds"/>
        <form:label path="workDay">
            日期 <form:errors path="workDay" cssClass="error"/>
        </form:label>
        <form:input path="workDay"/>
            <%--<form:label path="doc">
                文档 <form:errors path="doc" cssClass="error"/>
            </form:label>--%>
        <input type="file" name="doc"/>
        <input type="file" name="doc"/>
    </fieldset>
    <p>
        <button type="submit">发布</button>
    </p>
</form:form>
</body>
</html>