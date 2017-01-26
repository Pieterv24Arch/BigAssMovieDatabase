package FileManipulation.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BiographyParser implements ParserBase {

    Pattern seperationPattern = Pattern.compile("(--)+");
    Pattern namePattern = Pattern.compile("NM: (.*)");
    Pattern birthPattern = Pattern.compile("DB: (.*)");

    //Namebuffer
    String name = "";

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
                if(birthInfo.length >= 3)
                {
                    String locationString = "";
                    for(int i = birthInfo.length-1; i >= 0; i--)
                    {
                        if(!birthInfo[i].matches(".*\\d+.*"))
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
