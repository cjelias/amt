package ca.celias.amt.services.dao;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;

import ca.celias.amt.dto.PatchItem;
import ca.celias.amt.dto.VehicleDTO;
import ca.celias.amt.mvc.ResultNotFoundException;
import ca.celias.amt.services.entity.EngineType;
import ca.celias.amt.services.entity.MaintenanceOption;
import ca.celias.amt.services.entity.Vehicle;

@ApplicationScoped
public class VehicleDAO extends BaseDAO<VehicleDTO, Vehicle, UUID> {

    public VehicleDAO() {
        super(VehicleDTO.class, Vehicle.class);
    }

    @Override
    protected String NamedfindAllDTOQuery() {
        return MaintenanceOption.QUERY_FIND_ALL_DTO;
    }
    
    @Override
    protected String NamedfindDTOQuery() {
        return MaintenanceOption.QUERY_FIND_DTO;
    }

    @Override
    public UUID create(EntityManager entityManager, VehicleDTO dto) {
        var entity = new Vehicle();
        
        var engineType = entityManager.getReference(EngineType.class, dto.getEngineType());
        entity.setEngineType(engineType);
        
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
                entity.setOdometerReading(Double.parseDouble(value));
            }
        }
    }
}