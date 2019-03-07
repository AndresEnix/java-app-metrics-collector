drop table IF EXISTS dummy_table;

create table dummy_table
(
   seq_number integer not null,
   identifier integer not null,
   change_type varchar(255) not null,
   status varchar(255) not null,
   process_date date not null,
   primary key(seq_number)
);