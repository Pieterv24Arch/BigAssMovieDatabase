package net.pieterdev.BigAssMovieParser;

import net.pieterdev.BigAssMovieParser.Parsers.MovieParser;
import net.pieterdev.BigAssMovieParser.Parsers.RuntimeParser;
import net.pieterdev.BigAssMovieParser.Parsers.GenreParser;
import net.pieterdev.BigAssMovieParser.Parsers.ParserBase;

public enum DataType {
    MOVIES,
    GERNES,
    RUNTIMES;

    MovieParser movieParser = new MovieParser();
    GenreParser genreParser = new GenreParser();
    RuntimeParser runtimeParser = new RuntimeParser();
    public ParserBase getParser()
    {
        switch (this)
        {
            case MOVIES:
                return movieParser;
            case GERNES:
                return genreParser;
            case RUNTIMES:
                return runtimeParser;
            default:
                return null;
        }
    }
}
