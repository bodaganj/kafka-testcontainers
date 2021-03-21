from openjdk:8u141-jdk

RUN apt-get update && \
    apt-get install -y gettext-base && \
    rm -rf /var/lib/apt/lists/* && \
    groupadd -r hortonworks && useradd --no-log-init -r -g hortonworks hortonworks && \
    mkdir -p /opt/ && \
    wget -O /opt/hortonworks-registry-0.5.1.zip https://github.com/hortonworks/registry/releases/download/v0.5.1/hortonworks-registry-0.5.1.zip && \
    unzip /opt/hortonworks-registry-0.5.1.zip -d /opt && \
    chown -R hortonworks:hortonworks /opt/hortonworks-registry-0.5.1 && \
    rm /opt/hortonworks-registry-0.5.1.zip && \
    ln -s /opt/hortonworks-registry-0.5.1 /opt/hortonworks-registry

RUN chown -R hortonworks:hortonworks /opt/hortonworks-registry-0.5.1

EXPOSE 9090

USER hortonworks

WORKDIR /opt/hortonworks-registry

CMD ["./bin/registry-server-start.sh","./conf/registry-inmemory-example.yaml"]