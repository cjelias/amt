package ca.celias.amt.mvc.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.celias.amt.mvc.model.Vehicle;

/**
 *
 * @author Chris Elias
 */
@WebServlet("/ui/vehicles")
public class VehiclesServlet extends HttpServlet {

    private static final long serialVersionUID = 5713585602444766428L;
    
    @Inject
    private Vehicle model;
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        request.setAttribute(model.navigationName(), "active" );
        request.getRequestDispatcher(model.view()).forward(request, response);
    }
}