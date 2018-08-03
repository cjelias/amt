package ca.celias.amt.mvc;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.celias.amt.dto.ErrorMessage;

/**
 * 
 * @author Chris Elias
 */
@Provider
public class IllegalArgumentExceptionMapper 
implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        return Response.status(Status.BAD_REQUEST)
                .entity(new ErrorMessage(Status.BAD_REQUEST.getStatusCode(), exception.getMessage())).build();
    }

}
