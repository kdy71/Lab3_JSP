package controller.DepartmentProcessors;

import controller.Actions;
import controller.Processor;
import model.Department;
import model.OracleDataAccess;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Dmitry Khoruzhenko on 01.06.2016.
 * Берёт из реквеста DEPARTMENT_ID и по нему вытягивает из базы department.
 * Этот department запоминает в параметрах сессии и переходит на страничку редактирования отделов.
 */
public class EditDepartment implements Processor {
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        int departmentId = Integer.parseInt( request.getParameter(DepartmentModification.DEPARTMENT_ID));
        System.out.println("--- EditDepartment.java - controller. departmentId= "+departmentId);  // debug
        Department department = OracleDataAccess.getInstance().getDepartmentById(departmentId);
        request.getSession().setAttribute(Actions.EDIT_DEPARTMENT, department);
        System.out.println("  department= "+department);  // debug

        RequestDispatcher rd = request.getRequestDispatcher("/Department-new.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
