package ca.celias.amt.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import ca.celias.amt.dto.MaintenanceOptionDTO;
import ca.celias.amt.dto.PatchDTO;
import ca.celias.amt.services.dao.MaintenanceOptionsDAO;

/**
 * 
 * @author Chris Elias
 */
@RequestScoped
public class MaintenanceOptionsService extends BaseService {
    
    @Inject
    private MaintenanceOptionsDAO dao;

    /**
     * 
     * @return
     */
    public MaintenanceOptionDTO[] findAllDTO() {
        return nonTransaction(entityManager -> {
           var results = dao.findAllDTO(entityManager);
           var size = results.size();
           
           return results.toArray(new MaintenanceOptionDTO[size]);
        });
    }
    
    /**
     * 
     * @param dto
     * @return
     */
    public String create(MaintenanceOptionDTO dto) {
        return transaction(entityManager -> dao.create(entityManager, dto));
    }
    
    /**
     * 
     * @param code
     * @return
     */
    public MaintenanceOptionDTO findDTO(String code)
    throws ResultNotFoundException {
        return nonTransaction(entityManager -> dao.findDTO(entityManager, code))
                .orElseThrow(() -> new ResultNotFoundException("MaintenanceOption:" + code));
    }
    
    /**
     * 
     * @param code
     * @param patchItems
     */
    public void update(String code, PatchDTO[] patchDTO) {
        transactionNoResult(entityManager -> dao.update(entityManager, code, patchDTO));
    }
    
    /**
     * 
     * @param code
     */
    public void remove(String code) {
        transactionNoResult(entityManager -> dao.remove(entityManager, code));
    }
}