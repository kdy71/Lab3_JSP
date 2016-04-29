package controller;

import model.Employee;
import model.EmployeeImpl;
import model.OracleDataAccess;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Dmitry Khoruzhenko on 14.04.2016.
 * Контроллер.
 * Перенаправляет на титульную страницу (список сотрудников)
 */
public class ServletStart extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost execute..."); // debug
        process(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet execute...");  // debug
        process(request,response);
    }



    private void process (HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html; charset=UTF-8");

        try {
//            response.sendRedirect("pages/EmployeesMainJsp.jsp");
            response.sendRedirect("pages/EmployeesList.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
