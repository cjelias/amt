package ca.celias.amt.services.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;

import ca.celias.amt.dto.EngineTypeDTO;
import ca.celias.amt.dto.PatchDTO;
import ca.celias.amt.services.ResultNotFoundException;
import ca.celias.amt.services.entity.EngineType;

/**
 *
 * @author Chris Elias
 */
@ApplicationScoped
public class EngineTypeDAO extends BaseDAO<EngineTypeDTO, EngineType, String> {

    public EngineTypeDAO() {
        super(EngineTypeDTO.class, EngineType.class);
    }

    @Override
    protected String NamedfindAllDTOQuery() {
        return EngineType.QUERY_FIND_ALL_DTO;
    }
    
    @Override
    protected String NamedfindDTOQuery() {
        return EngineType.QUERY_FIND_DTO;
    }

    @Override
    public String create(EntityManager entityManager, EngineTypeDTO dto) {
        var entity = new EngineType();
        entity.setCode(dto.getCode());
        entity.setDescription(dto.getDescription());
        
        return create(entityManager, entity);
    }

    @Override
    public void update(EntityManager entityManager, String code, PatchDTO ... patchDTO) {
        var entity = find(entityManager, code).orElseThrow(() -> new ResultNotFoundException("EngineType: " + code));
        
        for (var patch : patchDTO) {
            var fieldName = patch.getPath();
            var value = patch.getValue() == null ? null : patch.getValue()[0];  
            
            switch(fieldName) {
            case "description":
                entity.setDescription(value);
            }
        }
    }
}