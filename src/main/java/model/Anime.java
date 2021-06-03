package model;

import jikanEnums.Genre;

public class Anime {

    // Important stuff
    private final int malID;
    private final String title;
    private final String synopsis;
    private final String imageURL;
    
    // Miscellaneous
    private final String numEpisodes;
    private final String dateAired;
    private String[] licensors;
    private String[] producers;
    private final Genre[] genres;
    
    // Statistics
    private String watching;
    private String completed;
    private String onHold;
    private String dropped;
    private String planToWatch;
    
    // Scoring
    private int[] scoringVotes;
    private final double averageScore;
    
    public Anime (
            String malID, String title, String synopsis,
            String averageScore,
            String dateAired, String numEpisodes,
            String[] genres,
            String imageURL) {
    
        this.malID = Integer.parseInt(malID);
        this.title = title;
        this.synopsis = synopsis;
        this.dateAired = dateAired;
        this.numEpisodes = numEpisodes;
        
        this.genres = new Genre[genres.length];
        for (int i = 0; i<genres.length; ++i) {
            this.genres[i] = Genre.parseGenre(genres[i]);
        }
    
        this.imageURL = imageURL;
        this.averageScore = Double.parseDouble(averageScore);
    
    }
    
    public void setToAnimePanel (
            String[] licensors, String[] producers,
            String watching, String completed, String onHold, String dropped, String planToWatch,
            String[] scoringVotes) {
    
        this.licensors = licensors;
        this.producers = producers;
        this.watching = watching;
        this.completed = completed;
        this.onHold = onHold;
        this.dropped = dropped;
        this.planToWatch = planToWatch;
        
        this.scoringVotes = new int[scoringVotes.length];
        for (int i = 0; i<scoringVotes.length; ++i) {
            this.scoringVotes[i] = Integer.parseInt(scoringVotes[i]);
        }
    
    }
    
    public int getMalID () {
        return malID;
    }
    
    public String getTitle () {
        return title;
    }
    
    public String getSynopsis () {
        return synopsis;
    }
    
    public String getImageURL () {
        return imageURL;
    }
    
    public String getNumEpisodes () {
        return numEpisodes;
    }
    
    public String getDateAired () {
        return dateAired;
    }
    
    public String[] getLicensors () {
        return licensors;
    }
    
    public String[] getProducers () {
        return producers;
    }
    
    public Genre[] getGenres () {
        return genres;
    }
    
    public String getWatching () {
        return watching;
    }
    
    public String getCompleted () {
        return completed;
    }
    
    public String getOnHold () {
        return onHold;
    }
    
    public String getDropped () {
        return dropped;
    }
    
    public String getPlanToWatch () {
        return planToWatch;
    }
    
    public int[] getScoringVotes () {
        return scoringVotes;
    }
    
    public double getAverageScore () {
        return averageScore;
    }
    
}
