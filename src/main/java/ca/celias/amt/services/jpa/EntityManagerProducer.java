/**
 * 
 */
package ca.celias.amt.services.jpa;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ca.celias.amt.resources.HasLogger;

/**
 *
 * @author Chris Elias
 */
public class EntityManagerProducer
implements HasLogger {

    @Inject
    private EntityManagerFactory emf;

    @Produces
    @RequestScoped
    public EntityManager create() {
        logger().trace("ENTER create()");
        
        try {
            return emf.createEntityManager();
        } finally {
            logger().trace("EXIT create()");
        }
    }

    /**
     * 
     * @param em
     */
    public void destroy(@Disposes EntityManager entityManager) {
        logger().trace("ENTER destroy(entityManager)");
        
        entityManager.clear();
        entityManager.close();
        logger().debug("Entity manager was closed");
        
        logger().trace("ENTER destroy(entityManager)");
    }
}
