<%@ page import="java.util.ArrayList" %>
<%@ page import="model.*" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Dudkin Alexander, Dmitry Khoruzhenko
  Date: 15.04.2016
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <meta charset='utf-8'>
    <title>Редактировать сотрудника</title>
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
            left: 200px; /* Положение от края */
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
            left: 200px;
            font-family:Calibri;
        }

        article {
            background: lightgoldenrodyellow;
            padding: 10px;
            margin-left: 430px;
            width: 410px;
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
    <h3>Редактирование информации о сотруднике:</h3>
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
    <form action = 'controller/ServletStart' method = 'post'> <!--исправить адрес к сервлету и метод-->

        <%
//            Integer empId = (Integer) session.getAttribute("empId");
            Employee employeeForEdit = (Employee) session.getAttribute("employeeForEdit");
            if (employeeForEdit == null) {
                employeeForEdit = new EmployeeImpl();}
            ArrayList<Employee>   listEmployees = (ArrayList<Employee>) OracleDataAccess.getInstance().getAllEmployees();
            ArrayList<Department> listDepartments = (ArrayList<Department>) OracleDataAccess.getInstance().getAllDepartments();
        %>

        Имя сотрудника:<br/>
        <input type='text' size='50' name='empName' value= <%=employeeForEdit.getName()%> >
        <br/>
        <br/>

        <!-- организовать выбор из массива всех отделов-->
        Отдел сотрудника:<br/>
        <select size='3' name='departmentName'>
<!--            <option disabled>Выберите отдел</option>   -->
            <%  for (Department currDep : listDepartments ) { %>
            <option value= <%=currDep.getId()%> >  <%=currDep.getName()%>  </option>
            <%    }      %>
<!--
            <option value='Отдел 1'>Отдел 1</option>
            <option value='Отдел 1'>Отдел 2</option>
            -->
        </select>

        <!-- организовать выбор из массива всех сотрудников-->
        <br/> <br/>
        Менеджер сотрудника:<br/>
        <select size='3' name='managerName'> <!--атрибут принимает имя менеджера, а в БД мы работаем по ID-->
      <%    for (Employee currEmp:listEmployees) {
                if (currEmp.equals(employeeForEdit)) {continue;}
      %>
                <option value = <%=currEmp.getId()%> >   <%=   currEmp.getName()%></option>
      <%   }
      %>

<!--        <option value='Менеджер 1'>Менеджер 1</option>
            <option value='Менеджер 1'>Менеджер 2</option>    -->
        </select>

        <br/> <br/>
        Позиция сотрудника:<br/>
        <input type='text' size='50' name='jobName' value=<%=employeeForEdit.getJobName()%> >

        <br/> <br/>
        Зарплата сотрудника:<br/>
        <input type='text' size='50' name='salary' value=<%=employeeForEdit.getSalary()%> >

        <br/> <br/>
        Дата трудоустройства:<br/>
        <input type='date' name='dateIn'  value=<%=employeeForEdit.getDateIn()%> date>

        <br/> <br/>
        <p><input type='submit' value = 'Save changes'></p>

    </form>
</article>
</body>
</html>