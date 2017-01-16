package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for the ratings list
 * @author David
 */
public class RatingParser implements ParserBase
{   
    /**
     * Parser logic is used on given line.
     * If line does not conform with patterns an empty string is returned.
     * @param line line to be parsed
     * @return parsed string or and empty string
     */
    public String parseString(String line)
    {
        Pattern movieRatingPattern = getPattern();
        Pattern serieRatingPattern = Pattern.compile(".*(\\d{1,2}\\.\\d)\\s*(.+)\\((\\d{4})\\)\\s*(?:\\{(.*?)(?:\\(#(\\d*?)\\.(\\d*?)\\))?\\}|)");

        
        if(serieRatingPattern.matcher(line).matches())
        {
            /**
             * Pattern Regex Ratings series:  .*(\d{1,2}\.\d)\s*(.+)\((\d{4})\)\s*(?:\{(.*?)(?:\(#(\d*?)\.(\d*?)\))?\}|)
             * Group 1: Rating
             * Group 2: Serie Title
             * Group 3: Year
             * Group 4: Episode Title; 
             * Group 5: Season Number; 
             * Group 6: Episode Number;
             */
            Matcher m = serieRatingPattern.matcher(line);
            if(m.find())
            {
                String Rating = m.group(1);
                String SerieTitle = m.group(2);
                String Year = m.group(3);
                String EpTitle = m.group(4);
                String SeasonNr = m.group(5);
                String EpNr = m.group(6);
                
                return String.format("%s%s%s", Rating, SerieTitle.trim(), Year, EpTitle.trim(), SeasonNr, EpNr);
            }
            return "";
        }
        else if (movieRatingPattern.matcher(line).matches())
        {
            /**
             * Pattern Regex Ratings movies:  .*(\d{1,2}\.\d)\s*(.+)\((\d{4})
             * Group 1: Rating
             * Group 2: Title
             * Group 3: Year
             */
            Matcher m = movieRatingPattern.matcher(line);
            if(m.find())
            {
                String Rating = m.group(1);
                String MovieTitle = m.group(2);
                String Year = m.group(3);
                
                return String.format("%s~%s~%s", Rating, MovieTitle.trim(), Year);
            }
            return "";  
        }
        return "";
    }

    public Pattern getPattern()
    {
        return Pattern.compile(".*(\\d{1,2}\\.\\d)\\s*(.+)\\((\\d{4})");
    }
}
