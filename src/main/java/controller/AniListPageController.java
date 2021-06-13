package controller;

import model.Anime;
import view.AniListAnimeBar;
import view.AniListPage;
import view.Page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AniListPageController extends PageController {
    
    private final AniListPage gui;
    
    public AniListPageController (AniListPage gui) {
        super(gui.getAnimePanel());
        this.gui = gui;
    }
    
    @Override
    public void setUpListeners () {
    
        super.setUpListeners();
        gui.getSortComboBox().addActionListener(this);
        for (AniListAnimeBar bar: gui.getAnimeBars()) {
            bar.getEditButton().addActionListener(this);
        }
    
    }
    
    @Override
    public void actionPerformed (ActionEvent e) {
    
        if (e.getSource()==gui.getSortComboBox()) {
            generateAniList();
            return;
        }
        
        for (AniListAnimeBar bar: gui.getAnimeBars()) {
            
            // If the source is an edit button then inquire
            // the user about the status of the anime
            if (e.getSource()==bar.getEditButton()) {
    
                // Update the anime with the new status
                inquireAnimeStatus(bar.getAnime());
    
                // Create a new updated list
                generateAniList();
                break;
                
            }
            
        }
    
        if (e.getSource()==gui.getAnimePanel().getBackButton()) {
            gui.disableAnimePanel();
        }
        
    }
    
    /**
     * Create the AniList
     */
    public void generateAniList () {
        
        final ArrayList<Anime> aniList = gui.isOrderedDescending()
                ? Page.getAniList().generateDescendingList()
                : Page.getAniList().generateAscendingList();
        final ArrayList<AniListAnimeBar> animeBars = gui.getAnimeBars();
        
        // Remove all the current ani bars from the display
        for (AniListAnimeBar bar: animeBars) {
            gui.getAniListPanel().getDisplayPanel().remove(bar);
        }
        animeBars.clear();
        
        // Display all the anime
        for (Anime anime: aniList) {
            
            int back = animeBars.size();
            animeBars.add(new AniListAnimeBar(anime, back+1));
            animeBars.get(back).setLocation(0, Page.PADDING+Page.PADDING_Y+AniListAnimeBar.HEIGHT*back);
            animeBars.get(back).getEditButton().addActionListener(this);
            gui.getAniListPanel().getDisplayPanel().add(animeBars.get(back));
            
        }
        
        // Adjust the size of the list
        gui.getAniListPanel().setSize(
                gui.getAniListPanelDimensions().width,
                gui.getAniListPanelDimensions().height
                        +AniListAnimeBar.HEIGHT*aniList.size()
        );
        
        gui.getActualSize().setSize(new Dimension(
                Page.WIDTH,
                Page.HEIGHT+AniListAnimeBar.HEIGHT*aniList.size()
        ));
        gui.revalidate();
        
    }
    
    @Override
    public void mouseDragged (MouseEvent e) {
        super.mouseDragged(e);
        gui.repaint();
    }
    
}
