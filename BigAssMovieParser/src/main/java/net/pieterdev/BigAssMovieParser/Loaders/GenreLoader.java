package net.pieterdev.BigAssMovieParser.Loaders;

import net.pieterdev.BigAssMovieParser.DataType;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;
import net.pieterdev.BigAssMovieParser.References;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Loader for the genre list.
 * this class loads the targeted file, sends lines to the parser and then writes those lines back to new files
 * @author Lex
 */

public class GenreLoader extends BaseLoader {
     /**
     * Declaration of target file and file type.
     */
    public GenreLoader() {
        super("genresTest.txt", DataType.GERNES);
    }
}
    