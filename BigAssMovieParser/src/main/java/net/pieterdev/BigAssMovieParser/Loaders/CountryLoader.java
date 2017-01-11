package net.pieterdev.BigAssMovieParser.Loaders;

import net.pieterdev.BigAssMovieParser.DataType;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import net.pieterdev.BigAssMovieParser.References;

public class CountryLoader extends BaseLoader
{
    public CountryLoader(String sourceFile, String targetFile)
    {
        super(sourceFile, targetFile, DataType.COUNTRIES);
    }

    public void StreamOperations(Stream<String> lines, DataType dataType, String targetLocation) throws FileNotFoundException
    {
        ParserBase parser = dataType.getParser();
        File MovieCountriesFile = new File(targetLocation + "MovieCountries.txt");
        File SerieCountriesFile = new File(targetLocation + "SerieCountries.txt");

        PrintWriter MovieCountriesWriter = new PrintWriter(MovieCountriesFile);
        PrintWriter SerieCountriesWriter = new PrintWriter(SerieCountriesFile);

        assert parser != null;
        
        lines
                .map(parser::parseString)
                .filter(s -> !s.isEmpty())
                .forEach(s -> WriteTofile(MovieCountriesWriter, SerieCountriesWriter, s));
        MovieCountriesWriter.close();
        SerieCountriesWriter.close();
    }
    
    private void WriteTofile(PrintWriter MovieCountriesWriter, PrintWriter SerieCountriesWriter, String line) {
        if (line.contains("\"")) {
            SerieCountriesWriter.println(line.replace("\"", ""));
        } else {
            MovieCountriesWriter.println(line);
        }
    }
}
