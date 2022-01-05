-- apply changes
create table groups (
  id                            varchar(40) not null,
  name                          varchar(255),
  constraint uq_groups_name unique (name),
  constraint pk_groups primary key (id)
);

create table group_permissions (
  id                            integer auto_increment not null,
  permission                    varchar(255),
  group_id                      varchar(40),
  constraint uq_group_permissions_group_id_permission unique (group_id,permission),
  constraint pk_group_permissions primary key (id)
);

create table player_groups (
  id                            integer auto_increment not null,
  player_id                     varchar(40),
  group_id                      varchar(40),
  constraint uq_player_groups_player_id_group_id unique (player_id,group_id),
  constraint pk_player_groups primary key (id)
);

create table player_permissions (
  id                            integer auto_increment not null,
  permission                    varchar(255),
  player_id                     varchar(40),
  constraint uq_player_permissions_player_id_permission unique (player_id,permission),
  constraint pk_player_permissions primary key (id)
);

create index ix_group_permissions_group_id on group_permissions (group_id);
alter table group_permissions add constraint fk_group_permissions_group_id foreign key (group_id) references groups (id) on delete cascade on update restrict;

create index ix_player_groups_group_id on player_groups (group_id);
alter table player_groups add constraint fk_player_groups_group_id foreign key (group_id) references groups (id) on delete cascade on update restrict;

