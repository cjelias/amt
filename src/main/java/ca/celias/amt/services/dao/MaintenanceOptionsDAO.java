package ca.celias.amt.services.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;

import ca.celias.amt.dto.MaintenanceOptionDTO;
import ca.celias.amt.dto.PatchDTO;
import ca.celias.amt.services.ResultNotFoundException;
import ca.celias.amt.services.entity.EngineType;
import ca.celias.amt.services.entity.MaintenanceOption;

/**
 *
 * @author Chris Elias
 */
@ApplicationScoped
public class MaintenanceOptionsDAO extends BaseDAO<MaintenanceOptionDTO,MaintenanceOption, String> {

    public MaintenanceOptionsDAO() {
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
    public void update(EntityManager entityManager, String code, PatchDTO ... patchDTO) {
        var entity = find(entityManager, code).orElseThrow(() -> new ResultNotFoundException("MaintenanceOption: " + code));
        
        for (var patch : patchDTO) {
            var fieldName = patch.getPath();
            var value = patch.getValue() == null ? null : patch.getValue()[0];  
            
            switch(fieldName) {
            case "description":
                entity.setDescription(value);
            }
        }
    }
    
    /**
     * 
     * @param entityManager
     * @param engineType
     * @return
     */
    public List<MaintenanceOptionDTO> findByEngineType(EntityManager entityManager, EngineType engineType) {
        return entityManager.createNamedQuery(MaintenanceOption.QUERY_FIND_ENGINETYPE_DTO, MaintenanceOptionDTO.class)
                .setParameter("engineType", engineType)
                .getResultList();
    }
}