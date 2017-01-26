CREATE TABLE VideoMateriaal (
  naam VARCHAR(500),
  jaar INT,
  duur INT,
  beoordeling DECIMAL,
  leeftijdCat VARCHAR(50),
  isSerie BOOLEAN,
  PRIMARY KEY (naam, jaar, isSerie)
);

CREATE TABLE Genre (
  type VARCHAR(100),
  PRIMARY KEY (type)
);

CREATE TABLE VideoGenre(
  genreType VARCHAR(100),
  videoMateriaalNaam VARCHAR(500),
  videoMateriaalJaar INT,
  isSerie BOOLEAN,
  FOREIGN KEY (genreType) REFERENCES Genre(type),
  FOREIGN KEY (videoMateriaalNaam, videoMateriaalJaar, isSerie) REFERENCES VideoMateriaal(naam, jaar, isSerie)
);

CREATE TABLE Regiseur (
  Naam VARCHAR(200),
  PRIMARY KEY (Naam)
);

CREATE TABLE VideoRegiseur (
  RegiseurNaam VARCHAR(200),
  videoMateriaalNaam VARCHAR(500),
  videoMateriaalJaar INT,
  isSerie BOOLEAN,
  FOREIGN KEY (RegiseurNaam) REFERENCES Regiseur(Naam),
  FOREIGN KEY (videoMateriaalNaam, videoMateriaalJaar, isSerie) REFERENCES VideoMateriaal(naam, jaar, isSerie)
);

CREATE TABLE geboorteplaatsen(
	geboorteplaats VARCHAR(200),
	coords VARCHAR(400),
	PRIMARY KEY (geboorteplaats)
);

CREATE TABLE Acteur(
  Naam VARCHAR(500),
  isman BOOLEAN,
  geboorteplaats VARCHAR(200),
  PRIMARY KEY (Naam, isman),
  FOREIGN KEY (geboorteplaats) REFERENCES geboorteplaatsen(geboorteplaats)
);

CREATE TABLE Rol (
  ActeurNaam VARCHAR(500),
  videoMateriaalNaam VARCHAR(500),
  videoMateriaalJaar INT,
  isSerie BOOLEAN,
  rolNaam TEXT,
  priroriteit INT,
  isman BOOLEAN
  FOREIGN KEY (ActeurNaam, isman) REFERENCES Acteur(Naam, isman),
  FOREIGN KEY (videoMateriaalNaam, videoMateriaalJaar, isSerie) REFERENCES VideoMateriaal(naam, jaar, isSerie)
);

CREATE TABLE Land(
  Naam VARCHAR(100),
  fullname VARCHAR(500),
  PRIMARY KEY (Naam)
);

CREATE TABLE VideoLand(
  landNaam VARCHAR(100),
  videoMateriaalNaam VARCHAR(500),
  videoMateriaalJaar INT,
  isSerie BOOLEAN,
  FOREIGN KEY (landNaam) REFERENCES Land(Naam),
  FOREIGN KEY (videoMateriaalNaam, videoMateriaalJaar, isSerie) REFERENCES VideoMateriaal(naam, jaar, isSerie)
);

CREATE TABLE Scrijver(
  naam VARCHAR(200),
  achternaam VARCHAR(200),
  PRIMARY KEY (Naam, achternaam)
);

CREATE TABLE VideoSchrijver(
  schrijverNaam VARCHAR(200),
  schrijverAchternaam VARCHAR(200),
  videoMateriaalNaam VARCHAR(500),
  videoMateriaalJaar INT,
  isSerie BOOLEAN,
  FOREIGN KEY (schrijverNaam, schrijverAchternaam) REFERENCES Scrijver(naam, achternaam),
  FOREIGN KEY (videoMateriaalNaam, videoMateriaalJaar, isSerie) REFERENCES videomateriaal(naam, jaar, isSerie)
);

CREATE TABLE opbrengsten (
  videomateriaalnaam VARCHAR(500),
  videomateriaaljaar INT,
  isserie BOOLEAN,
  valuta VARCHAR(20),
  hoeveelheid DECIMAL,
  origine VARCHAR(100),
  FOREIGN KEY (videomateriaalnaam, videomateriaaljaar, isserie REFERENCES videomateriaal(naam, jaar, isserie)
);
