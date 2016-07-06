<%@ page import="model.Employee" %>
<%@ page import="model.OracleDataAccess" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="model.UtilDates" %>
<%@ page import="controller.PaginationController" %>
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

    <form name="search" action='EmployeesList.jsp' method='post' accept-charset="utf-8">

        <%
            request.setCharacterEncoding("utf-8");
            List<Employee> employeesList = new ArrayList<Employee>();
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
            namePattern = request.getParameter("namePattern");
            jobPattern = request.getParameter("jobPattern");
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
            manPattern = request.getParameter("manPattern");
            dateMinPattern = request.getParameter("dateMinPattern");
            if (dateMinPattern == null || dateMinPattern.isEmpty()) {
                dateMin = null;
            } else {
                dateMin = UtilDates.stringToDate(dateMinPattern);
            }
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

            //ВАРИАНТ 1 ПОИСКА НЕ БЫЛО НИ РАЗУ
            if (request.getSession().getAttribute("afterSearch").equals("no")
                    && (request.getSession().getAttribute("hasPreviousPatterns") == null
                    || request.getSession().getAttribute("hasPreviousPatterns").toString().isEmpty())) {

                employeesCount = OracleDataAccess.getInstance().getTotalCountOfEmployees();

                //выбираем работников для нужной страницы. Помещаем в реквест
                employeesList = OracleDataAccess.getInstance().getAllEmployeesByPage(pageNumber, employeesPerPage);
                request.getSession().setAttribute("EmployeesForPage", employeesList);
            }

            // ВАРИАНТ 2 ЕСТЬ НОВЫЙ ПОИСК
            else if (request.getSession().getAttribute("afterSearch").equals("yes")) {
                //узнаем количество найденных по паттернам работников
                employeesCount = OracleDataAccess.getInstance().getEmployeesFiltered(namePattern, jobPattern, salMin, salMax,
                        null, null, dateMin, dateMax, manPattern, depPattern).size();

                //делаем список найденных работников для нужной страницы и помещаем в сессию
                List <Employee> employeesForPage = OracleDataAccess.getInstance().getEmployeesFiltered(namePattern, jobPattern, salMin, salMax,
                        null, null, dateMin, dateMax, manPattern, depPattern, pageNumber, employeesPerPage);
                request.getSession().setAttribute("EmployeesForPage", employeesForPage);

                // сохраняем паттерны поиска в сессии
                request.getSession().setAttribute("namePattern", namePattern);
                request.getSession().setAttribute("jobPattern", jobPattern);
                request.getSession().setAttribute("salMin", salMin);
                request.getSession().setAttribute("salMax", salMax);
                request.getSession().setAttribute("depPattern", depPattern);
                request.getSession().setAttribute("manPattern", manPattern);
                request.getSession().setAttribute("dateMin", dateMin);
                request.getSession().setAttribute("dateMax", dateMax);

                //помещаем метку в сессию, что ранее был поиск и в сессии уже есть паттерны
                request.getSession().setAttribute("hasPreviousPatterns", "yes");
            }

            //  СЕЙЧАС ПОИСКА НЕ БЫЛО, НО БЫЛ ДО ЭТОГО
            else if (request.getSession().getAttribute("afterSearch").equals("no")
                    && (request.getSession().getAttribute("hasPreviousPatterns").equals("yes"))) {
                // Достаем из реквеста все паттерны
                namePattern = request.getSession().getAttribute("namePattern").toString();
                jobPattern = request.getSession().getAttribute("jobPattern").toString();
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
                depPattern = request.getSession().getAttribute("depPattern").toString();
                manPattern = request.getSession().getAttribute("manPattern").toString();
                dateMinPattern = request.getParameter("dateMinPattern");
                if (dateMinPattern == null || dateMinPattern.isEmpty()) {
                    dateMin = null;
                } else {
                    dateMin = UtilDates.stringToDate(dateMinPattern);
                }
                dateMaxPattern = request.getParameter("dateMaxPattern");
                if (dateMaxPattern == null || dateMaxPattern.isEmpty()) {
                    dateMax = null; //
                } else {
                    dateMax = UtilDates.stringToDate(dateMaxPattern);
                }

                // узнаем количество найденных работников
                employeesCount = OracleDataAccess.getInstance().getEmployeesFiltered(namePattern, jobPattern, salMin, salMax,
                        null, null, dateMin, dateMax, manPattern, depPattern).size();

                // Ищем работников по паттернам для нужной страницы. помещаем в сессию
                List <Employee> employeesForPage = OracleDataAccess.getInstance().getEmployeesFiltered(namePattern, jobPattern, salMin, salMax,
                        null, null, dateMin, dateMax, manPattern, depPattern, pageNumber, employeesPerPage);
                request.getSession().setAttribute("EmployeesForPage", employeesForPage);
            }

            // после всего cоздаем paginationController и помещаем его в сессию
            PaginationController paginationController = new PaginationController(employeesCount, employeesPerPage, pageNumber);
            request.getSession().setAttribute("paginationController", paginationController);
        %>


    </form>

</div>
</body>
</html>
