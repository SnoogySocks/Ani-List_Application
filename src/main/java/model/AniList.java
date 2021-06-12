package model;

import jikanEnums.Genre;

import java.util.*;

public class AniList {
    
    private final HashMap<Genre, Double> totalUserGenreScore;    // Tracks the user's preferred genre
    private final HashSet<Integer> alreadyRecommended;           // Contains the malIDs of already recommended anime
    private final HashSet<Anime> myAnimeList;                    // Stores the user's preferred anime
    
    public AniList () {
    
        totalUserGenreScore = new HashMap<>();
        alreadyRecommended = new HashSet<>();
        myAnimeList = new HashSet<>();
    
    }
    
    public HashMap<Genre, Double> getTotalUserGenreScore () {
        return totalUserGenreScore;
    }
    
    public HashSet<Integer> getAlreadyRecommended () {
        return alreadyRecommended;
    }
    
    public HashSet<Anime> getMyAnimeList () {
        return myAnimeList;
    }
    
    public void add (Anime anime) {
        myAnimeList.add(anime);
        // modify frequency and stuff
    }
    
    public ArrayList<Anime> generateAscendingList () {
        
        ArrayList<Anime> arr = new ArrayList<>(myAnimeList);
        arr.sort(Comparator.comparingInt(Anime::getScore));
        return arr;
        
    }
    
    public ArrayList<Anime> generateDescendingList () {
    
        ArrayList<Anime> arr = new ArrayList<>(myAnimeList);
        arr.sort((Anime a1, Anime a2)->Integer.compare(a2.getScore(), a1.getScore()));
        return arr;
        
    }
    
}
