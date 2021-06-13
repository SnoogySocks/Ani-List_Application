package controller;

import jikanEnums.Genre;
import model.Anime;
import view.AnimeImage;
import view.Page;
import view.RecommendPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class RecommendPageController extends PageController {
    
    private final RecommendPage gui;
    private final ArrayList<AnimeImage> displayedAnime;
    
    private final Point draggedAnimeOGLocation;
    private AnimeImage draggedAnime;
    
    private final HashSet<Anime> uninterestedAnime;
    private final HashSet<Anime> interestedAnime;
    
    public RecommendPageController (RecommendPage gui) {
        
        super(gui.getAnimePanel());
        this.gui = gui;
        draggedAnimeOGLocation = new Point();
        displayedAnime = new ArrayList<>();
        uninterestedAnime = new HashSet<>();
        interestedAnime = new HashSet<>();
        
    }
    
    @Override
    public void setUpListeners () {
        super.setUpListeners();
        gui.getSwitchModesButton().addActionListener(this);
    }
    
    @Override
    public void actionPerformed (ActionEvent e) {
    
        if (e.getSource()==gui.getSwitchModesButton()) {
    
            // Only generate the anime if it is switching from
            // non-recommending to recommending mode
            if (gui.getTitlePanel().isVisible()) {
        
                // Do not toggle the screen if there are not enough anime.
                if (!generateAnime()) {
                    return;
                }
                // Clear the interested and uninterested anime
                uninterestedAnime.clear();
                interestedAnime.clear();
        
                // Otherwise, Remove all the anime images from the screen
            } else {
        
                for (AnimeImage animeImage: displayedAnime) {
                    gui.remove(animeImage);
                }
                displayedAnime.clear();
        
            }
            
            toggleRecommending();
            
        }
    
        if (e.getSource()==gui.getAnimePanel().getBackButton()) {
            gui.disableAnimePanel();
        }
        
    }
    
    /**
     * The panel is not recommending if the title panel is visible
     */
    public void toggleRecommending () {
        gui.setToRecommending(gui.getTitlePanel().isVisible());
    }
    
    /**
     * Generate the anime that will possibly be recommended
     */
    public boolean generateAnime () {
        
        // Get the filters
        String season, year;
        JComboBox<String> comboBox = (JComboBox<String>) gui.getTitlePanel().getFilterBar().getFilterOptions().get(0);
        season = ((String) comboBox.getSelectedItem()).toLowerCase();
    
        comboBox = (JComboBox<String>) gui.getTitlePanel().getFilterBar().getFilterOptions().get(1);
        year = ((String) comboBox.getSelectedItem()).toLowerCase();
        
        // Query for the anime arraylist according to the filters
        ArrayList<Anime> animeArrayList = JikanController.getSeason(year, season);
        
        // Obtain the number of anime to generate
        int numAnimeToGenerate = (Integer) gui.getNumAnimeToGenerateComboBox().getSelectedItem();
        
        // Get numAnimeToGenerate anime that the user has not seen before
        Anime[] haveNotRecommended = new Anime[numAnimeToGenerate];
        for (int i = 0; i<animeArrayList.size() && numAnimeToGenerate!=0; ++i) {
            
            // If the anime has not been recommended yet and the average score
            // is not 0 then add it to the list to display
            if (!Page.getAniList().getAlreadyRecommended()
                    .contains(animeArrayList.get(i).getMalID())
                    && animeArrayList.get(i).getAverageScore()!=0) {
                haveNotRecommended[--numAnimeToGenerate] = animeArrayList.get(i);
            }
            
        }
        
        // If not enough new anime have been found, then notify the player
        if (numAnimeToGenerate!=0) {
            
            JOptionPane.showMessageDialog(
                    ApplicationController.getFrame(),
                    "Not enough anime have been found that\n" +
                            "you haven't already seen. Choose\n" +
                            "different filter options."
            );
            return false;
            
        }
        
        generateAnime(haveNotRecommended);
        return true;
        
    }
    
    /**
     * Display the generated anime sparingly around the screen
     * @param animeList = the anime to display
     */
    public void generateAnime (Anime[] animeList) {
    
        Random random = new Random();
        
        gui.setVisible(false);
        for (Anime anime: animeList) {
            
            AnimeImage animeImage = new AnimeImage(anime, AnimeImage.SMALL_SIZE);
            Page.getAniList().getAlreadyRecommended().add(animeImage.getAnime().getMalID());
            displayedAnime.add(animeImage);
            
            // Generate a random location within the screen
            int x = Page.WIDTH/2-gui.getInterestedPanel().getWidth()+random.nextInt(
                    gui.getInterestedPanel().getWidth()*2
                    -animeImage.getWidth()
            );
            int y = gui.getSwitchModesButton().getHeight()+random.nextInt(
                    gui.getInterestedPanel().getHeight()
                            -gui.getSwitchModesButton().getHeight()
                            -animeImage.getHeight()
            );
            animeImage.setLocation(x, y);
            animeImage.addMouseListener(this);
            animeImage.addMouseMotionListener(this);
            
            gui.add(animeImage);
            
        }
        
        // Add the panels here so that they appear under the anime images
        gui.remove(gui.getUninterestedPanel());
        gui.remove(gui.getInterestedPanel());
        gui.add(gui.getUninterestedPanel());
        gui.add(gui.getInterestedPanel());
        
        gui.setVisible(true);
    
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
     * Identify the dragged image
     * @param e = the event
     */
    @Override
    public void mousePressed (MouseEvent e) {
        
        super.mousePressed(e);
        if (!gui.getAnimePanel().isVisible() && e.getSource() instanceof AnimeImage) {
            
            draggedAnime = (AnimeImage) e.getSource();
            
            // Do not drag the anime if it has already been categorized
            if (uninterestedAnime.contains(draggedAnime.getAnime())
                    || interestedAnime.contains(draggedAnime.getAnime())) {
                
                draggedAnime = null;
                
                // Notify the user
//                JOptionPane.showMessageDialog(
//                        ApplicationController.getFrame(),
//                        "You cannot drag already categorized anime",
//                        "Alert", JOptionPane.ERROR_MESSAGE
//                );
                
            } else {
                draggedAnimeOGLocation.setLocation(draggedAnime.getLocation());
            }
            
        }
        
    }
    
    /**
     * Check if the anime had been dropped on the boxes or not.
     * @param e = the event
     */
    @Override
    public void mouseReleased (MouseEvent e) {
        
        super.mouseReleased(e);
        if (e.getSource()!=draggedAnime) {
            return;
        }
    
        // If the dragged anime is out of bounds,
        // set it to its original location
        if (draggedAnime.getX()<0 || Page.WIDTH<Page.getRightX(draggedAnime)
                || draggedAnime.getY()<0 || Page.HEIGHT<Page.getBottomY(draggedAnime)) {
            draggedAnime.setLocation(draggedAnimeOGLocation);
            
        // Otherwise if the anime is released in uninterested
        } else if (Page.getRightX(draggedAnime)<=Page.getRightX(gui.getUninterestedPanel())) {
            caterUserPreferences(draggedAnime.getAnime(), false);
        
        // Otherwise if the anime is released in interested
        } else if (gui.getInterestedPanel().getX()<=draggedAnime.getX()) {
            caterUserPreferences(draggedAnime.getAnime(), true);
        }
    
        draggedAnime = null;
        gui.repaint();
        
        evaluateResults();
        
    }
    
    public void caterUserPreferences (Anime anime, boolean isInterested) {
    
        // Record the anime
        if (isInterested) {
            interestedAnime.add(anime);
        } else {
            uninterestedAnime.add(anime);
        }
        
        double[] totalUserGenreScore = Page.getAniList().getTotalUserGenreScore();
        if (anime.getAverageScore()==0) {
            System.out.println("not ok");
        }
        
        // Iterate through the anime's genres
        for (Genre genre: anime.getGenres()) {
        
            // Evaluate the genre
            totalUserGenreScore[genre.getGenreID()] += isInterested
            
                    // Add 15-animeScore to the current genre’s score (lower scoring
                    // anime (score of around 5) that the user is interested
                    // in is more influential than a high scoring anime the
                    // user is interested in, so lower scoring anime provide
                    // higher genre scores than higher ones)
                    ? 15-anime.getAverageScore()
            
                    // Higher scoring anime that the user is uninterested
                    // in is more influential than a lower scoring anime
                    // the user is uninterested in, so higher scoring anime
                    // provide quadratically lower scores than lower scoring
                    // ones
                    : -2.3*(anime.getAverageScore()-5);
        
        }
        
        // Move the displayed anime
        for (AnimeImage animeImage: displayedAnime) {
            
            // Do not do anything to already categorized anime
            if (uninterestedAnime.contains(animeImage.getAnime())
                    || interestedAnime.contains(animeImage.getAnime())) {
                continue;
            }
            
            double totalGenreScore = 0d;
            
            // Iterate through each anime's genre
            for (Genre genre : animeImage.getAnime().getGenres()) {
                totalGenreScore += totalUserGenreScore[genre.getGenreID()];
            }
            
            // Calculate the recommendation score as a score out of 100
            double recommendationScore = totalGenreScore*anime.getAverageScore()
                    /(animeImage.getAnime().getGenres().length*150);
            
            // Set the anime's displacement based on its recommendation score and the
            // distance between it and category it is directed towards
            int displacement = (int) (recommendationScore*(recommendationScore>0
                    ? gui.getInterestedPanel().getX()-Page.getRightX(animeImage)
                    : animeImage.getX()-Page.getRightX(gui.getUninterestedPanel())
            ));
            
            int newX = recommendationScore>0
                    ? Math.min(animeImage.getX()+displacement, gui.getInterestedPanel().getX()-animeImage.getWidth())
                    : Math.max(animeImage.getX()+displacement, Page.getRightX(gui.getUninterestedPanel()));
            
            animeImage.setLocation(newX, animeImage.getY());
            
        }
    
    }
    
    public void evaluateResults () {
    
    
    
    }
    
    /**
     * Drag the anime with the mouse
     * @param e = the event
     */
    @Override
    public void mouseDragged (MouseEvent e) {
        
        super.mouseDragged(e);
    
        if (e.getSource()!=draggedAnime) {
            return;
        }
        
        // If the mouse is dragging the image is dragged with it
        Point mouseLocation = ApplicationController.getFrame().getMouseOnFrame(e);
    
        // An anime is dragged if draggedAnime is not null
        if (e.getSource()==draggedAnime) {
            
            draggedAnime.setLocation(
                    mouseLocation.x-draggedAnime.getWidth()/2,
                    mouseLocation.y-draggedAnime.getHeight()/2
            );
            
        }
        
        gui.repaint();
        
    }
    
}