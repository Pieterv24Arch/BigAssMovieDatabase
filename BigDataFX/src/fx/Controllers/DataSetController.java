package fx.Controllers;

import com.jfoenix.controls.*;
import fx.DataSet.DatabaseConnection;
import fx.TableGenerator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by rainslayerx on 1/26/17.
 */
public class DataSetController extends Controller implements Initializable {
    @FXML
    JFXComboBox<Label> comboBox;
    @FXML
    JFXTextArea textView;

    private ArrayList<QuestionQuery> questions;

    @FXML
    private void RequestData() throws SQLException {
        try {
            textView.setText("Connecting to database...\n");

            DatabaseConnection.StartConnection();
            Statement statement = DatabaseConnection.GetConnection().createStatement();
            ResultSet rs = statement.executeQuery(questions.get(comboBox.getSelectionModel().getSelectedIndex()).query);

            //Prepare View
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            ArrayList<String> columnNames = new ArrayList<>();

            for(int i = 1; i <= columnCount; i++)
                columnNames.add(rsmd.getColumnName(i));

            List<List<String>> data = new ArrayList<>();
            //PopulateData
            while (rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                for (int i = 0; i < columnCount; ++i) {
                    row.add(rs.getString(i+1)); // Or even rs.getObject()
                }
                data.add(row);
            }

            textView.setText(new TableGenerator().generateTable(columnNames, data).trim());
        }
        catch (Exception ex) {
            textView.appendText("Error!\n");
            textView.appendText(ex.getMessage()+"\n");
            ex.printStackTrace();
        }
        finally {
            DatabaseConnection.StopConnection();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InitQuestions();

        for (QuestionQuery question: questions) {
            comboBox.getItems().add(new Label(question.question));
        }
    }

    private void InitQuestions() {
        questions = new ArrayList<>();
        questions.add(new QuestionQuery("Welke acteur (m/v) heeft de meeste dubbelrollen",
                "SELECT count(*) as counted, acteurnaam FROM rol\n" +
                        "WHERE rolnaam LIKE '%/%' GROUP BY acteurnaam ORDER BY counted DESC\n" +
                        "LIMIT 10;"));
        questions.add(new QuestionQuery("In welke films speelde Joop Braakhekke",
                "SELECT videomateriaalnaam FROM rol\n" +
                        "WHERE acteurnaam = 'Braakhekke, Joop' \n" +
                        "AND rol.isserie = FALSE;"));
        questions.add(new QuestionQuery("Welke regisseur heeft de meeste films met Jim Carrey in de hoofdrol geregisseerd",
                "SELECT count(*) AS counted, regiseurnaam FROM videoregiseur\n" +
                        "INNER JOIN rol\n" +
                        "ON videoregiseur. videomateriaalnaam = rol. videomateriaalnaam\n" +
                        " AND videoregiseur.videomateriaaljaar = rol.videomateriaaljaar\n" +
                        " AND videoregiseur.isserie = rol.isserie\n" +
                        "WHERE rol.acteurnaam = 'Carrey, Jim'\n" +
                        "    AND rol.priroriteit = 1\n" +
                        "GROUP BY regiseurnaam\n" +
                        "ORDER BY counted DESC\n" +
                        "LIMIT 10;"));
        questions.add(new QuestionQuery("In welk jaar tussen 1990 en nu zijn de meeste films met het woord ‘beer’ in de titel geproduceerd",
                "SELECT counted, jaar from (\n" +
                        " SELECT count(*) AS counted, jaar FROM videomateriaal\n" +
                        "WHERE isserie = FALSE\n" +
                        "     and naam LIKE '%beer%'\n" +
                        "GROUP BY jaar ORDER BY counted DESC\n" +
                        ") AS count LIMIT 10"));
        questions.add(new QuestionQuery("^... En wat is het meest voorkomende genre",
                "SELECT counted, genretype from (\n" +
                        " SELECT count(*) AS counted, genretype FROM videogenre\n" +
                        "WHERE isserie = FALSE\n" +
                        "     and videomateriaalnaam LIKE '%beer%'\n" +
                        "GROUP BY genretype ORDER BY counted DESC\n" +
                        ") AS count LIMIT 10"));
        questions.add(new QuestionQuery("Wat is de kortste film met een waardering van 8.5 of hoger",
                "SELECT * FROM videomateriaal WHERE duur = (\n" +
                        " SELECT min(video.duur)\n" +
                        " FROM (SELECT *\n" +
                        "       FROM videomateriaal\n" +
                        "       WHERE beoordeling > 8.5) AS video\n" +
                        " WHERE video.duur NOTNULL AND video.isserie = FALSE AND\n" +
                        "       video.duur > 29\n" +
                        ") AND videomateriaal.isserie = FALSE AND beoordeling > 8.5;"));
        questions.add(new QuestionQuery("Hoeveel films heeft Woody Allen gemaakt",
                "SELECT count(*) FROM videoregiseur\n" +
                        "WHERE regiseurnaam = ‘Allen, Woody’;"));
        questions.add(new QuestionQuery("^... In hoeveel daarvan speelde hij zelf mee",
                "SELECT count(*) FROM rol\n" +
                        "INNER JOIN videoregiseur\n" +
                        "   ON videoregiseur. videomateriaalnaam = rol. videomateriaalnaam\n" +
                        " AND videoregiseur.videomateriaaljaar = rol.videomateriaaljaar\n" +
                        " AND videoregiseur.isserie = rol.isserie\n" +
                        "WHERE regiseurnaam = 'Allen, Woody'\n" +
                        "     AND acteurnaam = 'Allen, Woody'\n" +
                        "     AND rol.isserie = FALSE;"));
        questions.add(new QuestionQuery("^... en zijn er ook films waarin hij wel speelde maar niet regisseerde",
                "SELECT * FROM rol\n" +
                        "INNER JOIN videoregiseur\n" +
                        "   ON videoregiseur. videomateriaalnaam = rol. videomateriaalnaam\n" +
                        " AND videoregiseur.videomateriaaljaar = rol.videomateriaaljaar\n" +
                        " AND videoregiseur.isserie = rol.isserie\n" +
                        "WHERE regiseurnaam != 'Allen, Woody'\n" +
                        "     AND acteurnaam = 'Allen, Woody'\n" +
                        "     AND rol.isserie = FALSE;"));
    }

    private class QuestionQuery {
        public String question = "";
        public String query = "";

        public QuestionQuery(String question, String query) {
            this.question = question;
            this.query = query;
        }
    }
}