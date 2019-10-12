CREATE TABLE IF NOT EXISTS categories(
    id BIGINT NOT NULL,
    version BIGINT,
    image VARCHAR(200),
    description VARCHAR(1000),
    is_root BOOLEAN,
    name VARCHAR(100) NOT NULL
);
CREATE TABLE IF NOT EXISTS categories_children(
    category_id BIGINT NOT NULL,
    children_id BIGINT NOT NULL
);
CREATE TABLE IF NOT EXISTS brands(
    id BIGINT NOT NULL,
    version BIGINT,
    name VARCHAR(100) NOT NULL,
    site VARCHAR(200)
);
CREATE TABLE IF NOT EXISTS users(
    id BIGINT NOT NULL,
    version BIGINT,
    address VARCHAR(200),
    email VARCHAR(100) NOT NULL,
    firstname VARCHAR(50),
    lastname VARCHAR(50),
    password VARCHAR(100),
    role INTEGER,
    telnum VARCHAR(50),
    token VARCHAR(100),
    username VARCHAR(100) NOT NULL
);
CREATE TABLE IF NOT EXISTS uncompleted_orders_tools(
    order_id BIGINT NOT NULL,
    tools_id BIGINT NOT NULL
);
CREATE TABLE IF NOT EXISTS orders(
    id BIGINT NOT NULL,
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
    id BIGINT NOT NULL,
    version BIGINT,
    description VARCHAR(1000),
    image1 VARCHAR(100),
    image2 VARCHAR(100),
    image3 VARCHAR(100),
    name VARCHAR(100) NOT NULL ,
    pledge INTEGER,
    power VARCHAR(50),
    prev_image varchar(100),
    price INTEGER,
    quantity INTEGER,
    weight INTEGER,
    brand_id BIGINT,
    category_id BIGINT
);
CREATE TABLE IF NOT EXISTS uncompleted_orders(
    id BIGINT NOT NULL,
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