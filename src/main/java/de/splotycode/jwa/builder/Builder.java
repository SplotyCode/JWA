package de.splotycode.jwa.builder;

/**
 * Interface for Building a Object with a specific Type
 * @param <O> the Object Type you want to Build
 */
public interface Builder<O> {

    O build();

}
