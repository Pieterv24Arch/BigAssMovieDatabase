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
    private String apiUrl = "https://api.mapbox.com/geocoding/v5/mapbox.places/";
    private String accesToken = "?access_token=pk.eyJ1IjoicGlldGVydjI0IiwiYSI6ImNpeTl6emdieDAwMmIycXJ1eHIxOGllM28ifQ.1pBeyHG__iHlSgrR3hsidQ";
    public GetGeocode(Connection conn)
    {
        this.conn = conn;
    }

    public void run() throws Exception
    {
        Statement stmt = conn.createStatement();

        String sql = "SELECT * FROM \"bigmovieStaging\".birthplaces AS place WHERE place.coords IS NULL";
        ResultSet rs = stmt.executeQuery(sql);

        int count = 0;
        while (rs.next()){
            try{
                String birthplace = rs.getString("birthplace");
                System.out.println(birthplace.substring(0, birthplace.length()-1));
                String[] location = birthplace.substring(0, birthplace.length()-1).split(",");
                JSONObject json = readJSONfromURL(urlBuilder(location));

                System.out.println(json.toString());

                JSONArray arr = json.getJSONArray("features");
                JSONObject firstEntry = arr.getJSONObject(0);
                JSONArray coords = firstEntry.getJSONArray("center");

                String coordString = coords.getDouble(1) + "," + coords.getDouble(0);
                String updateSQL = String.format("UPDATE \"bigmovieStaging\".birthplaces SET coords = \'%s\' WHERE birthplace = \'%s\'", coordString, birthplace);
                stmt.executeUpdate(updateSQL);

                count++;
            }
            catch (Exception ex)
            {
                System.out.println("Something went wrong. Continuing");
            }
        }
        System.out.println("There were: " + count + " entries");
    }

    private JSONObject readJSONfromURL(String apiUrl) throws Exception{
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        if(conn.getResponseCode() == 429 || conn.getResponseMessage() != "OK"){
            TimeUnit.MINUTES.sleep(1);
            readJSONfromURL(apiUrl);
            System.out.println("Timeout invoked");
        }
        InputStream is = conn.getInputStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        }
        finally {
            is.close();
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
            terms += args[i];
            if(i != args.length -1)
                terms+= "+";
        }
        return apiUrl + terms + ".json" + accesToken;
    }
}
