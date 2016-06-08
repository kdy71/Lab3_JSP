<%@ page import="controller.DepartmentProcessors.DepartmentModification" %>
<%@ page import="model.Department" %>
<%@ page import="controller.Actions" %><%--
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
<jsp:include page="InsteadOfSearch.jsp"/>

<article>
    <%
        System.out.println("--- We are into Department-new.jsp  ---");  // debug
        String action = request.getParameter("action");
        System.out.println("action= " + action);  // debug
        Boolean isEdit = (Actions.EDIT_DEPARTMENT.equals(action));
        Department departmentForEdit = null;
        String depForEditName = "";
        String depForEditDescription = "";
        if(isEdit) {
            departmentForEdit = (Department) session.getAttribute(Actions.EDIT_DEPARTMENT);
            depForEditName = departmentForEdit.getName();
            depForEditDescription = departmentForEdit.getDescription();
        }
    %>

    <h3><%= isEdit ? "Редактирование информации об отделе:" : "Добавление нового отдела:" %>     </h3>

    <!--    <form name="addDepartmentForm" action='ServletStart?action=createDepartment' method='post' accept-charset="utf-8">  -->
    <form name="addDepartmentForm" action=<%= isEdit ?
            "ServletStart?action="+Actions.UPDATE_DEPARTMENT +"&"+ DepartmentModification.DEPARTMENT_ID +"=" + departmentForEdit.getId() :
            "ServletStart?action=createDepartment" %> method="post" accept-charset="utf-8">


        Название отдела:<br/>
        <input type='text' size='50' id="<%=DepartmentModification.DEPARTMENT_ID%>"
               name='<%=DepartmentModification.DEPARTMENT_NAME%>'
               <%= isEdit ? "value=\"" + departmentForEdit.getName() +"\"" : "placeholder=\"Name of department\"" %> required/>
        <br/>
        <br/>
        Описание отдела:<br/>
        <%--<textarea cols='50' rows='5' name='description'></textarea>--%>
        <input type='text' size='50' id="<%=DepartmentModification.DEPARTMENT_ID%>"
               name='<%=DepartmentModification.DESCRIPTION%>'
               <%= isEdit ? "value=\"" + departmentForEdit.getDescription() +"\"" : "placeholder=\"Description\"" %> required/>


        <p><input type='submit' value='Save changes' required/></p>

    </form>
</article>
</body>
</html>