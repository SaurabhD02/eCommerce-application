
create table users (
user_id BIGINT AUTO_INCREMENT primary key,
mobile varchar(12) unique,
user_code varchar(20) not null unique,
email varchar(50) not null,
role varchar(20) not null
);