package ca.celias.amt.mvc;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.celias.amt.dto.ErrorMessage;
import ca.celias.amt.services.VINExistsException;

/**
 * 
 * @author Chris Elias
 */
@Provider
public class VINExistsExceptionMapper 
implements ExceptionMapper<VINExistsException> {

    @Override
    public Response toResponse(VINExistsException exception) {
        return Response.status(Status.BAD_REQUEST)
                .entity(new ErrorMessage(Status.BAD_REQUEST.getStatusCode(), exception.getMessage())).build();
    }

}
