package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MPAAParser implements ParserBase
{
    Pattern moviePattern = Pattern.compile("MV: (.*?)(?: \\()(\\d{4})(?:\\/)?[\\w]*?\\).*");
    Pattern ratingPattern = Pattern.compile("RE: Rated (PG-13|R|PG|R|NC-17).*");
    Pattern splitPattern = Pattern.compile("(--)+");
    String movieName = "";
    String movieYear = "";

    public String parseString(String line)
    {
        if(splitPattern.matcher(line).matches())
        {
            movieName = "";
            movieYear = "";
        }
        if(moviePattern.matcher(line).matches())
        {
            /**
             * Group 1: Moviename
             * Group 2: MovieYear
             */
            Matcher m = moviePattern.matcher(line);
            if(m.find())
            {
                movieName = m.group(1);
                movieYear = m.group(2);
            }
        }
        if(ratingPattern.matcher(line).matches())
        {
            /**
             * Group 1: Rating
             */
            Matcher m = ratingPattern.matcher(line);
            if(m.find())
            {
                if(!movieYear.isEmpty() && !movieName.isEmpty())
                    return String.format("%s~%s~%s", movieName, movieYear, m.group(1));
                return "";
            }
        }
        return "";
    }

    public Pattern getPattern()
    {
        return null;
    }
}
