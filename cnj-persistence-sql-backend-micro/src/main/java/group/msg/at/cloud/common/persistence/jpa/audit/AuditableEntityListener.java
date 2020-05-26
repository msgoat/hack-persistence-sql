package group.msg.at.cloud.common.persistence.jpa.audit;

import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.security.enterprise.SecurityContext;
import java.time.LocalDateTime;

/**
 * {@code JPA lifecycle listener} that tracks creation and modification of
 * {@link AbstractAuditableEntity}s.
 * <p>
 * <strong>Note:</strong> Due to a known bug in Payara's version of EclipseLink, EclipseLink does
 * not support CDI in JPA resources as long as AttributeConverters are present. So we have to
 * use a programmatic lookup of the {@code SecurityContext} if the local field should be {@code null}.
 * </p>
 * @see https://github.com/payara/Payara/issues/3720
 *
 * @author Michael Theis (michael.theis@msg.group)
 * @version 1.0
 * @since release SS2019
 */
public class AuditableEntityListener {

    @Inject
    private SecurityContext securityContext;

    @PrePersist
    public void onPrePersist(AbstractAuditableEntity entity) {
        entity.trackCreation(getAuthenticatedUserId(), LocalDateTime.now());
    }

    @PreUpdate
    public void onPreUpdate(AbstractAuditableEntity entity) {
        entity.trackModification(getAuthenticatedUserId(), LocalDateTime.now());
    }

    private String getAuthenticatedUserId() {
        String result = "anonymous";
        if (getSecurityContext().getCallerPrincipal() != null) {
            result = getSecurityContext().getCallerPrincipal().getName();
        }
        return result;
    }

    /**
     * Wraps access to {@link #securityContext} in order to perform a programmatic CDI lookup in case
     * CDI injection didn't happen.
     */
    private SecurityContext getSecurityContext() {
        if (this.securityContext == null) {
            this.securityContext = CDI.current().select(SecurityContext.class).get();
        }
        return this.securityContext;
    }

}
