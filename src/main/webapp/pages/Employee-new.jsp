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
        @import url("../css/style.css");
    </style>
</head>

<body>

<jsp:include page="Header.jsp"/>
<jsp:include page="Menu.jsp"/>
<jsp:include page="Footer.jsp"/>

<article>
    <h3>Добавить нового сотрудника</h3>

    <form action = '/aid/Servlet' method = 'post'> <!--исправить адрес к сервлету и метод-->

        Имя сотрудника:<br/>
        <input type='text' size='50' name='empName'>
        <br/>
        <br/>

        <!-- организовать выбор из массива всех отделов-->
        Отдел сотрудника:<br/>
        <select size='3' name='departmentName'>
            <option disabled>Выберите отдел</option>
            <option value='Отдел 1'>Отдел 1</option>
            <option value='Отдел 1'>Отдел 2</option>
        </select>

        <!-- организовать выбор из массива всех сотрудников-->
        <br/> <br/>
        Менеджер сотрудника:<br/>
        <select size='3' name='managerName'> <!--атрибут принимает имя менеджера, а в БД мы работаем по ID-->
            <option disabled>Выберите менеджера</option>
            <option value='Менеджер 1'>Менеджер 1</option>
            <option value='Менеджер 1'>Менеджер 2</option>
        </select>

        <br/> <br/>
        Позиция сотрудника:<br/>
        <input type='text' size='50' name='jobName'>

        <br/> <br/>
        Зарплата сотрудника:<br/>
        <input type='text' size='50' name='salary'>

        <br/> <br/>
        Дата трудоустройства:<br/>
        <input type='date' name='dateIn' date>

        <br/> <br/>
        <p><input type='submit' value = 'Save new employee'></p>

    </form>
</article>
</body>
</html>
