--Add data to schrijver table
INSERT INTO bigmovie.schrijver (
  SELECT DISTINCT name FROM "bigmovieStaging".writers
);

--Add data to genre table
INSERT INTO bigmovie.genre (
  SELECT DISTINCT genre FROM "bigmovieStaging".genres
);

--Add data to land table
INSERT INTO bigmovie.land (
  SELECT DISTINCT country FROM "bigmovieStaging".countries
);

--Add data to director table
INSERT INTO bigmovie.regiseur (
  SELECT DISTINCT name FROM "bigmovieStaging".directors
);

--Add movies to videomateriaal table
INSERT INTO bigmovie.videomateriaal(naam, jaar, isserie) (
    SELECT DISTINCT name, releaseyear, videos.isserie FROM "bigmovieStaging".videos
);

--Add movie runtime to corresponding movie
UPDATE bigmovie.videomateriaal AS new
  SET duur = stage.runtime
  FROM "bigmovieStaging"."running-times" AS stage
  WHERE (new.naam = stage.moviename AND new.jaar = stage.releaseyear AND new.isserie = stage.isserie)
        AND (new.isserie = FALSE OR stage.isserie = FALSE)

--Add average runtime to series
UPDATE bigmovie.videomateriaal AS new
  SET duur = stage.runtime
  FROM (
      SELECT moviename, releaseyear, AVG(runtime) AS runtime, "running-times".isserie FROM "bigmovieStaging"."running-times"
      WHERE "running-times".isserie
      GROUP BY moviename, releaseyear, "running-times".isserie
       )AS stage
  WHERE (new.naam = stage.moviename AND new.jaar = stage.releaseyear AND new.isserie = stage.isserie)

--Add Mpaa ratings to videomateriaal
UPDATE bigmovie.videomateriaal AS new
  SET leeftijdcat = stage.rating
  FROM "bigmovieStaging"."MPAA-ratings" as stage
  WHERE new.naam = stage.moviename AND new.jaar = stage.releaseyear AND new.isserie = stage.inserie

--add movie rating to videomateriaal
UPDATE bigmovie.videomateriaal AS new
  SET beoordeling = stage.rating
  FROM "bigmovieStaging".ratings as stage
  WHERE new.naam = stage.moviename AND new.jaar = stage.releaseyear AND new.isserie = stage.isserie AND stage.isserie = FALSE

--Add average rating to series in videomateriaal
UPDATE bigmovie.videomateriaal AS new
  SET beoordeling = stage.rating
  FROM (
      SELECT moviename, releaseyear, AVG(rating) AS rating, ratings.isserie FROM "bigmovieStaging".ratings
      WHERE ratings.isserie = FALSE
      GROUP BY moviename, releaseyear, ratings.isserie) AS stage
  WHERE new.naam = stage.moviename AND new.jaar = stage.releaseyear AND new.isserie = stage.isserie
