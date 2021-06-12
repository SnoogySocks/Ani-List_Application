package controller;

import view.RecommendPage;

import java.awt.event.ActionEvent;

public class RecommendPageController extends PageController {
    
    private RecommendPage gui;
    
    public RecommendPageController (RecommendPage gui) {
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