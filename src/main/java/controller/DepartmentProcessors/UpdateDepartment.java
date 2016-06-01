package controller.DepartmentProcessors;

import controller.Actions;
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
public class UpdateDepartment extends DepartmentModification{

    /**
     * Обновляет изменные данные отдела в БД. Потом берет список отделов из БД. Заносит их в сессию.
     * Переходит на jsp-страницу со списком отделов.
     * @param request
     * @param response
     * @param department
     */
    @Override
    protected void forwardForDepartment(HttpServletRequest request, HttpServletResponse response, Department department) {

        System.out.println("--- We are into  processor UpdateDepartment.java - begin. ---");  // debug
        System.out.println("department= "+department); // debug
        OracleDataAccess.getInstance().updateDepartment(department);

//        List<Department> departmentList = OracleDataAccess.getInstance().getAllDepartments();
//        request.getSession().setAttribute(DepartmentModification.DEPARTMENT_LIST, departmentList);

//        String urlString = formUrl(department);
//        RequestDispatcher rd = request.getRequestDispatcher(urlString);
        RequestDispatcher rd = request.getRequestDispatcher("/DepartmentsList.jsp");
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
     * @param department
     * @return
     */
    private String formUrl(Department department) {
        // cхема пути: /<Имя сервлета>?параметр1=значение1&параметр2=значение2
        String urlString = "/ServletStart?action=" + Actions.READ_DEPARTMENT + "&" +
                DepartmentModification.DEPARTMENT_ID + "=" + department.getId();
        return urlString;
    }
}
