package jikanEnums;

public enum Genre {
    
    ACTION(1), ADVENTURE(2), CARS(3),
    COMEDY(4), DEMENTIA(5), DEMONS(6),
    MYSTERY(7), DRAMA(8), ECCHI(9),
    FANTASY(10), GAME(11), HENTAI(12),
    HISTORICAL(13), HORROR(14), KIDS(15),
    MAGIC(16), MARTIAL_ARTS(17), MECHA(18),
    MUSIC(19), PARODY(20), SAMURAI(21),
    ROMANCE(22), SCHOOL(23), SCI_FI(24),
    SHOUJO(25), SHOUJO_AI(28), SPACE(29),
    SPORTS(30), SUPER_POWER(31), VAMPIRE(32),
    YAOI(33), YURI(34), HAREM(35),
    SLICE_OF_LIFE(36), SUPERNATURAL(37), MILITARY(38),
    POLICE(39), PSYCHOLOGICAL(40), THRILLER(41),
    SEINEN(42), JOSEI(43);
    
    public String request;
    public int genreID;
    
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
    
    @Override
    public String toString () {
        return request;
    }
    
}
