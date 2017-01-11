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

/**
 * Base class for the loaders.
 * The purpose of a loader is to handle the input of data into the parser
 * and hence into the output file.
 * @author Pieter
 */
public class BaseLoader {

    /**
     * Constructor for the base loader.
     * Requires source/target and type of file(what parser should be used)
     * @param sourceFile file to be parsed
     * @param targetFile file with parsed data
     * @param dataType type of sourcefile(what parser should be used)
     */
    public BaseLoader(String sourceFile, String targetFile, DataType dataType)
    {
        try(Stream<String> lines = Files.lines(new File(sourceFile).toPath(), StandardCharsets.ISO_8859_1))
        {
            StreamOperations(lines, dataType, targetFile);
            lines.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * Stream to pass lines to the parser and to the output file.
     * @param lines stream of lines from the input file
     * @param dataType type of parser to be used
     * @param targetLocation target file for the parsed data to go
     * @throws FileNotFoundException
     */
    public void StreamOperations(Stream<String> lines, DataType dataType, String targetLocation) throws FileNotFoundException {
        ParserBase parser = dataType.getParser();

        File targetFile = new File(targetLocation);
        PrintWriter writer = new PrintWriter(targetFile);

        assert parser != null;
        Pattern pattern = parser.getPattern();
        lines
                .filter(s -> pattern.matcher(s).matches())
                .map(parser::parseString)
                .filter(s -> !s.isEmpty())
                .forEach(writer::println);
        writer.close();
    }
}
