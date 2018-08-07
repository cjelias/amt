package ca.celias.amt.mvc.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.celias.amt.mvc.model.EngineType;

/**
 * 
 * @author Chris Elias
 */
@WebServlet("/ui/enginetype")
public class EngineTypeServlet extends HttpServlet {

    private static final long serialVersionUID = -4906767305702603076L;
    
    @Inject
    private EngineType model;
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        request.setAttribute(model.navigationName(), "active" );
        request.getRequestDispatcher(model.view()).forward(request, response);
    }
}