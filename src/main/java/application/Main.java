package application;

import controller.JikanController;
import jikanEnums.Genre;
import jikanEnums.Status;

public class Main {
    
    public static void main (String[] args) {
    
        for (Genre genre : Genre.values()) {
            System.out.println(genre);
        }
    
    }
    
}
