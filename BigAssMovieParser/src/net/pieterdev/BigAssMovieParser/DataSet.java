package net.pieterdev.BigAssMovieParser;

public enum DataSet {
    MOVIES(0),
    ACTRESSSES(1);

    private int thisEnum;

    DataSet(int thisEnum)
    {
        this.thisEnum = thisEnum;
    }

    public Command getCommand() {
        if(thisEnum == 0)
            return new myCommand();
        return null;
    }

    abstract class Command
    {
        abstract void doStuff();
    }

    private class myCommand extends Command
    {
        public void doStuff()
        {
            System.out.println("Done stuff");
        }
    }
}
