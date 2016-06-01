package controller.EmployeeProcessors;

import model.Employee;
import model.OracleDataAccess;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Oleksandr Dudkin.
 */
public class CreateEmployee extends EmployeeModification {

    /**
     * Создает работника в БД. Потом берет список работников из БД. Заносит их в сессию.
     * Переходит на jsp-страницу со списком работников.
     * @param request
     * @param response
     * @param employee
     */
    @Override
    protected void forwardForEmployee(HttpServletRequest request, HttpServletResponse response, Employee employee) {

        OracleDataAccess.getInstance().insertEmployee(employee);

        // TODO: позже доделать постраничный вывод
        List employeeList = (List)OracleDataAccess.getInstance().getAllEmployees();
        request.getSession().setAttribute(EmployeeModification.EMPLOYEE_LIST, employeeList);

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
