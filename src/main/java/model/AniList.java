package model;

import jikanEnums.Genre;

import java.io.Serializable;
import java.util.*;

/**
 * Records
 * - User's genre preferences
 * - Anime recommendation history
 * - The user's anime
 */
public class AniList implements Serializable {
    
    private static final long serialVersionUID = -1271192980665485395L;
    
    private final double[] totalUserGenreScore;           // Tracks the user's preferred genre
    private final HashSet<Integer> alreadyRecommended;    // Contains the malIDs of already recommended anime
    private final HashSet<Anime> myAnimeList;             // Stores the user's preferred anime
    
    public AniList () {
        
        totalUserGenreScore = new double[Genre.values().length+1];
        alreadyRecommended = new HashSet<>();
        myAnimeList = new HashSet<>();
    
    }
    
    public void setAniList (AniList rhs) {
        
        if (rhs==null) {
            return;
        }
        
        System.arraycopy(rhs.totalUserGenreScore, 0,
                totalUserGenreScore, 0, totalUserGenreScore.length);
        alreadyRecommended.addAll(rhs.alreadyRecommended);
        myAnimeList.addAll(rhs.myAnimeList);
        
    }
    
    // Getters
    public double[] getTotalUserGenreScore () {
        return totalUserGenreScore;
    }
    
    public HashSet<Integer> getAlreadyRecommended () {
        return alreadyRecommended;
    }
    
    public HashSet<Anime> getMyAnimeList () {
        return myAnimeList;
    }
    
    /**
     * Add the anime to the user's Ani-List
     * @param anime = anime to add
     */
    public void add (Anime anime) {
        
        // Add it to the list
        myAnimeList.add(anime);
        
        // Record the anime to not recommend in the algorithm
        alreadyRecommended.add(anime.getMalID());
        
        // Score the genre of the anime
        for (Genre genre: anime.getGenres()) {
            totalUserGenreScore[genre.getGenreID()] += 15-anime.getUserScore();
        }
        
    }
    
    /**
     * @return a sorted user's Ani-List in ascending order based on the user's score
     */
    public ArrayList<Anime> generateAscendingList () {
        
        ArrayList<Anime> arr = new ArrayList<>(myAnimeList);
        arr.sort(Comparator.comparingInt(Anime::getUserScore));
        return arr;
        
    }
    
    /**
     * @return a sorted user's Ani-List in descending order based on the user's score
     */
    public ArrayList<Anime> generateDescendingList () {
    
        ArrayList<Anime> arr = new ArrayList<>(myAnimeList);
        arr.sort((Anime a1, Anime a2)->Integer.compare(a2.getUserScore(), a1.getUserScore()));
        return arr;
        
    }
    
}
