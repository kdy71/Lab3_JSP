<%@ page import="model.EmployeeImpl" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.OracleDataAccess" %>
<%@ page import="model.Employee" %>

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
    <style>

        header {
            background: lightblue;
            padding: 10px;
            width: 650px;
            position: absolute; /* Абсолютное позиционирование */
            top: 10px; /* Положение от  края */
            left: 20px; /* Положение от края */
            font-family:Calibri;
            text-align: center;
        }

        aside {
            background: lightgreen;
            padding: 10px;
            width: 200px;
            float: left;
            position: absolute;
            top: 100px;
            left: 20px;
            font-family:Calibri;
        }

        article {
            background: lightgoldenrodyellow;
            padding: 10px;
/*            margin-left: 430px; */
            margin-left: 250px;
            width: 610px;
            display: block;
            position: absolute;
            top: 100px;
            left: 10px;
            font-family:Calibri;
        }
    </style>
</head>

<body>

<header>
    <h3>Список всех сотрудников организации</h3>
</header>

<aside>
    <b>Опции: </b><br/>
    <a href='EmployeesList.html'>Cписок сотрудников<br/></a>
    <a href='DepartmentsList.html'>Список отделов<br/></a>
    <a href='Employee-new.html'>Добавить сотрудника<br/></a>
    <a href='Department-new.html'>Добавить отдел<br/></a>
    <a href='Help.html'>Помощь<br/></a>
</aside>

<article>

    <form action = 'Employee-edit.jsp' method = 'post'> <!--исправить адрес к сервлету и метод-->
        <p><input name='employee' type='radio' value='Cотрудник 1'>Cотрудник 1</p>
        <p><input name='employee' type='radio' value='Cотрудник 2'>Cотрудник 2</p>

        <table border="1">
            <tr>
                <th>ФИО</th>  <th>Отдел</th> <th>Должность</th> <th>Менеджер</th> <th>Дата приёма</th>
            </tr>

        <%
//            ArrayList<EmployeeImpl> listEmployees = (ArrayList<EmployeeImpl>) OracleDataAccess.getInstance().getAllEmployees();
            ArrayList<Employee> listEmployees = (ArrayList<Employee>) OracleDataAccess.getInstance().getAllEmployees();
            for (Employee currEmp:listEmployees) { %>

            <tr>
                <td> <%=currEmp.getName()%>           </td>
                <td> <%=currEmp.getDepartmentName()%> </td>
                <td> <%=currEmp.getJobName()%>        </td>
                <td> <%=currEmp.getManagerName()%>    </td>
                <td> <%=currEmp.getDateIn()%>         </td>
                <td> Удалить                          </td>
            </tr>

        <%
            }
        %>

        <% session.setAttribute("employeeForEdit", listEmployees.get(0)); %>  <!--  debug Передавать надо выбранного работника  -->
                                                                              <!--  Только как определить, какой из них выбран???  -->

        </table>
        <p><input type='submit' value = 'Choose employee'></p>

    </form>
</article>
</body>
</html>