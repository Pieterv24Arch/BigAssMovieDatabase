package net.pieterdev.BigAssMovieParser.Parsers;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusinessParser implements ParserBase {

    Pattern moviePattern = Pattern.compile("MV: (.*?)\\(([\\d{4}]*)(?:\\/)?[\\w]*?\\)");
    Pattern moneyPattern = Pattern.compile("GR: (\\w+)\\s(.*?)\\s(?:\\((.*?)\\))?.*");
    Pattern divisionPatter = Pattern.compile("-{2,}");

    String movieName = "";
    String movieYear = "";
    Boolean isSerie = false;

    /**
     * movieName, movieYear and isSerie are a buffer.
     * The buffer is emptied everytime a sequence of --- is found.
     * If movie name/year is found buffer is filled.
     * If MovieGross is found it will be appended with the buffer and returned.
     * @param line line to parse
     * @return parsed string if complete dataset is found otherwise and empty string will be returned.
     */
    @Override
    public String parseString(String line) {
        if(divisionPatter.matcher(line).matches())
        {
            movieName = "";
            movieYear = "";
            isSerie = false;
        }
        else if(moviePattern.matcher(line).matches())
        {
            /**
             * Group 1: MovieName
             * Group 2: MovieYear
             */
            Matcher m = moviePattern.matcher(line);
            if(m.find())
            {
                String name = m.group(1);
                if(name.contains("\"")){
                    isSerie = true;
                    name = name.replace("\"", "");
                }
                movieName = name.trim();
                movieYear = m.group(2).trim();
            }
        }
        else if(moneyPattern.matcher(line).matches())
        {
            /**
             * Group 1: Currency
             * Group 2: Money
             * Group 3: Type
             */
            Matcher m = moneyPattern.matcher(line);
            if(!movieName.isEmpty() && !movieYear.isEmpty())
            {
                if(m.find())
                    return String.format("%s\t%s\t%s\t%s\t%s\t%s", movieName, movieYear, isSerie, m.group(1).trim(), m.group(2).replace(",", "").trim(), m.group(3).trim());
            }
        }
        return "";
    }

    @Override
    public Pattern getPattern() {
        return Pattern.compile(".*");
    }
}
