FROM maven:3.3.3-jdk-8
CMD /bin/bash

COPY hikari.properties /hikari.properties

RUN git clone https://github.com/Medhoc/computer-database
RUN cp hikari.properties /computer-database/ServerComputerDatabase/src/main/ressources
RUN cp hikari.properties /computer-database/ServerComputerDatabase/src/test/ressources
WORKDIR computer-database/ServerComputerDatabase
RUN git pull

# For the moment, we skip tests
#RUN mvn -Dmaven.test.skip=true install
RUN mvn test
