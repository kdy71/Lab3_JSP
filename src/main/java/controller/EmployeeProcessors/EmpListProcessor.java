package controller.EmployeeProcessors;

import controller.Processor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import model.Employee;
import model.OracleDataAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by khoruzh on 04.07.2016.
 * Shows list of employees.
 */
public class EmpListProcessor implements Processor {
    private static final Logger LOG = LogManager.getLogger(DeleteEmployee.class);

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher rd = request.getRequestDispatcher("/EmployeesList.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            LOG.error(e);
        } catch (IOException e) {
            LOG.error(e);
        }
    }


    /**
     * Прокладка - чтоб не вызывать OracleDataAccess.getInstance().getEmployeesFiltered(...) из EmployeesList.jsp
     * Возвращает список работников, отфильтрованный по заданным параметрам.
     *
     */
    public List<Employee> getEmployeesFiltered(String pName, String pJobName, Float pSalaryFrom, Float pSalaryTo,
                                               Integer pDepartmentId, Integer pManagerId, Date pDateInFrom, Date pDateInTo,
                                               String pManagerName, String pDepartmentName, int page, int employeesPerPage) {

        List <Employee> employeesForPage = OracleDataAccess.getInstance().getEmployeesFiltered(pName, pJobName, pSalaryFrom, pSalaryTo,
                null, null, pDateInFrom, pDateInTo, pManagerName, pDepartmentName, page, employeesPerPage);
        return employeesForPage;
    }


    /**
     * Прокладка - чтоб не вызывать OracleDataAccess.getInstance().countFilteredEmployees(...) из EmployeesList.jsp
     * Подсчитывает кол-во работников по заданным условиям поиска
     *
     */
    public int countFilteredEmployees (String pName, String pJobName, Float pSalaryFrom, Float pSalaryTo,
                                       Integer pDepartmentId, Integer pManagerId, Date pDateInFrom, Date pDateInTo,
                                       String pManagerName, String pDepartmentName) {
        int num_emp = OracleDataAccess.getInstance().countFilteredEmployees(pName, pJobName, pSalaryFrom, pSalaryTo,
                null, null, pDateInFrom, pDateInTo, pManagerName, pDepartmentName);
        return num_emp;
    }


}
