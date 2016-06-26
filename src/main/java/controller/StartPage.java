package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Oleksandr Dudkin.
 * Class contains processor to go to start page.
 */
public class StartPage implements Processor {
    private static final Logger LOG = LogManager.getLogger(StartPage.class);

    /**
     * Goes to the start page with list of employees.
     *
     * @param request Http Servlet Request
     * @param response Http Servlet Response
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/Start.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
            LOG.error(e);
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error(e);
        }
    }
}