package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Scanner;

public class JikanController {

    private static final String CHARSET = "UTF-8";
    
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
    
    public void getSeason (String year, String season) {
    
        HttpResponse<JsonNode> response;
        try {
            String i = "season/"+year+'/'+season;
            String query;
            query = URLEncoder.encode(i, CHARSET);
        
            response = Unirest.get(HOST+query)
                    .header("x-rapidapi-key", x_rapidapi_key)
                    .header("x-rapidapi-host", X_RAPIDAPI_HOST)
                    .asJson();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }
    
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonObject = JsonParser.parseString(response.getBody().toString()).getAsJsonObject();
        String prettyJsonString = gson.toJson(jsonObject);
    
        System.out.println(prettyJsonString);
        
    }

}
