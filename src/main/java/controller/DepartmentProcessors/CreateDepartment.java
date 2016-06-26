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
import java.util.List;

/**
 * Created by Oleksandr Dudkin.
 * Class creates record about new department in data base.
 */
public class CreateDepartment extends DepartmentModification {
    private static final Logger LOG = LogManager.getLogger(CreateDepartment.class);

    /**
     * Creates record about newly created department and goes to the page with list of departments.
     *
     * @param request HttpServlet request
     * @param response HttpServlet response
     * @param department created department
     */
    @Override
    protected void forwardForDepartment(HttpServletRequest request, HttpServletResponse response, Department department) {

        OracleDataAccess.getInstance().insertDepartment(department);

        List departmentList = (List) OracleDataAccess.getInstance().getAllDepartments();
        request.getSession().setAttribute(DepartmentModification.DEPARTMENT_LIST, departmentList);

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
