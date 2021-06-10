package controller;

import view.HomePage;

public class HomePageController extends GUIController {
    
    private HomePage gui;
    
    public HomePageController (JikanController jikan, HomePage gui) {
        
        super(jikan);
        this.gui = gui;
        
    }
    
    @Override
    public void setUpListeners () {
    
    
    
    }
    
}
