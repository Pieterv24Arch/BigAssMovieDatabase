package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for the countries list
 * @author David
 */
public class CountryParser implements ParserBase
{
    /**
     * Parser logic is used on given line.
     * If line does not conform with patterns an empty string is returned.
     * @param line line to be parsed
     * @return parsed string or and empty string
     */
   public String parseString(String line)
    {
        Pattern serieCountriesPattern = Pattern.compile("(.*)\\((\\d{4})(?:\\/\\w*?)\\)\\s+(?:\\(.+\\)|\\{(.*?)(?:\\(#(\\d*?)\\.(\\d*?)\\))?\\}|)\\s+(?:\\w+\\:|)(\\w+)");
        /**
         * Regex pattern series: (.*)\((\d{4}(?:\/\w*)?)\)\s+(?:\(.+\)|\{(.*?)(?:\(#(\d*?)\.(\d*?)\))?\}|)\s+(?:\w+\:|)(\w+)
         * Group 1: Serie Title; 
         * Group 2: Year; 
         * Group 3: Episode Title; 
         * Group 4: Season Number; 
         * Group 5: Episode Number;
         * Group 6: Country;
         */
        Matcher m = serieCountriesPattern.matcher(line);
        if(m.find())
        {
            if (m.group(1).startsWith("\""))
            {
                String SerieTitle = m.group(1).replace("\"", "");
                return String.format("%s\t%s\t%s\ttrue", SerieTitle.trim(), m.group(2), m.group(6));
            }
            return String.format("%s\t%s\t%s\tfalse", m.group(1).trim(), m.group(2), m.group(6));
        }
        return "";
    }

    public Pattern getPattern()
    {
        return Pattern.compile("(.*)\\((\\d{4}(?:\\/\\w*)?|\\?{4})\\)\\s+(?:\\(.+\\)\\s+)?(?:\\w+\\:|)(.*)");
    }
}
