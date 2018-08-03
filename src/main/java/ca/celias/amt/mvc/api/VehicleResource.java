package ca.celias.amt.mvc.api;

import java.util.UUID;

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

import com.google.gson.Gson;

import ca.celias.amt.dto.PatchItem;
import ca.celias.amt.dto.ResponseEntity;
import ca.celias.amt.dto.ResultDTO;
import ca.celias.amt.dto.VehicleDTO;
import ca.celias.amt.resources.HasLogger;
import ca.celias.amt.resources.JsonPatch;
import ca.celias.amt.services.ResultNotFoundException;
import ca.celias.amt.services.VehicleService;

/**
 * 
 * @author Chris Elias
 */
@Path("/api/vehicle")
public class VehicleResource
implements HasLogger {
    
    @Inject
    private VehicleService service;
 
    @Inject
    @JsonPatch
    private Gson gson;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResultDTO<VehicleDTO> getOptions() {
        logger().trace("ENTER getOptions()");

        try {
            var resultDTO = new ResultDTO<VehicleDTO>();
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
    public Response create(VehicleDTO dto, @Context UriInfo uriInfo) {
        logger().trace("ENTER create(dto)");

        try {
            var uriBuilder = uriInfo.getAbsolutePathBuilder();
            uriBuilder.path(service.create(dto).toString());
            
            return Response.created(uriBuilder.build()).build();
        } finally {
            logger().trace("EXIT create(dto)");
        }
    }

    @GET
    @Path("{code}")
    public VehicleDTO get(@PathParam("code") String code)
    throws ResultNotFoundException {
        logger().trace("ENTER get({})", code);
        
        try {
            return service.findDTO(UUID.fromString(code));
        } finally {
            logger().trace("EXIT get({})", code);
        }
    }
    
    @PATCH
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON )
    @Produces(MediaType.APPLICATION_JSON)
    public Response patchObject(@PathParam("code") String code, String patch) {
        logger().trace("ENTER patchObject(patch)");
        
        logger().debug("Patch String: {}", patch);

        final PatchItem[] patchItems = gson.fromJson(patch, PatchItem[].class);
        
        try {
            service.update(UUID.fromString(code), patchItems);
            return Response.status(200).entity(new ResponseEntity("OK")).build();
        } finally {
            logger().trace("EXIT patchObject(patch)");
        }
    }
    
    @DELETE
    @Path("{code}")
    public Response delete(@PathParam("code") String code) {
        logger().trace("ENTER delete({})", code);
        
        try {
            service.remove(UUID.fromString(code));
            return Response.status(200).entity(new ResponseEntity("OK")).build();
        } finally {
            logger().trace("EXIT delete({})", code);
        }
    }    

}
