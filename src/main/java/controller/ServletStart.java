package controller;


import model.OracleDataAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Dmitry Khoruzhenko, Oleksandr Dudkin on 14.04.2016.
 * Class of main servlet. Works with all processors.
 */
public class ServletStart extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(ServletStart.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ServletStart - doPost execute..."); // debug
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ServletStart - doGet execute...");  // debug
        process(request, response);
    }

    /**
     * Gets parameter "action" from request and execute corresponding processor.
     *
     * @param request Http Servlet Request
     * @param response Http Servlet Response
     */
    private void process(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("ServletStart");

        int pageNumber;
        if (request.getParameter("page") != null) {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        } else {
            pageNumber = 1;
        }
        System.out.println("pageNumber " + pageNumber);

        int departmentsCount = OracleDataAccess.getInstance().getTotalCountOfDepartments();
        int departmentsPerPage = 5;
        System.out.println("departmentsCount" + departmentsCount);

        PaginationController paginationController = new PaginationController(departmentsCount, departmentsPerPage, pageNumber);

        request.getSession().setAttribute("paginationControllerForDep", paginationController);
        System.out.println("Положили все в сессию");
        System.out.println("");



/*        // TODO: 27.06.2016 заменил вызов метода getAllEmployeesByPage
        //ArrayList<Employee> listOfEmployees = (ArrayList<Employee>) OracleDataAccess.getInstance().getAllEmployeesByPage(page, employeesPerPage);
        ArrayList<Employee> listOfEmployees = (ArrayList<Employee>) OracleDataAccess.getInstance().getEmployeesFiltered(null, null, null, null,
                null, null, null, null, null, null, page, employeesPerPage);


        //request.getSession().setAttribute("listOfEmployees", listOfEmployees); // TODO: поменял название атрибута
        request.getSession().setAttribute("foundEmployees", listOfEmployees);*/




        String action = request.getParameter("action");
        Map mapOfActions = Actions.getInstance().getMapOfActions();

        if (action == null) {
            action = Actions.START_PAGE; //if "action" is null, then go to the start page
        }

        Processor processor = (Processor) mapOfActions.get(action);

        if (processor == null) {
            LOG.info("Start page");
            processor = (Processor) mapOfActions.get(Actions.START_PAGE); //if processor is null, then go to the start page
            processor.process(request, response);
        } else {
            LOG.info("Action: " + action);
            processor.process(request, response);
        }
    }

}
