package controller;

import model.Anime;
import view.AniListAnimeBar;
import view.AniListPage;
import view.AnimeImage;
import view.Page;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Controller for the AniListPage
 * @see PageController
 */
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
    
        // Generate the list when a new sort is chosen
        if (e.getSource()==gui.getSortComboBox()) {
            generateAniList();
            return;
        }
        
        for (AniListAnimeBar bar: gui.getAnimeBars()) {
            
            // If the source is an edit button then inquire
            // the user about the status of the anime and regenerate the list
            if (e.getSource()==bar.getEditButton()) {
                inquireAnimeStatus(bar.getAnime());
                generateAniList();
                break;
            }
            
        }
    
        // Disable the anime panel when the back button is pressed
        if (e.getSource()==gui.getAnimePanel().getBackButton()) {
            gui.disableAnimePanel();
        }
        
    }
    
    /**
     * Generate the AniList on the panel
     */
    public void generateAniList () {
        
        boolean isOrderedDescending = gui.isOrderedDescending();
        
        // Get the ani-list based on the sorting order
        final ArrayList<Anime> aniList = isOrderedDescending
                ? Page.getAniList().generateDescendingList()
                : Page.getAniList().generateAscendingList();
        
        // Remove all the current ani bars from the display
        for (AniListAnimeBar bar: gui.getAnimeBars()) {
            gui.getAniListPanel().getDisplayPanel().remove(bar);
        }
        gui.getAnimeBars().clear();
        
        // Display all the anime
        for (Anime anime: aniList) {
            
            int back = gui.getAnimeBars().size();
            int rank = isOrderedDescending ? back+1 : aniList.size()-back;
    
            // Create the animeBar of the anime
            gui.getAnimeBars().add(new AniListAnimeBar(anime, rank));
            gui.getAnimeBars().get(back).setLocation(0, Page.PADDING+Page.PADDING_Y+AniListAnimeBar.HEIGHT*back);
            gui.getAnimeBars().get(back).getEditButton().addActionListener(this);
            gui.getAnimeBars().get(back).getAnimeImage().addMouseListener(this);
            gui.getAniListPanel().getDisplayPanel().add(gui.getAnimeBars().get(back));
            
        }
        
        // Adjust the size of the list
        gui.getAniListPanel().setSize(
                gui.getAniListPanelDimensions().width,
                gui.getAniListPanelDimensions().height
                        +AniListAnimeBar.HEIGHT*aniList.size()
        );
        
        // Readjust the size of the screen
        gui.getActualSize().setSize(new Dimension(
                Page.WIDTH,
                Page.HEIGHT+AniListAnimeBar.HEIGHT*aniList.size()
        ));
        gui.revalidate();
        
    }
    
    /**
     * Provide an anime panel if the user double clicks
     * @param e = the event
     */
    @Override
    public void mouseClicked (MouseEvent e) {
        
        if (e.getClickCount()==2 && e.getSource() instanceof AnimeImage) {
            
            Anime pickedAnime = ((AnimeImage) e.getSource()).getAnime();
            JikanController.setAnimePanel(pickedAnime);
            gui.enableAnimePanel(pickedAnime);
            
        }
        
    }
    
    /**
     * @see PageController#mouseDragged(MouseEvent)
     */
    @Override
    public void mouseDragged (MouseEvent e) {
        super.mouseDragged(e);
        gui.repaint();
    }
    
}
