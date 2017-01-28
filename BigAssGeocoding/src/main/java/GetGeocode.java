import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.*;
import java.math.*;
import java.util.concurrent.TimeUnit;

import org.json.*;


/**
 * Class that handles all geocoding
 * @author Pieter
 */
public class GetGeocode {
    //Database Connection
    private Connection conn;
    //Url to api
    private String apiUrl = "http://api.mapbox.com/geocoding/v5/mapbox.places/";
    //Api key
    private String accesToken = "?access_token=pk.eyJ1IjoicGlldGVydjI0IiwiYSI6ImNpeTl6emdieDAwMmIycXJ1eHIxOGllM28ifQ.1pBeyHG__iHlSgrR3hsidQ";

    /**
     * Instantiate the class with a database connection
     * @param conn Connection to database
     */
    public GetGeocode(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Geocoding code
     * @throws Exception
     */
    public void run() throws Exception
    {
        Statement stmt = conn.createStatement();
        Statement updateStatement = conn.createStatement();

        //Get all birthplaces from database that have not yet been geocoded.
        String sql = "SELECT * FROM \"bigmovieStaging\".birthplaces AS place WHERE place.coords IS NULL";
        //Run querry
        ResultSet rs = stmt.executeQuery(sql);

        int count = 0;
        //Geocoding loop for all birthplaces
        while (rs.next()){
            try{
                String birthplace = rs.getString("birthplace");
                System.out.println("Geocoding: " + birthplace.substring(0, birthplace.length()-1));
                String[] location = birthplace.substring(0, birthplace.length()-1).split(",");
                JSONObject json = readJSONfromURL(urlBuilder(location));

                JSONArray arr = json.getJSONArray("features");
                //If lengh is 0 then the api could not find coordinates for the location.
                if(arr.length() == 0){
                    System.out.println("No geocode found");
                    continue;
                }
                JSONObject firstEntry = arr.getJSONObject(0);
                JSONArray coords = firstEntry.getJSONArray("center");

                String coordString = coords.getDouble(1) + "," + coords.getDouble(0);
                //Update querry to place coords back into database
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

    /**
     * Get json response from api
     * @param apiUrl api url from urlBuilder
     * @return json response from api
     * @throws Exception
     */
    private JSONObject readJSONfromURL(String apiUrl) throws Exception{
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        /**
         * Api has cap op 600 requests/min
         * Api gives responsecode 429 if cap i reached
         * If cap is reached timeout for 1 min
         */
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

    /**
     * Takes all parts of the birthplace and adds them together in a url to the api
     * @param args parts of the brithplace ex: country, state, city
     * @return url with all parts of birthplace in get variable
     */
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
