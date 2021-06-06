package jikanEnums;

import java.util.HashMap;

public enum Season {

    WINTER, SPRING, SUMMER, AUTUMN;
    
    private static final HashMap<String, Season> SEASON_PARSER = new HashMap<>() {{
        
        for (Season season: Season.values()) {
            put(season.toString(), season);
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
    
    public static Season parseSeason (String season) {
        return SEASON_PARSER.get(season);
    }
    
}
