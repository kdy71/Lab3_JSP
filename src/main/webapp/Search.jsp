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
            System.out.println("-- в Search.jsp--");

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


            //find page number
            int pageNumber;

            if (request.getParameter("page") != null) {
                pageNumber = Integer.parseInt(request.getParameter("page"));
            } else {
                pageNumber = 1;
            }

            System.out.println("namePattern= "+namePattern);
            System.out.println("jobPattern="+jobPattern);
            System.out.println("salMin= "+salMin);
            System.out.println("salMax= "+salMax);
            System.out.println("depPattern= "+depPattern);
            System.out.println("manPattern= "+manPattern);
            System.out.println("dateMin= "+dateMin);
            System.out.println("dateMax= "+dateMax);

            // создать атрибут afterSearch
            // ПО ПЕРЕХОДУ ЧЕРЕЗ ССЫЛКУ ОБНУЛЯЕТСЯ ФОРМА ПОИСКА, А ЗНАЧИТ ОБНУЛЯЮТСЯ ПАТТЕРНЫ, А ЗНАЧИТ AFTER SEARCH СТАНОВИТСЯ NO

            /**
             * 1. До первого заполения формы "afterSearch", "no"
             * 2. После первого заполнения "afterSearch", "yes"
             * 3. После этого пока не обнулим всегда будет "yes"
             */

            if (namePattern != null || jobPattern != null || salMinPattern != null || salMaxPattern != null ||
                    depPattern != null || manPattern != null || dateMinPattern != null || dateMaxPattern != null) {
                request.getSession().setAttribute("afterSearch", "yes");
            } else {
                request.getSession().setAttribute("afterSearch", "no");
            }

            System.out.println("-- в Search.jsp--");
            //Если СЕЙЧАС ПОИСКА НЕТ и при этом ранее не было отобраного по поиску списка, то
            if (request.getSession().getAttribute("afterSearch").equals("no")
                    && (request.getSession().getAttribute("foundEmployees") == null
                    || request.getSession().getAttribute("foundEmployees").toString().isEmpty())
                    ) {

                System.out.println("ВАРИАНТ 1 ПОИСКА НЕ БЫЛО НИ РАЗУ");
                // находим количество работников в списке и на странице. Помещаем PaginationController с количеством
                // работников, кол-вом их на странице, и номером страницы в реквест
                int employeesCount = OracleDataAccess.getInstance().getTotalCountOfEmployees();
                int employeesPerPage = 5;
                PaginationController paginationController = new PaginationController(employeesCount, employeesPerPage, pageNumber);
                request.getSession().setAttribute("paginationController", paginationController);

                //выбираем работников для нужной страницы. Помещаем в реквест
                employeesList = OracleDataAccess.getInstance().getEmployeesFiltered(namePattern, jobPattern, salMin, salMax,
                        null, null, dateMin, dateMax, manPattern, depPattern, pageNumber, employeesPerPage);
                request.getSession().setAttribute("allEmployees", employeesList);

            //ЕСЛИ НОВОГО ПОИСКА НЕТ А foundEmployees ЕСТЬ УЖЕ
            } else if (request.getSession().getAttribute("afterSearch").equals("no")
                    && (!request.getSession().getAttribute("foundEmployees").toString().isEmpty())) {
                System.out.println("ВАРИАНТ 2 СЕЙЧАС ПОИСКА НЕ БЫЛО, НО БЫЛ ДО ЭТОГО");
                //достаем из реквеста список
                employeesList = (List<Employee>) request.getSession().getAttribute("foundEmployees");
                System.out.println("Отобранные по поиску ранее все работники");
                System.out.println(employeesList);

                // находим количество работников в списке и на странице. Помещаем PaginationController с количеством
                // работников, кол-вом их на странице, и номером страницы в реквест
                int employeesCount = employeesList.size();
                System.out.println("размер отобранного списка = " + employeesList.size());
                int employeesPerPage = 5;
                PaginationController paginationController = new PaginationController(employeesCount, employeesPerPage, pageNumber);
                request.getSession().setAttribute("paginationController", paginationController);

                //выбираем работников для нужной страницы. Помещаем в реквест
                List <Employee> employeesForPage = new ArrayList<>();
                int endOfList= (pageNumber * employeesPerPage);// индекс последнего работника на странице

                System.out.println("номер страницы = " + pageNumber);
                System.out.println("индекс последнего работника по расчетам = " + endOfList);
                System.out.println("индекс последнего работника по списку = " + employeesCount);
                if (endOfList > employeesCount) {
                    endOfList = employeesCount;
                }
                System.out.println("Индекс последнего работника для станицы = " + endOfList);

                for (int i = ((pageNumber - 1) * employeesPerPage); i < endOfList; i++) {
                    System.out.println(employeesList.get(i));
                    employeesForPage.add(employeesList.get(i));
                }
                System.out.println("Отобранные для страницы работники");
                System.out.println(employeesForPage);

                request.getSession().setAttribute("foundEmployeesForPage", employeesForPage);
                System.out.println("ВАРИАНТ 2 ВСЕ ПОДГОТОВЛЕНО");
            }

            //ЕСЛИ ЕСТЬ НОВЫЙ ПОИСК
            else if (request.getSession().getAttribute("afterSearch").equals("yes")) {
                System.out.println("ВАРИАНТ 3 ЕСТЬ НОВЫЙ ПОИСК");
                //короткий метод
                employeesList = OracleDataAccess.getInstance().getEmployeesFiltered(namePattern, jobPattern, salMin, salMax,
                        null, null, dateMin, dateMax, manPattern, depPattern);
                request.getSession().setAttribute("foundEmployees", employeesList);

                int employeesCount = employeesList.size();
                int employeesPerPage = 5;
                //длинный метод
                List <Employee> employeesForPage = OracleDataAccess.getInstance().getEmployeesFiltered(namePattern, jobPattern, salMin, salMax,
                        null, null, dateMin, dateMax, manPattern, depPattern, pageNumber, employeesPerPage);

                PaginationController paginationController = new PaginationController(employeesCount, employeesPerPage, pageNumber);

                request.getSession().setAttribute("paginationController", paginationController);
                request.getSession().setAttribute("foundEmployeesForPage", employeesForPage);
            }

        %>


    </form>

</div>
</body>
</html>
