package ca.celias.amt.mvc;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.celias.amt.dto.ErrorMessage;
import ca.celias.amt.services.ResultNotFoundException;

/**
 * 
 * @author Chris Elias
 */
@Provider
public class ResultNotFoundMapper 
implements ExceptionMapper<ResultNotFoundException> {

    @Override
    public Response toResponse(ResultNotFoundException exception) {
        return Response.status(Status.NOT_FOUND)
                .entity(new ErrorMessage(Status.NOT_FOUND.getStatusCode(), "Not Found: " + exception.getMessage())).build();
    }

}
