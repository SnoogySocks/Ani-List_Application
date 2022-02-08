package controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import jikanEnums.Genre;
import jikanEnums.Schedule;
import model.Anime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Handles the interactions between the program and the api such as:
 * - Extracting necessary information from JSON
 * - Providing Arraylists of anime from specific parameters
 * - Providing statistics about specific anime
 */
public class JikanController {
    
    private static final String HOST = "https://jikan1.p.rapidapi.com/";
    private static final String X_RAPIDAPI_HOST = "jikan1.p.rapidapi.com";
    private static String x_rapidapi_key;
    
    static {
        
        // Read the x_rapid_key from the text file for safety
        // (the file would usually be in a separate folder from the project)
        try {
            Scanner input = new Scanner(new File("C:/Users/felix/OneDrive/Documents/confidential/APIKey"));
            x_rapidapi_key = input.next();
            input.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
    /**
     * @param query = query for the api
     * @return the JSON list of anime according to the query
     */
    private static JSONObject getRequest (String query) {
    
        HttpResponse<JsonNode> response;
        try {
            
            // Encode the query by replacing spaces with their readable url character
            String encodedQuery = query.replace(" ", "%20");
        
            // Get the response from Unirest
            response = Unirest.get(HOST+encodedQuery)
                    .header("x-rapidapi-key", x_rapidapi_key)
                    .header("x-rapidapi-host", X_RAPIDAPI_HOST)
                    .asJson();
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        
        // Return the JSON data from the query
        return response.getBody().getArray().getJSONObject(0);
        
    }
    
    /**
     * @param query = the api query
     * @param listQuery = the directory for useful data from the json list
     * @param isTop = identifies whether the query is a top request
     * @return A full ArrayList of Anime
     */
    private static ArrayList<Anime> getListOfAnime (String query, String listQuery, boolean isTop) {
    
        // Request from rapid api the anime list
        JSONObject response = getRequest(query);
        JSONArray animeList = response.getJSONArray(listQuery);
        ArrayList<Anime> arr = new ArrayList<>(animeList.length());
        
        // Initialize the anime
        for (int i = 0; i<animeList.length(); ++i) {
            
            JSONObject jsonAnime = animeList.getJSONObject(i);
            Anime anime = new Anime();
            
            // Transfer the anime from the json to the object
            setAnime(anime, jsonAnime, isTop);
    
            // Find out whether the anime is 18+
            boolean isEighteenPlus = false;
            for (Genre genre: anime.getGenres()) {
                if (genre==Genre.HENTAI || genre==Genre.YURI || genre==Genre.YAOI) {
                    isEighteenPlus = true;
                    break;
                }
            }
            
            // Add the anime to the list if the anime is not 18+
            if (!isEighteenPlus) {
                arr.add(anime);
            }
            
        }
        return arr;
    
    }
    
    /**
     * @param year = the year the anime was released
     * @param season = the season the anime was released
     * @return All the anime that match the parameters
     */
    public static ArrayList<Anime> getSeason (String year, String season) {
        return getListOfAnime("season/"+year+'/'+season, "anime", false);
    }
    
    /**
     * @return The anime that are currently trending
     */
    public static ArrayList<Anime> getTrending () {
        return getListOfAnime("top/anime/1/airing", "top", true);
    }
    
    /**
     * @return the anime that are coming
     */
    public static ArrayList<Anime> getUpAndComing () {
        return getListOfAnime("top/anime/1/upcoming", "top", true);
    }
    
    /**
     * @return the naime that have just updated today
     */
    public static ArrayList<Anime> getLatestUpdated () {
    
        Calendar c = Calendar.getInstance();
        String dayOfWeek = Schedule.values()[c.get(Calendar.DAY_OF_WEEK)-1].toString();
        return getListOfAnime("schedule/"+dayOfWeek, dayOfWeek, false);
        
    }
    
    /**
     * Prepares the anime for the anime panel
     * @param anime = the anime to prepare
     */
    public static void setAnimePanel (Anime anime) {
    
        // If the anime used a top request, get missing information
        if (anime.isTop()) {
            
            // Missing information can be found by searching for the anime by name
            JSONObject response = getRequest("search/anime?q= "+anime.getTitle());
            JSONObject jsonAnime = response.getJSONArray("results").getJSONObject(0);
            setAnime(anime, jsonAnime, false);
            
        }
        // Get the statistics of the anime
        getAnimeStatistics(anime);
    
    }
    
    /**
     * Retrieve the statistics of the anime
     * @param anime = the anime to retrieve statistics from
     */
    public static void getAnimeStatistics (Anime anime) {
        
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
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    
    }
    
    /**
     * Set the anime from JSON. Also provides default values if the JSON
     * does not have the specified property
     * @param anime = Place to store the anime
     * @param jsonAnime = The anime as a JSON
     * @param isTop = Indicates whether the anime was retrieved from a top request
     */
    private static void setAnime (Anime anime, JSONObject jsonAnime, boolean isTop) {
    
        String synopsis = !jsonAnime.isNull("synopsis")
                ? jsonAnime.getString("synopsis") : "N/A";
    
        double score = !jsonAnime.isNull("score")
                ? jsonAnime.getDouble("score") : 0;
    
        String dateAired;
        if (!isTop && !jsonAnime.isNull("airing_start")) {
            dateAired = jsonAnime.getString("airing_start").substring(0, 10);
        } else if (!isTop && !jsonAnime.isNull("start_date")) {
            dateAired = jsonAnime.getString("start_date").substring(0, 10);
        } else {
            dateAired = "N/A";
        }
        
        int numEpisodes = !jsonAnime.isNull("episodes")
                ? jsonAnime.getInt("episodes") : 0;
    
        JSONArray genres = !jsonAnime.isNull("genres")
                ? jsonAnime.getJSONArray("genres") : null;
    
        JSONArray licensors = !jsonAnime.isNull("licensors")
                ? jsonAnime.getJSONArray("licensors") : null;
    
        JSONArray producers = !jsonAnime.isNull("producers")
                ? jsonAnime.getJSONArray("producers") : null;
    
        anime.setAnime(
                jsonAnime.getInt("mal_id"),
                jsonAnime.getString("title"),
                synopsis,
                jsonAnime.getString("image_url"),
                score,
                dateAired,
                numEpisodes,
                genres,
                licensors, producers,
                isTop
        );
        
    }

}
