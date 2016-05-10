FROM mysql:latest

ENV MYSQL_DATABASE computer-database-db
ENV MYSQL_ROOT_PASSWORD root

COPY 1-SCHEMA.sql /docker-entrypoint-initdb.d/1-SCHEMA.sql
COPY 2-PRIVILEGES.sql /docker-entrypoint-initdb.d/2-PRIVILEGES.sql
COPY insertCompany.sql /docker-entrypoint-initdb.d/3-ENTRIES.sql

CMD mysql computer-database-db < docker-entrypoint-initdb.d/1-SCHEMA.sql
CMD mysql computer-database-db < docker-entrypoint-initdb.d/2-PRIVILEGES.sql
CMD mysql computer-database-db < docker-entrypoint-initdb.d/3-ENTRIES.sql
