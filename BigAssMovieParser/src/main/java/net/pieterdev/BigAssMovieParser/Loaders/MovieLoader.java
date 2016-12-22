package net.pieterdev.BigAssMovieParser.Loaders;

import net.pieterdev.BigAssMovieParser.DataType;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;
import net.pieterdev.BigAssMovieParser.References;

import java.io.File;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MovieLoader extends BaseLoader
{
    public MovieLoader()
    {
        super("movieTest.txt");
    }

    @Override
    void StreamOpperation(Stream<String> lines)
    {
        ParserBase parser = DataType.MOVIES.getParser();
        File movieFile = new File(References.MOVIES_PATH);
        File serieFile = new File(References.SERIES_PATH);
        try
        {
            PrintWriter movieWriter = new PrintWriter(movieFile);
            PrintWriter serieWriter = new PrintWriter(serieFile);

            Pattern pattern = Pattern.compile("(.*?)\\(([\\d{4}]*)(?:\\/)?[\\w]*?\\).*");
            lines
                    .filter(s -> pattern.matcher(s).matches())
                    .map(s -> parser.parseString(s))
                    .filter(s -> !s.isEmpty())
                    .forEach(s -> WriteTofile(movieWriter, serieWriter, s));
            movieWriter.close();
            serieWriter.close();
        }
        catch (Exception ex)
        {
            System.out.println("PrintWriter: " + ex);
        }
    }

    void WriteTofile(PrintWriter movieWriter, PrintWriter serieWriter, String line)
    {
        if(line.contains("\""))
        {
            serieWriter.println(line.replace("\"", ""));
        }
        else
        {
            movieWriter.println(line);
        }
    }
}
