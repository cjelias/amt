package ca.celias.amt.services.jpa;

import javax.persistence.NoResultException;

/**
 *
 * @author Chris Elias
 */
@FunctionalInterface
public interface DaoRetriever<T> {
    
    /**
     * 
     * @return
     * @throws NoResultException
     */
    T retrieve() throws NoResultException;
}