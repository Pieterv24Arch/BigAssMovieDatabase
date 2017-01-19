package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Parser for the writers list
 * @author Pieter
 */
public class WriterParser implements ParserBase
{
    Pattern namePattern = Pattern.compile("(.*?),(.*?)\\t+(.*?)\\((\\d{4})(?:\\/)?[\\w]*?\\)(\\s*\\{(.*?)(\\(#(\\d*?)\\.(\\d*?)\\))\\})?.*");
    Pattern creationPattern = Pattern.compile("\\t{3}(.*?)\\((\\d{4})(?:\\/)?\\w*?\\)(\\s*?\\{(.*?)\\(#(\\d*?).(\\d*?)\\)\\})?.*");
    String[] writer = new String[2];

    /**
     * If empty line is detected the writer in the class is reset.
     * If string matches the name pattern that means a writer name is detected and this writer is in turn saved in the writers array.
     * If string matches the creation pattern the writer is derived from the writers array.
     * Return Format: Movie/SerieName~ReleaseYear~WriterFistName~WriterLastName~isSerie
     * @param line incomming information
     * @return parsed string or empty string if string does not conform to patterns
     */
    public String parseString(String line)
    {
        if(line.isEmpty())
        {
            writer = new String[2];
        }
        if(namePattern.matcher(line).matches())
        {
            /**
             * Group 1: Family name
             * Group 2: Frist Name
             * Group 3: Film/Serie Name
             * Group 4: Year of creation
             * Group 6: Episode name(if available)
             * Group 7: Season(if available)
             * Group 8: Episode Nr(if available)
             */
            Matcher m = namePattern.matcher(line);
            if(m.find())
            {
                writer[0] = m.group(2);
                writer[1] = m.group(1);
                for (int i = 1; i <= m.groupCount(); i++)
                {
                    if(m.group(i) != null)
                        m.group(i).trim();
                }
                String movieName = m.group(3);
                boolean isSerie = false;
                if(movieName.contains("\""))
                {
                    movieName = movieName.replace("\"", "");
                    isSerie = true;
                }
                return String.format("%s\t%s\t%s\t%s\t%s", movieName, m.group(4), m.group(2), m.group(1), isSerie);
            }
            return "";
        }
        else if(creationPattern.matcher(line).matches())
        {
            Matcher m = creationPattern.matcher(line);
            if(m.find())
            {
                /**
                 * Group 1: Film/Serie Name
                 * Group 2: Year of creation
                 * Group 4: Episode name(if available)
                 * Group 5: Season(if available)
                 * Group 6: Episode Nr(if available)
                 */
                if(writer[0] == null && writer[1] == null)
                {
                    return "";
                }
                else
                {
                    for (int i = 1; i <= m.groupCount(); i++)
                    {
                        if(m.group(i) != null)
                            m.group(i).trim();
                    }
                    String movieName = m.group(1);
                    boolean isSerie = false;
                    if(movieName.contains("\""))
                    {
                        movieName = movieName.replace("\"", "");
                        isSerie = true;
                    }
                    return String.format("%s\t%s\t%s\t%s\t%s", movieName, m.group(2), writer[0], writer[1], isSerie);
                }
            }
            return "";
        }
        return "";
    }

    public Pattern getPattern()
    {
        return Pattern.compile(".*");
    }
}
