package controller.DepartmentProcessors;

import model.Department;
import model.OracleDataAccess;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Oleksandr Dudkin on 27.04.2016.
 */
public class CreateDepartment extends DepartmentModification {

    /**
     * Создает запись о новом отделе в БД. Потом берет список отделов из БД. Заносит их в сессию.
     * Переходит на jsp-страницу со списком отделов.
     * @param request
     * @param response
     * @param department
     */
    @Override
    protected void forwardForDepartment(HttpServletRequest request, HttpServletResponse response, Department department) {

        OracleDataAccess.getInstance().insertDepartment(department);

        // TODO: позже доделать постраничный вывод
        List departmentList = (List)OracleDataAccess.getInstance().getAllDepartments();
        request.getSession().setAttribute(DepartmentModification.DEPARTMENT_LIST, departmentList);

        RequestDispatcher rd = request.getRequestDispatcher("/DepartmentsList.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}