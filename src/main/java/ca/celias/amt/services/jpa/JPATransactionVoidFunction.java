package ca.celias.amt.services.jpa;

import java.util.function.Consumer;

import javax.persistence.EntityManager;

/**
 *
 * @author Chris Elias
 */
@FunctionalInterface
public interface JPATransactionVoidFunction extends Consumer<EntityManager> {
    
    default void beforeTransactionCompletion() {
    }

    default void afterTransactionCompletion() {
    }
    
}
