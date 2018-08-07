/**
 * 
 */
package ca.celias.amt.services.dao;

import static ca.celias.amt.services.jpa.DAOUtils.findOrEmpty;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import ca.celias.amt.dto.PatchDTO;
import ca.celias.amt.resources.HasLogger;
import ca.celias.amt.services.ResultNotFoundException;
import ca.celias.amt.services.entity.AmtEntity;

/**
 * 
 * @author Chris Elias
 */
public abstract class BaseDAO<DTO, ENTITY extends AmtEntity<IDTYPE>, IDTYPE> 
implements HasLogger {

    private final Class<DTO> dtoType;
    private final Class<ENTITY> entityType;
    
    protected BaseDAO(Class<DTO> dtoType, Class<ENTITY> entityType) {
        this.dtoType = dtoType;
        this.entityType = entityType;
    }

    /**
     * 
     * @return
     */
    protected abstract String NamedfindDTOQuery();
    
    /**
     * 
     * @return
     */
    protected abstract String NamedfindAllDTOQuery();
    
    /**
     * 
     * @param id
     * @return
     */
    public Optional<DTO> findDTO(EntityManager entityManager, IDTYPE id) {
        return findOrEmpty(() ->
            entityManager.createNamedQuery(NamedfindDTOQuery(), dtoType)
            .setParameter("oid", id)
            .getSingleResult()); 
    }

    /**
     * 
     * @return
     */
    public List<DTO> findAllDTO(EntityManager entityManager) {
        return entityManager.createNamedQuery(NamedfindAllDTOQuery(), dtoType).getResultList();
    }
    
    /**
     * 
     * @param entity
     * @return
     */
    public abstract IDTYPE create(EntityManager entityManager, DTO dto);

    /**
     * 
     * @param entityManager
     * @param id
     * @param patchDTO
     * @throws ResultNotFoundException
     */
    public abstract void update(EntityManager entityManager, IDTYPE id, PatchDTO ... patchDTO);
    
    /**
     * 
     * @param entityManager
     * @param entity
     * @return
     */
    public IDTYPE create(EntityManager entityManager, ENTITY entity) {
        entityManager.persist(entity);
        return entity.getOid();
    }

    /**
     * 
     * @param entityManager
     * @param id
     * @return
     */
    public Optional<ENTITY> find(EntityManager entityManager, IDTYPE id) {
        return findOrEmpty(() -> entityManager.find(entityType, id));
    }

    /**
     * 
     * @param entityManager
     * @param id
     * @throws ResultNotFoundException
     */
    public void remove(EntityManager entityManager, IDTYPE id) {
        logger().trace("ENTER remove(entityManager, {})", id);
        
        var entity = find(entityManager,id).orElseThrow(() -> new ResultNotFoundException(id.toString()));
        entityManager.remove(entity);
        
        logger().trace("EXIT remove(entityManager, {})", id);
    }    
}