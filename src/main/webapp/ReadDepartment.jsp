<%@ page import="model.Department" %>
<%@ page import="controller.DepartmentProcessors.DepartmentModification" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 11.05.2016
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Department department = (Department) session.getAttribute(DepartmentModification.DEPARTMENT); %>
<html>
<head>
    <title>Department information</title>
    <script>
        document.createElement('aside'); // todo убрать?
        document.createElement('article');
    </script>
    <style type="text/css">
        @import url("css/style.css");
    </style>
</head>
<body>

<jsp:include page="Header.jsp"/>
<jsp:include page="Menu.jsp"/>
<jsp:include page="Footer.jsp"/>
<jsp:include page="Search.jsp"/>

Name: <%=department.getName()%>
Description: <%=department.getDescription()%>

</body>
</html>
