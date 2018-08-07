package ca.celias.amt.ws.rs;

import java.util.UUID;

import javax.enterprise.context.RequestScoped;
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

import ca.celias.amt.dto.AddAppoinmentDTO;
import ca.celias.amt.dto.PatchDTO;
import ca.celias.amt.dto.ResponseEntity;
import ca.celias.amt.dto.ResultDTO;
import ca.celias.amt.dto.VehicleDTO;
import ca.celias.amt.dto.VehicleMaintenanceDTO;
import ca.celias.amt.resources.HasLogger;
import ca.celias.amt.services.ResultNotFoundException;
import ca.celias.amt.services.VehicleService;

/**
 * 
 * @author Chris Elias
 */
@Path("/vehicle")
@RequestScoped
public class VehicleResource
implements HasLogger {
    
    @Inject
    private VehicleService service;
 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResultDTO<VehicleDTO> getVehicles() {
        logger().trace("ENTER getVehicles()");

        try {
            var resultDTO = new ResultDTO<VehicleDTO>();
            resultDTO.setDraw(0);
            resultDTO.setData(service.findAllDTO());
            
            return resultDTO;
        } finally {
            logger().trace("EXIT getVehicles()");
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

    @GET
    @Path("{code}/appointment")
    @Produces(MediaType.APPLICATION_JSON)
    public ResultDTO<VehicleMaintenanceDTO> getMaintenance(@PathParam("code") String code) {
        logger().trace("ENTER getMaintenance({})", code);

        try {
            var resultDTO = new ResultDTO<VehicleMaintenanceDTO>();
            resultDTO.setDraw(0);
            
            resultDTO.setData(service.getHistory(UUID.fromString(code)));
            
            return resultDTO;
        } finally {
            logger().trace("EXIT getMaintenance({})", code);
        }
    }

    @Path("{code}/appointment")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAppointment(@PathParam("code") String oid, AddAppoinmentDTO dto, @Context UriInfo uriInfo) {
        logger().trace("ENTER createAppointment({},dto)", oid);

        try {
            service.createAppointment(UUID.fromString(oid), dto);
            return Response.status(200).entity(new ResponseEntity("OK")).build();
        } finally {
            logger().trace("EXIT createAppointment({},dto)", oid);
        }
    }
    
    
    
    @PATCH
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON )
    @Produces(MediaType.APPLICATION_JSON)
    public Response patchObject(@PathParam("code") String code, PatchDTO[] patchDTO) {
        logger().trace("ENTER patchObject({}, patchDTO)", code);
        
        try {
            service.update(UUID.fromString(code), patchDTO);
            return Response.status(200).entity(new ResponseEntity("OK")).build();
        } finally {
            logger().trace("EXIT patchObject({}, patchDTO)", code);
        }
    }
    
    @DELETE
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
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