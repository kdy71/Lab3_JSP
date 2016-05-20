package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Oleksandr Dudkin on 27.04.2016.
 */
public class StartPage implements Processor {

/*    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("class StartPage implements Processor   execution...");  // debug

//        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/Start.jsp"); //todo сделать стартовую jsp-страницу
//        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/EmployeesList.jsp");

//        System.out.println("RequestDispatcher dispatcher ="+dispatcher);  // debug
        try {
//            dispatcher.forward(request, response);
            response.sendRedirect("pages/EmployeesList.jsp");
        }
        catch (ServletException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

//        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/EmployeesList.jsp"); //todo сделать стартовую jsp-страницу
        RequestDispatcher dispatcher = request.getRequestDispatcher("/EmployeesList.jsp"); //todo сделать redirect ?
        try {
            dispatcher.forward(request, response);
//            response.sendRedirect("pages/EmployeesList.jsp");
        }
        catch (ServletException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
