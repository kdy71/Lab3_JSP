package controller.DepartmentProcessors;

import model.Department;
import model.OracleDataAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Oleksandr Dudkin on 27.04.2016.
 * Class makes preparations for the department updating.
 */
public class UpdateDepartment extends DepartmentModification {
    private static final Logger LOG = LogManager.getLogger(UpdateDepartment.class);

    /**
     * Updates record of chosen department and goes to the page with list of departments.
     *
     * @param request HttpServlet request
     * @param response HttpServlet response
     * @param department updated department
     */
    @Override
    protected void forwardForDepartment(HttpServletRequest request, HttpServletResponse response, Department department) {

        OracleDataAccess.getInstance().updateDepartment(department);

        RequestDispatcher rd = request.getRequestDispatcher("/DepartmentsList.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            LOG.error(e);
        } catch (IOException e) {
            LOG.error(e);
        }
    }
}
