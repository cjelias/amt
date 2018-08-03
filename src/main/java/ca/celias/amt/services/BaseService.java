/**
 * 
 */
package ca.celias.amt.services;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ca.celias.amt.services.jpa.JPAFunction;
import ca.celias.amt.services.jpa.JPATransactionFunction;
import ca.celias.amt.services.jpa.JPATransactionVoidFunction;

/**
 * 
 * @author Chris Elias
 */
public class BaseService {

    @Inject
    private EntityManager entityManager;
    
    /**
     * 
     * @param function
     * @return
     * @throws Throwable 
     */
    protected <T> T transaction(JPATransactionFunction<T> function) {
        try {
            entityManager.getTransaction().begin();
            function.beforeTransactionCompletion();
            var result = function.apply(entityManager);

            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().commit();
                function.afterTransactionCompletion();
            } else if (entityManager.getTransaction().getRollbackOnly()) {
                try { entityManager.getTransaction().rollback(); } catch (Exception ignore){ }
            }

            return result;
        } catch (final Throwable t) {
            try { entityManager.getTransaction().rollback(); } catch (Exception ignore){ }
            throw t;
        }
    }
    
    /**
     * 
     * @param function
     */
    protected void transactionNoResult(JPATransactionVoidFunction function) {
        try {
            entityManager.getTransaction().begin();
        
            function.beforeTransactionCompletion();
            function.accept(entityManager);
            
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().commit();
                function.afterTransactionCompletion();
            } else if (entityManager.getTransaction().getRollbackOnly()) {
                try { entityManager.getTransaction().rollback(); } catch (Exception ignore){ }
            }
        } catch (final Throwable t) {
            try { entityManager.getTransaction().rollback(); } catch (Exception ignore){ }
            throw t;
        }
    }
    
    /**
     * 
     * @param function
     * @return
     */
    protected <T> T nonTransaction(JPAFunction<T> function) {
        return function.apply(entityManager);
    }    
}