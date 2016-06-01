package controller.EmployeeProcessors;

import controller.Actions;
import model.Employee;
import model.OracleDataAccess;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Oleksandr Dudkin.
 */
public class UpdateEmployee extends EmployeeModification {

    /**
     * Обновляет данные работника в БД. Потом берет список работников из БД. Заносит их в сессию.
     * Переходит на jsp-страницу со списком работников.
     * @param request
     * @param response
     * @param employee
     */
    @Override
    protected void forwardForEmployee(HttpServletRequest request, HttpServletResponse response, Employee employee) {

        System.out.println("We are into  processor UpdateEmployee.java - begin.");  // debug
        System.out.println("employee= "+employee); // debug
        OracleDataAccess.getInstance().updateEmployee(employee);
//        Integer empId = (Integer) request.getSession().getAttribute(EmployeeModification.EMP_ID);
//        OracleDataAccess oda = OracleDataAccess.getInstance();
//        Employee employeeById = oda.getEmployeeById(empId);
//        System.out.println("employeeById= "+employeeById);
//        OracleDataAccess.getInstance().updateEmployee(employeeById);

//        List<Employee> employeeList = (List) OracleDataAccess.getInstance().getAllEmployees();
//        request.getSession().setAttribute(EmployeeModification.EMPLOYEE_LIST, employeeList);

//        String urlString = formUrl(employee);
//        RequestDispatcher rd = request.getRequestDispatcher(urlString);
        RequestDispatcher rd = request.getRequestDispatcher("/EmployeesList.jsp"); //todo проверить точное название страницы
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Формирует url
     * @param employee
     * @return
     */
    private String formUrl(Employee employee) {
        // cхема пути: /Имя сервлета?параметр1=значение1&параметр2=значение2
        String urlString = "/ServletStart?action=" + Actions.READ_EMPLOYEE + "&" +
                EmployeeModification.EMP_ID + "=" + employee.getId();
        return urlString;
    }
}
