package net.pieterdev.BigAssMovieParser;

import net.pieterdev.BigAssMovieParser.Parsers.MPAAParser;
import net.pieterdev.BigAssMovieParser.Parsers.MovieParser;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;
import net.pieterdev.BigAssMovieParser.Parsers.WriterParser;

public enum DataType {
    MOVIES,
    WRITERS,
    MPAA;

    MovieParser movieParser = new MovieParser();
    WriterParser writerParser = new WriterParser();
    MPAAParser mpaaParser = new MPAAParser();

    public ParserBase getParser()
    {
        switch (this)
        {
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
