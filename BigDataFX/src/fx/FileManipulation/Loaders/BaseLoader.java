package fx.FileManipulation.Loaders;

import com.jfoenix.controls.JFXTextArea;
import fx.FileManipulation.DataType;
import fx.FileManipulation.LoaderOnFinished;
import fx.FileManipulation.Parsers.ParserBase;
import javafx.application.Platform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Base class for the loaders.
 * The purpose of a loader is to handle the input of data into the parser
 * and hence into the output file.
 * @author Pieter
 */
public class BaseLoader {
    /**
     * Constructor for the base loader.
     * Requires source/target and type of file(what parser should be used)
     * @param sourceFile file to be parsed
     * @param targetFile file with parsed data
     * @param dataType type of sourcefile(what parser should be used)
     */
    public BaseLoader(String sourceFile, String targetFile, DataType dataType)
    {
        Date beginTime = new Date();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm");
        System.out.println("Current time is: " + df.format(beginTime));
        try(Stream<String> lines = Files.lines(new File(sourceFile).toPath(), StandardCharsets.ISO_8859_1))
        {
            StreamOperations(lines, dataType, targetFile);
            lines.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        Date stopTime = new Date();
        System.out.println("Current time is: " + df.format(stopTime));
        System.out.println(((stopTime.getTime() - beginTime.getTime()) / 1000) + " Seconds have elapsed");
    }


    /**
     * Stream to pass lines to the parser and to the output file.
     * @param lines stream of lines from the input file
     * @param dataType type of parser to be used
     * @param targetLocation target file for the parsed data to go
     * @throws FileNotFoundException
     */
    public void StreamOperations(Stream<String> lines, DataType dataType, String targetLocation) throws Exception {
        ParserBase parser = dataType.getParser();

        File targetFile = new File(targetLocation);
        PrintWriter writer = new PrintWriter(targetFile);

        assert parser != null;
        Pattern pattern = parser.getPattern();
        lines
                .filter(s -> pattern.matcher(s).matches())
                .map(parser::parseString)
                .filter(s -> !s.isEmpty())
                .forEach(c -> {writer.println(c); UpdateUI(c);});
        writer.close();

        Platform.runLater(() -> {
            textArea.setText("Finished!\n" + updateCount + " entries have been written to " + targetLocation);
            if(onFinishedParsing != null)
                onFinishedParsing.OnLoaderFinished();
        });
    }

    public String tempBuffer = "\n";
    private int updateCount = 0;
    public static JFXTextArea textArea;
    public static LoaderOnFinished onFinishedParsing;
    void UpdateUI(String text) {
        tempBuffer += text + "\n";
        updateCount++;

        if(updateCount % 25 == 0) {
            Platform.runLater(() -> textArea.setText(tempBuffer));
            tempBuffer = "";
        }
    }
}
