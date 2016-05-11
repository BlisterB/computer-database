# USAGE :
# Le build :
# docker build -t vivian-server .
# Le lancement :
# docker run --name vivian-server -d vivian-server
# Connexion via un autre terminal :
# mysql -h 172.17.0.2 -P 3306 -uroot -proot
# /!\ Attention : il faut remplir la base a partir d'un autre terminal

FROM mysql:5.6

ENV MYSQL_DATABASE computer-database-db
ENV MYSQL_ROOT_PASSWORD root

COPY 1-SCHEMA.sql /docker-entrypoint-initdb.d/1-SCHEMA.sql
COPY 2-PRIVILEGES.sql /docker-entrypoint-initdb.d/2-PRIVILEGES.sql

# Il faut lancer le script de remplissage à partir d'un autre terminal
# Car il contient des données incorrectes qui stope le run
# COPY 3-ENTRIES.sql /docker-entrypoint-initdb.d/3-ENTRIES.sql
