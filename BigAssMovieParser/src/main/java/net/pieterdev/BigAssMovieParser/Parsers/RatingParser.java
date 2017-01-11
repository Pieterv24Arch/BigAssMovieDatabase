package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RatingParser implements ParserBase
{
    //Pattern Regex Ratings:  .*(\d{1,2}\.\d)\s*(.+)\((\d{4})
    //Group 1: Rating; Group 2: Title; Group 3: Year;
    
    Pattern ratingPattern = Pattern.compile(".*(\\d{1,2}\\.\\d)\\s*(.+)\\((\\d{4})");

    public String parseString(String line)
    {
        if(ratingPattern.matcher(line).matches())
        {
            /**
             * Group 1: Rating
             * Group 2: MovieTitle
             * Group 2: MovieYear
             */
            Matcher m = ratingPattern.matcher(line);
            if(m.find())
            {
                String rating;
                rating = m.group(1);
                
                String movieTitle;
                movieTitle = m.group(2);
                
                String movieYear;
                movieYear = m.group(3);
                
                return String.format("%s%s%s", m.group(1), m.group(2), m.group(3));
            }
        }
        return "";
    }

    public Pattern getPattern()
    {
        return null;
    }
}
