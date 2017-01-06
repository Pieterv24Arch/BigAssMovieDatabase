package net.pieterdev.BigAssMovieParser.Loaders;

import net.pieterdev.BigAssMovieParser.DataType;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;
import net.pieterdev.BigAssMovieParser.References;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MovieLoader extends BaseLoader {
    public MovieLoader() {
        super("movieTest.txt", DataType.MOVIES);
    }

    @Override
    void StreamOperations(Stream<String> lines, DataType dataType) throws FileNotFoundException {
        ParserBase parser = dataType.getParser();
        File movieFile = new File(References.MOVIES_PATH);
        File seriesFile = new File(References.SERIES_PATH);

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

    private void WriteTofile(PrintWriter movieWriter, PrintWriter serieWriter, String line) {
        if (line.contains("\"")) {
            serieWriter.println(line.replace("\"", ""));
        } else {
            movieWriter.println(line);
        }
    }
}
