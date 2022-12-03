-- -----------------------------------------------------
-- Schema billetservice
-- -----------------------------------------------------

-- CREATE DATABASE billetservice
--     WITH
--     OWNER = postgres
--     ENCODING = 'UTF8'
--     LC_COLLATE = 'Russian_Russia.1251'
--     LC_CTYPE = 'Russian_Russia.1251'
--     TABLESPACE = pg_default
--     CONNECTION LIMIT = -1
--     IS_TEMPLATE = False;

CREATE SCHEMA IF NOT EXISTS billetservice;

CREATE TABLE IF NOT EXISTS billetservice.list_of_cities (
  id INT NOT NULL UNIQUE,
  city VARCHAR(45) NOT NULL,
  PRIMARY KEY (id));

CREATE UNIQUE INDEX id_UNIQUE on billetservice.list_of_cities(id);
CREATE UNIQUE INDEX city_UNIQUE on billetservice.list_of_cities(city);

-- -----------------------------------------------------
-- Table `billetservice`.`route_list`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS billetservice.route_list (
  id INT NOT NULL UNIQUE ,
  city_output INT NOT NULL,
  city_input INT NOT NULL,
  departure_time DATE NULL,
  value INT NULL,
  count INT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_route_list_list_of_cities
    FOREIGN KEY (city_output)
    REFERENCES billetservice.list_of_cities(id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_route_list_list_of_cities1
    FOREIGN KEY (city_input)
    REFERENCES billetservice.list_of_cities(id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE UNIQUE INDEX fk_route_list_list_of_cities_idx on billetservice.route_list(city_output);
CREATE UNIQUE INDEX fk_route_list_list_of_cities1_idx on billetservice.route_list(city_input);


-- -----------------------------------------------------
-- Table `billetservice`.`payment_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS billetservice.payment_status (
  id INT NOT NULL UNIQUE ,
  status VARCHAR(45) NOT NULL,
  PRIMARY KEY (id));


-- -----------------------------------------------------
-- Table `billetservice`.`bilet_list`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS billetservice.bilet_list (
  id INT NOT NULL UNIQUE ,
  route_list_id INT NOT NULL,
  firstname VARCHAR(45) NOT NULL,
  surname VARCHAR(45) NOT NULL,
  patronomic VARCHAR(45) NOT NULL,
  count INT NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  CONSTRAINT fk_bilet_list_route_list1
    FOREIGN KEY (route_list_id)
    REFERENCES billetservice.route_list(id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE UNIQUE INDEX fk_bilet_list_route_list1_idx on billetservice.bilet_list(route_list_id);

-- -----------------------------------------------------
-- Table `billetservice`.`payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS billetservice.payment (
  id INT NOT NULL UNIQUE ,
  payment_status_id INT NOT NULL,
  bilet_list_id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_payment_payment_status1
    FOREIGN KEY (payment_status_id)
    REFERENCES billetservice.payment_status (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_payment_bilet_list1
    FOREIGN KEY (bilet_list_id)
    REFERENCES billetservice.bilet_list (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE UNIQUE INDEX fk_payment_payment_status1_idx on billetservice.payment(payment_status_id);
CREATE UNIQUE INDEX fk_payment_bilet_list1_idx on billetservice.payment(bilet_list_id);
