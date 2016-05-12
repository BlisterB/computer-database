FROM ubuntu:trusty

RUN apt-get update \
	&& apt-get install -y software-properties-common

RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections \
	&& add-apt-repository -y ppa:webupd8team/java \
	&& apt-get update \
	&& apt-get install -y oracle-java8-installer \
	&& rm -rf /var/lib/apt/lists/* \
	&& rm -rf /var/cache/oracle-jdk8-installer

RUN apt-get -y update \
	&& apt-get install -y maven

COPY hikari.properties /hikari.properties
