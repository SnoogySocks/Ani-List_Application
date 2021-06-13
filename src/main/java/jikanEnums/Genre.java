package jikanEnums;

import java.util.HashMap;

public enum Genre {
    
    ACTION(1), ADVENTURE(2), COMEDY(4),
    MYSTERY(7), DRAMA(8), FANTASY(10),
    HORROR(14), MAGIC(16), ROMANCE(22),
    SCHOOL(23), SCI_FI(24), SHOUJO(25),
    SHOUJO_AI(26), SHOUNEN(27), SHOUNEN_AI(28),
    SPORTS(30), SUPER_POWER(31), HAREM(35),
    SLICE_OF_LIFE(36), SUPERNATURAL(37), PSYCHOLOGICAL(40),
    THRILLER(41), SEINEN(42),
    
    // Unused genres
    CARS(3), DEMENTIA(5), DEMONS(6),
    ECCHI(9), GAME(11), HENTAI(12),
    HISTORICAL(13), KIDS(15), MARTIAL_ARTS(17),
    MECHA(18), MUSIC(19), PARODY(20),
    SAMURAI(21), SPACE(29), VAMPIRE(32),
    YAOI(33), YURI(34), MILITARY(38),
    POLICE(39), JOSEI(43);
    
    private static final HashMap<String, Genre> GENRE_PARSER = new HashMap<>() {{
        
        for (Genre genre : Genre.values()) {
            put(genre.toString(), genre);
        }
        remove(SLICE_OF_LIFE.toString());
        put("Slice of Life", SLICE_OF_LIFE);
        remove(SCI_FI.toString());
        put("Sci-Fi", SCI_FI);
        
    }};
    
    private final String request;
    private final int genreID;
    
    Genre (int genreID) {
        
        // Reformat the string
        char[] temp = super.toString().toCharArray();
        for (int i = 1; i<temp.length; ++i) {
            
            if (temp[i]=='_') {
                temp[i] = ' ';
            } else if (temp[i-1]==' ') {
                temp[i] = Character.toUpperCase(temp[i]);
            } else {
                temp[i] = Character.toLowerCase(temp[i]);
            }
            
        }
        request = String.valueOf(temp);
        this.genreID = genreID;
        
    }
    
    public int getGenreID () {
        return genreID;
    }
    
    public static Genre[] getFilterOptions () {
        
        Genre[] filterOptions = new Genre[23];
        System.arraycopy(Genre.values(), 0, filterOptions, 0, filterOptions.length);
        return filterOptions;
        
    }
    
    @Override
    public String toString () {
        return request;
    }
    
    public static Genre parseGenre (String genre) {
        return GENRE_PARSER.get(genre);
    }
    
}
