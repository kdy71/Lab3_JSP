package controller.DepartmentProcessors;

import controller.Actions;
import controller.Processor;
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
 * Created by Dmitry Khoruzhenko on 01.06.2016.
 * Class makes preparations for the department editing.
 */
public class EditDepartment implements Processor {
    private static final Logger LOG = LogManager.getLogger(EditDepartment.class);

    /**
     * Gets from request department id, creates department-object by id, then goes to the editing page.
     *
     * @param request HttpServlet request
     * @param response HttpServlet response
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        int departmentId = Integer.parseInt(request.getParameter(DepartmentModification.DEPARTMENT_ID));
        Department department = OracleDataAccess.getInstance().getDepartmentById(departmentId);

        request.getSession().setAttribute(Actions.EDIT_DEPARTMENT, department);

        RequestDispatcher rd = request.getRequestDispatcher("/Department-new.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            LOG.error(e);
        } catch (IOException e) {
            LOG.error(e);
        }
    }
}
