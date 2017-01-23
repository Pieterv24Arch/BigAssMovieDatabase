package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Parser for the director list
 * @author rainslayerx
 */
public class DirectorParser implements ParserBase
{
    Pattern namePattern = Pattern.compile("(.*?),(.*?)\\t+(.*?)\\((\\d{4})(?:\\/)?\\w*?\\)(?:\\s*\\{(.*?)(\\(#(\\d*?)\\.(\\d*?)\\))\\})?.*");
    Pattern creationPattern = Pattern.compile("\\t{3}(.*?)\\((\\d{4})(?:\\/)?\\w*?\\)(?:\\s*?\\{(.*?)\\(#(\\d*?).(\\d*?)\\)\\})?.*");

    String prevDirector = "";

    public String parseString(String line)
    {
        if(line.isEmpty())
            prevDirector = "";
        else if(namePattern.matcher(line).matches())
        {
            Matcher m = namePattern.matcher(line);
            if(m.find())
            {
                prevDirector = m.group(1) + "," + m.group(2);
                String movieName = m.group(3);
                boolean isSerie = false;
                if(movieName.contains("\""))
                {
                    movieName = movieName.replace("\"", "");
                    isSerie = true;
                }
                return String.format("%s\t%s\t%s\t%s", movieName.trim(), m.group(4).trim(), prevDirector.trim(), isSerie);
            }
        }
        else if(creationPattern.matcher(line).matches())
        {
            Matcher m = creationPattern.matcher(line);
            if(m.find())
            {
                if(prevDirector == "")
                    return "";
                else
                {
                    String movieName = m.group(1);
                    Boolean isSerie = false;
                    if(movieName.contains("\""))
                    {
                        movieName = movieName.replace("\"", "");
                        isSerie = true;
                    }
                    return String.format("%s\t%s\t%s\t%s", movieName.trim(), m.group(2).trim(), prevDirector.trim(), isSerie);
                }
            }
        }
        return "";
    }

    public Pattern getPattern()
    {
        return Pattern.compile(".*");
    }
}
