package ca.celias.amt.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import ca.celias.amt.dto.AddAppoinmentDTO;
import ca.celias.amt.dto.PatchDTO;
import ca.celias.amt.dto.VehicleDTO;
import ca.celias.amt.dto.VehicleMaintenanceDTO;
import ca.celias.amt.services.dao.MaintenanceOptionsDAO;
import ca.celias.amt.services.dao.VehicleDAO;
import ca.celias.amt.services.entity.MaintenanceOption;
import ca.celias.amt.services.entity.VehicleMaintenance;

/**
 * 
 * @author Chris Elias
 */
@RequestScoped
public class VehicleService extends BaseService {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm a");
    
    @Inject
    private VehicleDAO dao;

    @Inject
    private MaintenanceOptionsDAO moDao;

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
     * @return
     */
    public VehicleMaintenanceDTO[] getHistory(UUID id) {
        return nonTransaction(entityManager -> {
            var vehicle = dao.find(entityManager, id).get();
            var query = entityManager.createNamedQuery(VehicleMaintenance.FIND_BY_VEHICLE, VehicleMaintenance.class);
            var results = query.setParameter("vehicle", vehicle).getResultList();
            
            var list = new ArrayList<VehicleMaintenanceDTO>(results.size());
            
            results.forEach(result -> {
                var options = result.getMaintenanceOptions().stream().
                    map(MaintenanceOption::getDescription).
                    collect(Collectors.joining(", "));
                
                var dto = VehicleMaintenanceDTO.emptyBuilder()
                        .withOid(result.getOid().toString())
                        .withDate(result.getAppointmentDate().format(FORMATTER))
                        .withOptions(options).build();
                
                list.add(dto);
            });
            
            return list.toArray(new VehicleMaintenanceDTO[results.size()]);
        });
    }
    
    public void createAppointment(UUID id, AddAppoinmentDTO dto) {
        transactionNoResult(entityManager -> {
            var vehicle = dao.find(entityManager, id).get();
            var dateTime = LocalDateTime.parse(dto.getAppointmentDate(), FORMATTER);
            
            var appointment = new VehicleMaintenance();
            appointment.setAppointmentDate(dateTime);
            appointment.setVehicle(vehicle);
            
            if (dto.getAppointmentOption() != null && dto.getAppointmentOption().length > 0) {
                for (var option : dto.getAppointmentOption()) {
                    appointment.getMaintenanceOptions().add(moDao.find(entityManager, option).get());
                }
            }
            
            entityManager.persist(appointment);
        });
    }
    
    /**
     * 
     * @param id
     * @param patchItems
     */
    public void update(UUID id, PatchDTO[] patchDTO) {
        transactionNoResult(entityManager -> dao.update(entityManager, id, patchDTO));
    }
    
    /**
     * 
     * @param id
     */
    public void remove(UUID id) {
        transactionNoResult(entityManager -> dao.remove(entityManager, id));
    }
}