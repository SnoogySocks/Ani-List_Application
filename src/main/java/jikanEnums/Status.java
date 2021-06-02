package jikanEnums;

public enum Status {
    
    AIRING, COMPLETE, TO_BE_AIRED, TBA, UPCOMING;
    
    public String request;
    
    Status () {
        request = super.toString().toLowerCase();
    }
    
    @Override
    public String toString () {
        return request;
    }
    
}
