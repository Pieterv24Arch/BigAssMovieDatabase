package net.pieterdev.BigAssMovieParser;

import net.pieterdev.BigAssMovieParser.Parsers.ActorParser;
import net.pieterdev.BigAssMovieParser.Parsers.MPAAParser;
import net.pieterdev.BigAssMovieParser.Parsers.MovieParser;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;
import net.pieterdev.BigAssMovieParser.Parsers.WriterParser;

public enum DataType {
    MOVIES,
    ACTORS;
    WRITERS,
    MPAA;

    MovieParser movieParser = new MovieParser();
    WriterParser writerParser = new WriterParser();
    MPAAParser mpaaParser = new MPAAParser();
    ActorParser actorParser = new ActorParser();

    public ParserBase getParser()
    {
        switch (this)
        {
            case ACTORS:
                return actorParser;
            case MOVIES:
                return movieParser;
            case WRITERS:
                return writerParser;
            case MPAA:
                return mpaaParser;
            default:
                return null;
        }
    }
}
