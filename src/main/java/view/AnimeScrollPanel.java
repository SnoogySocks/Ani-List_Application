package view;

import model.Anime;

import javax.swing.*;
import java.util.ArrayList;

import static view.Page.*;

/**
 * Displays anime from a category that allows for scrolling
 */
public class AnimeScrollPanel extends JPanel {
    
    private ArrayList<Anime> animeCategory;         // The anime from the category
    private int leftPtr;                            // The left display position in animeCategory
    private final AnimeImage[] displayedAnime;      // The displayed anime
    private final JButton[] displayedAnimeButtons;  // The buttons for the displayed anime
    
    private final JButton[] scrollButtons;          // The buttons for scrolling
    
    public AnimeScrollPanel (ArrayList<Anime> animeCategory, double animeImageSize) {
    
        // Set up the panel
        setOpaque(false);
        setSize(Page.WIDTH-PADDING*11, AnimeImage.getScaledHeight(animeImageSize));
        setLayout(null);
    
        // Initialize the anime category
        this.animeCategory = animeCategory;
    
        // Determine the size of the panel and each animeImage
        final int ANIME_IMAGE_PADDING;
        if (animeImageSize==AnimeImage.LARGE_SIZE) {
            ANIME_IMAGE_PADDING = (getWidth()-PADDING*6-AnimeImage.getScaledWidth(animeImageSize)*5)/4;
            displayedAnime = new AnimeImage[5];
            displayedAnimeButtons = new JButton[5];
        } else {
            ANIME_IMAGE_PADDING = (getWidth()-PADDING*6-AnimeImage.getScaledWidth(animeImageSize)*7)/6;
            displayedAnime = new AnimeImage[7];
            displayedAnimeButtons = new JButton[7];
        }
        leftPtr = 0;
    
        // Initialize the images on the panel
        for (int i = leftPtr; i<displayedAnime.length; ++i) {
            
            // Initialize displayed anime and their buttons
            displayedAnime[i] = new AnimeImage(animeCategory.get(i), animeImageSize);
            displayedAnimeButtons[i] = new JButton();
            
            displayedAnimeButtons[i].setIcon(displayedAnime[i].getIcon());
            displayedAnimeButtons[i].setSize(displayedAnime[i].getSize());
            displayedAnimeButtons[i].setLocation(PADDING*3+(displayedAnime[i].getDisplayedImage().getIconWidth()+ANIME_IMAGE_PADDING)*i, 0);
            add(displayedAnimeButtons[i]);
            
        }
    
        int displayedAnimeHeight = displayedAnime[0].getDisplayedImage().getIconHeight();
        scrollButtons = new JButton[2];
        String[] buttonText = { "left", "right" };
        
        // Initialize the scroll buttons
        for (int i = 0; i<scrollButtons.length; ++i) {
    
            scrollButtons[i] = new JButton(buttonText[i]);
            scrollButtons[i].setFont(DIALOGUE_FONT);
            scrollButtons[i].setBorder(null);
            scrollButtons[i].setBackground(USER_INPUT_COLOUR);
            scrollButtons[i].setForeground(TEXT_COLOUR);
            scrollButtons[i].setSize(PADDING*3, displayedAnimeHeight/3);
            add(scrollButtons[i]);
            
        }
        scrollButtons[0].setLocation(0, displayedAnimeHeight/3);
        scrollButtons[1].setLocation(Page.getRightX(displayedAnimeButtons[displayedAnimeButtons.length-1]), displayedAnimeHeight/3);
        
    }
    
    /**
     * Scroll to the left
     */
    public void scrollLeft () {
        
        // Scrolls displayedAnime.length anime to the left
        leftPtr -= displayedAnime.length;
        
        // Cycle to the end if it is out of bounds
        if (leftPtr<0) {
            leftPtr += animeCategory.size();
        }
        updateDisplayedAnime();
        
    }
    
    /**
     * Scroll to the right
     */
    public void scrollRight () {
        
        // Scrolls displayedAnime.length anime to the right
        leftPtr += displayedAnime.length;
        
        // Cycle to the beginning
        if (leftPtr>=animeCategory.size()) {
            leftPtr -= animeCategory.size();
        }
        updateDisplayedAnime();
        
    }
    
    // Getters and setters
    public ArrayList<Anime> getAnimeCategory () {
        return animeCategory;
    }
    
    public void setAnimeCategory (ArrayList<Anime> newCategory) {
        
        animeCategory = newCategory;
        leftPtr = displayedAnime.length;
        updateDisplayedAnime();
        
    }
    
    public AnimeImage[] getDisplayedAnime () {
        return displayedAnime;
    }
    
    public JButton[] getDisplayedAnimeButtons () {
        return displayedAnimeButtons;
    }
    
    public JButton[] getScrollButtons () {
        return scrollButtons;
    }
    
    /**
     * Update the displayed anime when scrolled
     */
    private void updateDisplayedAnime () {
        
        // For each displayed anime
        for (int i = leftPtr, j = 0; j<displayedAnime.length; ++i, ++j) {
            
            // Cycle to the beginning if out of bounds
            if (i>=animeCategory.size()) {
                i = 0;
            }
            
            // Provide the new anime image
            displayedAnime[j].setAnime(animeCategory.get(i));
            displayedAnimeButtons[j].setIcon(displayedAnime[j].getIcon());
            
        }
        
    }
    
    /**
     * Disable or enable all input
     * @param enabled = whether to disable or enable input
     * @see Page#setEnabledUserInput(boolean)
     */
    public void setEnabledUserInput (boolean enabled) {
        
        // Enable/disable anime buttons
        for (JButton animeButton : displayedAnimeButtons) {
            animeButton.setEnabled(enabled);
        }
        
        // Enable/disable scrolling
        for (JButton scrollButton : scrollButtons) {
            scrollButton.setEnabled(enabled);
        }
        
    }
    
}
