package controller.DepartmentProcessors;

import controller.Processor;
import model.Department;
import model.OracleDataAccess;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Oleksandr Dudkin on 29.04.2016.
 */
public class ReadDepartment implements Processor {

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        int departmentId = Integer.parseInt(request.getParameter(DepartmentModification.DEPARTMENT_ID));
        Department department = OracleDataAccess.getInstance().getDepartmentById(departmentId);

        request.getSession().setAttribute(DepartmentModification.DEPARTMENT, department);

        RequestDispatcher rd = request.getRequestDispatcher("/pages/ReadDepartment.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
