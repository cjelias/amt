package ca.celias.amt.services.jpa;

import java.util.Optional;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Chris Elias
 */
public class DAOUtils {

    private static final Logger LOG = LoggerFactory.getLogger(DAOUtils.class.getName());
    
    /**
     * 
     * @param retriever
     * @return
     */
    public static <T> Optional<T> findOrEmpty(final DaoRetriever<T> retriever) {
        try {
            return Optional.ofNullable(retriever.retrieve());
        } catch (NoResultException ex) {
            LOG.debug("NoResult: {}", ex.getMessage());
        } 
        
        return Optional.empty();
    }
}
