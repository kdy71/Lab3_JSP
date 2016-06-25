<%@ page import="model.Employee" %>
<%@ page import="model.OracleDataAccess" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="controller.Actions" %>
<%@ page import="controller.EmployeeProcessors.EmployeeModification" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="ErrorPage.jsp" %>

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
        @import url("css/style.css");
    </style>
</head>

<body>

<jsp:include page="Search.jsp"/>
<jsp:include page="Header.jsp"/>
<jsp:include page="Menu.jsp"/>
<jsp:include page="Footer.jsp"/>

<article>

    <!--    <form action = 'Employee-edit.jsp' method = 'post'> исправить адрес к сервлету и метод-->

    <table border="1">
        <%--        <% if (request.getAttribute("foundEmployees") != null ) {--%>
        <% if (request.getAttribute("afterSearch").equals("yes")) {
        %><h3>Результаты поиска:</h3><%
    } else {
    %><h3>Список всех сотрудников:</h3><%
            request.setAttribute("afterSearch", "no");
        }%>

        <tr>
            <th>ФИО</th>
            <th>Отдел</th>
            <th>Должность</th>
            <th>Оклад</th>
            <th>Менеджер</th>
            <th>Дата приёма</th>
            <th>Обновить</th>
            <th>Удалить</th>
        </tr>

        <%
            String stConfirmDel = "  onclick=\"return confirm('Вы точно хотите удалить запись о сотруднике?')\"";

            ArrayList<Employee> listEmployees = new ArrayList<Employee>();
            listEmployees = (ArrayList<Employee>) request.getAttribute("foundEmployees");
//            listEmployees = (ArrayList<Employee>) OracleDataAccess.getInstance().getAllEmployees();

            if (listEmployees == null) {
                listEmployees = (ArrayList<Employee>) OracleDataAccess.getInstance().getAllEmployees();
            }

            String ref4EditEmployee;
            for (Employee currEmp : listEmployees) {
//                    ref4EditEmployee = "<a href=Employee-edit.jsp&action1='edit'&employee_id="+currEmp.getId()+"> редактировать </a>";
//                    System.out.println(ref4EditEmployee);
        %>


        <tr>
            <td><%=currEmp.getName()%>
            </td>
            <td><%=currEmp.getDepartmentName()%>
            </td>
            <td><%=currEmp.getJobName()%>
            </td>
            <%--            <td> <%=  currEmp.getSalary()  %>           </td>  --%>
            <td align="right"><%= String.format("% 10.2f", currEmp.getSalary()) %>
            </td>
            <td><%=currEmp.getManagerName()%>
            </td>
            <td><%=currEmp.getDateIn()%>
            </td>
            <!--                <td> <input type='submit' value = 'Update'></td> -->
            <td>
                <a href=<%="ServletStart?action=" + Actions.EDIT_EMPLOYEE + "&" + EmployeeModification.EMP_ID + "=" +
                        currEmp.getId() %>> редактировать </a>

            </td>
            <td>
                <!--                    <input type='submit' value = 'Delete'>  -->
                <a href=<%= "ServletStart?action=" + Actions.DELETE_EMPLOYEE + "&" + EmployeeModification.EMP_ID + "=" + currEmp.getId() + stConfirmDel %>>
                    удалить </a>
            </td>
        </tr>

        <%
            }
        %>

        <%request.setAttribute("foundEmployees", null);%>

    </table>

</article>
</body>
</html>