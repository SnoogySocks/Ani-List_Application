package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class AniList {
    
    private ArrayList<Double> totalUserGenreScore;      // Tracks the user's preferred genre
    private ArrayList<Integer> alreadyRecommended;      // Contains the malIDs of already recommended anime
    private HashSet<Anime> myAnimeList;                 // Stores the user's preferred anime
    
    public AniList () {
    
        totalUserGenreScore = new ArrayList<>();
        alreadyRecommended = new ArrayList<>();
        myAnimeList = new HashSet<>();
    
    }
    
    public ArrayList<Double> getTotalUserGenreScore () {
        return totalUserGenreScore;
    }
    
    public ArrayList<Integer> getAlreadyRecommended () {
        return alreadyRecommended;
    }
    
    public HashSet<Anime> getMyAnimeList () {
        return myAnimeList;
    }
    
    public void add (Anime anime) {
        // TODO
        myAnimeList.add(anime);
    }
    
}
