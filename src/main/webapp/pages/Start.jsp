<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset='utf-8'>
    <title>Стартовая страница</title>
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
            font-family: Calibri;
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
            font-family: Calibri;
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
            font-family: Calibri;
        }
    </style>
</head>

<body>

<header>
    <h3><b>Информационная система отдела кадров</b></h3>
</header>

<aside>
    <b>Опции: </b><br/>
    <a href='html/EmployeesList.html'>Cписок сотрудников<br/></a>
    <a href='html/DepartmentsList.html'>Список отделов<br/></a>
    <a href='html/Employee-new.html'>Добавить сотрудника<br/></a>
    <a href='html/Department-new.html'>Добавить отдел<br/></a>
    <a href='html/Help.html'>Помощь<br/></a>
</aside>
<article>
    Приложение обращается к базе данных, которая содержит информацию в виде двух таблиц:
    <ul>Таблица 1. Работники
        <li>Имя работника</li>
        <li>Его отдел</li>
        <li>Его менеджер</li>
        <li>Его позиция</li>
        <li>Его зарплата</li>
        <li>Его дата трудоустройства</li>
    </ul>
    <ul>Таблица 2. Отделы
        <li>Название отдела</li>
        <li>Описание отдела</li>
    </ul>
</article>
</body>
</html>