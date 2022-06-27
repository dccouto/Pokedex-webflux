DROP TABLE IF EXISTS TB_POKEMON;
CREATE TABLE TB_POKEMON (id SERIAL PRIMARY KEY, name VARCHAR(255), type VARCHAR(255));

DROP TABLE IF EXISTS TB_USER;
CREATE TABLE TB_USER (id SERIAL PRIMARY KEY, name VARCHAR(255) not null, username VARCHAR(100) not null, password VARCHAR(150) not null, authorities VARCHAR(150) not null);
