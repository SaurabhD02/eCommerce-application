create table otpverification (
id bigint auto_increment primary key,
mobile_number varchar(15) unique not null,
otp varchar(6)  not null,
expiration_time date not null,
retry_count int default 0
);