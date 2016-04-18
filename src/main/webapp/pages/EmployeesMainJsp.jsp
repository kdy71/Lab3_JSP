<%@ page import="model.EmployeeImpl" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.OracleDataAccess" %><%--
  Created by IntelliJ IDEA.
  User: khoruzh
  Date: 15.04.2016
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EmployeesMain</title>
</head>
<body>
    <h1> Список сотрудников </h1>
    <br>

    <%
    ArrayList<EmployeeImpl> listEmployees = (ArrayList<EmployeeImpl>) OracleDataAccess.getInstance().getAllEmployees();
    for (EmployeeImpl currEmp:listEmployees) { %>
        ФИО: <%=   currEmp.getName()%>      Отдел:  <%=currEmp.getDepartmentId()%> <br>
    <%
    }
    %>


</body>
</html>
