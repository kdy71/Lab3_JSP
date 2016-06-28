<%@ page import="controller.Actions" %>
<%@ page import="controller.DepartmentProcessors.DepartmentModification" %>
<%@ page import="controller.PaginationController" %>
<%@ page import="model.Department" %>
<%@ page import="model.OracleDataAccess" %>
<%@ page import="java.util.ArrayList" %>
<%--<%@ page errorPage="ErrorPage.jsp" %>--%>
<%--
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
<jsp:include page="InsteadOfSearch.jsp"/>

<article>
    <h3>Список всех отделов:</h3>
    <%
        System.out.println("в начале DepartmentList.jsp - подготовка к пейджинации");  // debug

        //PaginationController paginationController = (PaginationController) request.getSession().getAttribute("paginationControllerForDep");

        int pageNumber;
        if (request.getParameter("page") != null) {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        } else {
            pageNumber = 1;
        }
        System.out.println("pageNumber" + pageNumber);

        int departmentsCount = OracleDataAccess.getInstance().getTotalCountOfDepartments();
        int departmentsPerPage = 5;
        System.out.println("departmentsCount" + departmentsCount);

        PaginationController paginationController = new PaginationController(departmentsCount, departmentsPerPage, pageNumber);
        request.getSession().setAttribute("paginationControllerForDep", paginationController);
    %>

    <form name="addDepartmentForm" action='ServletStart?action=addDepartment' method='post'>

        <table border="1">
            <tr>
                <th> № </th>
                <th>Отдел</th>
                <th>Описание</th>
                <th>Обновить</th>
                <th>Удалить</th>
            </tr>

            <%
                String stConfirmDel = "  onclick=\"return confirm('Вы точно хотите удалить отдел?')\"";
                //ArrayList<Department> listDepartments = (ArrayList<Department>) OracleDataAccess.getInstance().getAllDepartments();
                ArrayList<Department> listDepartments = (ArrayList<Department>) OracleDataAccess.getInstance().getAllDepartments(pageNumber, departmentsPerPage);

                int number = ((pageNumber - 1) * departmentsPerPage);
                for (Department currDept : listDepartments) { %>

            <tr>
                <td><%=++number%></td>
                <td><%=currDept.getName()%>
                </td>
                <td><%=currDept.getDescription()%>
                    <!--                <td><input type='submit' value='Update'></td>  -->
                <td>
                    <a href=<%="ServletStart?action=" + Actions.EDIT_DEPARTMENT + "&" + DepartmentModification.DEPARTMENT_ID + "=" +
                    currDept.getId() %>> редактировать </a>

                </td>
                <td>
                    <!--                    <input type='submit' value='Delete'>  -->
                    <a href=<%= "ServletStart?action=" + Actions.DELETE_DEPARTMENT + "&" + DepartmentModification.DEPARTMENT_ID + "="
                        + currDept.getId() +stConfirmDel %>> удалить </a>
                </td>
            </tr>

            <%
                }
            %>

            <% session.setAttribute("departmentForEdit", listDepartments.get(0)); %>
            <!--  debug Передавать надо выбранного работника  -->
            <!--  Только как определить, какой из них выбран???  -->

        </table>

        <%
            //если контроллера в параметрах нет, то
            // создаем его для первой страницы
            // если есть, то используем его для считанной страницы ()
            System.out.println("в конце DepartmentList.jsp - пейджинация");  // debug

            /*PaginationController*/ paginationController = (PaginationController) request.getSession().getAttribute("paginationControllerForDep");

            /*int pageNumber;*/
            if (request.getParameter("page") != null) {
                pageNumber = Integer.parseInt(request.getParameter("page"));
            } else {
                pageNumber = 1;
            }
            System.out.println("pageNumber" + pageNumber);

            paginationController.setCurrentPageNumber(pageNumber);
        %>
        <%= paginationController.makePagingLinks("DepartmentsList.jsp", "")%>

    </form>
</article>
</body>
</html>
