package controller.EmployeeProcessors;

import controller.Processor;
import model.Employee;
import model.EmployeeImpl;
import model.UtilDates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Oleksandr Dudkin.
 * Abstract class that modifies fields of employee (that is newly created employee or already existed one).
 * Inheritors are CreateEmployee and UpdateEmployee classes.
 */
public abstract class EmployeeModification implements Processor {
    private static final Logger LOG = LogManager.getLogger(EmployeeModification.class);

    // Attributes related to employee modification.
    public static final String EMP_ID = "emp_id";
    public static final String EMP_NAME = "emp_name";
    public static final String DEPARTMENT_ID = "department_id";
    public static final String MANAGER_ID = "manager_id";
    public static final String JOB_NAME = "job_name";
    public static final String SALARY = "salary";
    public static final String DATE_IN = "date_in";

    public static final String EMPLOYEE_LIST = "employeeList";

    /**
     * Gets from request employee parameters, create employee-object, then transfers object
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

        Integer empId = null; //initially set id as null
        String empIdAsString = request.getParameter(EmployeeModification.EMP_ID);
        if (empIdAsString != null) {
            empId = Integer.parseInt(empIdAsString);
        }

        String empName = request.getParameter(EMP_NAME);
        Integer departmentId = Integer.parseInt(request.getParameter(DEPARTMENT_ID));
        Integer managerId;
        if (request.getParameter(MANAGER_ID) == null) {
            managerId = null; //  MANAGER_ID - unnecessary field
        } else {
            managerId = Integer.parseInt(request.getParameter(MANAGER_ID));
        }
        String jobName = request.getParameter(JOB_NAME);
        Float salary = Float.parseFloat(request.getParameter(SALARY));
        Date dateIn = null;
        try {
            dateIn = UtilDates.stringToDate(request.getParameter(DATE_IN));
        } catch (ParseException e) {
            LOG.error(e);
        }

        Employee employee = new EmployeeImpl(empId, empName, jobName, salary, departmentId, managerId, dateIn);

        // call abstract method forward with additional parameter - employee
        forwardForEmployee(request, response, employee);
    }

    /**
     * Method that contains in inheritors actions on further employee modification.
     *
     * @param request HttpServlet request
     * @param response HttpServlet response
     * @param employee modified employee
     */
    protected abstract void forwardForEmployee(HttpServletRequest request, HttpServletResponse response, Employee employee);
}
