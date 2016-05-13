NETWORK="network-computer-db"
JENKINS="computer-db-jenkins"
JENKINS_REPO="mkhelifi/computer-db-jenkins:latest"
MYSQL_TEST="computer-db-mysql-test"
MYSQL_TEST_REPO="mkhelifi/computer-db-mysql-test:latest"
JAVA_MAVEN="computer-db-java-maven"
JAVA_MAVEN_REPO="mkhelifi/computer-db-java-maven:latest"

# Network creation
docker network rm $NETWORK
docker network create --subnet 172.18.0.0/16 --gateway=172.18.1.1 $NETWORK

# Jenkins : occupy the ip:port  172.18.0.1:8080, accessible from localhost:8080
docker stop $JENKINS ; docker rm $JENKINS
docker pull $JENKINS_REPO
docker run -d --name $JENKINS --net $NETWORK -p 8080:8080 -v /var/run/docker.sock:/var/run/docker.sock -v $HOME/jenkins/computer-db/jenkins_home:/var/jenkins_home $JENKINS_REPO

# Mysql : 172.18.0.2:8080
docker stop $MYSQL_TEST ; docker rm $MYSQL_TEST
docker run -d --name $MYSQL_TEST --net $NETWORK $MYSQL_TEST_REPO

# Java+Maven : 172.18.0.3:8080
docker stop $JAVA_MAVEN ; docker rm $JAVA_MAVEN
docker run -dit --name $JAVA_MAVEN --net $NETWORK $JAVA_MAVEN_REPO

# Mysql : 172.18.0.4:8080

# Mysql : 172.18.0.5:8080
