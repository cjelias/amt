package ca.celias.amt.services;

import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import ca.celias.amt.dto.PatchItem;
import ca.celias.amt.dto.VehicleDTO;
import ca.celias.amt.services.dao.VehicleDAO;

/**
 * 
 * @author Chris Elias
 */
@RequestScoped
public class VehicleService extends BaseService {
    
    @Inject
    private VehicleDAO dao;

    /**
     * 
     * @return
     */
    public VehicleDTO[] findAllDTO() {
        return nonTransaction(entityManager -> {
           var results = dao.findAllDTO(entityManager);
           var size = results.size();
           
           return results.toArray(new VehicleDTO[size]);
        });
    }
    
    /**
     * 
     * @param dto
     * @return
     */
    public UUID create(VehicleDTO dto) {
        return transaction(entityManager -> dao.create(entityManager, dto));
    }
    
    /**
     * 
     * @param id
     * @return
     */
    public VehicleDTO findDTO(UUID id)
    throws ResultNotFoundException {
        return nonTransaction(entityManager -> dao.findDTO(entityManager, id))
                .orElseThrow(() -> new ResultNotFoundException("Vehicle Id: " + id));
    }
    
    /**
     * 
     * @param id
     * @param patchItems
     */
    public void update(UUID id, PatchItem[] patchItems) {
        transactionNoResult(entityManager -> dao.update(entityManager, id, patchItems));
    }
    
    /**
     * 
     * @param id
     */
    public void remove(UUID id) {
        transactionNoResult(entityManager -> dao.remove(entityManager, id));
    }
}