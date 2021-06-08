package model;

import jikanEnums.Genre;
import org.json.JSONArray;
import org.json.JSONObject;

public class Anime implements Comparable<Anime> {

    // Important stuff
    private int malID;
    private String title;
    private String synopsis;
    private String imageURL;
    
    // Scoring
    private int totalVotes;
    private int[] scoringVotes;
    private double[] percentage;
    private double averageScore;
    
    // Miscellaneous
    private int numEpisodes;
    private String dateAired;
    private Genre[] genres;
    private String[] licensors, producers;
    
    // Statistics
    private int watching;
    private int completed;
    private int onHold;
    private int dropped;
    private int planToWatch;
    
    // For up and coming anime only
    boolean top;
    
    public Anime () {
        this(
                -1, "N/A", "N/A", null,
                -1.0,
                "N/A", -1,
                null, null, null,
                false
        );
    }
    
    public Anime (
            int malID, String title, String synopsis, String imageURL,
            double averageScore,
            String dateAired, int numEpisodes,
            JSONArray genres,
            JSONArray licensors, JSONArray producers,
            boolean top) {
        
        setAnime(
                malID, title, synopsis, imageURL,
                averageScore,
                dateAired, numEpisodes,
                genres,
                licensors, producers,
                top
        );
        
    }
    
    public void setAnime (
            int malID, String title, String synopsis, String imageURL,
            double averageScore,
            String dateAired, int numEpisodes,
            JSONArray genres,
            JSONArray licensors, JSONArray producers,
            boolean top) {
    
        this.malID = malID;
        this.title = title;
        this.synopsis = synopsis;
        this.imageURL = imageURL;
    
        this.averageScore = averageScore;
    
        this.dateAired = dateAired;
        this.numEpisodes = numEpisodes;
    
        this.top = top;
    
        if (genres==null || licensors==null || producers==null) {
            return;
        }
    
        this.genres = new Genre[genres.length()];
        for (int i = 0; i<genres.length(); ++i) {
            JSONObject genre = genres.getJSONObject(i);
            this.genres[i] = Genre.parseGenre(genre.getString("name"));
        }
    
        this.licensors = new String[licensors.length()];
        for (int i = 0; i<licensors.length(); ++i) {
            this.licensors[i] = licensors.getString(0);
        }
    
        this.producers = new String[producers.length()];
        for (int i = 0; i<producers.length(); ++i) {
            JSONObject producer = producers.getJSONObject(i);
            this.producers[i] = producer.getString("name");
        }
    
    }
    
    public void setStatistics (
            int watching, int completed, int onHold, int dropped, int planToWatch,
            int totalVotes, JSONObject scores) {
        
        this.watching = watching;
        this.completed = completed;
        this.onHold = onHold;
        this.dropped = dropped;
        this.planToWatch = planToWatch;
        this.totalVotes = totalVotes;
        
        this.scoringVotes = new int[scores.length()];
        this.percentage = new double[scores.length()];
        for (int i = 0; i<scores.length(); ++i) {
            
            JSONObject score = scores.getJSONObject(Integer.toString(i+1));
            this.scoringVotes[i] = score.getInt("votes");
            this.percentage[i] = score.getDouble("percentage");
            
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
    
    public int getNumEpisodes () {
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
    
    public int getWatching () {
        return watching;
    }
    
    public int getCompleted () {
        return completed;
    }
    
    public int getOnHold () {
        return onHold;
    }
    
    public int getDropped () {
        return dropped;
    }
    
    public int getPlanToWatch () {
        return planToWatch;
    }
    
    public int getTotalVotes () {
        return totalVotes;
    }
    
    public int[] getScoringVotes () {
        return scoringVotes;
    }
    
    public double[] getPercentage () {
        return percentage;
    }
    
    public double getAverageScore () {
        return averageScore;
    }
    
    public boolean isTop () {
        return top;
    }
    
    @Override
    public int compareTo (Anime anime) {
        return Double.compare(averageScore, anime.averageScore);
    }
    
}
