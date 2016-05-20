<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 05.05.2016
  Time: 0:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menu</title>
</head>
<body>
<div class="menu">

    <b>Опции: </b><br/>
    <a href='EmployeesList.jsp'>Cписок сотрудников<br/></a>
    <a href='DepartmentsList.jsp'>Список отделов<br/></a> <%--cоздать--%>
    <a href='Employee-new.jsp'>Добавить сотрудника<br/></a> <%-- Достаточно, что action != editEmployee --%>
    <%--    <a href='Employee-new.jsp?action=tmp_AddEmp'>Добавить сотрудника<br/></a>  Достаточно, что action != editEmployee  --%>
    <a href='Department-new.jsp'>Добавить отдел<br/></a> <%--cоздать--%>
    <a href='Help.jsp'>Помощь<br/></a>

</div>
</body>
</html>
