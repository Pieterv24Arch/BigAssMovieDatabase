package net.pieterdev.BigAssMovieParser;

import net.pieterdev.BigAssMovieParser.Parsers.ActorParser;
import net.pieterdev.BigAssMovieParser.Parsers.MovieParser;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;

public enum DataType {
    MOVIES,
    ACTORS;

    public ParserBase getParser()
    {
        switch (this)
        {
            case MOVIES:
                return new MovieParser();
            case ACTORS:
                return new ActorParser();
            default:
                return null;
        }
    }
}
