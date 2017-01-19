package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Parser for the director list
 * @author rainslayerx
 */
public class DirectorParser implements ParserBase
{
    Pattern namePattern = Pattern.compile("(.*)\\t\\t(.*)\\s\\S(\\d*)");
    static String prevDirector = "";

    public String parseString(String line)
    {
        if(namePattern.matcher(line).matches())
        {
            Matcher m = namePattern.matcher(line);
            if(m.find())
            {
                for (int i = 1; i <= m.groupCount(); i++)
                    if(m.group(i) != null)
                        m.group(i).trim();

                if(!m.group(1).isEmpty())
                    prevDirector = m.group(1);

                if(m.group(3).isEmpty())
                    return "";

                return String.format("%s\t%s\t%s", prevDirector, m.group(2), m.group(3));
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
