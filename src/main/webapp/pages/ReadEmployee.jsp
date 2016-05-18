<%@ page import="model.Employee" %>
<%@ page import="controller.EmployeeProcessors.EmployeeModification" %>
<%@ page import="model.Department" %>
<%@ page import="model.OracleDataAccess" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 10.05.2016
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% Employee employee = (Employee) session.getAttribute(EmployeeModification.EMPLOYEE);
    Department department = OracleDataAccess.getInstance().getDepartmentById(employee.getDepartmentId());
    Employee manager = OracleDataAccess.getInstance().getEmployeeById(employee.getManagerId());
%>

<html>
<head>
    <title>Employee information</title>
    <script>
        document.createElement('aside'); // todo убрать?
        document.createElement('article');
    </script>
    <style type="text/css">
        @import url("../css/style.css"); /*проверить как работают стили и настроить их*/
    </style>
</head>

<body>

<jsp:include page="Header.jsp"/>
<jsp:include page="Menu.jsp"/>
<jsp:include page="Footer.jsp"/>

Name: <%=employee.getName()%>
Department: <%=department.getName() %>
Manager: <%=manager.getName()%>
Job: <%=employee.getJobName()%>
Salary: <%=employee.getSalary()%>
Works since: <%=employee.getDateIn()%>


</body>
</html>
