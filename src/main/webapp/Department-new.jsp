<%@ page import="controller.DepartmentProcessors.DepartmentModification" %><%--
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
        @import url("css/style.css");
    </style>
</head>

<body>

<jsp:include page="Header.jsp"/>
<jsp:include page="Menu.jsp"/>
<jsp:include page="Footer.jsp"/>

<article>

    <h3>Добавить новый отдел</h3>

    <form name="addDepartmentForm" action='ServletStart?action=createDepartment' method='post'>

        Название отдела:<br/>
        <input type='text' size='50' id="<%=DepartmentModification.DEPARTMENT_ID%>"
               name='<%=DepartmentModification.DEPARTMENT_NAME%>'
               placeholder="Name of department" required/>
        <br/>
        <br/>
        Описание отдела:<br/>
        <%--<textarea cols='50' rows='5' name='description'></textarea>--%>
        <input type='text' size='50' id="<%=DepartmentModification.DEPARTMENT_ID%>"
               name='<%=DepartmentModification.DESCRIPTION%>'
               placeholder="Description" required/>

        <p><input type='submit' value='Save new department' required/></p>

    </form>
</article>
</body>
</html>
