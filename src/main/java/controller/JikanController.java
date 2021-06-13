package controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import jikanEnums.Genre;
import jikanEnums.Schedule;
import model.Anime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class JikanController {
    
    private static final String HOST = "https://jikan1.p.rapidapi.com/";
    private static final String X_RAPIDAPI_HOST = "jikan1.p.rapidapi.com";
    private static String x_rapidapi_key;
    
    public static void init () {
        
        // Read the key from the text file
        try {
            Scanner input = new Scanner(new File("src/main/resources/APIKey"));
            x_rapidapi_key = input.next();
            input.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
    private static JSONObject getRequest (String query) {
    
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
    
    private static ArrayList<Anime> getListOfAnime (String query, String listQuery, boolean isTop) {
    
        JSONObject response = getRequest(query);
        JSONArray animeList = response.getJSONArray(listQuery);
        ArrayList<Anime> arr = new ArrayList<>(animeList.length());
        
        // Initialize the anime
        for (int i = 0; i<animeList.length(); ++i) {
            
            JSONObject animeProperties = animeList.getJSONObject(i);
            Anime anime = new Anime();
            
            createAnime(anime, animeProperties, isTop);
    
            // If the anime is for 18+ then don't add it to the list
            boolean doNotAdd = false;
            for (Genre genre: anime.getGenres()) {
                if (genre==Genre.HENTAI || genre==Genre.YURI || genre==Genre.YAOI) {
                    doNotAdd = true;
                    break;
                }
            }
            
            if (!doNotAdd) {
                arr.add(anime);
            }
            
        }
        return arr;
    
    }
    
    public static ArrayList<Anime> getSeason (String year, String season) {
        return getListOfAnime("season/"+year+'/'+season, "anime", false);
    }
    
    public static ArrayList<Anime> getTrending () {
        return getListOfAnime("top/anime/1/airing", "top", true);
    }
    
    public static ArrayList<Anime> getUpAndComing () {
        return getListOfAnime("top/anime/1/upcoming", "top", true);
    }
    
    public static ArrayList<Anime> getLatestUpdated () {
    
        Calendar c = Calendar.getInstance();
        String dayOfWeek = Schedule.values()[c.get(Calendar.DAY_OF_WEEK)-1].toString();
        return getListOfAnime("schedule/"+dayOfWeek, dayOfWeek, false);
        
    }
    
    public static void setAnimePanel (Anime anime) {
    
        // If the anime used a top request, get missing information
        if (anime.isTop()) {
            
            // Missing information can be found by searching for the anime by name
            JSONObject response = getRequest("search/anime?q= "+anime.getTitle());
            JSONObject animeProperties = response.getJSONArray("results").getJSONObject(0);
            createAnime(anime, animeProperties, false);
            
        }
        getAnimeStatistics(anime);
    
    }
    
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
    
    private static void createAnime (Anime anime, JSONObject animeProperties, boolean top) {
    
        // Check whether there is null
        String synopsis = !animeProperties.isNull("synopsis")
                ? animeProperties.getString("synopsis") : "N/A";
    
        double score = !animeProperties.isNull("score")
                ? animeProperties.getDouble("score") : 0;
    
        String dateAired;
        if (!top && !animeProperties.isNull("airing_start")) {
            dateAired = animeProperties.getString("airing_start").substring(0, 10);
        } else if (!top && !animeProperties.isNull("start_date")) {
            dateAired = animeProperties.getString("start_date").substring(0, 10);
        } else {
            dateAired = "N/A";
        }
        
        int numEpisodes = !animeProperties.isNull("episodes")
                ? animeProperties.getInt("episodes") : 0;
    
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
