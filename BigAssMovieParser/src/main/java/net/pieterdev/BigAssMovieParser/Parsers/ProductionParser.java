package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for production-companies.list
 * @author Pieter
 */
public class ProductionParser implements ParserBase {
    Pattern productionPattern = getPattern();

    /**
     * parses movie and productioncompany and filters out the location of the productioncompany
     * @param line input line.
     * @return returns parsed string if line conforms with regex otherwise return empty string
     */
    @Override
    public String parseString(String line) {
        {
            /**
             * Group 1: Movie Name.
             * Group 2: Movie Year.
             * Group 3: Country
             */
            Matcher m = productionPattern.matcher(line);
            if(m.find())
            {
                if(m.group(2).contains("?"))
                    return "";
                String name = m.group(1);
                Boolean isSerie = false;
                if(name.contains("\""))
                {
                    isSerie = true;
                    name = name.replace("\"", "");
                }
                return String.format("%s\t%s\t%s\t%s", name, m.group(2), isSerie, m.group(3));
            }
        }
        return "";
    }

    @Override
    public Pattern getPattern() {
        return Pattern.compile("(.*?)(?: \\()([[0-9\\?]{4}]*)(?:\\/)?[\\w]*?\\).*\\[(\\w{2}?)\\].*");
    }
}
