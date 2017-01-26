package barchart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lexpunter
 */

public class GetGenreInfo {
    private Connection conn;
    public GetGenreInfo(Connection conn)
    {
        this.conn = conn;
    }
    public ResultSet getInfo(String land) throws Exception
    {
        Statement stmt = conn.createStatement();
        String sql = String.format("SELECT count(*) as counted, genretype FROM videogenre\n" +
"INNER JOIN videoland ON videogenre.videomateriaalnaam = videoland.videomateriaalnaam\n" +
"AND videogenre.videomateriaaljaar = videoland.videomateriaaljaar\n" +
"                        AND videoland.isserie = videogenre.isserie\n" +
"WHERE landnaam = '%s' AND videogenre.isserie = FALSE GROUP BY genretype\n" +
"ORDER BY counted DESC", land);
        ResultSet rs = stmt.executeQuery(sql);        
        return rs;
}
}

   