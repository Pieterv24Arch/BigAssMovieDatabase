package net.pieterdev.BigAssMovieParser;

import net.pieterdev.BigAssMovieParser.Parsers.*;

/**
 * Factory for the parser types.
 * @author Pieter
 */
public enum DataType {
    MOVIES,
    GERNES,
    RUNTIMES,
    ACTORS,
    WRITERS,
    MPAA, 
    COUNTRIES,
    RATINGS,
    BIOGRAPHY;

    MovieParser movieParser = new MovieParser();
    WriterParser writerParser = new WriterParser();
    MPAAParser mpaaParser = new MPAAParser();
    ActorParser actorParser = new ActorParser();
    CountryParser countriesParser = new CountryParser();
    RatingParser ratingsParser = new RatingParser();
    GenreParser genreParser = new GenreParser();
    RuntimeParser runtimeParser = new RuntimeParser();
    BiographyParser biographyParser = new BiographyParser();

    /**
     * returns preinstantiated of the parser for this type
     * @return
     */
    public ParserBase getParser()
    {
        switch (this)
        {
            case ACTORS:
                return actorParser;
            case MOVIES:
                return movieParser;
            case GERNES:
                return genreParser;
            case RUNTIMES:
                return runtimeParser;
            case WRITERS:
                return writerParser;
            case MPAA:
                return mpaaParser;
            case COUNTRIES:
                return countriesParser;
            case RATINGS:
                return ratingsParser;
            case BIOGRAPHY:
                return biographyParser;
            default:
                return null;
        }
    }
}
