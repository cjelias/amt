package ca.celias.amt.services.jpa;

/**
 *
 * @author Chris Elias
 */
@FunctionalInterface
public interface JPATransactionFunction<T> extends JPAFunction<T> {

    default void beforeTransactionCompletion() {
    }

    default void afterTransactionCompletion() {
    }
}
