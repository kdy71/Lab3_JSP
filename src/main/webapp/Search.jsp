<%@ page import="model.Employee" %>
<%@ page import="model.OracleDataAccess" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Util_dates" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 01.06.2016
  Time: 11:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Search</title>
    <meta charset='utf-8'>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script>
        $(function () {
            $("#datepicker").datepicker();
        });
    </script>

    <script>
        $(function () {
            $("#datepicker1").datepicker();
        });
    </script>

</head>

<body>
<div class="search">
    <h3><b>Поиск:</b></h3>

    <form name="search" action='EmployeesList.jsp' method='post' accept-charset="utf-8">

        <%
            request.setCharacterEncoding("utf-8");
            List<Employee> employeesList = new ArrayList<Employee>();
            String namePattern = request.getParameter("namePattern");
            String jobPattern = request.getParameter("jobPattern");
            String salMinPattern = request.getParameter("salMinPattern");
//            Integer salMin;
            Float salMin;
            String salMaxPattern = request.getParameter("salMaxPattern");
//            Integer salMax;
            Float salMax;
            String depPattern = request.getParameter("depPattern");
            String manPattern = request.getParameter("manPattern");
            String dateMinPattern = request.getParameter("dateMinPattern");
            Date dateMin;
            String dateMaxPattern = request.getParameter("dateMaxPattern");
            Date dateMax;

            if (namePattern != null  &&  namePattern.trim().equals("")) {   namePattern = null;  }
            if (jobPattern  != null  &&  jobPattern.trim().equals(""))  {   jobPattern = null;   }
            if (depPattern  != null  &&  depPattern.trim().equals(""))  {   depPattern = null;   }
            if (manPattern  != null  &&  manPattern.trim().equals(""))  {   manPattern = null;   }
            if (salMinPattern == null || salMinPattern.isEmpty()) {
                salMin = null;
            } else {
                salMin = Float.parseFloat(request.getParameter("salMinPattern"));
            }
            if (salMaxPattern == null || salMaxPattern.isEmpty()) {
                salMax = null;
            } else {
                salMax = Float.parseFloat(request.getParameter("salMaxPattern"));
            }

        %>

        Имя:
<!--        <input type='text' size='20' name='namePattern'   placeholder="Имя работника"/>   -->
        <input type='text' size='20' name='namePattern'  <%= namePattern==null ? "placeholder=\"Имя работника\"" : "value=\"" +namePattern +"\""   %> />
        <br/>
        Должность:
<!--        <input type='text' size='20' name='jobPattern' placeholder="Должность"/>  -->
        <input type='text' size='20' name='jobPattern' <%= jobPattern==null ? "placeholder=\"Должность\"" : "value=\"" +jobPattern +"\""   %> />
        <br/>

        Зарплата:
<!--        <input type="number" size='20' name='salMinPattern' placeholder="От"/>   -->
        <input type="number" size='20' name='salMinPattern' <%= salMin==null ? "placeholder=\"От\"" : "value=\"" +salMin +"\"" %> />
<!--        <input type="number" size='20' name='salMaxPattern' placeholder="До"/>  -->
        <input type="number" size='20' name='salMaxPattern' <%= salMax==null ? "placeholder=\"До\"" : "value=\"" +salMax +"\""   %> />
        <br/>

        Отдел:
<!--        <input type='text' size='20' name='depPattern' placeholder="Отдел"/>    -->
        <input type='text' size='20' name='depPattern'  <%= depPattern==null ? "placeholder=\"Отдел\"" : "value=\"" +depPattern +"\""   %> />

        <br/>Менеджер:
<!--        <input type='text' size='20' name='manPattern' placeholder="Mенеджер"/>   -->
        <input type='text' size='20' name='manPattern'  <%= manPattern==null ? "placeholder=\"Mенеджер\"" : "value=\"" +manPattern +"\"" %>  />

        <br/>Трудоустройство:
<!--        <input type='text' id='datepicker' name='dateMinPattern' placeholder="Начиная с"/>  -->
        <input type='text' id='datepicker' name='dateMinPattern' <%= dateMinPattern==null ? "placeholder=\"Начиная с\"" : "value=\"" +dateMinPattern +"\"" %> />
<!--        <input type='text' id='datepicker1' name='dateMaxPattern' placeholder="Заканчивая"/>  -->
        <input type='text' id='datepicker1' name='dateMaxPattern' <%= dateMaxPattern==null ? "placeholder=\"Заканчивая\"" : "value=\"" +dateMaxPattern +"\"" %>  />

        <input type='submit' value='Search'/>

        <%  /*
            request.setCharacterEncoding("utf-8");
            List<Employee> employeesList = new ArrayList<Employee>();
            String namePattern;
            String jobPattern;
            String salMinPattern;
//            Integer salMin;
            Float salMin;
            String salMaxPattern;
//            Integer salMax;
            Float salMax;
            String depPattern;
            String manPattern;
            String dateMinPattern;
            Date dateMin;
            String dateMaxPattern;
            Date dateMax;  */

            namePattern = request.getParameter("namePattern");
/*            if (namePattern == null) {
                namePattern = "";
            } */

            jobPattern = request.getParameter("jobPattern");
/*            if (jobPattern == null) {
                jobPattern = "";
            } */

            salMinPattern = request.getParameter("salMinPattern");
            if (salMinPattern == null || salMinPattern.isEmpty()) {
                salMin = null;
            } else {
                salMin = Float.parseFloat(request.getParameter("salMinPattern"));
            }

            salMaxPattern = request.getParameter("salMaxPattern");
            if (salMaxPattern == null || salMaxPattern.isEmpty()) {
                salMax = null;
            } else {
                salMax = Float.parseFloat(request.getParameter("salMaxPattern"));
            }

            depPattern = request.getParameter("depPattern");
/*            if (depPattern == null) {
                depPattern = "";
            } */

            manPattern = request.getParameter("manPattern");
/*            if (manPattern == null) {
                manPattern = "";
            }  */

            dateMinPattern = request.getParameter("dateMinPattern");
            if (dateMinPattern == null || dateMinPattern.isEmpty()) {
//                dateMin = new Date(0); //минимальная дата
                dateMin = null;
            } else {
                dateMin = Util_dates.str2Date(dateMinPattern);
            }

            dateMaxPattern = request.getParameter("dateMaxPattern");
            if (dateMaxPattern == null || dateMaxPattern.isEmpty()) {
//                dateMax = new Date(100000000000000L); //5138 year
//                dateMax = new Date(0); //5138 year
                dateMax = null; //
            } else {
                dateMax = Util_dates.str2Date(dateMaxPattern);
            }

/*            System.out.println("  -- We are into Search.jsp ---- "); // debug
            System.out.println("namePattern= "+namePattern);
            System.out.println("jobPattern="+jobPattern);
            System.out.println("salMin= "+salMin);
            System.out.println("salMax= "+salMax);
            System.out.println("depPattern= "+depPattern);
            System.out.println("manPattern= "+manPattern);
            System.out.println("dateMin= "+dateMin);
            System.out.println("dateMax= "+dateMax);   */

//            System.out.println("  go to find  - findEmployees(namePattern, jobPattern, salMin, salMax, depPattern, manPattern, dateMin, dateMax)..."); //debug
//            employeesList = OracleDataAccess.getInstance().findEmployees(namePattern, jobPattern, salMin, salMax, depPattern, manPattern, dateMin, dateMax);
            employeesList = OracleDataAccess.getInstance().getEmployeesFiltered(namePattern, jobPattern,
                            salMin, salMax, null, null, dateMin, dateMax, manPattern, depPattern);
            request.setAttribute("foundEmployees", employeesList);
/*
            for (int i = 0; i < employeesList.size(); i++) { //debug
                System.out.println(i+"- "+employeesList.get(i));
            }
*/
        %>

    </form>

</div>
</body>
</html>
