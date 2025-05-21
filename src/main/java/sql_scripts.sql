CREATE TABLE customer
(
    id          SERIAL PRIMARY KEY,
    name        varchar(255) not null,
    email       varchar(255) not null,
    password    varchar(255) not null,
    created_date date default current_date,
    updated_date date default current_date,
	customer_type VARCHAR(255) not null,
	birth_date date not null
);


CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username varchar(100) unique not null,
    password varchar(200)        not null,
    role     varchar(100)        not null,
	email	 varchar(200) unique not null,
    active   boolean default true
);

CREATE TABLE category
(
    id          SERIAL PRIMARY KEY,
    name        varchar(255) NOT NULL,
    created_date date default current_date,
    updated_date date default current_date,
	rental_rate_per_hour NUMERIC(15,2),
	rental_rate_per_day NUMERIC(15,2),
	rental_rate_per_week NUMERIC(15,2),
	rental_rate_per_month NUMERIC(15,2)
);

CREATE TABLE vehicle
(
    id          SERIAL PRIMARY KEY,
    name        varchar(255) NOT NULL,
    price       NUMERIC(10, 2),
    stock       INT,
    category_id INT REFERENCES category (id),
	created_by INT REFERENCES users(id),
	updated_by INT REFERENCES users(id),
	created_date date default current_date,
	updated_date date default current_date
);

CREATE TABLE rental (
    id SERIAL PRIMARY KEY,
    customer_id INT REFERENCES customer(id),
    vehicle_id INT REFERENCES vehicle(id),
    rental_type VARCHAR(20),
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    total_price NUMERIC(15,2),
    deposit NUMERIC(15,2)
);

select * from users;
select * from customer;
select * from category;
select * from vehicle;
select * from rental;

