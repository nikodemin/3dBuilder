CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START 1;
CREATE TABLE IF NOT EXISTS categories(
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT,
    image VARCHAR(200),
    description VARCHAR,
    is_root BOOLEAN,
    name VARCHAR(100) NOT NULL,
    sort_index BIGINT
);
CREATE TABLE IF NOT EXISTS categories_children(
    category_id BIGINT NOT NULL,
    children_id BIGINT NOT NULL
);
CREATE TABLE IF NOT EXISTS brands(
    id BIGINT NOT NULL PRIMARY KEY ,
    version BIGINT,
    name VARCHAR(100) NOT NULL,
    site VARCHAR(200)
);
CREATE TABLE IF NOT EXISTS users(
    id BIGINT NOT NULL PRIMARY KEY ,
    version BIGINT,
    address VARCHAR(200),
    email VARCHAR(100) NOT NULL,
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    password VARCHAR(100),
    role INTEGER,
    telnum VARCHAR(50),
    token VARCHAR(100),
    username VARCHAR(100) NOT NULL
);
CREATE TABLE IF NOT EXISTS orders_tools(
    order_id BIGINT NOT NULL,
    tools_id BIGINT NOT NULL
);
CREATE TABLE IF NOT EXISTS orders(
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT,
    address VARCHAR(200),
    date TIMESTAMP WITHOUT TIME ZONE,
    done BOOLEAN NOT NULL,
    fordays INTEGER,
    pledge INTEGER,
    status INTEGER,
    total INTEGER,
    user_id BIGINT
);
CREATE TABLE IF NOT EXISTS orders_tools(
    order_id BIGINT NOT NULL,
    tools_id BIGINT NOT NULL
);
CREATE TABLE IF NOT EXISTS tools(
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT,
    description VARCHAR,
    image1 VARCHAR,
    image2 VARCHAR,
    image3 VARCHAR,
    name VARCHAR(100) NOT NULL ,
    pledge INTEGER,
    power VARCHAR(50),
    prev_image varchar,
    price INTEGER,
    quantity INTEGER,
    weight INTEGER,
    brand_id BIGINT,
    category_id BIGINT,
    sort_index BIGINT
);
CREATE TABLE IF NOT EXISTS orders(
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT,
    address VARCHAR(200),
    date TIMESTAMP WITHOUT TIME ZONE,
    done BOOLEAN NOT NULL,
    fordays INTEGER,
    pledge INTEGER,
    status INTEGER,
    total INTEGER,
    user_id BIGINT
);
ALTER TABLE brands DROP CONSTRAINT IF EXISTS UK_oce3937d2f4mpfqrycbr0l93m;
ALTER TABLE categories DROP CONSTRAINT IF EXISTS UK_t8o6pivur7nn124jehx7cygw5;
ALTER TABLE categories_children DROP CONSTRAINT IF EXISTS UK_4vnklxd3nji8vubkcogayvbl5;
ALTER TABLE tools DROP CONSTRAINT IF EXISTS UK_gn1w3u49lbxxmtqrjfkcn9g3u;
ALTER TABLE users DROP CONSTRAINT IF EXISTS UK_6dotkott2kjsp8vw4d0m25fb7;
ALTER TABLE users DROP CONSTRAINT IF EXISTS UK_r43af9ap4edm43mmtq01oddj6;
ALTER TABLE categories_children DROP CONSTRAINT IF EXISTS FKs5er396t9r98ethqjy3v4v4c7;
ALTER TABLE categories_children DROP CONSTRAINT IF EXISTS FKcwiwyla89s18q8xxu1dk7mr97;
ALTER TABLE orders DROP CONSTRAINT IF EXISTS FK32ql8ubntj5uh44ph9659tiih;
ALTER TABLE orders_tools DROP CONSTRAINT IF EXISTS FKe56kev2knyrquyryo57ccmeqa;
ALTER TABLE orders_tools DROP CONSTRAINT IF EXISTS FKpwn1ojkmgi54mtkdv75xn97hu;
ALTER TABLE tools DROP CONSTRAINT IF EXISTS FKnfqbh5dyykfumrdacwtq87irm;
ALTER TABLE tools DROP CONSTRAINT IF EXISTS FKge0gi7xq4yyvr9gdafpp72s98;

alter table brands add constraint UK_oce3937d2f4mpfqrycbr0l93m unique (name);
alter table categories add constraint UK_t8o6pivur7nn124jehx7cygw5 unique (name);
alter table categories_children add constraint UK_4vnklxd3nji8vubkcogayvbl5 unique (children_id);
alter table tools add constraint UK_gn1w3u49lbxxmtqrjfkcn9g3u unique (name);
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table users add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);
alter table categories_children add constraint FKs5er396t9r98ethqjy3v4v4c7 foreign key (children_id) references categories;
alter table categories_children add constraint FKcwiwyla89s18q8xxu1dk7mr97 foreign key (category_id) references categories;
alter table orders add constraint FK32ql8ubntj5uh44ph9659tiih foreign key (user_id) references users;
alter table orders_tools add constraint FKe56kev2knyrquyryo57ccmeqa foreign key (tools_id) references tools;
alter table orders_tools add constraint FKpwn1ojkmgi54mtkdv75xn97hu foreign key (order_id) references orders;
alter table tools add constraint FKnfqbh5dyykfumrdacwtq87irm foreign key (brand_id) references brands;
alter table tools add constraint FKge0gi7xq4yyvr9gdafpp72s98 foreign key (category_id) references categories;