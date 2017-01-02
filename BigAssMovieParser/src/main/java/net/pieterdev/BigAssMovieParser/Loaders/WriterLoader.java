package net.pieterdev.BigAssMovieParser.Loaders;

import net.pieterdev.BigAssMovieParser.DataType;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;
import net.pieterdev.BigAssMovieParser.References;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.stream.Stream;

public class WriterLoader extends BaseLoader
{
    public WriterLoader(){ super("writerTest.txt", DataType.WRITERS); }

    @Override
    void StreamOperations(Stream<String> lines, DataType dataType) throws FileNotFoundException
    {
        ParserBase parser = DataType.WRITERS.getParser();
        File writerFile = new File(References.WRITERS_PATH);

        PrintWriter writerWriter = new PrintWriter(writerFile);

        lines
                .map(parser::parseString)
                .filter(s -> !s.isEmpty())
                .forEach(writerWriter::println);
        writerWriter.close();

    }
}
