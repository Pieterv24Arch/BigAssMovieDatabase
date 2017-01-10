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
    public CountryLoader()
    {
        super("countries.txt", DataType.COUNTRIES);
    }

    @Override
    void StreamOperations(Stream<String> lines, DataType dataType) throws FileNotFoundException
    {
        
        ParserBase parser = dataType.getParser();
        File countryFile = new File(References.COUNTRIES_PATH);
  
        PrintWriter countryWriter = new PrintWriter(countryFile);

        lines
                .filter(s -> !s.isEmpty())
                .map(parser::parseString)
                .filter(s -> !s.isEmpty())
                .forEach(countryWriter::println);
        countryWriter.close();
    }
}
