package net.pieterdev.BigAssMovieParser;

import net.pieterdev.BigAssMovieParser.Parsers.MovieParser;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;

public enum DataType {
    MOVIES;

    MovieParser movieParser = new MovieParser();

    public ParserBase getParser()
    {
        switch (this)
        {
            case MOVIES:
                return movieParser;
            default:
                return null;
        }
    }
}
