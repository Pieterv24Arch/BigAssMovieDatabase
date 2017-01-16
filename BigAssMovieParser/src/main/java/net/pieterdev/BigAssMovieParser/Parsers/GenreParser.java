package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Parser for the movie genres
 * @author lexpunter
 */
public class GenreParser implements ParserBase{
        /**
        * Parser logic is used on given line.
         * If line does not conform with patterns an empty string is returned.
         * @param line line to be parsed
         * @return parsed string or and empty string
        */
    public String parseString(String line)
    {
        Pattern genrePattern = getPattern();
       
        Matcher m = genrePattern.matcher(line);
        System.out.println(line);
        if(m.find())
        {
            /**
             * Group 1: Movie/Serie name
             * Group 2: MovieYear
             * Group 3: Episode name, null if not found
             * Group 4: Season number, null if not found
             * Group 5: Episode number, null if not found
             * Group 6: Genre
             */
             if(line.startsWith("\""))
            {
                /**
                 * return if line contains a serie
                 */
                String serieName = m.group(1).replace("\"", "").trim();
                return String.format("%s~%s~%s~true", serieName, m.group(2), m.group(6));
            }   /**
                 * return if line contains a movie
                 */
            else return String.format("%s~%s~%s~false", m.group(1).trim(), m.group(2), m.group(6));
            }
       
        return "";
    }

    public Pattern getPattern()
    {
        return Pattern.compile("(.*)\\(([\\d{4}]*)(?:\\/\\w+)?\\)\\s*(?:\\(.+\\)\\s+)?(?:\\{(.*?)(?:\\(#(\\d+)\\.(\\d+)\\))?\\}\\s+)?(?:.*:)?(\\w+(?:-\\w+)?).*");
    }
}
