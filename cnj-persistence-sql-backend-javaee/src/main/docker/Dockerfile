FROM docker.at.automotive.msg.team/cloudtrain/cnj-docker-payara-full:latest

LABEL maintainer="michael.theis@hm.edu" \
        ${project.groupId}.${project.artifactId}.project="HMFWPCNJ" \
        ${project.groupId}.${project.artifactId}.version="${project.version}" \
        ${project.groupId}.${project.artifactId}.description="${project.description}"

ENV \
  POSTGRES_JDBC_DRIVER_VERSION=42.2.5 \
  POSTGRES_JDBC_POOL_NAME=cnj-postgres-pool \
  POSTGRES_JDBC_DATASOURCE_NAME=cnj-postgres-datasource \
  PAYARA_AS_ADMIN_CMD="${PAYARA_PATH}/bin/asadmin --user ${ADMIN_USER} --passwordfile=${PAYARA_PASSWORD_FILE} --echo"

# Init JDBC Resources / Connection Pools
RUN \
  echo "Downloading PostgreSQL JDBC driver version ${POSTGRES_JDBC_DRIVER_VERSION}" \
  && curl \
       --silent \
       --location \
       --max-time 300 \
       --output ${PAYARA_PATH}/glassfish/domains/${PAYARA_DOMAIN}/lib/postgresql-${POSTGRES_JDBC_DRIVER_VERSION}.jar \
       http://central.maven.org/maven2/org/postgresql/postgresql/${POSTGRES_JDBC_DRIVER_VERSION}/postgresql-${POSTGRES_JDBC_DRIVER_VERSION}.jar \
  && echo "Starting Payara domain ${PAYARA_DOMAIN} to configure application resources" \
  && ${PAYARA_AS_ADMIN_CMD} start-domain ${PAYARA_DOMAIN} \
  && echo "Creating connection pool ${POSTGRES_JDBC_POOL_NAME}" \
  && ${PAYARA_AS_ADMIN_CMD} create-jdbc-connection-pool \
    	--datasourceclassname org.postgresql.ds.PGSimpleDataSource \
        --restype javax.sql.DataSource \
        --property user=\${ENV=POSTGRES_DB_USER}:password=\${ENV=POSTGRES_DB_PASSWORD}:DatabaseName=\${ENV=POSTGRES_DB_NAME}:ServerName=\${ENV=POSTGRES_DB_HOST}:port=\${ENV=POSTGRES_DB_PORT} \
        ${POSTGRES_JDBC_POOL_NAME} \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.connection-creation-retry-attempts=2 \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.connection-creation-retry-interval-in-seconds=10 \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.connection-leak-reclaim=true \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.connection-leak-timeout-in-seconds=120 \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.validation-classname=org.glassfish.api.jdbc.validation.PostgresConnectionValidation \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.connection-validation-method=custom-validation \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.is-connection-validation-required=true \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.is-isolation-level-guaranteed=true \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.max-pool-size=32 \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.max-wait-time-in-millis=30000 \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.pool-resize-quantity=4 \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.pooling=true \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.slow-query-threshold-in-seconds=15 \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.statement-cache-size=50 \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.statement-leak-reclaim=true \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.statement-leak-timeout-in-seconds=120 \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.statement-timeout-in-seconds=30 \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.steady-pool-size=0 \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.transaction-isolation-level=read-committed \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.validate-atmost-once-period-in-seconds=90 \
  && ${PAYARA_AS_ADMIN_CMD} set resources.jdbc-connection-pool.${POSTGRES_JDBC_POOL_NAME}.wrap-jdbc-objects=true \
  && echo "Creating JDBC datasource ${POSTGRES_JDBC_DATASOURCE_NAME}" \
  && ${PAYARA_AS_ADMIN_CMD} create-jdbc-resource \
    	--connectionpoolid ${POSTGRES_JDBC_POOL_NAME} \
    	jdbc/${POSTGRES_JDBC_DATASOURCE_NAME} \
  && echo "Stopping Payara domain ${PAYARA_DOMAIN} after configuring application resources" \
  && ${PAYARA_AS_ADMIN_CMD} stop-domain --kill true ${PAYARA_DOMAIN}

# copy build artifact into payara deployment folder
COPY *.war ${DEPLOY_DIR}/
