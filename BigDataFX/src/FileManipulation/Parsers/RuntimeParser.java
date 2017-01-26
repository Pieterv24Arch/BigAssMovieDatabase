package FileManipulation.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuntimeParser implements ParserBase{
    public String parseString(String line)
    {
        Pattern genrePattern = getPattern();
       
        Matcher m = genrePattern.matcher(line);
        if(m.find())
        {
            if(line.startsWith("\""))
            {
                /**
                 * return if line contains a serie
                 */
                String serieName = m.group(1).replace("\"", "").trim();
                return String.format("%s\t%s\t%s\ttrue", serieName, m.group(2), m.group(6));
            }   /**
                 * return if line contains a movie
                 */
            else return String.format("%s\t%s\t%s\tfalse", m.group(1).trim(), m.group(2), m.group(6));
            }
       
        return "";
    }

    public Pattern getPattern()
    {
        return Pattern.compile("(.*)\\(([\\d{4}]*)\\)\\s+(?:\\(.+\\)|\\{(.*?)(?:\\(#(\\d*?)\\.(\\d*?)\\))?\\}|)\\s+(?:\\w+\\:|)(\\d+)");
    }
}



//      (.*)\(([\d{4}]*)\)\s+(?:\(.+\)|\{(.*?)(?:\(#(\d*?)\.(\d*?)\))?\}|)\s+(?:\w+\:|)(\w+)