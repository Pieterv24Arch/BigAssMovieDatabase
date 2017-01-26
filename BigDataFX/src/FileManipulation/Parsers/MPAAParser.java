package FileManipulation.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for the MPAA Ratings
 * @author Pieter
 */
public class MPAAParser implements ParserBase
{
    Pattern moviePattern = Pattern.compile("MV: (.*?)(?: \\()(\\d{4})(?:\\/)?[\\w]*?\\).*");
    Pattern ratingPattern = Pattern.compile("RE: Rated (PG-13|R|PG|R|NC-17).*");
    Pattern splitPattern = Pattern.compile("(--)+");
    String movieName = "";
    String movieYear = "";

    /**
     * movieName and movieYear are a buffer.
     * The buffer is emptied everytime a sequence of --- is found.
     * If movie name/year is found buffer is filled.
     * If rating is found it will be appended with the buffer and returned.
     * Returns format: Movie/SerieName~ReleaseYear~Rating~isSerie
     * @param line line to parse
     * @return parsed string if complete dataset is found otherwise and empty string will be returned.
     */
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
                if(!movieYear.isEmpty() && !movieName.isEmpty()) {
                    boolean isSerie = false;
                    if (movieName.contains("\"")) {
                        movieName = movieName.replace("\"", "");
                        isSerie = true;
                    }
                    return String.format("%s\t%s\t%s\t%s", movieName, movieYear, m.group(1), isSerie);
                }
                return "";
            }
        }
        return "";
    }

    public Pattern getPattern()
    {
        return Pattern.compile(".*");
    }
}
