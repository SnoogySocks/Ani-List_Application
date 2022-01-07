package jikanEnums;

/**
 * Enum class for the day of the week
 */
public enum Schedule {

    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;
    
    private final String weekDay;
    
    Schedule () {
        this.weekDay = super.toString().toLowerCase();
    }
    
    @Override
    public String toString () {
        return weekDay;
    }
    
}
