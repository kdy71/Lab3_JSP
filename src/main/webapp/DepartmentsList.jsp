<%@ page import="model.Department" %>
<%@ page import="model.OracleDataAccess" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="controller.DepartmentProcessors.DepartmentModification" %>
<%@ page import="controller.Actions" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 11.05.2016
  Time: 20:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset='utf-8'>
    <title>Список отделов</title>
    <script>
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
<%-- <jsp:include page="Search.jsp"/>  --%>

<article>
    <h3>Список всех отделов в организации</h3>

    <form name="addDepartmentForm" action='ServletStart?action=addDepartment' method='post'> <!--исправить адрес к сервлету и метод-->

        <table border="1">
            <tr>
                <th>Отдел</th>
                <th>Описание</th>
                <th>Обновить</th>
                <th>Удалить</th>
            </tr>

            <%
                String stConfirmDel = "  onclick=\"return confirm('Вы точно хотите удалить отдел?')\"";
                ArrayList<Department> listDepartments = (ArrayList<Department>) OracleDataAccess.getInstance().getAllDepartments();
                for (Department currDept : listDepartments) { %>

            <tr>
                <td><%=currDept.getName()%>
                </td>
                <td><%=currDept.getDescription()%>
<!--                <td><input type='submit' value='Update'></td>  -->
                <td>
                    <a href=<%="ServletStart?action=" + Actions.EDIT_DEPARTMENT + "&" + DepartmentModification.DEPARTMENT_ID + "=" +
                    currDept.getId() %> > редактировать </a>

                </td>                <td>
<!--                    <input type='submit' value='Delete'>  -->
                    <a href=<%= "ServletStart?action=" + Actions.DELETE_DEPARTMENT + "&" + DepartmentModification.DEPARTMENT_ID + "="
                        + currDept.getId() +stConfirmDel %> > удалить </a>
                </td>
            </tr>

            <%
                }
            %>

            <% session.setAttribute("departmentForEdit", listDepartments.get(0)); %>
            <!--  debug Передавать надо выбранного работника  -->
            <!--  Только как определить, какой из них выбран???  -->

        </table>

    </form>
    </article>
</body>
</html>
