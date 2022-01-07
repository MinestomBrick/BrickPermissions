-- apply changes
alter table group_permissions add column data varchar(255);

alter table player_permissions add column data varchar(255);

create index ix_player_groups_player_id on player_groups (player_id);
create index ix_player_permissions_player_id on player_permissions (player_id);
