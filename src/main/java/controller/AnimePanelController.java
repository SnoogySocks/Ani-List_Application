package controller;

import view.Page;

import java.awt.event.ActionEvent;

public class AnimePanelController extends PageController {
    
    private Page page;
    
    public AnimePanelController (JikanController jikan, Page page) {
        super(jikan);
        this.page = page;
    }
    
    @Override
    public void setUpListeners () {
        page.getAnimePanel().getBackButton().addActionListener(this);
    }
    
    @Override
    public void actionPerformed (ActionEvent e) {
        
        if (e.getSource()==page.getAnimePanel().getBackButton()) {
            page.disableAnimePanel();
        }
        
    }
    
}