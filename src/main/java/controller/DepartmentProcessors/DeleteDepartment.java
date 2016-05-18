package controller.DepartmentProcessors;

import controller.Processor;
import model.Department;
import model.OracleDataAccess;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Oleksandr Dudkin on 29.04.2016.
 */
public class DeleteDepartment implements Processor {

    /**
     * Из реквеста получить айди отдела и по нему удалить отдела из БД. Потом получить обновленный список отделов.
     * Поместить список в сессию. Перейти на jsp страницу со списком отделов.
     * @param request
     * @param response
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        int departmentId = Integer.parseInt(request.getParameter(DepartmentModification.DEPARTMENT_ID));
        OracleDataAccess.getInstance().deleteDepartment(departmentId); //todo проверить имя метода

        //todo добавить постраничный вывод
        List<Department> departmentList = OracleDataAccess.getInstance().getAllDepartments();
        request.getSession().setAttribute(DepartmentModification.DEPARTMENT_LIST, departmentList);

        RequestDispatcher rd = request.getRequestDispatcher("/pages/DepartmentList.jsp"); //todo проверить имя страницы
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
