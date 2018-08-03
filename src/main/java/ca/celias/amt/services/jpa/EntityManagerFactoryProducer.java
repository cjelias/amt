/**
 * 
 */
package ca.celias.amt.services.jpa;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ca.celias.amt.resources.HasLogger;

/**
 *
 * @author Chris Elias
 */
public class EntityManagerFactoryProducer
implements HasLogger {

    @Produces
    @ApplicationScoped
    public EntityManagerFactory create() {
        return Persistence.createEntityManagerFactory("AMTPU");
    }

    /**
     * 
     * @param factory
     */
    public void destroy(@Disposes EntityManagerFactory entityManagerFactory) {
        logger().trace("ENTER destroy(entityManagerFactory)");
        
        entityManagerFactory.close();
        
        try {
            DriverManager.getConnection("jdbc:derby:target/AMTDS;shutdown=true");
        } catch (SQLException ignore) {
        }
        logger().info("EntityManagerFactory shutdown");
        
        logger().trace("EXIT destroy(entityManagerFactory)");
    }
}
