package controller;

import view.RecommendPage;

public class RecommendPageController extends GUIController {
    
    private RecommendPage gui;
    
    public RecommendPageController (JikanController jikan, RecommendPage gui) {
        
        super(jikan);
        this.gui = gui;
        
    }
    
    @Override
    public void setUpListeners () {
    
    
    
    }
    
}