package fx.FileManipulation.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for the movies list
 * @author Pieter
 */
public class MovieParser implements ParserBase {
    /**
     * Parser logic is used on given line.
     * If line does not conform with patterns an empty string is returned.
     * Fromat Returned: Movie/SerieName~ReleaseYear~isSerie
     *
     * @param line line to be parsed
     * @return parsed string or and empty string
     */
    public String parseString(String line) {
        Pattern moviePattern = getPattern();
        Pattern seriePattern = Pattern.compile("(.*?)(?: \\()([\\d{4}]*)(?:\\/)?[\\w]*?\\)\\s*\\{(.*?)(\\(#(\\d*?)\\.(\\d*?)\\))?\\}.*");

        //Remove series that have episodes0
        if (seriePattern.matcher(line).matches()) {
            /**
             * Because we don't want duplicates these are to be removed
             * Group 1: Movie/Serie Name.
             * Group 2: Movie/Serie Year.
             * Group 3: Episode name(null if not episode)
             * Group 5: Season nr(null if not found)
             * Group 6: Episode nr(null if not found)
             */
            return "";
        } else if (moviePattern.matcher(line).matches()) {
            /**
             * Group 1: Movie/Serie Name.
             * Group 2: Movie/Serie Year.
             */
            Matcher m = moviePattern.matcher(line);
            if (m.find()) {
                return isSerie(m);
            }
            return "";
        } else
            return "";
    }

    private String isSerie(Matcher m)
    {
        String name = m.group(1);
        boolean isSerie = false;
        if (name.contains("\"")) {
            name = name.replace("\"", "");
            isSerie = true;
        }
        return String.format("%s\t%s\t%s", name.trim(), m.group(2), isSerie);
    }

    public Pattern getPattern()
    {
        return Pattern.compile("(.*?)(?: \\()([\\d{4}]*)(?:\\/)?[\\w]*?\\).*");
    }
}
