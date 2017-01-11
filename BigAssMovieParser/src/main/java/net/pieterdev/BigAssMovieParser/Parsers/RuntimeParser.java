package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuntimeParser implements ParserBase{
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
             * Group 3: Episode name
             * Group 4: Season number
             * Group 5: Episode number
             * Group 6: Genre
             */
            if(line.startsWith("\""))
            {
                return String.format("%s~%s~%s~%s~%s~%s", m.group(1).trim(), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6));
            } else return String.format("%s~%s~%s", m.group(1).trim(), m.group(2), m.group(6));
        }
       
        return "";
    }

    public Pattern getPattern()
    {
        return Pattern.compile("(.*)\\(([\\d{4}]*)\\)\\s+(?:\\(.+\\)|\\{(.*?)(?:\\(#(\\d*?)\\.(\\d*?)\\))?\\}|)\\s+(?:\\w+\\:|)(\\d+)");
    }
}
