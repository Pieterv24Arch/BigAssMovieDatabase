package fx.FileManipulation.Parsers;

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
        Pattern serieRatingPattern = Pattern.compile(".*(\\d{1,2}\\.\\d)\\s*(.+)\\((\\d{4})\\)\\s*(?:\\{(.*?)(?:\\(#(\\d*?)\\.(\\d*?)\\))?\\}|)");
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
            if (m.group(2).startsWith("\""))
            {
                String SerieTitle = m.group(2).replace("\"", "");
                return String.format("%s\t%s\t%s\ttrue", SerieTitle.trim(), m.group(3), m.group(1));
            }
            return String.format("%s\t%s\t%s\tfalse", m.group(2).trim(), m.group(3), m.group(1));
        }
    return "";
    }

    public Pattern getPattern()
    {
        return Pattern.compile(".*(\\d{1,2}\\.\\d)\\s*(.+)\\((\\d{4}).+");
    }
}
