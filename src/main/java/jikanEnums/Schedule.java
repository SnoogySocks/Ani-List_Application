package jikanEnums;

public enum Schedule {

    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;
    
    private String name;
    
    Schedule () {
        this.name = super.toString().toLowerCase();
    }
    
    @Override
    public String toString () {
        return name;
    }
    
}
