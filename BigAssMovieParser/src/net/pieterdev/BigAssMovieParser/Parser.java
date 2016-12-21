package net.pieterdev.BigAssMovieParser;

public enum Parser {
    MOVIES,
    ACTRESSSES;

    private MovieParser movieParser = new MovieParser();
    private ActressParser actressParser = new ActressParser();

    public Command getParser() {
        switch (this){
            case MOVIES:
                return movieParser;
            case ACTRESSSES:
                return actressParser;
            default:
                return null;
        }
    }

    abstract class Command
    {
        abstract void parseString(String input);
    }

    private class MovieParser extends Command
    {
        public void parseString(String input)
        {
            System.out.println("movie: " + input + " parsed");
        }
    }

    private class ActressParser extends Command
    {
        public void parseString(String input)
        {
            System.out.println("actress: " + input + " parsed");
        }
    }
}
