package net.pieterdev.BigAssMovieParser.Loaders;

import net.pieterdev.BigAssMovieParser.DataType;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MovieLoader extends BaseLoader {

    public MovieLoader(String sourceFile, String targetFile) {

        try(Stream<String> lines = Files.lines(new File(sourceFile).toPath(), StandardCharsets.ISO_8859_1)){
            StreamOperations(lines, targetFile);
            lines.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void StreamOperations(Stream<String> lines, String targetLocation) throws FileNotFoundException {
        ParserBase parser = DataType.MOVIES.getParser();
        File movieFile = new File(targetLocation + "/moviesexport.csv");
        File seriesFile = new File(targetLocation + "/seriesexport.csv");

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
