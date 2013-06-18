
create table article(
    article_id int auto_increment(1,1) primary key,
    article_name varchar(200),
    article_full_name varchar(500),
    /*.....*/
    description varchar(4000),
    comment varchar(2000)
);


create table counteragent(
    counteragent_id int auto_increment(1,1) primary key,
    fio varchar(500),
    email varchar(200),
    phone varchar(20)
);

create table credit(
    credit_id int auto_increment(1,1) primary key,
    credit_date date,
    comment varchar(2000)
);

create table credit_position(
    credit_id int auto_increment(1,1) primary key,
    article_id int not null,
    quantity int not null,
    price int not null, --копейки
    current_quantity int not null -- сколько сейчас 
);


create table delivery(
    delivery_id int auto_increment(1,1) primary key,    
    counteragent_id int not null,
    delivery_date date,
    foreign key (counteragent_id) references counteragent(counteragent_id)
);



create table delivery_position(
    delivery_id int not null,
    article_id int not null, 
    credit_id int not null, 
    quantity int not null,
    price int not null --копейки
)





-- create table tt(
--     id long not null default id_seq.nextval,
--     name varchar
-- ) 
-- 

-- insert into tt(name) values('C');

select * from tt;
