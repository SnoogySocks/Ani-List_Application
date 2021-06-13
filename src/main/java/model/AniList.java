package model;

import jikanEnums.Genre;

import java.util.*;

public class AniList {
    
    private final double[] totalUserGenreScore;           // Tracks the user's preferred genre
    private final HashSet<Integer> alreadyRecommended;    // Contains the malIDs of already recommended anime
    private final HashSet<Anime> myAnimeList;             // Stores the user's preferred anime
    
    public AniList () {
    
        Genre[] genres = Genre.values();
        totalUserGenreScore = new double[genres.length+1];
        for (Genre genre : genres) {
            totalUserGenreScore[genre.getGenreID()] = 0d;
        }
        
        alreadyRecommended = new HashSet<>();
        myAnimeList = new HashSet<>();
    
    }
    
    public double[] getTotalUserGenreScore () {
        return totalUserGenreScore;
    }
    
    public HashSet<Integer> getAlreadyRecommended () {
        return alreadyRecommended;
    }
    
    public void add (Anime anime) {
        
        myAnimeList.add(anime);
        alreadyRecommended.add(anime.getMalID());
        
        for (Genre genre: anime.getGenres()) {
            totalUserGenreScore[genre.getGenreID()] += 15-anime.getUserScore();
        }
        
    }
    
    public ArrayList<Anime> generateAscendingList () {
        
        ArrayList<Anime> arr = new ArrayList<>(myAnimeList);
        arr.sort(Comparator.comparingInt(Anime::getUserScore));
        return arr;
        
    }
    
    public ArrayList<Anime> generateDescendingList () {
    
        ArrayList<Anime> arr = new ArrayList<>(myAnimeList);
        arr.sort((Anime a1, Anime a2)->Integer.compare(a2.getUserScore(), a1.getUserScore()));
        return arr;
        
    }
    
}
