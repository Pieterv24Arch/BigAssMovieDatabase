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

CREATE TABLE Acteur(
  Naam VARCHAR(200),
  geslacht BOOLEAN,
  PRIMARY KEY (Naam)
);

CREATE TABLE Rol (
  ActeurNaam VARCHAR(200),
  videoMateriaalNaam VARCHAR(500),
  videoMateriaalJaar INT,
  isSerie BOOLEAN,
  rolNaam VARCHAR(200),
  priroriteit INT,
  FOREIGN KEY (ActeurNaam) REFERENCES Acteur(Naam),
  FOREIGN KEY (videoMateriaalNaam, videoMateriaalJaar, isSerie) REFERENCES VideoMateriaal(naam, jaar, isSerie)
);

CREATE TABLE Land(
  Naam VARCHAR(100),
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

CREATE TABLE bigmovie.Scrijver(
  Naam VARCHAR(200),
  PRIMARY KEY (Naam)
);

CREATE TABLE bigmovie.VideoSchrijver(
  schrijverNaam VARCHAR(200),
  videoMateriaalNaam VARCHAR(500),
  videoMateriaalJaar INT,
  isSerie BOOLEAN,
  FOREIGN KEY (schrijverNaam) REFERENCES Scrijver(Naam),
  FOREIGN KEY (videoMateriaalNaam, videoMateriaalJaar, isSerie) REFERENCES videomateriaal(naam, jaar, isSerie)
);
