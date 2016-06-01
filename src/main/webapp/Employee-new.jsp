<%@ page import="java.util.ArrayList" %>
<%@ page import="controller.EmployeeProcessors.EmployeeModification" %>
<%@ page import="controller.Actions" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ page import="model.*" %><%--
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
            $("#datepicker").datepicker();
        });
    </script>


</head>

<body>

<jsp:include page="Header.jsp"/>
<jsp:include page="Menu.jsp"/>
<jsp:include page="Footer.jsp"/>

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
//        String strDateIn =  Util_dates.dat2Str(new Date());
        if (isEdit) {
            employeeForEdit = (Employee) session.getAttribute(Actions.EDIT_EMPLOYEE);
            employeeForEditId = employeeForEdit.getId();
            employeeForEditDepId = employeeForEdit.getDepartmentId();
            employeeForEditManagerId = employeeForEdit.getManagerId();
//            employeeDateIn =employeeForEdit.getDateIn();
//            strDateIn =  Util_dates.dat2Str(employeeForEdit.getDateIn());

            System.out.println(employeeForEdit.toString());  // debug
        }
    %>

    <h3><%= isEdit ? "Редактирование информации о сотруднике:" : "Добавление нового сотрудника:" %>
    </h3>

    <form name="addEmployeeForm" action=<%= isEdit ?
            "ServletStart?action="+Actions.UPDATE_EMPLOYEE +"&"+ EmployeeModification.EMP_ID +"=" + employeeForEdit.getId() :
            "ServletStart?action=createEmployee" %> method="post" accept-charset="utf-8">

        <% System.out.println("--- 2 --- ");  // debug %>
        Имя сотрудника:<br/>
        <input type='text' size='50' id="<%=EmployeeModification.EMP_ID%>"
               name='<%=EmployeeModification.EMP_NAME%>'
               <%= isEdit ? "value=\"" + employeeForEdit.getName() +"\"" : "placeholder=\"Name of employee\"" %>required/>
        <br/>
        <br/>

        Отдел сотрудника:<br/>
        <select size='5' id="<%=EmployeeModification.EMP_ID%>" name='<%=EmployeeModification.DEPARTMENT_ID%>'>
            <option disabled>Выберите отдел</option>
            <% for (Department currDep : listDepartments) { %>
            <option <%= (currDep.getId() == employeeForEditDepId) ? " selected " : "" %>
                    value=<%=currDep.getId()%>><%=currDep.getName()%>
            </option>
            <% } %>
        </select>

        <% System.out.println("--- 3 --- ");  // debug %>
        <br/> <br/>
        Менеджер сотрудника:<br/>
        <select size='5' id="<%=EmployeeModification.EMP_ID%>" name='<%=EmployeeModification.MANAGER_ID%>'>
            <option disabled>Выберите менеджера</option>
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
        Позиция сотрудника:<br/>
        <%--<input type='text' size='50' name='jobName'required/>--%>
        <input type='text' size='50' id="<%=EmployeeModification.EMP_ID%>"
               name="<%=EmployeeModification.JOB_NAME%>"
                <%= isEdit ? "value=\"" + employeeForEdit.getJobName() + "\"" : "placeholder=\"Job name\"" %> required/>

        <% System.out.println("--- 4 --- ");  // debug %>

        <br/> <br/>
        Зарплата сотрудника:<br/>
        <input type='number' size='50' id="<%=EmployeeModification.EMP_ID%>" name="<%=EmployeeModification.SALARY%>"
                <%= isEdit ? "value=" + employeeForEdit.getSalaryAsString() : "placeholder=\"Salary size\"" %>
               required/>

        <% if (employeeForEdit != null) {
            System.out.println("employeeForEdit.getDateIn()= " + employeeForEdit.getDateIn());
        } // debug %>

        <br/> <br/>
        Дата трудоустройства:<br/>

<%--        <input type='date' id="<%=EmployeeModification.EMP_ID%>" name='<%=EmployeeModification.DATE_IN%>'
                <%= isEdit ? "value=" + employeeForEdit.getDateIn() : "placeholder=\"Date of work beginning\"" %> required/>--%>
        Date: <input type='text' id='datepicker' name='<%=EmployeeModification.DATE_IN%>'
            <%= isEdit ? "value=" + employeeForEdit.getDateIn() : "placeholder=\"Date of work beginning\"" %> required/>


        <br/> <br/>
        <p><input type='submit' value='Save changes'></p>

    </form>
</article>
</body>
</html>
