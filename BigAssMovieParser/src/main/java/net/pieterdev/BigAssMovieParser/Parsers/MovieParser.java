package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieParser implements ParserBase{
    public String parseString(String line)
    {
        Pattern moviePattern = Pattern.compile("(.*?)\\(([\\d{4}]*)(?:\\/)?[\\w]*?\\).*");
        Pattern seriePattern = Pattern.compile("(.*?)\\(([\\d{4}]*)(?:\\/)?[\\w]*?\\)\\s*\\{(.*?)(\\(#(\\d*?)\\.(\\d*?)\\))?\\}.*");

        if(seriePattern.matcher(line).matches())
        {
            Matcher m = seriePattern.matcher(line);
            if(m.find())
            {
                return String.format("%s~%s~%s~%s~%s", m.group(1).trim(), m.group(2), m.group(3).trim(), m.group(5), m.group(6));
            }
            return "";
        }
        else if(moviePattern.matcher(line).matches())
        {
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
}
