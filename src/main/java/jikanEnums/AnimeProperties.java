package jikanEnums;

public enum AnimeProperties {
    
    TITLE,
    START_DATE, END_DATE,
    SCORE,
    TYPE,
    MEMBERS,
    ID,
    EPISODES,
    RATING;
    
    public String request;
    
    AnimeProperties () {
        request = super.toString().toLowerCase();
    }
    
    @Override
    public String toString () {
        return request;
    }
    
}
