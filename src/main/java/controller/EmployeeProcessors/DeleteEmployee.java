package controller.EmployeeProcessors;

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
public class DeleteEmployee implements Processor {
    private static final Logger LOG = LogManager.getLogger(DeleteEmployee.class);

    /**
     * Из реквеста получить айди работника и по нему удалить работника из БД. Потом получить обновленный список работников.
     * Поместить список в сессию. Перейти на jsp страницу со списком работников.
     *
     * @param request
     * @param response
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        int employeeId = Integer.parseInt(request.getParameter(EmployeeModification.EMP_ID));
        OracleDataAccess.getInstance().deleteEmployee(employeeId);

        RequestDispatcher rd = request.getRequestDispatcher("/EmployeesList.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            LOG.error(e);
        } catch (IOException e) {
            LOG.error(e);
        }
    }
}
