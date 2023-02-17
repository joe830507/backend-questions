insert into member(account, password) values('John', 'john123'),('Mary','Mary123'),('James','James123'),('Chris','Chris123'),('Johnson','Johnson123'),('Henry','Henry123');

insert into product(product_name, count) values('pencil', 20),('pencil box',5),('eraser',15);

insert into member_order(product_id,account_id,purchased_count,purchased_date) values(1,2,1, '2021-01-12'),(2,1,3, '2020-05-09'),(1,5,2, '2019-02-22'),(3,4,5, '2020-10-05'),(1,6,1, '2021-11-23'),(2,1,3, '2018-08-19'),(3,3,4, '2020-01-17');