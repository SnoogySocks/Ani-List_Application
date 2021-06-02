package jikanEnums;

public enum SearchParameters {
    
    Q, PAGE, TYPE,
    STATUS, RATED, GENRE,
    SCORE,
    START_DATE, END_DATE,
    GENRE_EXCLUDE,
    LIMIT,
    ORDER_BY, SORT,
    PRODUCER,
    MAGAZINE,
    LETTER;
    
    public String request;
    
    SearchParameters () {
        request = super.toString().toLowerCase();
    }
    
    @Override
    public String toString () {
        return request;
    }
    
}
