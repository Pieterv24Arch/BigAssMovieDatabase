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

public class BaseLoader {
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

    public BaseLoader() {
    }

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
