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

RUN configure.sh
