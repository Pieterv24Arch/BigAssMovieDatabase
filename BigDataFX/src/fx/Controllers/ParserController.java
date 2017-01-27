package fx.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import fx.FileManipulation.DataType;
import fx.FileManipulation.LoaderOnFinished;
import fx.FileManipulation.Loaders.BaseLoader;
import fx.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by rainslayerx on 1/26/17.
 */
public class ParserController extends Controller implements Initializable, LoaderOnFinished {
    @FXML
    private JFXTextField inputFileLocation;
    @FXML
    private JFXTextField outputFileLocation;
    @FXML
    private JFXComboBox<Label> fileTypeSelector;
    @FXML
    private JFXTextArea outputLog;
    @FXML
    private JFXButton parsingButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileTypeSelector.getItems().add(new Label("Movies and Series"));
        fileTypeSelector.getItems().add(new Label("Actors"));
        fileTypeSelector.getItems().add(new Label("Actresses"));
        fileTypeSelector.getItems().add(new Label("MPAA"));
        fileTypeSelector.getItems().add(new Label("Countries"));
        fileTypeSelector.getItems().add(new Label("Ratings"));
        fileTypeSelector.getItems().add(new Label("Runtimes"));
        fileTypeSelector.getItems().add(new Label("Writers"));
        fileTypeSelector.getItems().add(new Label("Genres"));
        fileTypeSelector.getItems().add(new Label("Biographies"));
        fileTypeSelector.getItems().add(new Label("Directors"));
        fileTypeSelector.getItems().add(new Label("Production-Companies"));
        fileTypeSelector.getItems().add(new Label("Business"));
    }

    @FXML
    void OpenFileChooserInputFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(Main.stageInstance);
        if (file != null) {
            inputFileLocation.setText(file.getPath());
        }
    }

    @FXML
    void OpenFileChooserOutputLocation(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showSaveDialog(Main.stageInstance);
        if (file != null) {
            outputFileLocation.setText(file.getPath());
        }
    }

    @FXML
    void StartParsing(ActionEvent event) {
        String fileLocation = inputFileLocation.getText();
        if(!new File(fileLocation).exists())
        {
            ShowAlert("Input file does not exist!");
            return;
        }
        outputLog.setText("Starting...");

        parsingButton.setText("Running...");
        parsingButton.setDisable(true);

        BaseLoader.textArea = outputLog;
        BaseLoader.onFinishedParsing = this;
        Runnable task = () -> new BaseLoader(fileLocation, outputFileLocation.getText(), GetDataType(fileTypeSelector.getSelectionModel().getSelectedIndex()));
        new Thread(task).start();
    }

    private static DataType GetDataType(int type)
    {
        switch (type) {
            case 0: return DataType.MOVIES;
            case 1: return DataType.ACTORS;
            case 2: return DataType.ACTRESSES;
            case 3: return DataType.MPAA;
            case 4: return DataType.COUNTRIES;
            case 5: return DataType.RATINGS;
            case 6: return DataType.RUNTIMES;
            case 7: return DataType.WRITERS;
            case 8: return DataType.GERNES;
            case 9: return DataType.BIOGRAPHY;
            case 10: return DataType.DIRECTORS;
            case 11: return DataType.PRODUCTION;
            case 12: return DataType.BUSINESS;
        }
        return null;
    }

    @Override
    public void OnLoaderFinished() {
        parsingButton.setText("Start Parsing");
        parsingButton.setDisable(false);
    }
}
