package controller.DepartmentProcessors;

import controller.Processor;
import model.OracleDataAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Oleksandr Dudkin on 29.04.2016.
 */
public class DeleteDepartment implements Processor {
    private static final Logger LOG = LogManager.getLogger(DeleteDepartment.class);

    /**
     * Из реквеста получить айди отдела и по нему удалить отдела из БД. Потом получить обновленный список отделов.
     * Поместить список в сессию. Перейти на jsp страницу со списком отделов.
     *
     * @param request
     * @param response
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        int departmentId = Integer.parseInt(request.getParameter(DepartmentModification.DEPARTMENT_ID));
        OracleDataAccess.getInstance().deleteDepartment(departmentId);

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
