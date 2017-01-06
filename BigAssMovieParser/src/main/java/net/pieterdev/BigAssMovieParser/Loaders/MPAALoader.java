package net.pieterdev.BigAssMovieParser.Loaders;

import net.pieterdev.BigAssMovieParser.DataType;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;
import net.pieterdev.BigAssMovieParser.References;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.stream.Stream;

public class MPAALoader extends BaseLoader
{
    public MPAALoader(){super("mpaaTest.txt", DataType.MPAA);}

    @Override
    void StreamOperations(Stream<String> lines, DataType dataType) throws FileNotFoundException
    {
        ParserBase parser = dataType.getParser();
        File mpaaFile = new File(References.MPAA_PATH);

        PrintWriter mpaaWriter = new PrintWriter(mpaaFile);

        lines
                .filter(s -> !s.isEmpty())
                .map(parser::parseString)
                .filter(s -> !s.isEmpty())
                .forEach(mpaaWriter::println);
        mpaaWriter.close();
    }
}
