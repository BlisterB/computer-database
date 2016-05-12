# Network creation
docker network rm jenkins-dind
docker network create --subnet 172.18.0.0/16 --gateway=172.18.1.1 jenkins-dind

# DinD : occupy the ip:port  172.18.0.1:2375
docker stop dind-test ; docker rm dind-test
docker run --privileged --name dind-test --net jenkins-dind -d docker:1.11.1-dind
docker exec -it dind-test docker network create --subnet 172.19.0.0/16 --gateway=172.19.1.1 mysql-java

# Jenkins : occupy the ip:port  172.18.0.2:8080, accessible from localhost:8080
docker stop jenkins-test ; docker rm jenkins-test
docker pull mkhelifi/jenkins-test
docker run -d --name jenkins-test --net jenkins-dind -p 8080:8080 -p 50000:50000 mkhelifi/jenkins-test:latest

# Mysql
#docker exec -it dind-test docker stop mysql-test-db ; docker exec -it dind-test docker rm mysql-test-db
docker exec dind-test docker run -d --name mysql-test-db --net mysql-java mkhelifi/mysql-test-db:latest

# Java+Maven : will need to be restart to launch commands
docker exec dind-test docker run -dti --name java-maven --net mysql-java mkhelifi/java-maven:latest
# docker run --rm --link dind-test:docker --name java-maven     --net computer-db-test mkhelifi/java-maven
