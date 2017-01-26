/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barchart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import org.postgresql.Driver;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
 
public class Main extends Application {
    @Override public void start(Stage stage) {
        
        String land = "us";
        stage.setTitle("Bar Chart Sample");
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        final BarChart<String,Number> bc = 
            new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle(land + "-Genre Summary");
        xAxis.setLabel("Genre");  
        //xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Aantal");
        XYChart.Series serie = new XYChart.Series();
        serie.setName(land);
        
    
        try{
           Driver myDriver = new Driver();
            DriverManager.registerDriver(myDriver);
            String Url = "jdbc:postgresql://141.252.214.46:5432/postgres?currentSchema=bigmovie";
            String USER = "user";
            String Password = "123456789";
            Connection conn = DriverManager.getConnection(Url, USER, Password);
            GetGenreInfo genreInfo = new GetGenreInfo(conn);
            
            ResultSet rs = genreInfo.getInfo(land);
            while(rs.next())
            {
                int aantal = Integer.parseInt(rs.getString("counted"));
                String genre = rs.getString("genretype");
                serie.getData().add(new XYChart.Data(genre, aantal));
                
            }
            
            conn.close(); 
        }  catch (Exception ex)
        {
            System.out.println("Something went wrong. Continuing\n"+ex);
        }
        Scene scene  = new Scene(bc,800,600);
        
        bc.getData().addAll(serie);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);        
        
    }
}
