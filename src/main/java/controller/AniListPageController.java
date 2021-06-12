package controller;

import view.AniListPage;

import java.awt.event.ActionEvent;

public class AniListPageController extends PageController {
    
    private AniListPage gui;
    
    public AniListPageController (AniListPage gui) {
        super(gui.getAnimePanel());
        this.gui = gui;
    }
    
    @Override
    public void setUpListeners () {
    
        super.setUpListeners();
    
    }
    
    @Override
    public void actionPerformed (ActionEvent e) {
    
    }
    
}
