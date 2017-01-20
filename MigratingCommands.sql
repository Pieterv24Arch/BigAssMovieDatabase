INSERT INTO bigmovie.schrijver (
  SELECT DISTINCT name FROM "bigmovieStaging".writers
);

INSERT INTO bigmovie.genre (
  SELECT DISTINCT genre FROM "bigmovieStaging".genres
);

INSERT INTO bigmovie.land (
  SELECT DISTINCT country FROM "bigmovieStaging".countries
);

INSERT INTO bigmovie.regiseur (
  SELECT DISTINCT name FROM "bigmovieStaging".directors
);

INSERT INTO bigmovie.videomateriaal(naam, jaar, isserie) (
    SELECT DISTINCT name, releaseyear, videos.isserie FROM "bigmovieStaging".videos
);

UPDATE bigmovie.videomateriaal AS new
  SET duur = stage.runtime
  FROM "bigmovieStaging"."running-times" AS stage
  WHERE (new.naam = stage.moviename AND new.jaar = stage.releaseyear AND new.isserie = stage.isserie)
        AND (new.isserie = FALSE OR stage.isserie = FALSE)
