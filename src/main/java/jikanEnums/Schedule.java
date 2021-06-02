package jikanEnums;

public enum Schedule {
    
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY,
    OTHER, UNKNOWN;
    
    public String request;
    
    Schedule () {
        request = super.toString().toLowerCase();
    }
    
    @Override
    public String toString () {
        return request;
    }
    
}
