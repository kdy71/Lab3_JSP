package controller;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ServletStart - doPost execute..."); // debug
        process(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ServletStart - doGet execute...");  // debug
        process(request,response);
    }


/*    private void process (HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html; charset=UTF-8");

        try {
            //response.sendRedirect("pages/EmployeesMainJsp.jsp");
            response.sendRedirect("pages/EmployeesList.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    /**
     * Метод берет из реквеста параметр action и выполняет требемое действие с объектом (работником или отделом),
     * после переходит на нужную страницу.
     * @param request
     * @param response
     */
    private void process (HttpServletRequest request, HttpServletResponse response){

        System.out.println("process execute...");  // debug
        //из реквеста берем параметр action
        String action = request.getParameter("action");
        System.out.println("action =" + action);  // debug
        Map mapOfActions = Actions.getInstance().getMapOfActions();

        if (action == null) {
            action = Actions.START_PAGE; //если параметр налл, то присваиваем значение для перехода на стартовую страницу
        }

        Processor processor = (Processor) mapOfActions.get(action);
        System.out.println("processor =" + processor);  // debug


        if (processor == null) {
            processor = (Processor) mapOfActions.get(Actions.START_PAGE); //если нет процессора по ключу, то переход на стартовую страницу
        }
        else {
            processor.process(request, response);
        }
    }

}
