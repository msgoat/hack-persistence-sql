FROM open-liberty:full

LABEL \
  org.opencontainers.image.authors="Your Name" \
  org.opencontainers.image.vendor="Open Liberty" \
  org.opencontainers.image.url="local" \
  org.opencontainers.image.source="https://github.com/OpenLiberty/guide-containerize" \
  org.opencontainers.image.version="${project.version}" \
  vendor="Open Liberty" \
  name="inventory" \
  version="${project.version}" \
  summary="${project.description}" \
  description="This image contains the inventory microservice running with the Open Liberty runtime."

COPY --chown=1001:0 server.xml /config/

COPY --chown=1001:0 *.war /config/apps

COPY --chown=1001:0 postgres*.jar /opt/ol/wlp/lib/

# Default setting for the verbose option
ARG VERBOSE=false

# see https://github.com/OpenLiberty/ci.docker/blob/master/SECURITY.md
ENV SEC_TLS_TRUSTDEFAULTCERTS=true \
# switch to JSON logging for Docker containers (suitable for ELK stack)
    WLP_LOGGING_MESSAGE_FORMAT=json \
    WLP_LOGGING_MESSAGE_SOURCE="" \
    WLP_LOGGING_CONSOLE_FORMAT=json \
    WLP_LOGGING_CONSOLE_LOGLEVEL=info \
    WLP_LOGGING_CONSOLE_SOURCE=message,trace,accessLog,ffdc,audit

RUN configure.sh
