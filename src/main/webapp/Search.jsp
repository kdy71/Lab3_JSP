<%@ page import="model.Employee" %>
<%@ page import="model.OracleDataAccess" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="model.UtilDates" %>
<%@ page import="controller.PaginationController" %>
<%@ page import="controller.EmployeeProcessors.EmpListProcessor" %>
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
            $("#datepicker").datepicker({
                firstDay: 1
            });
        });
    </script>

    <script>
        $(function () {
            $("#datepicker1").datepicker({
                firstDay: 1
            });
        });
    </script>

</head>

<body>
<div class="search">
    <h3><b>Поиск работника:</b></h3>

<%--    <form name="search" action='EmployeesList.jsp' method='post' accept-charset="utf-8">   --%>
    <form name="search" action='ServletStart?action=listEmployees' method='post' accept-charset="utf-8">

        <%
            System.out.println("--------------- enter to Search.jsp ----------------"); // debug

            request.setCharacterEncoding("utf-8");

            // Достаем из реквеста все паттерны
            String namePattern = request.getParameter("namePattern");
            String jobPattern = request.getParameter("jobPattern");
            String salMinPattern = request.getParameter("salMinPattern");
            Float salMin;
            String salMaxPattern = request.getParameter("salMaxPattern");
            Float salMax;
            String depPattern = request.getParameter("depPattern");
            String manPattern = request.getParameter("manPattern");
            String dateMinPattern = request.getParameter("dateMinPattern");
            Date dateMin;
            String dateMaxPattern = request.getParameter("dateMaxPattern");
            Date dateMax;

            System.out.println(" -1- "); // debug
            // Достаем из сессии паттерны (в случае, если в реквесте они пустые)
            if (namePattern   == null) {  namePattern   = (String) request.getSession().getAttribute("namePattern");  }
            if (jobPattern    == null) { jobPattern     = (String) request.getSession().getAttribute("jobPattern");    }
            if (salMinPattern == null) { salMinPattern  = (String) request.getSession().getAttribute("salMinPattern"); }
            if (salMaxPattern == null) { salMaxPattern  = (String) request.getSession().getAttribute("salMaxPattern"); }
            if (depPattern    == null) { depPattern     = (String) request.getSession().getAttribute("depPattern"); }
            if (manPattern    == null) { manPattern     = (String) request.getSession().getAttribute("manPattern"); }
            if (dateMinPattern== null) { dateMinPattern = (String) request.getSession().getAttribute("dateMinPattern"); }
            if (dateMaxPattern== null) { dateMaxPattern = (String) request.getSession().getAttribute("dateMaxPattern"); }
            System.out.println("  - 1 - salMinPattern from Session = "+(String) request.getSession().getAttribute("salMinPattern")); // debug
            System.out.println("  - 1 - request.getParameter(\"salMinPattern\"= "+request.getParameter("salMinPattern"));

            if (salMinPattern == null || salMinPattern.isEmpty()) {
                salMin = null;
            } else {
                salMin = Float.parseFloat(salMinPattern);
            }
            if (salMaxPattern == null || salMaxPattern.isEmpty()) {
                salMax = null;
            } else {
                salMax = Float.parseFloat(request.getParameter("salMaxPattern"));
            }
/*
            if (dateMinPattern == null || dateMinPattern.isEmpty()) {
                dateMin = null;
            } else {
                dateMin = UtilDates.stringToDate(dateMinPattern);
            }
            if (dateMaxPattern == null || dateMaxPattern.isEmpty()) {
                dateMax = null; //
            } else {
                dateMax = UtilDates.stringToDate(dateMaxPattern);
            }
*/
            if (namePattern != null  &&  namePattern.trim().equals("")) {   namePattern = null;  }
            if (jobPattern  != null  &&  jobPattern.trim().equals(""))  {   jobPattern = null;   }
            if (depPattern  != null  &&  depPattern.trim().equals(""))  {   depPattern = null;   }
            if (manPattern  != null  &&  manPattern.trim().equals(""))  {   manPattern = null;   }

            // Если была команда очистить шаблоны поиска
            if ( "yes".equals(request.getParameter("ClearSearchPatterns"))) {
                namePattern = null;
                jobPattern = null;
                salMin = null;
                salMax = null;
                depPattern = null;
                manPattern = null;
                dateMinPattern = null;
                dateMaxPattern = null;
            }


            System.out.println("salMin= "+salMin); // debug
        %>

        <p>Имя:<br/>
<!--        <input type='text' size='20' name='namePattern'   placeholder="Имя работника"/>   -->
        <input type='text' size='20' name='namePattern'  <%= namePattern==null ? "placeholder=\"Имя работника\"" : "value=\"" +namePattern +"\""   %> />
        </p>
        <p>
        Должность:<br/>
        <!--        <input type='text' size='20' name='jobPattern' placeholder="Должность"/>  -->
        <input type='text' size='20' name='jobPattern' <%= jobPattern==null ? "placeholder=\"Должность\"" : "value=\"" +jobPattern +"\""   %> />
        </p>

        <p>Зарплата:<br/>
<!--        <input type="number" size='20' name='salMinPattern' placeholder="От"/>   -->
        <input type="number" size='20' name='salMinPattern' <%= salMin==null ? "placeholder=\"От\"" : "value=\"" +salMin +"\"" %> />
<!--        <input type="number" size='20' name='salMaxPattern' placeholder="До"/>  -->
        <input type="number" size='20' name='salMaxPattern' <%= salMax==null ? "placeholder=\"До\"" : "value=\"" +salMax +"\""   %> />
        </p>

        <p>Отдел:<br/>
<!--        <input type='text' size='20' name='depPattern' placeholder="Отдел"/>    -->
        <input type='text' size='20' name='depPattern'  <%= depPattern==null ? "placeholder=\"Отдел\"" : "value=\"" +depPattern +"\""   %> />
        </p>

        <p>Менеджер:<br/>
<!--        <input type='text' size='20' name='manPattern' placeholder="Mенеджер"/>   -->
        <input type='text' size='20' name='manPattern'  <%= manPattern==null ? "placeholder=\"Mенеджер\"" : "value=\"" +manPattern +"\"" %>  />
        </p>

        <p>Трудоустройство:<br/>
<!--        <input type='text' id='datepicker' name='dateMinPattern' placeholder="Начиная с"/>  -->
        <input type='text' id='datepicker' name='dateMinPattern' <%= dateMinPattern==null ? "placeholder=\"Начиная с\"" : "value=\"" +dateMinPattern +"\"" %> readonly/>

        <!--        <input type='text' id='datepicker1' name='dateMaxPattern' placeholder="Заканчивая"/>  -->
        <input type='text' id='datepicker1' name='dateMaxPattern' <%= dateMaxPattern==null ? "placeholder=\"Заканчивая\"" : "value=\"" +dateMaxPattern +"\"" %>  readonly/>
        </p>
        <input type='submit' value='Искать'/>

        <%
            System.out.println("--- code after btSubmit ---"); // debug
            namePattern = request.getParameter("namePattern");
            jobPattern = request.getParameter("jobPattern");
            salMinPattern = request.getParameter("salMinPattern");
            System.out.println("  - 2 - salMinPattern = "+salMinPattern); // debug
            System.out.println("  - 2 - request.getParameter(\"salMinPattern\")= "+request.getParameter("salMinPattern"));

            if (salMinPattern == null || salMinPattern.isEmpty()) {
                salMin = null;
            } else {
                salMin = Float.parseFloat(request.getParameter("salMinPattern"));
            }
//            System.out.println(" -2- "); // debug

            salMaxPattern = request.getParameter("salMaxPattern");
            if (salMaxPattern == null || salMaxPattern.isEmpty()) {
                salMax = null;
            } else {
                salMax = Float.parseFloat(request.getParameter("salMaxPattern"));
            }
            depPattern = request.getParameter("depPattern");
            manPattern = request.getParameter("manPattern");
            dateMinPattern = request.getParameter("dateMinPattern");
            if (dateMinPattern == null || dateMinPattern.isEmpty()) {
                dateMin = null;
            } else {
                dateMin = UtilDates.stringToDate(dateMinPattern);
            }
//            System.out.println(" -3- "); // debug

            dateMaxPattern = request.getParameter("dateMaxPattern");
            if (dateMaxPattern == null || dateMaxPattern.isEmpty()) {
                dateMax = null; //
            } else {
                dateMax = UtilDates.stringToDate(dateMaxPattern);
            }

            //find page number
            int pageNumber;
            if (request.getParameter("page") != null) {
                pageNumber = Integer.parseInt(request.getParameter("page"));
            } else {
                pageNumber = 1;
            }

            // создать атрибут afterSearch
            if (namePattern != null || jobPattern != null || salMinPattern != null || salMaxPattern != null ||
                    depPattern != null || manPattern != null || dateMinPattern != null || dateMaxPattern != null) {
                request.getSession().setAttribute("afterSearch", "yes");
            } else {
                request.getSession().setAttribute("afterSearch", "no");
            }

            int employeesCount = 0;
            int employeesPerPage = 5;








//            System.out.println(" - 1 -"); // debug

            EmpListProcessor empListProcessor = new EmpListProcessor();
            List <Employee> employeesForPage = empListProcessor.getEmployeesFiltered(namePattern, jobPattern, salMin, salMax,
                    null, null, dateMin, dateMax, manPattern, depPattern, pageNumber, employeesPerPage);
            request.getSession().setAttribute("EmployeesForPage", employeesForPage);
            employeesCount = empListProcessor.countFilteredEmployees(namePattern, jobPattern, salMin, salMax,
                    null, null, dateMin, dateMax, manPattern, depPattern);

//            System.out.println(" - 2 -"); // debug

            // сохраняем паттерны поиска в сессии
            request.getSession().setAttribute("namePattern", namePattern);
            request.getSession().setAttribute("jobPattern", jobPattern);
            request.getSession().setAttribute("salMinPattern", salMinPattern);
            request.getSession().setAttribute("salMax", salMax);
            request.getSession().setAttribute("depPattern", depPattern);
            request.getSession().setAttribute("manPattern", manPattern);
            request.getSession().setAttribute("dateMin", dateMin);
            request.getSession().setAttribute("dateMax", dateMax);









            // после всего cоздаем paginationController и помещаем его в сессию
            PaginationController paginationController = new PaginationController(employeesCount, employeesPerPage, pageNumber);
            request.getSession().setAttribute("paginationController", paginationController);
            System.out.println(" ---- leave Search.jsp ----"); // debug
        %>


    </form>

    <a href='EmployeesList.jsp?ClearSearchPatterns=yes'><br/>Очистить шаблоны поиска<br/></a>

</div>
</body>
</html>
