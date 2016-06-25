<%@ page import="model.Department" %>
<%@ page import="model.Employee" %>
<%@ page import="model.EmployeeImpl" %>
<%@ page import="model.OracleDataAccess" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="controller.EmployeeProcessors.EmployeeModification" %>
<%@ page import="controller.Actions" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ page import="model.*" %>
<%@ page errorPage="ErrorPage.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 11.05.2016
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset='utf-8'>
    <title>Create employee</title>
    <script>
        document.createElement('article');
    </script>
    <style type="text/css">
        @import url("css/style.css");
    </style>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script>
        $(function () {
            $("#datepicker0").datepicker({
                firstDay: 1
            });
        });
    </script>

</head>

<body>

<jsp:include page="Header.jsp"/>
<jsp:include page="Menu.jsp"/>
<jsp:include page="Footer.jsp"/>
<jsp:include page="InsteadOfSearch.jsp"/>

<article>
    <%
        ArrayList<Employee> listEmployees = (ArrayList<Employee>) OracleDataAccess.getInstance().getAllEmployees();
        ArrayList<Department> listDepartments = (ArrayList<Department>) OracleDataAccess.getInstance().getAllDepartments();

        System.out.println("--- We are into Employee-add_edit.jsp  ---");
        String action = request.getParameter("action");
        System.out.println("action= " + action);  // debug
        Boolean isEdit = (Actions.EDIT_EMPLOYEE.equals(action));
        Employee employeeForEdit = null;
        Integer employeeForEditId = 0;
        Integer employeeForEditDepId = 0;
        Integer employeeForEditManagerId = 0;
//        Date employeeDateIn = new Date();
//        String strDateIn =  UtilDates.dateToString(new Date());
        if (isEdit) {
            employeeForEdit = (Employee) session.getAttribute(Actions.EDIT_EMPLOYEE);
            employeeForEditId = employeeForEdit.getId();
            employeeForEditDepId = employeeForEdit.getDepartmentId();
            employeeForEditManagerId = employeeForEdit.getManagerId();
//            employeeDateIn =employeeForEdit.getDateIn();
//            strDateIn =  UtilDates.dateToString(employeeForEdit.getDateIn());

            System.out.println(employeeForEdit.toString());  // debug
        }
    %>

    <h3><%= isEdit ? "Редактирование информации о сотруднике:" : "Добавление нового сотрудника:" %>
    </h3>

    <form name="addEmployeeForm" action=<%= isEdit ?
            "ServletStart?action="+Actions.UPDATE_EMPLOYEE +"&"+ EmployeeModification.EMP_ID +"=" + employeeForEdit.getId() :
            "ServletStart?action=createEmployee" %> method="post" accept-charset="utf-8">

        <% System.out.println("--- 2 --- ");  // debug %>
        Имя:<br/>
        <input type='text' size='50' id="<%=EmployeeModification.EMP_ID%>"
               name='<%=EmployeeModification.EMP_NAME%>'
               <%= isEdit ? "value=\"" + employeeForEdit.getName() +"\"" : "placeholder=\"Введите имя работника\"" %>required/>
        <br/>
        <br/>

        Отдел:<br/>
        <select size='5' id="<%=EmployeeModification.EMP_ID%>" name='<%=EmployeeModification.DEPARTMENT_ID%>' required>
            <option disabled>Выберите отдел работника</option>
            <% for (Department currDep : listDepartments) { %>
            <option <%= (currDep.getId() == employeeForEditDepId) ? " selected " : "" %>
                    value=<%=currDep.getId()%>><%=currDep.getName()%>
            </option>
            <% } %>
        </select>

        <% System.out.println("--- 3 --- ");  // debug %>
        <br/> <br/>
        Руководитель:<br/>
        <select size='5' id="<%=EmployeeModification.EMP_ID%>" name='<%=EmployeeModification.MANAGER_ID%>' required>
            <option disabled>Выберите руководителя</option>
            <% for (Employee currEmp : listEmployees) {
                if (currEmp.equals(employeeForEdit)) {
                    continue;
                }
            %>
            <option  <%= (currEmp.getId() == employeeForEditManagerId) ? " selected " : "" %>
                    value=<%=currEmp.getId()%>><%= currEmp.getName()%>
            </option>
            <% }
            %>
        </select>

        <br/> <br/>
        Должность:<br/>
        <%--<input type='text' size='50' name='jobName'required/>--%>
        <input type='text' size='50' id="<%=EmployeeModification.EMP_ID%>"
               name="<%=EmployeeModification.JOB_NAME%>"
                <%= isEdit ? "value=\"" + employeeForEdit.getJobName() + "\"" : "placeholder=\"Введите должность работника\"" %>
               required/>

        <% System.out.println("--- 4 --- ");  // debug %>

        <br/> <br/>
        Зарплата:<br/>
        <input type='number' size='50' id="<%=EmployeeModification.EMP_ID%>" name="<%=EmployeeModification.SALARY%>"
                <%= isEdit ? "value=" + employeeForEdit.getSalaryAsString() : "placeholder=\"Введите размер зарплаты\"" %>
               required/>

        <% if (employeeForEdit != null) {
            System.out.println("employeeForEdit.getDateIn()= " + employeeForEdit.getDateIn());
        } // debug %>

        <br/> <br/>
        Дата трудоустройства:<br/>

        <%--        <input type='date' id="<%=EmployeeModification.EMP_ID%>" name='<%=EmployeeModification.DATE_IN%>'
                        <%= isEdit ? "value=" + employeeForEdit.getDateIn() : "placeholder=\"Date of work beginning\"" %> required/>--%>
        <input type='text' id='datepicker0' name='<%=EmployeeModification.DATE_IN%>'
        <%--
                    <%= isEdit ? "value=" + employeeForEdit.getDateIn() : "placeholder=\"Date of work beginning\"" %> required/>
        --%>
                <%= isEdit ? "value=" + UtilDates.dateToString(employeeForEdit.getDateIn()) : "value=" + UtilDates.dateToString(new Date()) %>
               readonly required/>

        <br/> <br/>
        <p><input type='submit' value='Сохранить данные'></p>

    </form>
</article>
</body>
</html>
