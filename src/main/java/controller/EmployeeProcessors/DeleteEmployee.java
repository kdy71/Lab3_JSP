package controller.EmployeeProcessors;

import controller.Processor;
import model.Employee;
import model.OracleDataAccess;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Oleksandr Dudkin on 29.04.2016.
 */
public class DeleteEmployee implements Processor {

    /**
     * Из реквеста получить айди работника и по нему удалить работника из БД. Потом получить обновленный список работников.
     * Поместить список в сессию. Перейти на jsp страницу со списком работников.
     * @param request
     * @param response
     */    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        int employeeId = Integer.parseInt(request.getParameter(EmployeeModification.EMP_ID));
        OracleDataAccess.getInstance().deleteEmployee(employeeId); //todo проверить имя метода

//        List<Employee> employeeList = OracleDataAccess.getInstance().getAllEmployees();
//        request.getSession().setAttribute("employeeList", employeeList);

        RequestDispatcher rd = request.getRequestDispatcher("/EmployeesList.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
