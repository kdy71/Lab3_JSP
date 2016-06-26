package controller.DepartmentProcessors;

import controller.Processor;
import model.Department;
import model.DepartmentImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by Oleksandr Dudkin.
 * Abstract class that modifies fields of department (that is newly created department or already existed one).
 * Inheritors are CreateDepartment and UpdateDepartment classes.
 */
public abstract class DepartmentModification implements Processor {
    private static final Logger LOG = LogManager.getLogger(DepartmentModification.class);

    // Attributes related to department modification.
    public static final String DEPARTMENT_ID = "department_id";
    public static final String DEPARTMENT_NAME = "department_name";
    public static final String DESCRIPTION = "description";

    public static final String DEPARTMENT_LIST = "departmentList";
    public static final String DEPARTMENT = "department";

    /**
     * Gets from request department parameters, create department-object, then transfers object
     * for modification in data base (creating or updating).
     *
     * @param request HttpServlet request
     * @param response HttpServlet response
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error(e);
        }

        Integer departmentId = null; //initially set id as null
        String depIdAsString = request.getParameter(DepartmentModification.DEPARTMENT_ID);
        if (depIdAsString != null) { departmentId = Integer.parseInt(depIdAsString);}

        String departmentName = request.getParameter(DEPARTMENT_NAME);
        String description = request.getParameter(DESCRIPTION);
        System.out.println(departmentId + description);

        Department department = new DepartmentImpl(departmentId, departmentName, description);

        // call abstract method forward with additional parameter - department
        forwardForDepartment(request, response, department);
    }

    /**
     * Method that contains in inheritors actions on further department modification.
     *
     * @param request HttpServlet request
     * @param response HttpServlet response
     * @param department modified department
     */
    protected abstract void forwardForDepartment(HttpServletRequest request, HttpServletResponse response, Department department);
}
