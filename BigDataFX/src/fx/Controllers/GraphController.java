package fx.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import fx.DataSet.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import org.postgresql.Driver;

import javax.swing.text.html.StyleSheet;
import java.net.URL;
import java.sql.*;
import java.util.*;

/**
 * @author Pieter/Lex/Richard
 */
public class GraphController extends Controller implements Initializable {

    @FXML
    private JFXComboBox<Label> countrySelector;

    @FXML
    private BarChart barChart;

    @FXML
    private JFXButton graphButton;

    ArrayList<String> countries;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            InitCountries();
            for(String country: countries)
            {
                countrySelector.getItems().add(new Label(country));
            }
        }
        catch (Exception ex){

        }
    }

    /**
     * Create a list of countries for the combobox to display
     * @throws SQLException
     */
    private void InitCountries() throws SQLException{
        try {
            DatabaseConnection.StartConnection();
            Statement stmt = DatabaseConnection.GetConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT naam, fullname FROM bigmovie.land WHERE fullname IS NOT NULL ORDER BY fullname ASC");
            countries = new ArrayList<String>();
            while (rs.next())
            {
                countries.add(rs.getString("fullname"));
            }
        }
        catch (SQLException ex)
        {

        }
        finally {
            DatabaseConnection.StopConnection();
        }
    }

    /**
     * plot graph when button is clicked
     */
    @FXML
    private void graphButtonClicked()
    {
        try {
            String country = getCountryCode(countrySelector.getSelectionModel().getSelectedItem().getText());
            createPlot(country, countrySelector.getSelectionModel().getSelectedItem().getText());
        }
        catch (Exception ex)
        {

        }
    }

    /**
     * Generates graph and pushes it to the interface
     * @param country input from combobox
     * @param fullName full name of country for display purposes
     * @throws SQLException
     */
    private void createPlot(String country, String fullName) throws SQLException
    {
        try{
            DatabaseConnection.StartConnection();
            String sql = String.format("SELECT count(*) as counted, genretype FROM videogenre\n" +
                    "INNER JOIN videoland ON videogenre.videomateriaalnaam = videoland.videomateriaalnaam\n" +
                    "AND videogenre.videomateriaaljaar = videoland.videomateriaaljaar\n" +
                    "                        AND videoland.isserie = videogenre.isserie\n" +
                    "WHERE landnaam = '%s' AND videogenre.isserie = FALSE GROUP BY genretype\n" +
                    "ORDER BY counted DESC", country);
            Statement statement = DatabaseConnection.GetConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            XYChart.Series serie = new XYChart.Series();
            serie.setName(fullName);
            while (rs.next())
            {
                int aantal = Integer.parseInt(rs.getString("counted"));
                String genre = rs.getString("genretype");
                serie.getData().add(new XYChart.Data(genre, aantal));
            }
            barChart.getData().retainAll();
            barChart.getData().add(serie);
        }
        catch (SQLException ex)
        {
            ShowAlert("Something went wrong\n"+ ex.getStackTrace());
        }
        finally {
            DatabaseConnection.StopConnection();
        }
    }

    /**
     * Get countrycode from database for querry purposes
     * @param fullname Full name of country
     * @return return shortened version of country
     * @throws SQLException
     */
    private String getCountryCode(String fullname) throws SQLException
    {
        try {
            DatabaseConnection.StartConnection();
            Statement stmt = DatabaseConnection.GetConnection().createStatement();
            String sql = "SELECT land.naam FROM bigmovie.land WHERE fullname = '" + fullname + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            return rs.getString("naam");
        }
        catch (Exception ex){System.out.println("Something went wrong\n"+ex);}
        return "";
    }
}
