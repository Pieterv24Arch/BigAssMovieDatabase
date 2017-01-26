import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.*;
import java.math.*;
import java.util.concurrent.TimeUnit;

import org.json.*;

public class GetGeocode {
    private Connection conn;
    private String apiUrl = "http://api.mapbox.com/geocoding/v5/mapbox.places/";
    private String accesToken = "?access_token=pk.eyJ1IjoicGlldGVydjI0IiwiYSI6ImNpeTl6emdieDAwMmIycXJ1eHIxOGllM28ifQ.1pBeyHG__iHlSgrR3hsidQ";
    public GetGeocode(Connection conn)
    {
        this.conn = conn;
    }

    public void run() throws Exception
    {
        Statement stmt = conn.createStatement();
        Statement updateStatement = conn.createStatement();

        String sql = "SELECT * FROM \"bigmovieStaging\".birthplaces AS place WHERE place.coords IS NULL";
        ResultSet rs = stmt.executeQuery(sql);

        int count = 0;
        while (rs.next()){
            try{
                String birthplace = rs.getString("birthplace");
                System.out.println("Geocoding: " + birthplace.substring(0, birthplace.length()-1));
                String[] location = birthplace.substring(0, birthplace.length()-1).split(",");
                JSONObject json = readJSONfromURL(urlBuilder(location));

                JSONArray arr = json.getJSONArray("features");
                if(arr.length() == 0){
                    System.out.println("No geocode found");
                    continue;
                }
                JSONObject firstEntry = arr.getJSONObject(0);
                JSONArray coords = firstEntry.getJSONArray("center");

                String coordString = coords.getDouble(1) + "," + coords.getDouble(0);
                String updateSQL = String.format("UPDATE \"bigmovieStaging\".birthplaces SET coords = \'%s\' WHERE birthplace = \'%s\'", coordString, birthplace.replace("'", "''"));
                updateStatement.executeUpdate(updateSQL);

                System.out.println("Geocoding success.");

                count++;
            }
            catch (Exception ex)
            {
                System.out.println("Something went wrong. Continuing\n"+ex);
                System.in.read();
            }
        }
        System.out.println("There were: " + count + " entries");
    }

    private JSONObject readJSONfromURL(String apiUrl) throws Exception{
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        if(connection.getResponseCode() == 429){
            System.out.println("Timeout invoked");
            TimeUnit.MINUTES.sleep(1);
            readJSONfromURL(apiUrl);
        }
        InputStream is = connection.getInputStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        }
        finally {
            is.close();
            connection.disconnect();
        }
    }
    private String readAll(Reader rd) throws IOException{
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1)
        {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private String urlBuilder(String[] args)
    {
        String terms = "";
        for(int i = 0; i < args.length; i++)
        {
            terms += args[i].trim().replace(" ", "%20").replace("/", "+");
            if(i != args.length -1)
                terms+= "+";
        }
        return apiUrl + terms + ".json" + accesToken;
    }
}
