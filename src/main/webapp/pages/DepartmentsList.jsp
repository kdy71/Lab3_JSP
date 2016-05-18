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
        @import url("../css/style.css");
    </style>
</head>

<body>

<jsp:include page="Header.jsp"/>
<jsp:include page="Menu.jsp"/>
<jsp:include page="Footer.jsp"/>

<article>
    <h3>Список всех отделов в организации</h3>

    <form action = '/aid/Servlet' method = 'post'> <!--исправить адрес к сервлету и метод-->
        <p><input name='department' type='radio' value='Отдел 1'>Отдел 1</p>
        <p><input name='department' type='radio' value='Отдел 2'>Отдел 2</p>

        <p><input type='submit' value = 'Choose department'></p>

    </form>
</article>
</body>
</html>
