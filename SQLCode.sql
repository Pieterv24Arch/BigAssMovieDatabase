CREATE TABLE VideoMateriaal (
  naam VARCHAR(500),
  jaar INT,
  duur INT,
  beoordeling DECIMAL,
  leeftijdCat VARCHAR(50),
  isSerie BOOLEAN,
  PRIMARY KEY (naam, jaar)
);

CREATE TABLE Genre (
  type VARCHAR(100),
  PRIMARY KEY (type)
);

CREATE TABLE VideoGenre(
  genreType VARCHAR(100),
  videoMateriaalNaam VARCHAR(500),
  videoMateriaalJaar INT,
  FOREIGN KEY (genreType) REFERENCES Genre(type),
  FOREIGN KEY (videoMateriaalNaam, videoMateriaalJaar) REFERENCES VideoMateriaal(naam, jaar)
);

CREATE TABLE Regiseur (
  Naam VARCHAR(200),
  PRIMARY KEY (Naam)
);

CREATE TABLE VideoRegiseur (
  RegiseurNaam VARCHAR(200),
  videoMateriaalNaam VARCHAR(500),
  videoMateriaalJaar INT,
  FOREIGN KEY (RegiseurNaam) REFERENCES Regiseur(Naam),
  FOREIGN KEY (videoMateriaalNaam, videoMateriaalJaar) REFERENCES VideoMateriaal(naam, jaar)
);

CREATE TABLE Acteur(
  Naam VARCHAR(200),
  geslacht BOOLEAN,
  geboortePlaats VARCHAR(400),
  PRIMARY KEY (Naam)
);

CREATE TABLE Rol (
  ActeurNaam VARCHAR(200),
  videoMateriaalNaam VARCHAR(500),
  videoMateriaalJaar INT,
  rolNaam VARCHAR(200),
  priroriteit INT,
  FOREIGN KEY (ActeurNaam) REFERENCES Acteur(Naam),
  FOREIGN KEY (videoMateriaalNaam, videoMateriaalJaar) REFERENCES VideoMateriaal(naam, jaar)
);