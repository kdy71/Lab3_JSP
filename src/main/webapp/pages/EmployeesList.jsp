<%@ page import="model.Employee" %>
<%@ page import="model.OracleDataAccess" %>
<%@ page import="java.util.ArrayList" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Dudkin Alexander, Dmitry Khoruzhenko
  Date: 15.04.2016
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <meta charset='utf-8'>
    <title>Список сотрудников</title>
    <script>
        document.createElement('aside');
        document.createElement('article');
    </script>
    <style type="text/css">
        @import url("../css/style.css"); /*проверить как работают стили и настроить их*/
    </style>
</head>

<body>

<%--<header>
    <h3>Список всех сотрудников организации</h3>
</header>--%>

<jsp:include page="Header.jsp"/>

<%--<aside>
    <b>Опции: </b><br/>
    <a href='EmployeesList.html'>Cписок сотрудников<br/></a>
    <a href='DepartmentsList.html'>Список отделов<br/></a>
    <a href='Employee-new.html'>Добавить сотрудника<br/></a>
    <a href='Department-new.html'>Добавить отдел<br/></a>
    <a href='Help.html'>Помощь<br/></a>
</aside>--%>
<jsp:include page="Menu.jsp"/>

<article>

    <form action = 'Employee-edit.jsp' method = 'post'> <!--исправить адрес к сервлету и метод-->

        <table border="1">
            <tr>
                <th>ФИО</th>  <th>Отдел</th> <th>Должность</th> <th>Менеджер</th> <th>Дата приёма</th><th>Обновить</th><th>Удалить</th>
            </tr>

            <%
                //            ArrayList<EmployeeImpl> listEmployees = (ArrayList<EmployeeImpl>) OracleDataAccess.getInstance().getAllEmployees();
                ArrayList<Employee> listEmployees = (ArrayList<Employee>) OracleDataAccess.getInstance().getAllEmployees();
                String ref4EditEmployee;
                for (Employee currEmp:listEmployees) {
                    ref4EditEmployee = "<a href=Employee-edit.jsp&action1='edit'&employee_id="+currEmp.getId()+"> редактировать </a>";
                    System.out.println(ref4EditEmployee);
            %>

            <tr>
                <td> <%=currEmp.getName()%>           </td>
                <td> <%=currEmp.getDepartmentName()%> </td>
                <td> <%=currEmp.getJobName()%>        </td>
                <td> <%=currEmp.getManagerName()%>    </td>
                <td> <%=currEmp.getDateIn()%>         </td>
<!--                <td> <input type='submit' value = 'Update'></td> -->
                <td>
                    <a href="Employee-edit.jsp?action1=edit"> редактирование </a>

                </td>
                <td> <input type='submit' value = 'Delete'></td>
            </tr>

            <%
                }
            %>

            <% session.setAttribute("employeeForEdit", listEmployees.get(0)); %>  <!--  debug Передавать надо выбранного работника  -->
            <!--  Только как определить, какой из них выбран???  -->

        </table>

    </form>
</article>

<jsp:include page="Footer.jsp"/>

</body>
</html>