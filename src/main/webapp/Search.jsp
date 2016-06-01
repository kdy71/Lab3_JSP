<%@ page import="controller.DepartmentProcessors.DepartmentModification" %>
<%@ page import="controller.Actions" %>
<%@ page import="model.OracleDataAccess" %>
<%@ page import="model.Employee" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 01.06.2016
  Time: 11:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset='utf-8'>
</head>

<html>
<head>
    <title>Search</title>
</head>
<body>
<div class="search">
    <h3><b>Поиск:</b></h3>

    <form name="search" action='EmployeesList.jsp' method='post' accept-charset="utf-8">

        <%--
            <form name="search" action='ServletStart' method='post' accept-charset="utf-8">
        --%>

        <input type='text' size='20' id="searchPatternId"
               name='searchPattern' placeholder="Введите имя"/>
        <p>
            <select size='1' id="searchEntityId" name="searchEntity">
                <option disabled>Выберите сущность</option>
                <option selected value="employee">Работник</option>
                <option value="department">Отдел</option>
            </select>
        </p>
        <input type='submit' value='Search'/>

        <% request.setCharacterEncoding("utf-8");
            String searchEntity;
            String searchPattern;

            searchEntity = request.getParameter("searchEntity");

            searchPattern = request.getParameter("searchPattern"); //из формы берем паттерн поиска
            if (searchPattern == null) {
                searchPattern = "";
            }
            System.out.println(searchPattern);
            List<Employee> employeeList = new ArrayList<Employee>();

/*
            if (searchPattern != null && !searchPattern.isEmpty()) {
*/
            if (searchPattern != null && !searchPattern.isEmpty() && searchEntity.equals("employee")) {
                employeeList = OracleDataAccess.getInstance().findEmployeesByName(searchPattern);
                request.setAttribute("foundEmployees", employeeList);
            }

            for (int i = 0; i < employeeList.size(); i++) {
                System.out.println(employeeList.get(i));
            }
        %>

    </form>

</div>
</body>
</html>
