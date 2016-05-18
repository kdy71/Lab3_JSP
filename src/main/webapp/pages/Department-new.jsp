<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 11.05.2016
  Time: 20:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <title>Cоздать отдел</title>
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

    <h3>Добавить новый отдел</h3>

    <form action='/aid/Servlet' method='post'> <!--исправить адрес к сервлету и метод-->

        Название отдела:<br/>
        <input type='text' size='50' name='departmentName'>
        <br/>
        <br/>
        Описание отдела:<br/>
        <textarea cols='50' rows='5' name='description'></textarea>

        <p><input type='submit' value='Save new department'></p>

    </form>
</article>
</body>
</html>
