package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rainslayerx on 12/22/16.
 */
public class ActorParser implements ParserBase {
    public ActorParser(boolean isMale)
    {
        this.isMale = isMale;
    }

    Pattern namePattern = Pattern.compile("(.*?)\\t+(.*?)\\s\\(([\\d{4}]*).*\\[(.*)\\](?>.*<(.*)>)?");
    static String actor = "";
    static String previousInput = "";
    boolean isMale = false;

    /**
     * If empty line is detected the writer in the class is reset.
     * If string matches the name pattern that means a writer name is detected and this writer is in turn saved in the writers array.
     * If string matches the creation pattern the writer is derived from the writers array.
     * @param line incomming information
     * @return parsed string or empty string if string does not conform to patterns
     */
    public String parseString(String line)
    {
        if(namePattern.matcher(line).matches())
        {
            /**
             * G1: Name
             * G2:
             */
            Matcher m = namePattern.matcher(line);
            if(m.find())
            {
                //When actor group is empty use previous writer
                if(!m.group(1).trim().isEmpty())
                    actor = m.group(1);

                for (int i = 1; i <= m.groupCount(); i++)
                    if(m.group(i) != null)
                        m.group(i).trim();

                boolean isSerie = actor.contains("\"");
                String next = String.format("%s\t%s\t%s\t%s\t%s\t%s", actor, m.group(2).replace("\"", ""), m.group(3), m.group(4), m.group(5), isMale ? "m" : "f", isSerie);
                if(!next.equals(previousInput)) {
                    previousInput = next;
                    return next;
                }
                else
                    return "";
            }
            return "";
        }
        return "";
    }

    public Pattern getPattern()
    {
        return namePattern;
    }
}
