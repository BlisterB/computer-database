# Copy the project to the dind-test
docker cp ServerComputerDatabase computer-db-java-maven:/ServerComputerDatabase
docker cp /hikari-production.properties computer-db-java-maven:/ServerComputerDatabase/ServerComputerDatabase/src/main/ressources/hikari.properties
docker cp /hikari-test.properties       computer-db-java-maven:/ServerComputerDatabase/ServerComputerDatabase/src/test/ressources/hikari.properties
docker exec computer-db-java-maven mvn clean install

docker cp computer-db-java-maven:ServerComputerDatabase/ServerComputerDatabase/target/computer-database-0.3.0-SNAPSHOT.war /computer-database.war
docker cp /computer-database.war computer-db-tomcat:/usr/local/tomcat/webapps/computer-database.war
