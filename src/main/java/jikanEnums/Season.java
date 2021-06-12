package jikanEnums;

import java.util.HashMap;

public enum Season {

    WINTER, SPRING, SUMMER, FALL;
    
    private static final HashMap<String, Season> SEASON_PARSER = new HashMap<>() {{
        
        for (Season season: Season.values()) {
            put(season.toString().toLowerCase(), season);
        }
        
    }};
    
    String season;
    
    Season () {
        season = super.toString().charAt(0)+super.toString().toLowerCase().substring(1);
    }
    
    @Override
    public String toString () {
        return season;
    }
    
    public Season getNextSeason () {
        
        switch (this) {
            case WINTER: return SPRING;
            case SPRING: return SUMMER;
            case SUMMER: return FALL;
            case FALL: return WINTER;
            default: return null;
        }
        
    }
    
    public static Season parseSeason (String season) {
        return SEASON_PARSER.get(season);
    }
    
}
