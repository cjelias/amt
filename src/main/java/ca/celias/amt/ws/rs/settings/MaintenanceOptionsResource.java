/**
 * 
 */
package ca.celias.amt.ws.rs.settings;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import ca.celias.amt.dto.MaintenanceOptionDTO;
import ca.celias.amt.dto.PatchDTO;
import ca.celias.amt.dto.ResponseEntity;
import ca.celias.amt.dto.ResultDTO;
import ca.celias.amt.resources.HasLogger;
import ca.celias.amt.services.MaintenanceOptionsService;
import ca.celias.amt.services.ResultNotFoundException;

/**
 * 
 * @author Chris Elias
 */
@Path("/settings/maintenanceoptions")
public class MaintenanceOptionsResource
implements HasLogger {
    
    @Inject
    private MaintenanceOptionsService service;
 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResultDTO<MaintenanceOptionDTO> getOptions() {
        logger().trace("ENTER getOptions()");

        try {
            var resultDTO = new ResultDTO<MaintenanceOptionDTO>();
            resultDTO.setDraw(0);
            
            resultDTO.setData(service.findAllDTO());
            
            return resultDTO;
        } finally {
            logger().trace("EXIT getOptions()");
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(MaintenanceOptionDTO dto, @Context UriInfo uriInfo) {
        logger().trace("ENTER create(formParams)");

        try {
            var uriBuilder = uriInfo.getAbsolutePathBuilder();

            logger().debug("Code: {}", dto.getCode());
            logger().debug("Description: {}", dto.getDescription());
            
            uriBuilder.path(service.create(dto));
            
            return Response.created(uriBuilder.build()).build();
        } finally {
            logger().trace("EXIT create(formParams)");
        }
    }

    @GET
    @Path("{code}")
    public MaintenanceOptionDTO get(@PathParam("code") String code)
    throws ResultNotFoundException {
        logger().trace("ENTER get({})", code);
        
        try {
            return service.findDTO(code);
        } finally {
            logger().trace("EXIT get({})", code);
        }
    }
    
    @PATCH
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON )
    @Produces(MediaType.APPLICATION_JSON)
    public Response patchObject(@PathParam("code") String code, PatchDTO[] patch) {
        logger().trace("ENTER patchObject({}, patch)", code);
        
        try {
            service.update(code, patch);
            return Response.status(200).entity(new ResponseEntity("OK")).build();
        } finally {
            logger().trace("EXIT patchObject({}, patch)", code);
        }
    }
    
    @DELETE
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("code") String code) {
        logger().trace("ENTER delete(code)");
        
        try {
            service.remove(code);
            return Response.status(200).entity(new ResponseEntity("OK")).build();
        } finally {
            logger().trace("EXIT delete(code)");
        }
    }    
}