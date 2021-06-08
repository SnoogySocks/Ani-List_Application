package view;

import model.Anime;

import javax.swing.*;
import java.awt.*;

public abstract class Page extends JPanel {
    
    // Size constants
    public static final int WIDTH = MainFrame.WIDTH;
    public static final int HEIGHT = MainFrame.HEIGHT;
    
    public static int PADDING = 20;
    public static int PADDING_Y = 40;
    
    // Font sizes
    public static final Font CATEGORY_FONT = new Font("Ubuntu", Font.BOLD, 29);
    public static final Font DIALOGUE_FONT = new Font("Ubuntu", Font.PLAIN, 19);
    
    // Colour
    public static final Color BACKGROUND_COLOUR = new Color(237, 244, 244);
    public static final Color USER_INPUT_COLOUR = new Color(213, 235, 255);
    public static final Color TEXT_COLOUR = new Color(67, 67, 67);
    public static final Color DIALOGUE_COLOUR = new Color(255, 255, 255);
    
    // For eventually displaying anime
    private final AnimePanel animePanel;
    
    private final JScrollPane scrollPane;
    private final TitlePanel titlePanel;
    
    public Page () {
    
        setLayout(null);
        setBackground(BACKGROUND_COLOUR);
    
        scrollPane = new JScrollPane(this);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBounds(0, 0, WIDTH-12, HEIGHT-35);
    
        animePanel = new AnimePanel();
//        animePanel.setVisible(false);
        add(animePanel);
        
        // Does not create a genre if this is a RecommendationPage
        titlePanel = new TitlePanel(!(this instanceof RecommendationPage));
        add(titlePanel);
        
    }
    
    public JScrollPane getScrollPane () {
        return scrollPane;
    }
    
    public TitlePanel getTitlePanel () {
        return titlePanel;
    }
    
    public AnimePanel getAnimePanel () {
        return animePanel;
    }
    
    /**
     * Removes the anime panel.
     */
    public void disableAnimePanel () {
        
        animePanel.disableAnimePanel();
        titlePanel.setEnabledUserInput(true);
        
    }
    
    /**
     * Adds the anime panel when user interacts with something.
     * Cancels out all user input
     * @param anime
     */
    public void enableAnimePanel (Anime anime) {
        
        // Disable all input features
        titlePanel.setEnabledUserInput(false);
        
        // Remove scrolling
        setPreferredSize(getSize());
        animePanel.enableAnimePanel(anime);
        
    }
    
    public static int getRightX (JComponent comp) {
        return comp.getX()+comp.getWidth();
    }
    
    public static int getBottomY (JComponent comp) {
        return comp.getY()+comp.getHeight();
    }

}
