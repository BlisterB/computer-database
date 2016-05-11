FROM maven:3.3.3-jdk-8
CMD /bin/bash

RUN git clone https://github.com/Medhoc/computer-database
WORKDIR computer-database/ServerComputerDatabase

# For the moment, we skip tests
RUN mvn -Dmaven.test.skip=true install
