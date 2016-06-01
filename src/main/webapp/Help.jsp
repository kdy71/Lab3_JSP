<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 11.05.2016
  Time: 19:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset='utf-8'>
    <title>Help</title>
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
<jsp:include page="Search.jsp"/>

<article>

    <h3><b>Сравка</b></h3>

    Для <b>добавления нового работника</b> - в меню слева выберите <b>Добавить сотрудника</b>
    <br/><br/>
    Для <b>добавления нового отдела</b> - в меню слева выберите <b>Добавить отдел</b>
    <br/><br/>
    Для <b>редактирования данных о работнике</b> - в меню слева выберите <b>Список сотрудников</b>, после выберите сотрудника для редактирования
    <br/><br/>
    Для <b>редактирования данных об отделе</b> - в меню слева выберите <b>Список отделов</b>, после выберите отдел для редактирования

</article>
</body>
</html>
