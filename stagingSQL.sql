CREATE TABLE actors (
  name VARCHAR(500),
  role TEXT,
  payroll INT,
  moviename VARCHAR(500),
  releaseyear INT,
  ismale BOOLEAN,
  isserie BOOLEAN
);

CREATE TABLE biographies (
  name VARCHAR(200),
  birthplace VARCHAR(200)
);

CREATE TABLE birthplaces (
  birthplace VARCHAR(200),
  coords VARCHAR(400),
  PRIMARY KEY (birthplace)
);

CREATE TABLE business (
  moviename VARCHAR(500),
  movieyear INTEGER,
  isserie BOOLEAN,
  currency VARCHAR(20),
  amount NUMERIC,
  type VARCHAR(100)
);

CREATE TABLE directors (
  moviename VARCHAR(500),
  releaseyear INTEGER,
  name VARCHAR(100),
  isserie BOOLEAN
);

CREATE TABLE genres (
  moviename VARCHAR(500),
  releaseyear INTEGER,
  genre VARCHAR(100),
  isserie BOOLEAN
);

CREATE TABLE "MPAA-ratings" (
  moviename VARCHAR(500),
  releaseyear INTEGER,
  rating VARCHAR(10),
  isserie BOOLEAN
);

CREATE TABLE "producion-companies" (
  moviename VARCHAR(500),
  releaseyear INTEGER,
  isserie BOOLEAN,
  country VARCHAR(20)
);

CREATE TABLE ratings (
  moviename VARCHAR(500),
  releaseyear INTEGER,
  rating NUMERIC,
  isserie BOOLEAN
);

CREATE TABLE "running-times" (
  moviename VARCHAR(500),
  releaseyear INTEGER,
  runtime INTEGER,
  isserie BOOLEAN
);

CREATE TABLE videos (
  name VARCHAR(500),
  releaseyear INTEGER,
  isserie BOOLEAN
);

CREATE TABLE writers (
  moviename VARCHAR(500),
  releaseyear INTEGER,
  name VARCHAR(100),
  lastname VARCHAR(100),
  isserie BOOLEAN
);
