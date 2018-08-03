package ca.celias.amt.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import ca.celias.amt.dto.EngineTypeDTO;
import ca.celias.amt.dto.MaintenanceOptionDTO;
import ca.celias.amt.dto.PatchItem;
import ca.celias.amt.services.dao.EngineTypeDAO;
import ca.celias.amt.services.dao.MaintenanceOptionsDAO;

/**
 * 
 * @author Chris Elias
 */
@RequestScoped
public class EngineTypeService extends BaseService {
    
    @Inject
    private EngineTypeDAO dao;

    @Inject
    private MaintenanceOptionsDAO moDao;
    
    /**
     * 
     * @return
     */
    public EngineTypeDTO[] findAllDTO() {
        return nonTransaction(entityManager -> {
           var results = dao.findAllDTO(entityManager);
           var size = results.size();
           
           return results.toArray(new EngineTypeDTO[size]);
        });
    }
    
    /**
     * 
     * @param dto
     * @return
     */
    public String create(EngineTypeDTO dto) {
        return transaction(entityManager -> dao.create(entityManager, dto));
    }
    
    /**
     * 
     * @param code
     * @return
     */
    public EngineTypeDTO findDTO(String code)
    throws ResultNotFoundException {
        return nonTransaction(entityManager -> dao.findDTO(entityManager, code))
                .orElseThrow(() -> new ResultNotFoundException());
    }
    
    /**
     * 
     * @param code
     * @param patchItems
     */
    public void update(String code, PatchItem[] patchItems) {
        transactionNoResult(entityManager -> dao.update(entityManager, code, patchItems));
    }
    
    /**
     * 
     * @param code
     */
    public void remove(String code) {
        transactionNoResult(entityManager -> dao.remove(entityManager, code));
    }

    /**
     * 
     * @param code
     * @return
     */
    public MaintenanceOptionDTO[] getOptions(String code) {

        return nonTransaction(entityManager -> {  
            var engineType = dao.find(entityManager, code)
                    .orElseThrow(() -> new ResultNotFoundException());
            
            var results = moDao.findByEngineType(entityManager, engineType);
            var size = results.size();
            
            return results.toArray(new MaintenanceOptionDTO[size]);
        });
    }
    
    /**
     * 
     * @param code
     * @param option
     */
    public void addOption(String code, String option) {
        transactionNoResult(entityManager -> {
            var engineType = dao.find(entityManager, code)
                    .orElseThrow(() -> new ResultNotFoundException());
            
            var newOption = moDao.find(entityManager, option)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid option ->" + option ));
            
            engineType.getMaintenanceOptions().add(newOption);
        });
    }
    
    /**
     * 
     * @param code
     * @param option
     */
    public void removeOption(String code, String option) {
        transactionNoResult(entityManager -> {
            var engineType = dao.find(entityManager, code)
                    .orElseThrow(() -> new ResultNotFoundException());
            
            var removeOption = moDao.find(entityManager, option)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid option ->" + option ));
            
            engineType.getMaintenanceOptions().remove(removeOption);
        });
    }
    
}