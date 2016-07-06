package controller.EmployeeProcessors;

import controller.Processor;
import model.OracleDataAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Oleksandr Dudkin on 29.04.2016.
 * Class deletes record about chosen employee from data base.
 */
public class DeleteEmployee implements Processor {
    private static final Logger LOG = LogManager.getLogger(DeleteEmployee.class);

    /**
     * Deletes record of chosen employee and goes to the page with list of employees.
     *
     * @param request HttpServlet request
     * @param response HttpServlet response
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        int employeeId = Integer.parseInt(request.getParameter(EmployeeModification.EMP_ID));
        OracleDataAccess.getInstance().deleteEmployee(employeeId);

        RequestDispatcher rd = request.getRequestDispatcher("/EmployeesList.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            LOG.error(e);
            //// TODO: 06.07.2016 не выводит ошибку при удалении главного менеджера
        } catch (IOException e) {
            LOG.error(e);
        }
    }
}
