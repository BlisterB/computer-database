drop schema if exists `computer-database-db2`;
  create schema if not exists `computer-database-db2`;
  use `computer-database-db2`;

  drop table if exists computer;
  drop table if exists company;

  create table company (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    constraint pk_company primary key (id))
  ;

CREATE INDEX ind_company_name_asc on company (name ASC);
CREATE INDEX ind_company_name_desc on company (name DESC);

  create table computer (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    introduced                timestamp NULL,
    discontinued              timestamp NULL,
    company_id                bigint default NULL,
    constraint pk_computer primary key (id))
  ;

CREATE INDEX ind_computer_name_asc on computer (name ASC);
CREATE INDEX ind_computer_name_desc on computer (name DESC);
CREATE INDEX ind_computer_introduced_asc on computer (introduced ASC);
CREATE INDEX ind_computer_introduced_desc on computer (introduced DESC);
CREATE INDEX ind_computer_discontinued_asc on computer (discontinued ASC);
CREATE INDEX ind_computer_discontinued_desc on computer (discontinued DESC);


  alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
  create index ix_computer_company_1 on computer (company_id);
