INSERT INTO bigmovie.schrijver (SELECT DISTINCT name FROM "bigmovieStaging".writers)

INSERT INTO bigmovie.videomateriaal(naam, jaar, isserie) (
    SELECT DISTINCT name, releaseyear, videos.isserie FROM "bigmovieStaging".videos
);
