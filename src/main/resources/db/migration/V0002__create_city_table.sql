create table city (
    id bigint not null auto_increment,
    name varchar(80) not null,
    primary key (id)
) engine=InnoDB default charset=utf8;