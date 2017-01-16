package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenreParser implements ParserBase{
    public String parseString(String line)
    {
        Pattern genrePattern = getPattern();
       
        Matcher m = genrePattern.matcher(line);
        System.out.println(line);
        if(m.find())
        {
<<<<<<< HEAD
            if(line.startsWith("\""))
            {System.out.println(m.group(4));
                return String.format("%s~%s~%s~%s~%s~%s", m.group(1).trim(), m.group(2), m.group(3).trim(), m.group(4), m.group(5), m.group(6));
            } else return String.format("%s~%s~%s", m.group(1).trim(), m.group(2), m.group(6));
        }
=======
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
>>>>>>> d70ca0496eb029f4317fa2916e9fa78b4f4d49a4
       
        return "";
    }

    public Pattern getPattern()
    {
        return Pattern.compile("(.*)\\(([\\d{4}]*)\\)\\s+(?:\\(.+\\)|\\{(.*?)(?:\\(#(\\d*?)\\.(\\d*?)\\))?\\}|)\\s+(.+)");
    }
}
