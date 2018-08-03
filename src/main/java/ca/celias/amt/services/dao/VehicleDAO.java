package ca.celias.amt.services.dao;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import ca.celias.amt.dto.PatchItem;
import ca.celias.amt.dto.VehicleDTO;
import ca.celias.amt.resources.HasLogger;
import ca.celias.amt.services.InvalidEngineTypeFoundException;
import ca.celias.amt.services.ResultNotFoundException;
import ca.celias.amt.services.VINExistsException;
import ca.celias.amt.services.entity.EngineType;
import ca.celias.amt.services.entity.Vehicle;

/**
 *
 * @author Chris Elias
 */
@ApplicationScoped
public class VehicleDAO extends BaseDAO<VehicleDTO, Vehicle, UUID>
implements HasLogger {

    public VehicleDAO() {
        super(VehicleDTO.class, Vehicle.class);
    }

    @Override
    protected String NamedfindAllDTOQuery() {
        return Vehicle.QUERY_FIND_ALL_DTO;
    }
    
    @Override
    protected String NamedfindDTOQuery() {
        return Vehicle.QUERY_FIND_DTO;
    }

    @Override
    public UUID create(EntityManager entityManager, VehicleDTO dto) {
        
        var engineType = entityManager.find(EngineType.class, dto.getEngineType());
        if (engineType == null)
            throw new InvalidEngineTypeFoundException(dto.getEngineType());

        logger().warn("EngineType: {}", engineType.getCode());

        try {
            entityManager.createNamedQuery(Vehicle.QUERY_CHECK_VIN)
                .setParameter("vin", dto.getVin())
                .getSingleResult();
            
            logger().warn("VIN found: {}", dto.getVin());
            throw new VINExistsException(dto.getVin());
        } catch(final NoResultException ignore) {
            // no result, we are all good
            logger().debug("VIN not found: {}", dto.getVin());
        }
        
        var entity = new Vehicle();
        entity.setEngineType(engineType);
        
        entity.setVin(dto.getVin());
        entity.setMake(dto.getMake());
        entity.setModel(dto.getModel());
        entity.setOdometerReading(dto.getOdometerReading());
        entity.setYear(dto.getYear());
        
        return create(entityManager, entity);
    }

    @Override
    public void update(EntityManager entityManager, UUID id, PatchItem ... patchItems) {
        var entity = find(entityManager, id).orElseThrow(() -> new ResultNotFoundException());
        
        for (var patchItem : patchItems) {
            var fieldName = patchItem.getPath();
            var value = patchItem.getValue() == null ? null : patchItem.getValue().getNameArr()[0];  
            
            switch(fieldName) {
            case "odometerReading":
                try {
                    entity.setOdometerReading(Double.parseDouble(value));
                } catch(final NumberFormatException ex) {
                    throw new IllegalArgumentException("odometerReading -> " + ex.getMessage());
                }
            }
        }
    }
}