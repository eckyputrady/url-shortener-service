create table short_url(
    id bigint not null auto_increment,
    long_url varchar(512) not null,
    primary key (id)
);

create table short_url_analytics(
    short_url_id bigint not null,
    analytics_at_date date not null,
    time_zone tinyint not null,
    click_count int not null,
    foreign key (short_url_id) references short_url(id)
);