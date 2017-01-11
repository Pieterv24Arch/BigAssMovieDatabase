package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for the movies list
 * @author Pieter
 */
public class MovieParser implements ParserBase{
    /**
     * Parser logic is used on given line.
     * If line does not conform with patterns an empty string is returned.
     * @param line line to be parsed
     * @return parsed string or and empty string
     */
    public String parseString(String line)
    {
        Pattern moviePattern = getPattern();
        Pattern seriePattern = Pattern.compile("(.*?)\\(([\\d{4}]*)(?:\\/)?[\\w]*?\\)\\s*\\{(.*?)(\\(#(\\d*?)\\.(\\d*?)\\))?\\}.*");

        if(seriePattern.matcher(line).matches())
        {
            /**
             * Group 1: Movie/Serie Name.
             * Group 2: Movie/Serie Year.
             * Group 3: Episode name(null if not episode)
             * Group 5: Season nr(null if not found)
             * Group 6: Episode nr(null if not found)
             */
            Matcher m = seriePattern.matcher(line);
            if(m.find())
            {
                return String.format("%s~%s~%s~%s~%s", m.group(1).trim(), m.group(2), m.group(3).trim(), m.group(5), m.group(6));
            }
            return "";
        }
        else if(moviePattern.matcher(line).matches())
        {
            /**
             * Group 1: Movie/Serie Name.
             * Group 2: Movie/Serie Year.
             */
            Matcher m = moviePattern.matcher(line);
            if(m.find())
            {
                return String.format("%s~%s", m.group(1).trim(), m.group(2));
            }
            return "";
        }
        else
            return "";
    }

    public Pattern getPattern()
    {
        return Pattern.compile("(.*?)\\(([\\d{4}]*)(?:\\/)?[\\w]*?\\).*");
    }
}
