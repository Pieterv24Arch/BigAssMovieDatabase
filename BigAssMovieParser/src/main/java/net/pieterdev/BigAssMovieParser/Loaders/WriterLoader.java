package net.pieterdev.BigAssMovieParser.Loaders;

import net.pieterdev.BigAssMovieParser.DataType;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;
import net.pieterdev.BigAssMovieParser.References;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.stream.Stream;

/**
 * Loader for the writers list.
 * this class loads the targeted file, sends lines to the parser and then writes those lines back to new files
 * @author Pieter
 */
public class WriterLoader extends BaseLoader
{
    /**
     * Declaration of target file and file type.
     */
    public WriterLoader(){ super("writerTest.txt", DataType.WRITERS); }

    /**
     * loader of the lines into the parser.
     * @param lines line stream from target file
     * @param dataType type of data
     * @throws FileNotFoundException
     */
    @Override
    void StreamOperations(Stream<String> lines, DataType dataType) throws FileNotFoundException
    {
        ParserBase parser = dataType.getParser();
        File writerFile = new File(References.WRITERS_PATH);

        PrintWriter writerWriter = new PrintWriter(writerFile);

        lines
                .map(parser::parseString)
                .filter(s -> !s.isEmpty())
                .forEach(writerWriter::println);
        writerWriter.close();

    }
}
