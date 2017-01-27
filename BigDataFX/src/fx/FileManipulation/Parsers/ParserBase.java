package fx.FileManipulation.Parsers;

import java.util.regex.Pattern;

/**
 * base interface for all parsers.
 * @author Pieter
 */
public interface ParserBase {
    String parseString(String line);
    Pattern getPattern();
}
