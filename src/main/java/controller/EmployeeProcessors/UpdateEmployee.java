package controller.EmployeeProcessors;

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
 * Created by Oleksandr Dudkin.
 * Class makes preparations for the employee updating.
 */
public class UpdateEmployee extends EmployeeModification {
    private static final Logger LOG = LogManager.getLogger(UpdateEmployee.class);

    /**
     * Updates record of chosen employee and goes to the page with list of employees.
     *
     * @param request HttpServlet request
     * @param response HttpServlet response
     * @param employee updated employee
     */
    @Override
    protected void forwardForEmployee(HttpServletRequest request, HttpServletResponse response, Employee employee) {

        System.out.println("We are into  processor UpdateEmployee.java - begin.");  // debug
        System.out.println("employee= " + employee); // debug

        OracleDataAccess.getInstance().updateEmployee(employee);

        RequestDispatcher rd = request.getRequestDispatcher("/EmployeesList.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            LOG.error(e);
        } catch (IOException e) {
            LOG.error(e);
        }
    }
}
