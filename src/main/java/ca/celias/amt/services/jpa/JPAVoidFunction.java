package ca.celias.amt.services.jpa;

import java.util.function.Consumer;

import javax.persistence.EntityManager;

/**
 *
 * @author Chris Elias
 */
@FunctionalInterface
public interface JPAVoidFunction extends Consumer<EntityManager> {
}
