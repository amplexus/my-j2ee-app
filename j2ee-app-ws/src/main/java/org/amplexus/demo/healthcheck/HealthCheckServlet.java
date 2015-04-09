package org.amplexus.demo.healthcheck;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.amplexus.demo.bean.OrderBean;
import org.amplexus.demo.model.Order;
import org.apache.log4j.Logger;

public class HealthCheckServlet extends HttpServlet {

    public static final int INITIAL_PAUSE_MILLIS = 4000;
    /**
     *  Serializable!
     */
    private static final long serialVersionUID = 1L;

    static Logger log = Logger.getLogger(HealthCheckServlet.class.getName());

    @EJB
    OrderBean orderBean;

    static boolean firstTime = true;

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        if (firstTime) {
            try {
                // This delay is needed so that JBoss 7.1.1 has time to
                // initialise the web services at startup, otherwise the 
                // load tests get lots of 404 errors initially.
                Thread.sleep(INITIAL_PAUSE_MILLIS);
            } catch (InterruptedException e) {
            }
            firstTime = false;
        }

        out.println("<html><head><title>health checker</title></head><body>");
        try {
            @SuppressWarnings("unused")
            Order o = orderBean.retrieveOrder(1);
            out.println("<h1>OrderService: OK</h1>");
        } catch (Throwable t) {
            out.println("<h1>OrderService: ERROR</h1>");
        }
        out.println("</body></html>");
        out.close();
    }
}
