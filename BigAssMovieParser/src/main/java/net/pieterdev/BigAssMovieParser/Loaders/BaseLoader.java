package net.pieterdev.BigAssMovieParser.Loaders;

import net.pieterdev.BigAssMovieParser.DataType;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;
import net.pieterdev.BigAssMovieParser.References;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public abstract class BaseLoader {
    public BaseLoader(String sourceFile, DataType dataType)
    {
        try(Stream<String> lines = Files.lines(getPath(sourceFile)))
        {
            StreamOperations(lines, dataType);
        }
        catch (Exception ex)
        {
            System.out.println("Stream: " + ex);
        }
    }

    void StreamOperations(Stream<String> lines, DataType dataType) throws FileNotFoundException {
        ParserBase parser = dataType.getParser();

        File targetFile = new File(References.MOVIES_PATH);
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

    private Path getPath(String pathString) throws URISyntaxException
    {
        return Paths.get(ClassLoader.getSystemResource(pathString).toURI());
    }
}
