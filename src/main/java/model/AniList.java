package model;

import java.util.ArrayList;
import java.util.Collections;

public class AniList {
    
    private ArrayList<Double> totalUserGenreScore;      // Tracks the user's preferred genre
    private ArrayList<Integer> alreadyRecommended;      // Contains the malIDs of already recommended anime
    private ArrayList<Anime> myAnimeList;               // Stores the user's preferred anime
    
    public AniList () {
    
        totalUserGenreScore = new ArrayList<>();
        alreadyRecommended = new ArrayList<>();
        myAnimeList = new ArrayList<>();
    
    }
    
    public ArrayList<Double> getTotalUserGenreScore () {
        return totalUserGenreScore;
    }
    
    public ArrayList<Integer> getAlreadyRecommended () {
        return alreadyRecommended;
    }
    
    public ArrayList<Anime> getMyAnimeList () {
        return myAnimeList;
    }
    
    public void sortAscending () {
        Collections.sort(myAnimeList);
    }
    
    public void sortDescending () {
        myAnimeList.sort(Collections.reverseOrder());
    }
    
}
