create table order (
    id bigint not null auto_increment,
    subtotal decimal(10,2) not null,
    delivery_tax decimal(10,2) not null,
    total decimal(10,2) not null,

    restaurant_id bigint not null,
    customer_user_id bigint not null,
    payment_type_id bigint not null,

    address_city_id bigint(20) not null,
    address_postal_code varchar(9) not null,
    address_street varchar(100) not null,
    address_number varchar(20) not null,
    address_additional varchar(60) null,
    address_neighbour varchar(60) not null,

    status varchar(10) not null,
    creation_date datetime not null,
    confirmation_date datetime null,
    cancel_date datetime null,
    delivery_date datetime null,

    primary key (id),

    constraint fk_restaurant_order foreign key (restaurant_id) references restaurant (id),
    constraint fk_user_order foreign key (user_customer_id) references user (id),
    constraint fk_payment_type_order foreign key (payment_type_id) references payment_type (id)
) engine=InnoDB default charset=utf8;

create table order_item (
     id bigint not null auto_increment,
     quantity smallint(6) not null,
     unit_price decimal(10,2) not null,
     total_price decimal(10,2) not null,
     notes varchar(255) null,
     order_id bigint not null,
     product_id bigint not null,
    
     primary key (id),
     unique key uk_item_pedido_produto (order_id, product_id),
    
     constraint fk_order_item foreign key (order_id) references order (id),
     constraint fk_product_order_item foreign key (product_id) references product (id)
) engine=InnoDB default charset=utf8;