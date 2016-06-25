package controller.EmployeeProcessors;

import controller.Actions;
import controller.Processor;
import model.Employee;
import model.OracleDataAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Dmitry Khoruzhenko on 18.05.2016.
 * <p>
 * Переправляет на jsp-страницу редактирования данных работника.
 */

public class EditEmployee implements Processor {
    private static final Logger LOG = LogManager.getLogger(EditEmployee.class);

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        int employeeId = Integer.parseInt(request.getParameter(EmployeeModification.EMP_ID));

        System.out.println("EditEmployee.java - controller. employeeId= " + employeeId);

        Employee employee = OracleDataAccess.getInstance().getEmployeeById(employeeId);

        request.getSession().setAttribute(Actions.EDIT_EMPLOYEE, employee);
        RequestDispatcher rd = request.getRequestDispatcher("/Employee-new.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            LOG.error(e);
        } catch (IOException e) {
            LOG.error(e);
        }
    }
}
