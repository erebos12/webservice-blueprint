FROM registry.bisnode.net/bisnode/server-jre:8u111bisnode7
LABEL maintainer "Alexander Sahm <alexander.sahm@bisnode.com>"

RUN mkdir /opt/bhc-ws

COPY bin /opt/bhc-ws/bin
COPY lib /opt/bhc-ws/lib
COPY config /opt/bhc-ws/config

EXPOSE 8080

WORKDIR /opt/bhc-ws

CMD "bin/bhc-ws"

