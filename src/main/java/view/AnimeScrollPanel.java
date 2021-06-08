package view;

import model.Anime;

import javax.swing.*;
import java.util.ArrayList;

import static view.Page.*;

public class AnimeScrollPanel extends JPanel {

    private final double animeImageSize;
    
    private ArrayList<Anime> animeCategory;
    private int rightPtr;
    private final AnimeImage[] displayedAnime;
    
    private final JButton[] scrollButtons;
    
    public AnimeScrollPanel (ArrayList<Anime> animeCategory, double animeImageSize) {
    
        setOpaque(false);
        setSize(Page.WIDTH-PADDING*11, AnimeImage.getScaledHeight(animeImageSize));
        setLayout(null);
    
        this.animeCategory = animeCategory;
        this.animeImageSize = animeImageSize;
    
        final int ANIME_IMAGE_PADDING;
        if (animeImageSize==AnimeImage.LARGE_SIZE) {
            ANIME_IMAGE_PADDING = (getWidth()-PADDING*6-AnimeImage.getScaledWidth(animeImageSize)*5)/4;
            displayedAnime = new AnimeImage[5];
        } else {
            ANIME_IMAGE_PADDING = (getWidth()-PADDING*6-AnimeImage.getScaledWidth(animeImageSize)*7)/6;
            displayedAnime = new AnimeImage[7];
        }
        rightPtr = displayedAnime.length;
    
        for (int i = getLeft(); i!=rightPtr; ++i) {
            displayedAnime[i] = new AnimeImage(animeCategory.get(i), animeImageSize);
            displayedAnime[i].setLocation(PADDING*3+(displayedAnime[i].getDisplayedImage().getIconWidth()+ANIME_IMAGE_PADDING)*i, 0);
            add(displayedAnime[i]);
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
        scrollButtons[1].setLocation(Page.getRightX(displayedAnime[displayedAnime.length-1]), displayedAnimeHeight/3);
        
    }
    
    public int getLeft () {
        int left = rightPtr-displayedAnime.length;
        return left>=0? left : animeCategory.size()+left;
    }
    
    public void scrollLeft () {
        
        rightPtr -= displayedAnime.length;
        
        // Check bounds
        if (rightPtr==0) {
            rightPtr = animeCategory.size();
        }
        updateDisplayedAnime();
        
    }
    
    public int getRight () {
        return rightPtr;
    }
    
    public void scrollRight () {
        
        rightPtr += displayedAnime.length;
        
        // Check Bounds
        if (rightPtr>animeCategory.size()) {
            rightPtr = animeCategory.size()-rightPtr;
        }
        updateDisplayedAnime();
        
    }
    
    public ArrayList<Anime> getAnimeCategory () {
        return animeCategory;
    }
    
    public AnimeImage getDisplayedAnime (int index) {
        return displayedAnime[index];
    }
    
    public JButton getScrollButtons (int index) {
        return scrollButtons[index];
    }
    
    public void updateAnimeCategory (ArrayList<Anime> newCategory) {
        
        animeCategory = newCategory;
        rightPtr = displayedAnime.length;
        updateDisplayedAnime();
        
    }
    
    public void updateDisplayedAnime () {
        
        for (int i = getLeft(); i!=rightPtr; ++i) {
            if (i>=animeCategory.size()) {
                i = 0;
            }
            displayedAnime[i].setAnime(animeCategory.get(i));
        }
        
    }
    
    public void setEnabledUserInput (boolean enabled) {
        
        // Enable/disable anime buttons
        for (AnimeImage anime : displayedAnime) {
            anime.setEnabled(enabled);
        }
        
        // Enable/disable scrolling
        for (JButton scrollButton : scrollButtons) {
            scrollButton.setEnabled(enabled);
        }
        
    }
    
}
