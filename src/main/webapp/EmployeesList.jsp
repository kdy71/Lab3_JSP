<%@ page import="controller.Actions" %>
<%@ page import="controller.EmployeeProcessors.EmployeeModification" %>
<%@ page import="controller.PaginationController" %>
<%@ page import="model.Employee" %>
<%@ page import="model.OracleDataAccess" %>
<%@ page import="java.util.ArrayList" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
<%@ page errorPage="ErrorPage.jsp" %>
--%>

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

    <table border="1">
        <%
            System.out.println("-- в EmployeesList.jsp--");

            //если в реквесте параметр ренью не пустой, то нужен вывод всего
            if (request.getParameter("renew") != null
                    && request.getParameter("renew").equals("yes")) {
                System.out.println(request.getParameter("renew"));

                request.getSession().setAttribute("afterSearch", "no");
                request.getSession().setAttribute("foundEmployees", null);

                //обновляем PaginationController, иначе будет старый перечень страниц
                int employeesCount = OracleDataAccess.getInstance().getTotalCountOfEmployees();
                int employeesPerPage = 5;
                int pageNumber = 1;
                PaginationController paginationController = new PaginationController(employeesCount, employeesPerPage, pageNumber);
                request.getSession().setAttribute("paginationController", paginationController);
            }

    if (request.getSession().getAttribute("afterSearch").equals("yes")) {
        %><h3>Результаты поиска:</h3><%
    }else if(request.getSession().getAttribute("afterSearch").equals("no")
            && (request.getSession().getAttribute("foundEmployees") != null)) {
        %><h3>Результаты поиска:</h3><%
    } else {
    %><h3>Список всех сотрудников:</h3><%
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

            if (request.getSession().getAttribute("afterSearch").equals("yes")) {
                System.out.println("ВАРИАНТ 3 ЕСТЬ НОВЫЙ ПОИСК");
                listEmployees = (ArrayList<Employee>) request.getSession().getAttribute("foundEmployeesForPage");
            } else if (request.getSession().getAttribute("afterSearch").equals("no")
                    && (request.getSession().getAttribute("foundEmployees") == null
                    || request.getSession().getAttribute("foundEmployees").toString().isEmpty())) {
                System.out.println("ВАРИАНТ 1 ПОИСКА НЕ БЫЛО НИ РАЗУ");
                listEmployees = (ArrayList<Employee>) request.getSession().getAttribute("allEmployees");
            } else if (request.getSession().getAttribute("afterSearch").equals("no")
                    && (!request.getSession().getAttribute("foundEmployees").toString().isEmpty())) {
                System.out.println("ВАРИАНТ 2 СЕЙЧАС ПОИСКА НЕ БЫЛО, НО БЫЛ ДО ЭТОГО");
                listEmployees = (ArrayList<Employee>) request.getSession().getAttribute("foundEmployeesForPage");
                System.out.println(listEmployees);
            }


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

    </table>

    <%--Links under the table.--%>
    <%
        PaginationController paginationController = (PaginationController) request.getSession().getAttribute("paginationController");
        int pageNumber;
        if (request.getParameter("page") != null) {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        } else {
            pageNumber = 1;
        }
        paginationController.setCurrentPageNumber(pageNumber);
    %>
    <%= paginationController.makePagingLinks("EmployeesList.jsp", "")%>

    <%
        if (request.getSession().getAttribute("afterSearch").equals("yes")
                || (request.getSession().getAttribute("afterSearch").equals("no")
                && (request.getSession().getAttribute("foundEmployees") != null))) {
    %><a href='EmployeesList.jsp?renew=yes'><br/>Сбросить результаты поиска<br/></a><%
    }%>

</article>
</body>
</html>