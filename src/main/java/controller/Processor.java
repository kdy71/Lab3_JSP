package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Oleksandr Dudkin.
 * Common interface for all processors - contains method process().
 */
public interface Processor {

    void process(HttpServletRequest request, HttpServletResponse response);

}
