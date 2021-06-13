package jikanEnums;

/**
 * Enum to indicate the user's status for an anime
 */
public enum Status {
    
    ON_HOLD, WATCHING, COMPLETED, DROPPED, NA;
    
    public String name;
    
    Status () {
        
        String og = super.toString();
        if (og.equals("ON_HOLD")) {
            name = "On Hold";
        } else if (og.equals("NA")) {
            name = "N/A";
        } else {
            name = og.charAt(0)+og.substring(1).toLowerCase();
        }
        
    }
    
    @Override
    public String toString () {
        return name;
    }
    
}
