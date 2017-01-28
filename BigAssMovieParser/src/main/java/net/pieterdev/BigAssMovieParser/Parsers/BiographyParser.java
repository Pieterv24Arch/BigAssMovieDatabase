package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Parser for the Biography list
 * @author Pieter
 */
public class BiographyParser implements ParserBase {

    Pattern seperationPattern = Pattern.compile("-{2,}");
    Pattern namePattern = Pattern.compile("NM: (.*)");
    Pattern birthPattern = Pattern.compile("DB: (.*)");

    //Namebuffer
    String name = "";

    /**
     * Actor/Actress Name is buffer
     * The buffer is emptied everytime a sequence of --- is found.
     * If Name is found buffer is filled.
     * If Birthplace is found it will be appended with the buffer and returned.
     * @param line line to parse
     * @return parsed string if complete dataset is found otherwise and empty string will be returned.
     */
    @Override
    public String parseString(String line) {
        if(seperationPattern.matcher(line).matches())
        {
            name = "";
        }
        else if(namePattern.matcher(line).matches())
        {
            Matcher m = namePattern.matcher(line);
            if(m.find())
            {
                name = m.group(1);
            }
        }
        else if(birthPattern.matcher(line).matches())
        {
            Matcher m = birthPattern.matcher(line);
            if(m.find())
            {
                String[] birthInfo = m.group(1).split(",");
                //Check if birthplace has enough information
                if(birthInfo.length >= 3)
                {
                    String locationString = "";
                    for(int i = birthInfo.length-1; i >= 0; i--)
                    {
                        /**
                         * check if there is a number or questionmark in the birthplace
                         * add to location if not a number or questionmark in there
                         */
                        if(!birthInfo[i].matches("(.*\\d+.*|\\?+)"))
                        {
                            locationString = birthInfo[i] + "," + locationString;
                        }
                    }
                    if(name != "")
                    {
                        return String.format("%s~%s", name, locationString);
                    }
                }
            }
        }
        return "";
    }

    @Override
    public Pattern getPattern() {
        return Pattern.compile("(?:(NM: (.*))|(DB: (.*))|(?:--)+)");
    }
}
