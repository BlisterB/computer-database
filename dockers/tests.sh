NETWORK="network-computer-db"
JENKINS="computer-db-jenkins"
JENKINS_REPO="mkhelifi/computer-db-jenkins:latest"
MYSQL_TEST="computer-db-mysql-test"
MYSQL_TEST_REPO="mkhelifi/computer-db-mysql-test:latest"
JAVA_MAVEN="computer-db-java-maven"
JAVA_MAVEN_REPO="mkhelifi/computer-db-java-maven:latest"
MYSQL_PROD="computer-db-mysql-prod"
MYSQL_PROD_REPO="mkhelifi/computer-db-mysql-prod:latest"
TOMCAT="computer-db-tomcat"
TOMCAT_REPO="mkhelifi/computer-db-tomcat:latest"


docker stop $JENKINS ; docker rm $JENKINS
docker stop $MYSQL_TEST ; docker rm $MYSQL_TEST
docker stop $JAVA_MAVEN ; docker rm $JAVA_MAVEN
docker stop $MYSQL_PROD ; docker rm $MYSQL_PROD
docker stop $TOMCAT ; docker rm $TOMCAT

# Network creation
docker network rm $NETWORK
docker network create --subnet 172.18.0.0/16 --gateway=172.18.1.1 $NETWORK

# Jenkins : occupy the ip:port  172.18.0.1:8080, accessible from localhost:8080
docker pull $JENKINS_REPO
docker run -d --name $JENKINS --net $NETWORK -p 8080:8080 -v /var/run/docker.sock:/var/run/docker.sock -v $HOME/jenkins/computer-db/jenkins_home:/var/jenkins_home $JENKINS_REPO

# Mysql test : 172.18.0.2:8080
docker pull $MYSQL_TEST
docker run -d --name $MYSQL_TEST --net $NETWORK $MYSQL_TEST_REPO

# Java+Maven : 172.18.0.3:8080
docker pull $JAVA_MAVEN
docker run -dit --name $JAVA_MAVEN --net $NETWORK $JAVA_MAVEN_REPO

# Mysql production : 172.18.0.4:8080
#docker pull $MYSQL_PROD
#docker run -d --name $MYSQL_PROD --net $NETWORK $MYSQL_PROD_REPO

# Tomcat : 172.18.0.5:8080
docker pull $TOMCAT
docker run --rm --name $TOMCAT -p 8888:8080 $TOMCAT_REPO
