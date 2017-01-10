package net.pieterdev.BigAssMovieParser;

import net.pieterdev.BigAssMovieParser.Loaders.BaseLoader;
import net.pieterdev.BigAssMovieParser.Loaders.MovieLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("What kind of file do you want to convert");
        System.out.println("(0) Movies and Series");
        System.out.println("(1) Actors");
        System.out.println("(2) MPAA");
        System.out.println("(3) Countries");
        System.out.println("(4) Ratings");
        String typeInput = br.readLine();

        DataType type = GetDataType(typeInput);
        if(type != null)
        {
            System.out.println("Where is the file located?");
            String fileLocation = br.readLine();

            if(!new File(fileLocation).exists())
                return;

            System.out.println("Where should the output be saved?");
            String targetLocation = br.readLine();

            System.out.println("Busy...");

            if(type.equals(DataType.MOVIES))
                new MovieLoader(fileLocation, targetLocation);
            else
                new BaseLoader(fileLocation, targetLocation, type);
        }
        else
            System.out.println("Incorrect Input, Exiting!");
    }

    private static DataType GetDataType(String type)
    {
        switch (type) {
            case "0": return DataType.MOVIES;
            case "1": return DataType.ACTORS;
            case "2": return DataType.MPAA;
            case "3": return DataType.COUNTRIES;
            case "4": return DataType.RATINGS;
        }
        return null;
    }
}
