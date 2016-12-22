package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Pattern;

public interface ParserBase {
    String parseString(String line);
    Pattern getPattern();
}
