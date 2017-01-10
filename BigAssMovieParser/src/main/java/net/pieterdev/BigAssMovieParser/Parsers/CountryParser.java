package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountryParser implements ParserBase
{
    /*
    Regex pattern: (.*(\d{4}(\/\w*)?|\?{4})\))\s*(\{.*\})?\s*(\w+) 
    Group 1: Titel + jaar; 
    Group 2: Jaar (overslaan); 
    Group 3: Evt. versie in dat jaar (overslaan); 
    Group 4: Afleveringtitels (overslaan); 
    Group 5: Country;
    */
    Pattern countryPattern = Pattern.compile("(.*(\\d{4}(\\/\\w*)?|\\?{4})\\))\\s*(\\{.*\\})?\\s*(\\w+)");
    String movieTitle = "";
    String country = "";

    public String parseString(String line)
    {
        if(countryPattern.matcher(line).matches())
        {
            /**
             * Group 1: movieTitle + year
             * Group 5: country
             */
            Matcher m = countryPattern.matcher(line);
            if(m.find())
            {
                movieTitle = m.group(1);
                country = m.group(5);
                return String.format("%s%s", m.group(1), m.group(5));
            }
        }
        return "";
    }

    public Pattern getPattern()
    {
        return null;
    }
}
