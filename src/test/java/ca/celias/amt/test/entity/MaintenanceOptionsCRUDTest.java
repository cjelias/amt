/**
 * 
 */
package ca.celias.amt.test.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ca.celias.amt.dto.MaintenanceOptionDTO;
import ca.celias.amt.dto.PatchItem;
import ca.celias.amt.dto.PatchValue;
import ca.celias.amt.services.BaseService;
import ca.celias.amt.services.dao.MaintenanceOptionsDAO;
import ca.celias.amt.services.jpa.EntityManagerFactoryProducer;
import ca.celias.amt.services.jpa.EntityManagerProducer;

/**
 * 
 * @author Chris Elias
 */
@EnableWeld
public class MaintenanceOptionsCRUDTest extends BaseService {

    @WeldSetup
    public WeldInitiator weld = 
        WeldInitiator.from(MaintenanceOptionsDAO.class, EntityManager.class, 
                           EntityManagerFactoryProducer.class,EntityManagerProducer.class)
                     .activate(RequestScoped.class, ApplicationScoped.class).build();    
    
    @Inject private MaintenanceOptionsDAO dao;
    
    private final String testCode = "MO-Testing";
    private final String description = "MO-Testing";
    private final String updateDescription = "update to data";

    @Test
    @DisplayName("MaintenanceOptions - Create/Read/Update/Delete")
    public void crud() {
        transactionNoResult(entityManager -> {
            var dto = MaintenanceOptionDTO.emptyBuilder()
                        .withCode(testCode).withDescription(description)
                        .build();
            
            var code = dao.create(entityManager, dto);
            
            assertEquals(testCode, code, () -> "created dto.code" );
            
            var readDTO = dao.findDTO(entityManager, testCode).get();
            
            assertNotNull(readDTO, () -> "dto");
            assertEquals(testCode, readDTO.getCode(), () -> "read dto.code" );
            
            var patchItem = new PatchItem("add", "description", new PatchValue(updateDescription));
            
            dao.update(entityManager, code, patchItem);
            
            var updatedDTO = dao.findDTO(entityManager, code).get();
            assertEquals(updateDescription, updatedDTO.getDescription(), () -> "updated dto.description" );
            
            dao.remove(entityManager, testCode);
            
            entityManager.getTransaction().rollback();
        });
    }
    
    @Test
    @DisplayName("Duplicate Insert Test")
    public void duplicateTest() {
        var dto = MaintenanceOptionDTO.emptyBuilder()
                .withCode(testCode).withDescription(description)
                .build();

        transactionNoResult(entityManager -> dao.create(entityManager, dto));
        
        assertThrows(PersistenceException.class, ()-> {
            var dtoDup = dto.builder().build();
            
            try {
                transactionNoResult(entityManager -> dao.create(entityManager, dtoDup));
            } finally {
                // remove the first entity that was created
                transactionNoResult(entityManager -> dao.remove(entityManager, testCode));
            }
        });
    }
}