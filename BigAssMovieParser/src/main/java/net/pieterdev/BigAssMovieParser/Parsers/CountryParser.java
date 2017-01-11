package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountryParser implements ParserBase
{
   public String parseString(String line)
    {
        Pattern movieCountriesPattern = getPattern();
        Pattern serieCountriesPattern = Pattern.compile("(.*)\\((\\d{4}(?:\\/\\w*)?|\\?{4})\\)\\s+(?:\\(.+\\)|\\{(.*?)(?:\\(#(\\d*?)\\.(\\d*?)\\))?\\}|)\\s+(?:\\w+\\:|)(\\w+)");
        
        if(serieCountriesPattern.matcher(line).matches())
        {
            /**
             * Regex pattern series: (.*)\((\d{4}(?:\/\w*)?|\?{4})\)\s+(?:\(.+\)|\{(.*?)(?:\(#(\d*?)\.(\d*?)\))?\}|)\s+(?:\w+\:|)(\w+)
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
                String SerieTitle = m.group(1);
                String Year = m.group(2);
                String EpTitle = m.group(3);
                String SeasonNr = m.group(4);
                String EpNr = m.group(5);
                String Country = m.group(6);
                
                return String.format("%s~%s~%s~%s~%s~%s", SerieTitle.trim(), Year, EpTitle.trim(), SeasonNr, EpNr, Country.trim());
            }
            return "";
        }
        else if (movieCountriesPattern.matcher(line).matches())
        {
            /**
             * Regex pattern movies: (.*)\((\d{4}(?:\/\w*)?|\?{4})\)\s+(?:\(.+\)\s+)?(?:\w+\:|)(.*)
             * Group 1: Movie Title
             * Group 2: Year
             * Group 3: Country
             */
            Matcher m = movieCountriesPattern.matcher(line);
            if(m.find())
            {
                String Title = m.group(1);
                String Year = m.group(2);
                String Country = m.group(3);
                return String.format("%s~%s~%s", Title.trim(), Year, Country.trim());
            }
            return "";
        }
        else
            return "";
    }

    public Pattern getPattern()
    {
        return Pattern.compile("(.*)\\((\\d{4}(?:\\/\\w*)?|\\?{4})\\)\\s+(?:\\(.+\\)\\s+)?(?:\\w+\\:|)(.*)");
    }
}
