package group.msg.at.cloud.cloudtrain.core.entity;

/**
 * Enumeration representing the various categories of a {@link Task}.
 *
 * @author Michael Theis (mtheis@hm.edu)
 * @version 1.0
 * @since release SS2019
 */
public enum TaskCategory {

    UNDEFINED,
    BUGFIX,
    REFACTORING,
    NEW_FEATURE,
    PERFORMANCE_IMPROVEMENT,
    RELEASE_MANAGEMENT,
    QUALITY_ASSURANCE,
    BUILD_FAILURE,
    COMMUNICATION
}
