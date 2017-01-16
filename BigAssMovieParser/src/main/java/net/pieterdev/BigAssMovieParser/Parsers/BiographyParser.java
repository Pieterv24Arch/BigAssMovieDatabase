package net.pieterdev.BigAssMovieParser.Parsers;

import java.util.regex.Pattern;

public class BiographyParser implements ParserBase {\

    @Override
    public String parseString(String line) {
        return "";
    }

    @Override
    public Pattern getPattern() {
        return Pattern.compile(".*");
    }
}
