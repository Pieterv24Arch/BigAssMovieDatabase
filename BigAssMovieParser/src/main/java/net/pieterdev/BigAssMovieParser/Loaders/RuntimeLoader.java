package net.pieterdev.BigAssMovieParser.Loaders;

import net.pieterdev.BigAssMovieParser.DataType;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;
import net.pieterdev.BigAssMovieParser.References;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class RuntimeLoader extends BaseLoader {
    public RuntimeLoader() {
        super("runtimeTest.txt", DataType.RUNTIMES);
    }
}
    