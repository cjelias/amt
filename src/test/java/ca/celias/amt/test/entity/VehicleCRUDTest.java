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

import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ca.celias.amt.dto.EngineTypeDTO;
import ca.celias.amt.dto.PatchItem;
import ca.celias.amt.dto.PatchDTO;
import ca.celias.amt.dto.VehicleDTO;
import ca.celias.amt.services.BaseService;
import ca.celias.amt.services.InvalidEngineTypeFoundException;
import ca.celias.amt.services.VINExistsException;
import ca.celias.amt.services.dao.EngineTypeDAO;
import ca.celias.amt.services.dao.VehicleDAO;
import ca.celias.amt.services.jpa.EntityManagerFactoryProducer;
import ca.celias.amt.services.jpa.EntityManagerProducer;

/**
 * 
 * @author Chris Elias
 */
@EnableWeld
public class VehicleCRUDTest extends BaseService {

    @WeldSetup
    public WeldInitiator weld = 
        WeldInitiator.from(VehicleDAO.class, EngineTypeDAO.class, EntityManager.class, 
                           EntityManagerFactoryProducer.class,EntityManagerProducer.class)
                     .activate(RequestScoped.class, ApplicationScoped.class).build();    
    
    @Inject private VehicleDAO dao;
    @Inject private EngineTypeDAO engineTypeDao;
    
    private final String vin = "SOME-VIN";
    private final int year = 2018;
    private final String make = "FORD";
    private final String model = "F-F150";
    private final Double odReading = 15_000d;
    private final Double newOdReading = 18_000d;
    
    private final String engineType = "Gas-TEST";
    

    @Test
    @DisplayName("Vehicle - Create/Read/Update/Delete")
    public void crud() {
        transactionNoResult(entityManager -> {
            
            var engineDto = EngineTypeDTO.emptyBuilder()
                        .withCode(engineType).withDescription(engineType)
                        .build();
            
            engineTypeDao.create(entityManager, engineDto);
            
            var dto = VehicleDTO.emptyBuilder()
                    .withVin(vin).withYear(year).withMake(make)
                    .withModel(model).withOdometerReading(odReading)
                    .withEngineType(engineDto.getCode())
                    .build();
            
            var uuid = dao.create(entityManager, dto);
            
            assertNotNull(uuid, () -> "dto");
            
            var readDTO = dao.findDTO(entityManager, uuid).get();
            
            assertNotNull(readDTO, () -> "dto");
            assertEquals(uuid.toString(), readDTO.getOid(), () -> "read dto.oid" );
            
            var patchItem = new PatchItem("replace", "odometerReading", new PatchDTO(Double.toString(newOdReading)));
            
            dao.update(entityManager, uuid, patchItem);
            
            var updatedDTO = dao.findDTO(entityManager, uuid).get();
            assertEquals(newOdReading, updatedDTO.getOdometerReading(), () -> "updated dto.description" );
            
            dao.remove(entityManager, uuid);
            
            entityManager.getTransaction().rollback();
        });
    }
    
    @Test
    @DisplayName("VIN Exists Test")
    public void vinExists() {
        var engineDto = EngineTypeDTO.emptyBuilder()
                .withCode(engineType).withDescription(engineType)
                .build();
        
        var dto = VehicleDTO.emptyBuilder()
                .withVin(vin).withYear(year).withMake(make)
                .withModel(model).withEngineType(engineType)
                .withOdometerReading(odReading)
                .build();

        transactionNoResult(entityManager -> engineTypeDao.create(entityManager, engineDto));
        transactionNoResult(entityManager -> dao.create(entityManager, dto));
        
        assertThrows(VINExistsException.class, ()-> {
            var dtoDup = dto.builder().build();
            
            try {
                transactionNoResult(entityManager -> dao.create(entityManager, dtoDup));
                
            } finally {
                transactionNoResult(entityManager -> 
                    entityManager.createNativeQuery("DELETE FROM VEHICLE v WHERE v.engine_type = :engineType")
                        .setParameter("engineType", engineType).executeUpdate()
                );
                
                transactionNoResult(entityManager -> engineTypeDao.remove(entityManager, engineType));
            }
        });
    }
    
    @Test
    @DisplayName("Invalid Engine Type")
    public void invalidEngineType() {
        
        var dto = VehicleDTO.emptyBuilder()
                .withVin(vin).withYear(year).withMake(make)
                .withModel(model).withEngineType(engineType)
                .withOdometerReading(odReading)
                .build();

        assertThrows(InvalidEngineTypeFoundException.class, ()-> {
            transaction(entityManager -> dao.create(entityManager, dto));
        });
    }
}