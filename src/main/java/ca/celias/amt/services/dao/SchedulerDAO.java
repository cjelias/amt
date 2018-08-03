package ca.celias.amt.services.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;

import ca.celias.amt.dto.MaintenanceOptionDTO;
import ca.celias.amt.dto.PatchItem;
import ca.celias.amt.mvc.ResultNotFoundException;
import ca.celias.amt.services.entity.MaintenanceOption;

/**
 *
 * @author Chris Elias
 */
@ApplicationScoped
public class SchedulerDAO extends BaseDAO<MaintenanceOptionDTO,MaintenanceOption, String> {

    public SchedulerDAO() {
        super(MaintenanceOptionDTO.class, MaintenanceOption.class);
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
    public String create(EntityManager entityManager, MaintenanceOptionDTO dto) {
        var entity = new MaintenanceOption();
        entity.setCode(dto.getCode());
        entity.setDescription(dto.getDescription());
        
        return create(entityManager, entity);
    }

    @Override
    public void update(EntityManager entityManager, String code, PatchItem ... patchItems) {
        var entity = find(entityManager, code).orElseThrow(() -> new ResultNotFoundException());
        
        for (var patchItem : patchItems) {
            var fieldName = patchItem.getPath();
            var value = patchItem.getValue() == null ? null : patchItem.getValue().getNameArr()[0];  
            
            switch(fieldName) {
            case "description":
                entity.setDescription(value);
            }
        }
    }
}