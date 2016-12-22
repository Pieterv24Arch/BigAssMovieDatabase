package net.pieterdev.BigAssMovieParser.Loaders;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public abstract class BaseLoader {
    public BaseLoader(String sourceFile)
    {
        try(Stream<String> lines = Files.lines(getPath(sourceFile)))
        {
            StreamOpperation(lines);
        }
        catch (Exception ex)
        {
            System.out.println("Stream: " + ex);
        }
    }

    abstract void StreamOpperation(Stream<String> lines);

    private Path getPath(String pathString) throws URISyntaxException
    {
        return Paths.get(ClassLoader.getSystemResource(pathString).toURI());
    }
}
