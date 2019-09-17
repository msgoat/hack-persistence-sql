FROM docker.at.automotive.msg.team/msgoat/at41-docker-jre11:1.0.0

LABEL maintainer="michael.theis@msg.group" \
        ${project.groupId}.${project.artifactId}.project="AT41" \
        ${project.groupId}.${project.artifactId}.version="${project.version}" \
        ${project.groupId}.${project.artifactId}.description="${project.description}"

ENV JAVA_OPTS="" \
    DOCKER_JAVA_OPTS="" \
    SPRING_JAVA_OPTS="" \
    SPRING_PROFILES_ACTIVE="default" \
    CNAP_CLOUD="local" \
# Keycloak OpenID Connect Provider configuration (empty values must be provided!)
    KEYCLOAK_REALM="" \
    KEYCLOAK_AUTH_SERVER_URL="" \
    KEYCLOAK_SSL_REQUIRED="" \
    KEYCLOAK_RESOURCE="" \
    KEYCLOAK_CREDENTIALS_SECRET="" \
    KEYCLOAK_USE_RESOURCE_ROLE_MAPPINGS="true" \
    KEYCLOAK_PRINCIPAL_ATTRIBUTE="preferred_username" \
    KEYCLOAK_BEARER_ONLY="true" \
    KEYCLOAK_VERIFY_TOKEN_AUDIENCE="false"

RUN echo "adding run user spring to system" \
    && addgroup -S spring -g 1000 \
    && adduser -S spring -u 1000 -G spring

COPY *.jar /home/spring/
COPY docker-entrypoint.sh /home/spring/

RUN chown -R spring:spring /home/spring \
    && chmod u+x /home/spring/docker-entrypoint.sh

USER spring

EXPOSE 8080

ENTRYPOINT ["/home/spring/docker-entrypoint.sh"]
CMD ["java"]



