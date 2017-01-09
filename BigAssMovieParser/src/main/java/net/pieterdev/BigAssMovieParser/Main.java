package net.pieterdev.BigAssMovieParser;

import net.pieterdev.BigAssMovieParser.Loaders.BaseLoader;
import net.pieterdev.BigAssMovieParser.Loaders.MovieLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("What kind of file do you want to convert");
        System.out.println("(0) Movies and Series");
        System.out.println("(1) Actors");
        String typeInput = br.readLine();

        DataType type = GetDataType(typeInput);
        if(type != null)
        {
            System.out.println("Where is the file located?");
            String fileLocation = br.readLine();

            if(type.equals(DataType.MOVIES))
                new MovieLoader(fileLocation);
            else
                new BaseLoader(fileLocation, type);
        }
        else
            System.out.println("Incorrect Input, Exiting!");
    }

    private static DataType GetDataType(String type)
    {
        switch (type) {
            case "0": return DataType.MOVIES;
            case "1": return DataType.ACTORS;
        }
        return null;
    }
}
