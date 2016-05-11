FROM maven:3.3.3-jdk-8
CMD /bin/bash

COPY hikari.properties /hikari.properties

RUN git clone https://github.com/Medhoc/computer-database
RUN mv hikari.properties /computer-database/ServerComputerDatabase/src/main/ressources
WORKDIR computer-database/ServerComputerDatabase

# For the moment, we skip tests
RUN mvn -Dmaven.test.skip=true install
