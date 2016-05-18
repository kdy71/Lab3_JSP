package controller.EmployeeProcessors;

import controller.Processor;
import model.Employee;
import model.OracleDataAccess;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Oleksandr Dudkin.
 */
public class ReadEmployee implements Processor {

    //достаем из реквеста айди, по нему находим сотрудника, помещаем в сессию,
    //переходим на страницу, где выводим информацию о нем
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        int employeeId = Integer.parseInt(request.getParameter(EmployeeModification.EMP_ID));
        Employee employee = OracleDataAccess.getInstance().getEmployeeById(employeeId);

        request.getSession().setAttribute(EmployeeModification.EMPLOYEE, employee);

        RequestDispatcher rd = request.getRequestDispatcher("/pages/ReadEmployee.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
