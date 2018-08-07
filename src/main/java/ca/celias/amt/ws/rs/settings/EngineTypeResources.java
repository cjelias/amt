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

import com.google.gson.Gson;

import ca.celias.amt.dto.EngineTypeDTO;
import ca.celias.amt.dto.MaintenanceOptionDTO;
import ca.celias.amt.dto.PatchItem;
import ca.celias.amt.dto.ResponseEntity;
import ca.celias.amt.dto.ResultDTO;
import ca.celias.amt.resources.HasLogger;
import ca.celias.amt.resources.JsonPatch;
import ca.celias.amt.services.EngineTypeService;
import ca.celias.amt.services.ResultNotFoundException;

/**
 * 
 * @author Chris Elias
 */
@Path("/settings/enginetype")
public class EngineTypeResources
implements HasLogger {

    @Inject
    private EngineTypeService service;

    @Inject
    @JsonPatch
    private Gson gson;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResultDTO<EngineTypeDTO> get() {
        logger().trace("ENTER get()");

        try {
            var resultDTO = new ResultDTO<EngineTypeDTO>();
            resultDTO.setDraw(0);
            
            resultDTO.setData(service.findAllDTO());
            
            return resultDTO;
        } finally {
            logger().trace("EXIT get()");
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(EngineTypeDTO dto, @Context UriInfo uriInfo) {
        logger().trace("ENTER create(dto)");

        try {
            var uriBuilder = uriInfo.getAbsolutePathBuilder();

            logger().debug("Code: {}", dto.getCode());
            logger().debug("Description: {}", dto.getDescription());
            
            uriBuilder.path(service.create(dto));
            
            return Response.created(uriBuilder.build()).build();
        } finally {
            logger().trace("EXIT create(dto)");
        }
    }

    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public EngineTypeDTO get(@PathParam("code") String code)
    throws ResultNotFoundException {
        logger().trace("ENTER get({})", code);
        
        try {
            return service.findDTO(code);
        } finally {
            logger().trace("EXIT get({})", code);
        }
    }

    @GET
    @Path("{code}/maintenanceoptions")
    @Produces(MediaType.APPLICATION_JSON)
    public MaintenanceOptionDTO [] getMaintenanceOptions(@PathParam("code") String code)
    throws ResultNotFoundException {
        logger().trace("getMaintenanceOptions get({})", code);
        
        try {
            return service.getOptions(code);
        } finally {
            logger().trace("EXIT getMaintenanceOptions({})", code);
        }
    }

    @PUT
    @Path("{code}/maintenanceoptions/{option}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMaintenanceOptions(@PathParam("code") String code, @PathParam("option") String option)
    throws ResultNotFoundException {
        logger().trace("ENTER addMaintenanceOptions({})", code, option);
        
        try {
            service.addOption(code, option);
            return Response.status(200).entity(new ResponseEntity("OK")).build();
        } finally {
            logger().trace("EXIT addMaintenanceOptions({})", code, option);
        }
    }

    @DELETE
    @Path("{code}/maintenanceoptions/{option}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeMaintenanceOptions(@PathParam("code") String code, @PathParam("option") String option)
    throws ResultNotFoundException {
        logger().trace("ENTER removeMaintenanceOptions({},{})", code, option);
        
        try {
            service.removeOption(code, option);
            return Response.status(200).entity(new ResponseEntity("OK")).build();
        } finally {
            logger().trace("EXIT removeMaintenanceOptions({},{})", code, option);
        }
    }

    
    @PATCH
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response patchObject(@PathParam("code") String code, String patch) {
        logger().trace("ENTER patchObject(patch)");
        
        logger().debug("Patch String: {}", patch);

        final PatchItem[] patchItems = gson.fromJson(patch, PatchItem[].class);
        
        try {
            service.update(code, patchItems);
            return Response.status(200).entity(new ResponseEntity("OK")).build();
        } finally {
            logger().trace("EXIT patchObject(patch)");
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