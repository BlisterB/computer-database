FROM jenkins:latest

USER root

RUN apt-get update &&\
   apt-get install curl

RUN curl -sSL https://get.docker.com/ | sh

# Copy plugin's config
COPY hikari-production.properties /hikari-production.properties
COPY hikari-test.properties /hikari-test.properties
COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt
