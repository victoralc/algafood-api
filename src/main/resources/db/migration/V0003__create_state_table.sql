create table state (
    id bigint not null auto_increment,
    name varchar(80) not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

insert into state(name) select distinct name from city;

alter table city add column state_id bigint not null;

update city c set c.state_id = (select e.id from state e where e.name = c.name);

alter table city add constraint fk_state_city foreign key (state_id) references state (id);
