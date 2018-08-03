package ca.celias.amt.test.entity;

import org.junit.jupiter.api.Test;

import ca.celias.amt.resources.HasLogger;
import ca.celias.amt.services.MaintenanceOptionsService;

/**
 * @author Chris Elias
 */
public class JPACreateTest
implements HasLogger {

    @Test
    public void createEntityManagerInstance() {
        new MaintenanceOptionsService();
    }
}
