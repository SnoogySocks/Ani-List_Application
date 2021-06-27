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

/**
 * The controller for the recommend page
 * @see PageController
 */
public class RecommendPageController extends PageController {
    
    private final RecommendPage gui;
    private final ArrayList<AnimeImage> displayedAnime;     // Track the displayed anime that got generated
    
    private final Point draggedAnimeOGLocation;         // Track the original location of the dragged anime
    private AnimeImage draggedAnime;                    // The image to the dragged anime
    
    private final HashSet<Anime> uninterestedAnime;     // Anime the user is not interested in
    private final HashSet<Anime> interestedAnime;       // Anime the user is interested in
    
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
    
        // Do not do anything if an action is in the progress of being performed
        if (ApplicationController.getCurrentPage().getLoadingPanel().isVisible()) {
            return;
        }
        
        if (e.getSource()==gui.getSwitchModesButton()) {
    
            // If the user made a decision with an anime then
            // evaluate the session
            if (uninterestedAnime.size()!=0 || interestedAnime.size()!=0) {
                evaluateResults();
            }
            
            ApplicationController.runLongTask(()->{
    
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
    
                // Toggle the mode of the page
                toggleRecommending();
                
            });
    
            // Display a help message to the user when transitioning to recommend mode
            if (!gui.getTitlePanel().isVisible()) {
                
                JOptionPane.showMessageDialog(
                        ApplicationController.getFrame(),
                        "Drag anime you are interested in to the\n"+
                                "green, and uninterested in the red. The \n"+
                                "program will try to guess your interests\n" +
                                "by moving the anime towards either\n"+
                                "the interested or uninterested panels.\n\n"+
                                "Double click an image to view more about\n"+
                                "it.",
                        "Information Message",
                        JOptionPane.INFORMATION_MESSAGE
                );
                
            }
            
        }
    
        // Disable the anime panel when the back button is pressed
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
        
        if (!ApplicationController.getCurrentPage().getLoadingPanel().isVisible()
                && e.getClickCount()==2 && e.getSource() instanceof AnimeImage) {
            
            ApplicationController.runLongTask(()->{
                
                Anime pickedAnime = ((AnimeImage) e.getSource()).getAnime();
                JikanController.setAnimePanel(pickedAnime);
                gui.enableAnimePanel(pickedAnime);
                
            });
            
        }
        
    }
    
    /**
     * Identify the dragged image
     * @param e = the event
     */
    @Override
    public void mousePressed (MouseEvent e) {
        
        super.mousePressed(e);
        if (!ApplicationController.getCurrentPage().getLoadingPanel().isVisible()
                && !gui.getAnimePanel().isVisible() && e.getSource() instanceof AnimeImage) {
            
            draggedAnime = (AnimeImage) e.getSource();
            
            // Do not drag the anime if it has already been categorized
            if (uninterestedAnime.contains(draggedAnime.getAnime())
                    || interestedAnime.contains(draggedAnime.getAnime())) {
                
                draggedAnime = null;
                
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
        if (ApplicationController.getCurrentPage().getLoadingPanel().isVisible()
                || e.getSource()!=draggedAnime) {
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
        
    }
    
    /**
     * Move the anime towards either interested or uninterested
     * based on the user's interest in the anime the user selected
     * @param anime = the anime the user selected
     * @param isInterested = whether the user is interested in the anime
     */
    public void caterUserPreferences (Anime anime, boolean isInterested) {
    
        // Record the anime
        if (isInterested) {
            interestedAnime.add(anime);
        } else {
            uninterestedAnime.add(anime);
        }
        
        // Iterate through the anime's genres
        double[] totalUserGenreScore = Page.getAniList().getTotalUserGenreScore();
        for (Genre genre: anime.getGenres()) {
        
            // Evaluate the genre
            totalUserGenreScore[genre.getGenreID()] += isInterested
                    ? 13-anime.getAverageScore()
                    : -3*Math.pow(1.8, anime.getAverageScore()-6.5);
        
        }
        
        // Move the displayed anime
        for (AnimeImage animeImage: displayedAnime) {
            
            Anime anotherAnime = animeImage.getAnime();
            
            // Do not do anything to already categorized anime
            if (uninterestedAnime.contains(anotherAnime)
                    || interestedAnime.contains(anotherAnime)) {
                continue;
            }
            
            double totalGenreScore = 0d;
            
            // Iterate through each anime's genre
            for (Genre genre : anotherAnime.getGenres()) {
                totalGenreScore += totalUserGenreScore[genre.getGenreID()];
            }
            
            if (anotherAnime.getTitle().equals("Ex-Arm")) {
                System.out.println();
            }
            
            // Calculate the recommendation score as a score out of 100
            double recommendationScore = (totalGenreScore/(anotherAnime.getGenres().length*150)
                    +Math.log(anotherAnime.getAverageScore()/7))*Math.abs(anotherAnime.getAverageScore()-6.5)*2;
            
            if (anotherAnime.getTitle().equals("Horimiya")
                    || anotherAnime.getTitle().equals("Mushoku Tensei: Isekai Ittara Honki Dasu")
                    || anotherAnime.getTitle().equals("5-toubun no Hanayome ∬")) {
                System.out.println();
            }
            
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
    
    /**
     * Display a summary of all the anime the user selected
     * and their preferences towards genre
     */
    public void evaluateResults () {
    
        // Obtain the uninterested anime as an array
        Anime[] uninterestedAnime = new Anime[this.uninterestedAnime.size()];
        this.uninterestedAnime.toArray(uninterestedAnime);
    
        // Record already categorized anime
        for (Anime anime: this.uninterestedAnime) {
            Page.getAniList().getAlreadyRecommended().add(anime.getMalID());
            
            
        }
        
        // Obtain the interested anime as an array
        Anime[] interestedAnime = new Anime[this.interestedAnime.size()];
        this.interestedAnime.toArray(interestedAnime);
    
        // Add interested anime to the user's Ani-List
        for (Anime anime: this.interestedAnime) {
            Page.getAniList().add(anime);
        }
    
        // Summarize the current recommending session to the user
        Object[] message = {
                "The anime you are not interested in are...\n",
                uninterestedAnime
        };
        
        JOptionPane.showMessageDialog(
                ApplicationController.getFrame(),
                message,
                "Uninterested Anime",
                JOptionPane.INFORMATION_MESSAGE
        );
        
        message = new Object[] {
                "The anime you are not interested in are...\n",
                interestedAnime
        };
        
        JOptionPane.showMessageDialog(
                ApplicationController.getFrame(),
                message,
                "Interested Anime",
                JOptionPane.INFORMATION_MESSAGE
        );
        
        // Find the user's most and least favourite genre
        double[] totalUserGenreScore = Page.getAniList().getTotalUserGenreScore();
        
        Genre mostFavouriteGenre, mostHatedGenre;
        mostFavouriteGenre = mostHatedGenre = Genre.NOTHING;
        for (Genre genre: Genre.values()) {
            
            // Replace the mostFavouriteGenre with genre if
            // the score is less than it
            if (totalUserGenreScore[mostFavouriteGenre.getGenreID()]
                    <totalUserGenreScore[genre.getGenreID()]) {
                mostFavouriteGenre = genre;
            }
    
            // Replace the mostHatedGenre with genre if
            // the score is greater than it
            if (totalUserGenreScore[mostHatedGenre.getGenreID()]
                    >totalUserGenreScore[genre.getGenreID()]) {
                mostHatedGenre = genre;
            }
            
        }
        
        // There is no hated or favourite genre if the genre score is 0
        if (totalUserGenreScore[mostFavouriteGenre.getGenreID()]==0) {
            mostFavouriteGenre = Genre.NOTHING;
        } else if (totalUserGenreScore[mostHatedGenre.getGenreID()]==0) {
            mostHatedGenre = Genre.NOTHING;
        }
    
        // Provide the user with the least and most favourite genre
        JOptionPane.showMessageDialog(
                ApplicationController.getFrame(),
                "Your most hated genre is "
                        +mostHatedGenre
                        +"\nand your most favourite is "
                        +mostFavouriteGenre
        );
    
        JOptionPane.showMessageDialog(
                ApplicationController.getFrame(),
                "Adding interested anime to your Ani-List..."
        );
    
    
    
    }
    
    /**
     * Drag the anime with the mouse
     * @param e = the event
     */
    @Override
    public void mouseDragged (MouseEvent e) {
    
        if (ApplicationController.getCurrentPage().getLoadingPanel().isVisible()
                || e.getSource()!=draggedAnime) {
            return;
        }
        
        super.mouseDragged(e);
        
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