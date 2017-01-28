package net.pieterdev.BigAssMovieParser;

import net.pieterdev.BigAssMovieParser.Loaders.BaseLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Commandline interface for the application
 * @author rainslayerx
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //print options
        System.out.println("What kind of file do you want to convert");
        System.out.println("(0) Movies and Series");
        System.out.println("(1) Actors");
        System.out.println("(2) Actresses");
        System.out.println("(3) MPAA");
        System.out.println("(4) Countries");
        System.out.println("(5) Ratings");
        System.out.println("(6) Runtimes");
        System.out.println("(7) Writers");
        System.out.println("(8) Genres");
        System.out.println("(9) Biographies");
        System.out.println("(10) Directors");
        System.out.println("(11) Production");
        System.out.println("(12) Business");
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

            //Pass input, output and type to baseloader.
            new BaseLoader(fileLocation, targetLocation, type);
        }
        else
            System.out.println("Incorrect Input, Exiting!");
    }

    //Return correct datatype
    private static DataType GetDataType(String type)
    {
        switch (type) {
            case "0": return DataType.MOVIES;
            case "1": return DataType.ACTORS;
            case "2": return DataType.ACTRESSES;
            case "3": return DataType.MPAA;
            case "4": return DataType.COUNTRIES;
            case "5": return DataType.RATINGS;
            case "6": return DataType.RUNTIMES;
            case "7": return DataType.WRITERS;
            case "8": return DataType.GERNES;
            case "9": return DataType.BIOGRAPHY;
            case "10": return DataType.DIRECTORS;
            case "11": return DataType.PRODUCTION;
            case "12": return DataType.BUSINESS;
        }
        return null;
    }
}
