create table if not exists member (
  id bigint generated by default as identity,
  account varchar unique not null,
  password varchar not null
);

create table if not exists product (
  id bigint generated by default as identity,
  product_name varchar not null,
  count int not null default 0
);


create table if not exists member_order (
  id bigint generated by default as identity,
  product_id bigint not null,
  account_id bigint not null,
  purchased_count int not null default 0,
  purchased_date date
);