package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Common interface for all processors - contains method process().
 */
public interface Processor {

    public void process(HttpServletRequest request, HttpServletResponse response);

}
