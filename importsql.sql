COPY "bigmovieStaging".actors FROM '/home/pieter/Documents/Lists/Parsed/actors.csv' DELIMITER E'\t' CSV QUOTE '|';

COPY "bigmovieStaging".actors FROM '/home/pieter/Documents/Lists/Parsed/actresses.csv' DELIMITER E'\t' CSV QUOTE '|';

COPY "bigmovieStaging"."production-companies" FROM '/home/pieter/Documents/Lists/Parsed/production.csv' DELIMITER E'\t' CSV QUOTE '|';

COPY "bigmovieStaging".business FROM '/home/pieter/Documents/Lists/Parsed/business.csv' DELIMITER E'\t' CSV QUOTE '|';

COPY "bigmovieStaging".biographies FROM '/home/pieter/Documents/Lists/Parsed/biogrphies.csv' DELIMITER E'\t' CSV QUOTE '|';

COPY "bigmovieStaging".directors FROM '/home/pieter/Documents/Lists/Parsed/directors.csv' DELIMITER E'\t' CSV QUOTE '|';

COPY "bigmovieStaging".genres FROM '/home/pieter/Documents/Lists/Parsed/genres.csv' DELIMITER E'\t' CSV QUOTE '|';

COPY "bigmovieStaging"."MPAA-ratings" FROM '/home/pieter/Documents/Lists/Parsed/MPAA-ratings.csv' DELIMITER E'\t' CSV QUOTE '|';

COPY "bigmovieStaging".ratings FROM '/home/pieter/Documents/Lists/Parsed/ratings.csv' DELIMITER E'\t' CSV QUOTE '|';

COPY "bigmovieStaging"."running-times" FROM '/home/pieter/Documents/Lists/Parsed/running-times.csv' DELIMITER E'\t' CSV QUOTE '|';

COPY "bigmovieStaging".videos FROM '/home/pieter/Documents/Lists/Parsed/movies.csv' DELIMITER E'\t' CSV QUOTE '|';

COPY "bigmovieStaging".writers FROM '/home/pieter/Documents/Lists/Parsed/writers.csv' DELIMITER E'\t' CSV QUOTE '|';
