package ca.celias.amt.services.jpa;

import java.util.function.Function;
import javax.persistence.EntityManager;

/**
 * 
 * @author Chris Elias
 */
@FunctionalInterface
public interface JPAFunction<T> extends Function<EntityManager, T> {
}
