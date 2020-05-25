package group.msg.at.cloud.common.persistence.jpa.repository;

import javax.persistence.Query;

/**
 * Represents a set of query parameters that are supposed to be passed to a
 * {@link Query}.
 *
 * @author Michael Theis (michael.theis@hm.edu)
 * @version 1.0
 * @since release SS2019
 */
public interface QueryParameters {
    /**
     * Applies all internally stored parameters to the given {@code Query}.
     *
     * @param query JPA Query
     */
    void applyParameters(Query query);
}