package controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import model.Anime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class JikanController {
    
    private static final String HOST = "https://jikan1.p.rapidapi.com/";
    private static final String X_RAPIDAPI_HOST = "jikan1.p.rapidapi.com";
    private String x_rapidapi_key;
    
    public JikanController () {
        
        /*
            TODO
                - Set up methods for JikenController to get specific data from the API
         */
        
        // Read the key from the text file
        try {
            Scanner input = new Scanner(new File("src/main/resources/APIKey"));
            x_rapidapi_key = input.next();
            input.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    
    }
    
    public JSONObject getRequest (String query) {
    
        HttpResponse<JsonNode> response;
        try {
            String encodedQuery = query.replace(" ", "%20");
        
            response = Unirest.get(HOST+encodedQuery)
                    .header("x-rapidapi-key", x_rapidapi_key)
                    .header("x-rapidapi-host", X_RAPIDAPI_HOST)
                    .asJson();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        
        return response.getBody().getArray().getJSONObject(0);
        
    }
    
    private ArrayList<Anime> getListOfAnime (String query, String listQuery, boolean isTop) {
    
        JSONObject response = getRequest(query);
        JSONArray animeList = response.getJSONArray(listQuery);
        ArrayList<Anime> arr = new ArrayList<>(animeList.length());
        
        // Initialize the anime
        for (int i = 0; i<animeList.length(); ++i) {
            JSONObject animeProperties = animeList.getJSONObject(i);
            Anime anime = new Anime();
            createAnime(anime, animeProperties, isTop);
            arr.add(anime);
        }
        return arr;
    
    }
    
    public ArrayList<Anime> getSeason (String year, String season) {
        return getListOfAnime("season/"+year+'/'+season, "anime", false);
    }
    
    public ArrayList<Anime> getUpAndComing () {
        return getListOfAnime("top/anime/1/upcoming", "top", true);
    }
    
    public ArrayList<Anime> getAiring () {
        return getListOfAnime("top/anime/1/airing", "top", true);
    }
    
    public void setAnimePanel (Anime anime) {
    
        // If the anime used a top request, get missing information
        if (anime.isTop()) {
            
            // Missing information can be found by searching for the anime by name
            JSONObject response = getRequest("search/anime?q="+anime.getTitle());
            JSONObject animeProperties = response.getJSONArray("results").getJSONObject(0);
            createAnime(anime, animeProperties, false);
            
        }
        getAnimeStatistics(anime);
    
    }
    
    public void getAnimeStatistics (Anime anime) {
        
        JSONObject response = getRequest("anime/"+anime.getMalID()+"/stats");
        
        try {
            anime.setStatistics(
                    response.getInt("watching"),
                    response.getInt("completed"),
                    response.getInt("on_hold"),
                    response.getInt("dropped"),
                    response.getInt("plan_to_watch"),
                    response.getJSONObject("scores")
            );
        } catch (JSONException e) {
            System.err.println(e.getMessage());
        }
    
    }
    
    private void createAnime (Anime anime, JSONObject animeProperties, boolean top) {
    
        // Check whether there is null
        String synopsis = !animeProperties.isNull("synopsis")
                ? animeProperties.getString("synopsis") : "N/A";
    
        double score = !animeProperties.isNull("score")
                ? animeProperties.getDouble("score") : -1;
    
        String dateAired;
        if (!top && !animeProperties.isNull("airing_start")) {
            dateAired = animeProperties.getString("airing_start").substring(0, 11);
        } else if (!top && !animeProperties.isNull("start_date")) {
            dateAired = animeProperties.getString("start_date").substring(0, 10);
        } else {
            dateAired = "N/A";
        }
        
        int numEpisodes = !animeProperties.isNull("episodes")
                ? animeProperties.getInt("episodes") : -1;
    
        JSONArray genres = !animeProperties.isNull("genres")
                ? animeProperties.getJSONArray("genres") : null;
    
        JSONArray licensors = !animeProperties.isNull("licensors")
                ? animeProperties.getJSONArray("licensors") : null;
    
        JSONArray producers = !animeProperties.isNull("producers")
                ? animeProperties.getJSONArray("producers") : null;
    
        anime.setAnime(
                animeProperties.getInt("mal_id"),
                animeProperties.getString("title"),
                synopsis,
                animeProperties.getString("image_url"),
                score,
                dateAired,
                numEpisodes,
                genres,
                licensors, producers,
                top
        );
        
    }

}
