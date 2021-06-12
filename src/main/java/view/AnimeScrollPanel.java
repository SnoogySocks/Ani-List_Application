package view;

import model.Anime;

import javax.swing.*;
import java.util.ArrayList;

import static view.Page.*;

public class AnimeScrollPanel extends JPanel {
    
    private ArrayList<Anime> animeCategory;
    private int leftPtr;
    private final AnimeImage[] displayedAnime;
    private final JButton[] displayedAnimeButtons;
    
    private final JButton[] scrollButtons;
    
    public AnimeScrollPanel (ArrayList<Anime> animeCategory, double animeImageSize) {
    
        setOpaque(false);
        setSize(Page.WIDTH-PADDING*11, AnimeImage.getScaledHeight(animeImageSize));
        setLayout(null);
    
        this.animeCategory = animeCategory;
    
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
    
        for (int i = leftPtr; i<displayedAnime.length; ++i) {
            
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
    
    public void scrollLeft () {
        
        leftPtr -= displayedAnime.length;
        
        // Cycle to the end
        if (leftPtr<0) {
            leftPtr += animeCategory.size();
        }
        updateDisplayedAnime();
        
    }
    
    public void scrollRight () {
        
        leftPtr += displayedAnime.length;
        
        // Cycle to the beginning
        if (leftPtr>=animeCategory.size()) {
            leftPtr -= animeCategory.size();
        }
        updateDisplayedAnime();
        
    }
    
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
    
    private void updateDisplayedAnime () {
        
        for (int i = leftPtr, j = 0; j<displayedAnime.length; ++i, ++j) {
            if (i>=animeCategory.size()) {
                i = 0;
            }
            displayedAnime[j].setAnime(animeCategory.get(i));
            displayedAnimeButtons[j].setIcon(displayedAnime[j].getIcon());
        }
        
    }
    
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
