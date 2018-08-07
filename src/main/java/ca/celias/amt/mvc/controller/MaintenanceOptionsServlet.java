package ca.celias.amt.mvc.controller;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.celias.amt.mvc.model.MaintenanceOptions;

/**
 * 
 * @author Chris Elias
 */
@WebServlet("/ui/maintenanceoptions")
@RequestScoped
public class MaintenanceOptionsServlet extends HttpServlet {

    private static final long serialVersionUID = 2946405895334821866L;
    
    @Inject
    private MaintenanceOptions model;
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        request.setAttribute(model.navigationName(), "active" );
        request.getRequestDispatcher(model.view()).forward(request, response);
    }
}
