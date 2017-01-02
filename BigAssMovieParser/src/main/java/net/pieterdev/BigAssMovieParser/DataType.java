package net.pieterdev.BigAssMovieParser;

import net.pieterdev.BigAssMovieParser.Parsers.MovieParser;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;
import net.pieterdev.BigAssMovieParser.Parsers.WriterParser;

public enum DataType {
    MOVIES,
    WRITERS;

    MovieParser movieParser = new MovieParser();
    WriterParser writerParser = new WriterParser();

    public ParserBase getParser()
    {
        switch (this)
        {
            case MOVIES:
                return movieParser;
            case WRITERS:
                return writerParser;
            default:
                return null;
        }
    }
}
