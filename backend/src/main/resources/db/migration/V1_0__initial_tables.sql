create table short_url(
    id bigint not null auto_increment,
    long_url varchar(512) not null,
    click_count bigint default 0,
    primary key (id)
);