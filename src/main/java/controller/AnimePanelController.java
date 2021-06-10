package controller;

import view.AnimePanel;

public class AnimePanelController extends GUIController {
    
    private AnimePanel gui;
    
    public AnimePanelController (JikanController jikan, AnimePanel gui) {
        
        super(jikan);
        this.gui = gui;
        
    }
    
    @Override
    public void setUpListeners () {
    
    
    
    }
    
}