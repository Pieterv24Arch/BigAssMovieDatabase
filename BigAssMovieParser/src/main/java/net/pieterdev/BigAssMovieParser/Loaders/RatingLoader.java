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
    public RatingLoader()
    {
        super("ratings.txt", DataType.RATINGS);
    }

    @Override
    void StreamOperations(Stream<String> lines, DataType dataType) throws FileNotFoundException
    {
        ParserBase parser = dataType.getParser();
        File ratingFile = new File(References.RATINGS_PATH);
        
        PrintWriter ratingWriter = new PrintWriter(ratingFile);

        lines
                .filter(s -> !s.isEmpty())
                .map(parser::parseString)
                .filter(s -> !s.isEmpty())
                .forEach(ratingWriter::println);
        ratingWriter.close();
    }
}