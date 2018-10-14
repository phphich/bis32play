# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table tbAnimal (
  id                        varchar(255) not null,
  name                      varchar(255),
  price                     double,
  amount                    integer,
  constraint pk_tbAnimal primary key (id))
;

create table tbFaculty (
  id                        varchar(255) not null,
  name                      varchar(255),
  shot_name                 varchar(255),
  constraint pk_tbFaculty primary key (id))
;

create table tbMajor (
  id                        varchar(255) not null,
  name                      varchar(255),
  level                     varchar(255),
  year                      integer,
  faculty_id                varchar(255),
  constraint pk_tbMajor primary key (id))
;

create table tbOrders (
  id                        varchar(255) not null,
  date                      datetime,
  user_id                   varchar(255),
  status                    varchar(255),
  constraint pk_tbOrders primary key (id))
;

create table tbOrdersDetail (
  id                        varchar(255) not null,
  orders_id                 varchar(255),
  animal_id                 varchar(255),
  amount                    integer,
  constraint pk_tbOrdersDetail primary key (id))
;

create table tbStudent (
  id                        varchar(255) not null,
  name                      varchar(255),
  surname                   varchar(255),
  faculty                   varchar(255),
  major                     varchar(255),
  level                     integer,
  constraint pk_tbStudent primary key (id))
;

create table tbUser (
  id                        varchar(255) not null,
  name                      varchar(255),
  status                    varchar(255),
  picture                   varchar(255),
  password                  varchar(255),
  constraint pk_tbUser primary key (id))
;

alter table tbMajor add constraint fk_tbMajor_faculty_1 foreign key (faculty_id) references tbFaculty (id) on delete restrict on update restrict;
create index ix_tbMajor_faculty_1 on tbMajor (faculty_id);
alter table tbOrders add constraint fk_tbOrders_user_2 foreign key (user_id) references tbUser (id) on delete restrict on update restrict;
create index ix_tbOrders_user_2 on tbOrders (user_id);
alter table tbOrdersDetail add constraint fk_tbOrdersDetail_orders_3 foreign key (orders_id) references tbOrders (id) on delete restrict on update restrict;
create index ix_tbOrdersDetail_orders_3 on tbOrdersDetail (orders_id);
alter table tbOrdersDetail add constraint fk_tbOrdersDetail_animal_4 foreign key (animal_id) references tbAnimal (id) on delete restrict on update restrict;
create index ix_tbOrdersDetail_animal_4 on tbOrdersDetail (animal_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table tbAnimal;

drop table tbFaculty;

drop table tbMajor;

drop table tbOrders;

drop table tbOrdersDetail;

drop table tbStudent;

drop table tbUser;

SET FOREIGN_KEY_CHECKS=1;

