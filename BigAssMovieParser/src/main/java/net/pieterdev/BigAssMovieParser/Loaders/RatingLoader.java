package net.pieterdev.BigAssMovieParser.Loaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import net.pieterdev.BigAssMovieParser.DataType;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;
import net.pieterdev.BigAssMovieParser.References;

public class RatingLoader extends BaseLoader
{
    public RatingLoader(String sourceFile, String targetFile)
    {
        super(sourceFile, targetFile, DataType.RATINGS);
    }

    @Override
    public void StreamOperations(Stream<String> lines, DataType dataType, String targetLocation) throws FileNotFoundException
    {
        ParserBase parser = dataType.getParser();
        File movieRatingFile = new File(targetLocation + "movieRating.txt");
        File serieRatingFile = new File(targetLocation + "serieRating.txt");

        PrintWriter movieRatingWriter = new PrintWriter(movieRatingFile);
        PrintWriter serieRatingWriter = new PrintWriter(serieRatingFile);
        
        assert parser != null;
        lines
                .map(parser::parseString)
                .filter(s -> !s.isEmpty())
                .forEach(s -> WriteTofile(movieRatingWriter, serieRatingWriter, s));
        movieRatingWriter.close();
        serieRatingWriter.close();
    }
    
    private void WriteTofile(PrintWriter movieRatingWriter, PrintWriter serieRatingWriter, String line) {
        if (line.contains("\"")) {
            serieRatingWriter.println(line.replace("\"", ""));
        } else {
            movieRatingWriter.println(line);
        }
    }
}