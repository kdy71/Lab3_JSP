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
 * Created by Oleksandr Dudkin on 27.04.2016.
 */
public class StartPage implements Processor {
    private static final Logger LOG = LogManager.getLogger(StartPage.class);

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/EmployeesList.jsp");
        try {
            dispatcher.forward(request, response);
            //LOG.info("START PAGE Dispatcher");
        } catch (ServletException e) {
            e.printStackTrace();
            LOG.error(e);
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error(e);
        }
    }
}