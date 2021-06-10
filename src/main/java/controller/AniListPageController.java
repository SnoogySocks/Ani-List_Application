package controller;

import view.AniListPage;

public class AniListPageController extends GUIController {
    
    private AniListPage gui;
    
    public AniListPageController (JikanController jikan, AniListPage gui) {
        
        super(jikan);
        this.gui = gui;
        
    }
    
    @Override
    public void setUpListeners () {
    
    
    
    }
    
}
