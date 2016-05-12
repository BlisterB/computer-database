FROM jenkins:1.596

ENV DOCKER_HOST=tcp://dind-test:2375

USER root
RUN apt-get update \
      && apt-get install -y sudo \
      && rm -rf /var/lib/apt/lists/*
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers

# Install docker
RUN curl -fsSL https://get.docker.com/ | sh

# Copy plugin's config
USER jenkins
COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt
