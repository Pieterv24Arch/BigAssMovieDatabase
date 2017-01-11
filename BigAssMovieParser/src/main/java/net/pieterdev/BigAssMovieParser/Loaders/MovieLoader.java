package net.pieterdev.BigAssMovieParser.Loaders;

import net.pieterdev.BigAssMovieParser.DataType;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Loader for the movie list.
 * @author Pieter
 */
public class MovieLoader extends BaseLoader {

    public MovieLoader(String sourceFile, String targetFile) {
        super(sourceFile, targetFile, DataType.MOVIES);
    }

    @Override
    public void StreamOperations(Stream<String> lines, DataType dataType, String targetLocation) throws FileNotFoundException {
        ParserBase parser = dataType.getParser();
        File movieFile = new File(targetLocation + "Movies.txt");
        File seriesFile = new File(targetLocation + "Series.txt");

        PrintWriter movieWriter = new PrintWriter(movieFile);
        PrintWriter seriesWriter = new PrintWriter(seriesFile);

        Pattern pattern = Pattern.compile("(.*?)\\(([\\d{4}]*)(?:\\/)?[\\w]*?\\).*");
        assert parser != null;
        lines
                .filter(s -> pattern.matcher(s).matches())
                .map(parser::parseString)
                .filter(s -> !s.isEmpty())
                .forEach(s -> WriteTofile(movieWriter, seriesWriter, s));
        movieWriter.close();
        seriesWriter.close();
    }

    /**
     * Splits of series from movies into 2 seperate files.
     * @param movieWriter writer for the movies file
     * @param serieWriter writer for the series file
     * @param line parsed line to be written
     */
    private void WriteTofile(PrintWriter movieWriter, PrintWriter serieWriter, String line) {
        if (line.contains("\"")) {
            serieWriter.println(line.replace("\"", ""));
        } else {
            movieWriter.println(line);
        }
    }
}
