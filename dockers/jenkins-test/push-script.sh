# Copy the project to the dind-test
docker cp ServerComputerDatabase java-maven:/ServerComputerDatabase
docker exec java-maven cp /hikari.properties /ServerComputerDatabase/ServerComputerDatabase/src/main/ressources/hikari.properties
docker exec java-maven cp /hikari.properties /ServerComputerDatabase/ServerComputerDatabase/src/main/ressources/hikari.properties
docker exec java-maven mvn test
